package com.mdlive.demosdk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.mdlive.mdlcore.application.MdlApplicationSupport;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSOGender;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfSSORelationship;
import com.mdlive.mdlcore.fwfrodeo.fwf.enumz.FwfState;
import com.mdlive.mdlcore.model.MdlSSODetail;
import com.mdlive.mdlcore.model.MdlUserSession;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;

/*
 * Copyright MDLive.  All rights reserved.
 */
public class MainActivity extends AppCompatActivity {

    private Button mSeeProviderButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSeeProviderButton = (Button) findViewById(R.id.enter);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }

    public void enterSDK(View v) {
        showProgressBar(true);

        Calendar birthdateCalendar = GregorianCalendar.getInstance();
        birthdateCalendar.set(1985, 3, 10);


  /*      String str = "10-04-1985";
        Date dt = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        try {
            dt = formatter.parse(str);
            birthdateCalendar.setTime(dt);
            Log.v("Emilio", "dt ==> "+dt.toString());
            Log.v("Emilio", "birthdateCalendar ==> "+birthdateCalendar.toString());
            Log.v("Emilio", "birthdateCalendar.getTime() ==> "+birthdateCalendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/

        MdlSSODetail ssoDetail = MdlSSODetail.builder()
                .ou("Cigna")
                .firstName("Bella")
                .lastName("Fraser")
                .gender(FwfSSOGender.MALE)
                .birthdate(birthdateCalendar.getTime())
                .subscriberId("11627701")
                .memberId("bellafraser|1508509088.9120774|ZDJjMDU0NDEwMzQyODZkZmE4MzQ4ZjM3MmY3NmVhNWQ3NjAzMWFlZGRmOWFjMGRjOThkOTkzODEzMzJkMmU4MQ==")
                .phone("555-555-5555")
                .email("ahadida@mdlive.com")
                .address1("address1")
                .address2("address2")
                .city("Sunrise")
                .state(FwfState.FL)
                .zipCode("33303")
                .relationship(FwfSSORelationship.SELF)
                .build();

        MdlApplicationSupport.getAuthenticationCenter()
                .singleSignOn(ssoDetail)
                .map(new Func1<MdlUserSession, Intent>() {
                    @Override
                    public Intent call(MdlUserSession mdlUserSession) {
                        return MdlApplicationSupport.getIntentFactory().ssoDashboard(MainActivity.this);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Action1<Intent>() {
                            @Override
                            public void call(Intent intent) {
                                startActivity(intent);
                                showProgressBar(false);
                            }
                        },
                        new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                Log.e(MainActivity.class.getSimpleName(), throwable.toString());
                                showProgressBar(false);
                            }
                        }
                );
    }

    private void showProgressBar(boolean showProgressBar) {
        mSeeProviderButton.setVisibility(showProgressBar ? View.GONE : View.VISIBLE);
        mProgressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }

}
