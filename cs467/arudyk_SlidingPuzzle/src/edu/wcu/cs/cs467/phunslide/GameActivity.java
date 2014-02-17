package edu.wcu.cs.cs467.phunslide;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.ImageColumns;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/*
 * Team: Andriy Rudyk & Tim Sizemore
 * GRADE: 98 -- nice work
 *
 * Extras:
 *
 * - Make puzzle from picture in gallery
 * - Make puzzle from a new picture (use camera)
 * - Displays toasts that tell user game status (ex. "game solved")
 * - Change background color
 * - Change line color
 * - Custom photoshoped "gimped" splash image (lots of points....)
 * 
 * ARD: I enable the 'Grid numbers' option in the preferences
 * ARD: while playing the game, but I don't see any grid numbers?
 * 
 * ARD: After starting a new game, the app remembers that I changed
 * ARD: the line color in the settings, but it draws the lines in
 * ARD: black; I have to change it _again_ in order for the line
 * ARD: color to take effect.
 * 
 * ARD: I can start playing the game before I've randomized it.
 * 
 * ARD: I caused a NPE while trying to select a picture from the
 * ARD: gallary:
 * 
 * 04-05 14:28:26.607: E/AndroidRuntime(1760): FATAL EXCEPTION: main
 * 04-05 14:28:26.607: java.lang.NullPointerException
 * 04-05 14:28:26.607: 	at edu.wcu.cs.cs467.phunslide.GameView.onTouchEvent(GameView.java:253)
 * 04-05 14:28:26.607: 	at android.view.View.dispatchTouchEvent(View.java:3901)
 * 04-05 14:28:26.607: 	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:869)
 * 04-05 14:28:26.607: 	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:869)
 * 04-05 14:28:26.607: 	at android.view.ViewGroup.dispatchTouchEvent(ViewGroup.java:869)
 * 04-05 14:28:26.607: 	at com.android.internal.policy.impl.PhoneWindow$DecorView.superDispatchTouchEvent(PhoneWindow.java:1737)
 * 04-05 14:28:26.607: 	at com.android.internal.policy.impl.PhoneWindow.superDispatchTouchEvent(PhoneWindow.java:1153)
 * 04-05 14:28:26.607: 	at android.app.Activity.dispatchTouchEvent(Activity.java:2096)
 * 04-05 14:28:26.607: 	at com.android.internal.policy.impl.PhoneWindow$DecorView.dispatchTouchEvent(PhoneWindow.java:1721)
 * 04-05 14:28:26.607: 	at android.view.ViewRoot.deliverPointerEvent(ViewRoot.java:2200)
 * 04-05 14:28:26.607: 	at android.view.ViewRoot.handleMessage(ViewRoot.java:1884)
 * 04-05 14:28:26.607: 	at android.os.Handler.dispatchMessage(Handler.java:99)
 * 04-05 14:28:26.607: 	at android.os.Looper.loop(Looper.java:130)
 * 04-05 14:28:26.607: 	at android.app.ActivityThread.main(ActivityThread.java:3835)
 * 04-05 14:28:26.607: 	at java.lang.reflect.Method.invokeNative(Native Method)
 * 04-05 14:28:26.607: 	at java.lang.reflect.Method.invoke(Method.java:507)
 * 04-05 14:28:26.607: 	at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:847)
 * 04-05 14:28:26.607: 	at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:605)
 * 04-05 14:28:26.607: 	at dalvik.system.NativeStart.main(Native Method)
 */

/**
 * Game Activity that has the game screen "GameView".
 *
 * @author Andriy Rudyk
 * @author Tim Sizemore
 * @version 27.02.2012
 */
public class GameActivity extends Activity {
    /** The default value for numbers on tiles. */
    public static final boolean DEFAULT_ON_NUMBERS = false;

    /** The default color for lines on the view. */
    public static final String DEFAULT_COLOR = "black";

    /** Tag for this Activity. */
    private static final String TAG = "GameActivity";

    /** Activity id for taking new picture. */
    private static final int TAKE_PICTURE = 0;

    /** Activity id for choosing picture. */
    private static final int CHOOSE_PICTURE = 1;

    /** Default grid size. */
    private static final int DEFAULT_SIZE = 3;

