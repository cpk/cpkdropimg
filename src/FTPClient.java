
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Khoa
 */
public class FTPClient {

    /**
     * ftp://user:password@host:port/filePath;type=i.
     */
    static public void upload(String fileName, File source) throws MalformedURLException, IOException {
        if (General.SERVER != null && fileName != null && source != null) {
            StringBuffer sb = new StringBuffer("ftp://");

            if (General.USER != null && General.PASS != null) {
                sb.append(General.USER);
                sb.append(':');
                sb.append(General.PASS);
                sb.append('@');
            }
            sb.append(General.SERVER);
            sb.append(':');
            sb.append(General.PORT);
            sb.append('/');
            sb.append("cpkdropimg/app/webroot/img/" + General.DIR + "/" + fileName);
            /*
             * type ==> a=ASCII mode, i=image (binary) mode, d= file directory listing
             */
            sb.append(";type=i");

            BufferedInputStream bis = null;
            BufferedOutputStream bos = null;
            try {
                URL url = new URL(sb.toString());
                URLConnection urlc = url.openConnection();

                bos = new BufferedOutputStream(urlc.getOutputStream());
                bis = new BufferedInputStream(new FileInputStream(source));

                int i;

                while ((i = bis.read()) != -1) {
                    bos.write(i);
                }
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
                if (bos != null) {
                    try {
                        bos.close();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            }
        } else {
            System.out.println("Input not available.");
        }
    }
}
