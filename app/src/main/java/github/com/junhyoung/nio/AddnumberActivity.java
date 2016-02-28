package github.com.junhyoung.nio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddnumberActivity extends AppCompatActivity {
    EditText num1;
    EditText num2;
    EditText num3;
    EditText name;
    SQLiteDatabase db;
    String dbName="save.db";
    String tableName="call";
    int dbMode= Context.MODE_PRIVATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnumber);
        db= openOrCreateDatabase(dbName,dbMode,null);
        createTable();
        num1=(EditText)findViewById(R.id.num);
        num2=(EditText)findViewById(R.id.num1);
        num3=(EditText)findViewById(R.id.num2);
        name=(EditText)findViewById(R.id.name);
    }

    public void cancle(View v){
        finish();
    }
    public void save(View v){
        String Num1=num1.getText().toString();
        String Num2=num2.getText().toString();
        String Num3=num3.getText().toString();
        String Name=name.getText().toString();
        String Number="";
        if(Name.equals("")){ //이름유무
            Toast.makeText(getApplicationContext(),"이름을 입력해주세요",Toast.LENGTH_SHORT).show();
        }

        if(!Num1.equals("") && !Num2.equals("") && !Num3.equals("")) { //번호 유무
            Number = Num1 + "-" + Num2 + "-" + Num3;

        }else{
            Toast.makeText(getApplicationContext(),"번호를 입력해주세요",Toast.LENGTH_SHORT).show();
        }

        String sql = "insert into " + tableName + " values(NULL, '" + Name + "'" + ", " + "'" + Number + "');"; //db 저장
        db.execSQL(sql);
        finish();
    }

    public void createTable(){ //DB의 Table이 없을때 table 생성
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null, number text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }
}
