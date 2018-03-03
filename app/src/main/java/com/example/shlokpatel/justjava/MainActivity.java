package com.example.shlokpatel.justjava;

/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(), "Hello Caffeine addicts ☕️", Toast.LENGTH_LONG).show();
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increement(View view) {
        quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        if (quantity > 1)
            quantity--;

        display(quantity);
    }

    public void submitOrder(View view) {
        CheckBox wccheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean haswc = wccheckBox.isChecked();
        CheckBox chcheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasch = chcheckBox.isChecked();
        EditText name = (EditText) findViewById(R.id.name_desc);
        String gname = name.getText().toString();

        int price = calculatePrice(haswc, hasch);
        String pricemessage = createOrderSummary(price, haswc, hasch, gname);
        if (gname.isEmpty()) {
            Toast.makeText(getApplicationContext(),"Enter username",Toast.LENGTH_SHORT).show();
        }
        else if(quantity==0){
            Toast.makeText(getApplicationContext(),"Order can't be empty",Toast.LENGTH_SHORT).show();
        }
        else {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:")); // only email apps should handle this
            intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + gname);
            intent.putExtra(Intent.EXTRA_TEXT, pricemessage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    private String createOrderSummary(int p, boolean h, boolean c, String a) {
        String pricemessage = "Name : " + a;
        pricemessage += "\nQuantity : " + quantity;
        pricemessage += "\nWhipped Cream : " + h;
        pricemessage += "\nChocolate : " + c;
        pricemessage += "\nTotal : ₹" + p;
        pricemessage += "\nThank You \uD83D\uDE01";
        return pricemessage;
    }

    private int calculatePrice(boolean a, boolean b) {
        int p = 20;
        if (a)
            p += 2;
        if (b)
            p += 3;
        return quantity * p;
    }
}