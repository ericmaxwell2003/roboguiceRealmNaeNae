package software.credible.naenaelistapp;

import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class LyricDataSource {

    private Realm r;

    public LyricDataSource(Realm realm) {
        r = realm;
    }

    public Lyric createLyric(String lyricString) {
        final Lyric lyric = new Lyric();
        lyric.setId(UUID.randomUUID().toString());
        lyric.setLyricText(lyricString);
        lyric.setSortKey(System.currentTimeMillis());

        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(lyric);
            }
        });

        return lyric;
	}

	public void deleteLyric(final Lyric lyric) {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.where(Lyric.class)
                        .equalTo("id", lyric.getId())
                        .findAll()
                        .deleteAllFromRealm();
            }
        });
	}

    public void deleteAllLyrics() {
        r.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.delete(Lyric.class);
            }
        });

    }

	public Lyric getLyricByValue(String lyricText) {
        return r.where(Lyric.class).equalTo("lyricText", lyricText).findFirst();
	}

	public List<Lyric> getAllLyrics() {
        return r.where(Lyric.class).findAllSorted("sortKey");
	}
}
