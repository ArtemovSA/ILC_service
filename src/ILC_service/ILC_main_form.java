package ILC_service;

import ILC_service.Serial_port.Buf_class;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import jssc.SerialPortList;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author user
 */

public class ILC_main_form extends javax.swing.JFrame {

    public byte DEBUG_WRITE_BURST = 0x01;       //Записать массив байт
    public byte DEBUG_WRITE_BYTE = 0x02;        //Записать байт
    public byte DEBUG_READ_BURST = 0x03;        //Читать массив байт
    public byte DEBUG_READ_BYTE = 0x04;         //Читать байт
    public byte DEBUG_WRITE_SCRIPT = 0x05;      //Записать скрипт в память
    public byte DEBUG_PREPARE_WRITE_SCRIPT =0x06;//Подготовить запись
    public byte DEBUG_START_SCRIPT = 0x07;      //Запустить скрипт
    public byte DEBUG_STOP_SCRIPT = 0x08;       //Остановить скрипт
    public byte DEBUG_PAUSE_SCRIPT = 0x09;      //Приостановить скрипт   
    public byte DEBUG_SD_WRITE_SCRIPT = 0x0A;   //Записать скрипт на sd карту

    public DefaultTableModel settings_table; 
    public DefaultTableModel calibratePhase_table;
    public DefaultTableModel calibrateMain_table;
    public DefaultTableModel flash_table;
    public JTable SelTable = null;
    
    String sFileName = "ILC.prop";
    public static Properties props = new Properties(); //Переменная настроек
    
    public DefaultListModel debug_List_files = new DefaultListModel();
    public DefaultComboBoxModel debug_List_libs = new DefaultComboBoxModel();
    public String debug_path = "D:\\Sergey\\ILC_project\\Libs\\VM_python\\conversion";
    public Path debug_path_var = Paths.get(debug_path);
    public String debug_main_name;
    public String debug_current_file;
    
    //Настроечные переменные
    public long prp_fadr[] = new long[15];
    public long prp_alladr[] = new long[10];
    public String prp_com_name;
    public String prp_com_speed;    
    
    //Serial port 
    public Serial_port port;
    public ILC_device_c deviceILC;
    
    //Debug out timer
    Timer DebugTimer;
    
    //Terminal colors
    private Color colorMsg_msg = Color.black;
    private Color colorMsg_good = Color.BLUE;
    private Color colorMsg_error = Color.RED;
    private Color colorMsg_debug = Color.black;
            
    /**
     * Creates new form ILC_main_form
     */
    public ILC_main_form() {
        initComponents();
        start_load_settings(); //Загрузка свойств
        ILC_init_params(); //Инициализация параметров
    }
    
    //Загрузка свойств
    private void start_load_settings() {
        
        //Настроить подсветку синтаксиса
        rSyntaxTextArea_debug.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_PYTHON);
        
