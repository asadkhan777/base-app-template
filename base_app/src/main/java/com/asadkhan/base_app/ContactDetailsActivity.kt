package com.asadkhan.base_app

import android.os.Bundle
import androidx.navigation.findNavController
import com.asadkhan.global.base.BaseActivity

class ContactDetailsActivity : BaseActivity() {
  private val navController by lazy { findNavController(R.id.nav_host_fragment_main) }
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_my_contacts)
    navController.setGraph(R.navigation.nav_graph_my_contacts)
  }
}