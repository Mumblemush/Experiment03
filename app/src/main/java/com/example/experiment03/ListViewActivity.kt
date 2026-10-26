package com.example.experiment03

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat

/**
 * 实验三 · 第 1 部分：用 SimpleAdapter 实现动物列表
 * 每行左边名字、右边动物图片；点某行 Toast 提示 + 发通知；选中行背景变红
 */
class ListViewActivity : AppCompatActivity() {

    private val CHANNEL_ID = "animal_notify"
    private val REQUEST_NOTIFY_CODE = 1001
    // 当前选中行下标，-1 表示未选中
    private var selectedPos = -1

    private lateinit var dataList: ArrayList<HashMap<String, Any>>
    private lateinit var myAdapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Android 13+ 动态申请通知权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQUEST_NOTIFY_CODE
                )
            }
        }

        createNotificationChannel()

        val listView: ListView = findViewById(R.id.listView)
        dataList = ArrayList()

        // 准备动物数据：name → 名字，pic → 图片资源 id
        fun addAnimal(name: String, picRes: Int) {
            val map = HashMap<String, Any>()
            map["name"] = name
            map["pic"] = picRes
            dataList.add(map)
        }

        addAnimal("Lion", R.drawable.lion)
        addAnimal("Tiger", R.drawable.tiger)
        addAnimal("Monkey", R.drawable.monkey)
        addAnimal("Dog", R.drawable.dog)
        addAnimal("Cat", R.drawable.cat)
        addAnimal("Elephant", R.drawable.elephant)

        // SimpleAdapter：把 dataList 每条 Map 填到 item_animal 布局
        myAdapter = object : SimpleAdapter(
            this,
            dataList,
            R.layout.item_animal,
            arrayOf("name", "pic"),
            intArrayOf(R.id.tv_name, R.id.iv_pic)
        ) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup?): View {
                val view = super.getView(position, convertView, parent)
                // 选中行背景暗红，其余白色
                if (position == selectedPos) {
                    view.setBackgroundColor(0xFF8B0000.toInt())
                } else {
                    view.setBackgroundColor(0xFFFFFFFF.toInt())
                }
                return view
            }
        }

        listView.adapter = myAdapter

        // 点击列表项：Toast 提示 + 发送通知
        listView.setOnItemClickListener { _, _, position, _ ->
            selectedPos = position
            myAdapter.notifyDataSetChanged()

            val animalName = dataList[position]["name"].toString()
            Toast.makeText(this@ListViewActivity, "选中：$animalName", Toast.LENGTH_SHORT).show()
            sendNotification(animalName)
        }

        // 底部按钮：选中大象
        findViewById<Button>(R.id.btn_elephant).setOnClickListener {
            val elephantPos = dataList.indexOfFirst { it["name"] == "Elephant" }
            if (elephantPos >= 0) {
                selectedPos = elephantPos
                myAdapter.notifyDataSetChanged()
                Toast.makeText(this, "选中：Elephant", Toast.LENGTH_SHORT).show()
                sendNotification("Elephant")
            }
        }
    }

    // 创建通知渠道
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "动物选择通知",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

    // 发送通知：图标为应用图标，标题为动物名
    private fun sendNotification(titleText: String) {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(titleText)
            .setContentText("你选中了动物 $titleText")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(100, notificationBuilder.build())
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
