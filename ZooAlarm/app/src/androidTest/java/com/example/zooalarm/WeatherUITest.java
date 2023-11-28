package com.example.zooalarm;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches; // sus
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.containsString;

import static java.lang.Character.isDigit;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.zooalarm.ui.activities.WeatherActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class WeatherUITest {

    @Rule
    public ActivityScenarioRule<WeatherActivity> activityRule =
            new ActivityScenarioRule<>(WeatherActivity.class);

    @Test
    public void noCityInput() {
        onView(withId(R.id.etCity)).perform(typeText(""));
        onView(withId(R.id.btnGet)).perform(click());
        onView(withText("Hello Steve!")).check(matches(isDisplayed()));
    }

    @Test
    public void checkTokyoInput() {
        onView(withId(R.id.etCity)).perform(typeText("tokyo"));
        onView(withId(R.id.btnGet)).perform(click());
        onView(withText("Tokyo, Japan")).check(matches(isDisplayed()));
    }

    @Test
    public void checkButtonText() {
        onView(withId(R.id.btnGet)).check(matches(withText(containsString("Get Info"))));
    }


    @Test
    public void checkLongitude() {
        onView(withId(R.id.tvLongitude)).check(matches(withText(containsString("Longitude: \n"))));
        onView(withId(R.id.etCity)).perform(typeText("Tokyo"));
        onView(withId(R.id.btnGet)).perform(click());
        onView(withId(R.id.tvLongitude)).check(matches(withText(containsString(".")))); //checks if the latitude
    }


//    @Test
//    public void checkSunsetDrawingIsDisplayed() {
//        onView(withId(R.drawable.sunset)).check(matches(isDisplayed()));
//    }

}

//https://developer.android.com/training/testing/espresso/setup#java