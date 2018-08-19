/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILC_service;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

/**
 *
 * @author user
 */
public class Serial_port {
    
    public byte stop_1 = 0x54;
    public byte stop_2 = 0x55;
    
    public COM_data_с COM_data; //Класс данных
    public COM_thead_с COM_thead; //Поток работы с портом
     
    Serial_port(){
        COM_data = new COM_data_с();
    }
    
    public static class COM_data_с {
        
        public int packRecive = 0; //recive flag
        public int dataRecive = 0; //recive flag
        public int state  = 0; //Состояние COM порта
        public final int rxMAX_len = 300;
        public byte[] rx_buf = new byte[rxMAX_len]; //Буфер приема;
        public int rx_len = 0;  //Счетчик
        public static SerialPort serialPort;
        public int Mode;
        public static int MODE_PROTOCOL = 0;
        public static int MODE_TRANSPARENT = 1;
    }
    
    public class COM_thead_с extends Thread {

     
        @Override
        public void run() {
            synchronized(COM_data) {
                try {
                    COM_data.serialPort.addEventListener(new EventListener(), SerialPort.MASK_RXCHAR);
                } catch (SerialPortException ex) {
                    Logger.getLogger(ILC_main_form.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            while (true) {

            }
        }

        //Событие приема данных
        private class EventListener implements SerialPortEventListener {

            @Override
            public void serialEvent(SerialPortEvent event) {
                if (event.isRXCHAR() && event.getEventValue() > 0) {

                    try {

                        //Protocol mode
                        if (COM_data.Mode == COM_data.MODE_PROTOCOL) {
                            //Check process
                            if (COM_data.packRecive == 1) {
                                Thread.sleep(500);
                            }

                            if (COM_data.packRecive == 1) {
                                return;
                            }

                            //Получить данные
                            int len = event.getEventValue();
                            byte[] data = COM_data.serialPort.readBytes(len);

                            //Счетчик принятых байт
                            if ((COM_data.rx_len + len) < COM_data.rxMAX_len) {

                                System.arraycopy(data, 0, COM_data.rx_buf, COM_data.rx_len, len);
                                COM_data.rx_len += len;

                            } else {
                                Arrays.fill(COM_data.rx_buf, (byte) 0);
                                COM_data.rx_len = 0;
                                COM_data.serialPort.readBytes();
                            }

                            //PArce package
                            if (COM_data.rx_len > 4) {

                                //Найти стоповые байты в массиве
                                for (int i = 0; i < COM_data.rx_len - 1; i++) {

                                    //Если стоповые байты найдены
                                    if ((COM_data.rx_buf[i] == stop_1) && (COM_data.rx_buf[i + 1] == stop_2)) {

                                        COM_data.packRecive = 1; //Пойман пакет
                                        COM_data.serialPort.readBytes();
                                    }
                                }
                            }
                        }
                        
                        //Transparent mode
                        if (COM_data.Mode == COM_data.MODE_TRANSPARENT) {
                            
                            //Check process
                            if (COM_data.dataRecive == 1) {
                                Thread.sleep(5);
                            }

                            if (COM_data.dataRecive == 1) {
                                return;
                            }
                            
                            //Получить данные
                            int len = event.getEventValue();
                            byte[] data = COM_data.serialPort.readBytes(len);

                            //Счетчик принятых байт
                            if ((COM_data.rx_len + len) < COM_data.rxMAX_len) {

                                System.arraycopy(data, 0, COM_data.rx_buf, COM_data.rx_len, len);
                                COM_data.rx_len += len;
                                COM_data.serialPort.readBytes();
                                COM_data.dataRecive = 1;

                            } else {
                                Arrays.fill(COM_data.rx_buf, (byte) 0);
                                COM_data.rx_len = 0;
                                COM_data.serialPort.readBytes();
                            }

                        }


                    } catch (SerialPortException ex) {
                        Logger.getLogger(Serial_port.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Serial_port.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                }

            }
        }
    }

    //Открыть COM порт
    public boolean openCOMPort(String COM_in, int COM_speed) {
        boolean val = false;
        
        COM_data.serialPort = new SerialPort(COM_in);
        try {
            COM_data.serialPort.openPort();
            COM_data.serialPort.setParams(COM_speed, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            COM_thead = new COM_thead_с();
            COM_thead.start();
            COM_thead.setPriority(2);
            COM_data.state = 1; //COM порт открыт
            val = true;
        }
        catch (SerialPortException ex) {
            System.out.println(ex);
        }
        return val;
    }
    
    //Закрыть порт
    public boolean closeCOMPort() {
        boolean val = false;
        
        try {
            COM_data.serialPort.closePort();
            COM_data.state = 0; //COM порт закрыт
            COM_thead.stop();
            val = true;
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
        return val;
    }
    
    //Отправить байты в COM порт
    public void writeBytes(byte array[], int len) {
        byte[] array_toSend = new byte[len];
        System.arraycopy(array, 0, array_toSend, 0, len);
        
        try {
            COM_data.serialPort.writeBytes(array_toSend);
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }
    
    //Отправить байты в COM порт
    public void writeBytes(byte array[]) {
        try {
            COM_data.serialPort.writeBytes(array);
        } catch (SerialPortException ex) {
            System.out.println(ex);
        }
    }

    //Return buffer
    public class Buf_class {
        public byte[] retData = new byte[300];
        public int retLen = 0;
        public boolean recived = false;
        public byte retStatus = 0;
    }
    
    //Send command return ret data len
    public Buf_class sendCMD(byte cmd, byte[] payload, int payloadLen) {

        Buf_class retVal = new Buf_class();
        byte[] crc_val = new byte[2];
        CRC16_c CRC16 = new CRC16_c();
        byte[] sendData = new byte[300];
        
        sendData[0] = cmd;
        
        if (payload == null)
        {
            payloadLen = 0;
        }else{
            System.arraycopy(payload, 0, sendData, 1, payloadLen);
        }
                
        CRC16.calc(crc_val, sendData, payloadLen+1);
        sendData[payloadLen+1] = crc_val[0];
        sendData[payloadLen+2] = crc_val[1];
        
        sendData[payloadLen+3] = stop_1;
        sendData[payloadLen+4] = stop_2;
        
        writeBytes(sendData, payloadLen + 5);

        int try_counter = 0;
        COM_data.packRecive = 0;

        try {
            while (COM_data.packRecive != 1) {

                if (try_counter == 200) {
                    break;
                }
                try_counter++;
                Thread.sleep(10);

            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Serial_port.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Если пакет пришел
        if (COM_data.packRecive == 1) {

            //Проверка ответа
            crc_val[0] = 0;
            crc_val[1] = 0;
            //Расчет CRC
            CRC16.calc(crc_val, COM_data.rx_buf, COM_data.rx_len - 2);

            //Проверка CRC
            if ((crc_val[0] == (byte) 0x00) & (crc_val[1] == (byte) 0x00)) {
                System.arraycopy(COM_data.rx_buf, 2, retVal.retData, 0, COM_data.rx_len - 6);
                retVal.retLen = COM_data.rx_len - 6;
                retVal.retStatus = COM_data.rx_buf[1];
                retVal.recived = true;
            }
        }else{
            retVal.recived = false;
        }
        
        COM_data.packRecive = 0;
        COM_data.rx_len = 0;
            
        return retVal;
    }
}
