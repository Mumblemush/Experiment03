package com.example.experiment03

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

/**
 * 实验导航主页：5个按钮分别进入5个实验部分
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        findViewById<Button>(R.id.btn_listview).setOnClickListener {
            startActivity(Intent(this, ListViewActivity::class.java))
        }
        findViewById<Button>(R.id.btn_dialog).setOnClickListener {
            startActivity(Intent(this, LoginDialogActivity::class.java))
        }
        findViewById<Button>(R.id.btn_menu).setOnClickListener {
            startActivity(Intent(this, MenuTestActivity::class.java))
        }
        findViewById<Button>(R.id.btn_actionmode).setOnClickListener {
            startActivity(Intent(this, ActionModeActivity::class.java))
        }
        findViewById<Button>(R.id.btn_recycler).setOnClickListener {
            startActivity(Intent(this, RecyclerViewActivity::class.java))
        }
    }
}
