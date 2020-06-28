package com.asadkhan.base_app

import android.os.Bundle
import android.os.Handler
import com.asadkhan.global.Constants
import com.asadkhan.global.base.BaseActivity
import com.asadkhan.global.handler
import kotlinx.android.synthetic.main.activity_main.tv_hello_world

class MyCityActivity : BaseActivity() {
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    handler.postDelayed({
      tv_hello_world.text = getString(R.string.hello_world)
    }, 2000)
    val answerToEverything = Constants.answerToEverything()
  }
}
