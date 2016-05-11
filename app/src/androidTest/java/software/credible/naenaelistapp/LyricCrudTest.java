package software.credible.naenaelistapp;

import android.test.ApplicationTestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import io.realm.Realm;

public class LyricCrudTest extends ApplicationTestCase<NaeNaeApplication> {

    private LyricDataSource datasource;
    private Realm realm;

    public LyricCrudTest() {
        super(NaeNaeApplication.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        createApplication();
        realm = Realm.getDefaultInstance();
        datasource = new LyricDataSource(realm);
        datasource.deleteAllLyrics();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        datasource.deleteAllLyrics();
        realm.close();
        super.terminateApplication();
        super.tearDown();
    }

    @Test
    public void testCrudSingleLyric() {
        assertEquals(0, datasource.getAllLyrics().size());
        Lyric persistentLyric = datasource.createLyric("Let's get it started");
        assertEquals(1, datasource.getAllLyrics().size());
        datasource.deleteLyric(persistentLyric);
        assertEquals(0, datasource.getAllLyrics().size());
    }

    @Test
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
