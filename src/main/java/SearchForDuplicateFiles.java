package searchforduplicatefiles;

import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.awt.Dimension;
import javax.swing.JOptionPane;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */



public class SearchForDuplicateFiles
{
    boolean packFrame = false;
    CheckForUpdate th1;

    /**
     * Construct and show the application.
     */
    public SearchForDuplicateFiles()
    {
        globals.settings = new Settings();

        globals.frame1 = new Frame1();
        // Validate frames that have preset sizes
        // Pack frames that have useful preferred size info, e.g. from their layout
        if (packFrame)
        {
            globals.frame1.pack();
        }
        else
        {
            globals.frame1.validate();
        }

        // Center the window
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = globals.frame1.getSize();
        if (frameSize.height > screenSize.height)
        {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width)
        {
            frameSize.width = screenSize.width;
        }
        globals.frame1.setLocation((screenSize.width - frameSize.width) / 2,
                                   (screenSize.height - frameSize.height) / 2);
        globals.frame1.setVisible(true);
        JOptionPane.showMessageDialog(globals.frame1,"This is an open source version of DFS that offers only basic functionality.\nThere is also avilable the full version which offers extra features.\nThe full version is also freeware!");
        th1 = new CheckForUpdate();
        th1.start();
    }

    /**
     * Application entry point.
     *
     * @param args String[]
     */
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.
                                             getSystemLookAndFeelClassName());
                } catch (Exception exception)
                {
                    exception.printStackTrace();
                }

                new SearchForDuplicateFiles();
            }
        });
    }
}
