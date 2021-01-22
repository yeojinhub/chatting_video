package com.portfolio.chatting_video

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.portfolio.chatting_video.Model.User
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private val TAG = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        btn_join.setOnClickListener {

            val email = email_main.text.toString()
            val pwd = pwd_main.text.toString()

            auth.createUserWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "회원가입 성공")

                        val uid = FirebaseAuth.getInstance().uid ?: ""

                        val user = User(uid, username.text.toString())

                        // Access a Cloud Firestore instance from your Activity
                        val db = FirebaseFirestore.getInstance().collection("users")
                        db.document(uid)
                            .set(user)
                            .addOnSuccessListener {
                                Log.d(TAG, "데이터베이스 성공")
                            }
                            .addOnFailureListener {
                                Log.d(TAG, "데이터베이스 실패")
                            }
                        // auth.uid.toString() == uid

                        val intent = Intent(this, ChatListActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "회원가입 실패", task.exception)
                        Toast.makeText(baseContext, "회원가입에 실패하였습니다.",
                            Toast.LENGTH_SHORT).show()
                    }

                    // ...
                }
        }

        btn_back.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}