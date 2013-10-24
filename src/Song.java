/*
 * @file: Song.java
 * @purpose: consists of song meta data
 */

import java.util.Comparator;

class Song
{
    //private data members
	private String name;
	private Metadata data;
	
	//public methods
	
	public Song(String name) {
		this.name = name;
		data = new Metadata();
	}
	
	public Song(String name, String[][] arr) {
		this.name = name;
		data = new Metadata(arr);
	}
	
	public Song(String[][] arr) {
		data = new Metadata(arr);
		
		this.name = data.get("name");
	}
	
	public Song(Metadata m) {
		this.name = m.get("name");
		this.data = m;
	}
	
	public String toString() {
		return this.name +  "\t" + data.toString();
	}
	
	public String get(String key) {
		if (key.equalsIgnoreCase("name"))
			return this.name;
		return this.data.get(key);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getYear() {
		return Integer.valueOf(this.data.get("year"));
	}
	
	public Metadata getMetaData() {
		return data;
	}
	
	public boolean isEqual(Object a){
		if (Song.class.isInstance(a)) {
			Song b = (Song) a;
			if (b.getName() == this.name &&	b.getMetaData() == this.data)
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
				return s1.get("artist").compareToIgnoreCase(s2.get("artist"));
			else if (sortBy.equalsIgnoreCase("album"))
				return s1.get("album").compareToIgnoreCase(s2.get("album"));
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
