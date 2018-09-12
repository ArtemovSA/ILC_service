/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ILC_service;

import ILC_service.Serial_port.Buf_class;
import static java.lang.Math.floor;

/**
 *
 * @author user
 */
public class ILC_device_c {

    public Serial_port port;
    
    //Commands
    private final int USBC_CMD_DEBUG = 0; //Debug command
    private final int USBC_CMD_CONN_CHECK = 1; //Connection check
    private final int USBC_CMD_FLASH_WRITE = 2; //Write data to NAND
    private final int USBC_CMD_FLASH_READ = 3; //Read data from NAND
    private final int USBC_CMD_CHANGE_MODE = 4;  //Change mode
    private final int USBC_CMD_SCRYPT_LOAD = 5;  //Load script
    private final int USBC_CMD_SCRYPT_START = 6;  //Start script
    private final int USBC_CMD_SCRYPT_STOP = 7;  //Stop script
    private final int USBC_CMD_SCRYPT_PAUSE = 8;  //Pause script
    private final int USBC_CMD_SET_SETTINGS = 9;  //Set settings
    private final int USBC_CMD_GET_SETTINGS = 10;   //Get settings
    private final int USBC_CMD_ASSIGN_SETTINGS = 11; //Assign settings
    private final int USBC_CMD_DEFAULT_SETTINGS = 12; //Set default settings
    private final int USBC_CMD_SET_CALIBRATE = 13;  //Set CALIBRATE
    private final int USBC_CMD_SYSTEM_RESET = 14; //Reset
    private final int USBC_CMD_SET_CALIBR = 15;          //Set calibratings data
    private final int USBC_CMD_GET_CALIBR = 16;          //Get calibrate data
    private final int USBC_CMD_GET_VALUES = 17;     //Get values
    //Modes
    public static int USBP_MODE_CMD = 0;
    public static int USBP_MODE_DEBUG = 1;
    public static int USBP_MODE_SCRIPT = 2;
    
    //Command status
    public static int USBC_STAT_EXEC_END = 0;
    public static int USBC_STAT_EXEC_START = 1;
    public static int USBC_STAT_EXEC_CONT = 2;

    //Return values
    public static final int USBC_RET_ERROR = 0;
    public static final int USBC_RET_OK = 1;
    public static final int USBC_RET_ADDR_ERR = 2;
    public static final int USBC_RET_OVERF = 3;
    public static final int USBC_RET_NANS = 4;
    public static final int USBC_RET_NEXIST = 5;        

    //Settings ID
    public static final int DC_SET_NET_MAC_ADR = 1;
    public static final int DC_SET_NET_DHCP_EN = 2;
    public static final int DC_SET_NET_DEV_IP = 3;
    public static final int DC_SET_NET_GW_IP = 4;
    public static final int DC_SET_NET_MASK = 5;
    public static final int DC_SET_NTP_DOMEN = 6;
    public static final int DC_SET_NET_DNS_IP = 7;
    public static final int DC_SET_MQTT_IP = 8;
    public static final int DC_SET_MQTT_DOMEN = 9;
    public static final int DC_SET_MQTT_CH = 10;
    public static final int DC_SET_MQTT_PORT = 11;
    public static final int DC_SET_MQTT_USER = 12;
    public static final int DC_SET_MQTT_PASS = 13;
    public static final int DC_SET_MQTT_QOS = 14;
    public static final int DC_SET_EMS_PERIOD = 15;
    public static final int DC_SET_EMS_AUTO_SEND = 16;
    public static final int DC_SET_EMS_CHANNEL_EN = 17;
    public static final int DC_SET_VM_AUTO_START = 18;

