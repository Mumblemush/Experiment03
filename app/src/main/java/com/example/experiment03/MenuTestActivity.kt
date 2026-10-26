package com.example.experiment03

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 实验三 · 第 3 部分：使用 XML 定义菜单（Options Menu）
 * - 字体大小：小(10号)/中(16号)/大(20号)
 * - 普通菜单项：Toast 提示
 * - 字体颜色：红色/黑色
 */
class MenuTestActivity : AppCompatActivity() {

    private lateinit var tvTest: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_test)
        // 把 Toolbar 设为 ActionBar，右上角才会出现"三点菜单"
        setSupportActionBar(findViewById(R.id.toolbar))
        tvTest = findViewById(R.id.tv_test)
    }

    // 创建选项菜单：把 XML 菜单资源加载到 ActionBar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_context, menu)
        return true
    }

    // 处理菜单项点击
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.font_small -> {
                tvTest.textSize = 10f
                Toast.makeText(this, "已设置小号字体（10号字）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.font_middle -> {
                tvTest.textSize = 16f
                Toast.makeText(this, "已设置中号字体（16号字）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.font_large -> {
                tvTest.textSize = 20f
                Toast.makeText(this, "已设置大号字体（20号字）", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.menu_normal -> {
                Toast.makeText(this, "你点击了普通菜单项", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.font_red -> {
                tvTest.setTextColor(Color.RED)
                Toast.makeText(this, "已设置字体颜色：红色", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.font_black -> {
                tvTest.setTextColor(Color.BLACK)
                Toast.makeText(this, "已设置字体颜色：黑色", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
