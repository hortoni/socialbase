package socialbase.com.br.challengesocialbase.component;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import socialbase.com.br.challengesocialbase.model.Image;
import socialbase.com.br.challengesocialbase.model.Post;

/**
 * Created by hortoni on 22/02/16.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "post_database";

    private static final String TABLE_POSTS = "tposts";
    private static final String TABLE_IMAGES = "timages";

    private static final String KEY_POST_ID = "id";
    private static final String KEY_POST_REVISION_ID = "revisionid";
    private static final String KEY_POST_URL = "url";
    private static final String KEY_POST_TYPE= "type";
    private static final String KEY_POST_CATEGORY = "category";
    private static final String KEY_POST_SUBJECT = "subject";
    private static final String KEY_POST_TITLE= "title";
    private static final String KEY_POST_USERNAME = "username";
    private static final String KEY_POST_IMAGE_ID_FK = "imageid";

    private static final String KEY_IMAGE_ID = "id";
    private static final String KEY_IMAGE_GUID = "guid";
    private static final String KEY_IMAGE_MEDIUM = "medium";

    private static final String CREATE_POST_TABLE =
        "CREATE TABLE " + TABLE_POSTS+ "(" +
                KEY_POST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_POST_REVISION_ID + " INTEGER," +
                KEY_POST_URL + " TEXT," +
                KEY_POST_TYPE + " TEXT," +
                KEY_POST_CATEGORY + " TEXT," +
                KEY_POST_SUBJECT + " TEXT," +
                KEY_POST_TITLE + " TEXT," +
                KEY_POST_USERNAME + " TEXT," +
                KEY_POST_IMAGE_ID_FK + " INTEGER REFERENCES " + TABLE_IMAGES+ ")";

    private String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGES+ "(" +
            KEY_IMAGE_ID+ " INTEGER PRIMARY KEY," +
            KEY_IMAGE_GUID+ " TEXT," +
            KEY_IMAGE_MEDIUM+ " TEXT" + ")";


    public static synchronized DatabaseHelper getInstance(Context context) {
        if (instance == null) {
            instance= new DatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_POST_TABLE);
        db.execSQL(CREATE_IMAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGES);
        onCreate(db);
    }

    public long addImage(Image image) {
        SQLiteDatabase db = getWritableDatabase();
        long userId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_IMAGE_GUID, image.getGuid());
            values.put(KEY_IMAGE_MEDIUM, image.getMedium());

            userId = db.insertOrThrow(TABLE_IMAGES, null, values);
            db.setTransactionSuccessful();

        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
        return userId;
    }

    public void addPost(Post post) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {

            ContentValues values = new ContentValues();
            values.put(KEY_POST_REVISION_ID, post.getRevisionid());
            values.put(KEY_POST_URL, post.getUrl());
            values.put(KEY_POST_TYPE, post.getType());
            values.put(KEY_POST_CATEGORY, post.getCategory());
            values.put(KEY_POST_SUBJECT, post.getSubject());
            values.put(KEY_POST_TITLE, post.getTitle());
            values.put(KEY_POST_USERNAME, post.getUsername());

            if (post.getImage() != null) {
                long imageId = addImage(post.getImage());
                values.put(KEY_POST_IMAGE_ID_FK, imageId);
            }

            db.insertOrThrow(TABLE_POSTS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to add post to database");
        } finally {
            db.endTransaction();
        }
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();

        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s LEFT OUTER JOIN %s ON %s.%s = %s.%s",
                        TABLE_POSTS,
                        TABLE_IMAGES,
                        TABLE_POSTS, KEY_POST_IMAGE_ID_FK,
                        TABLE_IMAGES, KEY_IMAGE_ID);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Image newImage= new Image();
                    newImage.setGuid(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_GUID)));
                    newImage.setMedium(cursor.getString(cursor.getColumnIndex(KEY_IMAGE_MEDIUM)));

                    Post newPost = new Post();
                    newPost.setRevisionid(cursor.getInt(cursor.getColumnIndex(KEY_POST_REVISION_ID)));
                    newPost.setUrl(cursor.getString(cursor.getColumnIndex(KEY_POST_URL)));
                    newPost.setType(cursor.getString(cursor.getColumnIndex(KEY_POST_TYPE)));
                    newPost.setCategory(cursor.getString(cursor.getColumnIndex(KEY_POST_CATEGORY)));
                    newPost.setSubject(cursor.getString(cursor.getColumnIndex(KEY_POST_SUBJECT)));
                    newPost.setTitle(cursor.getString(cursor.getColumnIndex(KEY_POST_TITLE)));
                    newPost.setUsername(cursor.getString(cursor.getColumnIndex(KEY_POST_USERNAME)));
                    newPost.setImage(newImage);
                    posts.add(newPost);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return posts;
    }

    public void deleteAllPosts() {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        try {
            db.delete(TABLE_POSTS, null, null);
            db.delete(TABLE_IMAGES, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("DEBUG", "Error while trying to delete all posts and users");
        } finally {
            db.endTransaction();
        }
    }
}