    //Connection check
    public boolean checkConnection() {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_CONN_CHECK, null, 0, 2000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Change mode
    public boolean changeMode(int mode)
    {
        Buf_class retVal;
        byte[] modeData = new byte[2];
        modeData[0] = (byte)mode;
        
        retVal = port.sendCMD((byte)USBC_CMD_CHANGE_MODE, modeData, 1, 2000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Return buffer
    public class ILC_buf_c {
        public byte[] Data = new byte[300];
        public int Len = 0;
        public int status;
    }
    //Write NAND data
    public Buf_class writeNandData(int block, int page, int offset, byte[] data, int len)
    {
        Buf_class retVal;
        byte[] payload = new byte[300];
        payload[0] = HI(block);
        payload[1] = LO(block);
        
        payload[2] = HI(page);
        payload[3] = LO(page);
        
        payload[4] = HI(offset);
        payload[5] = LO(offset);
        
        payload[6] = HI(len);
        payload[7] = LO(len);
        
        System.arraycopy(data, 0, payload, 8, len);
        
        retVal = port.sendCMD((byte)USBC_CMD_FLASH_WRITE, payload, len+8, 2000);
        
        return retVal;
    }
    
    //Read NAND data
    public Buf_class readNandData(int block, int page, int offset, int len)
    {
        Buf_class retVal;
        
        byte[] payload = new byte[300];
        payload[0] = HI(block);
        payload[1] = LO(block);
        
        payload[2] = HI(page);
        payload[3] = LO(page);
        
        payload[4] = HI(offset);
        payload[5] = LO(offset);
        
        payload[6] = HI(len);
        payload[7] = LO(len);
        
        retVal = port.sendCMD((byte)USBC_CMD_FLASH_READ, payload, 8, 2000);
        
        return retVal;
    }
    
    //Reset device
    public boolean resetDevice()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_SYSTEM_RESET, null, 0, 2000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Load scrypt
    public boolean loadScypt(String name, byte[] script, long len)
    {
        int maxLenPart = 256;
        Buf_class retVal;
        byte[] dataScript = new byte[300];
        int countParts = (int) floor(len/maxLenPart);
        int tailLen = (int) (len - countParts*maxLenPart);
        byte[] nameBuf = new byte[20];
        byte[] ctcVal = new byte[2];
        CRC16_c crc16 = new CRC16_c();
        
        //Send info data
        System.arraycopy(name.getBytes(), 0, nameBuf, 0, name.getBytes().length);
        crc16.calc(ctcVal, script, len);
 
        dataScript[0] = (byte) USBC_STAT_EXEC_START;
        dataScript[1] = HI(len);
        dataScript[2] = LO(len);
        dataScript[3] = ctcVal[1];
        dataScript[4] = ctcVal[0];
        System.arraycopy(nameBuf, 0, dataScript, 5, 20);
        
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_LOAD, dataScript, 25, 4000);
        
        if (retVal.retStatus != USBC_RET_OK)
            return false;
        
        //Full Parts
        for (int i = 0; i < countParts; i++) {
            
            dataScript[0] = (byte) USBC_STAT_EXEC_CONT;
            dataScript[1] = HI(i);
            dataScript[2] = LO(i);
            dataScript[3] = HI(maxLenPart);
            dataScript[4] = LO(maxLenPart);
            
            
            System.arraycopy(script, i*maxLenPart, dataScript, 5, maxLenPart);
            
            retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_LOAD, dataScript, maxLenPart+5, 4000);
            
            if (retVal.retStatus != USBC_RET_OK)
                return false;
        }

        //Tail
        dataScript[0] = (byte)USBC_STAT_EXEC_END;
        dataScript[1] = HI(countParts);
        dataScript[2] = LO(countParts);
        dataScript[3] = HI(tailLen);
        dataScript[4] = LO(tailLen);
            
        System.arraycopy(script, countParts*maxLenPart, dataScript, 5, tailLen);
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_LOAD, dataScript, tailLen+5, 10000);
        
        if (retVal.retStatus != USBC_RET_OK)
            return false;
        
        return true;
    }
    
    //Start scrypt
    public boolean StartScrypt()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_START, null, 0, 1000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Stop scrypt
    public boolean StopScrypt()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_STOP, null, 0, 1000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }

    //Pause scrypt
    public boolean PauseScrypt()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_PAUSE, null, 0, 1000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    public ILC_buf_c readSettingParam(int setID)
    {
        ILC_buf_c retBuf = new ILC_buf_c();
        Buf_class retVal;
        byte[] payload = new byte[20];

        payload[0] = (byte) setID;
     
        retVal = port.sendCMD((byte)USBC_CMD_GET_SETTINGS, payload, 1, 1000);

        retBuf.status = retVal.retStatus;
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            retBuf.Len = retVal.retData[0];
            System.arraycopy(retVal.retData, 1, retBuf.Data, 0, retBuf.Len);
        }

        return  retBuf;
    }
    
    public boolean writeSettingParam(int setID, byte[] data, int len)
    {
        byte[] payload = new byte[300];
        Buf_class retVal;
        
        payload[0] = (byte) setID;
        payload[1] = (byte)len;
        System.arraycopy(data, 0, payload, 2, len);
        
        retVal = port.sendCMD((byte)USBC_CMD_SET_SETTINGS, payload, len+2, 1000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Assign settings
    public boolean assignSettings()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_ASSIGN_SETTINGS, null, 0, 2000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Reset settings
    public boolean resetSettings()
    {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_DEFAULT_SETTINGS, null, 0, 2000);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    public ILC_buf_c readValues(int valID, int channel, int line)
    {
        ILC_buf_c retBuf = new ILC_buf_c();
        Buf_class retVal;
        byte[] payload = new byte[20];

        payload[0] = (byte) valID;
        payload[1] = (byte) channel;
        payload[2] = (byte) line;
     
        retVal = port.sendCMD((byte)USBC_CMD_GET_VALUES, payload, 3, 1000);

        retBuf.status = retVal.retStatus;
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            retBuf.Len = retVal.retData[0];
            System.arraycopy(retVal.retData, 1, retBuf.Data, 0, retBuf.Len);
        }

        return  retBuf;
    }

    public ILC_device_c(Serial_port serialport) {
        port = serialport;
    }

    
    public byte HI(long value) {
       return  (byte) ((value & 0xFF00) >> 8);
    }
    
    public byte LO(long value) {
       return  (byte) (value & 0x00FF) ;
    }
    
}
