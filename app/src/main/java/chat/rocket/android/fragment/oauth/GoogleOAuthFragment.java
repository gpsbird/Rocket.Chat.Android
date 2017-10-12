package chat.rocket.android.fragment.oauth;

import android.webkit.WebView;

import chat.rocket.core.models.LoginServiceConfiguration;
import okhttp3.HttpUrl;

public class GoogleOAuthFragment extends AbstractOAuthFragment {

  @Override
  protected String getOAuthServiceName() {
    return "google";
  }

  @Override
  protected String generateURL(LoginServiceConfiguration oauthConfig) {
    return new HttpUrl.Builder().scheme("https")
        .host("accounts.google.com")
        .addPathSegment("o")
        .addPathSegment("oauth2")
        .addPathSegment("auth")
        .addQueryParameter("response_type", "code")
        .addQueryParameter("client_id", oauthConfig.getKey())
        .addQueryParameter("scope", "profile email")
        .addQueryParameter("redirect_uri", "https://" + hostname + "/_oauth/google?close")
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
