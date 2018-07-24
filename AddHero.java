package com.example.acer.summer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import static android.provider.CalendarContract.CalendarCache.URI;
import static android.widget.ImageView.*;

public class AddHero extends AppCompatActivity {
    Button add,save;
    EditText Name, Type, Descript;
    ImageView Image;
    Bitmap bitmap;

    private static final int PICK_IMAGE=100;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addhero);
        add = (Button) findViewById(R.id.button);
        Name = (EditText) findViewById(R.id.name);
        Type = (EditText) findViewById(R.id.type);
        Descript = (EditText) findViewById(R.id.description);
        Image = (ImageView) findViewById(R.id.imageView2);


        save = (Button) findViewById(R.id.save);
        bitmap=Image.getDrawingCache();

databaseReference=FirebaseDatabase.getInstance().getReference("artist");
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openGallery();
            }
        });

        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
addHero();

            }
        });
    }
        private void addHero()
        {
            String name =Name.getText().toString().trim();
            String type=Type.getText().toString().trim();
            String description=Descript.getText().toString().trim();
                if(((!TextUtils.isEmpty(name))&&(!TextUtils.isEmpty(type))&&(!TextUtils.isEmpty(description))))
                {

                    String id=databaseReference.push().getKey();
                    Hero hero=new Hero(id,name,type,description);
                    databaseReference.child(id).setValue(hero);
                    Toast.makeText(this, "Hero added", Toast.LENGTH_SHORT).show();

                }
                else
                {
                    Toast.makeText(this, "Add values ", Toast.LENGTH_SHORT).show();

                }

        }




    private  void openGallery()
    {
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,PICK_IMAGE);

    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode==RESULT_OK &&requestCode==PICK_IMAGE)
        {
            Uri imageUri = data.getData();
            Image.setImageURI(imageUri);
        }
    }


    }





