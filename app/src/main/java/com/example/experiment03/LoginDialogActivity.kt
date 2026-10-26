package com.example.experiment03

import android.app.AlertDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * 实验三 · 第 2 部分：创建自定义布局的 AlertDialog
 * - 自定义 dialog_login 布局（ANDROID APP 标题、Username/Password 输入框、Cancel/Sign in 按钮）
 * - 调用 AlertDialog.Builder 对象上的 setView() 将布局添加到 AlertDialog
 */
class LoginDialogActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_dialog)

        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            showLoginDialog()
        }
    }

    private fun showLoginDialog() {
        // 1. 加载自定义布局
        val dialogView = layoutInflater.inflate(R.layout.dialog_login, null)
        val etUsername = dialogView.findViewById<EditText>(R.id.et_username)
        val etPassword = dialogView.findViewById<EditText>(R.id.et_password)

        // 2. 创建 AlertDialog 并通过 setView() 添加自定义布局
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        // 3. 设置按钮点击事件（在对话框内容上，不使用 Builder 自带按钮）
        dialogView.findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            dialog.dismiss()
        }
        dialogView.findViewById<Button>(R.id.btn_signin).setOnClickListener {
            val user = etUsername.text.toString()
            val pwd = etPassword.text.toString()
            Toast.makeText(
                this,
                "登录：用户名=$user，密码=${pwd.ifEmpty { "（空）" }}",
                Toast.LENGTH_SHORT
            ).show()
            dialog.dismiss()
        }

        // 4. 显示对话框，并调整宽度使其更贴合自定义布局
        dialog.show()
        dialog.window?.setLayout(
            (resources.displayMetrics.widthPixels * 0.85).toInt(),
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}
