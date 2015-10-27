package software.credible.commentslistapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.inject.Guice;
import com.google.inject.Injector;

import junit.framework.Assert;
import junit.framework.TestResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import javax.inject.Inject;

import roboguice.RoboGuice;

public class CommentsCrudTest extends ApplicationTestCase<Application> {

    @Inject
    private CommentsDataSource datasource;

    public CommentsCrudTest() {
        super(Application.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        createApplication();
        Guice.createInjector(RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(getApplication()));
        RoboGuice.getInjector(getApplication()).injectMembers(this);
        datasource.deleteAllComments();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        datasource.deleteAllComments();
        super.terminateApplication();
        RoboGuice.Util.reset();
        super.tearDown();
    }

    public void testCrudSingleComment() {
        assertEquals(0, datasource.getAllComments().size());
        Comment persistentComment = datasource.createComment("Let's get it started");
        assertEquals(1, datasource.getAllComments().size());
        datasource.deleteComment(persistentComment);
        assertEquals(0, datasource.getAllComments().size());
    }

    public void testLookupByCommentText() {
        assertEquals(0, datasource.getAllComments().size());
        for(int i = 0; i < 10; i++) {
            datasource.createComment("Comment_" + i);
        }
        Comment comment = datasource.getCommentByValue("Comment_7");
        assertNotNull(comment);
        assertEquals("Comment_7", comment.getComment());
    }
}