package hajjhackthonamz.com.hajjwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Train extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);
    }

    public void goBack(View view) {
        onBackPressed();
    }
}
