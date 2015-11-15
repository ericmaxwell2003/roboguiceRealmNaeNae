package software.credible.naenaelistapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Lyric extends RealmObject {

    @PrimaryKey private String id;
    @Required   private String lyricText;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLyricText() {
		return lyricText;
	}

	public void setLyricText(String lyricText) {
		this.lyricText = lyricText;
	}

}
