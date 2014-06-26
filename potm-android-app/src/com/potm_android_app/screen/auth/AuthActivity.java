
package com.potm_android_app.screen.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.potm_android_app.R;
import com.potm_android_app.screen.main.MainActivity;
import com.potm_android_app.utils.AuthPreferences;

public class AuthActivity extends Activity {

    private static final int AUTHORIZATION_CODE = 1993;
    private static final int ACCOUNT_CODE = 1601;

    private AccountManager accountManager;
    private Button mButtonLogin;
    private Button mButtonLogout;
    private Button mButtonContinuar;

    /**
     * change this depending on the scope needed for the things you do in
     * doCoolAuthenticatedStuff()
     */
    private static final String SCOPE = "https://www.googleapis.com/auth/userinfo.email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        accountManager = AccountManager.get(this);

        View view = findViewById(R.id.buttonLogin);

        if (view instanceof Button) {
            mButtonLogin = (Button) view;

            mButtonLogin.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    chooseAccount();
                }
            });
        }

        view = findViewById(R.id.buttonLogout);

        if (view instanceof Button) {
            mButtonLogout = (Button) view;

            mButtonLogout.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    invalidateToken();
                    refreshButtons();
                }
            });
        }

        view = findViewById(R.id.buttonContinuar);

        if (view instanceof Button) {
            mButtonContinuar = (Button) view;

            mButtonContinuar.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            });
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshButtons();
    }

    private void refreshButtons() {
        if (AuthPreferences.getUser(this) == null && AuthPreferences.getToken(this) == null) {
        	mButtonContinuar.setVisibility(View.GONE);
        	mButtonLogout.setVisibility(View.GONE);
        	mButtonLogin.setVisibility(View.VISIBLE);

        } else {
        	mButtonContinuar.setVisibility(View.VISIBLE);
        	mButtonLogout.setVisibility(View.VISIBLE);
        	mButtonLogin.setVisibility(View.GONE);
        }
    }

    private void doCoolAuthenticatedStuff() {
        Log.e("AuthApp", AuthPreferences.getToken(this));
        Log.e("AuthApp", AuthPreferences.getUser(this));

        Intent intent = new Intent(AuthActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void chooseAccount() {
        // use https://github.com/frakbot/Android-AccountChooser for
        // compatibility with older devices
        Intent intent = AccountManager.newChooseAccountIntent(null, null,
                new String[] {
                    "com.google"
                }, false, null, null, null, null);
        startActivityForResult(intent, ACCOUNT_CODE);
    }

    private void requestToken() {
        Account userAccount = null;
        String user = AuthPreferences.getUser(this);
        for (Account account : accountManager.getAccountsByType("com.google")) {
            if (account.name.equals(user)) {
                userAccount = account;

                break;
            }
        }

        accountManager.getAuthToken(userAccount, "oauth2:" + SCOPE, null, this,
                new OnTokenAcquired(), null);
    }

    /**
     * call this method if your token expired, or you want to request a new
     * token for whatever reason. call requestToken() again afterwards in order
     * to get a new token.
     */
    private void invalidateToken() {
        AccountManager accountManager = AccountManager.get(this);
        accountManager.invalidateAuthToken("com.google",
                AuthPreferences.getToken(this));

        AuthPreferences.setToken(this, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == AUTHORIZATION_CODE) {
                requestToken();
            } else if (requestCode == ACCOUNT_CODE) {
                String accountName = data
                        .getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                AuthPreferences.setUser(this, accountName);

                // invalidate old tokens which might be cached. we want a fresh
                // one, which is guaranteed to work
                invalidateToken();

                requestToken();
            }
        }
    }

    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {

        @Override
        public void run(AccountManagerFuture<Bundle> result) {
            try {
                Bundle bundle = result.getResult();

                Intent launch = (Intent) bundle.get(AccountManager.KEY_INTENT);
                if (launch == null) {
                	String token = bundle
                			.getString(AccountManager.KEY_AUTHTOKEN);
                	
                	AuthPreferences.setToken(AuthActivity.this, token);
                	
                	doCoolAuthenticatedStuff();
                } else {
                	startActivityForResult(launch, AUTHORIZATION_CODE);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
