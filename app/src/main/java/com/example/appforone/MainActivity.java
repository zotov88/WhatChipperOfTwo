package com.example.appforone;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private TextView volume_1;
    private TextView volume_2;
    private TextView price_1;
    private TextView price_2;
    private TextView output;
    private Button bt_calc;
    private Button bt_clear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        volume_1 = findViewById(R.id.tv_volume_1);
        volume_2 = findViewById(R.id.tv_volume_2);
        price_1 = findViewById(R.id.tv_price_1);
        price_2 = findViewById(R.id.tv_price_2);
        output = findViewById(R.id.tv_output);
        bt_calc = findViewById(R.id.bt_calc);
        bt_clear = findViewById(R.id.bt_clear);
        radioGroup = findViewById(R.id.radioGroup);
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());

        bt_calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Product[] products = initProducts();
                if (products == null) {
                    Toast.makeText(getApplicationContext(), "Некорректные данные", Toast.LENGTH_SHORT).show();
                } else {
                    output.setVisibility(View.VISIBLE);
                    calculate(products);
                }
            }
        });

        bt_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                output.setVisibility(View.INVISIBLE);
                output.setText("");
                volume_1.setText("");
                volume_2.setText("");
                price_1.setText("");
                price_2.setText("");
                Toast.makeText(getApplicationContext(), "Очищено", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void checkButton(View v) {
        radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
        Toast.makeText(this, radioButton.getText(), Toast.LENGTH_SHORT).show();
    }

    private void calculate(Product[] products) {
        String str = radioButton.getText().equals("грамм") ? "килограмм" : "литр";
        StringBuilder stringBuilder = new StringBuilder("За 1 " + str + ":\n\n");
        output.setText(stringBuilder.toString());
        String one = "\t№" + (1) + " - " + products[0].priceFor1Kg() + " руб" + "\n";
        String two = "\t№" + (2) + " - " + products[1].priceFor1Kg() + " руб" + "\n\n";
        coloredStrings(products, one, two);
        stringBuilder.setLength(0);
        stringBuilder.append(Product.whatBigger(products[0], products[1]));
        stringBuilder.append(stringBuilder.toString().endsWith(" ") ? (radioButton.getText().equals("грамм") ? "килограмм" : "литр") : "");
        output.append(stringBuilder);
    }

    private void coloredStrings(Product[] products, String one, String two) {
        Spannable wordOne = new SpannableString(one);
        Spannable wordTwo = new SpannableString(two);
        double dif = Product.different(products[0], products[1]);
        int green = Color.argb(255, 115, 230, 0);
        wordOne.setSpan(new ForegroundColorSpan(dif > 0 ? Color.RED : green), 0, wordOne.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        wordTwo.setSpan(new ForegroundColorSpan(dif > 0 ? green : dif < 0 ? Color.RED : green),
                0, wordTwo.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        output.append(wordOne);
        output.append(wordTwo);
    }

    private Product[] initProducts() {
        Product[] products = new Product[2];
        try {
            double p_1 = Double.parseDouble(price_1.getText().toString());
            double p_2 = Double.parseDouble(price_2.getText().toString());
            double v_1 = Double.parseDouble(volume_1.getText().toString());
            double v_2 = Double.parseDouble(volume_2.getText().toString());
            if (p_1 > 0 && p_2 > 0 && v_1 > 0 && v_2 > 0 ) {
                products[0] = new Product(p_1, v_1);
                products[1] = new Product(p_2, v_2);
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
        }
        return products[1] == null ? null : products;
    }
}