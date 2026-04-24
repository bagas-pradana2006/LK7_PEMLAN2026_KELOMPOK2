import java.io.*;

public class FileHandler {
    static String fileName = "siswa.csv";

    public static int readData(String[][] data) {
        int i = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {
                String[] temp = line.split(",");
                data[i][0] = temp[0];
                data[i][1] = temp[1];
                data[i][2] = temp[2];
                i++;
            }
            br.close();
        } catch (Exception e) {
            System.out.println("File tidak ditemukan, akan dibuat baru.");
        }
        return i;
    }

    public static void writeAll(String[][] data, int jumlah) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < jumlah; i++) {
                bw.write(data[i][0] + "," + data[i][1] + "," + data[i][2]);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}