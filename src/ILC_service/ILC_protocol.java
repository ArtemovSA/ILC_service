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
public class ILC_protocol {

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
    private final int USBC_CMD_SET_SETTINGS = 8;  //Set settings
    private final int USBC_CMD_SET_CALIBRATE = 9;  //Set CALIBRATE

    //Modes
    public static int USBP_MODE_CMD = 0;
    public static int USBP_MODE_DEBUG = 1;
    public static int USBP_MODE_SCRIPT = 2;

    //Return values
    public static final int USBC_RET_ERROR = 0;
    public static final int USBC_RET_OK = 1;
    public static final int USBC_RET_ADDR_ERR = 2;
    public static final int USBC_RET_OVERF = 3;
    public static final int USBC_RET_NANS = 4;
    public static final int USBC_RET_NEXIST = 5;        

    //Connection check
    public boolean checkConnection() {
        Buf_class retVal;
        retVal = port.sendCMD((byte)USBC_CMD_CONN_CHECK, null, 0);
        
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
        
        retVal = port.sendCMD((byte)USBC_CMD_CHANGE_MODE, modeData, 1);
        
        if (retVal.retStatus == USBC_RET_OK)
        {
            return true;
        }else{
            return false;
        }
    }
    
    //Return buffer
    public class buf_c {
        public byte[] Data = new byte[300];
        public int Len = 0;
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
        
        retVal = port.sendCMD((byte)USBC_CMD_FLASH_WRITE, payload, len+8);
        
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
        
        retVal = port.sendCMD((byte)USBC_CMD_FLASH_READ, payload, 8);
        
        return retVal;
    } 
    
    //Load scrypt
    public boolean loadScypt(byte[] script, long len)
    {
        int maxLenPart = 256;
        Buf_class retVal;
        byte[] partScript = new byte[300];
        int countParts = (int) floor(len/maxLenPart);
        int tailLen = (int) (len - countParts*maxLenPart);
        
        //Full Parts
        for(int i=0; i<countParts; i++)
        {
            partScript[0] = HI(i);
            partScript[1] = LO(i);
            partScript[2] = HI(maxLenPart);
            partScript[3] = LO(maxLenPart);
            
            System.arraycopy(script, i*maxLenPart, partScript, 4, maxLenPart);
            
            retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_LOAD, partScript, maxLenPart+4);
            
            if (retVal.retStatus != USBC_RET_OK)
                return false;
        }

        //Tail
        partScript[0] = HI(countParts);
        partScript[1] = LO(countParts);
        partScript[2] = HI(tailLen);
        partScript[3] = LO(tailLen);
            
        System.arraycopy(script, countParts*maxLenPart, partScript, 4, tailLen);
        retVal = port.sendCMD((byte)USBC_CMD_SCRYPT_LOAD, partScript, tailLen+4);
        
        if (retVal.retStatus != USBC_RET_OK)
                return false;
        
        return true;
    }

    public ILC_protocol(Serial_port serialport) {
        port = serialport;
    }

    
    public byte HI(long value) {
       return  (byte) ((value & 0xFF00) >> 8);
    }
    
    public byte LO(long value) {
       return  (byte) (value & 0x00FF) ;
    }
}
