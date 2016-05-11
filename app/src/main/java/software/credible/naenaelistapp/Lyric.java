package software.credible.naenaelistapp;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Lyric extends RealmObject {

    @PrimaryKey
    @Required
    private String id;

    @Required
    private String lyricText;

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

    public void myCustomMethod() {
        System.out.println("Test");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Lyric lyric = (Lyric) o;

        if (!id.equals(lyric.id)) return false;
        return lyricText.equals(lyric.lyricText);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + lyricText.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return getLyricText();
    }
}
