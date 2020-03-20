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

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
        int price = calculatePrice(Check_whipped(), Check_choclate());
        TextView pricetextview = findViewById(R.id.price_text_view);
        pricetextview.setText("$ " + price + "");
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 0) {
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

    }

    public boolean Check_whipped() {
        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        return hasWhippedCream;
    }

    public boolean Check_choclate() {
        // Figure out if the user wants choclate topping
        CheckBox chocolateCheckBox = findViewById(R.id.choclate_id);
        boolean hasChocolate = chocolateCheckBox.isChecked();
        return hasChocolate;
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Get user's name
        EditText nameField = (EditText) findViewById(R.id.name_text_id);
        Editable nameEditable = nameField.getText();
        String name = nameEditable.toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_id);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants choclate topping
        CheckBox chocolateCheckBox = findViewById(R.id.choclate_id);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Use an intent to launch an email app.
        // Send the order summary in the email body.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Coffee Order for : " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @param addWhippedCream is whether or not we should include whipped cream topping in the price
     * @param addChocolate    is whether or not we should include chocolate topping in the price
     * @return total price
     */
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

    /**
     * Create summary of the order.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream,
                                      boolean addChocolate) {
        String priceMessage = "";
        priceMessage += "\n whipped cream : " + addWhippedCream;
        priceMessage += "\n chocolate     : " + addChocolate;
        priceMessage += "\n quantity      : " + quantity;
        priceMessage += "\n total price   : " + price;
        priceMessage += "\n" + "Thank You for delivering the order in time.";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
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
