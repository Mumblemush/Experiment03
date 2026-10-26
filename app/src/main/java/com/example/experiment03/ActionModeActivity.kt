package com.example.experiment03

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AbsListView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 实验三 · 第 4 部分：创建上下文操作模式（ActionMode）的上下文菜单
 * - 使用 ListView 创建列表（One~Five）
 * - 为 List Item 创建 ActionMode 形式的上下文菜单
 * - 长按列表项进入多选模式，顶部显示 "N selected"，支持勾选完成/删除
 */
class ActionModeActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var dataList: ArrayList<HashMap<String, Any>>
    private lateinit var adapter: SimpleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_action_mode)

        listView = findViewById(R.id.list_view)

        // 准备列表数据：One~Five
        dataList = ArrayList()
        val names = arrayOf("One", "Two", "Three", "Four", "Five")
        for (name in names) {
            val map = HashMap<String, Any>()
            map["name"] = name
            dataList.add(map)
        }

        adapter = object : SimpleAdapter(
            this,
            dataList,
            R.layout.item_action_mode,
            arrayOf("name"),
            intArrayOf(R.id.tv_name)
        ) {
            override fun getView(position: Int, convertView: View?, parent: android.view.ViewGroup?): View {
                val view = super.getView(position, convertView, parent)
                // 选中的列表项高亮为蓝色
                if (listView.isItemChecked(position)) {
                    view.setBackgroundColor(0xFF2196F3.toInt())
                } else {
                    view.setBackgroundColor(0xFFFFFFFF.toInt())
                }
                return view
            }
        }
        listView.adapter = adapter

        // 使用 CHOICE_MODE_MULTIPLE_MODAL 为列表项创建 ActionMode 上下文菜单
        listView.choiceMode = ListView.CHOICE_MODE_MULTIPLE_MODAL
        listView.setMultiChoiceModeListener(object : AbsListView.MultiChoiceModeListener {

            // 勾选状态变化时：更新操作栏标题为选中数量
            override fun onItemCheckedStateChanged(mode: ActionMode?, position: Int, id: Long, checked: Boolean) {
                mode?.title = "${listView.checkedItemCount} selected"
                adapter.notifyDataSetChanged()
            }

            // 创建 ActionMode：填充 XML 菜单
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                menuInflater.inflate(R.menu.action_mode_menu, menu)
                mode?.title = "0 selected"
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = true

            // 点击菜单项：删除或完成
            override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.action_delete -> {
                        // 收集所有选中的位置并删除
                        val checkedPositions = ArrayList<Int>()
                        for (i in dataList.indices) {
                            if (listView.isItemChecked(i)) checkedPositions.add(i)
                        }
                        for (i in checkedPositions.indices.reversed()) {
                            dataList.removeAt(checkedPositions[i])
                        }
                        adapter.notifyDataSetChanged()
                        Toast.makeText(
                            this@ActionModeActivity,
                            "已删除 ${checkedPositions.size} 项",
                            Toast.LENGTH_SHORT
                        ).show()
                        mode?.finish()
                        true
                    }
                    R.id.action_done -> {
                        // 勾选：完成操作并退出 ActionMode
                        mode?.finish()
                        true
                    }
                    else -> false
                }
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                // ActionMode 销毁后恢复普通列表状态
                adapter.notifyDataSetChanged()
            }
        })
    }
}
