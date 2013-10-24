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
	private String Genre;
	private boolean isBarrowable;
	private boolean permBarrow;
	private boolean isAvailable;
	private User owner;
	
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
	
	public boolean isEqual(Song a){
		if(a.getName().equalsIgnoreCase(name) && owner.isEqual(a.getOwner()))
			return true;
		else 
			return false;
	}
	
	public User getOwner() {
		return owner;
	}
	
	public void Borrow() {
		this.isBarrowable = false;
		this.isAvailable = false;
	}
	
	public void unBorrow() {
		this.isBarrowable = true;
		this.isAvailable = true;
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
>>>>>>> Stashed changes
	}
}
