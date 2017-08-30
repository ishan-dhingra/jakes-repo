package com.anythingintellect.jakesgit.view;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.anythingintellect.jakesgit.JakesGitApp;
import com.anythingintellect.jakesgit.R;
import com.anythingintellect.jakesgit.di.AppComponent;
import com.anythingintellect.jakesgit.di.DaggerAppComponent;
import com.anythingintellect.jakesgit.model.GitRepo;
import com.anythingintellect.jakesgit.util.MockData;
import com.anythingintellect.jakesgit.util.MockNetworkModule;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import io.realm.Realm;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by ishan.dhingra on 30/08/17.
 */

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule =
            new ActivityTestRule<>(MainActivity.class, false, false);

    @Before
    public void setup() {
        AppComponent appComponent = DaggerAppComponent.builder()
                .networkModule(new MockNetworkModule())
                .build();
        getApp().setAppComponent(appComponent);
        deleteRealm();
    }

    private void deleteRealm() {
        for (String dir : getInstrumentation().getTargetContext().getCacheDir().list()) {
            File file = new File(dir);
            file.delete();
        }
    }

    private JakesGitApp getApp() {
        return (JakesGitApp) getInstrumentation()
                .getTargetContext().getApplicationContext();
    }

    // Should render data from API
    @Test
    public void shouldRenderDataFromAPI() {

        GitRepo gitRepo = MockData.getGitRepo();
        activityTestRule.launchActivity(null);
        onView(withId(R.id.rvRepoList))
                .check(matches(hasDescendant(withText(gitRepo.getName()))));
        onView(withId(R.id.rvRepoList))
                .check(matches(hasDescendant(withText(String.valueOf(gitRepo.getDescription())))));
        onView(withId(R.id.rvRepoList)).check(matches(hasDescendant(withText(gitRepo.getLanguage()))));

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

    // Should open browser on click

}
