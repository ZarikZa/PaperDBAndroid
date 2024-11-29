package com.example.paperdb;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.paperdb.Paper;

public class OpenPageActivity  extends AppCompatActivity {
    EditText opis;
    EditText name;
    EditText img;
    Button vihod;
    Button update;
    Button delete;
    String imageName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.openpage);
        opis = findViewById(R.id.opisOpen);
        name = findViewById(R.id.textNazv);
        vihod = findViewById(R.id.vixodBTM);
        update = findViewById(R.id.updateButton);
        delete = findViewById(R.id.deleteButton);
        img = findViewById(R.id.imgOpen);
        Intent intent = getIntent();
        String selectedBookTitle = intent.getStringExtra("book");
        Book book = Paper.book().read(selectedBookTitle, null);
        opis.setText(book.getPrice());
        name.setText(book.getName());
        img.setText(book.getImg());
        ImageView imageView = findViewById(R.id.flagOpen);
        imageName = book.getImg();
        if (imageName != null && !imageName.isEmpty()) {
            try {
                int imageResource = getResources().getIdentifier(imageName, "drawable", getPackageName());
                if (imageResource != 0) {
                    imageView.setImageResource(imageResource);
                } else {
                    Toast.makeText(this, "Изображение не найдено: " + imageName, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Ошибка загрузки изображения: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Имя изображения пустое или null!", Toast.LENGTH_SHORT).show();
        }
        vihod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OpenPageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(view -> {
            if(selectedBookTitle == null){
                Toast.makeText(OpenPageActivity.this, "Не выбрана", Toast.LENGTH_SHORT).show();
                return;
            }
            String title = name.getText().toString();
            String autor = opis.getText().toString();
            String iimage = img.getText().toString();
            if(!title.isEmpty() && !autor.isEmpty()){
                Book bookUp = new Book(null ,title, autor, iimage);
                Paper.book().write(selectedBookTitle, bookUp);
            }
        });

        delete.setOnClickListener(view -> {
            if(selectedBookTitle == null){
                Toast.makeText(OpenPageActivity.this, "Не выбрана", Toast.LENGTH_SHORT).show();
                return;
            }
            Paper.book().delete(selectedBookTitle);
            Intent intent1 = new Intent(OpenPageActivity.this, MainActivity.class);
            startActivity(intent1);
        });
    }
}
