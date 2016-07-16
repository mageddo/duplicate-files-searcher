package searchforduplicatefiles;

import java.io.*;
import java.security.*;
import java.util.Vector;

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
public final class CalculateHash extends Thread
{


    private Frame1 frame;
    public boolean suspended;
    public boolean stop;
    Vector files;
    Vector todeletion;

    public CalculateHash()
    {
        suspended = false;
        stop = false;
        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public CalculateHash(Frame1 arg)
    {
        frame = arg;
        suspended = false;
        stop = false;
    }

    public final void run()
    {
        /*this method calaculates hashes*/

        int i;
        byte[] buf;
        byte[] sha_digest;
        byte[] md5_digest;
        byte[] digest;
        boolean sha;
        boolean md5;
        DigestInputStream din;
        FileOutputStream out;

        buf = new byte[512];

        sha_digest = null;
        md5_digest = null;
        sha = frame.DelFilesCheckBox.isSelected();
        md5 = frame.MD5CheckBox.isSelected();

        globals.frame1.jLabel2.setText("Enumerating files in " +
                                       globals.settings.getProperty(
                                               "LastDirectory"));
        globals.selFile = new File(globals.settings.getProperty("LastDirectory"));
        files = new Vector();
        listfiles(globals.selFile);
        globals.frame1.jProgressBar1.setMaximum(files.size() - 1);
        globals.frame1.jProgressBar1.setMinimum(0);
        globals.frame1.PauseButton.setEnabled(true);
        globals.frame1.StopButton.setEnabled(true);
        globals.frame1.StartButton.setEnabled(false);

        for (i = 0; i < files.size(); i++)
        {
            while (suspended)
            {
                globals.frame1.PauseButton.setText("Continue");
                try
                {
                    this.sleep(1000);
                } catch (InterruptedException ex1)
                {
                }
            }
            if (stop)
            {
                globals.frame1.DelFilesCheckBox.setEnabled(true);
                globals.frame1.MD5CheckBox.setEnabled(true);
                globals.frame1.SHACheckBox.setEnabled(true);
                globals.frame1.SubDirCheckBox.setEnabled(true);
                globals.frame1.jMenuFile.setEnabled(true);
                globals.frame1.OpenDirButton.setEnabled(true);
                globals.frame1.jLabel3.setEnabled(true);
                globals.frame1.PauseButton.setEnabled(false);
                globals.frame1.StopButton.setEnabled(false);
                globals.frame1.StartButton.setEnabled(true);
                return;
            }

            globals.frame1.DelFilesCheckBox.setEnabled(false);
            globals.frame1.MD5CheckBox.setEnabled(false);
            globals.frame1.SHACheckBox.setEnabled(false);
            globals.frame1.SubDirCheckBox.setEnabled(false);
            globals.frame1.jMenuFile.setEnabled(false);
            globals.frame1.OpenDirButton.setEnabled(false);
            globals.frame1.jLabel3.setEnabled(false);
            globals.frame1.PauseButton.setText("Pause");

            globals.frame1.jProgressBar1.setValue(i);
            globals.frame1.jLabel1.setText("Current File: " + (i + 1) + " of " +
                                           files.size());
            globals.frame1.jLabel2.setText(((FileItem) files.elementAt(i)).
                                           filename);

            /*in = new File((String) frame.files.elementAt(i));
                         if (in.length() == 0L)
                         {

                frame.files.removeElementAt(i--);
                frame.jProgressBar1.setMaximum(frame.files.size() - 1);
                continue;
                         }*/

            if (sha)
            {
                try
                {
                    din = null;
                    din = new DigestInputStream(new FileInputStream(((FileItem)
                            files.elementAt(i)).filename),
                                                MessageDigest.getInstance(
                            "SHA-1"));
                    din.on(true);
                    din.getMessageDigest().reset();
                    while (din.read(buf, 0, buf.length) != -1)
                    {
                        ;
                    }
                    sha_digest = din.getMessageDigest().digest();
                    din.close();
                } catch (NoSuchAlgorithmException ex)
                {
                } catch (FileNotFoundException ex)
                {
                    files.removeElementAt(i);
                    i--;
                    continue;
                } catch (java.io.IOException ex)
                {
                    files.removeElementAt(i);
                    i--;
                    continue;

                }
            }
            if (md5)
            {
                try
                {
                    din = null;
                    din = new DigestInputStream(new FileInputStream(((FileItem)
                            files.elementAt(i)).filename),
                                                MessageDigest.getInstance("MD5"));
                    din.on(true);
                    din.getMessageDigest().reset();
                    while (din.read(buf, 0, buf.length) != -1)
                    {
                        ;
                    }
                    md5_digest = din.getMessageDigest().digest();
                    din.close();
                } catch (NoSuchAlgorithmException ex)
                {
                } catch (FileNotFoundException ex)
                {
                    files.removeElementAt(i);
                    i--;
                    continue;

                } catch (java.io.IOException ex)
                {
                    files.removeElementAt(i);
                    i--;
                    continue;

                }
            }
            ((FileItem) files.elementAt(i)).digest = append(sha_digest,
                    md5_digest);
        }

        try
        {
            out = new FileOutputStream("hashes.txt");
            for (i = 0; i < files.size(); i++)
            {
                digest = ((FileItem) files.elementAt(i)).digest;
                out.write((((FileItem) files.elementAt(i)).filename +
                           "\t").getBytes());
                out.write(BytetoHexString(digest).getBytes());
                out.write("\r\n".getBytes());
            }
            out.close();
        } catch (IOException ex)
        {
        }
        savereport();
        delete_duplicated_files(files);


        frame.jLabel1.setText(
                "Searching for duplicate files is done. Hashes are stored in hashes.txt.");
        frame.jLabel2.setText("Duplicate files names are stored in same.htm.");
        frame.jLabel3.setEnabled(true);
        globals.frame1.DelFilesCheckBox.setEnabled(true);
        globals.frame1.MD5CheckBox.setEnabled(true);
        globals.frame1.SHACheckBox.setEnabled(true);
        globals.frame1.SubDirCheckBox.setEnabled(true);
        globals.frame1.jMenuFile.setEnabled(true);
        globals.frame1.OpenDirButton.setEnabled(true);
        globals.frame1.PauseButton.setEnabled(false);
        globals.frame1.StopButton.setEnabled(false);
        globals.frame1.StartButton.setEnabled(true);
    }


    private final byte[] append(byte[] arg1, byte[] arg2)
    {
        byte[] out;
        int i;
        int j;
        if (arg1 == null)
        {
            return arg2;
        }
        else if (arg2 == null)
        {
            return arg1;
        }
        else
        {
            out = new byte[arg1.length + arg2.length];
            for (i = 0, j = 0; i < arg1.length; i++, j++)
            {
                out[j] = arg1[i];
            }
            for (i = 0; i < arg2.length; i++, j++)
            {
                out[j] = arg2[i];
            }
            return out;
        }
    }


    private final boolean digestcmp(byte[] arg1, byte[] arg2)
    {
        int i;
        if (arg1 == null || arg2 == null || arg1.length != arg2.length)
        {
            return false;
        }
        else
        {
            for (i = 0; i < arg1.length && arg1[i] == arg2[i]; i++)
            {
                ;
            }
            if (i == arg1.length)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }


    private final void savereport()
    {
        /*this method saves same.htm*/

        int i;
        int j;
        int n;
        int k;
        long d;
        FileOutputStream out;
        byte[] digest;
        File p;
        todeletion = new Vector();

        d = 0;

        frame.jProgressBar1.setMaximum(files.size() - 1);
        frame.jProgressBar1.setMinimum(0);

        try
        {
            out = new FileOutputStream("same.htm");
            out.write("<html><body>".getBytes());
            k = 1;
            for (j = 0; j < files.size(); j++)
            {
                frame.jProgressBar1.setValue(j);
                frame.jLabel1.setText("Writting log file");
                frame.jLabel2.setText(((FileItem) files.elementAt(j)).
                                      filename);

                digest = ((FileItem) files.elementAt(j)).digest;
                n = 1;
                for (i = j + 1; i < files.size(); i++)
                {
                    if (digestcmp(digest,
                                  ((FileItem) files.elementAt(i)).digest))
                    {
                        if (n == 1)
                        {
                            out.write(("<br><br>" + k + "<a href=\"file:///" +
                                       ((FileItem) files.elementAt(j)).
                                       filename +
                                       "\">" +
                                       ((FileItem) files.elementAt(j)).
                                       filename +
                                       "</a> is the same as<br>").getBytes());
                            k++;
                        }

                        out.write(("<a href=\"file:///" +
                                   ((FileItem) files.elementAt(i)).
                                   filename + "\">" +
                                   ((FileItem) files.elementAt(i)).
                                   filename +
                                   "</a><br>").
                                  getBytes());
                        todeletion.add(files.elementAt(i));
                        p = new File(((FileItem) files.elementAt(i)).
                                     filename);
                        d += p.length();
                        n++;
                        files.removeElementAt(i);
                        frame.jProgressBar1.setMaximum(files.size() - 1);
                        i--;
                    }
                }

            }
            out.write(("<br>Total duplicate files length is: " + d + " bytes").
                      getBytes());
            out.write("</body></html>".getBytes());
            out.close();
        } catch (IOException ex)
        {
        }
        frame.jLabel1.setText("");
        frame.jLabel2.setText("");
    }

    private final boolean delete_duplicated_files(Vector files)
    {

        int j;
        int n; /*zmienna okreœlaj¹ca numer kolejnego tego samego pliku*/
        boolean delete;
        File p;
        FileOutputStream out=null;

        delete = frame.DelFilesCheckBox.isSelected();

        if (!delete)
        {
            return false; /*wróc gdy nie zaznaczono usuwania plików*/
        }

        try
        {
            out = new FileOutputStream("deleted.htm");
            out.write("<html><body>".getBytes());
        } catch (FileNotFoundException ex)
        {
        } catch (IOException ex)
        {
        }

        frame.jProgressBar1.setMinimum(0);
        frame.jProgressBar1.setMaximum(todeletion.size() - 1);

        for (j = 0; j < todeletion.size(); j++)
        {

            frame.jProgressBar1.setValue(j);
            frame.jLabel1.setText("Deleting duplicated files");
            frame.jLabel2.setText(((FileItem) todeletion.elementAt(j)).
                                  filename);

            try
            {
                out.write(("<a href=\"file:///" +
                           ((FileItem) todeletion.elementAt(j)).
                           filename + "\">" +
                           ((FileItem) todeletion.elementAt(j)).
                           filename +
                           "</a>    removed<br>").
                          getBytes());

                p = new File(((FileItem) todeletion.elementAt(j)).
                             filename);
                p.delete();

            } catch (IOException ex1)
            {
            }
        }

        try
        {
            out.close();
        } catch (IOException ex2)
        {
        }

        return true;

    }

    private final String BytetoHexString(byte[] tab)
    {
        String out = "";
        int i;
        int p1;
        int p2;

        for (i = 0; i < tab.length; i++)
        {
            p1 = tab[i] & 0x0F;
            p2 = (tab[i] & 0xF0) >> 4;

            if (p2 == 0)
            {
                out += "0";
            }
            else if (p2 == 1)
            {
                out += "1";
            }
            else if (p2 == 2)
            {
                out += "2";
            }
            else if (p2 == 3)
            {
                out += "3";
            }
            else if (p2 == 4)
            {
                out += "4";
            }
            else if (p2 == 5)
            {
                out += "5";
            }
            else if (p2 == 6)
            {
                out += "6";
            }
            else if (p2 == 7)
            {
                out += "7";
            }
            else if (p2 == 8)
            {
                out += "8";
            }
            else if (p2 == 9)
            {
                out += "9";
            }
            else if (p2 == 10)
            {
                out += "A";
            }
            else if (p2 == 11)
            {
                out += "B";
            }
            else if (p2 == 12)
            {
                out += "C";
            }
            else if (p2 == 13)
            {
                out += "D";
            }
            else if (p2 == 14)
            {
                out += "E";
            }
            else if (p2 == 15)
            {
                out += "F";
            }

            if (p1 == 0)
            {
                out += "0";
            }
            else if (p1 == 1)
            {
                out += "1";
            }
            else if (p1 == 2)
            {
                out += "2";
            }
            else if (p1 == 3)
            {
                out += "3";
            }
            else if (p1 == 4)
            {
                out += "4";
            }
            else if (p1 == 5)
            {
                out += "5";
            }
            else if (p1 == 6)
            {
                out += "6";
            }
            else if (p1 == 7)
            {
                out += "7";
            }
            else if (p1 == 8)
            {
                out += "8";
            }
            else if (p1 == 9)
            {
                out += "9";
            }
            else if (p1 == 10)
            {
                out += "A";
            }
            else if (p1 == 11)
            {
                out += "B";
            }
            else if (p1 == 12)
            {
                out += "C";
            }
            else if (p1 == 13)
            {
                out += "D";
            }
            else if (p1 == 14)
            {
                out += "E";
            }
            else if (p1 == 15)
            {
                out += "F";
            }
        }
        return out;
    }

    private final void listfiles(File startfile)
    {
        int i;
        File[] lista;
        lista = startfile.listFiles();

        for (i = 0; i < lista.length; i++)
        {
            if (lista[i].isFile() && lista[i].length() != 0)
            {
                files.add(new FileItem(lista[i].getAbsolutePath()));
            }
            else if (lista[i].isDirectory() &&
                     globals.settings.getProperty("seachsubdirectories").
                     equalsIgnoreCase("true"))
            {
                listfiles(lista[i]);
            }
        }
    }

    private final void jbInit() throws Exception
    {
    }
}
