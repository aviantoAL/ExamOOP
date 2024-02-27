import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Staff> karyawanList = new ArrayList<>();

    public static void main(String[] args) {
        loadDataFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            displayMenu();
            System.out.print("Pilih Menu (1-5, 0 untuk keluar): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    inputKaryawan(scanner);
                    break;
                case 2:
                    editDataKaryawan(scanner);
                    break;
                case 3:
                    absensiKaryawan(scanner);
                    break;
                case 4:
                    hitungTotalGaji();
                    break;
                case 5:
                    tampilkanLaporan(scanner);
                    break;
                case 0:
                    System.out.println("Program selesai.");
                    break;
                default:
                    System.out.println("Pilihan tidak valid.");
            }
        } while (choice != 0);

        saveDataToFile();
        generateAllKaryawanFile();
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nMENU");
        System.out.println("1. Input Data Karyawan");
        System.out.println("2. Edit Data Karyawan");
        System.out.println("3. Absensi Karyawan");
        System.out.println("4. Hitung Total Gaji Karyawan");
        System.out.println("5. Tampilkan Laporan Karyawan");
        System.out.println("0. Keluar");
    }

    private static void inputKaryawan(Scanner scanner) {
        System.out.print("Masukkan ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character
        System.out.print("Masukkan Nama: ");
        String nama = scanner.nextLine();
        System.out.print("Masukkan Gaji Pokok: ");
        double gajiPokok = scanner.nextDouble();
        System.out.print("Masukkan Jumlah Absensi: ");
        int jmlAbsensi = scanner.nextInt();
        System.out.print("Masukkan Jumlah Izin: ");
        int jmlIzin = scanner.nextInt();

        karyawanList.add(new Staff(id, nama, gajiPokok, jmlAbsensi, jmlIzin));
        System.out.println("Data Karyawan berhasil ditambahkan!");
    }

    private static void editDataKaryawan(Scanner scanner) {
        System.out.print("Masukkan ID Karyawan yang ingin diedit: ");
        int idToEdit = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        for (Staff karyawan : karyawanList) {
            if (karyawan.ID == idToEdit) {
                displayEditMenu();
                int editChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character

                switch (editChoice) {
                    case 1:
                        System.out.print("Masukkan Nama baru: ");
                        karyawan.Nama = scanner.nextLine();
                        break;
                    case 2:
                        System.out.print("Masukkan ID baru: ");
                        karyawan.ID = scanner.nextInt();
                        break;
                    case 3:
                        System.out.print("Masukkan Gaji Pokok baru: ");
                        karyawan.GajiPokok = scanner.nextDouble();
                        break;
                    default:
                        System.out.println("Pilihan tidak valid.");
                }

                System.out.println("Data Karyawan berhasil diubah!");
                return;
            }
        }

        System.out.println("Karyawan dengan ID " + idToEdit + " tidak ditemukan.");
    }

    private static void displayEditMenu() {
        System.out.println("MENU EDIT DATA KARYAWAN");
        System.out.println("1. Ubah Nama");
        System.out.println("2. Ubah ID");
        System.out.println("3. Ubah Gaji Pokok");
        System.out.print("Pilih Menu Edit (1-3): ");
    }

    private static void absensiKaryawan(Scanner scanner) {
        System.out.print("Masukkan ID Karyawan: ");
        int id = scanner.nextInt();

        for (Staff karyawan : karyawanList) {
            if (karyawan.ID == id) {
                karyawan.prosesAbsensi();
                System.out.println("Absensi berhasil ditambahkan!");
                return;
            }
        }

        System.out.println("Karyawan dengan ID " + id + " tidak ditemukan.");
    }

    private static void hitungTotalGaji() {
        for (Staff karyawan : karyawanList) {
            double totalGajiBulanan = karyawan.GajiPokok + (10_000 * karyawan.JmlAbsensi) + (20_000 * karyawan.JmlAbsensi);
            System.out.println("ID: " + karyawan.ID);
            System.out.println("Nama: " + karyawan.Nama);
            System.out.println("Total Gaji Bulanan: " + totalGajiBulanan);
            System.out.println();
        }
    }

    private static void tampilkanLaporan(Scanner scanner) {
        System.out.print("Masukkan ID atau Nama Karyawan: ");
        String input = scanner.next();

        for (Staff karyawan : karyawanList) {
            if (String.valueOf(karyawan.ID).equals(input) || karyawan.Nama.equals(input)) {
                System.out.println("ID: " + karyawan.ID);
                System.out.println("Nama: " + karyawan.Nama);
                System.out.println("Absensi: " + karyawan.JmlAbsensi);
                System.out.println("Izin: " + karyawan.JmlIzin);
                System.out.println("Total Gaji: " + karyawan.TotalGaji);
                return;
            }
        }

        System.out.println("Karyawan dengan ID atau Nama " + input + " tidak ditemukan.");
    }

    private static void loadDataFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("Karyawan.txt"))) {
            karyawanList = (ArrayList<Staff>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Gagal membaca data dari file. Mulai dengan daftar kosong.");
        }
    }

    private static void saveDataToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Karyawan.txt"))) {
            oos.writeObject(karyawanList);
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data ke file.");
        }
    }

    private static void generateAllKaryawanFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("All_Karyawan.txt"))) {
            writer.println("ID,Nama,TotalGaji");

            for (Staff karyawan : karyawanList) {
                writer.println(karyawan.toString());
            }

            System.out.println("File All_Karyawan.txt berhasil dibuat.");
        } catch (IOException e) {
            System.out.println("Gagal membuat file All_Karyawan.txt.");
        }
    }
}
