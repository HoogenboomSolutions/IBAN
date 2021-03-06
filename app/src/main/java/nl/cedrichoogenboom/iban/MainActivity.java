package nl.cedrichoogenboom.iban;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends ActionBarActivity  {

    // Array ipv bool omdat Java geen Call By Reference heeft.
    // Zucht... Java...
    int[] manualBank = new int[1];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        manualBank[0] = 0;
        EditText etReknr = (EditText)findViewById(R.id.editText);
        EditText editText2 = (EditText)findViewById(R.id.editText2);
        etReknr.setOnClickListener(new OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           EditText reknrInput = (EditText) findViewById(R.id.editText);
                                           EditText bankInput = (EditText) findViewById(R.id.bankEditText);
                                           TextView bankText = (TextView) findViewById(R.id.textView3);

                                           if (manualBank[0] == 1) {
                                               bankInput.setVisibility(View.GONE);
                                               bankText.setVisibility(View.GONE);
                                               reknrInput.setEnabled(true);
                                               manualBank[0] = 0;
                                           }
                                       }
                                   }
        );

        // "editable=false is depricated, hieronder bankEditText niet meer editable maken
        editText2.setKeyListener(null);
        editText2.setTextIsSelectable(true);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

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

        //reknrInput.setEnabled(true);

        if (getIBAN.equals("Fout rekeningnummer") || getIBAN.equals("Foute Bankcode")) {
            toast = Toast.makeText(context, getIBAN, Toast.LENGTH_SHORT);
            toast.show();
        } else if (getIBAN.substring(4, 9).equals("Error") || getIBAN.equals("Bank niet gevonden")) {
            toast = Toast.makeText(context, "Bank niet gevonden, vul banknaam in", Toast.LENGTH_LONG);
            toast.show();
            bankInput.setVisibility(View.VISIBLE);
            bankText.setVisibility(View.VISIBLE);
            //reknrInput.setEnabled(false);
            manualBank[0] = 1;
        } else {
            response.setText(getIBAN);
            bankInput.setVisibility(View.GONE);
            bankText.setVisibility(View.GONE);
        }
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//    // Handle action bar item clicks here. The action bar will
//    // automatically handle clicks on the Home/Up button, so long
//    // as you specify a parent activity in AndroidManifest.xml.
//    int id = item.getItemId();
//
//    //noinspection SimplifiableIfStatement
//    if (id == R.id.action_settings) {
//        return true;
//    }
//
//    return super.onOptionsItemSelected(item);
//    }
}