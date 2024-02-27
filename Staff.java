import java.io.Serializable;

public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;

    int ID;
    String Nama;
    double GajiPokok;
    int JmlAbsensi;
    int JmlIzin;
    double TotalGaji;

    public Staff(int ID, String Nama, double GajiPokok, int JmlAbsensi, int JmlIzin) {
        this.ID = ID;
        this.Nama = Nama;
        this.GajiPokok = GajiPokok;
        this.JmlAbsensi = JmlAbsensi;
        this.JmlIzin = JmlIzin;
        hitungTotalGaji();
    }

    public void prosesAbsensi() {
        if (JmlAbsensi + JmlIzin < 22) {
            JmlAbsensi++;
            hitungTotalGaji();
        } else {
            System.out.println("Absensi melebihi batas (22 hari)!");
        }
    }

    private void hitungTotalGaji() {
        double gajiPerHari = GajiPokok / 22;
        double tunjanganMakan = 10000 * JmlAbsensi;
        double tunjanganTransport = 20000 * JmlAbsensi;
        TotalGaji = gajiPerHari * JmlAbsensi + tunjanganMakan + tunjanganTransport;
    }

    public String toString() {
        return String.format("%d,%s,%.0f,%d,%d", ID, Nama, TotalGaji, JmlAbsensi, JmlIzin);
    }
}
