package Service;

import java.io.Serializable;

public class MovieDesc implements Serializable {

    String movieName;
    String isbn;
    String synopsis;
    private byte[] bytes;

    public MovieDesc(String movieName, String isbn, String synopsis, byte[] bytes) {
        this.movieName = movieName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.bytes = bytes;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public String toString() {
        return String.format("FilmID :"+isbn+"| Synopsis "+synopsis);
    }
}
