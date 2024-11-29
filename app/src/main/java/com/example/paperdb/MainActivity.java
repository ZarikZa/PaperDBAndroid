package com.example.paperdb;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private EditText titleText, priceText, UrlText;
    private Button addButton;
    private ListView listView;
    private ArrayAdapter<String> adapter;
    private String selectedBookTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Paper.init(this);

        addButton = findViewById(R.id.addButton);
        titleText = findViewById(R.id.name);
        priceText = findViewById(R.id.price);
        UrlText = findViewById(R.id.imgUrl);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getBookTitles());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedBookTitle = adapter.getItem(i);
                Book book = Paper.book().read(selectedBookTitle, null);
                Intent intent = new Intent(MainActivity.this, OpenPageActivity.class);
                intent.putExtra("book", selectedBookTitle);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(view -> {
            String title = titleText.getText().toString();
            String autor = priceText.getText().toString();
            String img = UrlText.getText().toString();
            if(!title.isEmpty() && !autor.isEmpty()){
                Book book = new Book(null ,title, autor, img);
                Paper.book().write(title, book);
                clearInputs();
                updateBookList();
            }
        });
    }

    private void clearInputs() {
        titleText.setText("");
        priceText.setText("");
        selectedBookTitle = null;
        UrlText.setText("");
    }

    private void updateBookList() {
        adapter.clear();
        adapter.addAll(getBookTitles());
        adapter.notifyDataSetChanged();

    }

    private List<String> getBookTitles() {
        return new ArrayList<>(Paper.book().getAllKeys());
    }
}