/*
  Dilarang keras menggandakan/mengcopy/menyebarkan/membajak/mendecompile 
  Software ini dalam bentuk apapun tanpa seijin pembuat software
  (Khanza.Soft Media). Bagi yang sengaja membajak softaware ini ta
  npa ijin, kami sumpahi sial 1000 turunan, miskin sampai 500 turu
  nan. Selalu mendapat kecelakaan sampai 400 turunan. Anak pertama
  nya cacat tidak punya kaki sampai 300 turunan. Susah cari jodoh
  sampai umur 50 tahun sampai 200 turunan. Ya Alloh maafkan kami 
  karena telah berdoa buruk, semua ini kami lakukan karena kami ti
  dak pernah rela karya kami dibajak tanpa ijin.
 */

package inventory;

import fungsi.WarnaTable2;
import fungsi.batasInput;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import simrskhanza.DlgCariBangsal;
import widget.Button;

/**
 *
 * @author dosen
 */
public final class DlgCariObat extends javax.swing.JDialog {
    private final DefaultTableModel tabModeobat,tabModeObatRacikan,tabModeDetailObatRacikan;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    private PreparedStatement psobat,pscarikapasitas,psobatasuransi,psstok,ps2;
    private ResultSet rsobat,carikapasitas,rsstok,rs2;
    private double x=0,y=0,embalase=Sequel.cariIsiAngka("select embalase_per_obat from set_embalase"),
                   tuslah=Sequel.cariIsiAngka("select tuslah_per_obat from set_embalase"),kenaikan=0,stokbarang=0,ttl=0,ppnobat=0;
    private int i=0,z=0,row2,r;
    private boolean[] pilih; 
    private double[] jumlah,harga,eb,ts,stok,beli,kapasitas,kandungan;
    private String[] kodebarang,namabarang,kodesatuan,letakbarang,namajenis,aturan,industri,kategori,golongan,no;
    private String kodedokter="",namadokter="",noresep="",bangsal="",bangsaldefault=Sequel.cariIsi("select kd_bangsal from set_lokasi limit 1"),tampilkan_ppnobat_ralan="";
    private DlgCariBangsal caribangsal=new DlgCariBangsal(null,false);
    public DlgBarang barang=new DlgBarang(null,false);
    public DlgAturanPakai aturanpakai=new DlgAturanPakai(null,false);
    private DlgMetodeRacik metoderacik=new DlgMetodeRacik(null,false);
    private WarnaTable2 warna=new WarnaTable2();
    private WarnaTable2 warna2=new WarnaTable2();
    private WarnaTable2 warna3=new WarnaTable2();
    private riwayatobat Trackobat=new riwayatobat();
    /** Creates new form DlgPenyakit
     * @param parent
     * @param modal */
    public DlgCariObat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocation(10,2);
        setSize(656,250);

