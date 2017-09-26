//package com.ibm.bluemix.appid.android.sample.appid;
package com.example.rajat.mymastercardapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import com.ibm.bluemix.appid.android.api.AppID;
import com.ibm.bluemix.appid.android.api.AppIDAuthorizationManager;
import com.ibm.bluemix.appid.android.api.LoginWidget;

import com.ibm.mobilefirstplatform.clientsdk.android.core.api.BMSClient;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Request;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.Response;
import com.ibm.mobilefirstplatform.clientsdk.android.core.api.ResponseListener;

import static android.Manifest.permission.READ_CONTACTS;
/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    private final static String region = AppID.REGION_SYDNEY;

    private AppID appId;
    private AppIDAuthorizationManager appIDAuthorizationManager;
    private TokensPersistenceManager tokensPersistenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        appId = AppID.getInstance();

        appId.initialize(this, getResources().getString(R.string.authTenantId), region);

        this.appIDAuthorizationManager = new AppIDAuthorizationManager(this.appId);
        tokensPersistenceManager = new TokensPersistenceManager(this, appIDAuthorizationManager);
    }

    /**
     * Continue as guest action
     * @param v
     */
//    public void onAnonymousClick (View v) {
//        Log.d(logTag("onAnonymousClick"),"Attempting anonymous authorization");
//
//        final String storedAccessToken = tokensPersistenceManager.getStoredAnonymousAccessToken();
//        AppIdSampleAuthorizationListener appIdSampleAuthorizationListener =
//                new AppIdSampleAuthorizationListener(this, appIDAuthorizationManager, true);
//
//        appId.loginAnonymously(getApplicationContext(), storedAccessToken, appIdSampleAuthorizationListener);
//    }

    /**
     * Log in with identity provider authentication action
     * @param v
     */
    public void onLoginClick(View v) {
        Log.d(logTag("onLoginClick"), "Attempting identified authorization");
        LoginWidget loginWidget = appId.getLoginWidget();
        final String storedAccessToken;
        storedAccessToken = tokensPersistenceManager.getStoredAccessToken();

        AppIdSampleAuthorizationListener appIdSampleAuthorizationListener =
                new AppIdSampleAuthorizationListener(this, appIDAuthorizationManager, false);

        loginWidget.launch(this, appIdSampleAuthorizationListener, storedAccessToken);
    }

    private String logTag(String methodName){
        return getClass().getCanonicalName() + "." + methodName;
    }
}