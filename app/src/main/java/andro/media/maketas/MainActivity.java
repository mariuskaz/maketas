package andro.media.maketas;

import android.annotation.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.webkit.*; 

public class MainActivity extends Activity {
	
	WebView webView;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		webView = findViewById(R.id.webView);
		webView.setWebViewClient(new WebViewClient());
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setWebChromeClient(new WebChromeClient());
		webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {
					if (url.startsWith("tel:") || url.startsWith("mailto:")) {
						Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(url));
						startActivity(intent);
						return true;
					}
					
					return false;
				}
			}
		); 
		webView.getSettings().setUserAgentString("Chrome");	
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setDatabaseEnabled(true);
		webView.getSettings().setDomStorageEnabled(true);	
	    webView.loadUrl("file:///android_asset/www/index.html");
		
		webView.setDownloadListener(new DownloadListener() {
				public void onDownloadStart(String url, String userAgent,
											String contentDisposition, String mimetype,
											long contentLength) {
					Intent i = new Intent(Intent.ACTION_VIEW);
					i.setData(Uri.parse(url.substring(5)));
					startActivity(i);
				}
		});
		
		
	}
	
	@Override
	public void onBackPressed() {
		if (webView.canGoBack()) {
			webView.goBack();
			return;
		}
		super.onBackPressed();
	}
	
}

