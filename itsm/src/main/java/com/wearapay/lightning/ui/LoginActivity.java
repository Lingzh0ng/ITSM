package com.wearapay.lightning.ui;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.wearapay.lightning.App;
import com.wearapay.lightning.R;
import com.wearapay.lightning.StatusBarCompat;
import com.wearapay.lightning.base.BaseActivity;
import com.wearapay.lightning.bean.BLoginUser;
import com.wearapay.lightning.net.ApiHelper;
import com.wearapay.lightning.net.BaseObserver;
import com.wearapay.lightning.uitls.Endecrypt;
import com.wearapay.lightning.uitls.RxBus;
import com.wearapay.lightning.uitls.event.UpdateEvent;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoaderCallbacks<Cursor> {

  /**
   * Id to identity READ_CONTACTS permission request.
   */
  private static final int REQUEST_READ_CONTACTS = 0;

  @BindView(R.id.toolbar) Toolbar toolbar;

  // UI references.
  private AutoCompleteTextView mEmailView;
  private EditText mPasswordView;
  private View mProgressView;
  private View mLoginFormView;
  private boolean isLogining = false;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);
    StatusBarCompat.compat(this, 0x20000000);
    ButterKnife.bind(this);
    // Set up the login form.
    mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
    populateAutoComplete();
    toolbar.setTitle(getString(R.string.action_sign_in));
    setSupportActionBar(toolbar);
    //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    mPasswordView = (EditText) findViewById(R.id.password);
    mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == R.id.login || id == EditorInfo.IME_NULL) {
          attemptLogin();
          return true;
        }
        return false;
      }
    });

    Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
    mEmailSignInButton.setOnClickListener(new OnClickListener() {
      @Override public void onClick(View view) {
        attemptLogin();
      }
    });

    mLoginFormView = findViewById(R.id.login_form);
    mProgressView = findViewById(R.id.login_progress);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        finish();
        break;
    }
    return super.onOptionsItemSelected(item);
  }

  private void populateAutoComplete() {
    if (!mayRequestContacts()) {
      return;
    }

    getLoaderManager().initLoader(0, null, this);
  }

  private boolean mayRequestContacts() {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
      return true;
    }
    if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
      return true;
    }
    if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
      Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
          .setAction(android.R.string.ok, new OnClickListener() {
            @Override @TargetApi(Build.VERSION_CODES.M) public void onClick(View v) {
              requestPermissions(new String[] { READ_CONTACTS }, REQUEST_READ_CONTACTS);
            }
          });
    } else {
      requestPermissions(new String[] { READ_CONTACTS }, REQUEST_READ_CONTACTS);
    }
    return false;
  }

  /**
   * Callback received when a permissions request has been completed.
   */
  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (requestCode == REQUEST_READ_CONTACTS) {
      if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        populateAutoComplete();
      }
    }
  }

  private void attemptLogin() {
    // Reset errors.
    mEmailView.setError(null);
    mPasswordView.setError(null);

    // Store values at the time of the login attempt.
    String email = mEmailView.getText().toString();
    String password = mPasswordView.getText().toString();

    boolean cancel = false;
    View focusView = null;

    // Check for a valid password, if the user entered one.
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mPasswordView.setError(getString(R.string.error_invalid_password));
      focusView = mPasswordView;
      cancel = true;
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(email)) {
      mEmailView.setError(getString(R.string.error_field_required));
      focusView = mEmailView;
      cancel = true;
    } else if (!isEmailValid(email)) {
      mEmailView.setError(getString(R.string.error_invalid_email));
      focusView = mEmailView;
      cancel = true;
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView.requestFocus();
    } else {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.
      showProgress(true);
      login(email, password);
    }
  }

  private boolean isEmailValid(String email) {
    //TODO: Replace this with your own logic
    return email.length() > 4;
  }

  private boolean isPasswordValid(String password) {
    //TODO: Replace this with your own logic
    return password.length() > 6;
  }

  private void login(final String email, String password) {
    Endecrypt test = new Endecrypt();
    String reValue = test.get3DESEncrypt(password, Endecrypt.SPKEY);
    reValue = reValue.trim().intern();
    System.out.println("3、进行3-DES加密后的内容: " + reValue);
    //String reValue2 = test.get3DESDecrypt(reValue, Endecrypt.SPKEY);
    //System.out.println("4、进行3-DES解密后的内容: " + reValue2);
    isLogining = true;
    BLoginUser loginUser = new BLoginUser();
    loginUser.setUsername(email);
    loginUser.setPassword(reValue);
    wrap(Observable.zip(ApiHelper.getInstance().login(loginUser),
        ApiHelper.getInstance().ZSCLogin(loginUser), new BiFunction<String, String, Boolean>() {
          @Override public Boolean apply(@io.reactivex.annotations.NonNull String userToken,
              @io.reactivex.annotations.NonNull String ZSCUserToken) throws Exception {
            if (!TextUtils.isEmpty(userToken) && !TextUtils.isEmpty(ZSCUserToken)) {
              ApiHelper.getInstance().storeUserId(userToken, ZSCUserToken);
              //ApiHelper.getInstance().storeZSCUserId(ZSCUserToken);
              ApiHelper.getInstance().storeEmail(email);
              return true;
            } else {
              mPasswordView.setError(getString(R.string.error_incorrect_password));
              mPasswordView.requestFocus();
              return false;
            }
          }
        })).subscribe(new BaseObserver<Boolean>(LoginActivity.this) {
      @Override public void onNext(@io.reactivex.annotations.NonNull Boolean aBoolean) {
        super.onNext(aBoolean);
        showProgress(false);
        isLogining = false;
        ApiHelper.getInstance().setLogining(false);
        if (aBoolean) {
          RxBus.getInstance().send(new UpdateEvent(true, true));
          finish();
        }
      }

      @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {
        super.onError(e);
        showProgress(false);
        isLogining = false;
        ApiHelper.getInstance().setLogining(false);
      }
    });
    //wrap(ApiHelper.getInstance().login(loginUser)).subscribe(
    //    new BaseObserver<String>(LoginActivity.this) {
    //      @Override public void onNext(@io.reactivex.annotations.NonNull String id) {
    //        super.onNext(id);
    //        showProgress(false);
    //        isLogining = false;
    //        if (!TextUtils.isEmpty(id)) {
    //          ApiHelper.getInstance().storeUserId(id);
    //          ApiHelper.getInstance().storeEmail(email);
    //          RxBus.getInstance().send(new UpdateEvent(true, true));
    //          finish();
    //        } else {
    //          mPasswordView.setError(getString(R.string.error_incorrect_password));
    //          mPasswordView.requestFocus();
    //          ApiHelper.getInstance().setLogining(false);
    //        }
    //      }
    //
    //      @Override public void onError(@io.reactivex.annotations.NonNull Throwable e) {
    //        super.onError(e);
    //        showProgress(false);
    //        isLogining = false;
    //        ApiHelper.getInstance().setLogining(false);
    //      }
    //    });
  }

  @Override public void onBackPressed() {
    if (!isLogining) {
      App.app.exitApp();
    }
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2) private void showProgress(final boolean show) {
    // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
    // for very easy animations. If available, use these APIs to fade-in
    // the progress spinner.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
      int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
      mLoginFormView.animate()
          .setDuration(shortAnimTime)
          .alpha(show ? 0 : 1)
          .setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
              mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
          });

      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mProgressView.animate()
          .setDuration(shortAnimTime)
          .alpha(show ? 1 : 0)
          .setListener(new AnimatorListenerAdapter() {
            @Override public void onAnimationEnd(Animator animation) {
              mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
          });
    } else {
      // The ViewPropertyAnimator APIs are not available, so simply show
      // and hide the relevant UI components.
      mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
      mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    }
  }

  @Override public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
    return new CursorLoader(this,
        // Retrieve data rows for the device user's 'profile' contact.
        Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
            ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

        // Select only email addresses.
        ContactsContract.Contacts.Data.MIMETYPE + " = ?", new String[] {
        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE
    },

        // Show primary email addresses first. Note that there won't be
        // a primary email address if the user hasn't specified one.
        ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
  }

  @Override public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
    List<String> emails = new ArrayList<>();
    cursor.moveToFirst();
    while (!cursor.isAfterLast()) {
      emails.add(cursor.getString(ProfileQuery.ADDRESS));
      cursor.moveToNext();
    }

    addEmailsToAutoComplete(emails);
  }

  @Override public void onLoaderReset(Loader<Cursor> cursorLoader) {

  }

  private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
    //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
    ArrayAdapter<String> adapter =
        new ArrayAdapter<>(LoginActivity.this, android.R.layout.simple_dropdown_item_1line,
            emailAddressCollection);

    mEmailView.setAdapter(adapter);
  }

  private interface ProfileQuery {
    String[] PROJECTION = {
        ContactsContract.CommonDataKinds.Email.ADDRESS,
        ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
    };

    int ADDRESS = 0;
    int IS_PRIMARY = 1;
  }
}

