package software.credible.naenaelistapp;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.realm.Realm;
import roboguice.RoboGuice;

public class LyricDataSource {

    @Inject
    private Realm r;

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
                    .findAll()
                    .deleteAllFromRealm();
            r.commitTransaction();
        }
	}

    public void deleteAllLyrics() {
        r.beginTransaction();
        r.delete(Lyric.class);
        r.commitTransaction();
    }

	public Lyric getLyricByValue(String lyricText) {
        return r.where(Lyric.class).equalTo("lyricText", lyricText).findFirst();
	}

	public List<Lyric> getAllLyrics() {
        return r.where(Lyric.class).findAll();
	}

}
