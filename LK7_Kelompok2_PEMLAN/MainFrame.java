import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;

public class MainFrame extends JFrame {
    JTextField txtNIS, txtNama, txtAlamat;
    JTable table;
    DefaultTableModel model;

    String[][] data = new String[100][3];
    int jumlahData = 0;

    public MainFrame() {
        setTitle("Data Siswa SMP Nusa 1");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel l1 = new JLabel("NIS");
        JLabel l2 = new JLabel("Nama");
        JLabel l3 = new JLabel("Alamat");

        l1.setBounds(20, 20, 80, 25);
        l2.setBounds(20, 50, 80, 25);
        l3.setBounds(20, 80, 80, 25);

        txtNIS = new JTextField();
        txtNama = new JTextField();
        txtAlamat = new JTextField();

        txtNIS.setBounds(100, 20, 150, 25);
        txtNama.setBounds(100, 50, 150, 25);
        txtAlamat.setBounds(100, 80, 150, 25);

        JButton btnTambah = new JButton("Tambah");
        JButton btnUpdate = new JButton("Update");
        JButton btnDelete = new JButton("Delete");

        btnTambah.setBounds(300, 20, 100, 25);
        btnUpdate.setBounds(300, 50, 100, 25);
        btnDelete.setBounds(300, 80, 100, 25);

        model = new DefaultTableModel(new String[]{"NIS","Nama","Alamat"}, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 120, 540, 200);

        add(l1); add(l2); add(l3);
        add(txtNIS); add(txtNama); add(txtAlamat);
        add(btnTambah); add(btnUpdate); add(btnDelete);
        add(sp);

        loadData();

        btnTambah.addActionListener(e -> {
            String nis = txtNIS.getText();

            if (cekNIS(nis)) {
                JOptionPane.showMessageDialog(null, "NIS sudah ada!");
                return;
            }

            data[jumlahData][0] = nis;
            data[jumlahData][1] = txtNama.getText();
            data[jumlahData][2] = txtAlamat.getText();
            jumlahData++;

            FileHandler.writeAll(data, jumlahData);
            refreshTable();
        });

        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                data[row][0] = txtNIS.getText();
                data[row][1] = txtNama.getText();
                data[row][2] = txtAlamat.getText();

                FileHandler.writeAll(data, jumlahData);
                refreshTable();
            }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                for (int i = row; i < jumlahData - 1; i++) {
                    data[i] = data[i + 1];
                }
                jumlahData--;

                FileHandler.writeAll(data, jumlahData);
                refreshTable();
            }
        });

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtNIS.setText(data[row][0]);
                txtNama.setText(data[row][1]);
                txtAlamat.setText(data[row][2]);
            }
        });

        setVisible(true);
    }

    void loadData() {
        jumlahData = FileHandler.readData(data);
        refreshTable();
    }

    void refreshTable() {
        model.setRowCount(0);
        for (int i = 0; i < jumlahData; i++) {
            model.addRow(new Object[]{
                data[i][0], data[i][1], data[i][2]
            });
        }
    }

    boolean cekNIS(String nis) {
        for (int i = 0; i < jumlahData; i++) {
            if (data[i][0].equals(nis)) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        new MainFrame();
    }
}