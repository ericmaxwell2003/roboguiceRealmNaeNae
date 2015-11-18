package software.credible.naenaelistapp;

import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class LyricDataSource {

    private Realm r;

    public LyricDataSource(Realm realm) {
        r = realm;
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
