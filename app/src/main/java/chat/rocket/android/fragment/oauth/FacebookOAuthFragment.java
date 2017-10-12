package chat.rocket.android.fragment.oauth;

import android.webkit.WebView;

import chat.rocket.core.models.LoginServiceConfiguration;
import okhttp3.HttpUrl;

public class FacebookOAuthFragment extends AbstractOAuthFragment {

  @Override
  protected String getOAuthServiceName() {
    return "facebook";
  }

  @Override
  protected String generateURL(LoginServiceConfiguration oauthConfig) {
    return new HttpUrl.Builder().scheme("https")
        .host("www.facebook.com")
        .addPathSegment("v2.2")
        .addPathSegment("dialog")
        .addPathSegment("oauth")
        .addQueryParameter("client_id", oauthConfig.getKey())
        .addQueryParameter("redirect_uri", "https://" + hostname + "/_oauth/facebook?close")
        .addQueryParameter("display", "popup")
        .addQueryParameter("scope", "email")
        .addQueryParameter("state", getStateString())
        .build()
        .toString();
  }

  @Override
  protected boolean shouldOverride(WebView webview, String url) {
    return true;
  }

  @Override
  protected boolean onHandleCallback(WebView webview, String url) {
    webview.loadUrl(url);
    return true;
  }
}
