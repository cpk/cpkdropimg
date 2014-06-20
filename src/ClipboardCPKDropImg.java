

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Khoa
 */
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class ClipboardCPKDropImg implements FlavorListener {

    private Clipboard clipboard;
    private static int i;
    TrayIcon trayIcon;

    public ClipboardCPKDropImg(TrayIcon trayIcon) {
        this.trayIcon = trayIcon;
    }

    ;

    void start() {
        clipboard = getSystemClipboard();
    }

    private Clipboard getSystemClipboard() {
        clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.addFlavorListener(this);
        if (clipboard == null) {
            System.err.println("clipboard is null");
            return clipboard;
        } else {
            return clipboard;
        }
    }

    @Override
    public void flavorsChanged(FlavorEvent e) {
        try {
            //Get data from clipboard and assign it to an image.
            //clipboard.getData() returns an object, so we need to cast it to a BufferdImage.
            if (Toolkit.getDefaultToolkit().getSystemClipboard().isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                BufferedImage image = (BufferedImage) clipboard.getData(DataFlavor.imageFlavor);
                //create Jframe and draw image 
                ShowJFrame drapDropImg = new ShowJFrame();
                drapDropImg.setExtendedState(JFrame.MAXIMIZED_BOTH);
                drapDropImg.imgPanel.setViewportView(new ImgShow(image, drapDropImg, this.trayIcon));
                drapDropImg.setVisible(true);
            }
        } //getData throws this.
        catch (UnsupportedFlavorException ufe) {
            ufe.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(ClipboardCPKDropImg.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
