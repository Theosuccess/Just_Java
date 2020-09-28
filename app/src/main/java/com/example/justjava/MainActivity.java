package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //this method is called when the button is clicked
    public void submitOrder (View view) {
        //this receives user's name
        EditText nameField = (EditText) findViewById(R.id.name_edit_text);
        String name = nameField.getText().toString();
       //to add either milk or chocolate to coffee
        CheckBox milkCheckBox =  findViewById(R.id.milk_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        boolean hasMilk = milkCheckBox.isChecked();

        int price = calculatePrice(hasChocolate, hasMilk);
        String priceMessage = createOrderSummary(price, hasMilk, hasChocolate, name);

        //create an Intent to send order via mail
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + "successtboy@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) !=null){
            startActivity(intent);
        }

        displayMessage(priceMessage);



    }
    //calculate price of order
    private int calculatePrice(boolean addChocolate, boolean addMilk){
        int basePrice = 5;
        if (addChocolate){
            basePrice = basePrice + 1;
        }
        if (addMilk){
            basePrice = basePrice + 2;
        }
        int price = quantity * basePrice;
        return price;
    }
    private String createOrderSummary(int price, boolean addMilk, boolean addChocolate, String name){
        String priceMessage = "Name: "+ name ;
        priceMessage = priceMessage + "\n Add Milk? " + addMilk;
        priceMessage = priceMessage + "\n Add Chocolate? " + addChocolate;
        priceMessage = priceMessage + "\n Number of coffee: " + quantity + " at $5 per cup ";
        priceMessage = priceMessage + "\n Total = $" + price;
        priceMessage = priceMessage + "\n Thanks for your patronage!";
        return priceMessage;
    }
    //this method displays the quantity value on screen
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText(""  + numberOfCoffees);
    }
    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    private void displayMessage (String message){
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

//this method is called when the + button is clicked
    public void increment(View view) {
        if (quantity>=50){
            Toast.makeText(this, "Sorry you can not order more than 50 cups of coffees",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    //this method is called when the - button is clicked
    public void decrement(View view) {
        if (quantity<1){
            Toast.makeText(this, "You can't order less than 1 cup of coffee",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }
}