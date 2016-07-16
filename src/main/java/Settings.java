package searchforduplicatefiles;

import java.io.*;
import java.util.Properties;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;


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
public final class Settings extends Properties
{
    /*this class stores all settings*/

    public Settings()
    {
        super();
        setProperty("MD5", "false");
        LoadSettings();

        try
        {
            jbInit();
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }


    private final void LoadSettings()
    {
        GZIPInputStream in = null;

        try
        {
            in = new GZIPInputStream(new FileInputStream("Settings.bin"));
            loadFromXML(in);
            if (getProperty("first").equalsIgnoreCase("true"))
            {
                setProperty("first", "false");
            }
            in.close();
        } catch (FileNotFoundException ex)
        {
            in = null;
        } catch (IOException ex)
        {
            in = null;
        }

        if (in == null)
        {
            setProperty("first", "true");
            setProperty("LastDirectory", "");
            setProperty("SHA", "true");
            setProperty("MD5", "true");
            setProperty("deleteduplicatedfiles", "false");
            setProperty("seachsubdirectories","true");
            setProperty("CurrentVersion", "1.7");
            SaveSettings();
        }
    }

    public final boolean SaveSettings()
    {
        GZIPOutputStream out = null;
        try
        {
            out = new GZIPOutputStream(new FileOutputStream("Settings.bin"));
            storeToXML(out, "Duplicate Files Seracher Settings", "UTF-8");
            out.close();
        } catch (IOException ex1)
        {
            return false;
        }
        return true;
    }

    private void jbInit() throws Exception
    {
    }
}
