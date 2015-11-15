package software.credible.naenaelistapp;

import android.content.Context;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;

public class LyricDataSource {

    private Context context;

    public LyricDataSource(Context context) {
        this.context = context;
    }

    public Lyric createLyric(String lyricString) {
        Lyric lyric = new Lyric();
        lyric.setId(UUID.randomUUID().toString());
        lyric.setLyricText(lyricString);
		try(Realm r = Realm.getInstance(context)) {
            r.beginTransaction();
            r.copyToRealm(lyric);
            r.commitTransaction();
            lyric = detachLyric(lyric);
        } catch (Throwable t) {
            throw new RuntimeException("Cannot save lyric", t);
        }
        return lyric;
	}

	public void deleteLyric(Lyric lyric) {
        if(!TextUtils.isEmpty(lyric.getId())) {
            try(Realm r = Realm.getInstance(context)) {
                r.beginTransaction();
                r.where(Lyric.class)
                        .equalTo("id", lyric.getId())
                        .findFirst()
                        .removeFromRealm();
                r.commitTransaction();
            }
        }
	}

    public void deleteAllLyrics() {
        try (Realm r = Realm.getInstance(context)) {
            r.beginTransaction();
            r.where(Lyric.class).findAll().clear();
            r.commitTransaction();
        }
    }

	public Lyric getLyricByValue(String lyricText) {
        Lyric result = null;
        try (Realm r = Realm.getInstance(context)) {
            result = detachLyric(r.where(Lyric.class).equalTo("lyricText", lyricText).findFirst());
        }
        return result;
	}

	public List<Lyric> getAllLyrics() {
        List<Lyric> result = new ArrayList<>();
        try (Realm r = Realm.getInstance(context)) {
            for(Lyric c : r.where(Lyric.class).findAll()) {
                result.add(detachLyric(c));
            }
        }
        return result;
	}

    private Lyric detachLyric(Lyric lyric) {
        if(lyric == null) {
            return null;
        }

        Lyric detatchedLyric = new Lyric(){
            @Override
            public String toString() {
                return getLyricText();
            }
        };
        detatchedLyric.setId(lyric.getId());
        detatchedLyric.setLyricText(lyric.getLyricText());
        return detatchedLyric;
    }

}
