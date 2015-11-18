package software.credible.naenaelistapp;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.inject.Guice;

import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;

import io.realm.Realm;
import roboguice.RoboGuice;

public class LyricCrudTest extends ApplicationTestCase<Application> {

    @Inject private LyricDataSource datasource;
    @Inject private Realm realm;

    public LyricCrudTest() {
        super(Application.class);
    }

    @Before
    @Override
    public void setUp() throws Exception {
        createApplication();
        Guice.createInjector(RoboGuice.DEFAULT_STAGE, RoboGuice.newDefaultRoboModule(getApplication()));
        RoboGuice.getInjector(getApplication()).injectMembers(this);
        datasource.deleteAllLyrics();
        super.setUp();
    }

    @After
    @Override
    public void tearDown() throws Exception {
        datasource.deleteAllLyrics();
        realm.close();
        super.terminateApplication();
        RoboGuice.Util.reset();
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
