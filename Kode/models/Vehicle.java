package models;

import java.time.LocalDateTime;

public class Vehicle {
    private int id;
    private String platNomor;
    private String jenis; // "motor" atau "mobil"
    private LocalDateTime waktuMasuk;
    private LocalDateTime waktuKeluar;
    private long durasiMenit;
    private double totalBayar;
    private int petugasId;

    public Vehicle() {}

    public Vehicle(int id, String platNomor, String jenis, LocalDateTime waktuMasuk,
                   LocalDateTime waktuKeluar, long durasiMenit, double totalBayar, int petugasId) {
        this.id = id;
        this.platNomor = platNomor;
        this.jenis = jenis;
        this.waktuMasuk = waktuMasuk;
        this.waktuKeluar = waktuKeluar;
        this.durasiMenit = durasiMenit;
        this.totalBayar = totalBayar;
        this.petugasId = petugasId;
    }

    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getPlatNomor() {
        return platNomor;
    }
    public void setPlatNomor(String platNomor) {
        this.platNomor = platNomor;
    }

    public String getJenis() {
        return jenis;
    }
    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public LocalDateTime getWaktuMasuk() {
        return waktuMasuk;
    }
    public void setWaktuMasuk(LocalDateTime waktuMasuk) {
        this.waktuMasuk = waktuMasuk;
    }

    public LocalDateTime getWaktuKeluar() {
        return waktuKeluar;
    }
    public void setWaktuKeluar(LocalDateTime waktuKeluar) {
        this.waktuKeluar = waktuKeluar;
    }

    public long getDurasiMenit() {
        return durasiMenit;
    }
    public void setDurasiMenit(long durasiMenit) {
        this.durasiMenit = durasiMenit;
    }

    public double getTotalBayar() {
        return totalBayar;
    }
    public void setTotalBayar(double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public int getPetugasId() {
        return petugasId;
    }
    public void setPetugasId(int petugasId) {
        this.petugasId = petugasId;
    }
}
