package com.example.zooalarm;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.res.Resources;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import kotlinx.coroutines.ExperimentalCoroutinesApi;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@ExperimentalCoroutinesApi
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = getInstrumentation().getTargetContext();
        assertEquals("com.example.zooalarm", appContext.getPackageName());
    }


    @Test
    public void useAppContextAppProv() {
        // Context of the app under test.
        Context context = ApplicationProvider.getApplicationContext();
        assertEquals("com.example.zooalarm", context.getPackageName());
    }


    @Test
    public void testResources() {

//        Context testContext = getInstrumentation().getContext();
//        Resources testRes = testContext.getResources();
//
//        assertNotNull(testRes);
//        assertNotNull(testRes.getString(R.string.app_name));

        // .. test project environment
        Context testContext = getInstrumentation().getContext();
        Resources testRes = testContext.getResources();
//        InputStream ts = testRes.openRawResource(R.raw.your_res);

        assertNotNull(testRes);
    }

}