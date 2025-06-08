package models;

import java.time.LocalDateTime;

public class Motor extends Vehicle {

    public Motor(String plate, int staffId) {
        super();
        setJenis("motor");
    }

    public Motor(int id, String platNomor, LocalDateTime waktuMasuk,
                 LocalDateTime waktuKeluar, long durasiMenit, double totalBayar, int petugasId) {
        super(id, platNomor, "motor", waktuMasuk, waktuKeluar, durasiMenit, totalBayar, petugasId);
    }
}
