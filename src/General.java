
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Khoa
 */
public class General {
    public static String PATHTEMP = "";
    public static String SERVER = "pchuanvn.myftp.org";
    public static String PORT = "21";
    public static String USER = "CNKD";
    public static String PASS = "cuibap";
    public static String DIR = "images";
    public static String PATHTEMP_NAME = "PATHTEMP_CPKDROPIMG";
    public static String SERVER_NAME = "SERVER_CPKDROPIMG";
    public static String PORT_NAME = "PORT_CPKDROPIMG";
    public static String USER_NAME = "USER_CPKDROPIMG";
    public static String PASS_NAME = "PASS_CPKDROPIMG";
    public static String DIR_NAME = "DIR_CPKDROPIMG";
    
    public static void clearClipBoard(){
        StringSelection selection = new StringSelection("CPKDropImg");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }
}
