package nl.cedrichoogenboom.iban;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    // Array ipv bool omdat Java geen Call By Reference heeft.
    // Zucht... Java...
    int[] manualBank = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manualBank[0] = 0;
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
        EditText bankInput = (EditText) findViewById(R.id.bankEditText);
        TextView bankText = (TextView) findViewById(R.id.textView3);
        String getIBAN;

        Context context = getApplicationContext();
        Toast toast;

        String rekening = reknrInput.getText().toString();
        if (rekening.length() < 3){
            toast = Toast.makeText(context, "Fout rekeningnummer", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        IBAN iban = new IBAN();
        if (manualBank[0] != 1 || bankInput.getText().toString().equals("") )
            getIBAN = iban.getIBAN(rekening);
        else {
            String bank = bankInput.getText().toString().toUpperCase();
            if (bank.length() != 4) {
                toast = Toast.makeText(context, "Bankcode moet 4 letters zijn", Toast.LENGTH_LONG);
                toast.show();
                return;
            }

            getIBAN = iban.getIBANWithBank(rekening, bank, manualBank);
        }

        reknrInput.setEnabled(true);

        // FIXME: RekNr = "693553309" gaat fout, ANR
        if (getIBAN.equals("Fout rekeningnummer")) {
            toast = Toast.makeText(context, "Fout rekeningnummer", Toast.LENGTH_SHORT);
            toast.show();
        } else if (getIBAN.substring(4, 9).equals("Error") || getIBAN.equals("Bank niet gevonden")) {
            toast = Toast.makeText(context, "Bank niet gevonden, vul banknaam in", Toast.LENGTH_LONG);
            toast.show();

            bankInput.setVisibility(View.VISIBLE);
            bankText.setVisibility(View.VISIBLE);
            reknrInput.setEnabled(false);
            manualBank[0] = 1;
        } else {
            response.setText(getIBAN);
            bankInput.setVisibility(View.GONE);
            bankText.setVisibility(View.GONE);
        }
    }
}
