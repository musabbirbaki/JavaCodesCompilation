package dbexam;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Year {//now a table name

    @Id
    private int year;//column names
    private int bedFlats1;
    private int bedFlats2;
    private int bedHouses2;
    private int bedHouses3;
    private int bedHouses4;

    public Year(int year, int bedFlats1, int bedFlats2, int bedHouses2, int bedHouses3, int bedHouses4) {
        this.year = year;
        this.bedFlats1 = bedFlats1;
        this.bedFlats2 = bedFlats2;
        this.bedHouses2 = bedHouses2;
        this.bedHouses3 = bedHouses3;
        this.bedHouses4 = bedHouses4;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getBedFlats1() {
        return bedFlats1;
    }

    public void setBedFlats1(int bedFlats1) {
        this.bedFlats1 = bedFlats1;
    }

    public int getBedFlats2() {
        return bedFlats2;
    }

    public void setBedFlats2(int bedFlats2) {
        this.bedFlats2 = bedFlats2;
    }

    public int getBedHouses2() {
        return bedHouses2;
    }

    public void setBedHouses2(int bedHouses2) {
        this.bedHouses2 = bedHouses2;
    }

    public int getBedHouses3() {
        return bedHouses3;
    }

    public void setBedHouses3(int bedHouses3) {
        this.bedHouses3 = bedHouses3;
    }

    public int getBedHouses4() {
        return bedHouses4;
    }

    public void setBedHouses4(int bedHouses4) {
        this.bedHouses4 = bedHouses4;
    }
}
