package com.asadkhan.base_app

import android.os.Bundle
import android.os.Handler
import com.asadkhan.global.Constants
import com.asadkhan.global.base.BaseActivity
import kotlinx.android.synthetic.main.activity_my_contacts.tv_hello_world

class MyContactsActivity : BaseActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_contacts)
    Handler().postDelayed({
      tv_hello_world.text = getString(R.string.hello_world)
    }, 2000)
    val answerToEverything = Constants.answerToEverything()
  }
}