    /** Game preferences for this game. */
    private OnSharedPreferenceChangeListener prefListener;

    /** GameView in this activity. */
    private GameView gameView;

    /** Picture that goes inside our gameView. */
    private Bitmap puzzlePicture;

    /** Grid size ex. 3 -> a grid thats 3x3. */
    private int gridSize;

    /**
     * Gets called when this activity is created.
     *
     * @param savedInstanceState Bundle with all the saved things.
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        gameView = (GameView) findViewById(R.id.game_view);
        gridSize = getIntent().getIntExtra("gridSize", DEFAULT_SIZE);
        gameView.setGridSize(gridSize);
        selectPictureType();

        prefListener = new OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences sharedPrefs,
                                                    String key) {
                if (key.equals("grid_color")) {
                    String color = sharedPrefs.getString("grid_color",
                                                         DEFAULT_COLOR);
                    gameView.setGridColor(color);
                } else {
                    boolean onNumbers = sharedPrefs.getBoolean("numbers_on",
                                                            DEFAULT_ON_NUMBERS);
                    gameView.setGridOn(onNumbers);
                    Log.d(TAG, "The onNumbers value is: " + onNumbers);
                }
            }
        };
        PreferenceManager.getDefaultSharedPreferences(getBaseContext()).
                        registerOnSharedPreferenceChangeListener(prefListener);
    }

    /**
     * Dialog that lets the user pick picture type from a number of given
     * options.
     */
    public void selectPictureType() {
        final CharSequence[] items = {"Default picture",
                                      "Take new picture",
                                      "Existing picture", };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Create puzzle from:");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals(items[2])) {
                    startExistingPictureIntent();
                } else if (items[item].equals(items[1])) {
                    startTakePictureIntent();
                } else {
                    startDefaultPictureIntent();
                }
            }
        });
        AlertDialog picDialog = builder.create();
        picDialog.show();
    }

    /**
     * Starts a new game from existing picture.
     */
    public void startExistingPictureIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, CHOOSE_PICTURE);
    }

    /**
     * Starts a new game from a taken picture.
     */
    public void startTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, TAKE_PICTURE);
    }

    /**
     * Starts new game using default picture.
     */
    public void startDefaultPictureIntent() {
        puzzlePicture = BitmapFactory.decodeResource(getResources(),
                R.drawable.robot);
        regenGamePicture(puzzlePicture);
    }

    /**
     * Chooses which activity to start.
     *
     * @param requestCode a value that is taken from the click
     * @param resultCode a value that is taken from the click
     * @param data the intent in which the method receives
     */
    protected void onActivityResult(int requestCode, int resultCode,
        Intent data) {
        if ((requestCode == TAKE_PICTURE)
                && (resultCode == Activity.RESULT_OK)) {
            puzzlePicture = (Bitmap) data.getExtras().get("data");
            regenGamePicture(puzzlePicture);
        } else if ((requestCode == CHOOSE_PICTURE)
                && (resultCode == Activity.RESULT_OK)) {
            Cursor cursor = getContentResolver().query(data.getData(), null,
                                                       null, null, null);
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(ImageColumns.DATA);
            String fileSrc = cursor.getString(idx);
            puzzlePicture = BitmapFactory.decodeFile(fileSrc);
            cursor.close();
            regenGamePicture(puzzlePicture);
        }
    }

    /**
     * Inflates a menu when the button on the phone is pressed.
     *
     * @param menu the menu button hard coded on the phone
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        return true;
    }

    /**
     * Determines what executes when you click a menu item.
     *
     * @param item the selected menu object
     * @return Boolean which shows whether a menu option was selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean onReturn = true;
        switch (item.getItemId()) {
        // ARD: Style: don't indent cases
            case R.id.randomize:
                gameView.randomize();
                gameView.invalidate();
                break;
            case R.id.preferences:
                Intent pref = new Intent(this, Preferences.class);
                startActivity(pref);
                break;
            default:
                onReturn = super.onOptionsItemSelected(item);
        }
        return onReturn;
    }

    /**
     * Regenerates the game picture in the gameView given the picture.
     *
     * @param picture Picture given to set the gameView as.
     */
    public void regenGamePicture(Bitmap picture) {
        gameView.initPicture(picture);
        gameView.invalidate();
    }
}
