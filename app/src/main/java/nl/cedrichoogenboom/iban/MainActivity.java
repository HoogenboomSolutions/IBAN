package nl.cedrichoogenboom.iban;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


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
        Context context = getApplicationContext();
        Toast toast;

        String rekening = reknrInput.getText().toString();
        IBAN iban = new IBAN();
        String getIBAN = iban.getIBAN(rekening);

        // RekNr = "693553309" gaat fout, ANR
        if (getIBAN == "Bank niet gevonden") {
            toast = Toast.makeText(context, "Bankt niet gevonden, controleer rekeningnummer", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (getIBAN == "Fout rekeningnummer") {
            toast = Toast.makeText(context, "Fout rekeningnummer", Toast.LENGTH_SHORT);
            toast.show();
        }
        else if (getIBAN.substring(4, 9) == "Error") {
            toast = Toast.makeText(context, "Bankt niet gevonden, controleer rekeningnummer", Toast.LENGTH_SHORT);
            toast.show();
        }
        else
            response.setText(getIBAN);
    }
}
