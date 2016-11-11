package software.credible.naenaelistapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Lyric extends RealmObject {

    @PrimaryKey @Required private String id;

    @Required private String lyricText;

    private long sortKey;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getSortKey() {
        return sortKey;
    }

    public void setSortKey(long sortKey) {
        this.sortKey = sortKey;
    }

    public String getLyricText() {
		return lyricText;
	}

	public void setLyricText(String lyricText) {
		this.lyricText = lyricText;
	}

    @Override
    public String toString() {
        return getLyricText();
    }
}
