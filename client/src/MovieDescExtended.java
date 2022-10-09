import Service.MovieDesc;

public class MovieDescExtended extends MovieDesc {
    byte[] teaser;

    public MovieDescExtended(String movieName, String isbn, String synopsis, byte[] bytes, byte[] teaser) {
        super(movieName, isbn, synopsis, bytes);
        this.teaser = teaser;
    }

    @Override
    public String toString() {
        return String.format("FilmID: " + super.getIsbn() + "| Synopsis: "+super.getSynopsis() + "| Teaser: " + teaser);
    }
}