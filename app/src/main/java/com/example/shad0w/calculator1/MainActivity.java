package com.example.shad0w.calculator1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView2;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView1 = findViewById(R.id.box1);
        textView2 = findViewById(R.id.box2);

    }

    String history = "";
    char op = '\0';
    String v2 = "";
    String v1 = "";
    String v3 = "";
    String ans = "";
    boolean dot = true;
    boolean tv1 = false;
    int size = 40;
    float size2 = 60.0f;
    int compare = 20;
    int compare2 = 10;

    public void values(View view) {
        if (history.length() >= compare) {
            compare += 7;
            size -= 2;
            if (history.length() > 50)
                compare += 7;
            textView1.setTextSize(size);
        }
        String l = "" + v1 + op + v2 + v3;
        if (l.length() >= compare2) {
            int temp = l.length() / 8;
            int temp1 = l.length() % 10;
            compare2 += 3;
            size2 -= temp + temp1 / 10.0;
            textView2.setTextSize(size2);
        }
        if (op == '\0' && v3.isEmpty())
            reset();
        Button b = (Button) view;
        String temp = (String) b.getText();
        v3 = v3 + temp;
        history = history + temp;
        print();
    }

    public void operator(View view) {
        if (op != '\0' && !v1.isEmpty() && !v3.isEmpty())
            solve();
        if (op != '\0')
            return;
        Button b = (Button) view;
        String temp = (String) b.getText();
        history += temp;
        op = temp.charAt(0);
        if ((op - '+'==0 || op-'-'==0) && ans.isEmpty()&&v3.isEmpty()&&v1.isEmpty()) {
            v3 = v3 + op;
            history = history + op;
            op = '\0';
            print();
            return;
        }
        if(v3.isEmpty()&&ans.isEmpty())
        {
            op='\0';
            return;
        }
        dot = true;
        if (!ans.isEmpty()) {
            v1 = ans;
            ans = "";
            v3 = "";
        } else if (v1.isEmpty()) {
            v1 = v3;
            v3 = "";
        } else if (v2.isEmpty()) {
            v2 = v3;
            v3 = "";
        }

        print();
    }

    private void solve() {
        if (!v3.isEmpty())
            v2 = v3;
        if (v1.isEmpty() || v2.isEmpty()) {
            Toast.makeText(getApplicationContext(), "enter proper value", Toast.LENGTH_SHORT).show();
            reset();
            return;
        }
        Double d1 = Double.parseDouble(v1);
        Double d2 = Double.parseDouble(v2);
        Double answer = 0.0;
        switch (op) {
            case '+':
                answer = d1 + d2;
                break;
            case '-':
                answer = d1 - d2;
                break;
            case '/':
                answer = d1 / d2;
                break;
            case 'X':
                answer = d1 * d2;
                break;
        }
        ans = answer + "";
        textView2.setText(ans);
        v1 = "";
        v2 = "";
        v3 = "";
        tv1 = true;
        dot = true;
        op = '\0';
    }

    private void reset() {
        history = "";
        op = '\0';
        v2 = "";
        v1 = "";
        v3 = "";
        ans = "";
        dot = true;
        tv1 = false;
        size = 40;
        compare = 20;
        size2 = 60.0f;
        compare2 = 10;
        textView2.setText("0");
        textView1.setText("");
        textView1.setTextSize(size);
        textView2.setTextSize(size2);
    }

    public void reset(View view) {
        reset();

    }

    public void solve(View view) {
        solve();

    }

    public void decimal(View view) {
        if (op == '\0' && v3.isEmpty())
            reset();
        if (dot) {
            v3 = v3 + ".";
            history = history + ".";
            dot = false;
            print();

        }
    }

    void print() {
        if (tv1) {
            textView1.setText(history);
            textView2.setText(v1 + op + v3);
        } else {
            textView2.setText(v1 + op + v3);
        }

    }

    public void delete(View view) {
        if (!v3.isEmpty()) {
            history = history.substring(0, history.length() - 1);
            v3 = v3.substring(0, v3.length() - 1);

        } else if (op != '\0') {
            history = history.substring(0, history.length() - 1);
            op = '\0';
        } else
            reset();
        print();
    }
}