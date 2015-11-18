package software.credible.naenaelistapp;

import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import roboguice.RoboGuice;

public class LyricDataSource {

    @Inject
    Realm r;

    @Inject
    public LyricDataSource(Context context) {
        RoboGuice.getInjector(context).injectMembers(this);
    }

    public Lyric createLyric(String lyricString) {
        Lyric lyric = new Lyric();
        lyric.setId(UUID.randomUUID().toString());
        lyric.setLyricText(lyricString);

        r.beginTransaction();

        r.copyToRealm(lyric);

        r.commitTransaction();

        return lyric;
	}

	public void deleteLyric(Lyric lyric) {
        if(!TextUtils.isEmpty(lyric.getId())) {
            r.beginTransaction();

            r.where(Lyric.class)
                    .equalTo("id", lyric.getId())
                    .findFirst()
                    .removeFromRealm();

            r.commitTransaction();
        }
	}

    public void deleteAllLyrics() {
        r.beginTransaction();
        r.where(Lyric.class).findAll().clear();
        r.commitTransaction();
    }

	public Lyric getLyricByValue(String lyricText) {
        return r.where(Lyric.class).equalTo("lyricText", lyricText).findFirst();
	}

	public List<Lyric> getAllLyrics() {
        return r.where(Lyric.class).findAll();
	}

}
