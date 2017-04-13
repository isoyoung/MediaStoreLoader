package isoyoung.com.mediastoreloader;

import android.media.ExifInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showMediaStoreContents();
    }

    private void showMediaStoreContents() {
        MediaStoreLoader loader = new MediaStoreLoader(this);
        ArrayList<MediaStoreItemDto> dtos = loader.loadMediaStore();

        for (MediaStoreItemDto dto : dtos) {
            Log.d(TAG, getExif(dto.mFilePath));
        }

    }

    private String getExif(String filePath) {
        try {
            ExifInterface ei = new ExifInterface(filePath);
            String result = ei.getAttribute(ExifInterface.TAG_F_NUMBER);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        // ここにきてはいけない
        return null;
    }


}
