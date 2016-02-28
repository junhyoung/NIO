package github.com.junhyoung.nio;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddMemoActivity extends AppCompatActivity {

    EditText contents;
    SQLiteDatabase db;
    String dbName="save.db";
    String tableName="memo";
    int dbMode= Context.MODE_PRIVATE;
    String date;
    String time;
    String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addmemo);
        db= openOrCreateDatabase(dbName,dbMode,null);
        createTable();
        contents=(EditText)findViewById(R.id.contents);
        Intent intent=getIntent();
        String temp= (String)intent.getSerializableExtra("text");
        contents.setText(temp);


    }

    public void cancle(View v){
        finish();
    }
    public void save(View v){
        content = contents.getText().toString();
        findDay();
        String sql= "insert into " + tableName + " values(NULL, '" +date+"'"+", " +"'"+content+ "');"; //db 저장
        db.execSQL(sql);
        finish();
    }

    public void createTable(){ //DB의 Table이 없을때 table 생성
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "date text not null,  contents text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }
    public void findDay() { //날짜 찾는 함수
        long now = System.currentTimeMillis();// 현재 시간을 msec으로 구한다.
        Date date = new Date(now);// 현재 시간을 저장 한다.

        SimpleDateFormat CurYearFormat = new SimpleDateFormat("yyyy"); //yyyy형식으로 년도
        SimpleDateFormat CurMonthFormat = new SimpleDateFormat("MM"); //MM형식으로 월
        SimpleDateFormat CurDayFormat = new SimpleDateFormat("dd"); //dd형식으로 일
        SimpleDateFormat CurHourFormat = new SimpleDateFormat("HH"); //HH형식으로 시간
        SimpleDateFormat CurMinuteFormat = new SimpleDateFormat("mm"); //mm형식으로 분

        time=CurHourFormat.format(date)+" : "+CurMinuteFormat.format(date);
        this.date=CurYearFormat.format(date) + " ." + CurMonthFormat.format(date) + " ." + CurDayFormat.format(date) ;
    }


}
