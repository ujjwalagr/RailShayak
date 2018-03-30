
package passengersecurity.com.passengersecurity.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class ComplaintProvider extends ContentProvider {
    public static final String LOG_TAG = ComplaintProvider.class.getSimpleName();


    private static final int COMPLAINTS = 100;

    private static final int COMPLAINTS_ID = 101;

    private static final int SMS_DELIVERY = 200;

    private static final int SMS_DELIVERY_ID = 201;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        sUriMatcher.addURI(ComplaintContract.CONTENT_AUTHORITY, ComplaintContract.PATH_PNR_LOG, COMPLAINTS);

        sUriMatcher.addURI(ComplaintContract.CONTENT_AUTHORITY, ComplaintContract.PATH_PNR_LOG + "/#", COMPLAINTS_ID);

        sUriMatcher.addURI(ComplaintContract.CONTENT_AUTHORITY, ComplaintContract.PATH_SMS, SMS_DELIVERY);

        sUriMatcher.addURI(ComplaintContract.CONTENT_AUTHORITY, ComplaintContract.PATH_SMS + "/#", SMS_DELIVERY_ID);
    }

    private ComplaintDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new ComplaintDbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String s1) {
        SQLiteDatabase database = mDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case COMPLAINTS:
                cursor = database.query(ComplaintContract.PnrLog.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, s1);
                break;
            case COMPLAINTS_ID:

               /* selection = ComplaintContract.PnrLog._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};*/

                cursor = database.query(ComplaintContract.PnrLog.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, s1);
                break;
            case SMS_DELIVERY:
                cursor = database.query(ComplaintContract.SmsDelivery.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, s1);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COMPLAINTS:
                String itemName = values.getAsString(ComplaintContract.PnrLog.COLUMN_PNR);
                if (itemName == null) {
                    throw new IllegalArgumentException("Item requires a name");
                }


                String getDate = values.getAsString(ComplaintContract.PnrLog.COLUMN_DOJ);
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    Date dojDate = simpleDateFormat.parse(getDate);
                    Date currentDate = simpleDateFormat.parse(Calendar.getInstance().getTime().toString());
                    if (dojDate.compareTo(currentDate) < 0) {
                        throw new IllegalArgumentException("Doj is behind the current date");
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                long id = database.insert(ComplaintContract.PnrLog.TABLE_NAME, null, values);
                if (id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }

                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, id);
            case SMS_DELIVERY:
                String msgBody = values.getAsString(ComplaintContract.SmsDelivery.COLUMN_MSG_BODY);
                if (msgBody == null || msgBody.equals(" ")) {
                    throw new IllegalArgumentException("Msg Body Needed");
                }

                long sms_id = database.insert(ComplaintContract.SmsDelivery.TABLE_NAME, null, values);
                if (sms_id == -1) {
                    Log.e(LOG_TAG, "Failed to insert row for " + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return ContentUris.withAppendedId(uri, sms_id);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();

        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case COMPLAINTS:
                rowsDeleted = database.delete(ComplaintContract.PnrLog.TABLE_NAME, selection, selectionArgs);
                break;
            case COMPLAINTS_ID:
                selection = ComplaintContract.PnrLog._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(ComplaintContract.PnrLog.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

