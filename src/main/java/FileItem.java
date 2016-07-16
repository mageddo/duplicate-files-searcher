package searchforduplicatefiles;

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
public final class FileItem
{
    String filename;
    byte[] digest;

    public FileItem()
    {
        super();
    }

    public FileItem(String arg1)
    {
        super();
        filename=arg1;
    }

}
