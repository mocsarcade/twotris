package com.github.capstone.Util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * SOURCE: https://stackoverflow.com/a/3348150
 */
public class FileUtils
{
/**
@copyFile
This method returns either a copied stream or a false Boolean value if the copy fails. 
@param toCopy the file to be copied. 
@param destFile where the file is to go. 
@return FileUtils.copyStream() 
@throws none
*/ 
    public static boolean copyFile(final File toCopy, final File destFile)
    {
        try
        {
            return FileUtils.copyStream(new FileInputStream(toCopy), new FileOutputStream(destFile));
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
/**
@copyFilesRecursively 
This method returns either a copied stream or a false Boolean value if the copy fails. 
@param toCopy the file to be copied. 
@param destFile where the file is to go. 
@return FileUtils.copyStream() 
@throws none
*/ 
    private static boolean copyFilesRecusively(final File toCopy, final File destDir)
    {
        assert destDir.isDirectory();

        if (!toCopy.isDirectory())
        {
            return FileUtils.copyFile(toCopy, new File(destDir, toCopy.getName()));
        }
        else
        {
            final File newDestDir = new File(destDir, toCopy.getName());
            if (!newDestDir.exists() && !newDestDir.mkdir())
            {
                return false;
            }
            for (final File child : toCopy.listFiles())
            {
                if (!FileUtils.copyFilesRecusively(child, newDestDir))
                {
                    return false;
                }
            }
        }
        return true;
    }
/**
@copyJarResourcesRecursively
This method returns either a copied stream or a false Boolean value if the copy fails. 
@param destdir where the file is to be copied. 
@param JarURLConnection 
@return Boolean true if the action completes correctly, false if not. 
@throws none
*/ 
    public static boolean copyJarResourcesRecursively(final File destDir, final JarURLConnection jarConnection) throws IOException
    {

        final JarFile jarFile = jarConnection.getJarFile();

        for (final Enumeration<JarEntry> e = jarFile.entries(); e.hasMoreElements(); )
        {
            final JarEntry entry = e.nextElement();
            if (entry.getName().startsWith(jarConnection.getEntryName()))
            {
                final String filename = removeStart(entry.getName(), //
                        jarConnection.getEntryName());

                final File f = new File(destDir, filename);
                if (!entry.isDirectory())
                {
                    final InputStream entryInputStream = jarFile.getInputStream(entry);
                    if (!FileUtils.copyStream(entryInputStream, f))
                    {
                        return false;
                    }
                    entryInputStream.close();
                }
                else
                {
                    if (!FileUtils.ensureDirectoryExists(f))
                    {
                        throw new IOException("Could not create directory: " + f.getAbsolutePath());
                    }
                }
            }
        }
        return true;
    }
/**
@copyResourcesRecursively
This method returns either a copied resource or a false Boolean value if the copy fails. 
@param originURL the original URL 
@param destination the file the result is to go. 
@return  FileUtils.copyJarResourcesRecursively() 
@throws none
*/ 
    public static boolean copyResourcesRecursively(final URL originUrl, final File destination)
    {
        try
        {
            final URLConnection urlConnection = originUrl.openConnection();
            if (urlConnection instanceof JarURLConnection)
            {
                return FileUtils.copyJarResourcesRecursively(destination,
                        (JarURLConnection) urlConnection);
            }
            else
            {
                return FileUtils.copyFilesRecusively(new File(originUrl.getPath()),
                        destination);
            }
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
/**
@copyStream
This method returns either a copied stream or a false Boolean value if the copy fails. 
@param is input stream to be copied
@param f file where the stream is to go. 
@return FileUtils.copyStream() 
@throws none
*/ 
    public static boolean copyStream(final InputStream is, final File f)
    {
        try
        {
            return FileUtils.copyStream(is, new FileOutputStream(f));
        }
        catch (final FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return false;
    }
/**
@copyStream
This method returns either a copied stream into a new stream or a false Boolean value if the copy fails. 
@param is input stream to be copied
@param os Output Stream 
@return FileUtils.copyStream() 
@throws none
*/ 
    private static boolean copyStream(final InputStream is, final OutputStream os)
    {
        try
        {
            final byte[] buf = new byte[1024];

            int len;
            while ((len = is.read(buf)) > 0)
            {
                os.write(buf, 0, len);
            }
            is.close();
            os.close();
            return true;
        }
        catch (final IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }
/**
@ensueDirectoryExists
This method returns a positive Boolean if the directory exists, or makes the directory if it doesn’t.. 
@param f A file to be tested.  
@return f.exists() or makes the directory.  
@throws none
*/
    private static boolean ensureDirectoryExists(final File f)
    {
        return f.exists() || f.mkdir();
    }
/**
@removeStart
This method removes a string from another string. 
@param str A string to be tested. 
@param remove The section of string to be removed. 
@return str Either the original string, or the updated string with ‘remove’ taken out.  
@throws none
*/ 
    public static String removeStart(String str, String remove)
    {
        if (isEmpty(str) || isEmpty(remove))
        {
            return str;
        }
        if (str.startsWith(remove))
        {
            return str.substring(remove.length());
        }
        return str;
    }
/**
@isEmpty
This method tests whether a character sequence is empty or not. 
@param cs character sequence to be tested. 
@return null or zero. True if empty, false if not.  
@throws none
*/ 
    public static boolean isEmpty(CharSequence cs)
    {
        return cs == null || cs.length() == 0;
    }
}