package software.credible.commentslistapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import junit.framework.Assert;
import junit.framework.TestResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CommentsCrudTest extends ApplicationTestCase<Application> {

    private CommentsDataSource datasource;

    public CommentsCrudTest() {
        super(Application.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        createApplication();
        datasource = new CommentsDataSource(getContext());
        datasource.open();
        datasource.deleteAllComments();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        datasource.deleteAllComments();
        datasource.close();
        super.terminateApplication();
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