/*
 * @file: Song.java
 * @purpose: consists of song meta data
 */

import java.util.Comparator;

class Song
{
    //private data members
	private String name;
	private String artist;
	private String album;
	private int year;
	private String composer;
	private String genre;
	
	//public methods
	public String getName() {
		return name;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public String getAlbum() {
		return album;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getComposer() {
		return composer;
	}
	
	public String getGenre() {
		return genre;
	}
	
	public boolean isEqual(Object a){
		if (Song.class.isInstance(a)) {
			Song b = (Song) a;
			if (
					b.getName() == this.name &&
					b.getAlbum() == this.album &&
					b.getArtist() == this.artist
				)
				return true;
		}
		return false;
	}
	
	static class SongComparator implements Comparator<Song> {
		public String sortBy;
		
		public SongComparator(String query) {
			this.sortBy = query;
		}
		
		public int compare(Song s1, Song s2) {
			if (sortBy.equalsIgnoreCase("name"))
				return s1.getName().compareToIgnoreCase(s2.getName());
			else if (sortBy.equalsIgnoreCase("artist"))
				return s1.getArtist().compareToIgnoreCase(s2.getArtist());
			else if (sortBy.equalsIgnoreCase("album"))
				return s1.getAlbum().compareToIgnoreCase(s2.getAlbum());
			else
				return 0;
		}
		
		public boolean equals(Object o) {
			if (SongComparator.class.isInstance(o))
				return (((SongComparator) o).sortBy.equals(this.sortBy));
			return false;
		}
	}
}