        try {
            FileInputStream properties = new FileInputStream(sFileName);
            props.load(properties);
            properties.close();
            
            //Получить настройки порта
            prp_com_name = props.getProperty("COM_NAME");
            prp_com_speed = props.getProperty("COM_SPEED");
            
        } catch (IOException ex) {
            
        }catch (NumberFormatException e) {
            
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser_file = new javax.swing.JFileChooser();
        buttonGroup_debug_gener = new javax.swing.ButtonGroup();
        buttonGroup_debug_lib = new javax.swing.ButtonGroup();
        jFrame_calCalculator = new javax.swing.JFrame();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jTextField_zeroVal = new javax.swing.JTextField();
        jTextField_appVal = new javax.swing.JTextField();
        jTextField_currAppVal = new javax.swing.JTextField();
        jButton_setZero = new javax.swing.JButton();
        jButton_setCurrAppVal = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jTextField_errorVal = new javax.swing.JTextField();
        jButton_getOffsetVal = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jTextField_offsetVal = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jTextField_koefVal = new javax.swing.JTextField();
        jButton_getKoefVal = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel35 = new javax.swing.JLabel();
        jTextField_convFloat = new javax.swing.JTextField();
        jTextField_convUint = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jButton_conv = new javax.swing.JButton();
        jButton_conv1 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel38 = new javax.swing.JLabel();
        jTextField_koefProp = new javax.swing.JTextField();
        jButton_getKoefProp = new javax.swing.JButton();
        jTabbedPane_tabs = new javax.swing.JTabbedPane();
        jPanel_connect = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jButton_checkConn = new javax.swing.JButton();
        jButton_checkConn1 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton_devReset2 = new javax.swing.JButton();
        jButton_resetSettings = new javax.swing.JButton();
        jPanel_PD = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jTextField_block = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField_page = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField_offset = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jTextField_count_bytes = new javax.swing.JTextField();
        jButton_burst_clear = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jButton_burst_read = new javax.swing.JButton();
        jButton_burst_write = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_flash = new javax.swing.JTable();
        jButton_burst_set_val = new javax.swing.JButton();
        jTextField_set_val = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_settings = new javax.swing.JTable();
        jButton_writeSettings = new javax.swing.JButton();
        jButton_readSettings = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton_assignSettings = new javax.swing.JButton();
        jButton_setSaveFile = new javax.swing.JButton();
        jButton_setOpenFile = new javax.swing.JButton();
        jPanel_param_main = new javax.swing.JPanel();
        jButton_readCalPhase = new javax.swing.JButton();
        jButton_writeCalPhase = new javax.swing.JButton();
        jComboBox_calPhaseChoose = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTable_calPhase = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTable_calMain = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable_valPhase = new javax.swing.JTable();
        jLabel18 = new javax.swing.JLabel();
        jButton_writeCalMain = new javax.swing.JButton();
        jButton_readCalMain = new javax.swing.JButton();
        jButton_readVal = new javax.swing.JButton();
        jButton_saveCalPhase = new javax.swing.JButton();
        jButton_openCalPhase = new javax.swing.JButton();
        jButton_openCalMain = new javax.swing.JButton();
        jButton_saveCalMain = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jComboBox_line = new javax.swing.JComboBox<>();
        jButton_setLine = new javax.swing.JButton();
        jButton_openVal = new javax.swing.JButton();
        jButton_saveVal = new javax.swing.JButton();
        jButton_calCalculator1 = new javax.swing.JButton();
        jLabel25 = new javax.swing.JLabel();
        jComboBox_ch = new javax.swing.JComboBox<>();
        jButton_serCh = new javax.swing.JButton();
        jCheckBox_readPeriodic = new javax.swing.JCheckBox();
        jTextField_readPeriod = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jComboBox_chCal = new javax.swing.JComboBox<>();
        jButton_asseptCalibr = new javax.swing.JButton();
        jLabel_readValCounter = new javax.swing.JLabel();
        jPanel_debug = new javax.swing.JPanel();
        jPanel_script = new javax.swing.JPanel();
        rTextScrollPane_debug = new org.fife.ui.rtextarea.RTextScrollPane();
        rSyntaxTextArea_debug = new org.fife.ui.rsyntaxtextarea.RSyntaxTextArea();
        jButton_flashcopy = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton_debug_open = new javax.swing.JButton();
        jButton_debug_save = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton_debug_compile = new javax.swing.JButton();
        jComboBox_debug_libs = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        jRadioButton_debug_c = new javax.swing.JRadioButton();
        jRadioButton_debug_bin = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jRadioButton_debug_stdLib = new javax.swing.JRadioButton();
        jRadioButton_debug_userLib = new javax.swing.JRadioButton();
        jComboBox__debug_memPlace = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane17 = new javax.swing.JScrollPane();
        jList_debug_files = new javax.swing.JList<>();
        jLabel6 = new javax.swing.JLabel();
        jButton_debug_addFile = new javax.swing.JButton();
        jButton_debug_delFile = new javax.swing.JButton();
        jButton_debug_addLibraries = new javax.swing.JButton();
        jButton_debug_clearList = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jButton_debug_main = new javax.swing.JButton();
        jButton_debug_libSave = new javax.swing.JButton();
        jButton_debug_add_current = new javax.swing.JButton();
        jButton_start_code = new javax.swing.JButton();
        jPanel_graphs = new javax.swing.JPanel();
        jPanel_graph = new javax.swing.JPanel();
        jButton_readValGraph = new javax.swing.JButton();
        jTextField_readPeriodGraph = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jTextField_readValGraph = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel_graphCounter = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jTextField_readMultGraph = new javax.swing.JTextField();
        jComboBox_valGraph = new javax.swing.JComboBox<>();
        jComboBox_chGraph = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jComboBox_lineGraph = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jSeparator4 = new javax.swing.JSeparator();
        jButton10 = new javax.swing.JButton();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel_terminal = new javax.swing.JPanel();
        jButton_term_send = new javax.swing.JButton();
        jTextField_term_in = new javax.swing.JTextField();
        jButton_terminal_clear = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextPane_terminal = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jComboBox_comPort = new javax.swing.JComboBox<>();
        jButton_comConnect = new javax.swing.JButton();
        jButton_comDisconnect = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jComboBox_comPortSpeed = new javax.swing.JComboBox<>();
        jButton_safe_settings = new javax.swing.JButton();
        jButton_devReset1 = new javax.swing.JButton();

        jFileChooser_file.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        jFileChooser_file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser_fileActionPerformed(evt);
            }
        });

        jFrame_calCalculator.setMinimumSize(new java.awt.Dimension(418, 350));
        jFrame_calCalculator.setResizable(false);
        jFrame_calCalculator.setType(java.awt.Window.Type.POPUP);

        jLabel9.setText("Значение параметра при нуле:");

        jLabel10.setText("Приложенное значение:");

        jLabel11.setText("Значение параметра при воздействи:");

        jButton2.setText("Рассчитать");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton_setZero.setText("<<");
        jButton_setZero.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setZeroActionPerformed(evt);
            }
        });

        jButton_setCurrAppVal.setText("<<");
        jButton_setCurrAppVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setCurrAppValActionPerformed(evt);
            }
        });

        jLabel12.setText("Общая ошибка %:");

        jButton_getOffsetVal.setText(">>");
        jButton_getOffsetVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_getOffsetValActionPerformed(evt);
            }
        });

        jLabel13.setText("Значение регистра Смещения:");

        jLabel16.setText("Значение регистра Усиления:");

        jButton_getKoefVal.setText(">>");
        jButton_getKoefVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_getKoefValActionPerformed(evt);
            }
        });

        jLabel35.setText("Конвертация uint32_t to Float");

        jLabel36.setText("uint32_t");

        jLabel37.setText("Float");

        jButton_conv.setText(">");
        jButton_conv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_convActionPerformed(evt);
            }
        });

        jButton_conv1.setText("<");
        jButton_conv1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_conv1ActionPerformed(evt);
            }
        });

        jLabel38.setText("Пропорциональный коэффициент");

        jButton_getKoefProp.setText(">>");
        jButton_getKoefProp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_getKoefPropActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame_calCalculatorLayout = new javax.swing.GroupLayout(jFrame_calCalculator.getContentPane());
        jFrame_calCalculator.getContentPane().setLayout(jFrame_calCalculatorLayout);
        jFrame_calCalculatorLayout.setHorizontalGroup(
            jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame_calCalculatorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                        .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_zeroVal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_appVal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_setZero))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                        .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField_errorVal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_offsetVal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_getOffsetVal))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_convUint)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_conv1)
                        .addGap(2, 2, 2)
                        .addComponent(jButton_conv)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel37)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_convFloat, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel35, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jFrame_calCalculatorLayout.createSequentialGroup()
                        .addComponent(jLabel38, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_koefProp, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_getKoefProp))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                                .addComponent(jTextField_koefVal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_getKoefVal))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame_calCalculatorLayout.createSequentialGroup()
                                .addComponent(jTextField_currAppVal, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_setCurrAppVal))
                            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jFrame_calCalculatorLayout.setVerticalGroup(
            jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame_calCalculatorLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jTextField_zeroVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_setZero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField_appVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(jTextField_currAppVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_setCurrAppVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jTextField_errorVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_getOffsetVal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField_offsetVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_koefVal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_getKoefVal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_koefProp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_getKoefProp))
                .addGap(14, 14, 14)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(jFrame_calCalculatorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_convFloat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField_convUint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_conv)
                    .addComponent(jButton_conv1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сервисная программа Immo");

        jTabbedPane_tabs.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        jTabbedPane_tabs.setMinimumSize(new java.awt.Dimension(316, 100));
        jTabbedPane_tabs.setPreferredSize(new java.awt.Dimension(316, 100));

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Управление"));
        jPanel12.setToolTipText("");

        jButton_checkConn.setText("Проверка связи");
        jButton_checkConn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkConnActionPerformed(evt);
            }
        });

        jButton_checkConn1.setText("Переключить на отладку");
        jButton_checkConn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_checkConn1ActionPerformed(evt);
            }
        });

        jButton1.setText("Переключить терминал");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton_devReset2.setText("Перезагрузить устройство");
        jButton_devReset2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_devReset2ActionPerformed(evt);
            }
        });

        jButton_resetSettings.setText("Сбросить настройки");
        jButton_resetSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_resetSettingsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jButton_devReset2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel12Layout.createSequentialGroup()
                            .addComponent(jButton_checkConn, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton_checkConn1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButton_resetSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_checkConn, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_checkConn1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_devReset2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_resetSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel_connectLayout = new javax.swing.GroupLayout(jPanel_connect);
        jPanel_connect.setLayout(jPanel_connectLayout);
        jPanel_connectLayout.setHorizontalGroup(
            jPanel_connectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_connectLayout.setVerticalGroup(
            jPanel_connectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane_tabs.addTab("Устройство", jPanel_connect);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Адресация"));

        jTextField_block.setText("0");

        jLabel21.setText("Block");

        jTextField_page.setText("0");

        jLabel22.setText("Page");

        jLabel23.setText("Offset");

        jTextField_offset.setText("0");

        jLabel17.setText("Количество байт");

        jTextField_count_bytes.setText("100");
        jTextField_count_bytes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField_count_bytesActionPerformed(evt);
            }
        });

        jButton_burst_clear.setText("Очистить");
        jButton_burst_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_burst_clearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_block, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_page, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel23)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_offset, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField_count_bytes, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_burst_clear)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_block, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField_page, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField_offset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(jLabel17)
                    .addComponent(jTextField_count_bytes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_burst_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("Чтение и запись массива"));

        jButton_burst_read.setText("Читать");
        jButton_burst_read.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_burst_readActionPerformed(evt);
            }
        });

        jButton_burst_write.setText("Писать");
        jButton_burst_write.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_burst_writeActionPerformed(evt);
            }
        });

        jTable_flash.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Offset", "Значение", "Символ"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTable_flash);

        jButton_burst_set_val.setText("Установить");
        jButton_burst_set_val.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_burst_set_valActionPerformed(evt);
            }
        });

        jTextField_set_val.setText("0x00");

        jLabel20.setText("Значение");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField_set_val, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_burst_set_val)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_burst_write)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_burst_read)
                        .addGap(4, 4, 4))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 358, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(jTextField_set_val)
                    .addComponent(jButton_burst_set_val, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_burst_write, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_burst_read, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel_PDLayout = new javax.swing.GroupLayout(jPanel_PD);
        jPanel_PD.setLayout(jPanel_PDLayout);
        jPanel_PDLayout.setHorizontalGroup(
            jPanel_PDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel_PDLayout.setVerticalGroup(
            jPanel_PDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_PDLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_tabs.addTab("Память", jPanel_PD);

        jTable_settings.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "MAC адрес", "--:--:--:--:--:--", "MAC", "6"},
                {"2", "Включить DHCP", "0", "byte", "1"},
                {"3", "Адрес IP устройства (ETH)", "---.---.---.---", "IP", "4"},
                {"4", "Адрес IP шлюза  (ETH)", "---.---.---.---", "IP", "4"},
                {"5", "Маска подсети  (ETH)", "---.---.---.---", "IP", "4"},
                {"6", "Домен NTP сервера  (ETH)", "-------", "String", "40"},
                {"7", "Адрес IP DNS сервера  (ETH)", "---.---.---.---", "IP", "4"},
                {"8", "Адрес IP MQTT брокера", "---.---.---.---", "IP", "4"},
                {"9", "Домен MQTT брокера", "-------", "String", "40"},
                {"10", "Адресация MQTT брокера", "0", "byte", "1"},
                {"11", "MQTT порт", "0", "word", "2"},
                {"12", "MQTT пользователь", "-------", "String", "20"},
                {"13", "MQTT пароль", "-------", "String", "20"},
                {"14", "MQTT QoS", "0", "byte", "1"},
                {"15", "Период пакета EMS", "0", "word", "2"},
                {"16", "Режим передачи EMS", "0", "byte", "1"},
                {"17", "Включить каналы EMS", "0", "byte", "1"},
                {"18", "Автостарт PyVM", "0", "byte", "1"}
            },
            new String [] {
                "ID", "Параметр", "Значение", "Тип", "Длина byte"
            }
        ));
        jScrollPane1.setViewportView(jTable_settings);
        if (jTable_settings.getColumnModel().getColumnCount() > 0) {
            jTable_settings.getColumnModel().getColumn(0).setMinWidth(10);
            jTable_settings.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTable_settings.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable_settings.getColumnModel().getColumn(1).setMinWidth(300);
            jTable_settings.getColumnModel().getColumn(1).setPreferredWidth(350);
            jTable_settings.getColumnModel().getColumn(1).setMaxWidth(400);
            jTable_settings.getColumnModel().getColumn(3).setMinWidth(40);
            jTable_settings.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable_settings.getColumnModel().getColumn(3).setMaxWidth(60);
            jTable_settings.getColumnModel().getColumn(4).setMinWidth(50);
            jTable_settings.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTable_settings.getColumnModel().getColumn(4).setMaxWidth(80);
        }

        jButton_writeSettings.setText("Записать");
        jButton_writeSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_writeSettingsActionPerformed(evt);
            }
        });

        jButton_readSettings.setText("Считать");
        jButton_readSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_readSettingsActionPerformed(evt);
            }
        });

        jLabel1.setText("Список настроек");

        jButton_assignSettings.setText("Применить настройки");
        jButton_assignSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_assignSettingsActionPerformed(evt);
            }
        });

        jButton_setSaveFile.setText("Сохранить в файл");
        jButton_setSaveFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setSaveFileActionPerformed(evt);
            }
        });

        jButton_setOpenFile.setText("Открыть из файла");
        jButton_setOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setOpenFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton_setOpenFile, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_setSaveFile, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton_assignSettings)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_writeSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_readSettings, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_writeSettings)
                    .addComponent(jButton_readSettings)
                    .addComponent(jButton_assignSettings)
                    .addComponent(jButton_setSaveFile)
                    .addComponent(jButton_setOpenFile))
                .addContainerGap())
        );

        jTabbedPane_tabs.addTab("Настройки", jPanel1);

        jButton_readCalPhase.setText("Считать");
        jButton_readCalPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_readCalPhaseActionPerformed(evt);
            }
        });

        jButton_writeCalPhase.setText("Записать");
        jButton_writeCalPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_writeCalPhaseActionPerformed(evt);
            }
        });

        jComboBox_calPhaseChoose.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox_calPhaseChoose.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Параметры фазы A", "Параметры фазы B", "Параметры фазы C" }));

        jLabel2.setText("Фаза:");

        jTable_calPhase.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTable_calPhase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"11", "WARTU", "0xFFFFFFFF", "U пропроциональный коэффициент"},
                {"12", "WARTI", "0xFFFFFFFF", "I пропроциональный коэффициент"},
                {"13", "WARPT", "0xFFFFFFFF", "P пропроциональный коэффициент"},
                {"14", "WARQT", "0xFFFFFFFF", "Q пропроциональный коэффициент"},
                {"15", "WWARTU", "0xFFFFFFFF", "U смещение"},
                {"16", "WWARTI", "0xFFFFFFFF", "I смещение"},
                {"17", "WWARPT", "0xFFFFFFFF", "P смещение"},
                {"18", "WWARQT", "0xFFFFFFFF", "Q смещение"}
            },
            new String [] {
                "ID", "Регистр", "Значение", "Описание"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_calPhase.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_calPhaseMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jTable_calPhase);
        if (jTable_calPhase.getColumnModel().getColumnCount() > 0) {
            jTable_calPhase.getColumnModel().getColumn(0).setMinWidth(20);
            jTable_calPhase.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTable_calPhase.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable_calPhase.getColumnModel().getColumn(1).setMinWidth(50);
            jTable_calPhase.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable_calPhase.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable_calPhase.getColumnModel().getColumn(2).setMinWidth(60);
            jTable_calPhase.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable_calPhase.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jLabel8.setText("Общие параметры:");

        jTable_calMain.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTable_calMain.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "CTHH", "0xFFFFFFFF", "См. даташит"},
                {"2", "CTHL", "0xFFFFFFFF", "См. даташит"},
                {"3", "WAEC0", "0xFFFFFFFF", "См. даташит"},
                {"4", "MTPARA0", "0xFFFFFFFF", "См. даташит"},
                {"5", "MTPARA1", "0xFFFFFFFF", "См. даташит"},
                {"6", "MTPARA2", "0xFFFFFFFF", "См. даташит"},
                {"7", "ANCtrl0", "0xFFFFFFFF", "См. даташит"},
                {"8", "ANCtrl1", "0xFFFFFFFF", "См. даташит"},
                {"9", "ANCtrl2", "0xFFFFFFFF", "См. даташит"},
                {"10", "ANCtrl3", "0xFFFFFFFF", "См. даташит"},
                {"19", "WARTIN", "0xFFFFFFFF", "I нетрали проп. коэффициент"},
                {"20", "WWARTIN", "0xFFFFFFFF", "I нейтрали смещение"},
                {"31", "PROP_P", "0xFFFFFFFF", "P выходной проп. коэф."},
                {"32", "PROP_RP", "0xFFFFFFFF", "Q выходной проп. коэф."},
                {"33", "PROP_U", "0xFFFFFFFF", "U выходной проп. коэф."},
                {"34", "PROP_I", "0xFFFFFFFF", "I выходной проп. коэф."},
                {"35", "PROP_FREQ", "0xFFFFFFFF", "FREQ выходной проп. коэф."},
                {"36", "PROP_COSFI", "0xFFFFFFFF", "COSfi выходной проп. коэф."},
                {"37", "THRDI_DETECT", "0xFFFFFFFF", "Граница детектировани I"},
                {"38", "THRDM_DETECT", "0xFFFFFFFF", "Граница фиксирования потреб."}
            },
            new String [] {
                "ID", "Рег./Парам.", "Значение", "Описание"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jTable_calMain.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_calMainMouseClicked(evt);
            }
        });
        jScrollPane7.setViewportView(jTable_calMain);
        if (jTable_calMain.getColumnModel().getColumnCount() > 0) {
            jTable_calMain.getColumnModel().getColumn(0).setMinWidth(20);
            jTable_calMain.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTable_calMain.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable_calMain.getColumnModel().getColumn(1).setMinWidth(50);
            jTable_calMain.getColumnModel().getColumn(1).setPreferredWidth(70);
            jTable_calMain.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable_calMain.getColumnModel().getColumn(2).setMinWidth(60);
            jTable_calMain.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable_calMain.getColumnModel().getColumn(2).setMaxWidth(90);
        }

        jTable_valPhase.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jTable_valPhase.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"1", "RMSV", "0.0", "float", "RMS напряжение", "CH_0", "Line_A"},
                {"2", "RMSI", "0.0", "float", "RMS ток", "CH_0", "Line_A"},
                {"3", "RMSP", "0.0", "float", "RMS акт. мощность", "CH_0", "Line_A"},
                {"4", "RMSRP", "0.0", "float", "RMS реакт. мощность", "CH_0", "Line_A"},
                {"5", "COSFI", "0.0", "float", "cosF коэф. мощности", "CH_0", "Line_A"},
                {"6", "CONSSP", "0", "uint64_t", "Потербление полн. мощность", "CH_0", "Line_A"},
                {"7", "CONSP", "0", "uint64_t", "Потребление акт. мощность", "CH_0", "Line_A"},
                {"8", "CONSRP", "0", "uint64_t", "Потребление реакт. мощность", "CH_0", "Line_A"},
                {"9", "FREQ", "0.0", "float", "Частота фазы", "CH_0", "Line_A"},
                {"2", "RMSNI", "0.0", "float", "RMS ток нулевого провода", "CH_0", "Line_N"},
                {"5", "CONSSP", "0", "uint64_t", "Потребление полн. мощность канала", "CH_0", "Line_S"},
                {"7", "CONSP", "0", "uint64_t", "Потребление акт. мощность канала", "CH_0", "Line_S"},
                {"8", "CONSRP", "0", "uint64_t", "Потребление реакт. мощность канала", "CH_0", "Line_S"},
                {"6", "COSFIS", "0.0", "float", "cosF коэф. мощности канала", "CH_0", "Line_S"}
            },
            new String [] {
                "ID", "Параметр", "Значение", "Тип", "Описание", "Канал", "Линия"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane8.setViewportView(jTable_valPhase);
        if (jTable_valPhase.getColumnModel().getColumnCount() > 0) {
            jTable_valPhase.getColumnModel().getColumn(0).setMinWidth(10);
            jTable_valPhase.getColumnModel().getColumn(0).setPreferredWidth(25);
            jTable_valPhase.getColumnModel().getColumn(0).setMaxWidth(30);
            jTable_valPhase.getColumnModel().getColumn(1).setMinWidth(50);
            jTable_valPhase.getColumnModel().getColumn(1).setPreferredWidth(60);
            jTable_valPhase.getColumnModel().getColumn(1).setMaxWidth(100);
            jTable_valPhase.getColumnModel().getColumn(2).setMinWidth(60);
            jTable_valPhase.getColumnModel().getColumn(2).setPreferredWidth(80);
            jTable_valPhase.getColumnModel().getColumn(2).setMaxWidth(90);
            jTable_valPhase.getColumnModel().getColumn(3).setMinWidth(30);
            jTable_valPhase.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable_valPhase.getColumnModel().getColumn(3).setMaxWidth(80);
            jTable_valPhase.getColumnModel().getColumn(5).setMinWidth(40);
            jTable_valPhase.getColumnModel().getColumn(5).setPreferredWidth(50);
            jTable_valPhase.getColumnModel().getColumn(5).setMaxWidth(60);
            jTable_valPhase.getColumnModel().getColumn(6).setMinWidth(40);
            jTable_valPhase.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTable_valPhase.getColumnModel().getColumn(6).setMaxWidth(60);
        }

        jLabel18.setText("Текущие значения:");

        jButton_writeCalMain.setText("Записать");
        jButton_writeCalMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_writeCalMainActionPerformed(evt);
            }
        });

        jButton_readCalMain.setText("Считать");
        jButton_readCalMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_readCalMainActionPerformed(evt);
            }
        });

        jButton_readVal.setText("Считать");
        jButton_readVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_readValActionPerformed(evt);
            }
        });

        jButton_saveCalPhase.setText("Сохранить");
        jButton_saveCalPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveCalPhaseActionPerformed(evt);
            }
        });

        jButton_openCalPhase.setText("Открыть");
        jButton_openCalPhase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_openCalPhaseActionPerformed(evt);
            }
        });

        jButton_openCalMain.setText("Открыть");
        jButton_openCalMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_openCalMainActionPerformed(evt);
            }
        });

        jButton_saveCalMain.setText("Сохранить");
        jButton_saveCalMain.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveCalMainActionPerformed(evt);
            }
        });

        jLabel24.setText("Линия:");

        jComboBox_line.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Line_A", "Line_B", "Line_C", "Line_N", "Line_S" }));

        jButton_setLine.setText("<<");
        jButton_setLine.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_setLineActionPerformed(evt);
            }
        });

        jButton_openVal.setText("Открыть");
        jButton_openVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_openValActionPerformed(evt);
            }
        });

        jButton_saveVal.setText("Сохранить");
        jButton_saveVal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_saveValActionPerformed(evt);
            }
        });

        jButton_calCalculator1.setText("Калькулятор");
        jButton_calCalculator1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_calCalculator1ActionPerformed(evt);
            }
        });

        jLabel25.setText("Канал:");

        jComboBox_ch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CH_0", "CH_1", "CH_2", "CH_3" }));

        jButton_serCh.setText("<<");
        jButton_serCh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_serChActionPerformed(evt);
            }
        });

        jCheckBox_readPeriodic.setText("Периодично");

        jTextField_readPeriod.setText("10");

        jLabel19.setText("сек.");

        jLabel26.setText("Канал:");

        jComboBox_chCal.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jComboBox_chCal.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CH_0", "CH_1", "CH_2", "CH_3" }));

        jButton_asseptCalibr.setText("Применить");
        jButton_asseptCalibr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_asseptCalibrActionPerformed(evt);
            }
        });

        jLabel_readValCounter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_readValCounter.setText("0");

        javax.swing.GroupLayout jPanel_param_mainLayout = new javax.swing.GroupLayout(jPanel_param_main);
        jPanel_param_main.setLayout(jPanel_param_mainLayout);
        jPanel_param_mainLayout.setHorizontalGroup(
            jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jCheckBox_readPeriodic, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel_readValCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_param_mainLayout.createSequentialGroup()
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton_saveVal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox_ch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBox_line, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton_setLine, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton_serCh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                                .addComponent(jTextField_readPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel19))
                            .addComponent(jButton_openVal, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_readVal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_calPhaseChoose, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_chCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_readCalPhase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_writeCalPhase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_saveCalPhase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_openCalPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton_calCalculator1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_readCalMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_saveCalMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_openCalMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_writeCalMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton_asseptCalibr, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14))
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel_param_mainLayout.setVerticalGroup(
            jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addGap(2, 2, 2)
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jButton_asseptCalibr, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_calCalculator1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_openCalMain, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_saveCalMain, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_writeCalMain, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_readCalMain, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_chCal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel26))
                    .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBox_calPhaseChoose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addComponent(jButton_openCalPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_saveCalPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_writeCalPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_readCalPhase, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel18)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_param_mainLayout.createSequentialGroup()
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel_readValCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jCheckBox_readPeriodic, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextField_readPeriod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_readVal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jComboBox_line, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_setLine, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_param_mainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jComboBox_ch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_serCh, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_openVal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_saveVal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
        );

        jTabbedPane_tabs.addTab("Калибровка", jPanel_param_main);

        jPanel_script.setBorder(javax.swing.BorderFactory.createTitledBorder("Текст скрипта"));

        rTextScrollPane_debug.setAutoscrolls(true);
        rTextScrollPane_debug.setLineNumbersEnabled(true);

        rSyntaxTextArea_debug.setColumns(20);
        rSyntaxTextArea_debug.setRows(5);
        rTextScrollPane_debug.setViewportView(rSyntaxTextArea_debug);

        javax.swing.GroupLayout jPanel_scriptLayout = new javax.swing.GroupLayout(jPanel_script);
        jPanel_script.setLayout(jPanel_scriptLayout);
        jPanel_scriptLayout.setHorizontalGroup(
            jPanel_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_scriptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rTextScrollPane_debug, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel_scriptLayout.setVerticalGroup(
            jPanel_scriptLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_scriptLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rTextScrollPane_debug, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jButton_flashcopy.setText("Загрузить скрипт");
        jButton_flashcopy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_flashcopyActionPerformed(evt);
            }
        });

        jButton5.setText("Приостановить");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton_debug_open.setText("Открыть");
        jButton_debug_open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_openActionPerformed(evt);
            }
        });

        jButton_debug_save.setText("Сохранить как");
        jButton_debug_save.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_saveActionPerformed(evt);
            }
        });

        jButton9.setText("Остановить");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton_debug_compile.setText("Компилировать");
        jButton_debug_compile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_compileActionPerformed(evt);
            }
        });

        jComboBox_debug_libs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox_debug_libsActionPerformed(evt);
            }
        });

        jPanel24.setBorder(javax.swing.BorderFactory.createTitledBorder("Опции"));

        buttonGroup_debug_gener.add(jRadioButton_debug_c);
        jRadioButton_debug_c.setSelected(true);
        jRadioButton_debug_c.setText("Си файл");

        buttonGroup_debug_gener.add(jRadioButton_debug_bin);
        jRadioButton_debug_bin.setText("BIN файл");

        jLabel3.setText("Генерировать:");

        jLabel4.setText("Сохранять:");

        buttonGroup_debug_lib.add(jRadioButton_debug_stdLib);
        jRadioButton_debug_stdLib.setText("Std library");

        buttonGroup_debug_lib.add(jRadioButton_debug_userLib);
        jRadioButton_debug_userLib.setSelected(true);
        jRadioButton_debug_userLib.setText("User library");

        jComboBox__debug_memPlace.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ram", "flash", "extern", "sd_card" }));
        jComboBox__debug_memPlace.setSelectedIndex(1);

        jLabel5.setText("Память хранения");

        jList_debug_files.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList_debug_filesMouseClicked(evt);
            }
        });
        jScrollPane17.setViewportView(jList_debug_files);

        jLabel6.setText("Список файлов:");

        jButton_debug_addFile.setText("Добавить");
        jButton_debug_addFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_addFileActionPerformed(evt);
            }
        });

        jButton_debug_delFile.setText("Убрать");
        jButton_debug_delFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_delFileActionPerformed(evt);
            }
        });

        jButton_debug_addLibraries.setText("Библиотеки");
        jButton_debug_addLibraries.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_addLibrariesActionPerformed(evt);
            }
        });

        jButton_debug_clearList.setText("Очистить");
        jButton_debug_clearList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_clearListActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(2, 2, 2))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jComboBox__debug_memPlace, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel4))
                                .addComponent(jLabel6)
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addComponent(jRadioButton_debug_bin)
                                    .addGap(23, 23, 23)
                                    .addComponent(jRadioButton_debug_stdLib))
                                .addGroup(jPanel24Layout.createSequentialGroup()
                                    .addComponent(jRadioButton_debug_c)
                                    .addGap(27, 27, 27)
                                    .addComponent(jRadioButton_debug_userLib))))
                        .addContainerGap(28, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jButton_debug_delFile, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_debug_addFile, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(jButton_debug_clearList, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_debug_addLibraries, javax.swing.GroupLayout.PREFERRED_SIZE, 94, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_debug_bin, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_debug_stdLib, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton_debug_c, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton_debug_userLib, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBox__debug_memPlace, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane17, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_debug_clearList)
                    .addComponent(jButton_debug_addLibraries))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_debug_delFile)
                    .addComponent(jButton_debug_addFile))
                .addContainerGap())
        );

        jLabel7.setText("Библиотеки");

        jButton_debug_main.setText("Исходный текст");
        jButton_debug_main.setEnabled(false);
        jButton_debug_main.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_mainActionPerformed(evt);
            }
        });

        jButton_debug_libSave.setText("Сохранить");
        jButton_debug_libSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_libSaveActionPerformed(evt);
            }
        });

        jButton_debug_add_current.setText("Добавить текущий");
        jButton_debug_add_current.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_debug_add_currentActionPerformed(evt);
            }
        });

        jButton_start_code.setText("Запустить");
        jButton_start_code.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_start_codeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_debugLayout = new javax.swing.GroupLayout(jPanel_debug);
        jPanel_debug.setLayout(jPanel_debugLayout);
        jPanel_debugLayout.setHorizontalGroup(
            jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_debugLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_debugLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox_debug_libs, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_debug_main)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_debug_add_current)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton_start_code, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_flashcopy, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel_debugLayout.createSequentialGroup()
                        .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel_script, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel_debugLayout.createSequentialGroup()
                                .addComponent(jButton_debug_compile)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_debug_save, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_debug_libSave, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton_debug_open, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14))))
        );
        jPanel_debugLayout.setVerticalGroup(
            jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_debugLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton_debug_main, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_debug_add_current, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_start_code, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton_flashcopy, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jComboBox_debug_libs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_debugLayout.createSequentialGroup()
                        .addComponent(jPanel_script, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_debugLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton_debug_compile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_debug_save, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_debug_libSave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton_debug_open, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane_tabs.addTab("Скрипт", jPanel_debug);

        jPanel_graphs.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel_graphsComponentShown(evt);
            }
        });

        jPanel_graph.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel_graphLayout = new javax.swing.GroupLayout(jPanel_graph);
        jPanel_graph.setLayout(jPanel_graphLayout);
        jPanel_graphLayout.setHorizontalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 987, Short.MAX_VALUE)
        );
        jPanel_graphLayout.setVerticalGroup(
            jPanel_graphLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
        );

        jButton_readValGraph.setText("Старт");
        jButton_readValGraph.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_readValGraphActionPerformed(evt);
            }
        });

        jTextField_readPeriodGraph.setText("10");

        jLabel27.setText("сек.");

        jLabel28.setText("Параметр:");

        jTextField_readValGraph.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jTextField_readValGraph.setText("10");

        jLabel31.setText("Значение");

        jLabel32.setText("Счетчик:");

        jLabel_graphCounter.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel_graphCounter.setText("0");

        jLabel33.setText("Период");

        jLabel34.setText("Масштаб");

        jTextField_readMultGraph.setText("1");

        jComboBox_valGraph.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RMSV", "RMSI", "RMSP", "RMSRP", "COSFI", "CONSSP", "CONSP", "CONSRP", "FREQ" }));

        jComboBox_chGraph.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CH_0", "CH_1", "CH_2", "CH_3" }));

        jLabel29.setText("Канал:");

        jLabel30.setText("Линия:");

        jComboBox_lineGraph.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Line_A", "Line_B", "Line_C", "Line_N", "Line_S" }));

        javax.swing.GroupLayout jPanel_graphsLayout = new javax.swing.GroupLayout(jPanel_graphs);
        jPanel_graphs.setLayout(jPanel_graphsLayout);
        jPanel_graphsLayout.setHorizontalGroup(
            jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_graphsLayout.createSequentialGroup()
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_valGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel29)
                            .addComponent(jComboBox_chGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox_lineGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                                .addGap(84, 84, 84)
                                .addComponent(jLabel34)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel31))
                            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                                .addComponent(jLabel33, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(jTextField_readMultGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField_readValGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                                .addComponent(jTextField_readPeriodGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel27)))
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel_graphCounter, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel32)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton_readValGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(455, 455, 455))
                    .addGroup(jPanel_graphsLayout.createSequentialGroup()
                        .addComponent(jPanel_graph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel_graphsLayout.setVerticalGroup(
            jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_graphsLayout.createSequentialGroup()
                .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_graphsLayout.createSequentialGroup()
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel33)
                            .addComponent(jLabel34)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_graphsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox_valGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_chGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_lineGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_readPeriodGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(jTextField_readMultGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField_readValGraph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel_graphCounter)))
                    .addGroup(jPanel_graphsLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton_readValGraph, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_graph, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane_tabs.addTab("Графики", jPanel_graphs);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Настройки клиента"));
        jPanel4.setToolTipText("");

        jLabel39.setText("Адрес брокера");

        jLabel40.setText("Логин");

        jLabel41.setText("Пароль");

        jTextField2.setText("111");

        jTextField3.setText("111");

        jLabel42.setText("Порт");

        jTextField4.setText("1883");

        jButton4.setText("Подключиться");

        jButton6.setText("Сохранить");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel39)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(50, 50, 50)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel41))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel42)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton6)
                    .addComponent(jButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Топики"));

        jLabel43.setText("Имя топика");

        jLabel44.setText("Имя топика");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane4.setViewportView(jTextArea1);

        jButton7.setText("Отправить");

        jButton8.setText("Подписаться");

        jScrollPane5.setViewportView(jList1);

        jButton10.setText("Отписаться");

        jLabel45.setText("Публикация");

        jLabel46.setText("Подписка");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(jLabel44)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane4))
                        .addContainerGap())
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel43)
                            .addComponent(jLabel46))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField5)
                            .addComponent(jSeparator4)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel45)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton8)
                                .addContainerGap())
                            .addComponent(jSeparator5)))))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel46)
                    .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton8)
                            .addComponent(jButton10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel45, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 588, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPane_tabs.addTab("MQTT", jPanel3);

        jPanel_terminal.setBorder(javax.swing.BorderFactory.createTitledBorder("Терминал"));

        jButton_term_send.setText("Отправить");
        jButton_term_send.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_term_sendActionPerformed(evt);
            }
        });

        jTextField_term_in.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField_term_inMouseClicked(evt);
            }
        });

        jButton_terminal_clear.setText("Очистить");
        jButton_terminal_clear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_terminal_clearActionPerformed(evt);
            }
        });

        jTextPane_terminal.setBackground(new java.awt.Color(204, 204, 204));
        jScrollPane3.setViewportView(jTextPane_terminal);

        javax.swing.GroupLayout jPanel_terminalLayout = new javax.swing.GroupLayout(jPanel_terminal);
        jPanel_terminal.setLayout(jPanel_terminalLayout);
        jPanel_terminalLayout.setHorizontalGroup(
            jPanel_terminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_terminalLayout.createSequentialGroup()
                .addComponent(jTextField_term_in, javax.swing.GroupLayout.DEFAULT_SIZE, 881, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_terminal_clear, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_term_send)
                .addContainerGap())
            .addComponent(jScrollPane3)
        );
        jPanel_terminalLayout.setVerticalGroup(
            jPanel_terminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_terminalLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_terminalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton_term_send)
                    .addComponent(jButton_terminal_clear)
                    .addComponent(jTextField_term_in, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jLabel14.setText("COM порт");

        jComboBox_comPort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COM70" }));
        jComboBox_comPort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox_comPortMouseClicked(evt);
            }
        });

        jButton_comConnect.setText("Подлючить");
        jButton_comConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_comConnectActionPerformed(evt);
            }
        });

        jButton_comDisconnect.setText("Отключить");
        jButton_comDisconnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_comDisconnectActionPerformed(evt);
            }
        });

        jLabel15.setText("Скорость");

        jComboBox_comPortSpeed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "460800", "230400", "115200", "9600" }));
        jComboBox_comPortSpeed.setSelectedIndex(2);
        jComboBox_comPortSpeed.setToolTipText("");

        jButton_safe_settings.setText("Сохранить");
        jButton_safe_settings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_safe_settingsActionPerformed(evt);
            }
        });

        jButton_devReset1.setText("Перезагрузить устройство");
        jButton_devReset1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton_devReset1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_comPort, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jButton_comConnect, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton_comDisconnect, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboBox_comPortSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton_safe_settings, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157)
                .addComponent(jButton_devReset1)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBox_comPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_comConnect)
                    .addComponent(jButton_comDisconnect)
                    .addComponent(jLabel15)
                    .addComponent(jComboBox_comPortSpeed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton_safe_settings)
                    .addComponent(jButton_devReset1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jTabbedPane_tabs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel_terminal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane_tabs, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel_terminal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane_tabs.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Отправка из терминала
    private void jButton_term_sendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_term_sendActionPerformed
        
    }//GEN-LAST:event_jButton_term_sendActionPerformed
    
    //Очистить окно терминала
    private void jButton_terminal_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_terminal_clearActionPerformed
        jTextPane_terminal.setText("");
    }//GEN-LAST:event_jButton_terminal_clearActionPerformed

    //Нажатие на текст
    private void jTextField_term_inMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField_term_inMouseClicked
        jTextField_term_in.selectAll();
    }//GEN-LAST:event_jTextField_term_inMouseClicked

    private void writeCal(JTable table, int channel, int line) {
        int calID;
        byte[] valBuf = new byte[4];
        String str;

        for (int i = 0; i < table.getRowCount(); i++) {
            try {
                calID = Integer.parseInt(table.getValueAt(i, 0).toString());

                str = table.getValueAt(i, 2).toString();
                if (str.equals("ERROR"))
                {
                    terminalAddStr(table.getValueAt(i, 1).toString() + " ошибка значения", colorMsg_error);
                    break;
                }
                Long val = Long.parseLong(str.substring(2, str.length()), 16);
                valBuf = deviceILC.LongToBytes(val);

                if (deviceILC.writeCal(calID, channel, line, valBuf) == true) {
                    terminalAddStr("Регистр :"+table.getValueAt(i, 1).toString() + " записан", colorMsg_good);
                } else {
                    terminalAddStr(table.getValueAt(i, 1).toString() + " ошибка записи", colorMsg_error);
                }

                Thread.sleep(100);

            } catch (InterruptedException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    //Записать основные параметры
    private void jButton_writeCalPhaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_writeCalPhaseActionPerformed
        int channel = jComboBox_chCal.getSelectedIndex();
        int line = jComboBox_calPhaseChoose.getSelectedIndex();

        writeCal(jTable_calPhase, channel, line);
        terminalAddStr("Данные записаны", colorMsg_msg);
    }//GEN-LAST:event_jButton_writeCalPhaseActionPerformed

    Timer timerCalIO;
    int channel;
    int line;
    
    //Считать основные параметры
    private void jButton_readCalPhaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_readCalPhaseActionPerformed
//        int channel = jComboBox_chCal.getSelectedIndex();
//        int line = jComboBox_calPhaseChoose.getSelectedIndex();
//        
//        readCal(jTable_calPhase, channel, line);
//        terminalAddStr("Данные считаны", colorMsg_msg);

        readCount = 0;
        channel = jComboBox_chCal.getSelectedIndex();
        line = jComboBox_calPhaseChoose.getSelectedIndex();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerCalIO.stop();
                readCal(jTable_calPhase, channel, line, readCount);
                if ((jTable_calPhase.getRowCount()-1) > readCount) {
                    readCount++;
                    timerCalIO.start();
                }else{
                    terminalAddStr("Регистры считаны***************************************", colorMsg_good);
                    timerCalIO = null;
                }
            }
        };

        readCal(jTable_calPhase, channel, line, readCount);
        readCount++;

        timerCalIO = new Timer(50, listener);
        timerCalIO.start();
    }//GEN-LAST:event_jButton_readCalPhaseActionPerformed
    
    private void readCal(JTable table, int channel, int line, int pos) {

        ILC_device_c.ILC_buf_c retBuf;
        byte[] longBuf = new byte[4];

        int calID = Integer.parseInt(table.getValueAt(pos, 0).toString());

        retBuf = deviceILC.readCal(calID, channel, line);

        if (retBuf.status == ILC_device_c.USBC_RET_OK) {
            //if (retBuf.Len == 4) {
                System.arraycopy(retBuf.Data, 0, longBuf, 0, longBuf.length);
                long val = deviceILC.bytesToLong(longBuf);

                String str = String.format("0x%08X", val);
                table.setValueAt(str, pos, 2);
                terminalAddStr("Регистр :" + table.getValueAt(pos, 1).toString() + " считан", colorMsg_good);
            //}
        } else if (retBuf.status == ILC_device_c.USBC_RET_ERROR) {
            table.setValueAt("ERROR", pos, 2);
            terminalAddStr(table.getValueAt(pos, 1).toString() + " ошибка чтения, пробуем еще...", colorMsg_error);
            readCal(table, channel, line, pos);
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void jButton_burst_clearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_burst_clearActionPerformed
        flash_table.setRowCount(0);
    }//GEN-LAST:event_jButton_burst_clearActionPerformed

    private void jButton_burst_set_valActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_burst_set_valActionPerformed
        int[] row_indexes=jTable_flash.getSelectedRows();
        for(int i=0;i<row_indexes.length;i++){
            flash_table.setValueAt(jTextField_set_val.getText(), row_indexes[i], 1);
        }
    }//GEN-LAST:event_jButton_burst_set_valActionPerformed

    //Записать массив данных
    private void jButton_burst_writeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_burst_writeActionPerformed
        int block = Integer.parseInt(jTextField_block.getText());
        int page = Integer.parseInt(jTextField_page.getText());
        int offset = Integer.parseInt(jTextField_offset.getText());
        int len = Integer.parseInt(jTextField_count_bytes.getText());
        byte[] data = new byte[256];
        
        if (offset > 2048) {
            terminalAddStr("Max offset 2048", colorMsg_error);
            return;
        }
        
        if (page > 64) {
            terminalAddStr("Max page count 64", colorMsg_error);
            return;
        }
        
        if (block > 1024) {
            terminalAddStr("Max block count 1024", colorMsg_error);
            return;
        }

        if (len == 0) {
            terminalAddStr("Len zero value", colorMsg_error);
            return;
        }

        if (len > 256) {
            terminalAddStr("Max pack 256 bytes", colorMsg_error);
            return;
        }
        
        if (len > flash_table.getRowCount())
        {
            terminalAddStr("Len to write more then row count", colorMsg_error);
            return;
        }
        
        //Записать массив
        for (int i=0; i<len; i++) {
            data[i] = (byte) StrHexToLong(flash_table.getValueAt(i, 1).toString());
        }
        
        Buf_class retVal = deviceILC.writeNandData(block, page, offset, data, len);
        
        if (retVal.recived == true) {
            if (retVal.retStatus == ILC_device_c.USBC_RET_OK) {
                terminalAddStr("Write data OK", colorMsg_good);
            } else {
                terminalAddStr("Write data ERROR", colorMsg_error);
            }
        } else {
            terminalAddStr("ERROR I/O", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_burst_writeActionPerformed

    private void jTextField_count_bytesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField_count_bytesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField_count_bytesActionPerformed

    //Читать массив
    private void jButton_burst_readActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_burst_readActionPerformed
        flash_table.setRowCount(0);
        
        int block = Integer.parseInt(jTextField_block.getText());
        int page = Integer.parseInt(jTextField_page.getText());
        int offset = Integer.parseInt(jTextField_offset.getText());
        int len = Integer.parseInt(jTextField_count_bytes.getText());

        if (offset > 2048) {
            terminalAddStr("Max offset 2048", colorMsg_error);
            return;
        }
        
        if (page > 64) {
            terminalAddStr("Max page count 64", colorMsg_error);
            return;
        }
        
        if (block > 1024) {
            terminalAddStr("Max block count 1024", colorMsg_error);
            return;
        }

        if (len == 0) {
            terminalAddStr("Len zero value", colorMsg_error);
            return;
        }

        if (len > 256) {
            terminalAddStr("Max pack 256 bytes", colorMsg_error);
            return;
        }

        Buf_class retVal = deviceILC.readNandData(block, page, offset, len);

        if (retVal.recived == true) {
            if (retVal.retStatus == ILC_device_c.USBC_RET_OK) {
                flash_table.setRowCount(retVal.retLen);
                for (int i = 0; i < retVal.retLen; i++) {
                    
                    flash_table.setValueAt(offset + i, i, 0);
                    StringBuilder sb_val = new StringBuilder();
                    sb_val.append(String.format("0x%02X", retVal.retData[i]));
                    flash_table.setValueAt(sb_val, i, 1);
                    flash_table.setValueAt((char)retVal.retData[i], i, 2);
                }
                terminalAddStr("Read data OK", colorMsg_good);
            }else{
                terminalAddStr("Recived data ERROR", colorMsg_error);
            }
        } else {
            terminalAddStr("ERROR I/O", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_burst_readActionPerformed

    //Сохранить настройки COM порта
    private void jButton_safe_settingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_safe_settingsActionPerformed
        try {
            FileOutputStream output;
            props.setProperty("COM_NAME", jComboBox_comPort.getSelectedItem().toString());
            props.setProperty("COM_SPEED", jComboBox_comPortSpeed.getSelectedItem().toString());
            output = new FileOutputStream(sFileName);
            props.store(output, "Saved settings");
            output.close();
            terminalAddStr("Настройки сохранены", colorMsg_good);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            terminalAddStr("Файл не найден", colorMsg_error);
        } catch (IOException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            terminalAddStr("Ошибка ввода/вывода", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_safe_settingsActionPerformed

    //Отключение от COM порта
    private void jButton_comDisconnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_comDisconnectActionPerformed
        DebugTimer.stop();
        port.COM_data.Mode = port.COM_data.MODE_PROTOCOL;
        
        if (port.closeCOMPort()) {
            jButton_comConnect.setBackground(Color.ORANGE);
            terminalAddStr("Порт закрыт", colorMsg_good);
        }else{
            jButton_comConnect.setBackground(Color.red);
        }
    }//GEN-LAST:event_jButton_comDisconnectActionPerformed

    //Подключение к COM порту
    private void jButton_comConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_comConnectActionPerformed
        if (port.openCOMPort(String.valueOf(jComboBox_comPort.getSelectedItem()),Integer.valueOf(jComboBox_comPortSpeed.getSelectedItem().toString()))) {
            jButton_comConnect.setBackground(Color.green);
            terminalAddStr("Порт подключен", colorMsg_good);
        } else {
            jButton_comConnect.setBackground(Color.red);
            terminalAddStr("Порт не подключен", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_comConnectActionPerformed

    private void loadTable(File file, DefaultTableModel table_model) {
        try {
            ObjectInputStream in = new ObjectInputStream(
            new FileInputStream(file));
            Vector rowData = (Vector)in.readObject();
            Iterator itr = rowData.iterator();
            table_model.setRowCount(0);
            while(itr.hasNext()) {
                table_model.addRow((Vector) itr.next());
            }
            in.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private void saveTable(File file, DefaultTableModel table_model) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(file));
                out.writeObject(table_model.getDataVector());
                out.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
    }
    
    private void jFileChooser_fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser_fileActionPerformed
        
    }//GEN-LAST:event_jFileChooser_fileActionPerformed

    private void jComboBox_comPortMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboBox_comPortMouseClicked
        refrash_COM_list();
    }//GEN-LAST:event_jComboBox_comPortMouseClicked

    private void jButton_debug_saveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_saveActionPerformed
        jFileChooser_file.setCurrentDirectory(new File(debug_path));
        int ret = jFileChooser_file.showDialog(null, "Сохранить файл");  
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            try {
                Files.write( save_file.toPath(), rSyntaxTextArea_debug.getText().getBytes(), StandardOpenOption.CREATE);
            } catch (IOException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_debug_saveActionPerformed

    private void jButton_debug_openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_openActionPerformed
        jFileChooser_file.setCurrentDirectory(new File(debug_path));
        int ret = jFileChooser_file.showDialog(null, "Открыть файл");                
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File open_file = jFileChooser_file.getSelectedFile();
            try {
                String str = new String(Files.readAllBytes(open_file.toPath()));
                rSyntaxTextArea_debug.setText(str);
                debug_main_name = open_file.getName();
                debug_current_file = debug_main_name;
                jButton_debug_main.setEnabled(true);
                terminalAddStr("Файл "+debug_current_file+" открыт\n\r", colorMsg_good);
            } catch (IOException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jButton_debug_openActionPerformed

    private void jButton_debug_compileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_compileActionPerformed
        int expect = 0;
        String file_name;
        
        try {   
            Path path = Paths.get(debug_path+"\\"+debug_current_file);
            File file = new File(path.toString());
            Files.deleteIfExists(path);
            Files.write(path, rSyntaxTextArea_debug.getText().getBytes(), StandardOpenOption.CREATE);           
            
            String command = "python pmImgCreator.py -f pmfeatures.py";
            if (jRadioButton_debug_bin.isSelected()){
                command += " -b";
            }else{
                command += " -c";
            }
            if (jRadioButton_debug_stdLib.isSelected()){
                command += " -s --native-file=inc/py_std_native.c";
            }else{
                command += " -u --native-file=inc/py_usr_native.c";
            }
            command += " --memspace="+jComboBox__debug_memPlace.getSelectedItem().toString()+" -o ";
            if (jRadioButton_debug_bin.isSelected()){
                if (jRadioButton_debug_stdLib.isSelected()){
                    file_name = "bin/py_std.bin";
                }else{
                    file_name = "bin/py_usr.bin";
                }
            }else{
                if (jRadioButton_debug_stdLib.isSelected()){
                    file_name = "inc/py_std.h";
                }else{
                    file_name = "inc/py_usr.h";
                }
            }
            command += file_name;
            
            for (int i =0; i<debug_List_files.getSize(); i++) {
                command += " "+debug_List_files.get(i);
            }

            String[] commands = {"cmd.exe","/c","cd \""+debug_path+"\" && "+command};
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(commands);
            proc.waitFor();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            OutputStream out = proc.getOutputStream();
            
            out.write(command.getBytes());
            
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                terminalAddStr(s+"\n", colorMsg_msg);
                expect = 1;
            }
            BufferedReader stdError = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            while ((s = stdError.readLine()) != null) {
                terminalAddStr(s+"\n", colorMsg_msg);
                expect = 1;
            }
            if (expect != 1) {
                long size = Files.size(Paths.get(debug_path+"\\"+file_name));
                terminalAddStr("Прошивка скомпилирована. Размер: "+String.valueOf(size)+" байта\n\r", colorMsg_good);
            }
        } catch (IOException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_jButton_debug_compileActionPerformed

    private void jButton_debug_addFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_addFileActionPerformed
        jFileChooser_file.setCurrentDirectory(new File(debug_path));
        int ret = jFileChooser_file.showDialog(null, "Добавить файл"); 
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File open_file = jFileChooser_file.getSelectedFile();
            Path abs_path = Paths.get(open_file.getPath());
            abs_path = debug_path_var.relativize(abs_path);
            debug_List_files.addElement(abs_path.toString());
        }
    }//GEN-LAST:event_jButton_debug_addFileActionPerformed

    private void jButton_debug_delFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_delFileActionPerformed
        debug_List_files.remove(jList_debug_files.getSelectedIndex());
    }//GEN-LAST:event_jButton_debug_delFileActionPerformed

    
    
    private void jButton_debug_mainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_mainActionPerformed
        try {
            debug_current_file = debug_main_name;
            Path path = Paths.get(debug_path+"\\"+debug_current_file);
            String str = new String(Files.readAllBytes(path));
            rSyntaxTextArea_debug.setText(str);
        } catch (IOException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_debug_mainActionPerformed

    private void jButton_debug_libSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_libSaveActionPerformed
        try {
            Path path = Paths.get(debug_path+"\\"+debug_current_file);
            Files.deleteIfExists(path);
            Files.write(path, rSyntaxTextArea_debug.getText().getBytes(), StandardOpenOption.CREATE);
            terminalAddStr("Файл "+debug_current_file+" сохранен\n\r", colorMsg_good);
        } catch (IOException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }      
    }//GEN-LAST:event_jButton_debug_libSaveActionPerformed

    private void jButton_flashcopyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_flashcopyActionPerformed
        try {
            String ScriptName = debug_path + "\\bin\\py_usr.bin";
            File ScriptFile = new File(ScriptName);
            byte[] ScriptBin = Files.readAllBytes(ScriptFile.toPath());
            
            //Load scrypt
            if (deviceILC.loadScypt("main", ScriptBin, ScriptBin.length) == true)
            {
                terminalAddStr("Скрипт записан!", colorMsg_good);
            }else{
                terminalAddStr("Ошибка записи", colorMsg_error);
            }
        } catch (IOException ex) {
            terminalAddStr("Ошибка файла прошивки", colorMsg_error);
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton_flashcopyActionPerformed

    private void jButton_start_codeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_start_codeActionPerformed
        //Load scrypt
        if (deviceILC.StartScrypt() == true) {
            terminalAddStr("Скрипт запущен!", colorMsg_good);
        } else {
            terminalAddStr("Ошибка старта", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_start_codeActionPerformed

    private void jButton_debug_addLibrariesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_addLibrariesActionPerformed
        File []fList;
        File F = new File(debug_path+"//Modules");
        fList = F.listFiles();
        debug_List_files.removeAllElements();
        
        Path abs_path = null;
        
        for(int i=0; i<fList.length; i++) {
            if(fList[i].isFile())
            {
                abs_path = Paths.get(fList[i].getPath());
                abs_path = debug_path_var.relativize(abs_path);
                debug_List_files.addElement(abs_path.toString());
            }
        }
    }//GEN-LAST:event_jButton_debug_addLibrariesActionPerformed

    private void jButton_debug_clearListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_clearListActionPerformed
        debug_List_files.removeAllElements();
    }//GEN-LAST:event_jButton_debug_clearListActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        //Stop scrypt
        if (deviceILC.StopScrypt() == true) {
            terminalAddStr("Скрипт остановлен!", colorMsg_good);
        } else {
            terminalAddStr("Ошибка осановки", colorMsg_error);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        //Pause scrypt
        if (deviceILC.PauseScrypt() == true) {
            terminalAddStr("Скрипт приостановлен!", colorMsg_good);
        } else {
            terminalAddStr("Ошибка приостановки", colorMsg_error);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton_debug_add_currentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_debug_add_currentActionPerformed
        debug_List_files.addElement(debug_current_file);
    }//GEN-LAST:event_jButton_debug_add_currentActionPerformed

    private void jComboBox_debug_libsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox_debug_libsActionPerformed
        try {
            debug_current_file = "\\Modules\\"+jComboBox_debug_libs.getSelectedItem().toString();
            Path path = Paths.get(debug_path+debug_current_file);
            String str = new String(Files.readAllBytes(path));
            rSyntaxTextArea_debug.setText(str);  
        } catch (IOException ex) {
            Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jComboBox_debug_libsActionPerformed

    private void jList_debug_filesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList_debug_filesMouseClicked
        if (evt.getClickCount() == 2) {
            try {
                String sel_name = jList_debug_files.getModel().getElementAt(jList_debug_files.getSelectedIndex()).toString();
                debug_current_file = sel_name;
                File F = new File(debug_path+"\\"+sel_name);
                String str = new String(Files.readAllBytes(F.toPath()));
                rSyntaxTextArea_debug.setText(str);
            } catch (IOException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_jList_debug_filesMouseClicked
    private void jButton_checkConnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_checkConnActionPerformed
        if (deviceILC.checkConnection() == true) {
            terminalAddStr("Устройство работает!", colorMsg_good);
        } else {
            terminalAddStr("Нет связи. Возможно устройство находится в режиме отладки!", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_checkConnActionPerformed

    private void jButton_checkConn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_checkConn1ActionPerformed
        if (deviceILC.changeMode(deviceILC.USBP_MODE_DEBUG) == true) {
            terminalAddStr("Режим отладки включен", colorMsg_good);
            port.COM_data.Mode = port.COM_data.MODE_TRANSPARENT;
            DebugTimer.start();
        } else {
            terminalAddStr("Нет связи. Возможно устройство находится в режиме отладки!", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_checkConn1ActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (port.COM_data.Mode != port.COM_data.MODE_TRANSPARENT)
        {
            terminalAddStr("Терминал в режиме отладки!", colorMsg_good);
            port.COM_data.Mode = port.COM_data.MODE_TRANSPARENT;
            DebugTimer.start();
        }else{
            terminalAddStr("Терминал в режиме протокола!", colorMsg_good);
            port.COM_data.Mode = port.COM_data.MODE_PROTOCOL;
            DebugTimer.stop();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton_writeSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_writeSettingsActionPerformed
        
        int setID;
        byte[] data = new byte[300];
        int len = 0;
        String str;

        for (int i = 0; i < jTable_settings.getRowCount(); i++) {
            try {
                setID = Integer.parseInt(jTable_settings.getValueAt(i, 0).toString());

                if (jTable_settings.getValueAt(i, 3).toString().equals("MAC")) {
                    str = jTable_settings.getValueAt(i, 2).toString();
                    String[] macAddressParts = str.split(":");
                    byte[] macAddressBytes = new byte[6];
                    
                    for (int j = 0; j < 6; j++) 
                    {
                        Integer hex = Integer.parseInt(macAddressParts[j], 16);
                        macAddressBytes[j] = hex.byteValue();
                    }
                    System.arraycopy(macAddressBytes, 0, data, 0, macAddressBytes.length);
                    len = 6;
                }

                if (jTable_settings.getValueAt(i, 3).toString().equals("IP")) {

                    str = jTable_settings.getValueAt(i, 2).toString();
                    InetAddress ip = InetAddress.getByName(str);
                    System.arraycopy(ip.getAddress(), 0, data, 0, 4);
                    len = 4;
                }

                if (jTable_settings.getValueAt(i, 3).toString().equals("byte")) {

                    str = jTable_settings.getValueAt(i, 2).toString();
                    data[0] = (byte) Integer.parseInt(str);
                    len = 1;
                }

                if (jTable_settings.getValueAt(i, 3).toString().equals("word")) {

                    str = jTable_settings.getValueAt(i, 2).toString();
                    int val = Integer.parseInt(str);

                    data[0] = (byte) ((val & 0xFF00) >> 8);
                    data[1] = (byte) (val & 0x00FF);
                    len = 2;
                }

                if (jTable_settings.getValueAt(i, 3).toString().equals("String")) {

                    str = jTable_settings.getValueAt(i, 4).toString();
                    len = Integer.parseInt(str);
                    str = jTable_settings.getValueAt(i, 2).toString();
                    byte[] bytes = str.getBytes();
                    Arrays.fill( data, (byte) 0 );
                    System.arraycopy(bytes, 0, data, 0, bytes.length);
                }

                if (deviceILC.writeSettingParam(setID, data, len) == true) {
                    terminalAddStr(jTable_settings.getValueAt(i, 1).toString() + " записано", colorMsg_good);
                } else {
                    terminalAddStr(jTable_settings.getValueAt(i, 1).toString() + " ошибка записи", colorMsg_error);
                }

                Thread.sleep(100);

            } catch (InterruptedException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnknownHostException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton_writeSettingsActionPerformed

    private void jButton_readSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_readSettingsActionPerformed
        
        ILC_device_c.ILC_buf_c retBuf;
        int setID;
        int reqLen;

        for (int i = 0; i < jTable_settings.getRowCount(); i++) {
            try //jTable_settings.getRowCount();
            {
                setID = Integer.parseInt(jTable_settings.getValueAt(i, 0).toString());
                retBuf = deviceILC.readSettingParam(setID);
                
                if (retBuf.status == ILC_device_c.USBC_RET_OK) {
                    
                    reqLen = Integer.parseInt(jTable_settings.getValueAt(i, 4).toString());
                    
                    if (reqLen == retBuf.Len) 
                    {
                        if (jTable_settings.getValueAt(i, 3).toString().equals("MAC"))
                        {
                            String ip = String.format("%02X", retBuf.Data[0]) + ":"
                                    + String.format("%02X", retBuf.Data[1]) + ":"
                                    + String.format("%02X", retBuf.Data[2]) + ":"
                                    + String.format("%02X", retBuf.Data[3]) + ":"
                                    + String.format("%02X", retBuf.Data[4]) + ":"
                                    + String.format("%02X", retBuf.Data[5]);
                            
                            jTable_settings.setValueAt(ip, i, 2);
                        }
                        
                        if (jTable_settings.getValueAt(i, 3).toString().equals("IP"))
                        {
                            String ip = Integer.toString((int)retBuf.Data[0] & 0xFF) + "."
                                    + Integer.toString((int)retBuf.Data[1] & 0xFF) + "."
                                    + Integer.toString((int)retBuf.Data[2] & 0xFF) + "."
                                    + Integer.toString((int)retBuf.Data[3] & 0xFF);
                            
                            jTable_settings.setValueAt(ip, i, 2);
                        }
                        
                        if (jTable_settings.getValueAt(i, 3).toString().equals("byte"))
                        {
                            String val = String.valueOf(retBuf.Data[0]);
                            jTable_settings.setValueAt(val, i, 2);
                        }
                        
                        if (jTable_settings.getValueAt(i, 3).toString().equals("word"))
                        {
                            String val = Integer.toString((retBuf.Data[0] << 8) & 0xFF + retBuf.Data[1] & 0xFF);
                            jTable_settings.setValueAt(val, i, 2);
                        }
                        
                        if (jTable_settings.getValueAt(i, 3).toString().equals("String"))
                        {
                            String str = new String(retBuf.Data, "UTF-8");
                            String strReplace = str.replaceAll(" ","");
                            jTable_settings.setValueAt(strReplace, i, 2);
                        }
                        
                        terminalAddStr(jTable_settings.getValueAt(i, 1).toString() + " получено", colorMsg_good);
                    } else {
                        terminalAddStr(jTable_settings.getValueAt(i, 1).toString() + " ошибка", colorMsg_error);
                    }
                } else {
                    terminalAddStr(jTable_settings.getValueAt(i, 1).toString() + " ошибка приема пакета", colorMsg_error);
                }
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }//GEN-LAST:event_jButton_readSettingsActionPerformed

    private void jButton_assignSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_assignSettingsActionPerformed
        if (deviceILC.assignSettings() == true)
        {
            terminalAddStr("Настройки применены. Пожалуйста перезагрузите устройство.", colorMsg_good);
            deviceILC.port.closeCOMPort();
        }else{
            terminalAddStr("Ошибка пакета", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_assignSettingsActionPerformed

    private void jButton_devReset1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_devReset1ActionPerformed
        resetMake();
    }//GEN-LAST:event_jButton_devReset1ActionPerformed

    private void jButton_devReset2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_devReset2ActionPerformed
        resetMake();
    }//GEN-LAST:event_jButton_devReset2ActionPerformed

    private void jButton_resetSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_resetSettingsActionPerformed
        if (deviceILC.resetSettings() == true)
        {
            terminalAddStr("Настройки сброшены. Для из применения перезагрузите устройство.", colorMsg_good);
            deviceILC.port.closeCOMPort();
        }else{
            terminalAddStr("Ошибка пакета", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_resetSettingsActionPerformed

    private void jButton_setOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setOpenFileActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Открыть файл");
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            loadTable(save_file, settings_table);
        }
    }//GEN-LAST:event_jButton_setOpenFileActionPerformed

    private void jButton_setSaveFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setSaveFileActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Сохранить файл");  
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            saveTable(save_file, settings_table);
        }
    }//GEN-LAST:event_jButton_setSaveFileActionPerformed

    private void jButton_writeCalMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_writeCalMainActionPerformed
        int channel = jComboBox_chCal.getSelectedIndex();
        int line = jComboBox_calPhaseChoose.getSelectedIndex();

        writeCal(jTable_calMain, channel, line);
    }//GEN-LAST:event_jButton_writeCalMainActionPerformed

    private void jButton_readCalMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_readCalMainActionPerformed
//        int channel = jComboBox_chCal.getSelectedIndex();
//        int line = jComboBox_calPhaseChoose.getSelectedIndex();
//        
//        readCal(jTable_calMain, channel, line);
//        terminalAddStr("Параметры калибровки считаны", colorMsg_good);

        readCount = 0;
        channel = jComboBox_chCal.getSelectedIndex();
        line = jComboBox_calPhaseChoose.getSelectedIndex();

        ActionListener listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerCalIO.stop();
                readCal(jTable_calMain, channel, line, readCount);
                if ((jTable_calMain.getRowCount() - 1) > readCount) {
                    readCount++;
                    timerCalIO.start();
                } else {
                    terminalAddStr("Регистры считаны***************************************", colorMsg_good);
                    timerCalIO = null;
                }
            }
        };

        readCal(jTable_calMain, channel, line, readCount);
        readCount++;

        timerCalIO = new Timer(50, listener);
        timerCalIO.start();
    }//GEN-LAST:event_jButton_readCalMainActionPerformed

    private void readValues() {
        ILC_device_c.ILC_buf_c retBuf;
        int valID = 0;
        int channel = 0;
        int line = 0;
        int tryCounter;

        for (int i = 0; i < jTable_valPhase.getRowCount(); i++) {

            if (jTable_valPhase.getValueAt(i, 5).toString().equals("CH_0")) {
                channel = 0;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("CH_1")) {
                channel = 1;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("CH_2")) {
                channel = 2;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("CH_3")) {
                channel = 3;
            }

            if (jTable_valPhase.getValueAt(i, 5).toString().equals("Line_A")) {
                line = 0;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("Line_B")) {
                line = 1;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("Line_C")) {
                line = 2;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("Line_N")) {
                line = 3;
            }
            if (jTable_valPhase.getValueAt(i, 5).toString().equals("Line_S")) {
                line = 4;
            }

            valID = Integer.parseInt(jTable_valPhase.getValueAt(i, 0).toString());

            tryCounter = 2;
            while (tryCounter-- >= 0) {

                retBuf = deviceILC.readValues(valID, channel, line);

                if (retBuf == null) {
                    terminalAddStr("Проверьте подключение", colorMsg_error);
                    return;
                }

                if (retBuf.status == ILC_device_c.USBC_RET_OK) {
                    if (jTable_valPhase.getValueAt(i, 3).toString().equals("float")) {
                        if (retBuf.Len == 4) {
                            byte[] data = new byte[4];
                            System.arraycopy(retBuf.Data, 0, data, 0, 4);
                            float f = ByteBuffer.wrap(data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
                            jTable_valPhase.setValueAt(String.format("%.2f", f), i, 2);
                        }
                    }
                    if (jTable_valPhase.getValueAt(i, 3).toString().equals("uint64_t")) {
                        if (retBuf.Len == 8) {
                            long val = deviceILC.bytesToLong(retBuf.Data);
                            jTable_valPhase.setValueAt(String.format("%d", val), i, 2);
                        }
                    }
                    
                    break;
                } else if (retBuf.status == ILC_device_c.USBC_RET_NAVAL) {
                    if (tryCounter == 0)
                    {
                        jTable_valPhase.setValueAt("NONE", i, 2);
                    }else{
                        terminalAddStr("Ошибка чтения: "+jTable_valPhase.getValueAt(i, 1).toString()+" пробуем еще...", colorMsg_error);
                    }
                }
            }
        }
    }

    Timer timerReadVal;
    int periodRead = 0;
    int readCount = 0;
    
    private void jButton_readValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_readValActionPerformed

        if (jCheckBox_readPeriodic.isSelected()) {
            if (periodRead == 1) {
                readCount = 0;
                jButton_readVal.setText("Считать");
                timerReadVal.stop();
                timerReadVal = null;
                jLabel_readValCounter.setText(String.valueOf(readCount));
            } else {jLabel_readValCounter.setText(String.valueOf(readCount));
                periodRead = 1;
                jButton_readVal.setText("Стоп");
                ActionListener listener = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        readValues();
                        readCount++;
                        jLabel_readValCounter.setText(String.valueOf(readCount));
                        terminalAddStr("Данные прочитаны #"+String.valueOf(readCount), colorMsg_good);
                    }
                };

                int period = Integer.parseInt(jTextField_readPeriod.getText());

                if (period == 0) {
                    return;
                }

                readValues();
                readCount++;
                jLabel_readValCounter.setText(String.valueOf(readCount));
                terminalAddStr("Данные прочитаны #"+String.valueOf(readCount), colorMsg_good);
                
                timerReadVal = new Timer(period*1000, listener);
                timerReadVal.start();
            }
        } else {
            readValues();
            terminalAddStr("Данные прочитаны", colorMsg_good);
        }
    }//GEN-LAST:event_jButton_readValActionPerformed

    private void jButton_saveCalPhaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveCalPhaseActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Сохранить файл");  
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            saveTable(save_file, (DefaultTableModel) jTable_calPhase.getModel());
        }
    }//GEN-LAST:event_jButton_saveCalPhaseActionPerformed

    private void jButton_openCalPhaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_openCalPhaseActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Открыть файл");
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            loadTable(save_file, (DefaultTableModel) jTable_calPhase.getModel());
        }
    }//GEN-LAST:event_jButton_openCalPhaseActionPerformed

    private void jButton_openCalMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_openCalMainActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Открыть файл");
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            loadTable(save_file, (DefaultTableModel) jTable_calMain.getModel());
        }
    }//GEN-LAST:event_jButton_openCalMainActionPerformed

    private void jButton_saveCalMainActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveCalMainActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Сохранить файл");  
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            saveTable(save_file, (DefaultTableModel) jTable_calMain.getModel());
        }
    }//GEN-LAST:event_jButton_saveCalMainActionPerformed

    private void jButton_getOffsetValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_getOffsetValActionPerformed
        int selRow = SelTable.getSelectedRow();
        if (selRow != -1)
        {
            SelTable.setValueAt(jTextField_offsetVal.getText(), selRow, 2); 
        }
    }//GEN-LAST:event_jButton_getOffsetValActionPerformed

    private void jButton_getKoefValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_getKoefValActionPerformed
        int selRow = SelTable.getSelectedRow();
        if (selRow != -1)
        {
            SelTable.setValueAt(jTextField_koefVal.getText(), selRow, 2); 
        }
    }//GEN-LAST:event_jButton_getKoefValActionPerformed

    private void jButton_setLineActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setLineActionPerformed
        for (int i=0; i<jTable_valPhase.getRowCount(); i++)
        {
            if (jTable_valPhase.isRowSelected(i))
            {
                jTable_valPhase.setValueAt(jComboBox_line.getSelectedItem().toString(), i, 6);
            }
        }
    }//GEN-LAST:event_jButton_setLineActionPerformed

    private void jButton_openValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_openValActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Открыть файл");
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            loadTable(save_file, (DefaultTableModel) jTable_valPhase.getModel());
        }
    }//GEN-LAST:event_jButton_openValActionPerformed

    private void jButton_saveValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_saveValActionPerformed
        int ret = jFileChooser_file.showDialog(null, "Сохранить файл");  
        if (ret == jFileChooser_file.APPROVE_OPTION) {
            File save_file = jFileChooser_file.getSelectedFile();
            saveTable(save_file, (DefaultTableModel) jTable_valPhase.getModel());
        }
    }//GEN-LAST:event_jButton_saveValActionPerformed

    private void jButton_calCalculator1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_calCalculator1ActionPerformed
        jFrame_calCalculator.setAlwaysOnTop(true);
        jFrame_calCalculator.setLocationRelativeTo(null);
        jFrame_calCalculator.setVisible(true);
    }//GEN-LAST:event_jButton_calCalculator1ActionPerformed

    private void jButton_serChActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_serChActionPerformed
        for (int i=0; i<jTable_valPhase.getRowCount(); i++)
        {
            if (jTable_valPhase.isRowSelected(i))
            {
                jTable_valPhase.setValueAt(jComboBox_ch.getSelectedItem().toString(), i, 5);
            }
        }
    }//GEN-LAST:event_jButton_serChActionPerformed

    private void jButton_asseptCalibrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_asseptCalibrActionPerformed
        if (deviceILC.assignCalibr() == true)
        {
            terminalAddStr("Калибровочные параметры применены. Пожалуйста перезагрузите устройство.", colorMsg_good);
            deviceILC.port.closeCOMPort();
        }else{
            terminalAddStr("Ошибка пакета", colorMsg_error);
        }
    }//GEN-LAST:event_jButton_asseptCalibrActionPerformed

    private void jButton_setZeroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setZeroActionPerformed
        int selRow = jTable_valPhase.getSelectedRow();
        if (selRow != -1)
        {
            String str = jTable_valPhase.getValueAt(selRow, 2).toString();
            str = str.substring(0, str.indexOf(","));
            int i = Integer.parseInt(str);
            jTextField_zeroVal.setText(String.valueOf(i));
        }
    }//GEN-LAST:event_jButton_setZeroActionPerformed

    private void jButton_setCurrAppValActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_setCurrAppValActionPerformed
        int selRow = jTable_valPhase.getSelectedRow();
        if (selRow != -1)
        {
            String str = jTable_valPhase.getValueAt(selRow, 2).toString();
            str = str.substring(0, str.indexOf(","));
            int i = Integer.parseInt(str);
            jTextField_currAppVal.setText(String.valueOf(i));
        }
    }//GEN-LAST:event_jButton_setCurrAppValActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int zeroVal = Integer.parseInt(jTextField_zeroVal.getText());
        int appVal = Integer.parseInt(jTextField_appVal.getText());
        int currVal = Integer.parseInt(jTextField_currAppVal.getText());

        if (currVal == 0) {
            terminalAddStr("Деление на 0", colorMsg_error);
            return;
        }

        float errorVal = ((float)(currVal - appVal) / currVal);
        int koefVal = (int) Math.round((float)Math.pow(2, 31)*((1/(1+errorVal))-1));
        float koefProp = appVal/currVal;
        
        byte[] bytes = ByteBuffer.allocate(8).putFloat(koefProp).array();
        
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(Integer.toString(b, 16));
        }
        jTextField_koefProp.setText("0x"+(builder.toString()).toUpperCase());

        jTextField_errorVal.setText(String.format("%.8f", errorVal));
        jTextField_offsetVal.setText(String.format("0x%08X", -zeroVal));
        jTextField_koefVal.setText(String.format("0x%08X", koefVal)); 
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTable_calMainMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_calMainMouseClicked
        SelTable = jTable_calMain;
    }//GEN-LAST:event_jTable_calMainMouseClicked

    private void jTable_calPhaseMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_calPhaseMouseClicked
        SelTable = jTable_calPhase;
    }//GEN-LAST:event_jTable_calPhaseMouseClicked

    int valIDGraph = 0;
    int channelGraph = 0;
    int lineGraph = 0;
    graphClass graph;
    float mult = 0;

    void graphRead() {
        ILC_device_c.ILC_buf_c retBuf = deviceILC.readValues(valIDGraph, channelGraph, lineGraph);

        if (retBuf == null) {
            terminalAddStr("Проверьте подключение", colorMsg_error);
            return;
        }

        if (retBuf.status == ILC_device_c.USBC_RET_OK) {
            readCount++;
            jLabel_graphCounter.setText(String.valueOf(readCount));
            float fVal = ByteBuffer.wrap(retBuf.Data).order(ByteOrder.LITTLE_ENDIAN).getFloat();
            jTextField_readValGraph.setText(String.format("%.2f", fVal * mult));
            graph.incLoadChart((long) (fVal * mult));
        }
    }
                    
    private void jButton_readValGraphActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_readValGraphActionPerformed

        if (periodRead == 1) {
            
            periodRead = 0;
            timerReadVal.stop();
            timerReadVal = null;
            readCount = 0;
            jLabel_graphCounter.setText(String.valueOf(readCount));
            jButton_readValGraph.setText("Старт");
            
        } else {
            
            periodRead = 1;
            jButton_readValGraph.setText("Стоп");
            valIDGraph = jComboBox_valGraph.getSelectedIndex() + 1;
            channelGraph = jComboBox_chGraph.getSelectedIndex();
            lineGraph = jComboBox_lineGraph.getSelectedIndex();
            graph = new graphClass(jComboBox_valGraph.getSelectedItem().toString());
            int period = Integer.parseInt(jTextField_readPeriodGraph.getText());
            mult = Float.parseFloat(jTextField_readMultGraph.getText());

            ActionListener listener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    graphRead();
                }
            };

            if (period == 0) {
                return;
            }

            graphRead();
            timerReadVal = new Timer(period * 1000, listener);
            timerReadVal.start();
        }
    }//GEN-LAST:event_jButton_readValGraphActionPerformed

    private void jPanel_graphsComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel_graphsComponentShown

    }//GEN-LAST:event_jPanel_graphsComponentShown

    private void jButton_convActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_convActionPerformed
        String uintStr = jTextField_convUint.getText();
        
        if (uintStr.length() == 0)
            return;
        
        uintStr = uintStr.substring(2, uintStr.length());
        Long i = Long.parseLong(uintStr, 16);
        Float f = Float.intBitsToFloat(i.intValue());
        
        jTextField_convFloat.setText(String.format("%.3f", f));
    }//GEN-LAST:event_jButton_convActionPerformed

    private void jButton_conv1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_conv1ActionPerformed
        String floatStr = jTextField_convFloat.getText();
        
        floatStr = floatStr.replace(",", ".");
        if (floatStr.length() == 0) {
            return;
        }

        Float f = Float.parseFloat(floatStr);
        byte[] bytes = ByteBuffer.allocate(4).putFloat(f).array();
        
        final StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(Integer.toString(b, 16));
        }
        jTextField_convUint.setText("0x"+(builder.toString()).toUpperCase());
    }//GEN-LAST:event_jButton_conv1ActionPerformed

    private void jButton_getKoefPropActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton_getKoefPropActionPerformed
        int selRow = SelTable.getSelectedRow();
        if (selRow != -1)
        {
            SelTable.setValueAt(jTextField_koefProp.getText(), selRow, 2); 
        }
    }//GEN-LAST:event_jButton_getKoefPropActionPerformed

    class graphClass {

        private XYSeries valSeries;
        
        private void initLoadChart(String name) {
            valSeries = new XYSeries(name);

            for (int i = 0; i < 60; i++) {
                valSeries.add(i, 0);
            }

            XYSeriesCollection dataset = new XYSeriesCollection();
            dataset.addSeries(valSeries);

            JFreeChart valChart = ChartFactory.createXYLineChart(null, "Время", name, dataset, PlotOrientation.VERTICAL, false, false, false);

//            cryptChart.getXYPlot().setDomainGridlinePaint(lafGridColor);
//            cryptChart.getXYPlot().setRangeGridlinePaint(lafGridColor);
//            cryptChart.setBackgroundPaint(lafBackground);
            valChart.getXYPlot().getRangeAxis().setAutoRangeMinimumSize(3);
//            valChart.getXYPlot().getRangeAxis().setLabelPaint(lafText);
//            valChart.getXYPlot().getRangeAxis().setTickLabelPaint(lafText);
            valChart.getXYPlot().getDomainAxis().setVisible(false);
            //valChart.getXYPlot().getRangeAxis().setVisible(false);
            //valChart.getXYPlot().setBackgroundPaint(lafForeground);

            int seriesCount = valChart.getXYPlot().getSeriesCount();
            for (int i = 0; i < seriesCount; i++) {
                valChart.getXYPlot().getRenderer().setSeriesStroke(i, new BasicStroke(3));
            }

            ChartPanel chartPanel = new ChartPanel(valChart);
            //chartPanel.setBackground(lafBackground);
            //chartPanel.setMaximumSize(new Dimension(991, 437));
            chartPanel.setPreferredSize(new Dimension(991, 437));

            
            jPanel_graph.setLayout(new java.awt.BorderLayout());
            //jPanel_graph.setBackground(lafBackground);
            jPanel_graph.add(chartPanel, BorderLayout.CENTER);
            jPanel_graph.validate();
        }

        public void incLoadChart(long val) {
            for (int i = 0; i < 59; i++) {
                valSeries.update(i, valSeries.getY(i + 1));
            }
            valSeries.update(59, (Number) (val));
        }

        public graphClass(String name) {
            initLoadChart(name);
        }
    }
    
    private void resetMake()
    {
        if (deviceILC.resetDevice() == true)
        {
            terminalAddStr("Устройство перезагружается. Дождитесь загрузки и заново подключитесь.", colorMsg_good);
            deviceILC.port.closeCOMPort();
        }else{
            terminalAddStr("Ошибка пакета", colorMsg_error);
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ILC_main_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ILC_main_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ILC_main_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ILC_main_form.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ILC_main_form().setVisible(true);
            }
        });
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup_debug_gener;
    private javax.swing.ButtonGroup buttonGroup_debug_lib;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton_asseptCalibr;
    private javax.swing.JButton jButton_assignSettings;
    private javax.swing.JButton jButton_burst_clear;
    private javax.swing.JButton jButton_burst_read;
    private javax.swing.JButton jButton_burst_set_val;
    private javax.swing.JButton jButton_burst_write;
    private javax.swing.JButton jButton_calCalculator1;
    private javax.swing.JButton jButton_checkConn;
    private javax.swing.JButton jButton_checkConn1;
    private javax.swing.JButton jButton_comConnect;
    private javax.swing.JButton jButton_comDisconnect;
    private javax.swing.JButton jButton_conv;
    private javax.swing.JButton jButton_conv1;
    private javax.swing.JButton jButton_debug_addFile;
    private javax.swing.JButton jButton_debug_addLibraries;
    private javax.swing.JButton jButton_debug_add_current;
    private javax.swing.JButton jButton_debug_clearList;
    private javax.swing.JButton jButton_debug_compile;
    private javax.swing.JButton jButton_debug_delFile;
    private javax.swing.JButton jButton_debug_libSave;
    private javax.swing.JButton jButton_debug_main;
    private javax.swing.JButton jButton_debug_open;
    private javax.swing.JButton jButton_debug_save;
    private javax.swing.JButton jButton_devReset1;
    private javax.swing.JButton jButton_devReset2;
    private javax.swing.JButton jButton_flashcopy;
    private javax.swing.JButton jButton_getKoefProp;
    private javax.swing.JButton jButton_getKoefVal;
    private javax.swing.JButton jButton_getOffsetVal;
    private javax.swing.JButton jButton_openCalMain;
    private javax.swing.JButton jButton_openCalPhase;
    private javax.swing.JButton jButton_openVal;
    private javax.swing.JButton jButton_readCalMain;
    private javax.swing.JButton jButton_readCalPhase;
    private javax.swing.JButton jButton_readSettings;
    private javax.swing.JButton jButton_readVal;
    private javax.swing.JButton jButton_readValGraph;
    private javax.swing.JButton jButton_resetSettings;
    private javax.swing.JButton jButton_safe_settings;
    private javax.swing.JButton jButton_saveCalMain;
    private javax.swing.JButton jButton_saveCalPhase;
    private javax.swing.JButton jButton_saveVal;
    private javax.swing.JButton jButton_serCh;
    private javax.swing.JButton jButton_setCurrAppVal;
    private javax.swing.JButton jButton_setLine;
    private javax.swing.JButton jButton_setOpenFile;
    private javax.swing.JButton jButton_setSaveFile;
    private javax.swing.JButton jButton_setZero;
    private javax.swing.JButton jButton_start_code;
    private javax.swing.JButton jButton_term_send;
    private javax.swing.JButton jButton_terminal_clear;
    private javax.swing.JButton jButton_writeCalMain;
    private javax.swing.JButton jButton_writeCalPhase;
    private javax.swing.JButton jButton_writeSettings;
    private javax.swing.JCheckBox jCheckBox_readPeriodic;
    private javax.swing.JComboBox<String> jComboBox__debug_memPlace;
    private javax.swing.JComboBox<String> jComboBox_calPhaseChoose;
    private javax.swing.JComboBox<String> jComboBox_ch;
    private javax.swing.JComboBox<String> jComboBox_chCal;
    private javax.swing.JComboBox<String> jComboBox_chGraph;
    private javax.swing.JComboBox<String> jComboBox_comPort;
    private javax.swing.JComboBox<String> jComboBox_comPortSpeed;
    private javax.swing.JComboBox<String> jComboBox_debug_libs;
    private javax.swing.JComboBox<String> jComboBox_line;
    private javax.swing.JComboBox<String> jComboBox_lineGraph;
    private javax.swing.JComboBox<String> jComboBox_valGraph;
    private javax.swing.JFileChooser jFileChooser_file;
    private javax.swing.JFrame jFrame_calCalculator;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel_graphCounter;
    private javax.swing.JLabel jLabel_readValCounter;
    private javax.swing.JList<String> jList1;
    private javax.swing.JList<String> jList_debug_files;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel_PD;
    private javax.swing.JPanel jPanel_connect;
    private javax.swing.JPanel jPanel_debug;
    private javax.swing.JPanel jPanel_graph;
    private javax.swing.JPanel jPanel_graphs;
    private javax.swing.JPanel jPanel_param_main;
    private javax.swing.JPanel jPanel_script;
    private javax.swing.JPanel jPanel_terminal;
    private javax.swing.JRadioButton jRadioButton_debug_bin;
    private javax.swing.JRadioButton jRadioButton_debug_c;
    private javax.swing.JRadioButton jRadioButton_debug_stdLib;
    private javax.swing.JRadioButton jRadioButton_debug_userLib;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTabbedPane jTabbedPane_tabs;
    private javax.swing.JTable jTable_calMain;
    private javax.swing.JTable jTable_calPhase;
    private javax.swing.JTable jTable_flash;
    private javax.swing.JTable jTable_settings;
    private javax.swing.JTable jTable_valPhase;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField_appVal;
    private javax.swing.JTextField jTextField_block;
    private javax.swing.JTextField jTextField_convFloat;
    private javax.swing.JTextField jTextField_convUint;
    private javax.swing.JTextField jTextField_count_bytes;
    private javax.swing.JTextField jTextField_currAppVal;
    private javax.swing.JTextField jTextField_errorVal;
    private javax.swing.JTextField jTextField_koefProp;
    private javax.swing.JTextField jTextField_koefVal;
    private javax.swing.JTextField jTextField_offset;
    private javax.swing.JTextField jTextField_offsetVal;
    private javax.swing.JTextField jTextField_page;
    private javax.swing.JTextField jTextField_readMultGraph;
    private javax.swing.JTextField jTextField_readPeriod;
    private javax.swing.JTextField jTextField_readPeriodGraph;
    private javax.swing.JTextField jTextField_readValGraph;
    private javax.swing.JTextField jTextField_set_val;
    private javax.swing.JTextField jTextField_term_in;
    private javax.swing.JTextField jTextField_zeroVal;
    private javax.swing.JTextPane jTextPane_terminal;
    private org.fife.ui.rsyntaxtextarea.RSyntaxTextArea rSyntaxTextArea_debug;
    private org.fife.ui.rtextarea.RTextScrollPane rTextScrollPane_debug;
    // End of variables declaration//GEN-END:variables

    //Функция инициализации параметров
    public void ILC_init_params() {
            
        //Настройка таблиц
        settings_table = (DefaultTableModel) jTable_settings.getModel();
        flash_table = (DefaultTableModel) jTable_flash.getModel();
        calibratePhase_table = (DefaultTableModel) jTable_calPhase.getModel();
        calibrateMain_table = (DefaultTableModel) jTable_calMain.getModel();
        jList_debug_files.setModel(debug_List_files);
        jComboBox_debug_libs.setModel(debug_List_libs);

        //Port init
        port = new Serial_port();
        deviceILC = new ILC_device_c(port);

        //Инициализация списка COM портов
        String[] portNames = SerialPortList.getPortNames();
        jComboBox_comPort.removeAllItems();
        for(int i = 0; i < portNames.length; i++){
            jComboBox_comPort.addItem(portNames[i]);
        }
        
        //Установка настроек порта
        jComboBox_comPort.setSelectedItem(prp_com_name);
        jComboBox_comPortSpeed.setSelectedItem(prp_com_speed);
        jComboBox_comPortSpeed.setSelectedIndex(0);
        
        //Terminal
        rTextScrollPane_debug.setLineNumbersEnabled(true);

        //Table settings
//        JComboBox<String> typeComboBox = new JComboBox<>();
//        typeComboBox.addItem("String");
//        typeComboBox.addItem("uint32");
//        typeComboBox.addItem("uint16");
//        typeComboBox.addItem("uint8");
//        typeComboBox.addItem("IP_addr");
//        TableColumn typetColumn = jTable_calibrate.getColumnModel().getColumn(2);
//        typetColumn.setCellEditor(new DefaultCellEditor(typeComboBox));

        //Debug listener
        ActionListener DebugListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (port.COM_data.dataRecive == 1) {
                    String s = new String(port.COM_data.rx_buf, 0, port.COM_data.rx_len, StandardCharsets.UTF_8);
                    terminalAddText(s, colorMsg_debug);
                    port.COM_data.rx_len = 0;
                    port.COM_data.dataRecive = 0;
                }
            }
        };
        //Timer
        DebugTimer = new Timer(10, DebugListener);
        
        //Com port mode
        port.COM_data.Mode = port.COM_data.MODE_PROTOCOL;
    }

    //Добавить строку в терминал
    public void terminalAddStr(String str, Color c) {
        appendToPane(jTextPane_terminal, str+"\r\n", c);
        jTextPane_terminal.setCaretPosition(jTextPane_terminal.getDocument().getLength());
    }
    
    //Add text to terminal
    public void terminalAddText(String str, Color c) {
        appendToPane(jTextPane_terminal, str, c);
        jTextPane_terminal.setCaretPosition(jTextPane_terminal.getDocument().getLength());
    }
    
    //Append text
    private void appendToPane(JTextPane tp, String msg, Color c)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    //Найти строку в таблице
    public String find_value_inTable(JTable obj, int col_key, int col_value, String key) {
        String str = null;
        
        for (int i = 0; i < obj.getRowCount(); i++) {
            if (obj.getValueAt(i, col_key).equals(key))
                str = obj.getValueAt(i,col_value).toString();
        }
        
        return str;
    }

    public void clear_byteArr(byte[] data) {
        int len = data.length;
        for (int i=0; i<len;i++) {
            data[i] = 0x00;
        }
    }
    
    public void refrash_COM_list() {
        jComboBox_comPort.removeAllItems();
        String[] portNames = SerialPortList.getPortNames();
        jComboBox_comPort.removeAllItems();
        for(int i = 0; i < portNames.length; i++){
            jComboBox_comPort.addItem(portNames[i]);
        }
    }
        
    //Конвертировать строку HEX в long
    public long StrHexToLong (String str) {
        long value = -1;
        
        if (str != null) {
            String str_adr = str.substring(2, str.length());
            value = Long.parseLong(str_adr,16) & 0xffffff;
        }
        
        return value;
    }

    //Конвертировать long в строку HEX
    public String LongToHexStr(long value, int len) {
        String str_out = null;
        String str_source = "0x";
        StringBuilder sb_val = new StringBuilder();
        
        str_source += "%0"+String.valueOf(len)+"X";
        sb_val.append(String.format(str_source, value));
        str_out = sb_val.toString();
        
        return str_out;
    } 
    
    public static void infoBox(String infoMessage, String titleBar) {
        JOptionPane optionPane = new JOptionPane(infoMessage);
        JDialog dialog = optionPane.createDialog(titleBar);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }

}