        tabModeobat=new DefaultTableModel(null,new Object[]{
                "K","Jumlah","Kode Barang","Nama Barang","Satuan",
                "Letak Barang","Harga(Rp)","Jenis Obat","Embalase","Tuslah",
                "Stok","Aturan Pakai","I.F.","H.Beli","Kategori","Golongan"
            }){
            @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==0)||(colIndex==1)||(colIndex==8)||(colIndex==9)||(colIndex==11)) {
                    a=true;
                }
                return a;
             }
            
             Class[] types = new Class[] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class, 
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Object.class,java.lang.Double.class, java.lang.Object.class, java.lang.Object.class
             };
             
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        
        tbObat.setModel(tabModeobat);
        tbObat.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObat.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (i = 0; i < 16; i++) {
            TableColumn column = tbObat.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(45);
            }else if(i==2){
                column.setPreferredWidth(75);
            }else if(i==3){
                column.setPreferredWidth(200);
            }else if(i==4){
                column.setPreferredWidth(45);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(85);
            }else if(i==8){
                column.setPreferredWidth(60);
            }else if(i==9){
                column.setPreferredWidth(60);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(130);
            }else if(i==12){
                column.setPreferredWidth(100);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setPreferredWidth(100);
            }else if(i==15){
                column.setPreferredWidth(100);
            }                 
        }
        warna.kolom=1;
        tbObat.setDefaultRenderer(Object.class,warna);
        
        tabModeObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Nama Racikan","Kode Racik","Metode Racik","Jml.Racik",
                "Aturan Pakai","Keterangan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = true;
                if ((colIndex==0)||(colIndex==2)||(colIndex==3)) {
                    a=false;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbObatRacikan.setModel(tabModeObatRacikan);
        tbObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 7; i++) {
            TableColumn column = tbObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(250);
            }else if(i==2){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==3){
                column.setPreferredWidth(100);
            }else if(i==4){
                column.setPreferredWidth(60);
            }else if(i==5){
                column.setPreferredWidth(200);
            }else if(i==6){
                column.setPreferredWidth(250);
            }
        }

        warna2.kolom=4;
        tbObatRacikan.setDefaultRenderer(Object.class,warna2);
        
        tabModeDetailObatRacikan=new DefaultTableModel(null,new Object[]{
                "No","Kode Barang","Nama Barang","Satuan","Harga(Rp)","H.Beli",
                "Jenis Obat","Stok","Kps","Kandungan","Jml",
                "Embal","Tuslah","I.F.","Kategori","Golongan"
            }){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if ((colIndex==9)||(colIndex==10)||(colIndex==11)||(colIndex==12)||(colIndex==13)) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Object.class,
                java.lang.Double.class, java.lang.Double.class, java.lang.Double.class,
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };

        tbDetailObatRacikan.setModel(tabModeDetailObatRacikan);
        tbDetailObatRacikan.setPreferredScrollableViewportSize(new Dimension(500,500));
        tbDetailObatRacikan.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);        
        
        for (i = 0; i < 16; i++) {
            TableColumn column = tbDetailObatRacikan.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(25);
            }else if(i==1){
                column.setPreferredWidth(75);
            }else if(i==2){
                column.setPreferredWidth(200);
            }else if(i==3){
                column.setPreferredWidth(45);
            }else if(i==4){
                column.setPreferredWidth(85);
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(85);
            }else if(i==7){
                column.setPreferredWidth(40);
            }else if(i==8){
                column.setPreferredWidth(40);
            }else if(i==9){
                column.setPreferredWidth(70);
            }else if(i==10){
                column.setPreferredWidth(40);
            }else if(i==11){
                column.setPreferredWidth(50);
            }else if(i==12){
                column.setPreferredWidth(50);
            }else if(i==13){
                column.setPreferredWidth(90);
            }else if(i==14){
                column.setPreferredWidth(90);
            }else if(i==15){
                column.setPreferredWidth(90);
            }
        }

        warna3.kolom=9;
        tbDetailObatRacikan.setDefaultRenderer(Object.class,warna3);
        
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(koneksiDB.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {BtnCariActionPerformed(null);}
                @Override
                public void removeUpdate(DocumentEvent e) {BtnCariActionPerformed(null);}
                @Override
                public void changedUpdate(DocumentEvent e) {BtnCariActionPerformed(null);}
            });
        }
        
        aturanpakai.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(aturanpakai.getTable().getSelectedRow()!= -1){  
                    if(TabRawat.getSelectedIndex()==0){
                        tbObat.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObat.getSelectedRow(),11);
                        tbObat.requestFocus();
                    }else if(TabRawat.getSelectedIndex()==1){
                        tbObatRacikan.setValueAt(aturanpakai.getTable().getValueAt(aturanpakai.getTable().getSelectedRow(),0).toString(),tbObatRacikan.getSelectedRow(),5);
                        tbObatRacikan.requestFocus();
                    }                        
                }   
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        caribangsal.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(caribangsal.getTable().getSelectedRow()!= -1){                   
                    kdgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(),0).toString());
                    nmgudang.setText(caribangsal.getTable().getValueAt(caribangsal.getTable().getSelectedRow(),1).toString());
                } 
                kdgudang.requestFocus();
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        metoderacik.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(metoderacik.getTable().getSelectedRow()!= -1){  
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),1).toString(),tbObatRacikan.getSelectedRow(),2);
                    tbObatRacikan.setValueAt(metoderacik.getTable().getValueAt(metoderacik.getTable().getSelectedRow(),2).toString(),tbObatRacikan.getSelectedRow(),3);
                }  
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        metoderacik.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_SPACE){
                    metoderacik.dispose();
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        tampilkan_ppnobat_ralan=Sequel.cariIsi("select tampilkan_ppnobat_ralan from set_nota"); 
        jam();
    }    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Popup = new javax.swing.JPopupMenu();
        ppBersihkan = new javax.swing.JMenuItem();
        ppStok = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        TNoRw = new widget.TextBox();
        Tanggal = new widget.TextBox();
        Jam = new widget.TextBox();
        KdPj = new widget.TextBox();
        internalFrame1 = new widget.InternalFrame();
        panelisi3 = new widget.panelisi();
        label9 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        BtnAll = new widget.Button();
        BtnTambah = new widget.Button();
        BtnSeek5 = new widget.Button();
        BtnSimpan = new widget.Button();
        BtnTambah1 = new widget.Button();
        BtnHapus = new widget.Button();
        label13 = new widget.Label();
        BtnKeluar = new widget.Button();
        FormInput = new widget.PanelBiasa();
        jLabel5 = new widget.Label();
        LTotal = new widget.Label();
        jLabel6 = new widget.Label();
        LPpn = new widget.Label();
        jLabel7 = new widget.Label();
        LTotalTagihan = new widget.Label();
        label12 = new widget.Label();
        Jeniskelas = new widget.ComboBox();
        ChkNoResep = new widget.CekBox();
        jLabel8 = new widget.Label();
        DTPTgl = new widget.Tanggal();
        cmbJam = new widget.ComboBox();
        cmbMnt = new widget.ComboBox();
        cmbDtk = new widget.ComboBox();
        ChkJln = new widget.CekBox();
        label21 = new widget.Label();
        kdgudang = new widget.TextBox();
        nmgudang = new widget.TextBox();
        BtnGudang = new widget.Button();
        jLabel10 = new widget.Label();
        jLabel11 = new widget.Label();
        jLabel12 = new widget.Label();
        LblNamaPasien = new widget.TextBox();
        LblNoRM = new widget.TextBox();
        LblNoRawat = new widget.TextBox();
        TabRawat = new javax.swing.JTabbedPane();
        Scroll = new widget.ScrollPane();
        tbObat = new widget.Table();
        jPanel3 = new javax.swing.JPanel();
        Scroll1 = new widget.ScrollPane();
        tbObatRacikan = new widget.Table();
        Scroll2 = new widget.ScrollPane();
        tbDetailObatRacikan = new widget.Table();

        Popup.setName("Popup"); // NOI18N

        ppBersihkan.setBackground(new java.awt.Color(255, 255, 255));
        ppBersihkan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBersihkan.setForeground(new java.awt.Color(100,80,80));
        ppBersihkan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppBersihkan.setText("Bersihkan Jumlah");
        ppBersihkan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBersihkan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBersihkan.setIconTextGap(8);
        ppBersihkan.setName("ppBersihkan"); // NOI18N
        ppBersihkan.setPreferredSize(new java.awt.Dimension(200, 25));
        ppBersihkan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBersihkanActionPerformed(evt);
            }
        });
        Popup.add(ppBersihkan);

        ppStok.setBackground(new java.awt.Color(255, 255, 255));
        ppStok.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppStok.setForeground(new java.awt.Color(100,80,80));
        ppStok.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        ppStok.setText("Tampilkan Semua Stok");
        ppStok.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppStok.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppStok.setIconTextGap(8);
        ppStok.setName("ppStok"); // NOI18N
        ppStok.setPreferredSize(new java.awt.Dimension(200, 25));
        ppStok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppStokActionPerformed(evt);
            }
        });
        Popup.add(ppStok);

        Kd2.setHighlighter(null);
        Kd2.setName("Kd2"); // NOI18N
        Kd2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kd2KeyPressed(evt);
            }
        });

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N

        Tanggal.setHighlighter(null);
        Tanggal.setName("Tanggal"); // NOI18N

        Jam.setHighlighter(null);
        Jam.setName("Jam"); // NOI18N

        KdPj.setHighlighter(null);
        KdPj.setName("KdPj"); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Data Obat, Alkes & BHP Medis ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(100,80,80))); // NOI18N
        internalFrame1.setFont(new java.awt.Font("Dialog", 0, 11)); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        panelisi3.setName("panelisi3"); // NOI18N
        panelisi3.setPreferredSize(new java.awt.Dimension(100, 43));
        panelisi3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 4, 9));

        label9.setText("Key Word :");
        label9.setName("label9"); // NOI18N
        label9.setPreferredSize(new java.awt.Dimension(68, 23));
        panelisi3.add(label9);

        TCari.setToolTipText("Alt+C");
        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(340, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelisi3.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/accept.png"))); // NOI18N
        BtnCari.setMnemonic('1');
        BtnCari.setToolTipText("Alt+1");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelisi3.add(BtnCari);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('2');
        BtnAll.setToolTipText("Alt+2");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelisi3.add(BtnAll);

        BtnTambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/plus_16.png"))); // NOI18N
        BtnTambah.setMnemonic('3');
        BtnTambah.setToolTipText("Alt+3");
        BtnTambah.setName("BtnTambah"); // NOI18N
        BtnTambah.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambahActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/011.png"))); // NOI18N
        BtnSeek5.setMnemonic('4');
        BtnSeek5.setToolTipText("Alt+4");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        BtnSeek5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSeek5KeyPressed(evt);
            }
        });
        panelisi3.add(BtnSeek5);

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        panelisi3.add(BtnSimpan);

        BtnTambah1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/category.png"))); // NOI18N
        BtnTambah1.setMnemonic('3');
        BtnTambah1.setToolTipText("Alt+3");
        BtnTambah1.setName("BtnTambah1"); // NOI18N
        BtnTambah1.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnTambah1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnTambah1ActionPerformed(evt);
            }
        });
        panelisi3.add(BtnTambah1);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        panelisi3.add(BtnHapus);

        label13.setName("label13"); // NOI18N
        label13.setPreferredSize(new java.awt.Dimension(65, 23));
        panelisi3.add(label13);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('5');
        BtnKeluar.setToolTipText("Alt+5");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        panelisi3.add(BtnKeluar);

        internalFrame1.add(panelisi3, java.awt.BorderLayout.PAGE_END);

        FormInput.setBackground(new java.awt.Color(215, 225, 215));
        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(100, 103));
        FormInput.setLayout(null);

        jLabel5.setText("Total :");
        jLabel5.setName("jLabel5"); // NOI18N
        jLabel5.setPreferredSize(new java.awt.Dimension(45, 23));
        FormInput.add(jLabel5);
        jLabel5.setBounds(4, 70, 65, 23);

        LTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotal.setText("0");
        LTotal.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotal.setName("LTotal"); // NOI18N
        LTotal.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotal);
        LTotal.setBounds(72, 70, 80, 23);

        jLabel6.setText("PPN :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(35, 23));
        FormInput.add(jLabel6);
        jLabel6.setBounds(145, 70, 35, 23);

        LPpn.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LPpn.setText("0");
        LPpn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LPpn.setName("LPpn"); // NOI18N
        LPpn.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(LPpn);
        LPpn.setBounds(183, 70, 65, 23);

        jLabel7.setText("Total+PPN :");
        jLabel7.setName("jLabel7"); // NOI18N
        jLabel7.setPreferredSize(new java.awt.Dimension(65, 23));
        FormInput.add(jLabel7);
        jLabel7.setBounds(251, 70, 65, 23);

        LTotalTagihan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LTotalTagihan.setText("0");
        LTotalTagihan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LTotalTagihan.setName("LTotalTagihan"); // NOI18N
        LTotalTagihan.setPreferredSize(new java.awt.Dimension(80, 23));
        FormInput.add(LTotalTagihan);
        LTotalTagihan.setBounds(319, 70, 80, 23);

        label12.setText("Tarif :");
        label12.setName("label12"); // NOI18N
        label12.setPreferredSize(new java.awt.Dimension(50, 23));
        FormInput.add(label12);
        label12.setBounds(385, 40, 50, 23);

        Jeniskelas.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Rawat Jalan", "Beli Luar", "Karyawan", "Utama/BPJS" }));
        Jeniskelas.setName("Jeniskelas"); // NOI18N
        Jeniskelas.setPreferredSize(new java.awt.Dimension(100, 23));
        Jeniskelas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                JeniskelasItemStateChanged(evt);
            }
        });
        Jeniskelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                JeniskelasKeyPressed(evt);
            }
        });
        FormInput.add(Jeniskelas);
        Jeniskelas.setBounds(438, 40, 150, 23);

        ChkNoResep.setBorder(null);
        ChkNoResep.setSelected(true);
        ChkNoResep.setText("No.Resep   ");
        ChkNoResep.setBorderPainted(true);
        ChkNoResep.setBorderPaintedFlat(true);
        ChkNoResep.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        ChkNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkNoResep.setName("ChkNoResep"); // NOI18N
        ChkNoResep.setOpaque(false);
        ChkNoResep.setPreferredSize(new java.awt.Dimension(85, 23));
        ChkNoResep.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ChkNoResepItemStateChanged(evt);
            }
        });
        FormInput.add(ChkNoResep);
        ChkNoResep.setBounds(598, 40, 85, 23);

        jLabel8.setText("Tanggal :");
        jLabel8.setName("jLabel8"); // NOI18N
        jLabel8.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel8);
        jLabel8.setBounds(4, 40, 65, 23);

        DTPTgl.setEditable(false);
        DTPTgl.setForeground(new java.awt.Color(50, 70, 50));
        DTPTgl.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "10-10-2018" }));
        DTPTgl.setDisplayFormat("dd-MM-yyyy");
        DTPTgl.setName("DTPTgl"); // NOI18N
        DTPTgl.setOpaque(false);
        DTPTgl.setPreferredSize(new java.awt.Dimension(100, 23));
        DTPTgl.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPTglKeyPressed(evt);
            }
        });
        FormInput.add(DTPTgl);
        DTPTgl.setBounds(72, 40, 100, 23);

        cmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        cmbJam.setName("cmbJam"); // NOI18N
        cmbJam.setOpaque(false);
        cmbJam.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbJamKeyPressed(evt);
            }
        });
        FormInput.add(cmbJam);
        cmbJam.setBounds(175, 40, 50, 23);

        cmbMnt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbMnt.setName("cmbMnt"); // NOI18N
        cmbMnt.setOpaque(false);
        cmbMnt.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbMnt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbMntKeyPressed(evt);
            }
        });
        FormInput.add(cmbMnt);
        cmbMnt.setBounds(228, 40, 50, 23);

        cmbDtk.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        cmbDtk.setName("cmbDtk"); // NOI18N
        cmbDtk.setOpaque(false);
        cmbDtk.setPreferredSize(new java.awt.Dimension(50, 23));
        cmbDtk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cmbDtkKeyPressed(evt);
            }
        });
        FormInput.add(cmbDtk);
        cmbDtk.setBounds(281, 40, 50, 23);

        ChkJln.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(195, 215, 195)));
        ChkJln.setSelected(true);
        ChkJln.setBorderPainted(true);
        ChkJln.setBorderPaintedFlat(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.setPreferredSize(new java.awt.Dimension(22, 23));
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(334, 40, 22, 23);

        label21.setText("Depo :");
        label21.setName("label21"); // NOI18N
        label21.setPreferredSize(new java.awt.Dimension(70, 23));
        FormInput.add(label21);
        label21.setBounds(385, 70, 50, 23);

        kdgudang.setEditable(false);
        kdgudang.setName("kdgudang"); // NOI18N
        kdgudang.setPreferredSize(new java.awt.Dimension(80, 23));
        kdgudang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdgudangKeyPressed(evt);
            }
        });
        FormInput.add(kdgudang);
        kdgudang.setBounds(438, 70, 55, 23);

        nmgudang.setEditable(false);
        nmgudang.setName("nmgudang"); // NOI18N
        nmgudang.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(nmgudang);
        nmgudang.setBounds(495, 70, 150, 23);

        BtnGudang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/picture/190.png"))); // NOI18N
        BtnGudang.setMnemonic('2');
        BtnGudang.setToolTipText("Alt+2");
        BtnGudang.setName("BtnGudang"); // NOI18N
        BtnGudang.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnGudang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGudangActionPerformed(evt);
            }
        });
        FormInput.add(BtnGudang);
        BtnGudang.setBounds(647, 70, 28, 23);

        jLabel10.setText("No.Rawat :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel10);
        jLabel10.setBounds(4, 10, 65, 23);

        jLabel11.setText("No.RM :");
        jLabel11.setName("jLabel11"); // NOI18N
        jLabel11.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel11);
        jLabel11.setBounds(188, 10, 65, 23);

        jLabel12.setText("Nama Pasien :");
        jLabel12.setName("jLabel12"); // NOI18N
        jLabel12.setPreferredSize(new java.awt.Dimension(68, 23));
        FormInput.add(jLabel12);
        jLabel12.setBounds(355, 10, 80, 23);

        LblNamaPasien.setEditable(false);
        LblNamaPasien.setName("LblNamaPasien"); // NOI18N
        LblNamaPasien.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNamaPasien);
        LblNamaPasien.setBounds(438, 10, 237, 23);

        LblNoRM.setEditable(false);
        LblNoRM.setName("LblNoRM"); // NOI18N
        LblNoRM.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNoRM);
        LblNoRM.setBounds(256, 10, 90, 23);

        LblNoRawat.setEditable(false);
        LblNoRawat.setName("LblNoRawat"); // NOI18N
        LblNoRawat.setPreferredSize(new java.awt.Dimension(207, 23));
        FormInput.add(LblNoRawat);
        LblNoRawat.setBounds(72, 10, 123, 23);

        internalFrame1.add(FormInput, java.awt.BorderLayout.PAGE_START);

        TabRawat.setBackground(new java.awt.Color(255, 255, 253));
        TabRawat.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(239,244,234)));
        TabRawat.setForeground(new java.awt.Color(100,80,80));
        TabRawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        TabRawat.setName("TabRawat"); // NOI18N
        TabRawat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabRawatMouseClicked(evt);
            }
        });

        Scroll.setComponentPopupMenu(Popup);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbObat.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObat.setComponentPopupMenu(Popup);
        tbObat.setName("tbObat"); // NOI18N
        tbObat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbObatMouseClicked(evt);
            }
        });
        tbObat.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbObatPropertyChange(evt);
            }
        });
        tbObat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatKeyPressed(evt);
            }
        });
        Scroll.setViewportView(tbObat);

        TabRawat.addTab("Umum", Scroll);

        jPanel3.setBorder(null);
        jPanel3.setName("jPanel3"); // NOI18N
        jPanel3.setOpaque(false);
        jPanel3.setPreferredSize(new java.awt.Dimension(300, 102));
        jPanel3.setLayout(new java.awt.BorderLayout());

        Scroll1.setName("Scroll1"); // NOI18N
        Scroll1.setOpaque(true);
        Scroll1.setPreferredSize(new java.awt.Dimension(454, 90));

        tbObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbObatRacikan.setName("tbObatRacikan"); // NOI18N
        tbObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbObatRacikanKeyPressed(evt);
            }
        });
        Scroll1.setViewportView(tbObatRacikan);

        jPanel3.add(Scroll1, java.awt.BorderLayout.PAGE_START);

        Scroll2.setName("Scroll2"); // NOI18N
        Scroll2.setOpaque(true);

        tbDetailObatRacikan.setAutoCreateRowSorter(true);
        tbDetailObatRacikan.setToolTipText("Silahkan klik untuk memilih data yang mau diedit ataupun dihapus");
        tbDetailObatRacikan.setComponentPopupMenu(Popup);
        tbDetailObatRacikan.setName("tbDetailObatRacikan"); // NOI18N
        tbDetailObatRacikan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbDetailObatRacikanMouseClicked(evt);
            }
        });
        tbDetailObatRacikan.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tbDetailObatRacikanPropertyChange(evt);
            }
        });
        tbDetailObatRacikan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbDetailObatRacikanKeyPressed(evt);
            }
        });
        Scroll2.setViewportView(tbDetailObatRacikan);

        jPanel3.add(Scroll2, java.awt.BorderLayout.CENTER);

        TabRawat.addTab("Racikan", jPanel3);

        internalFrame1.add(TabRawat, java.awt.BorderLayout.CENTER);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            tbObat.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            tampilobat();
        }else if(TabRawat.getSelectedIndex()==1){
            if(tbObatRacikan.getRowCount()!=0){
                if(tbObatRacikan.getSelectedRow()!= -1){
                    if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),2).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),3).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).toString().equals("")||
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).toString().equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan lengkapi data racikan..!!");
                    }else{
                        tampildetailracikanobat();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Silahkan pilih racikan..!!");
                }
            }else{
                JOptionPane.showMessageDialog(null,"Silahkan masukkan racikan..!!");
            }
        }   
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        BtnCariActionPerformed(evt);
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnAllActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnCari, TCari);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void tbObatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbObatMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getDataobat();
            } catch (java.lang.NullPointerException e) {
            }
            
            if(evt.getClickCount()==2){
                dispose();
            }
        }
}//GEN-LAST:event_tbObatMouseClicked

    private void tbObatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatKeyPressed
        if(tbObat.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                try {
                    getDataobat();
                    i=tbObat.getSelectedColumn();
                    if(i==8){
                        try {
                            if(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase,tbObat.getSelectedRow(),8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0,tbObat.getSelectedRow(),8);
                        }
                    }else if(i==9){
                        try {
                            if(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah,tbObat.getSelectedRow(),9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0,tbObat.getSelectedRow(),9);
                        }
                            
                        TCari.setText("");
                        TCari.requestFocus();
                    }else if((i==10)||(i==3)){
                        hitungObat();
                        TCari.setText("");
                        TCari.requestFocus();
                    }else if(i==11){
                        TCari.setText("");
                        TCari.requestFocus();
                    }
                } catch (java.lang.NullPointerException e) {
                }
            }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getDataobat();
                } catch (java.lang.NullPointerException e) {
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                i=tbObat.getSelectedColumn();
                if((i==1)||(i==11)||(i==8)||(i==9)){
                    if(tbObat.getSelectedRow()!= -1){
                        tbObat.setValueAt("",tbObat.getSelectedRow(),i);
                    }
                }   
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                i=tbObat.getSelectedColumn();
                if(i!=11){
                    TCari.requestFocus();
                }                
            }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbObat.getSelectedColumn();
                if(i==2){
                    try {
                        getDataobat();
                        
                        try {
                            if(tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),8).toString().equals("0,0")) {
                                tbObat.setValueAt(embalase,tbObat.getSelectedRow(),8);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0,tbObat.getSelectedRow(),8);
                        }

                        try {
                            if(tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0.0")||tbObat.getValueAt(tbObat.getSelectedRow(),9).toString().equals("0,0")) {
                                tbObat.setValueAt(tuslah,tbObat.getSelectedRow(),9);
                            }
                        } catch (Exception e) {
                            tbObat.setValueAt(0,tbObat.getSelectedRow(),9);
                        }  
                    } catch (Exception e) {
                        tbObat.setValueAt(0,tbObat.getSelectedRow(),10);
                    }
                    hitungObat();
                }else if(i==11){
                    var.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }
            }   
        }
}//GEN-LAST:event_tbObatKeyPressed

    private void Kd2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kd2KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_Kd2KeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        dispose();
    }//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambahActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        
        //barang.setModal(true);
        barang.emptTeks();
        barang.isCek();
        barang.setSize(internalFrame1.getWidth()+40,internalFrame1.getHeight()+40);
        barang.setLocationRelativeTo(internalFrame1);
        barang.setAlwaysOnTop(false);
        barang.setVisible(true);
        this.setCursor(Cursor.getDefaultCursor());           
    }//GEN-LAST:event_BtnTambahActionPerformed

