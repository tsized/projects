package edu.wcu.cs.cs467.phunslide;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Class representing all the preferences in our game.
 *
 * @author Andriy Rudyk
 * @author Tim Sizemore
 * @version 27.02.2012
 */
public class Preferences extends PreferenceActivity {
    /**
     * Gets called when preference is created.
     *
     * @param savedInstanceState Saved Bundle being given to this ACtivity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
