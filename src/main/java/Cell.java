import java.util.ArrayList;
import java.util.List;

public class Cell {
    Integer altitude;
    Integer country;
    Integer x;
    Integer y;
    List<Integer> neighbuorCountry = new ArrayList<>();

    public Cell() {
    }

    public Cell(Integer altitude, Integer country, Integer x, Integer y) {
        this.altitude = altitude;
        this.country = country;
        this.x = x;
        this.y = y;
    }

    public void addNeighbour(Integer number) {
        if (!neighbuorCountry.contains(number) && !country.equals(number)) {
            neighbuorCountry.add(number);
        }
    }

    public Integer getNeighboursSize() {
        return neighbuorCountry.size();
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public List<Integer> getNeighbuorCountry() {
        return neighbuorCountry;
    }

    public void setNeighbuorCountry(List<Integer> neighbuorCountry) {
        this.neighbuorCountry = neighbuorCountry;
    }
}
