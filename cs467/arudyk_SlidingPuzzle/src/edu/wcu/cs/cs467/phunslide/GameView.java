package edu.wcu.cs.cs467.phunslide;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * GaveView representing the game screen.
 *
 * @author Andriy Rudyk
 * @author Tim Sizemore
 * @version 25.02.2012
 */
public class GameView extends View {
    /** Tag representing this GameView. */
    private static final String TAG = "GameView";

    /** Number of pieces in the puzzle. */
    private int gridSize;

    /** Picture used for the puzzle. */
    private Bitmap puzzlePicture;

    /** Width of the puzzle picture. */
    private int width;

    /** Height of the puzzle picture. */
    private int height;

    /** Width of each piece. */
    private int pieceWidth;

    /** Height of each piece. */
    private int pieceHeight;

    /** Paint for the lines. */
    private Paint linePaint;

    /** Determines whether the numbers are printed on the tiles. */
    private boolean onNumbers;

    /** Holds the pieces of the puzzle picture mapped to an integer. */
    private Map<Integer, Bitmap> puzzleMap = new HashMap<Integer, Bitmap>();

    /** Holds the positions of each piece. */
    private int[][] positions;

    /** X position of the blank square. */
    private int blankX;

    /** Y position of the blank square. */
    private int blankY;

    /**
     * Constructor for GameView.
     *
     * @param context Contexts for this GameView view.
     */
    public GameView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor for this GameView.
     *
     * @param context Context given to this view.
     * @param attrs Attributes given to our GameView.
     */
    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor for this GameView.
     *
     * @param context Context given to this view.
     * @param attrs Attributes given to our GameView.
     * @param defStyle Given definition styles.
     */
    public GameView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initialization method of the view.
     */
    public void init() {
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        blankX = 0;
        blankY = 0;
    }

    /**
     * Sets the grid size of this game.
     *
     * @param gridSize Sets the game grid size to this.
     */
    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    /**
     * Sets the picture for the given puzzle.
     *
     * @param picture Picture to set for the puzzle.
     */
    public void initPicture(Bitmap picture) {
        for (Bitmap i : puzzleMap.values()) {
            i.recycle();
        }

        puzzleMap.clear();

        if (puzzlePicture != null) {
            puzzlePicture.recycle();
        }

        positions = new int[gridSize][gridSize];
        puzzlePicture = Bitmap.createScaledBitmap(picture,
                this.width, this.height, true);

        int counter = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Bitmap piece = Bitmap.createBitmap(puzzlePicture,
                        i * pieceWidth, j * pieceHeight,
                        pieceWidth, pieceHeight);
                puzzleMap.put(counter, piece);
                positions[i][j] = counter;
                counter++;
            }
        }
    }

    /**
     * Draws the initial picture and breaks it into smaller Bitmap pieces.
     *
     * @param canvas the view in which to draw on
     */
    public void draw(Canvas canvas) {
        if (puzzleMap != null && puzzlePicture != null) {
            for (int i = 0; i < gridSize; i++) {
                for (int j = 0; j < gridSize; j++) {
                    canvas.drawBitmap(puzzleMap.get(positions[i][j]),
                            i * pieceWidth, j * pieceHeight, null);
                    // if (positions[i][j] == 0) {
                    if (positions[i][j] == gridSize * gridSize - 1) {
                        blankX = i;
                        blankY = j;
                        canvas.drawRect(i * pieceWidth, j * pieceHeight,
                                i * pieceWidth + pieceWidth,
                                j * pieceHeight + pieceHeight, linePaint);
                    }
                }
            }

            for (int i = 1; i < gridSize; i++) {
                canvas.drawLine(pieceWidth * i, 0, pieceWidth * i, height,
                        linePaint);
                canvas.drawLine(0, pieceHeight * i, width, pieceHeight * i,
                        linePaint);
            }
        }
    }

    /**
     * Sets the size of the given Bitmap to the current screen size.
     *
     * @param width the width passed by the activity
     * @param height the height passed by the activity
     */
    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        //Bitmap pic = BitmapFactory.decodeResource(getResources(),
        //        R.drawable.robot);

        this.width  = MeasureSpec.getSize(width);
        this.height = MeasureSpec.getSize(height);

        this.pieceWidth        = this.width / gridSize;
        this.pieceHeight       = this.height / gridSize;
    }

    /**
     * Changes the colors of the lines between the tiles.
     *
     * @param color a color passed from the activity that sets the current line
     *              color
     */
    public void setGridColor(String color) {
        if (linePaint == null) {
            linePaint = new Paint();
        }
        if (color.equals("white")) {
            linePaint.setColor(getResources().getColor(R.color.white));
        } else if (color.equals("black")) {
            linePaint.setColor(getResources().getColor(R.color.black));
        } else if (color.equals("blue")) {
            linePaint.setColor(getResources().getColor(R.color.blue));
        } else if (color.equals("green")) {
            linePaint.setColor(getResources().getColor(R.color.green));
        } else if (color.equals("yellow")) {
            linePaint.setColor(getResources().getColor(R.color.yellow));
        } else if (color.equals("red")) {
            linePaint.setColor(getResources().getColor(R.color.red));
        } else {
            linePaint.setColor(getResources().getColor(R.color.magenta));
        }
    }

    /**
     * Set the field to determine whether to set the numbers on the tiles of
     * the view.
     *
     * @param onNumbers a boolean that is passed from the activity
     */
    public void setGridOn(boolean onNumbers) {
        this.onNumbers = onNumbers;
        invalidate();
    }

    /**
     * Registers when the view has been pressed and records the coordinates.
     *
     * @param event a MotionEvent that gives the position on the screen.
     * @return boolean
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            int x = (int) event.getX();
            int y = (int) event.getY();
            int xc = x / pieceWidth;
            int yc = y / pieceHeight;

            int temp = positions[blankX][blankY];

            if (blankX == xc && blankY - 1 == yc ||
                blankX + 1 == xc && blankY == yc ||
                blankX == xc && blankY + 1 == yc ||
                blankX - 1 == xc && blankY == yc) {
                positions[blankX][blankY] = positions[xc][yc];
                positions[xc][yc] = temp;
            }
            if (isSolved()) {
                Toast.makeText(getContext(), "Congrats, you solved it!",
                        Toast.LENGTH_LONG).show();
            }
            invalidate();
            break;
        }
        return true;
    }

    /**
     * Checks if the puzzle was solved.
     *
     * @return boolean
     */
    public boolean isSolved() {
        boolean solved;
        int wrong = 0;
        int count = 0;

        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (positions[i][j] != count) {
                    wrong++;
                }
                count++;
            }
        }
        if (wrong > 0) {
            solved = false;
        } else {
            solved = true;
        }
        return solved;
    }

    /**
     * Randomize the puzzle.
     */
    public void randomize() {
        Collections.shuffle(Arrays.asList(positions));
    }
}
