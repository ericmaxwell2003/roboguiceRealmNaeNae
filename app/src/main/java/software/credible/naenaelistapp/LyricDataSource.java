package software.credible.naenaelistapp;

import android.text.TextUtils;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.Sort;

public class LyricDataSource {

    private Realm r;

    public LyricDataSource(Realm realm) {
        r = realm;
    }

    public Lyric createLyric(String lyricString) {
        Lyric lyric = new Lyric();
        lyric.setId(UUID.randomUUID().toString());
        lyric.setLyricText(lyricString);
        lyric.setSortKey(System.currentTimeMillis());

        r.beginTransaction();
        r.copyToRealm(lyric);
        r.commitTransaction();

        return lyric;
	}

	public void deleteLyric(Lyric lyric) {
        r.beginTransaction();
        r.where(Lyric.class)
                .equalTo("id", lyric.getId())
                .findAll()
                .deleteAllFromRealm();
        r.commitTransaction();
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
        return r.where(Lyric.class).findAllSorted("sortKey");
	}
}
