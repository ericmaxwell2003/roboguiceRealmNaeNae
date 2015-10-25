package software.credible.commentslistapp;

import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SimpleEspressoTest extends ActivityInstrumentationTestCase2<CommentsActivity> {

    private CommentsActivity commentsActivity;
    private int[] properNeaNeaSequence = new int[] {
      R.id.watch_me,R.id.whip,
      R.id.watch_me,R.id.nea,R.id.nea,
      R.id.watch_me, R.id.whip, R.id.whip,
      R.id.watch_me,R.id.nea,R.id.nea,
      R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop,R.id.bop
    };

    public SimpleEspressoTest() {
        super(CommentsActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        commentsActivity = getActivity();
    }

    public void testPopperSequence() {

        Assert.assertEquals(0, commentsActivity.getListAdapter().getCount()); // sanity check.

        // For each nea nea word
        for(int resourceId : properNeaNeaSequence) {

            // tap the appropriate button and make sure an item is added to the list..
            onView(withId(resourceId)).perform(click());
            Assert.assertEquals(1, commentsActivity.getListAdapter().getCount());

            // and that the comment in the ListView matches the text of the button and then long click it.
            onData(instanceOf(Comment.class))
                    .inAdapterView(allOf(withId(android.R.id.list), isDisplayed()))
                    .atPosition(0)
                    .check(matches(withText(textForButton(resourceId))))
                    .perform(longClick());

            // Verify if was deleted
            Assert.assertEquals(0, commentsActivity.getListAdapter().getCount());

        }
    }

    private String textForButton(int commentButtonId) {
        Button button = (Button) getActivity().findViewById(commentButtonId);
        return button.getText().toString();
    }
}
