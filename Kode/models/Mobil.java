package models;

import java.time.LocalDateTime;

public class Mobil extends Vehicle {

    public Mobil(String plate, int staffId) {
        super();
        setJenis("mobil");
    }

    public Mobil(int id, String platNomor, LocalDateTime waktuMasuk,
                 LocalDateTime waktuKeluar, long durasiMenit, double totalBayar, int petugasId) {
        super(id, platNomor, "mobil", waktuMasuk, waktuKeluar, durasiMenit, totalBayar, petugasId);
    }
}
