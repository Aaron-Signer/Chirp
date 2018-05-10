package com.bignerdranch.android.clientsidechrip;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class CreateImageChirp extends AppCompatActivity {

    private Button add_image;
    private int image;
    private byte [] imageBit;
    private Bitmap iBit;
    private ImageView i;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_image_chirp);

        add_image = (Button) findViewById(R.id.add_image);

        add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),image);
            }
        });

        i = (ImageView)findViewById(R.id.imageView);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == image)
        {
            if (resultCode == Activity.RESULT_OK)
            {
                if (data != null)
                {
                    try
                    {
                        iBit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                        ByteArrayOutputStream streamByte = new ByteArrayOutputStream();
                        int downSize = Math.min(iBit.getHeight(), iBit.getWidth());
                        if (downSize > 256)
                        {
                            downSize = 100;
                        }
                        iBit.compress(Bitmap.CompressFormat.PNG, downSize, streamByte);
                        byte[] byteArray = streamByte.toByteArray();
                        i.setImageBitmap(iBit);
                        this.imageBit = byteArray;
                    }
                    catch (IOException e)
                    {
                        Toast.makeText(this, "Error resizing photo", Toast.LENGTH_SHORT).show();
                    }

                }
            }
            else if (resultCode == Activity.RESULT_CANCELED)
            {
                Toast.makeText(this, "Activity was canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Database.get().logOut();
        try
        {
            Log.d("getfilesdir",getFilesDir().toString());
            Database.get().save(new File(getFilesDir(), "info"));
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
        finish();
        return true;
    }

}
