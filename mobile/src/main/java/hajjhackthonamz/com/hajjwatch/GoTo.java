package hajjhackthonamz.com.hajjwatch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GoTo extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_to);
        intent = new Intent(this, GoToByMap.class);
    }

    public void goToCampaign(View view) {
        intent.putExtra("where","21.416526,39.892913");
        startActivity(intent);
    }

    public void goToMena(View view) {
        intent.putExtra("where","21.420359,39.874189");
        startActivity(intent);
    }

    public void goToTrainStation(View view) {
    }

    public void goToKabba(View view) {
        intent.putExtra("where","21.422951,39.826019");
        startActivity(intent);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
