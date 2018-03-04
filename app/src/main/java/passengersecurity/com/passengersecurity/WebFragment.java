package passengersecurity.com.passengersecurity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebFragment extends Fragment {
    WebView wb;
    ProgressDialog progressDialog;
    String url;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_web_fragment, null);
        wb = view.findViewById(R.id.webview);
        wb.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        wb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        wb.setWebViewClient(new myWebClient());
        wb.getSettings().setJavaScriptEnabled(true);
        if (HomeScreen.urlid == 3)
            url = "http://www.coms.indianrailways.gov.in/criscm/common/complaint_search.seam;jsessionid=8EDB483F3F6B7EF67877EA0EFEF61C20.cmsapp3ins3";
        else if (HomeScreen.urlid == 2)
            url = "http://www.coms.indianrailways.gov.in/criscm/common/complaint_search.seam";
        else
            url = "http://www.coms.indianrailways.gov.in/criscm/common/complaint_registration.seam;jsessionid=8EDB483F3F6B7EF67877EA0EFEF61C20.cmsapp3ins3";
        wb.loadUrl(url);
        return view;
    }

    public class myWebClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Please Wait..");
            progressDialog.show();
            progressDialog.setCancelable(true);

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }

    }

}
