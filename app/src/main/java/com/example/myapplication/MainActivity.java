package com.example.myapplication;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Button;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<CodeItem> codeList;
    private Button p_button;
    private AutoCompleteTextView editAutoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillCodeList();

        editAutoText = findViewById(R.id.actv);
        AutoCompleteCodeAdapter adapter = new AutoCompleteCodeAdapter(this, codeList);
        editAutoText.setAdapter(adapter);

        editAutoText.addTextChangedListener(loginTextWatcher);

        getSupportActionBar().setTitle("Module Code");
        //can add this after linking tgt with login page
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        p_button = findViewById(R.id.proceed_to_tors);


        p_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTorS();
            }
        });
    }

    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String codeInput = editAutoText.getText().toString().trim();

            p_button.setEnabled(!codeInput.isEmpty() && (codeInput.contentEquals("CS1010") || codeInput.contentEquals("CS1231") || codeInput.contentEquals("CG1111") ||
                    codeInput.contentEquals("MA1511") || codeInput.contentEquals("MA1512")));

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void openTorS() {
        Intent intent = new Intent(this, TorS.class);
        startActivity(intent);
    }


    private void fillCodeList() {
        codeList = new ArrayList<>();
        codeList.add(new CodeItem("CS1010"));
        codeList.add(new CodeItem("CS1231"));
        codeList.add(new CodeItem("MA1511"));
        codeList.add(new CodeItem("MA1512"));
        codeList.add(new CodeItem("CG1111"));
    }

}
