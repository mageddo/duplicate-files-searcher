package searchforduplicatefiles;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import java.net.URL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.io.FileOutputStream;

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
public class Frame1 extends JFrame
{
    JPanel contentPane;
    JMenuBar jMenuBar1 = new JMenuBar();
    JMenu jMenuFile = new JMenu();
    JMenuItem jMenuFileExit = new JMenuItem();
    JMenu jMenuHelp = new JMenu();
    JMenuItem jMenuHelpAbout = new JMenuItem();
    JToolBar jToolBar = new JToolBar();
    JButton OpenDirButton = new JButton();
    JButton HelpButton = new JButton();
    ImageIcon image1 = new ImageIcon(searchforduplicatefiles.Frame1.class.
                                     getResource("openFile.png"));
    ImageIcon image2 = new ImageIcon(searchforduplicatefiles.Frame1.class.
                                     getResource("closeFile.png"));
    ImageIcon image3 = new ImageIcon(searchforduplicatefiles.Frame1.class.
                                     getResource("help.png"));
    JLabel statusBar = new JLabel();

    JProgressBar jProgressBar1 = new JProgressBar();
    CalculateHash th1;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    DefaultMutableTreeNode root = new DefaultMutableTreeNode("Duplicate Files");

    JButton PauseButton = new JButton();
    JButton StopButton = new JButton();
    JButton StartButton = new JButton();
    Border border1 = BorderFactory.createEmptyBorder();
    JCheckBox DelFilesCheckBox = new JCheckBox();
    JCheckBox MD5CheckBox = new JCheckBox();
    JCheckBox SHACheckBox = new JCheckBox();
    TitledBorder titledBorder1 = new TitledBorder("");
    TitledBorder titledBorder2 = new TitledBorder("");
    JLabel jLabel3 = new JLabel();
    TitledBorder titledBorder3 = new TitledBorder("");
    JMenuItem jMenuItem1 = new JMenuItem();
    JMenuItem jMenuOpenDirectory = new JMenuItem();
    JCheckBox SubDirCheckBox = new JCheckBox();
    TitledBorder titledBorder4 = new TitledBorder("");
    TitledBorder titledBorder5 = new TitledBorder("");
    JPanel jPanel1 = new JPanel();
    JMenu jMenu1 = new JMenu();
    JMenuItem jMenuItem2 = new JMenuItem();
    public Frame1()
    {
        try
        {
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            jbInit();
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    /**
     * Component initialization.
     *
     * @throws java.lang.Exception
     */
    private void jbInit() throws Exception
    {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(420, 370));
        setTitle("Frame Title");
        statusBar.setBorder(null);
        statusBar.setText("");
        statusBar.setBounds(new Rectangle(1, 292, 408, 19));
        jMenuFile.setText("File");
        jMenuFileExit.setText("Exit");
        jMenuFileExit.addActionListener(new Frame1_jMenuFileExit_ActionAdapter(this));
        jMenuHelp.setText("Help");
        jMenuHelpAbout.setText("About");
        jMenuHelpAbout.addActionListener(new
                                         Frame1_jMenuHelpAbout_ActionAdapter(this));
        OpenDirButton.addActionListener(new Frame1_jButton1_actionAdapter(this));
        jToolBar.setBounds(new Rectangle(0, 0, 400, 25));
        jProgressBar1.setForeground(new Color(122, 117, 190));
        jProgressBar1.setToolTipText("Postêp");
        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setBounds(new Rectangle(7, 245, 393, 22));
        DelFilesCheckBox.setDoubleBuffered(true);
        DelFilesCheckBox.setToolTipText(
                "Here you can choose if duplicated files should be deleted or not. " +
                "For example, if two identical files are found then one of them will " +
                "be deleted.");
        DelFilesCheckBox.setText("Delete duplicated files");
        DelFilesCheckBox.setBounds(new Rectangle(8, 40, 136, 23));
        DelFilesCheckBox.addActionListener(new Frame1_jCheckBox1_actionAdapter(this));

        MD5CheckBox.setText("MD5");
        MD5CheckBox.setBounds(new Rectangle(165, 8, 47, 23));
        MD5CheckBox.addActionListener(new Frame1_jCheckBox2_actionAdapter(this));
        MD5CheckBox.setDoubleBuffered(true);
        MD5CheckBox.setSelected(true);

        SHACheckBox.setText("SHA");
        SHACheckBox.setBounds(new Rectangle(228, 8, 47, 23));
        SHACheckBox.addActionListener(new Frame1_jCheckBox3_actionAdapter(this));
        SHACheckBox.setDoubleBuffered(true);
        SHACheckBox.setSelected(true);

        jLabel1.setBorder(null);
        jLabel1.setText("");
        jLabel1.setBounds(new Rectangle(7, 127, 378, 25));
        jLabel2.setBorder(null);
        jLabel2.setBounds(new Rectangle(7, 164, 380, 25));
        PauseButton.setBounds(new Rectangle(89, 202, 76, 25));
        PauseButton.setText("Pause");
        PauseButton.addActionListener(new Frame1_jButton4_actionAdapter(this));
        PauseButton.setEnabled(false);
        StopButton.setBounds(new Rectangle(185, 202, 66, 25));
        StopButton.setText("Stop");
        StopButton.addActionListener(new Frame1_jButton5_actionAdapter(this));
        StopButton.setEnabled(false);
        StartButton.setBounds(new Rectangle(7, 202, 66, 25));
        StartButton.setBorder(border1);
        StartButton.setText("Start");
        StartButton.addActionListener(new Frame1_jButton6_actionAdapter(this));
        StartButton.setEnabled(false);

        jLabel3.setToolTipText(
                "Here, You can select alorithms which are used to hash files. You " +
                "can choose one or two algorithms.");
        jLabel3.setText("Select hashing algorithm:");
        jLabel3.setBounds(new Rectangle(8, 9, 126, 20));
        jMenuOpenDirectory.setDoubleBuffered(true);
        jMenuOpenDirectory.setText("Open Directory");
        jMenuOpenDirectory.addActionListener(new
                                             Frame1_jMenuOpenDirectory_actionAdapter(this));
        SubDirCheckBox.setText("Search subdirectories");
        SubDirCheckBox.setBounds(new Rectangle(165, 40, 139, 23));
        SubDirCheckBox.addActionListener(new
                                         Frame1_SubDirCheckBox_actionAdapter(this));
        SubDirCheckBox.setToolTipText(
                "Select if subdirectories should be searched or not");
        SubDirCheckBox.setSelected(true);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setBounds(new Rectangle(7, 47, 370, 70));
        jPanel1.setLayout(null);
        jMenu1.setEnabled(false);
        jMenu1.setText("Get DFS Full");
        jMenuItem2.setText("Get Duplicate Files Searcher Full");
        jMenuItem2.addActionListener(new Frame1_jMenuItem2_actionAdapter(this));
        jMenuBar1.add(jMenuFile);
        jMenuFile.add(jMenuOpenDirectory);
        jMenuFile.add(jMenuFileExit);
        jMenuBar1.add(jMenuHelp);
        jMenuBar1.add(jMenu1);
        jMenuHelp.add(jMenuHelpAbout);
        setJMenuBar(jMenuBar1);
        OpenDirButton.setIcon(image1);
        OpenDirButton.setToolTipText("Open Directory");
        HelpButton.setIcon(image3);
        HelpButton.setToolTipText("Help");
        jToolBar.add(OpenDirButton);
        jToolBar.add(HelpButton);
        contentPane.add(jToolBar, null);
        contentPane.add(jPanel1);
        jPanel1.add(jLabel3);
        jPanel1.add(MD5CheckBox);
        jPanel1.add(SHACheckBox);
        jPanel1.add(DelFilesCheckBox);
        jPanel1.add(SubDirCheckBox);
        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(StartButton);
        contentPane.add(PauseButton);
        contentPane.add(StopButton);
        contentPane.add(jProgressBar1);
        contentPane.add(statusBar, null);
        jMenu1.add(jMenuItem2);
        setTitle(globals.settings.getProperty("LastDirectory"));
        jLabel1.setText("Go to menu \"File\"->\"Open Directory\"");
        jLabel2.setText("to select the directory for duplicate files finding.");
        UpDateComponents();
    }

    private final void UpDateComponents()
    {
        if (globals.settings.getProperty("SHA").equalsIgnoreCase("true"))
        {
            SHACheckBox.setSelected(true);
        }
        else
        {
            SHACheckBox.setSelected(false);
        }

        if (globals.settings.getProperty("MD5").equalsIgnoreCase("true"))
        {
            MD5CheckBox.setSelected(true);
        }
        else
        {
            MD5CheckBox.setSelected(false);
        }

        if (globals.settings.getProperty("deleteduplicatedfiles").
            equalsIgnoreCase("true"))
        {
            DelFilesCheckBox.setSelected(true);
        }
        else
        {
            DelFilesCheckBox.setSelected(false);
        }

        if (globals.settings.getProperty("seachsubdirectories").
            equalsIgnoreCase("true"))
        {
            SubDirCheckBox.setSelected(true);
        }
        else
        {
            SubDirCheckBox.setSelected(false);
        }

    }

    /**
     * File | Exit action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuFileExit_actionPerformed(ActionEvent actionEvent)
    {
        /*Exit*/
        globals.settings.SaveSettings();
        System.exit(0);
    }

