package isoyoung.com.mediastoreloader;


import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;

public class MediaStoreLoader {
    private static final String TAG = MediaStoreLoader.class.getSimpleName();

    private Context mContext;

    public MediaStoreLoader(Context context) {
        mContext = context;
    }

    public ArrayList<MediaStoreItemDto> loadMediaStore() {

        ArrayList<MediaStoreItemDto> dtos = new ArrayList<>();

        ContentResolver resolver = mContext.getContentResolver();

        Uri uri = MediaStore.Files.getContentUri("external");
        // Cursorオブジェクトに与える情報
        String[] projection = {
                MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.DATE_ADDED,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.Files.FileColumns.MIME_TYPE,
                MediaStore.Files.FileColumns.TITLE,
                MediaStore.Files.FileColumns.SIZE
        };

        // 取得対象は静止画のみ
        String selection = MediaStore.Files.FileColumns.MEDIA_TYPE + "=" + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE;

        Cursor cursor = resolver.query(uri, projection, selection, null, null);

        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                MediaStoreItemDto dto = new MediaStoreItemDto();

                String filePath = cursor.getString(cursor.getColumnIndex("_data"));
                dto.mFilePath = filePath;

                if (isContainedDirectory(filePath)) {
                    dtos.add(dto);
                }
            }
        }
        return dtos;
    }

    private boolean isContainedDirectory(final String filePath) {
        // ディレクトリ名
        final String dirName = "isoyoung";

        if (filePath.contains(dirName)) {
            return true;
        }

        return false;
    }


}
