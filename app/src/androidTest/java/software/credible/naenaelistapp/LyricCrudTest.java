package software.credible.naenaelistapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import org.junit.After;
import org.junit.Before;

public class LyricCrudTest extends ApplicationTestCase<Application> {

    private LyricDataSource datasource;

    public LyricCrudTest() {
        super(Application.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        createApplication();
        datasource = new LyricDataSource(getContext());
        datasource.open();
        datasource.deleteAllLyrics();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        datasource.deleteAllLyrics();
        datasource.close();
        super.terminateApplication();
        super.tearDown();
    }

    public void testCrudSingleLyric() {
        assertEquals(0, datasource.getAllLyrics().size());
        Lyric persistentLyric = datasource.createLyric("Let's get it started");
        assertEquals(1, datasource.getAllLyrics().size());
        datasource.deleteLyric(persistentLyric);
        assertEquals(0, datasource.getAllLyrics().size());
    }

    public void testLookupByLyricText() {
        assertEquals(0, datasource.getAllLyrics().size());
        for(int i = 0; i < 10; i++) {
            datasource.createLyric("Lyric_" + i);
        }
        Lyric lyric = datasource.getLyricByValue("Lyric_7");
        assertNotNull(lyric);
        assertEquals("Lyric_7", lyric.getLyricText());
    }
}