package software.credible.naenaelistapp;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import junit.framework.Assert;

import org.junit.Before;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;

public class UiEspressoTest extends ActivityInstrumentationTestCase2<NaeNaeActivity> {

    private NaeNaeActivity naeNaeActivity;
    private int[] properNeaNeaSequence = new int[] {
      R.id.watch_me,R.id.whip,
      R.id.watch_me,R.id.nea,
      R.id.watch_me, R.id.whip, R.id.whip,
      R.id.watch_me,R.id.nea,
      R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop
    };

    public UiEspressoTest() {
        super(NaeNaeActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        naeNaeActivity = getActivity();
        emptyList();
    }

    public void testPopperSequence() {


        // For each nea nea word
        for(int i = 0; i < properNeaNeaSequence.length; i++) {

            int resourceId = properNeaNeaSequence[i];

            // tap the appropriate button
            onView(withId(resourceId)).perform(click());

            // and verify it's prepended to the list.
            onData(instanceOf(Lyric.class))
                    .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                    .atPosition(i)
                    .check(matches(withText(textForButton(resourceId))));
        }

        // Make sure all are in the list.
        Assert.assertEquals(properNeaNeaSequence.length, naeNaeActivity.getListAdapter().getCount());

        // Remove all of the words.
        emptyList();

        // Verify we are back to an empty list.
        Assert.assertEquals(0, naeNaeActivity.getListAdapter().getCount());

    }

    private void emptyList() {
        while(!naeNaeActivity.getListAdapter().isEmpty()) {
            onData(instanceOf(Lyric.class))
                    .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                    .atPosition(0)
                    .perform(longClick());
        }
    }

    private String textForButton(int lyricButtonId) {
        Button button = (Button) getActivity().findViewById(lyricButtonId);
        return button.getText().toString();
    }
}