private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TCari,"Data");
        }else if(kdgudang.getText().equals("")){
            Valid.textKosong(TCari,"Lokasi");
        }else{
            int reply = JOptionPane.showConfirmDialog(rootPane,"Eeiiiiiits, udah bener belum data yang mau disimpan..??","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if (reply == JOptionPane.YES_OPTION) {
                try {  
                    ChkJln.setSelected(false);
                    koneksi.setAutoCommit(false);                  
                    for(i=0;i<tbObat.getRowCount();i++){ 
                        if(Valid.SetAngka(tbObat.getValueAt(i,1).toString())>0){                        
                            if(tbObat.getValueAt(i,0).toString().equals("true")){
                                pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                                try {
                                    pscarikapasitas.setString(1,tbObat.getValueAt(i,2).toString());
                                    carikapasitas=pscarikapasitas.executeQuery();
                                    if(carikapasitas.next()){ 
                                        if(Sequel.menyimpantf2("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?","data",12,new String[]{
                                            Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString(),
                                            tbObat.getValueAt(i,6).toString(),""+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),
                                            tbObat.getValueAt(i,8).toString(),tbObat.getValueAt(i,9).toString(),""+Valid.SetAngka2(Double.parseDouble(tbObat.getValueAt(i,8).toString())+
                                                    Double.parseDouble(tbObat.getValueAt(i,9).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*
                                                            (Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)))),"Ralan",kdgudang.getText()                          
                                        })==true){
                                            if(!tbObat.getValueAt(i,11).toString().equals("")){
                                                Sequel.menyimpan("aturan_pakai","?,?,?,?,?",5,new String[]{
                                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString()
                                                });  
                                            }                                            
                                            Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1)),"Pemberian Obat",var.getkode(),kdgudang.getText(),"Simpan");
                                            Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','-"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"'", 
                                                         "stok=stok-'"+(Double.parseDouble(tbObat.getValueAt(i,1).toString())/carikapasitas.getDouble(1))+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");   
                                        }else{
                                            JOptionPane.showMessageDialog(null,"Gagal Menyimpan, Kemungkinan ada data sama/kapasitas tidak ditemukan..!!");
                                        }  
                                    }else{
                                        if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?","data",12,new String[]{
                                            Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString(),
                                            tbObat.getValueAt(i,6).toString(),""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),
                                            tbObat.getValueAt(i,8).toString(),tbObat.getValueAt(i,9).toString(),""+(Double.parseDouble(tbObat.getValueAt(i,8).toString())+
                                                    Double.parseDouble(tbObat.getValueAt(i,9).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*
                                                            Double.parseDouble(tbObat.getValueAt(i,1).toString()))),"Ralan",kdgudang.getText()
                                        })==true){
                                            if(!tbObat.getValueAt(i,11).toString().equals("")){
                                                Sequel.menyimpan("aturan_pakai","?,?,?,?,?",5,new String[]{
                                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString()
                                                });  
                                            }                                              
                                            Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",var.getkode(),kdgudang.getText(),"Simpan");
                                            Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'", 
                                                         "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");   
                                        }                                   
                                    }
                                } catch (Exception e) {
                                    System.out.println("Notifikasi Kapasitas : "+e);
                                } finally{
                                    if(carikapasitas!=null){
                                        carikapasitas.close();
                                    }
                                    if(pscarikapasitas!=null){
                                        pscarikapasitas.close();
                                    }
                                }
                            }else{
                                if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?","data",12,new String[]{
                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,13).toString(),
                                    tbObat.getValueAt(i,6).toString(),""+Double.parseDouble(tbObat.getValueAt(i,1).toString()),
                                    tbObat.getValueAt(i,8).toString(),tbObat.getValueAt(i,9).toString(),""+(Double.parseDouble(tbObat.getValueAt(i,8).toString())+
                                            Double.parseDouble(tbObat.getValueAt(i,9).toString())+(Double.parseDouble(tbObat.getValueAt(i,6).toString())*
                                                    Double.parseDouble(tbObat.getValueAt(i,1).toString()))),"Ralan",kdgudang.getText()
                                })==true){
                                    if(!tbObat.getValueAt(i,11).toString().equals("")){
                                        Sequel.menyimpan("aturan_pakai","?,?,?,?,?",5,new String[]{
                                            Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),tbObat.getValueAt(i,2).toString(),tbObat.getValueAt(i,11).toString()
                                        });
                                    }                                        
                                    Trackobat.catatRiwayat(tbObat.getValueAt(i,2).toString(),0,Double.parseDouble(tbObat.getValueAt(i,1).toString()),"Pemberian Obat",var.getkode(),kdgudang.getText(),"Simpan");
                                    Sequel.menyimpan("gudangbarang","'"+tbObat.getValueAt(i,2).toString()+"','"+kdgudang.getText()+"','-"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'", 
                                                 "stok=stok-'"+Double.parseDouble(tbObat.getValueAt(i,1).toString())+"'","kode_brng='"+tbObat.getValueAt(i,2).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");   
                                }                                   
                            }                      
                        }
                        tbObat.setValueAt("",i,1);
                    }  

                    for(i=0;i<tbObatRacikan.getRowCount();i++){ 
                        if(Valid.SetAngka(tbObatRacikan.getValueAt(i,4).toString())>0){ 
                            Sequel.menyimpan("obat_racikan","?,?,?,?,?,?,?,?,?",9,new String[]{
                               Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),
                               tbObatRacikan.getValueAt(i,0).toString(),tbObatRacikan.getValueAt(i,1).toString(),
                               tbObatRacikan.getValueAt(i,2).toString(),tbObatRacikan.getValueAt(i,4).toString(),
                               tbObatRacikan.getValueAt(i,5).toString(),tbObatRacikan.getValueAt(i,6).toString()
                            });
                        }
                    }

                    Valid.tabelKosong(tabModeObatRacikan);

                    for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                        if(Valid.SetAngka(tbDetailObatRacikan.getValueAt(i,10).toString())>0){
                            if(Sequel.menyimpantf2("detail_obat_racikan","?,?,?,?,?","Data",5,new String[]{
                               Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),
                               tbDetailObatRacikan.getValueAt(i,0).toString(),tbDetailObatRacikan.getValueAt(i,1).toString()
                            })==true){
                                if(Sequel.menyimpantf("detail_pemberian_obat","?,?,?,?,?,?,?,?,?,?,?,?","data",12,new String[]{
                                    Valid.SetTgl(DTPTgl.getSelectedItem()+""),cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem(),TNoRw.getText(),
                                    tbDetailObatRacikan.getValueAt(i,1).toString(),tbDetailObatRacikan.getValueAt(i,5).toString(),
                                    tbDetailObatRacikan.getValueAt(i,4).toString(),""+Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString()),
                                    tbDetailObatRacikan.getValueAt(i,11).toString(),tbDetailObatRacikan.getValueAt(i,12).toString(),
                                    ""+(Double.parseDouble(tbDetailObatRacikan.getValueAt(i,11).toString())+                                        
                                            Double.parseDouble(tbDetailObatRacikan.getValueAt(i,12).toString())+
                                            (Double.parseDouble(tbDetailObatRacikan.getValueAt(i,4).toString())*
                                              Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString()))),"Ralan",kdgudang.getText()
                                })==true){
                                    Trackobat.catatRiwayat(tbDetailObatRacikan.getValueAt(i,1).toString(),0,Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString()),"Pemberian Obat",var.getkode(),kdgudang.getText(),"Simpan");
                                    Sequel.menyimpan("gudangbarang","'"+tbDetailObatRacikan.getValueAt(i,1).toString()+"','"+kdgudang.getText()+"','-"+Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString())+"'", 
                                                 "stok=stok-'"+Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString())+"'","kode_brng='"+tbDetailObatRacikan.getValueAt(i,1).toString()+"' and kd_bangsal='"+kdgudang.getText()+"'");   
                                }  
                            }   
                        }
                    }

                    Valid.tabelKosong(tabModeDetailObatRacikan);

                    if(!noresep.equals("")){
                        Sequel.mengedit("resep_obat","no_resep='"+noresep+"'","tgl_perawatan='"+Valid.SetTgl(DTPTgl.getSelectedItem()+"")+"',jam='"+cmbJam.getSelectedItem()+":"+cmbMnt.getSelectedItem()+":"+cmbDtk.getSelectedItem()+"'");
                    }
                    koneksi.setAutoCommit(true);
                    LTotal.setText("0");
                    LPpn.setText("0");
                    LTotalTagihan.setText("0");
                    if(ChkNoResep.isSelected()==true){
                        DlgResepObat resep=new DlgResepObat(null,false);
                        resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        resep.setLocationRelativeTo(internalFrame1);
                        resep.emptTeks(); 
                        resep.isCek();
                        if(!namadokter.equals("")){
                            resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),kodedokter,namadokter,"ralan");
                        }else{
                            resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),"ralan");
                            resep.setDokterRalan();
                        }
                        resep.tampil();
                        resep.setVisible(true);
                    }
                    ChkJln.setSelected(true);
                    dispose();
                } catch (Exception ex) {
                    System.out.println(ex);                
                }
            }                
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
    DlgCariKonversi carikonversi=new DlgCariKonversi(null,false);
    carikonversi.setLocationRelativeTo(internalFrame1);
    carikonversi.setAlwaysOnTop(false);
    carikonversi.setVisible(true);
}//GEN-LAST:event_BtnSeek5ActionPerformed

