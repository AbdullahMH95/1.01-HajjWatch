package hajjhackthonamz.com.hajjwatch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class AddFriend extends AppCompatActivity {

    private EditText nameedit;
    private ImageView plus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
            plus = findViewById(R.id.plus);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nameedit =  findViewById(R.id.Name);
                String name1 = nameedit.getText().toString();
              Singlton.getInstance().name.add(name1);
                startActivity(new Intent(getBaseContext(), MapsActivity.class));

            }
        });

    }

    public void goBack(View view) { onBackPressed();
    }
}
