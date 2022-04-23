package com.example.cook2;

import static androidx.test.InstrumentationRegistry.getTargetContext;
import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.getIntents;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotEquals;

import android.content.ComponentName;
import android.content.Intent;
import android.os.SystemClock;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingResource;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cook2.objects.Cook;
import com.example.cook2.objects.Order;
import com.example.cook2.objects.Util;
import com.google.firebase.firestore.FirebaseFirestore;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class CookMainActivityTest extends TestCase {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static Intent intent;
    static Cook testCook;
    static {
        intent = new Intent(getApplicationContext(),CookMainActivity.class);
        testCook = Util.getCook("gfore19@gmail.comgfore", db);
        intent.putExtra("Cook", testCook);
    }
    @Rule
    public ActivityScenarioRule<CookMainActivity> CookRule = new ActivityScenarioRule<>(intent);
    ActivityScenario<CookMainActivity> scenario;
    private CookMainActivity activity = null;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        //ActivityScenario<CookMainActivity>
        //scenario = CookRule.getScenario();
        Intents.init();
    }
    @After
    public void tearDown() throws Exception {
        Intents.release();
    }


    @Test
    public void testEditMenu() {
       // Intents.init();

        onView(withId(R.id.button3)).perform(click());
        intended(hasComponent(CookUpdateMenu.class.getName()));
        //Intents.release();


    }
    @Test
    public void testCookProfileEvent() {
     //   Intents.init();

        onView(withId(R.id.button)).perform(click());
        intended(hasComponent(CookProfileActivity.class.getName())); //change if fails
       // Intents.release();

    }

    //ASSUME COOK HAS UNACCPETED ORDER IN LIST POSITION 0
    @Test
    public void testStartOrderEvent() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).perform(click());



       // String temp = onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).toString();
       // String temp = onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).;
        String id = "gfore19@gmail.comgfore7";
        onView(withId(R.id.start_button)).perform(click());
        Order order = Util.getOrder(id,db);
        String result = order.status;
        order.status = "unaccepted_cook";
        Util.setOrder(order,db);
        assertEquals("START BUTTON FAILED", result,"accepted_cook");

        //intended(hasComponent(CookOrderDetailsActivity.class.getName()));

    }
    @Test
    public void testEndOrderEvent() {
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).perform(click());



        // String temp = onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).toString();
        // String temp = onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).;
        String id = "gfore19@gmail.comgfore7";
        onView(withId(R.id.start_button)).perform(click());
        onView(withId(R.id.end_button)).perform(click());
        Order order = Util.getOrder(id,db);
        String result = order.status;
        order.status = "unaccepted_cook";
        Util.setOrder(order,db);
        assertEquals("end BUTTON FAILED", result,"finished_cook");
    }


    //tests cook changing availability
    @Test
    public void testToggleButtonEvent() {
        //scenario.onActivity(activity1 -> {}); creates error null
        //Button button = scenario.onActivity()
        boolean start = testCook.open;
        onView(withId(R.id.toggleButton)).perform(click());
        boolean end = Util.getCook(testCook.getKey(), db).open;

        assertNotEquals("changing availability failed",start,end);
    }

    //check order details without selecting order
    @Test
    public void testCookOrderDetails() {
       // Intents.init();

        onView(withId(R.id.orderDetailsButton)).perform(click());

        //intended(hasComponent(CookMainActivity.class.getName())); //change if fails
        assertTrue(getIntents().isEmpty());

        //Intents.release();
    }

    //check order details with selecting order

    //WILL NEED TO TURN OFF ANIMATIONS inside your emulator
    // https://stackoverflow.com/questions/49691207/espresso-unable-to-perform-the-button-click-using-espresso
    //MAY STILL BE CRASHING IN THIS TEST
    @Test
    public void testCookOrderDetails1() {
     //   Intents.init();
        SystemClock.sleep(1000);
        onData(anything()).inAdapterView(withId(R.id.listOrders)).atPosition(0).perform(click());

        onView(withId(R.id.orderDetailsButton)).perform(click());

        intended(hasComponent(CookOrderDetailsActivity.class.getName())); //change if fails
        //assertTrue(getIntents().isEmpty());
       // Intents.release();
    }
}