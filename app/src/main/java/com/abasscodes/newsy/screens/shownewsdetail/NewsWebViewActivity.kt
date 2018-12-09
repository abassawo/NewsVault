package com.abasscodes.newsy.screens.shownewsdetail

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.view.MenuItemCompat
import android.support.v7.widget.ShareActionProvider
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.abasscodes.newsy.R
import com.abasscodes.newsy.base.BaseActivity
import com.abasscodes.newsy.models.NewsApiResponse
import com.abasscodes.newsy.models.NytResponse
import com.abasscodes.newsy.settings.SharePreferencesManager
import com.abasscodes.newsy.utils.ContentUtil
import kotlinx.android.synthetic.main.activity_web_screen.appbar
import kotlinx.android.synthetic.main.activity_web_screen.progressBar
import kotlinx.android.synthetic.main.activity_web_screen.swipeRefreshLayout
import kotlinx.android.synthetic.main.activity_web_screen.toolbar
import kotlinx.android.synthetic.main.activity_web_screen.webview
import java.util.concurrent.ConcurrentHashMap

class NewsWebViewActivity : BaseActivity() {

  private val map = ConcurrentHashMap<String, Any>()
  lateinit var webViewUrl: String

  override val layoutResourceId: Int get() = R.layout.activity_web_screen

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setupWebView()
    setSupportActionBar(toolbar)
    supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    supportActionBar!!.setTitle(R.string.app_name)
    toolbar.setOnClickListener({
      SharePreferencesManager.getInstance(this@NewsWebViewActivity).toggleAdminPriv()
      val toastMsg = "Debug build " + SharePreferencesManager.getInstance(this).hasAdminPriv()
      Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
    })
    loadContent()
    swipeRefreshLayout.setOnRefreshListener({
      webview.reload()
      swipeRefreshLayout.setRefreshing(false)
    })
    initScrollListener()
  }

  private fun initScrollListener() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      webview.setOnScrollChangeListener({ _, _, scrollY, _, oldScrollY ->
        if (scrollY > oldScrollY) {
          appbar.setExpanded(false)
        } else if (scrollY == 0) {
          appbar.setExpanded(true);
        }
      })
    }
  }

  private fun loadContent() {
    if (intent.hasExtra(SELECTED_NYT_ITEM)) {
      val result = intent.getParcelableExtra<NytResponse.Result>(SELECTED_NYT_ITEM)
      map[DEFAULT_MAP_KEY] = result
      map[ORIG_CONTENT_URL] = result.url
      webViewUrl = ContentUtil.getContentUrl(this, result)
    } else if (intent.hasExtra(SELECTED_WSJ_ITEM)) {
      val article = intent.getParcelableExtra<NewsApiResponse.Article>(SELECTED_WSJ_ITEM)
      map[DEFAULT_MAP_KEY] = article
      map[ORIG_CONTENT_URL] = article.url
      webViewUrl = ContentUtil.getContentUrl(this, article)
    } else {
      webViewUrl = intent.getStringExtra(SELECTED_URL)
    }
    webview.loadUrl(webViewUrl)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_detail, menu)
    val shareContentItem = menu.findItem(R.id.share_content) as MenuItem
    setupShareMenuItem(shareContentItem)
    return true
  }

  override fun onBackPressed() {
    if (webview.canGoBack()) {
      webview.goBack()
    } else {
      // Your exit alert code, or alternatively line below to finish
      super.onBackPressed() // finishes activity
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      android.R.id.home -> onBackPressed()
      R.id.save_article_option -> if (map[DEFAULT_MAP_KEY] is NytResponse.Result) {
        ContentUtil.saveResult(this, map[DEFAULT_MAP_KEY] as NytResponse.Result)
      } else {
        ContentUtil.saveArticle(this, map[DEFAULT_MAP_KEY] as NewsApiResponse.Article)
      }
      //            case R.id.open_original:
      //                webview.loadUrl(String.valueOf(map.get(ORIG_CONTENT_URL)));
      //                break;
      R.id.open_article_browser -> {
        val url = webview.getUrl()
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
      }
    }
    return true
  }

  fun setupShareMenuItem(actionItem: MenuItem) {
    val actionProvider = MenuItemCompat.getActionProvider(actionItem) as ShareActionProvider
    actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME)
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.putExtra(Intent.EXTRA_TEXT, webview.getUrl())
    shareIntent.type = "text/plain"
    actionItem.setIcon(android.R.drawable.ic_menu_share)
    actionProvider.setShareIntent(shareIntent)
    actionProvider.setOnShareTargetSelectedListener { source, intent ->
      intent.putExtra(Intent.EXTRA_SUBJECT, "Check out this article")
      false
    }
  }

  private fun setupWebView() {
    webview.getSettings().setJavaScriptEnabled(true)
    webview.setWebViewClient(WebViewClient())
    webview.setWebChromeClient(object : WebChromeClient() {
      override fun onProgressChanged(view: WebView, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        if (newProgress == 100) {
          swipeRefreshLayout.setRefreshing(false)
        }
        progressBar.visibility = if (newProgress < 50) VISIBLE else GONE
        progressBar.progress = newProgress
      }
    })
    webview.getSettings().setPluginState(WebSettings.PluginState.ON)
    webview.getSettings().setJavaScriptEnabled(true)
    webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(false)
    webview.getSettings().setSupportMultipleWindows(false)
    webview.getSettings().setSupportZoom(false)
    webview.setVerticalScrollBarEnabled(false)
    webview.setHorizontalScrollBarEnabled(false)
  }

  companion object {
    private val SELECTED_NYT_ITEM = "selectedNytItem"
    private val SELECTED_WSJ_ITEM = "selectedWsjItem"
    private val DEFAULT_MAP_KEY = "mapKey"
    private val ORIG_CONTENT_URL = "originalContent"
    private val TAG = NewsWebViewActivity::class.java.simpleName
    private val SELECTED_URL = "selectedUrl"

    fun newIntent(context: Context, url: String): Intent {
      val intent = Intent(context, NewsWebViewActivity::class.java)
      return intent.putExtra(SELECTED_URL, url)
    }

    fun newIntent(context: Context, item: NytResponse.Result): Intent {
      val intent = Intent(context, NewsWebViewActivity::class.java)
      return intent.putExtra(SELECTED_NYT_ITEM, item)
    }

    fun newIntent(context: Context, item: NewsApiResponse.Article): Intent {
      val intent = Intent(context, NewsWebViewActivity::class.java)
      return intent.putExtra(SELECTED_WSJ_ITEM, item)
    }
  }
}