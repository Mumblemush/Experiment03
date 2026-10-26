package com.example.experiment03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * 实验三 · 第 5 部分：使用 RecyclerView 实现列表视图效果
 * - 使用原动物图片资源，列表项包含图片、名称
 * - 使用 RecyclerView、Adapter 与 ViewHolder 展示数据
 * - 单击显示名称
 * - 点击"增加一条记录"后列表立即更新（notifyItemInserted）
 */
class RecyclerViewActivity : AppCompatActivity() {

    // 数据模型：动物名称 + 图片资源
    private data class Animal(val name: String, val picRes: Int)

    private val animalList = ArrayList<Animal>()
    private lateinit var adapter: AnimalAdapter

    // 可用的动物图片资源（与第 1 部分一致）
    private val picPool = intArrayOf(
        R.drawable.lion, R.drawable.tiger, R.drawable.monkey,
        R.drawable.dog, R.drawable.cat, R.drawable.elephant
    )
    private var addCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler)

        // 初始化数据：6 种动物
        animalList.add(Animal("Lion", R.drawable.lion))
        animalList.add(Animal("Tiger", R.drawable.tiger))
        animalList.add(Animal("Monkey", R.drawable.monkey))
        animalList.add(Animal("Dog", R.drawable.dog))
        animalList.add(Animal("Cat", R.drawable.cat))
        animalList.add(Animal("Elephant", R.drawable.elephant))

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        // 线性布局
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = AnimalAdapter(animalList)
        recyclerView.adapter = adapter

        // 增加一条记录：列表立即更新
        findViewById<Button>(R.id.btn_add).setOnClickListener {
            addCount++
            val newName = "新增动物 $addCount"
            val pic = picPool[addCount % picPool.size]
            val insertPos = animalList.size
            animalList.add(Animal(newName, pic))
            // 通知适配器在指定位置插入，立即刷新且不错位
            adapter.notifyItemInserted(insertPos)
            // 滚动到新插入的位置
            recyclerView.scrollToPosition(insertPos)
        }
    }

    /**
     * RecyclerView.Adapter + ViewHolder
     */
    private class AnimalAdapter(
        private val data: List<Animal>
    ) : RecyclerView.Adapter<AnimalAdapter.AnimalViewHolder>() {

        // 创建行布局：使用 CardView 卡片样式（自学内容：Card-Based Layout）
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_animal_card, parent, false)
            return AnimalViewHolder(view)
        }

        // 绑定数据
        override fun onBindViewHolder(holder: AnimalViewHolder, position: Int) {
            val animal = data[position]
            holder.tvName.text = animal.name
            holder.ivPic.setImageResource(animal.picRes)

            // 单击列表项：显示名称
            holder.itemView.setOnClickListener {
                Toast.makeText(
                    holder.itemView.context,
                    "单击了：${animal.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        override fun getItemCount(): Int = data.size

        // ViewHolder：持有列表项中的视图引用
        class AnimalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val tvName: TextView = itemView.findViewById(R.id.tv_name)
            val ivPic: ImageView = itemView.findViewById(R.id.iv_pic)
        }
    }
}
