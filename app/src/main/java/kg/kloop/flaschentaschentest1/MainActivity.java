package kg.kloop.flaschentaschentest1;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends Activity {

    public final static String SERVER_ADDRESS = "10.20.0.40";
    public final static int PORT_NUMBER = 1337;
    public final static int WIDTH = 45;
    public final static int HEIGHT = 35;


    private SeekBar seekRed;
    private SeekBar seekGreen;
    private SeekBar seekBlue;

    private NumberPicker pickerWidth;
    private NumberPicker pickerHeight;
    private NumberPicker pickerOffsetX;
    private NumberPicker pickerOffsetY;

    private EditText editOffsetZ;

    private TextView textRed;
    private TextView textGreen;
    private TextView textBlue;

    private PicSender picSender = new PicSender(SERVER_ADDRESS, PORT_NUMBER);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        seekRed = (SeekBar)findViewById(R.id.seekRed);
        seekGreen = (SeekBar)findViewById(R.id.seekGreen);
        seekBlue = (SeekBar)findViewById(R.id.seekBlue);

        pickerWidth = (NumberPicker)findViewById(R.id.pickerWidth);
        pickerHeight = (NumberPicker)findViewById(R.id.pickerHeight);
        pickerOffsetX = (NumberPicker)findViewById(R.id.pickerOffsetX);
        pickerOffsetY = (NumberPicker)findViewById(R.id.pickerOffsetY);

        editOffsetZ = (EditText)findViewById(R.id.editOffsetZ);

        textRed = (TextView)findViewById(R.id.textRed);
        textGreen = (TextView)findViewById(R.id.textGreen);
        textBlue = (TextView)findViewById(R.id.textBlue);

        picSender = new PicSender(SERVER_ADDRESS, PORT_NUMBER);



        pickerWidth.setMaxValue(WIDTH);
        pickerHeight.setMaxValue(HEIGHT);
        pickerOffsetX.setMaxValue(WIDTH);
        pickerOffsetY.setMaxValue(HEIGHT);

        seekRed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textRed.setText(Integer.toString(progress));

                update();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekGreen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textGreen.setText(Integer.toString(progress));

                update();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBlue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textBlue.setText(Integer.toString(progress));

                update();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        final Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                update();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void update() {
        picSender.superFill(seekRed.getProgress(),
                seekGreen.getProgress(),
                seekBlue.getProgress(),
                pickerWidth.getValue(),
                pickerHeight.getValue(),
                pickerOffsetX.getValue(),
                pickerOffsetY.getValue(),
                Integer.parseInt(editOffsetZ.getText().toString()));
    }

}
