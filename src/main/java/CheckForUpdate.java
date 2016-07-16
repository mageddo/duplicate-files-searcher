package searchforduplicatefiles;

import java.awt.Dimension;
import java.awt.Point;
import java.io.*;
import java.net.*;
import java.util.*;


public class CheckForUpdate extends Thread
{
    public CheckForUpdate()
    {
    }

    public void run()
    {
        checkforupdate();
    }

    static void checkforupdate()
    {
        /*this method checks if update is available*/

        Socket socket = null;
        BufferedReader in = null;
        BufferedWriter out = null;
        String tekst = new String();
        Date data;
        int wbuforze;

        /*cv.txt*/

        globals.frame1.statusBar.setText("Checking for update");
        StringBuffer dane = new StringBuffer();
        try
        {
            URL strona = new URL("http://rafalfr.w.interia.pl/cv.txt");
            BufferedReader instream = new BufferedReader(new
                    InputStreamReader(
                            strona.
                            openStream()));
            char[] tmpbufor = new char[128];

            while ((wbuforze = instream.read(tmpbufor)) > 0)
            {
                dane.append(tmpbufor, 0, wbuforze);
            }

            instream.close();
        } catch (MalformedURLException ex)
        {
        } catch (IOException ex)
        {
            globals.frame1.statusBar.setText("Cannot check for update!");
            return;
        }

        if (!globals.settings.getProperty("CurrentVersion").
            equalsIgnoreCase(
                    dane.substring(3)))
        {
            globals.frame1.statusBar.setText(
                    "New version available. Visit http://sourceforge.net/projects/dfr/");
            NewVersion_Box dlg = new NewVersion_Box();
            Dimension dlgSize = dlg.getPreferredSize();
            Dimension frmSize = globals.frame1.getSize();
            Point loc = globals.frame1.getLocation();
            dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                            (frmSize.height - dlgSize.height) / 2 + loc.y);
            dlg.setModal(true);
            dlg.pack();
            dlg.setVisible(true);
        }
        else
        {
            globals.frame1.statusBar.setText("No new version available");
        }



}
