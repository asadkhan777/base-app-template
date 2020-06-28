package com.asadkhan.global.base

import androidx.appcompat.app.AppCompatActivity
import com.asadkhan.global.Constants

open class BaseActivity : AppCompatActivity() {
  
  val answerToEverything = Constants.answerToEverything()
  
  fun getCurrentFragment(containerId: Int): BaseFragment? {
    val fragment = supportFragmentManager.findFragmentById(containerId)
    val fragmentPresent = fragment != null && fragment.childFragmentManager.fragments.size > 0
    return if (fragment != null && fragmentPresent) {
      fragment.childFragmentManager.fragments[0] as BaseFragment
    } else {
      null
    }
    
  }
}