    /**
     * Help | About action performed.
     *
     * @param actionEvent ActionEvent
     */
    void jMenuHelpAbout_actionPerformed(ActionEvent actionEvent)
    {
        Frame1_AboutBox dlg = new Frame1_AboutBox(this);
        Dimension dlgSize = dlg.getPreferredSize();
        Dimension frmSize = getSize();
        Point loc = getLocation();
        dlg.setLocation((frmSize.width - dlgSize.width) / 2 + loc.x,
                        (frmSize.height - dlgSize.height) / 2 + loc.y);
        dlg.setModal(true);
        dlg.pack();
        dlg.setVisible(true);
    }

    public void jButton1_actionPerformed(ActionEvent actionEvent)
    {
        /*open directory*/
        if (choosedirectory())
        {
            PauseButton.setEnabled(false);
            StopButton.setEnabled(false);
            StartButton.setEnabled(true);
        }
    }

    public void jButton6_actionPerformed(ActionEvent actionEvent)
    {
        /*Start*/
        th1 = new CalculateHash(this);
        th1.start();
        StartButton.setEnabled(false);
    }

    public void jButton4_actionPerformed(ActionEvent actionEvent)
    {
        /*Pause*/
        th1.suspended = !th1.suspended;
    }

    public void jButton5_actionPerformed(ActionEvent actionEvent)
    {
        /*Stop*/
        th1.stop = true;
        PauseButton.setEnabled(false);
        StopButton.setEnabled(false);
        StartButton.setEnabled(true);
    }

