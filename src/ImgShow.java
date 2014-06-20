/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Khoa
 */
public class ImgShow extends JPanel implements Runnable{

    public BufferedImage Bi;
    public Point start;
    public Point end;
    public ShowJFrame newJFrame;
    TrayIcon trayIcon;
    boolean dragImage;
    
    float time = 0;

    public ImgShow(BufferedImage bi, ShowJFrame newJFrame, TrayIcon trayIcon) {
        this.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
        this.newJFrame = newJFrame;
        this.trayIcon = trayIcon;
        this.Bi = bi;
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                imgPanelMousePressed(evt);
            }

            public void mouseReleased(java.awt.event.MouseEvent evt) {
                imgPanelMouseReleased(evt);
            }
        });
        this.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                imgPanelMouseDragged(evt);
            }
        });
        this.start = new Point(0, 0);
        this.end = new Point(0, 0);
        dragImage = true;
        Thread t = new Thread(this);
        t.start();
    }

    private void imgPanelMouseDragged(java.awt.event.MouseEvent evt) {
//        Graphics g = getGraphics();
//        g.setColor(new Color(0, 0, 0, 0.8f));
//        g.fillRect(this.start.x, this.start.y, this.end.x - this.start.x, this.end.y - this.start.y);
        this.end.x = evt.getX();
        this.end.y = evt.getY();
       
        //this.update(g);

    }

    private void imgPanelMousePressed(java.awt.event.MouseEvent evt) {
        this.start.x = evt.getX();
        this.start.y = evt.getY();
        this.end.x = evt.getX();
        this.end.y = evt.getY();
        //this.repaint();
    }

    private void imgPanelMouseReleased(java.awt.event.MouseEvent evt) {
        this.end.x = evt.getX();
        this.end.y = evt.getY();
        dragImage = false;
      
    }

    @Override
    public void update(Graphics g) {
        
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(0, 0, 0, 1));
        g.fillRect(0, 0, this.Bi.getWidth(), this.Bi.getHeight());
        g.drawImage(Bi, 0, 0, this);
        g.setColor(new Color(0, 0, 0, 0.8f));
        g.fillRect(0, 0, this.Bi.getWidth(), this.Bi.getHeight());
        g.drawImage(Bi, this.start.x, this.start.y, this.end.x, this.end.y, this.start.x, this.start.y, this.end.x, this.end.y, this);
        g.setColor(Color.WHITE);
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        g.drawString("Drag to Drop image", this.Bi.getWidth()/2 - 50, 50);
//         
    }

    public String getTime() {
        Calendar calendar = new GregorianCalendar();
        String am_pm;
        int day = calendar.get(Calendar.DATE);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        if (calendar.get(Calendar.AM_PM) == 0) {
            am_pm = "AM";
        } else {
            am_pm = "PM";
        }
        return "_" + day + "-" + month + "-" + year + "_" + hour + "h" + minute + "m" + second + "s" + am_pm;

    }

    @Override
    public void run() {
        while(dragImage){
//            time = System.get
            repaint();
        }
        
        BufferedImage img = this.Bi.getSubimage(this.start.x, this.start.y, this.end.x - this.start.x, this.end.y - this.start.y);
        //file that we'll save to disk.
        File file = new File(General.PATHTEMP + "cpk" + this.getTime() + ".jpg");
        this.newJFrame.hide();
        try {
            //class to write image to disk.  You specify the image to be saved, its type,
            // and then the file in which to write the image data.
            ImageIO.write(img, "jpg", file);
            FTPClient.upload(file.getName(), file);
        } catch (IOException ex) {
            //Logger.getLogger(ImgJPanel.class.getName()).log(Level.SEVERE, null, ex);
//            this.trayIcon.displayMessage("Upload error",
//                            ex.getMessage(), TrayIcon.MessageType.ERROR);
        }
        file.delete();
        String url = "http://" + General.SERVER + "/cpkdropimg/home/show/" + General.DIR + "/" + file.getName();
        StringSelection selection = new StringSelection(url);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
        this.trayIcon.displayMessage("Upload successful", "Link is copied to clipboard\n" + url, TrayIcon.MessageType.INFO);
        this.newJFrame.dispose();
    }
}
