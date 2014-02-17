package edu.wcu.cs.cs467.phunslide;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * View representing the splash screen.
 *
 * @author Andriy Rudyk
 * @author Time Sizemore
 * @version 24.02.2012
 */
public class SplashView extends View {
    /** Splashscreen tag. */
    public static final String TAG = "SplashView";

    /** Bitmap representing the splash screen. */
    private Bitmap splashScreen;

    /**
     * Constructor for splash view.
     *
     * @param context Given context.
     */
    public SplashView(Context context) {
        super(context);
        init();
    }

    /**
     * Constructor for splash screen.
     *
     * @param context Given context.
     * @param attrs Given attributes.
     */
    public SplashView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Constructor for splash screen.
     *
     * @param context Given context.
     * @param attrs Given attributes.
     * @param defStyle Given definition styles.
     */
    public SplashView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * Initializes the picture to the splash screen.
     */
    private void init() {
        splashScreen = BitmapFactory.decodeResource(getResources(),
                R.drawable.splash);
    }

    /**
     * Draws the picture on the canvas.
     *
     * @param canvas Given canvas.
     */
    public void onDraw(Canvas canvas) {
        splashScreen = Bitmap.createScaledBitmap(splashScreen,
                canvas.getWidth(), canvas.getHeight(), true);
        canvas.drawBitmap(splashScreen, 0, 0, null);
    }
}