    public void jMenuOpenDirectory_actionPerformed(ActionEvent e)
    {
        /*File->OpenDirectory*/
        if (choosedirectory())
        {
            PauseButton.setEnabled(false);
            StopButton.setEnabled(false);
            StartButton.setEnabled(true);
        }
    }

    private final boolean choosedirectory()
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
        {
            return false;
        }
        globals.selFile = fc.getSelectedFile();
        setTitle(globals.selFile.getAbsolutePath());
        globals.settings.put("LastDirectory", globals.selFile.getAbsolutePath());
        return true;
    }

    public void jCheckBox2_actionPerformed(ActionEvent e)
    {
        /*MD5*/
        if (MD5CheckBox.isSelected())
        {
            globals.settings.setProperty("MD5", "true");
        }
        else
        {
            globals.settings.setProperty("MD5", "false");
        }

    }

    public void jCheckBox3_actionPerformed(ActionEvent e)
    {
        /*SHA*/
        if (SHACheckBox.isSelected())
        {
            globals.settings.setProperty("SHA", "true");
        }
        else
        {
            globals.settings.setProperty("SHA", "false");
        }

    }

    public void jCheckBox1_actionPerformed(ActionEvent e)
    {
        /*Delete duplicated files*/
        if (DelFilesCheckBox.isSelected())
        {
            globals.settings.setProperty("deleteduplicatedfiles", "true");
        }
        else
        {
            globals.settings.setProperty("deleteduplicatedfiles", "false");
        }

    }

    public void SubDirCheckBox_actionPerformed(ActionEvent e)
    {
        /*Search subdirectories*/

        if (SubDirCheckBox.isSelected())
        {
            globals.settings.setProperty("seachsubdirectories", "true");
        }
        else
        {
            globals.settings.setProperty("seachsubdirectories", "false");
        }

    }

    public void jMenuItem2_actionPerformed(ActionEvent e)
    {
    /*Get Duplicate Files Searcher Full menu*/
        int wbuforze;
        //int total;
        //byte dane[]=null;

        JOptionPane.showMessageDialog(this,
                "Click Ok to start downloading the DFS Full");
        globals.frame1.statusBar.setText("Downloading DFS Full");

        try
        {
            URL strona = new URL(
                    "http://rafalfr.w.interia.pl/dfsfull/dfsfull.zip");

            InputStream instream = strona.openStream();
            FileOutputStream out = new FileOutputStream("dfsfull.zip");
            byte[] tmpbufor = new byte[1024];

            //total=0;
            while ((wbuforze = instream.read(tmpbufor)) > 0)
            {
                //total+=wbuforze;
                //dane=append(dane,tmpbufor,wbuforze);
                out.write(tmpbufor, 0, wbuforze);

            }

//out.write(dane,0,total);
            instream.close();
            out.close();
        } catch (MalformedURLException ex)
        {
            globals.frame1.statusBar.setText(ex.toString());
            return;
        } catch (IOException ex)
        {
            globals.frame1.statusBar.setText(ex.toString());
            return;
        }

        JOptionPane.showMessageDialog(this, "Download completed!\nThe file dfsfull.zip is saved in the same directory as dfs.jar");

        globals.frame1.statusBar.setText("Downlaod completed");


    }

    private final byte[] append(byte[] arg1, byte[] arg2, int length2)
    {
        byte[] out;
        int i;
        int j;
        if (arg1 == null)
        {
            out = new byte[length2];
            for (i = 0; i < length2; i++)
            {
                out[i] = arg2[i];
            }
            return out;

        }
        else if (arg2 == null)
        {
            return arg1;
        }
        else
        {
            out = new byte[arg1.length + length2];
            for (i = 0, j = 0; i < arg1.length; i++, j++)
            {
                out[j] = arg1[i];
            }
            for (i = 0; i < length2; i++, j++)
            {
                out[j] = arg2[i];
            }
            return out;
        }
    }


}


