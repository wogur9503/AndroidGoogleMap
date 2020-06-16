package com.cookandroid.project12_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    myDBHelper dbHelper;
    EditText etdptName, etStudentNum, etPhoneNum, etdptNameResult,
            etStudentNumResult, etPhoneNumResult;
    Button btnInput, btnInquiry;
    SQLiteDatabase sqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etdptName = (EditText)findViewById(R.id.etDptName);
        etdptNameResult = (EditText)findViewById(R.id.etDptNameResult);
        etStudentNum = (EditText)findViewById(R.id.etStudentNum);
        etStudentNumResult = (EditText)findViewById(R.id.etStudentNumResult);
        etPhoneNum = (EditText)findViewById(R.id.etPhoneNum);
        etPhoneNumResult = (EditText)findViewById(R.id.etPhoneNumResult);

        btnInput = (Button)findViewById(R.id.btnInput);
        btnInquiry = (Button)findViewById(R.id.btnInquiry);

        dbHelper = new myDBHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        dbHelper.onUpgrade(sqLiteDatabase, 1, 2);
        sqLiteDatabase.close();
        btnInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDatabase = dbHelper.getWritableDatabase();
                sqLiteDatabase.execSQL("INSERT INTO groupTBL VALUES ( '"
                        + etdptName.getText().toString() + "' , "
                        + etStudentNum.getText().toString() + ", "
                        + etPhoneNum.getText().toString() + ");");
                sqLiteDatabase.close();
                Toast.makeText(getApplicationContext(), "입력됨",
                        Toast.LENGTH_SHORT).show();
            }
        });

        btnInquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sqLiteDatabase = dbHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqLiteDatabase.rawQuery("SELECT * FROM groupTBL;", null);

                String strdptName = "\r" +"\r\n" + "\r" + "\r\n";
                String strStudentNum = "\r" +"\r\n" + "\r" + "\r\n";
                String strPhoneNum = "\r" +"\r\n" + "\r" + "\r\n";

                while (cursor.moveToNext()){
                    strdptName += cursor.getString(0) + "\r\n";
                    strStudentNum += cursor.getString(1) + "\r\n";
                    strPhoneNum += cursor.getString(2) + "\r\n";
                }

                etdptNameResult.setText(strdptName);
                etStudentNumResult.setText(strStudentNum);
                etPhoneNumResult.setText(strPhoneNum);

                cursor.close();
                sqLiteDatabase.close();
            }
        });
    }
    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE groupTBL (dptName CHAR(200) PRIMARY KEY, stNum INTEGER, stPhoneNum CHAR(200))");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS groupTBL");
            onCreate(db);
        }
    }
}
