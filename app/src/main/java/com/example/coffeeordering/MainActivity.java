package com.example.coffeeordering;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;


    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 cup of coffees ! ", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        int price = calculatePrice(Check_whipped(), Check_choclate());
        TextView pricetextview = findViewById(R.id.price_text_view);
        pricetextview.setText("$ " + price + "");
    }


    public void decrement(View view) {
        if (quantity <= 1) {
            Toast.makeText(this, "Sorry, You cannot have less than 1 cup of coffees ! ", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

        int price = calculatePrice(Check_whipped(), Check_choclate());
        TextView pricetextview = findViewById(R.id.price_text_view);
        pricetextview.setText("$ " + price + "");
    }

    public void onCheckboxClicked(View view) {


        int price = calculatePrice(Check_whipped(), Check_choclate());
        TextView pricetextview = findViewById(R.id.price_text_view);
        pricetextview.setText("$ " + price + "");
//NumberFormat.getCurrencyInstance().format(price)
    }

    public boolean Check_whipped() {

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        return hasWhippedCream;
    }

    public boolean Check_choclate() {
        CheckBox chocolateCheckBox = findViewById(R.id.choclate_id);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        return hasChocolate;
    }


    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_text_id);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = findViewById(R.id.choclate_id);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);


        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);


        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Coffee Order for : " + name.toUpperCase());
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // First calculate the price of one cup of coffee
        int basePrice = 5;

        // If the user wants whipped cream, add $1 per cup
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // If the user wants chocolate, add $2 per cup
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by the quantity
        return quantity * basePrice;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = "" + getString(R.string.Order_Summary_Name, name);
        priceMessage += "\n whipped cream : " + addWhippedCream;
        priceMessage += "\n chocolate     : " + addChocolate;
        priceMessage += "\n quantity      : " + quantity;
        priceMessage += "\n total price   : " + price;
        priceMessage += "\n" + getString(R.string.thankyou);
        return priceMessage;
    }


    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

//    private int quantity = 0;
//    private String name = "";
//    private boolean hasWhipped=false;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//    }
//
//    public void SubmitOrder(View view) {
//        //  TextView textView = (TextView) findViewById(R.id.quantity_text_view);
//        CalculatePrice(quantity);
//    }
//
//    public int CalculatePrice(int number) {
//        TextView textView = (TextView) findViewById(R.id.price_text_view);
//        CheckBox checkBox = (CheckBox) findViewById(R.id.whipped_cream_id);
//        if(checkBox.isSelected()){
//            hasWhipped=true;
//        }else{
//            hasWhipped=false;
//        }
//        textView.setText("$" + number * 5);
//        return number * 5;
//    }
//
//    public void IncreaseOrderQuantity(View view) {
//        quantity++;
//        TextView textView = (TextView) findViewById(R.id.quantity_text_view);
//        textView.setText("" + quantity);
//    }
//
//    public void DecreaseOrderQuantity(View view) {
//        if (quantity > 0) {
//            quantity--;
//        }
//        TextView textView = findViewById(R.id.quantity_text_view);
//        textView.setText("" + quantity);
//    }
//
//    private String OrderSummary() {
//        TextView textView =  findViewById(R.id.name_text_id);
//
//        return "";
//    }
}
