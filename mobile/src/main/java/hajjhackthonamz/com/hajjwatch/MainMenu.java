package hajjhackthonamz.com.hajjwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void goToMaps(View view) {
        startActivity(new Intent(getBaseContext(), MapsActivity.class));
    }

    public void goToTo(View view) {
        startActivity(new Intent(getBaseContext(), GoTo.class));
    }

    public void goToEmergency(View view) {
    }

    public void goToTrain(View view) {
        startActivity(new Intent(getBaseContext(), Train.class));

    }


    public void goBack(View view) { onBackPressed();
    }
}
