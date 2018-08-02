package hajjhackthonamz.com.hajjwatch;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;
import com.google.cloud.translate.Translation;

import java.util.ArrayList;
import java.util.Locale;


public class TranslatePage extends AppCompatActivity {

    private static final String API_KEY = "AIzaSyCwtP_Rjbz1OC7qAJUwmnXo-P3IvZxELx4";


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translate);
        final TextView textView = (TextView) findViewById(R.id.tex);
        final Handler textViewHandler = new Handler();
        Bundle bundle = getIntent().getExtras();
        final String word = bundle.getString("word");

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                TranslateOptions options = TranslateOptions.newBuilder()
                        .setApiKey(API_KEY)
                        .build();
                com.google.cloud.translate.Translate translate =  options.getService();
                final Translation translation =
                        translate.translate(word,
                                Translate.TranslateOption.targetLanguage("ar"));
                textViewHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (textView != null) {
                            textView.setText(translation.getTranslatedText());
                        }
                    }
                });
                return null;
            }
        }.execute();
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String tx = result.get(0);
                    Intent intent = new Intent(getBaseContext(), TranslatePage.class);
                    intent.putExtra("word",tx);
                    startActivity(intent);

                }
                break;
        }
    }

    public void goBack(View view) {
        startActivity(new Intent(getBaseContext(), MainActivity.class));
    }
}