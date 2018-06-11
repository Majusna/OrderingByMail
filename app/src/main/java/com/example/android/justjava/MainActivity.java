package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.duration;
import static android.R.attr.id;
import static android.R.attr.value;
import static android.R.string.yes;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.example.android.justjava.R.id.name_field;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Calculates the price of the order.
     */
    private int calculatePrice( boolean addchocolate,boolean addWhippedCream ) {
        int basePrice = 5;
        if(addWhippedCream){
            basePrice += 1;
        }
        if(addchocolate){
            basePrice += 2;
        }
        return basePrice* quantity;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity<1) {
            quantity = 1;
            Toast.makeText(this,"You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if(quantity>100) {
            quantity = 100;
            Toast.makeText(this,"You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /*************************************************************************
     */

    /**
     * This method displays the given text on the screen.
     */


    /**
     * making message that can be displyed
     * @param price of the order
     *@param whippedcream is wheder or not the user wants whipped cream topping
     */

    private String createOrderSummary(int price, boolean whippedcream, boolean chocolateCream, String value) {
        String priceMessage = getString(R.string.name) + value;
        priceMessage += "\n"+ getString(R.string.add_cream) + whippedcream;
        priceMessage += "\n"+getString(R.string.add_chocolate) + chocolateCream;
        priceMessage += "\n" +getString(R.string.quantity)+quantity ;
        priceMessage += "\n" + getString(R.string.total)+ price;
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText name = (EditText) findViewById(R.id.name_field);
        String value = name.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice( haschocolate, hasWhippedCream);
        String priceMessage = createOrderSummary(price, hasWhippedCream,haschocolate, value);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.costumer_name) + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }



}


