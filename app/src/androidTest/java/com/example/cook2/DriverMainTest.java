package com.example.cook2;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.getIntents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.anything;
import static org.junit.Assert.assertNotEquals;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Driver;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DriverMainTest extends TestCase {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static Intent intent;
    static Driver testDriver;
    static {
        intent = new Intent(getApplicationContext(),DriverMainActivity.class);
        testDriver = Util.getDriver("jones10@gmail.comjones", db);
        intent.putExtra("Driver", testDriver);
    }

    @Rule
    public ActivityScenarioRule<DriverMainActivity> DriverRule = new ActivityScenarioRule<>(intent);
    ActivityScenario<DriverMainActivity> scenario;
    private DriverMainActivity activity = null;


    @Before
    public void setUp() throws Exception {
        super.setUp();
        Intents.init();
    }


    @After
    public void tearDown() throws Exception {
        Intents.release();
    }


    @Test
    public void testProfileEvent() {
        onView(withId(R.id.profileButton)).perform(click());
        intended(hasComponent(DriverProfileActivity.class.getName()));
    }


    @Test
    public void testDriverOrderDetails1() {
        onView(withId(R.id.driverOrderDetailsButton)).perform(click());
        assertTrue(getIntents().isEmpty());
    }


    @Test
    public void testDriverOrderDetails2() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listMenu)).atPosition(0).perform(click());
        onView(withId(R.id.driverOrderDetailsButton)).perform(click());
        intended(hasComponent(DriverOrderDetailsActivity.class.getName()));
    }

    //=============================================================================
    //ASSUME COOK HAS UNACCPETED ORDER IN LIST POSITION 0
    @Test
    public void testStartOrderEvent() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listMenu)).atPosition(0).perform(click());
        String id = "gfore19@gmail.comgfore16";
        onView(withId(R.id.startOrder)).perform(click());
        Order order = Util.getOrder(id, db);
        String result = order.status;
        order.status = "finished_cook";
        Util.setOrder(order,db);
        assertEquals("START BUTTON FAILED", result,"accepted_driver");
    }


    @Test
    public void testEndOrderEvent() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listMenu)).atPosition(0).perform(click());
        String id = "gfore19@gmail.comgfore16";
        onView(withId(R.id.startOrder)).perform(click());
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listOrderItems)).atPosition(1).perform(click());
        onView(withId(R.id.endOrder)).perform(click());
        Order orderCompleted = Util.getCompletedOrder(id,db);
        String result = orderCompleted.status;
        assertEquals("end BUTTON FAILED", result,"accepted_customer");
    }


    @Test
    public void testNavigate() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listOrderItems)).atPosition(0).perform(click());
        onView(withId(R.id.navigateCustomer)).perform(click());
        intended(hasComponent(DriverMapsActivity.class.getName()));
    }
}
