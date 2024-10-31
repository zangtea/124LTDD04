package com.example.contentprovider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> dataList;
    private ArrayAdapter<String> adapter;
    private final int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        dataList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        listView.setAdapter(adapter);

        Button btnShowAllContacts = findViewById(R.id.btnContacts);
        Button btnAccessCallLog = findViewById(R.id.btnCallLog);
        Button btnAccessSMS = findViewById(R.id.btnSMS);

        checkPermissions();

        btnShowAllContacts.setOnClickListener(v -> showAllContacts());
        btnAccessCallLog.setOnClickListener(v -> accessCallLog());
        btnAccessSMS.setOnClickListener(v -> accessSMS());
    }

    private void checkPermissions() {
        String[] permissions = {
                Manifest.permission.READ_CONTACTS,
                Manifest.permission.READ_CALL_LOG,
                Manifest.permission.READ_SMS
        };

        ArrayList<String> permissionsToRequest = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissionsToRequest.add(permission);
            }
        }

        if (!permissionsToRequest.isEmpty()) {
            ActivityCompat.requestPermissions(this, permissionsToRequest.toArray(new String[0]), REQUEST_PERMISSION_CODE);
        }
    }

    private void showAllContacts() {
        dataList.clear();
        Uri uri = ContactsContract.Contacts.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                dataList.add("Liên hệ: " + name);
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    private void accessCallLog() {
        dataList.clear();
        Uri uri = CallLog.Calls.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, CallLog.Calls.DATE + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                dataList.add("Cuộc gọi: " + number + " (" + duration + " giây)");
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    private void accessSMS() {
        dataList.clear();
        Uri uri = Telephony.Sms.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null, null, null, Telephony.Sms.DATE + " DESC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String address = cursor.getString(cursor.getColumnIndex(Telephony.Sms.ADDRESS));
                String body = cursor.getString(cursor.getColumnIndex(Telephony.Sms.BODY));
                dataList.add("Tin nhắn từ: " + address + "\n" + body + "\n");
            }
            cursor.close();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Cần có sự cho phép cho chức năng ứng dụng.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
    }
}