package kuce15.myassistant;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by pratik on 2/11/17.
 */

public class TasksProvider extends ContentProvider{
    private static final String AUTHORITY = "com.example.pratik.locationr.tasksprovider";
    private static final String BASE_PATH = "notes";
    public static final Uri CONTENT_URI =
            Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH );

    // Constant to identify the requested operation
    private static final int NOTES = 1;
    private static final int NOTES_ID = 2;

    private static final UriMatcher uriMatcher= new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH,NOTES);

        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#",NOTES_ID);



    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        TaskDbhelper helper = new TaskDbhelper(getContext());
        database = helper.getWritableDatabase();

        return true;

    }


    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return database.query(TaskDbhelper.TABLE_NOTES,TaskDbhelper.ALL_COLUMNS,
                selection,null,null,null,
                TaskDbhelper.NOTE_CREATED + " Desc");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long id = database.insert(TaskDbhelper.TABLE_NOTES, null,values);

        return Uri.parse(BASE_PATH + "/" +id);

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

      return database.delete(TaskDbhelper.TABLE_NOTES, selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(TaskDbhelper.TABLE_NOTES,values,selection,selectionArgs);
    }
}
