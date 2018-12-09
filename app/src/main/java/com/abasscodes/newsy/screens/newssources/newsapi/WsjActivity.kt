package com.abasscodes.newsy.screens.newssources.newsapi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.abasscodes.newsy.R
import com.abasscodes.newsy.screens.newssources.newsapi.NewsApiDashboard.Companion.NEWS_API_TITLE
import com.abasscodes.newsy.settings.SharePreferencesManager
import kotlinx.android.synthetic.main.activity_fragment_container.toolbar

class WsjActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_fragment_container)
    val newsSource = intent.getSerializableExtra(NEWS_API_TITLE) as NewsApiSource
    toolbar.setTitle("News Vault - " + newsSource.name)
    toolbar.setOnClickListener({
      SharePreferencesManager.getInstance(this).toggleAdminPriv()
      val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
      Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
    })
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    val fm = supportFragmentManager
    fm.beginTransaction().replace(R.id.fragment_container, WsjFragment.newInstance(newsSource)).commit()
  }

  companion object {
    fun makeIntent(context: Context, source: NewsApiSource): Intent {
      return Intent(context, WsjActivity::class.java).putExtra(NEWS_API_TITLE, source)
    }
  }
}