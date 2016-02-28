package github.com.junhyoung.nio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ComputeActivity extends AppCompatActivity {

    TextView result;
    String oper="";
    boolean isFirst=true;
    String A="";
    String B="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compute);
        result=(TextView)findViewById(R.id.result);
        result.setText("");
    }


    public void zero(View v){
        if (A.equals("0")) {}
        else if(A.length()<9){
            addResult(0);}
        set();
    }
    public void one(View v){
        if(A.length()<9){
        addResult(1);
        set();}
    }
    public void two(View v){

        if(A.length()<9){
            addResult(2);
        }
        set();
    }

    public void three(View v){

        if(A.length()<9){
            addResult(3);
        }
        set();
    }

    public void four(View v){

        if(A.length()<9){
            addResult(4);
        }
        set();
    }

    public void five(View v){

        if(A.length()<9){
            addResult(5);
        }
        set();
    }

    public void six(View v){

        if(A.length()<9){
            addResult(6);
        }
        set();
    }

    public void seven(View v){

        if(A.length()<9){
            addResult(7);
        }
        set();
    }

    public void eight(View v){

        if(A.length()<9){
            addResult(8);
        }
        set();
    }

    public void nine(View v){

        if(A.length()<9){
            addResult(9);
        }
        set();
    }

    public void plus(View v){
        if(A.equals("")){}
        else if(isFirst){
            B=A;
            A="";
            oper="+";
            isFirst=false;
        }
        else{
            B=operation(oper);
            A="";
            oper="+";
            result.setText(B+"");
        }

    }

    public void minus(View v){
        if(A.equals("")){}
        else if(isFirst){
            B=A;
            A="";
            oper="-";
            isFirst=false;
        }
        else{
            B=operation(oper);
            A="";
            oper="-";
            result.setText(B+"");
        }
    }

    public void mod(View v){
        if(B.equals(""))
            B=A;
        else
            B = operation(oper);
        B = operation("%");

        isFirst = true;
        A=B;
        set();

    }
    public void multi(View v){
        if(A.equals("")){}
        else if(isFirst){
            B=A;
            A="";
            oper="*";
            isFirst=false;
        }
        else{
            B=operation(oper);
            A="";
            oper="*";
            result.setText(B+"");
        }
    }

    public void divide(View v){
        if(A.equals("")){}
        else if(isFirst){
            B=A;
            A="";
            oper="/";
            isFirst=false;
        }
        else{
            B=operation(oper);
            A="";
            oper="/";
            result.setText(B+"");
        }
    }

    public void decimal(View v){
        if(A.equals("")){}
        else{
        A+=".";}
    }

    public void negative(View v){
        String temp=A;
        if(A.equals("")){}
        else if(temp.substring(0,1).equals("-")){
            A=A.replaceFirst("-","");
        }else {
            A = "-" + A;
        }
        set();
    }

    public void answer(View v){
        if(A.equals("")){}
        else{
            A=operation(oper);
            B="";
            oper="";
            isFirst=true;
            set();

        }
    }

    public void clear(View v){
        A="";
        B="";
        isFirst=true;
        oper="";
        set();
    }

    public void erase(View v){
        if(A.equals("")){}
        else {
            A = A.substring(0, A.length() - 1);
            set();
        }
    }

    public void addResult(int a){
        A+=a;
    }
    public void set(){
            result.setText(A);
    }
    public String operation(String o){
        String res=B;

        switch (o){
            case "+":
                res=""+ (Double.parseDouble(B)+Double.parseDouble(A));break;
            case "-": res=""+ (Double.parseDouble(B)-Double.parseDouble(A));break;
            case "*": res=""+ (Double.parseDouble(B)*Double.parseDouble(A));break;
            case "/": res=""+ (Double.parseDouble(B)/Double.parseDouble(A));break;
            case "%": res=""+ (Double.parseDouble(B)/100);break;
        }
        if(res.endsWith(".0"))
            res=res.substring(0,res.length()-2);
        if(res.length()>144)
            return res.substring(0,144);
        else
            return res;
    }
}