class Frame1_jMenuItem2_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jMenuItem2_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jMenuItem2_actionPerformed(e);
    }
}


class Frame1_SubDirCheckBox_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_SubDirCheckBox_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.SubDirCheckBox_actionPerformed(e);
    }
}


class Frame1_jCheckBox3_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jCheckBox3_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jCheckBox3_actionPerformed(e);
    }
}


class Frame1_jCheckBox1_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jCheckBox1_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jCheckBox1_actionPerformed(e);
    }
}


class Frame1_jCheckBox2_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jCheckBox2_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jCheckBox2_actionPerformed(e);
    }
}


class Frame1_jButton5_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jButton5_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jButton5_actionPerformed(actionEvent);
    }
}


class Frame1_jButton4_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jButton4_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jButton4_actionPerformed(actionEvent);
    }
}


class Frame1_jButton6_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jButton6_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jButton6_actionPerformed(actionEvent);
    }
}


class Frame1_jButton1_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jButton1_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jButton1_actionPerformed(actionEvent);
    }
}


class Frame1_jMenuFileExit_ActionAdapter implements ActionListener
{
    Frame1 adaptee;

    Frame1_jMenuFileExit_ActionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jMenuFileExit_actionPerformed(actionEvent);
    }
}


class Frame1_jMenuOpenDirectory_actionAdapter implements ActionListener
{
    private Frame1 adaptee;
    Frame1_jMenuOpenDirectory_actionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e)
    {
        adaptee.jMenuOpenDirectory_actionPerformed(e);
    }
}


class Frame1_jMenuHelpAbout_ActionAdapter implements ActionListener
{
    Frame1 adaptee;

    Frame1_jMenuHelpAbout_ActionAdapter(Frame1 adaptee)
    {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent actionEvent)
    {
        adaptee.jMenuHelpAbout_actionPerformed(actionEvent);
    }
}
