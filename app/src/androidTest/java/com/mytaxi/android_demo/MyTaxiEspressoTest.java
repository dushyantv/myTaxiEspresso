package com.mytaxi.android_demo;

import android.os.SystemClock;
import android.support.test.espresso.NoMatchingViewException;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

/**
 * @Author Dushyant Vazarkar
 * TC_Name: MyTaxiEspressoTest - Login into myTaxi, Search for Driver Name and Click on Call button
 * Created on 23/08/2018.
 */

@RunWith(AndroidJUnit4.class)
public class MyTaxiEspressoTest {

    @Before
    public void setUp() throws Exception {
        //Before Test case execution
    }

    // Allow access for Contacts while launching myTaxi application
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule=new ActivityTestRule<>(MainActivity.class);


    @Test
    public void loginToMyTaxi() {

        try {

            // Login to myTaxi application

            String userName = "crazydog335";
            String passWord = "venture";

            //Enter Username and Password

            onView(withId(R.id.edt_username)).perform(typeText(userName));
            onView(withId(R.id.edt_password)).perform(typeText(passWord));

            //Click on Login Button

            onView(withId(R.id.btn_login)).perform(click());

            //After Click on Login, Wait for 5 seconds to load Home page properly

            SystemClock.sleep(5000);

            // Search for "sa" and click on 2nd result via Name and not by Index

            String searchName = "sa";
            String clickName = "Sarah Scott";

            onView(withId(R.id.textSearch))
                    .perform(typeText(searchName)).check(matches(withText(searchName)));

            // Click on 2nd result

            MainActivity activity = mainActivityActivityTestRule.getActivity();
            onView(withText(clickName))
                    .inRoot(withDecorView(not(is(activity.getWindow().getDecorView()))))
                    .check(matches(isDisplayed()))
                    .perform(click());

            // Click on call button to call Driver

            onView(withId(R.id.fab))
                    .perform(click()).check(matches(isDisplayed()));
        }
        catch(NoMatchingViewException e) {

            e.printStackTrace();
        }
    }

    @After
    public void tearDown() throws Exception {
        //After Test case Execution
    }
}