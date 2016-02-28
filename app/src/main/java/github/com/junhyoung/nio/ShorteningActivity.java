package github.com.junhyoung.nio;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class ShorteningActivity extends AppCompatActivity {


    private ListView mListView = null;
    private ListViewAdapter mAdapter = null;
    SQLiteDatabase db;
    String dbName = "save.db";
    String tableName = "call";
    int dbMode = Context.MODE_PRIVATE;

    AlertDialog.Builder builder;

    @Override
    public void onResume() {
        super.onResume();
        readAllDb();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortening);
        db = openOrCreateDatabase(dbName, dbMode, null);
        createTable();
        mListView = (ListView) findViewById(R.id.listView);
        builder = new AlertDialog.Builder(this);

        mAdapter = new ListViewAdapter(this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ListCall mdata = mAdapter.mListData.get(position);
                checkDelete(mdata); //선택된 데이터를 지우기위한 함수 호출
                return true;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                ListCall mData = mAdapter.mListData.get(position);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mData.number));

                try {
                    startActivity(intent);
                } catch (Exception e) {
                    startActivity(intent);
                }

            }
        });
        readAllDb();
    }

    public void back(View v){finish();}
    public void add(View v){startActivity(new Intent(getApplicationContext(), AddnumberActivity.class));}

    public void checkDelete(final ListCall mdata){ //선택된 데이터 지우기위해 확인창을 띄우는 함수
        // 여기서 부터는 알림창의 속성 설정
        builder.setTitle("정말 삭제하시겠습니까?")        // 제목 설정
                .setMessage(mdata.name+"의 번호를 \n삭제하시겠습니까?")        // 메세지 설정
                .setCancelable(false)        // 뒤로 버튼 클릭시 취소 가능 설정
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    // 확인 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) { //확인 클릭시
                        deleteData(mdata); // 삭제 함수 호출
                        mAdapter.clear(); // 리스트뷰 초기화
                        readAllDb(); //삭제후 DB 출력
                        mAdapter.notifyDataSetChanged(); // 리스트뷰 출력
                    }
                })
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    // 취소 버튼 클릭시 설정
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel(); //삭제 취소
                    }
                });

        AlertDialog dialog = builder.create();    // 알림창 객체 생성
        dialog.show();    // 알림창 띄우기
    }


    public void deleteData(ListCall mdata){ //삭제함수
        String sql = "delete from " + tableName + " where id = " + mdata.id + ";";
        db.execSQL(sql);

    }


    public void readAllDb(){
        String sql = "select * from " + tableName + " order by id DESC ;";//내림차순으로 최신데이터부터 출력
        Cursor result = db.rawQuery(sql, null);
        result.moveToFirst();
        mAdapter.clear();

        while (!result.isAfterLast()) {
            int id=result.getInt(0);
            String name = result.getString(1);
            String number = result.getString(2);

            Log.d("", name + number);

            mAdapter.addItem(id,name,number);
            result.moveToNext();
        }
        result.close();
    }


    public void createTable(){ //DB의 Table이 없을때 table 생성
        try {
            String sql = "create table " + tableName + "(id integer primary key autoincrement, " + "name text not null, number text not null)";
            db.execSQL(sql);
        } catch (android.database.sqlite.SQLiteException e) {
            Log.d("Lab sqlite", "error: " + e);
        }
    }

    private class ViewHolder{
        public int id;
        public TextView name;
        public TextView number;
    }

    private class ListViewAdapter extends BaseAdapter {
        private Context mContext=null;
        private ArrayList<ListCall> mListData = new ArrayList<ListCall>();
        public ListViewAdapter(Context mContext) {
            super();
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return mListData.size();
        }

        @Override
        public Object getItem(int position) {
            return mListData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();

                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listview_call, null);

                holder.name = (TextView) convertView.findViewById(R.id.name);
                holder.number = (TextView) convertView.findViewById(R.id.number);

                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            ListCall mData = mListData.get(position);



            holder.name.setText(mData.name);
            holder.number.setText(mData.number);

            return convertView;
        }
        public void clear(){
            mListData.clear();
        }

        public void addItem(int mId,String mTitle, String mDate){
            ListCall addInfo = null;
            addInfo = new ListCall();
            addInfo.id=mId;
            addInfo.name = mTitle;
            addInfo.number = mDate;

            mListData.add(addInfo);
        }

        public void remove(int position){
            mListData.remove(position);
            dataChange();
        }

        public void sort(){
            Collections.sort(mListData, ListCall.ALPHA_COMPARATOR);
            dataChange();
        }

        public void dataChange(){
            mAdapter.notifyDataSetChanged();
        }
    }
}
