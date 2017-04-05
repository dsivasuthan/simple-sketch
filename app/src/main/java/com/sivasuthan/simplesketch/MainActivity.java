package com.sivasuthan.simplesketch;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends Activity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);
        Toast.makeText(getApplicationContext(), "Application created by\nSivasuthan Dhayalan\nhttp://sivasuthan.com" , Toast.LENGTH_SHORT).show();
        Button button2 = (Button)findViewById(R.id.btnSave);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                File folder = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/SimpleSketch/");
                boolean success = false;
                if (!folder.exists())
                {
                    success = folder.mkdirs();
                }

                //System.out.println(success+"folder");
                System.out.println(Environment.getExternalStorageDirectory().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_Hmmss");
                String currentDateandTime = sdf.format(new Date());

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/Pictures/SimpleSketch/" + currentDateandTime + ".png");

                if ( !file.exists() )
                {
                    try {
                        success = file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println(success+"file");



                FileOutputStream ostream = null;
                try
                {
                    ostream = new FileOutputStream(file);

                    System.out.println(ostream);
                    View targetView = customCanvas;

                    // myDrawView.setDrawingCacheEnabled(true);
                    //   Bitmap save = Bitmap.createBitmap(myDrawView.getDrawingCache());
                    //   myDrawView.setDrawingCacheEnabled(false);
                    // copy this bitmap otherwise distroying the cache will destroy
                    // the bitmap for the referencing drawable and you'll not
                    // get the captured view
                    //   Bitmap save = b1.copy(Bitmap.Config.ARGB_8888, false);
                    //BitmapDrawable d = new BitmapDrawable(b);
                    //canvasView.setBackgroundDrawable(d);
                    //   myDrawView.destroyDrawingCache();
                    // Bitmap save = myDrawView.getBitmapFromMemCache("0");
                    // myDrawView.setDrawingCacheEnabled(true);
                    //Bitmap save = myDrawView.getDrawingCache(false);
                    Bitmap well = customCanvas.getBitmap();
                    Bitmap save = Bitmap.createBitmap(320, 480, Config.ARGB_8888);
                    Paint paint = new Paint();
                    paint.setColor(Color.WHITE);
                    Canvas now = new Canvas(save);
                    now.drawRect(new Rect(0,0,320,480), paint);
                    now.drawBitmap(well, new Rect(0,0,well.getWidth(),well.getHeight()), new Rect(0,0,320,480), null);

                    // Canvas now = new Canvas(save);
                    //myDrawView.layout(0, 0, 100, 100);
                    //myDrawView.draw(now);
                    if(save == null) {
                        System.out.println("NULL bitmap save\n");
                    }
                    save.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    Toast.makeText(getApplicationContext(), "File Saved. \n" + Environment.getExternalStorageDirectory().toString() +
                            "/Pictures/SimpleSketch/" + currentDateandTime + ".png", Toast.LENGTH_SHORT).show();
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    ostream.flush();
                    ostream.close();
                }catch (NullPointerException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Null error", Toast.LENGTH_SHORT).show();
                }

                catch (FileNotFoundException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "File error", Toast.LENGTH_SHORT).show();
                }

                catch (IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "IO error", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    
    public void clearCanvas(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to erase the canvas?\nUnsaved data will be lost!")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        customCanvas.clearCanvas();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();
    }

}