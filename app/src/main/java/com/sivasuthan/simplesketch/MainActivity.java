package com.sivasuthan.simplesketch;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.Button;
import android.widget.Toast;
public class MainActivity extends Activity {

    private CanvasView customCanvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customCanvas = (CanvasView) findViewById(R.id.signature_canvas);

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {


                File folder = new File(Environment.getExternalStorageDirectory().toString());
                boolean success = false;
                if (!folder.exists())
                {
                    success = folder.mkdirs();
                }

                System.out.println(success+"folder");

                File file = new File(Environment.getExternalStorageDirectory().toString() + "/sample.png");

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
                    //bitmap.compress(Bitmap.CompressFormat.PNG, 100, ostream);
                    //ostream.flush();
                    //ostream.close();
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
        customCanvas.clearCanvas();
    }

}