package com.example.externals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button saveBtn, loadBtn;
    EditText editTextInput;
    TextView textViewLoad;

    String filename = "";
    String filepath = "";
    String filecontent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadBtn = findViewById(R.id.btnLoad);
        saveBtn = findViewById(R.id.btnSave);
        editTextInput = findViewById(R.id.editInput);
        textViewLoad = findViewById(R.id.textViewLoad);
        filename = "shaggy.txt";
        filepath = "ShaggyDir";
        if (!isExternalStorageAvailable()) {
            saveBtn.setEnabled(false);

        }

        //savebutton Listner
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textViewLoad.setText("");
                filecontent = editTextInput.getText().toString().trim();
                if (!filecontent.equals("")) {
                    File myexternalfile = new File(getExternalFilesDir(filepath), filename);
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = new FileOutputStream(myexternalfile);
                        fileOutputStream.write(filecontent.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    editTextInput.setText("");
                    Toast.makeText(MainActivity.this, "Saved to SD Card", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Cannot contain empty list", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //load button listner
        loadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FileReader fileReader = null;
                File myFie = new File(getExternalFilesDir(filepath), filename);
                StringBuilder stringBuilder = new StringBuilder();
                try {
                    fileReader = new FileReader(myFie);
                    BufferedReader br = new BufferedReader(fileReader);
                    String line = br.readLine();
                    while (line != null) {
                        stringBuilder.append(line).append('\n');
                        line = br.readLine();
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    String filecontents = "File contents" + "\n" + stringBuilder.toString();
                    textViewLoad.setText(filecontents);
                }
            }
        });

    }

    private boolean isExternalStorageAvailable() {
        String extStorage = Environment.getExternalStorageState();
        if (extStorage.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }
}