package edu.wcu.cs.cs467.phunslide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Splash screen Activity with basic preference selection.
 *
 * @author Andriy Rudyk
 * @author Tim Sizemore
 * @version 24.02.2012
 */
public class PhunSlideActivity extends Activity {

    /** Default size for puzzle. */
    private static final int DEFAULT_GRID_SIZE = 3;

    /** Large grid size. */
    private static final int LARGE_GRID_SIZE = 5;

    /** Medium grid size. */
    private static final int MEDIUM_GRID_SIZE = 4;

    /** Large grid string size. */
    private static final String LARGE_GRID_STRING_SIZE = "5x5";

    /** Medium grid string size. */
    private static final String MEDIUM_GRID_STRING_SIZE = "4x4";

    /** Integer size for puzzle. */
    private int gridSize;

    /**
     * Called when the activity is first created.
     *
     * @param savedInstanceState Bundle passed to this Activity.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        gridSize = DEFAULT_GRID_SIZE;

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.size_array,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new MyOnSizeSelectedListener());
        // It does not appear twice in the file!!!
        Toast.makeText(getBaseContext(), "Touch to begin",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Starts the game activity.
     *
     * @param splash View from which the screen was pressed.
     */
    public void startGame(View splash) {
        Intent gameIntent = new Intent(this, GameActivity.class);
        gameIntent.putExtra("gridSize", gridSize);
        startActivity(gameIntent);
    }

    /**
     * Listener for Spinner.
     *
     */
    private class MyOnSizeSelectedListener implements OnItemSelectedListener {
        /**
         * When item is selected this will get called.
         *
         * @param parent Adapter parent.
         * @param view View of the Spinner.
         * @param pos Position of the Spinner.
         * @param id The is of the Spinner.
         */
        public void onItemSelected(AdapterView<?> parent,
                View view, int pos, long id) {
            String strSize = parent.getItemAtPosition(pos).toString();
            if (strSize.equals(LARGE_GRID_STRING_SIZE)) {
                gridSize = LARGE_GRID_SIZE;
            } else if (strSize.equals(MEDIUM_GRID_STRING_SIZE)) {
                gridSize = MEDIUM_GRID_SIZE;
            } else {
                gridSize = DEFAULT_GRID_SIZE;
            }
        }

        /**
         * Called when nothing is called.
         *
         * @param arg0 Arguments.
         */
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }
}
