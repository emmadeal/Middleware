package Service;

import java.io.Serializable;
import java.math.BigInteger;

public class Bill implements Serializable {
    String movieName;
    BigInteger outrageousPrice;

    public Bill(String movieName, BigInteger outrageousPrice) {
        this.movieName = movieName;
        this.outrageousPrice = outrageousPrice;
    }

    @Override
    public String toString() {
        return String.valueOf(outrageousPrice);
    }
}
