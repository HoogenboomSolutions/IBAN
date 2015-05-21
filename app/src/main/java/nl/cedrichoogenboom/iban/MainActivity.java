package nl.cedrichoogenboom.iban;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void onButtonClick(View view) {
        EditText reknrInput = (EditText) findViewById(R.id.editText);
        EditText response = (EditText) findViewById(R.id.editText2);

        String rekening = reknrInput.getText().toString();
        IBAN iban = new IBAN();
        String getIBAN = iban.getIBAN(rekening);

        response.setText(getIBAN);
    }
}
