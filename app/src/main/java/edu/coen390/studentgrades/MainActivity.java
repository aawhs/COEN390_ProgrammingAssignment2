package edu.coen390.studentgrades;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import edu.coen390.studentgrades.db.DBHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelper dbHelper = new DBHelper(this);
    }
}