private void BtnSeek5KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSeek5KeyPressed
// TODO add your handling code here:
}//GEN-LAST:event_BtnSeek5KeyPressed

private void ppBersihkanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBersihkanActionPerformed
    if(TabRawat.getSelectedIndex()==0){
        for(i=0;i<tbObat.getRowCount();i++){ 
            tbObat.setValueAt("",i,1);
            tbObat.setValueAt(0,i,10);
            tbObat.setValueAt(0,i,9);
            tbObat.setValueAt(0,i,8);
        }
    }else if(TabRawat.getSelectedIndex()==1){
        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
            tbDetailObatRacikan.setValueAt("",i,9);
            tbDetailObatRacikan.setValueAt(0,i,10);
            tbDetailObatRacikan.setValueAt(0,i,11);
            tbDetailObatRacikan.setValueAt(0,i,12);
        }
    }       
    hitungObat();
}//GEN-LAST:event_ppBersihkanActionPerformed

private void JeniskelasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_JeniskelasItemStateChanged
       tampilobat(); 
}//GEN-LAST:event_JeniskelasItemStateChanged

private void JeniskelasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_JeniskelasKeyPressed
        Valid.pindah(evt, TCari,BtnKeluar);
}//GEN-LAST:event_JeniskelasKeyPressed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            label13.setPreferredSize(new Dimension(65, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            label13.setPreferredSize(new Dimension(1, 23));
        }      
    }//GEN-LAST:event_formWindowActivated

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        if(noresep.equals("")){
            tampilobat();
        }            
    }//GEN-LAST:event_formWindowOpened

    private void ChkNoResepItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ChkNoResepItemStateChanged
        if(ChkNoResep.isSelected()==true){
            DlgResepObat resep=new DlgResepObat(null,false);
            resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            resep.setLocationRelativeTo(internalFrame1);
            resep.emptTeks(); 
            resep.isCek();
            if(!namadokter.equals("")){
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),kodedokter,namadokter,"ralan");
            }else{
                resep.setNoRm(TNoRw.getText(),DTPTgl.getDate(),DTPTgl.getDate(),cmbJam.getSelectedItem().toString(),cmbMnt.getSelectedItem().toString(),cmbDtk.getSelectedItem().toString(),"ralan");
                resep.setDokterRalan();
            }
            resep.tampil();
            resep.setVisible(true);
        }
    }//GEN-LAST:event_ChkNoResepItemStateChanged

    private void ppStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppStokActionPerformed
        if(TabRawat.getSelectedIndex()==0){
            for(i=0;i<tbObat.getRowCount();i++){ 
                try {
                    stokbarang=0; 
                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                    try {
                        psstok.setString(1,kdgudang.getText());
                        psstok.setString(2,tbObat.getValueAt(i,2).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stokbarang=rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        stokbarang=0;
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rsstok != null){
                            rsstok.close();
                        }

                        if(psstok != null){
                            psstok.close();
                        }
                    }

                    tbObat.setValueAt(stokbarang,i,10);
                } catch (Exception e) {
                    tbObat.setValueAt(0,i,10);
                }
            }   
        }else if(TabRawat.getSelectedIndex()==1){
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){ 
                try {
                    stokbarang=0; 
                    psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                    try {
                        psstok.setString(1,kdgudang.getText());
                        psstok.setString(2,tbDetailObatRacikan.getValueAt(i,1).toString());
                        rsstok=psstok.executeQuery();
                        if(rsstok.next()){
                            stokbarang=rsstok.getDouble(1);
                        }
                    } catch (Exception e) {
                        stokbarang=0;
                        System.out.println("Notifikasi : "+e);
                    }finally{
                        if(rsstok != null){
                            rsstok.close();
                        }

                        if(psstok != null){
                            psstok.close();
                        }
                    }

                    tbDetailObatRacikan.setValueAt(stokbarang,i,7);
                } catch (Exception e) {
                    tbDetailObatRacikan.setValueAt(0,i,7);
                }
            }
        }
    }//GEN-LAST:event_ppStokActionPerformed

    private void DTPTglKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPTglKeyPressed
        Valid.pindah(evt,BtnKeluar,cmbJam);
    }//GEN-LAST:event_DTPTglKeyPressed

    private void cmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbJamKeyPressed
        Valid.pindah(evt,DTPTgl,cmbMnt);
    }//GEN-LAST:event_cmbJamKeyPressed

    private void cmbMntKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbMntKeyPressed
        Valid.pindah(evt,cmbJam,cmbDtk);
    }//GEN-LAST:event_cmbMntKeyPressed

    private void cmbDtkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cmbDtkKeyPressed
        Valid.pindah(evt,cmbMnt,Jeniskelas);
    }//GEN-LAST:event_cmbDtkKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ChkJlnActionPerformed

    private void kdgudangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdgudangKeyPressed
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_PAGE_DOWN:
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            break;
            case KeyEvent.VK_PAGE_UP:
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            TCari.requestFocus();
            break;
            case KeyEvent.VK_ENTER:
            Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());
            BtnSimpan.requestFocus();
            break;
            case KeyEvent.VK_UP:
            BtnGudangActionPerformed(null);
            break;
            default:
            break;
        }
    }//GEN-LAST:event_kdgudangKeyPressed

    private void BtnGudangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGudangActionPerformed
        caribangsal.isCek();
        caribangsal.emptTeks();
        caribangsal.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        caribangsal.setLocationRelativeTo(internalFrame1);
        caribangsal.setAlwaysOnTop(false);
        caribangsal.setVisible(true);
    }//GEN-LAST:event_BtnGudangActionPerformed

    private void tbObatPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbObatPropertyChange
        if(this.isVisible()==true){
            getDataobat();
            hitungObat();
        }
    }//GEN-LAST:event_tbObatPropertyChange

    private void TabRawatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabRawatMouseClicked
        if(TabRawat.getSelectedIndex()==0){
            BtnTambah1.setVisible(false);
            BtnHapus.setVisible(false);
            label13.setPreferredSize(new Dimension(65, 23));
        }else if(TabRawat.getSelectedIndex()==1){
            BtnTambah1.setVisible(true);
            BtnHapus.setVisible(true);
            label13.setPreferredSize(new Dimension(1, 23));
        }
    }//GEN-LAST:event_TabRawatMouseClicked

    private void BtnTambah1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnTambah1ActionPerformed
        i=tabModeObatRacikan.getRowCount()+1;
        if(i==99){
            JOptionPane.showMessageDialog(null,"Maksimal 98 Racikan..!!");
        }else{
            tabModeObatRacikan.addRow(new Object[]{""+i,"","","","","",""});
        }            
    }//GEN-LAST:event_BtnTambah1ActionPerformed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),2).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),3).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),5).equals("")&&tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),6).equals("")){
            tabModeObatRacikan.removeRow(tbObatRacikan.getSelectedRow());
        }else{
            JOptionPane.showMessageDialog(null,"Maaf sudah terisi, gak boleh dihapus..!!");
        }
        
    }//GEN-LAST:event_BtnHapusActionPerformed

    private void tbObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbObatRacikanKeyPressed
        if(tbObatRacikan.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                i=tbObatRacikan.getSelectedColumn();
                if(i==5){
                    var.setform("DlgCariObat");
                    aturanpakai.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    aturanpakai.setLocationRelativeTo(internalFrame1);
                    aturanpakai.setVisible(true);
                }else if(i==3){
                    if(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),1).equals("")){
                        JOptionPane.showMessageDialog(null,"Silahkan masukkan nama racikan..!!");
                        tbObatRacikan.requestFocus();
                    }else{
                        metoderacik.isCek();
                        metoderacik.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                        metoderacik.setLocationRelativeTo(internalFrame1);
                        metoderacik.setVisible(true);
                    }                        
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                if((i==4)){
                    TCari.requestFocus();
                } 
            }
        }
    }//GEN-LAST:event_tbObatRacikanKeyPressed

    private void tbDetailObatRacikanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanKeyPressed
        if(tbObatRacikan.getSelectedRow()!= -1){
            if(tbDetailObatRacikan.getRowCount()!=0){
                if(evt.getKeyCode()==KeyEvent.VK_ENTER){
                    try {
                        getDatadetailobatracikan();
                        i=tbDetailObatRacikan.getSelectedColumn();
                        if(i==11){
                            try {
                                if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(embalase,tbDetailObatRacikan.getSelectedRow(),11);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                            }
                        }else if(i==12){
                            try {
                                if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(tuslah,tbDetailObatRacikan.getSelectedRow(),12);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                            }

                            TCari.setText("");
                            TCari.requestFocus();
                        }else if((i==9)||(i==10)){  
                            try {
                                tbDetailObatRacikan.setValueAt(
                                        Valid.SetAngka7((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString())
                                        *Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString()))
                                        /Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),8).toString()))
                                        ,tbDetailObatRacikan.getSelectedRow(),10);
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                            }

                            hitungObat();
                            TCari.setText("");
                            TCari.requestFocus();
                        }else if(i==11){
                            TCari.setText("");
                            TCari.requestFocus();
                        }
                    } catch (java.lang.NullPointerException e) {
                    }
                }else if((evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                    try {
                        if(!tabModeDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString().equals("")){
                            getDatadetailobatracikan();
                        }                        
                    } catch (java.lang.NullPointerException e) {
                    }
                }else if(evt.getKeyCode()==KeyEvent.VK_DELETE){
                    i=tbDetailObatRacikan.getSelectedColumn();
                    if((i==9)||(i==10)){
                        if(tbDetailObatRacikan.getSelectedRow()!= -1){
                            tbDetailObatRacikan.setValueAt("",tbDetailObatRacikan.getSelectedRow(),9);
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                        }
                    }

                }else if(evt.getKeyCode()==KeyEvent.VK_SHIFT){
                    i=tbDetailObatRacikan.getSelectedColumn();
                    if(i!=9){
                        TCari.requestFocus();
                    }                
                }else if(evt.getKeyCode()==KeyEvent.VK_RIGHT){
                    i=tbDetailObatRacikan.getSelectedColumn();
                    if((i==9)||(i==10)){
                        try {
                            getDatadetailobatracikan();

                            try {
                                if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),11).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(embalase,tbDetailObatRacikan.getSelectedRow(),11);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                            }

                            try {
                                if(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0.0")||tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),12).toString().equals("0,0")) {
                                    tbDetailObatRacikan.setValueAt(tuslah,tbDetailObatRacikan.getSelectedRow(),12);
                                }
                            } catch (Exception e) {
                                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                            }  
                        } catch (Exception e) {
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                            tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                        }
                        hitungObat();
                    }
                }   
            }
        }else{
            JOptionPane.showMessageDialog(null,"Silahkan pilih No.Racikan terlebih dahulu");
        }            
    }//GEN-LAST:event_tbDetailObatRacikanKeyPressed

    private void tbDetailObatRacikanPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanPropertyChange
        if(this.isVisible()==true){
            getDatadetailobatracikan();
            hitungObat();
        }
    }//GEN-LAST:event_tbDetailObatRacikanPropertyChange

    private void tbDetailObatRacikanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDetailObatRacikanMouseClicked
        if(tbObat.getRowCount()!=0){
            try {
                getDatadetailobatracikan();
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_tbDetailObatRacikanMouseClicked

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgCariObat dialog = new DlgCariObat(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.Button BtnAll;
    private widget.Button BtnCari;
    private widget.Button BtnGudang;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.Button BtnTambah;
    private widget.Button BtnTambah1;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkNoResep;
    private widget.Tanggal DTPTgl;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Jam;
    private widget.ComboBox Jeniskelas;
    private widget.TextBox Kd2;
    private widget.TextBox KdPj;
    private widget.Label LPpn;
    private widget.Label LTotal;
    private widget.Label LTotalTagihan;
    private widget.TextBox LblNamaPasien;
    private widget.TextBox LblNoRM;
    private widget.TextBox LblNoRawat;
    private javax.swing.JPopupMenu Popup;
    private widget.ScrollPane Scroll;
    private widget.ScrollPane Scroll1;
    private widget.ScrollPane Scroll2;
    private widget.TextBox TCari;
    private widget.TextBox TNoRw;
    private javax.swing.JTabbedPane TabRawat;
    private widget.TextBox Tanggal;
    private widget.ComboBox cmbDtk;
    private widget.ComboBox cmbJam;
    private widget.ComboBox cmbMnt;
    private widget.InternalFrame internalFrame1;
    private widget.Label jLabel10;
    private widget.Label jLabel11;
    private widget.Label jLabel12;
    private widget.Label jLabel5;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private javax.swing.JPanel jPanel3;
    private widget.TextBox kdgudang;
    private widget.Label label12;
    private widget.Label label13;
    private widget.Label label21;
    private widget.Label label9;
    private widget.TextBox nmgudang;
    private widget.panelisi panelisi3;
    private javax.swing.JMenuItem ppBersihkan;
    private javax.swing.JMenuItem ppStok;
    private widget.Table tbDetailObatRacikan;
    private widget.Table tbObat;
    private widget.Table tbObatRacikan;
    // End of variables declaration//GEN-END:variables

    public void tampilobat() {        
        z=0;
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,0).toString().equals("")){
                z++;
            }
        }    
        
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new double[z];
        harga=null;
        harga=new double[z];
        eb=null;
        eb=new double[z];
        ts=null;
        ts=new double[z];
        stok=null;
        stok=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        namajenis=null;
        namajenis=new String[z];                   
        aturan=null;
        aturan=new String[z];           
        industri=null;
        industri=new String[z];         
        beli=null;
        beli=new double[z]; 
        kategori=null;
        kategori=new String[z];
        golongan=null;
        golongan=new String[z];
        z=0;        
        for(i=0;i<tbObat.getRowCount();i++){
            if(!tbObat.getValueAt(i,1).toString().equals("")){
                pilih[z]=Boolean.parseBoolean(tbObat.getValueAt(i,0).toString());                
                try {
                    jumlah[z]=Double.parseDouble(tbObat.getValueAt(i,1).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }  
                kodebarang[z]=tbObat.getValueAt(i,2).toString();
                namabarang[z]=tbObat.getValueAt(i,3).toString();
                kodesatuan[z]=tbObat.getValueAt(i,4).toString();
                letakbarang[z]=tbObat.getValueAt(i,5).toString();
                try {
                    harga[z]=Double.parseDouble(tbObat.getValueAt(i,6).toString());
                } catch (Exception e) {
                    harga[z]=0;
                }                  
                namajenis[z]=tbObat.getValueAt(i,7).toString();
                try {
                    eb[z]=Double.parseDouble(tbObat.getValueAt(i,8).toString());
                } catch (Exception e) {
                    eb[z]=0;
                }  
                try {
                    ts[z]=Double.parseDouble(tbObat.getValueAt(i,9).toString());
                } catch (Exception e) {
                    ts[z]=0;
                } 
                try {
                    stok[z]=Double.parseDouble(tbObat.getValueAt(i,10).toString());
                } catch (Exception e) {
                    stok[z]=0;
                } 
                aturan[z]=tbObat.getValueAt(i,11).toString();
                industri[z]=tbObat.getValueAt(i,12).toString();
                try {
                    beli[z]=Double.parseDouble(tbObat.getValueAt(i,13).toString());
                } catch (Exception e) {
                    beli[z]=0;
                } 
                kategori[z]=tbObat.getValueAt(i,14).toString();
                golongan[z]=tbObat.getValueAt(i,15).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeobat);             
        
        for(i=0;i<z;i++){
            tabModeobat.addRow(new Object[] {
                pilih[i],jumlah[i],kodebarang[i],namabarang[i],kodesatuan[i],letakbarang[i],harga[i],namajenis[i],eb[i],ts[i],stok[i],aturan[i],industri[i],beli[i],kategori[i],golongan[i]
            });
        }
        
        try {
            if(kenaikan>0){
                psobatasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "+
                    " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and kategori_barang.nama like ? or "+
                    " databarang.status='1' and golongan_barang.nama like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                try{
                    psobatasuransi.setDouble(1,kenaikan);
                    psobatasuransi.setString(2,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(3,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(4,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(5,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(6,"%"+TCari.getText().trim()+"%");
                    rsobat=psobatasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                           rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("harga"),100),
                           rsobat.getString("nama"),0,0,0,"",rsobat.getString("nama_industri"),
                           rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                        });          
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psobatasuransi != null){
                        psobatasuransi.close();
                    }
                }
            }else{
                psobat=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.karyawan,databarang.ralan,databarang.beliluar,"+
                    " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan "+
                    " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "+
                    " where databarang.status='1' and databarang.kode_brng like ? or "+
                    " databarang.status='1' and databarang.nama_brng like ? or "+
                    " databarang.status='1' and kategori_barang.nama like ? or "+
                    " databarang.status='1' and golongan_barang.nama like ? or "+
                    " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                try{
                    psobat.setString(1,"%"+TCari.getText().trim()+"%");
                    psobat.setString(2,"%"+TCari.getText().trim()+"%");
                    psobat.setString(3,"%"+TCari.getText().trim()+"%");
                    psobat.setString(4,"%"+TCari.getText().trim()+"%");
                    psobat.setString(5,"%"+TCari.getText().trim()+"%");
                    rsobat=psobat.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                               rsobat.getString("nama"),0,0,0,"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                               rsobat.getString("nama"),0,0,0,"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                               rsobat.getString("nama"),0,0,0,"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeobat.addRow(new Object[] {false,"",rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("utama"),100),
                               rsobat.getString("nama"),0,0,0,"",rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }                 
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psobat != null){
                        psobat.close();
                    }
                }                        
            }      
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }
    
    public void tampilobat2(String no_resep) {     
        this.noresep=no_resep; 
        try {
            Valid.tabelKosong(tabModeobat);
            Valid.tabelKosong(tabModeObatRacikan);
            Valid.tabelKosong(tabModeDetailObatRacikan);
            if(kenaikan>0){
                psobatasuransi=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,jenis.nama, "+
                    "databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                    " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli, "+
                    " resep_dokter.jml, resep_dokter.aturan_pakai,kategori_barang.nama as kategori,golongan_barang.nama as golongan "+
                    " from databarang inner join jenis inner join golongan_barang inner join kategori_barang "+
                    " inner join industrifarmasi inner join resep_dokter on databarang.kdjns=jenis.kdjns "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " and resep_dokter.kode_brng=databarang.kode_brng "+
                    " and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  "+
                    " where resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
                try{
                    psobatasuransi.setDouble(1,kenaikan);
                    psobatasuransi.setString(2,no_resep);
                    psobatasuransi.setString(3,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(4,no_resep);
                    psobatasuransi.setString(5,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(6,no_resep);
                    psobatasuransi.setString(7,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(8,no_resep);
                    psobatasuransi.setString(9,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(10,no_resep);
                    psobatasuransi.setString(11,"%"+TCari.getText().trim()+"%");
                    rsobat=psobatasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                           rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("harga"),100),
                           rsobat.getString("nama"),0,0,0,rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                           rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                        });          
                    }  
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }
                    if(psobatasuransi != null){
                        psobatasuransi.close();
                    }
                }                                       
            }else{   
                psobat=koneksi.prepareStatement(
                    "select databarang.kode_brng, databarang.nama_brng,jenis.nama, "+
                    "databarang.kode_sat,databarang.karyawan,databarang.ralan,"+
                    "databarang.beliluar,databarang.letak_barang,databarang.utama,"+
                    "industrifarmasi.nama_industri,databarang.h_beli,resep_dokter.jml, "+
                    "resep_dokter.aturan_pakai,kategori_barang.nama as kategori,golongan_barang.nama as golongan "+
                    " from databarang inner join jenis inner join golongan_barang inner join kategori_barang "+
                    " inner join industrifarmasi inner join resep_dokter on databarang.kdjns=jenis.kdjns "+
                    " and industrifarmasi.kode_industri=databarang.kode_industri "+
                    " and resep_dokter.kode_brng=databarang.kode_brng  "+
                    " and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode  "+
                    " where resep_dokter.no_resep=? and databarang.status='1' and databarang.kode_brng like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and databarang.nama_brng like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and kategori_barang.nama like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and golongan_barang.nama like ? or "+
                    " resep_dokter.no_resep=? and databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");

                try{
                    psobat.setString(1,no_resep);
                    psobat.setString(2,"%"+TCari.getText().trim()+"%");
                    psobat.setString(3,no_resep);
                    psobat.setString(4,"%"+TCari.getText().trim()+"%");
                    psobat.setString(5,no_resep);
                    psobat.setString(6,"%"+TCari.getText().trim()+"%");
                    psobat.setString(7,no_resep);
                    psobat.setString(8,"%"+TCari.getText().trim()+"%");
                    psobat.setString(9,no_resep);
                    psobat.setString(10,"%"+TCari.getText().trim()+"%");
                    rsobat=psobat.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                               rsobat.getString("nama"),0,0,0,rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                               rsobat.getString("nama"),0,0,0,rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                               rsobat.getString("nama"),0,0,0,rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeobat.addRow(new Object[] {false,rsobat.getString("jml"),rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                               rsobat.getString("kode_sat"),rsobat.getString("letak_barang"),Valid.roundUp(rsobat.getDouble("utama"),100),
                               rsobat.getString("nama"),0,0,0,rsobat.getString("aturan_pakai"),rsobat.getString("nama_industri"),
                               rsobat.getDouble("h_beli"),rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }                 
                    }
                }catch(Exception e){
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsobat != null){
                        rsobat.close();
                    }

                    if(psobat != null){
                        psobat.close();
                    }
                }
            }        
            for(i=0;i<tbObat.getRowCount();i++){
                getDataobat(i);
            }
            psobat=koneksi.prepareStatement(
                    "select resep_dokter_racikan.no_racik,resep_dokter_racikan.nama_racik,"+
                    "resep_dokter_racikan.kd_racik,metode_racik.nm_racik as metode,"+
                    "resep_dokter_racikan.jml_dr,resep_dokter_racikan.aturan_pakai,"+
                    "resep_dokter_racikan.keterangan from resep_dokter_racikan inner join metode_racik "+
                    "on resep_dokter_racikan.kd_racik=metode_racik.kd_racik where "+
                    "resep_dokter_racikan.no_resep=? ");
            try {
                psobat.setString(1,no_resep);
                rsobat=psobat.executeQuery();
                while(rsobat.next()){
                    tabModeObatRacikan.addRow(new String[]{
                        rsobat.getString("no_racik"),rsobat.getString("nama_racik"),rsobat.getString("kd_racik"),
                        rsobat.getString("metode"),rsobat.getString("jml_dr"),rsobat.getString("aturan_pakai"),
                        rsobat.getString("keterangan")
                    });
                    if(kenaikan>0){
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"+
                            "databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,databarang.letak_barang,"+
                            "databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"+
                            "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "+
                            "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "+
                            "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                            "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "+
                            "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "+
                            "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                        try {
                            ps2.setDouble(1,kenaikan);
                            ps2.setString(2,no_resep);
                            ps2.setString(3,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                tabModeDetailObatRacikan.addRow(new Object[]{
                                    rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                    rs2.getString("kode_sat"),rs2.getDouble("harga"),rs2.getDouble("h_beli"),
                                    rs2.getString("jenis"),0,rs2.getDouble("kapasitas"),rs2.getDouble("kandungan"),
                                    rs2.getDouble("jml"),0,0,rs2.getString("nama_industri"),rs2.getString("kategori"),
                                    rs2.getString("golongan")                                    
                                });                          
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 3 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }else{
                        ps2=koneksi.prepareStatement("select databarang.kode_brng,databarang.nama_brng,resep_dokter_racikan_detail.jml,resep_dokter_racikan_detail.kandungan,"+
                            "databarang.kode_sat,databarang.karyawan,databarang.ralan,databarang.beliluar,databarang.letak_barang,"+
                            "databarang.utama,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,"+
                            "industrifarmasi.nama_industri,jenis.nama as jenis,databarang.kapasitas from resep_dokter_racikan_detail inner join databarang inner join jenis "+
                            "inner join golongan_barang inner join kategori_barang inner join industrifarmasi on "+
                            "resep_dokter_racikan_detail.kode_brng=databarang.kode_brng and databarang.kdjns=jenis.kdjns "+
                            "and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode "+
                            "and databarang.kode_kategori=kategori_barang.kode where resep_dokter_racikan_detail.no_resep=? and "+
                            "resep_dokter_racikan_detail.no_racik=? order by databarang.kode_brng");
                        try {
                            ps2.setString(1,no_resep);
                            ps2.setString(2,rsobat.getString("no_racik"));
                            rs2=ps2.executeQuery();
                            while(rs2.next()){
                                if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),rs2.getDouble("karyawan"),rs2.getDouble("h_beli"),
                                        rs2.getString("jenis"),0,rs2.getDouble("kapasitas"),rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"),0,0,rs2.getString("nama_industri"),rs2.getString("kategori"),
                                        rs2.getString("golongan")                                    
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),rs2.getDouble("ralan"),rs2.getDouble("h_beli"),
                                        rs2.getString("jenis"),0,rs2.getDouble("kapasitas"),rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"),0,0,rs2.getString("nama_industri"),rs2.getString("kategori"),
                                        rs2.getString("golongan")                                    
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),rs2.getDouble("beliluar"),rs2.getDouble("h_beli"),
                                        rs2.getString("jenis"),0,rs2.getDouble("kapasitas"),rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"),0,0,rs2.getString("nama_industri"),rs2.getString("kategori"),
                                        rs2.getString("golongan")                                    
                                    });
                                }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                                    tabModeDetailObatRacikan.addRow(new Object[]{
                                        rsobat.getString("no_racik"),rs2.getString("kode_brng"),rs2.getString("nama_brng"),
                                        rs2.getString("kode_sat"),rs2.getDouble("utama"),rs2.getDouble("h_beli"),
                                        rs2.getString("jenis"),0,rs2.getDouble("kapasitas"),rs2.getDouble("kandungan"),
                                        rs2.getDouble("jml"),0,0,rs2.getString("nama_industri"),rs2.getString("kategori"),
                                        rs2.getString("golongan")                                    
                                    });
                                }                                  
                            }
                        } catch (Exception e) {
                            System.out.println("Notifikasi 3 : "+e);
                        } finally{
                            if(rs2!=null){
                                rs2.close();
                            }
                            if(ps2!=null){
                                ps2.close();
                            }
                        }
                    }                        
                }
            } catch (Exception e) {
                System.out.println("Notifikasi 2 : "+e);
            } finally{
                if(rsobat!=null){
                    rsobat.close();
                }
                if(psobat!=null){
                    psobat.close();
                }
            }
            for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
                getDatadetailobatracikan(i);
            }
            hitungObat();
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }

    public void emptTeksobat() {
        Kd2.setText(""); 
        TCari.setText("");
        TCari.requestFocus();
    }

    private void getDataobat() {
        if(tbObat.getSelectedRow()!= -1){
            Kd2.setText("");
            Kd2.setText(tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
            try {
                stokbarang=0;  
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1,kdgudang.getText());
                    psstok.setString(2,tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
                    }                                
                } catch (Exception e) {
                    stokbarang=0;
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsstok != null){
                        rsstok.close();
                    }
                    if(psstok != null){
                        psstok.close();
                    }
                }

                tbObat.setValueAt(stokbarang,tbObat.getSelectedRow(),10);
                y=0;
                try {
                    if(tbObat.getValueAt(tbObat.getSelectedRow(),0).toString().equals("true")){
                        pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                        try {
                            pscarikapasitas.setString(1,tbObat.getValueAt(tbObat.getSelectedRow(),2).toString());
                            carikapasitas=pscarikapasitas.executeQuery();
                            if(carikapasitas.next()){ 
                                y=Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString())/carikapasitas.getDouble(1);
                            }else{
                                y=Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                            }
                        } catch (Exception e) {
                            y=Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                            System.out.println("Kapasitasmu masih kosong broooh : "+e);
                        } finally{
                            if(carikapasitas!=null){
                                carikapasitas.close();
                            }
                            if(pscarikapasitas!=null){
                                pscarikapasitas.close();
                            }
                        }
                    }else{
                        y=Double.parseDouble(tbObat.getValueAt(tbObat.getSelectedRow(),1).toString());
                    }                        
                } catch (Exception e) {
                    y=0;
                }
                if(stokbarang<y){
                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                    tbObat.setValueAt("",tbObat.getSelectedRow(),1);
                }
            } catch (Exception e) {
                tbObat.setValueAt(0,tbObat.getSelectedRow(),10);
            }   
        }
    }
    
    private void getDataobat(int data) {        
        try {
            stokbarang=0;  
            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
            try {
                psstok.setString(1,kdgudang.getText());
                psstok.setString(2,tbObat.getValueAt(data,2).toString());
                rsstok=psstok.executeQuery();
                if(rsstok.next()){
                    stokbarang=rsstok.getDouble(1);
                }                                
            } catch (Exception e) {
                stokbarang=0;
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsstok != null){
                    rsstok.close();
                }
                if(psstok != null){
                    psstok.close();
                }
            }

            tbObat.setValueAt(stokbarang,data,10);
            y=0;
            try {
                if(tbObat.getValueAt(data,0).toString().equals("true")){
                    pscarikapasitas= koneksi.prepareStatement("select IFNULL(kapasitas,1) from databarang where kode_brng=?");                                      
                    try {
                        pscarikapasitas.setString(1,tbObat.getValueAt(data,2).toString());
                        carikapasitas=pscarikapasitas.executeQuery();
                        if(carikapasitas.next()){ 
                            y=Double.parseDouble(tbObat.getValueAt(data,1).toString())/carikapasitas.getDouble(1);
                        }else{
                            y=Double.parseDouble(tbObat.getValueAt(data,1).toString());
                        }
                    } catch (Exception e) {
                        y=Double.parseDouble(tbObat.getValueAt(data,1).toString());
                        System.out.println("Kapasitasmu masih kosong broooh : "+e);
                    } finally{
                        if(carikapasitas!=null){
                            carikapasitas.close();
                        }
                        if(pscarikapasitas!=null){
                            pscarikapasitas.close();
                        }
                    }
                }else{
                    y=Double.parseDouble(tbObat.getValueAt(data,1).toString());
                }                        
            } catch (Exception e) {
                y=0;
            }
            if(stokbarang<y){
                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
            }
        } catch (Exception e) {
            tbObat.setValueAt(0,data,10);
        } 
    }

    public JTextField getTextField(){
        return Kd2;
    }

    public JTable getTable(){
        return tbObat;
    }
    
    public Button getButton(){
        return BtnSimpan;
    }
    
    public void isCek(){   
        bangsal=Sequel.cariIsi("select kd_bangsal from set_depo_ralan where kd_poli=?",Sequel.cariIsi("select kd_poli from reg_periksa where no_rawat=?",TNoRw.getText()));
        if(bangsal.equals("")){
            bangsal=bangsaldefault;
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        }else{
            if(var.getakses_depo_obat()==true){
                kdgudang.setEditable(true);
                nmgudang.setEditable(true);
                BtnGudang.setEnabled(true);
            }else{
                kdgudang.setEditable(false);
                nmgudang.setEditable(false);
                BtnGudang.setEnabled(false);
            }
        }            
        kdgudang.setText(bangsal);
        Sequel.cariIsi("select bangsal.nm_bangsal from bangsal where bangsal.kd_bangsal=?",nmgudang,kdgudang.getText());            
        BtnTambah.setEnabled(var.getobat());
        TCari.requestFocus();
        if(var.getkode().equals("Admin Utama")){
            kdgudang.setEditable(true);
            nmgudang.setEditable(true);
            BtnGudang.setEnabled(true);
        }
    }
    
    public void setNoRm(String norwt,String norm,String nama,String tanggal, String jam) {        
        TNoRw.setText(norwt);
        LblNoRawat.setText(norwt);
        LblNoRM.setText(norm);
        LblNamaPasien.setText(nama);
        noresep="";
        Tanggal.setText(tanggal);
        Jam.setText(jam);  
        KdPj.setText(Sequel.cariIsi("select kd_pj from reg_periksa where no_rawat=?",norwt));
        kenaikan=Sequel.cariIsiAngka("select (hargajual/100) from set_harga_obat_ralan where kd_pj=?",KdPj.getText());
        TCari.requestFocus();
    }
    
    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                // Membuat Date
                //Date dt = new Date();
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =cmbJam.getSelectedIndex();
                    nilai_menit =cmbMnt.getSelectedIndex();
                    nilai_detik =cmbDtk.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                cmbJam.setSelectedItem(jam);
                cmbMnt.setSelectedItem(menit);
                cmbDtk.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }
    
    public void setDokter(String kodedokter,String namadokter){
        this.kodedokter=kodedokter;
        this.namadokter=namadokter;
    }
    
    public void tampildetailracikanobat() {        
        z=0;
        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
            if(!tbDetailObatRacikan.getValueAt(i,9).toString().equals("")){
                z++;
            }
        }    
        
        pilih=null;
        pilih=new boolean[z]; 
        jumlah=null;
        jumlah=new double[z];
        harga=null;
        harga=new double[z];
        eb=null;
        eb=new double[z];
        ts=null;
        ts=new double[z];
        stok=null;
        stok=new double[z];
        kodebarang=null;
        kodebarang=new String[z];
        namabarang=null;
        namabarang=new String[z];
        kodesatuan=null;
        kodesatuan=new String[z];
        letakbarang=null;
        letakbarang=new String[z];
        no=null;
        no=new String[z];
        namajenis=null;
        namajenis=new String[z];        
        industri=null;
        industri=new String[z];         
        beli=null;
        beli=new double[z]; 
        kategori=null;
        kategori=new String[z];
        golongan=null;
        golongan=new String[z];        
        kapasitas=null;
        kapasitas=new double[z];   
        kandungan=null;
        kandungan=new double[z];
        z=0;        
        for(i=0;i<tbDetailObatRacikan.getRowCount();i++){
            if(!tbDetailObatRacikan.getValueAt(i,9).toString().equals("")){
                no[z]=tbDetailObatRacikan.getValueAt(i,0).toString();
                kodebarang[z]=tbDetailObatRacikan.getValueAt(i,1).toString();
                namabarang[z]=tbDetailObatRacikan.getValueAt(i,2).toString();
                kodesatuan[z]=tbDetailObatRacikan.getValueAt(i,3).toString();
                try {
                    harga[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,4).toString());
                } catch (Exception e) {
                    harga[z]=0;
                }
                try {
                    beli[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,5).toString());
                } catch (Exception e) {
                    beli[z]=0;
                }
                namajenis[z]=tbDetailObatRacikan.getValueAt(i,6).toString();
                try {
                    stok[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,7).toString());
                } catch (Exception e) {
                    stok[z]=0;
                }                
                try {
                    kapasitas[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,8).toString());
                } catch (Exception e) {
                    kapasitas[z]=0;
                }                
                try {
                    kandungan[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,9).toString());
                } catch (Exception e) {
                    kandungan[z]=0;
                }                
                try {
                    jumlah[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,10).toString());
                } catch (Exception e) {
                    jumlah[z]=0;
                }                 
                try {
                    eb[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,11).toString());
                } catch (Exception e) {
                    eb[z]=0;
                } 
                try {
                    ts[z]=Double.parseDouble(tbDetailObatRacikan.getValueAt(i,12).toString());
                } catch (Exception e) {
                    ts[z]=0;
                }                                 
                industri[z]=tbDetailObatRacikan.getValueAt(i,13).toString();
                kategori[z]=tbDetailObatRacikan.getValueAt(i,14).toString();
                golongan[z]=tbDetailObatRacikan.getValueAt(i,15).toString();
                z++;
            }
        }
        
        Valid.tabelKosong(tabModeDetailObatRacikan);             
        
        for(i=0;i<z;i++){
            tabModeDetailObatRacikan.addRow(new Object[] {
                no[i],kodebarang[i],namabarang[i],kodesatuan[i],harga[i],beli[i],
                namajenis[i],stok[i],kapasitas[i],kandungan[i],jumlah[i],eb[i],
                ts[i],industri[i],kategori[i],golongan[i]
            });
        }
        
        try {
            psobat=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,databarang.karyawan,databarang.ralan,databarang.beliluar,"+
                        " databarang.letak_barang,databarang.utama,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas  "+
                        " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "+
                        " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "+
                        " where databarang.status='1' and databarang.kode_brng like ? or "+
                        " databarang.status='1' and databarang.nama_brng like ? or "+
                        " databarang.status='1' and kategori_barang.nama like ? or "+
                        " databarang.status='1' and golongan_barang.nama like ? or "+
                        " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            psobatasuransi=koneksi.prepareStatement("select databarang.kode_brng, databarang.nama_brng,jenis.nama, databarang.kode_sat,(databarang.h_beli+(databarang.h_beli*?)) as harga,"+
                        " databarang.letak_barang,industrifarmasi.nama_industri,databarang.h_beli,kategori_barang.nama as kategori,golongan_barang.nama as golongan,databarang.kapasitas "+
                        " from databarang inner join jenis inner join industrifarmasi inner join golongan_barang inner join kategori_barang on databarang.kdjns=jenis.kdjns "+
                        " and industrifarmasi.kode_industri=databarang.kode_industri and databarang.kode_golongan=golongan_barang.kode and databarang.kode_kategori=kategori_barang.kode "+
                        " where databarang.status='1' and databarang.kode_brng like ? or "+
                        " databarang.status='1' and databarang.nama_brng like ? or "+
                        " databarang.status='1' and kategori_barang.nama like ? or "+
                        " databarang.status='1' and golongan_barang.nama like ? or "+
                        " databarang.status='1' and jenis.nama like ? order by databarang.nama_brng");
            try{    
                if(kenaikan>0){
                    psobatasuransi.setDouble(1,kenaikan);
                    psobatasuransi.setString(2,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(3,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(4,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(5,"%"+TCari.getText().trim()+"%");
                    psobatasuransi.setString(6,"%"+TCari.getText().trim()+"%");
                    rsobat=psobatasuransi.executeQuery();
                    while(rsobat.next()){
                        tabModeDetailObatRacikan.addRow(new Object[] {
                            tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                            rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                            rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("harga"),100),
                            rsobat.getDouble("h_beli"),rsobat.getString("nama"),0,
                            rsobat.getDouble("kapasitas"),"",0,0,0,rsobat.getString("nama_industri"),
                            rsobat.getString("kategori"),rsobat.getString("golongan")
                        });          
                    }                 
                }else{
                    psobat.setString(1,"%"+TCari.getText().trim()+"%");
                    psobat.setString(2,"%"+TCari.getText().trim()+"%");
                    psobat.setString(3,"%"+TCari.getText().trim()+"%");
                    psobat.setString(4,"%"+TCari.getText().trim()+"%");
                    psobat.setString(5,"%"+TCari.getText().trim()+"%");
                    rsobat=psobat.executeQuery();
                    while(rsobat.next()){
                        if(Jeniskelas.getSelectedItem().equals("Karyawan")){
                            tabModeDetailObatRacikan.addRow(new Object[] {
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("karyawan"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),0,
                                rsobat.getDouble("kapasitas"),"",0,0,0,rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Rawat Jalan")){
                            tabModeDetailObatRacikan.addRow(new Object[] {
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("ralan"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),0,
                                rsobat.getDouble("kapasitas"),"",0,0,0,rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Beli Luar")){
                            tabModeDetailObatRacikan.addRow(new Object[] {
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("beliluar"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),0,
                                rsobat.getDouble("kapasitas"),"",0,0,0,rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }else if(Jeniskelas.getSelectedItem().equals("Utama/BPJS")){
                            tabModeDetailObatRacikan.addRow(new Object[] {
                                tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),0).toString(),
                                rsobat.getString("kode_brng"),rsobat.getString("nama_brng"),
                                rsobat.getString("kode_sat"),Valid.roundUp(rsobat.getDouble("utama"),100),
                                rsobat.getDouble("h_beli"),rsobat.getString("nama"),0,
                                rsobat.getDouble("kapasitas"),"",0,0,0,rsobat.getString("nama_industri"),
                                rsobat.getString("kategori"),rsobat.getString("golongan")
                            });
                        }                 
                    }
                }              
            }catch(Exception e){
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsobat != null){
                    rsobat.close();
                }
                
                if(psobat != null){
                    psobat.close();
                }
                
                if(psobatasuransi != null){
                    psobatasuransi.close();
                }
            }
        } catch (Exception e) {
            System.out.println("Notifikasi : "+e);
        }            
    }
    
    private void getDatadetailobatracikan() {
        if(tbDetailObatRacikan.getSelectedRow()!= -1){
            try {
                tbDetailObatRacikan.setValueAt(
                                Valid.SetAngka7((Double.parseDouble(tbObatRacikan.getValueAt(tbObatRacikan.getSelectedRow(),4).toString())
                                *Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),9).toString()))
                                /Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),8).toString()))
                                ,tbDetailObatRacikan.getSelectedRow(),10);
                
                stokbarang=0;  
                psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
                try {
                    psstok.setString(1,kdgudang.getText());
                    psstok.setString(2,tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),1).toString());
                    rsstok=psstok.executeQuery();
                    if(rsstok.next()){
                        stokbarang=rsstok.getDouble(1);
                    }                                
                } catch (Exception e) {
                    stokbarang=0;
                    System.out.println("Notifikasi : "+e);
                }finally{
                    if(rsstok != null){
                        rsstok.close();
                    }
                    if(psstok != null){
                        psstok.close();
                    }
                }

                tbDetailObatRacikan.setValueAt(stokbarang,tbDetailObatRacikan.getSelectedRow(),7);
                y=0;
                try {
                    y=Double.parseDouble(tbDetailObatRacikan.getValueAt(tbDetailObatRacikan.getSelectedRow(),10).toString());
                } catch (Exception e) {
                    y=0;
                }
                
                if(stokbarang<y){
                    JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
                    tbDetailObatRacikan.setValueAt("",tbDetailObatRacikan.getSelectedRow(),9);
                    tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                    tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                    tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
                }
            } catch (Exception e) {
                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),10);
                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),11);
                tbDetailObatRacikan.setValueAt(0,tbDetailObatRacikan.getSelectedRow(),12);
            }   
        }
    }
    
    private void getDatadetailobatracikan(int data) {       
        try {
            stokbarang=0;  
            psstok=koneksi.prepareStatement("select ifnull(stok,'0') from gudangbarang where kd_bangsal=? and kode_brng=?");
            try {
                psstok.setString(1,kdgudang.getText());
                psstok.setString(2,tbDetailObatRacikan.getValueAt(data,1).toString());
                rsstok=psstok.executeQuery();
                if(rsstok.next()){
                    stokbarang=rsstok.getDouble(1);
                }                                
            } catch (Exception e) {
                stokbarang=0;
                System.out.println("Notifikasi : "+e);
            }finally{
                if(rsstok != null){
                    rsstok.close();
                }
                if(psstok != null){
                    psstok.close();
                }
            }

            tbDetailObatRacikan.setValueAt(stokbarang,data,7);
            y=0;
            try {
                y=Double.parseDouble(tbDetailObatRacikan.getValueAt(data,10).toString());
            } catch (Exception e) {
                y=0;
            }

            if(stokbarang<y){
                JOptionPane.showMessageDialog(rootPane,"Maaf stok tidak mencukupi..!!");
            }
        } catch (Exception e) {
            tbDetailObatRacikan.setValueAt(0,data,10);
            tbDetailObatRacikan.setValueAt(0,data,11);
            tbDetailObatRacikan.setValueAt(0,data,12);
        }           
    }

    private void hitungObat() {
        ttl=0;
        y=0;
        row2=tabModeobat.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabModeobat.getValueAt(r,1).toString())>0){
                    try {                
                        y=Double.parseDouble(tabModeobat.getValueAt(r,1).toString())*
                          Double.parseDouble(tabModeobat.getValueAt(r,6).toString())+
                          Double.parseDouble(tabModeobat.getValueAt(r,8).toString())+
                          Double.parseDouble(tabModeobat.getValueAt(r,9).toString());                                                
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }  
            } catch (Exception e) {
            }                           
        }
        row2=tabModeDetailObatRacikan.getRowCount();
        for(r=0;r<row2;r++){ 
            try {
                if(Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,10).toString())>0){
                    try {
                        y=Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,10).toString())*
                          Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,4).toString())+
                          Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,11).toString())+
                          Double.parseDouble(tabModeDetailObatRacikan.getValueAt(r,12).toString());
                    } catch (Exception e) {
                        y=0;
                    }
                    ttl=ttl+y;
                }
            } catch (Exception e) {
            }    
        }
        LTotal.setText(Valid.SetAngka(ttl));
        ppnobat=0;
        if(tampilkan_ppnobat_ralan.equals("Yes")){
            ppnobat=ttl*0.1;
            ttl=ttl+ppnobat;
            LPpn.setText(Valid.SetAngka(ppnobat));
            LTotalTagihan.setText(Valid.SetAngka(ttl));
        }
    }
}
