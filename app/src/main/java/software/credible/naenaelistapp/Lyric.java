package software.credible.naenaelistapp;

public class Lyric {
	private long id;
	private String lyricText;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLyricText() {
		return lyricText;
	}

	public void setLyricText(String lyricText) {
		this.lyricText = lyricText;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return lyricText;
	}
}
