package com.example.zooalarm.ui.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches; // sus
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.allOf;

import static java.lang.Character.isDigit;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.zooalarm.ui.activities.MainActivity;
import com.example.zooalarm.ui.activities.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.GrantPermissionRule;

import com.example.zooalarm.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import kotlin.jvm.JvmField;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherUITest {

    @Rule
    public ActivityScenarioRule<WeatherActivity> activityRule =
            new ActivityScenarioRule<>(WeatherActivity.class);


//    @Rule
//    @JvmField
//    public ActivityScenarioRule<MainActivity> activityRule =
//            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void noCityInput() {
//        onView(withId(R.id.weather_button)).perform(click());
        onView(withId(R.id.etCity)).perform(typeText(""));
        onView(withId(R.id.btnGet)).perform(click());
        onView(withText("City field can not be empty!")).check(matches(isDisplayed())); //tvResult
    }

    @Test
    public void checkTokyoInput() throws InterruptedException {
//        onView(withId(R.id.weather_button)).perform(click());
//        onView(withId(R.id.etCity)).perform(replaceText("tokyo"));
        onView(withId(R.id.etCity)).perform(typeText("tokyo"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnGet)).perform(click());
        Thread.sleep(2000); //wait for 2 seconds while app gets data i thnk
//        onView(withText("Tokyo, Japan")).check(matches(isDisplayed()));
//        onView(withId(R.id.tvLocation)).check(matches(withText(containsString("Tokyo, Japan"))));
//        onView(withId(R.id.tvLocation)).check(matches(withText("Tokyo, Japan")));
        onView(withText("Tokyo, Japan")).check(matches(isDisplayed())); //tvResult

    }
    @Test
    public void checkTokyoInputER() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etCity),
                        childAtPosition(
                                allOf(withId(R.id.addressContainer),
                                        childAtPosition(
                                                withId(R.id.mainContainer),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("tokyo"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnGet), withText("Get Info"),
                        childAtPosition(
                                allOf(withId(R.id.addressContainer),
                                        childAtPosition(
                                                withId(R.id.mainContainer),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        try {
            Thread.sleep(2000); //wait for 2 seconds while app gets data
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        ViewInteraction textView = onView(
                allOf(withId(R.id.tvLocation), withText("Tokyo, Japan"),
                        withParent(allOf(withId(R.id.addressContainer),
                                withParent(withId(R.id.mainContainer)))),
                        isDisplayed()));
        textView.check(matches(withText("Tokyo, Japan")));
    }

    @Test
    public void checkOriginalTextRendering() {
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tvResult), withText("Please Enter The Information"),
                        withParent(allOf(withId(R.id.addressContainer),
                                withParent(withId(R.id.mainContainer)))),
                        isDisplayed()));
        textView2.check(matches(withText("Please Enter The Information")));
    }


    @Test
    public void checkButtonText() {
        onView(withId(R.id.btnGet)).check(matches(withText(containsString("Get Info"))));
    }


    @Test
    public void checkTypoColumbusOhioLongitudeLatitude() {
        onView(withId(R.id.etCity)).perform(typeText("toky"));
//        Espresso.closeSoftKeyboard();
        onView(withId(R.id.btnGet)).perform(click());
        try {
            Thread.sleep(2000); //wait for 2 seconds while app gets data i thnk
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ////////////////////// data loads in ///////////////////////////

        onView(withText("Columbus, OH, USA")).check(matches(isDisplayed())); //tvResult
        onView(withId(R.id.tvResult)).check(matches(withText("The Weather")));
        onView(withId(R.id.tvLongitude)).check(matches(withText("Longitude: \n-82.99879419999999")));
        onView(withId(R.id.tvLatitude)).check(matches(withText("Latitude: \n39.9611755")));
    }
    @Test
    public void checkTokyoJapanLongitudeLatitude() {

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.etCity),
                        childAtPosition(
                                allOf(withId(R.id.addressContainer),
                                        childAtPosition(
                                                withId(R.id.mainContainer),
                                                0)),
                                2),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("tokyo"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.btnGet), withText("Get Info"),
                        childAtPosition(
                                allOf(withId(R.id.addressContainer),
                                        childAtPosition(
                                                withId(R.id.mainContainer),
                                                0)),
                                3),
                        isDisplayed()));
        materialButton2.perform(click());

        try {
            Thread.sleep(2000); //wait for 2 seconds while app gets data
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        ////////////////////////////////// data loads in //////////////////////////////


        ViewInteraction textView = onView(
                allOf(withId(R.id.tvLocation), withText("Tokyo, Japan"),
                        withParent(allOf(withId(R.id.addressContainer),
                                withParent(withId(R.id.mainContainer)))),
                        isDisplayed()));
        textView.check(matches(withText("Tokyo, Japan")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tvResult), withText("The Weather"),
                        withParent(allOf(withId(R.id.addressContainer),
                                withParent(withId(R.id.mainContainer)))),
                        isDisplayed()));
        textView2.check(matches(withText("The Weather")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.tvLongitude), withText("Longitude: \n139.650027"),
                        withParent(withParent(withId(R.id.addressContainer))),
                        isDisplayed()));
        textView3.check(matches(withText("Longitude: \n139.650027")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.tvLatitude), withText("Latitude: \n35.6764225"),
                        withParent(withParent(withId(R.id.addressContainer))),
                        isDisplayed()));
        textView4.check(matches(withText("Latitude: \n35.6764225")));

    }

    @Test
    public void checkTheSixFields() {

        ViewInteraction textView6 = onView(
                allOf(withText("Sunrise"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView6.check(matches(withText("Sunrise")));

        ViewInteraction textView7 = onView(
                allOf(withText("Sunset"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView7.check(matches(withText("Sunset")));

        ViewInteraction textView8 = onView(
                allOf(withText("Rain Sum"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView8.check(matches(withText("Rain Sum")));

        ViewInteraction textView9 = onView(
                allOf(withText("Max Temp"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView9.check(matches(withText("Max Temp")));

        ViewInteraction textView10 = onView(
                allOf(withText("Min Temp"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView10.check(matches(withText("Min Temp")));

        ViewInteraction textView11 = onView(
                allOf(withText("Snow Fall Sum"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView11.check(matches(withText("Snow Fall Sum")));

        ViewInteraction textView12 = onView(
                allOf(withText("Snow Fall Sum"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.LinearLayout.class))),
                        isDisplayed()));
        textView12.check(matches(withText("Snow Fall Sum")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }


}

//https://developer.android.com/training/testing/espresso/setup#java