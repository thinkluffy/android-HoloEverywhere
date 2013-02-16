
package org.holoeverywhere.test;

import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.test.app.TestedApplication;
import org.json.JSONObject;

import android.content.Context;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

public class ApplicationTest extends ApplicationTestCase<TestedApplication> {
    public ApplicationTest() {
        super(TestedApplication.class);
    }

    @SmallTest
    public void testSystemService() {
        assertTrue(getApplication().getSystemService(Context.LAYOUT_INFLATER_SERVICE) instanceof LayoutInflater);
    }

    @MediumTest
    public void testSharedPreferencesJSON() {
        testSharedPreferencesImpl(PreferenceImpl.JSON);
    }

    @MediumTest
    public void testSharedPreferencesXML() {
        testSharedPreferencesImpl(PreferenceImpl.XML);
    }

    @Override
    protected void setUp() throws Exception {
        createApplication();
    }

    private void testSharedPreferencesImpl(PreferenceImpl impl) {
        SharedPreferences prefs = getApplication().getDefaultSharedPreferences(impl);

        prefs.edit().putBoolean("key", true).commit();
        assertEquals(true, prefs.getBoolean("key", false));

        prefs.edit().putInt("key", 100500).commit();
        assertEquals(100500, prefs.getInt("key", -1));

        prefs.edit().putString("key", "I'll be back").commit();
        assertEquals("I'll be back", prefs.getString("key", ""));

        JSONObject json = TestUtils.makeRandomJson();
        prefs.edit().putJSONObject("key", json).commit();
        assertTrue(TestUtils.equals(json, prefs.getJSONObject("key", null)));
    }
}
