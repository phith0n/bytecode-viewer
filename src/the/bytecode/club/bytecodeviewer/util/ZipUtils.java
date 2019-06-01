package the.bytecode.club.bytecodeviewer.util;

import java.io.*;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/***************************************************************************
 * Bytecode Viewer (BCV) - Java & Android Reverse Engineering Suite        *
 * Copyright (C) 2014 Kalen 'Konloch' Kinloch - http://bytecodeviewer.com  *
 *                                                                         *
 * This program is free software: you can redistribute it and/or modify    *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation, either version 3 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>. *
 ***************************************************************************/

/**
 * Rudimentary utility class for Zip archives.
 */
public final class ZipUtils {

    /**
     * Unzip files to path.
     *
     * @param jarPath     the zip file name
     * @param destinationDir the file extract path
     * @throws IOException Signals that an I/O exception has occurred.
     */
    public static void unzipFilesToPath(String jarPath, String destinationDir) throws IOException {
        File file = new File(jarPath);
        JarFile jar = new JarFile(file);

        // fist get all directories,
        // then make those directory on the destination Path
        /*for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            if (fileName.endsWith("/")) {
                f.mkdirs();
            }

        }*/

        //now create all files
        for (Enumeration<JarEntry> enums = jar.entries(); enums.hasMoreElements(); ) {
            JarEntry entry = (JarEntry) enums.nextElement();

            String fileName = destinationDir + File.separator + entry.getName();
            File f = new File(fileName);

            File parent = f.getParentFile();
            if(!parent.exists())
            {
                parent.mkdirs();
            }

            if (!fileName.endsWith("/")) {
                InputStream is = jar.getInputStream(entry);
                FileOutputStream fos = new FileOutputStream(f);

                // write contents of 'is' to 'fos'
                while (is.available() > 0) {
                    fos.write(is.read());
                }

                fos.close();
                is.close();
            }
        }

        try {
            jar.close();
        } catch (Exception e) {

        }
    }

    public static void zipFile(File inputFile, File outputZip) {
        byte[] buffer = new byte[1024];

        try {
            FileOutputStream fos = new FileOutputStream(outputZip);
            ZipOutputStream zos = new ZipOutputStream(fos);
            ZipEntry ze = new ZipEntry(inputFile.getName());
            zos.putNextEntry(ze);
            FileInputStream in = new FileInputStream(inputFile);

            int len;
            while ((len = in.read(buffer)) > 0) {
                zos.write(buffer, 0, len);
            }

            in.close();
            zos.closeEntry();

            zos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void zipFolder(String srcFolder, String destZipFile, String ignore) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZip("", srcFolder, zip, ignore);
        zip.flush();
        zip.close();
    }

    public static void zipFolderAPKTool(String srcFolder, String destZipFile) throws Exception {
        ZipOutputStream zip = null;
        FileOutputStream fileWriter = null;

        fileWriter = new FileOutputStream(destZipFile);
        zip = new ZipOutputStream(fileWriter);

        addFolderToZipAPKTool("", srcFolder, zip);
        zip.flush();
        zip.close();
    }

    public static void addFileToZip(String path, String srcFile, ZipOutputStream zip, String ignore)
            throws Exception {

        File folder = new File(srcFile);
        if (folder.isDirectory()) {
            addFolderToZip(path, srcFile, zip, ignore);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            ZipEntry entry = null;
            if (ignore == null)
                entry = new ZipEntry(path + "/" + folder.getName());
            else
                entry = new ZipEntry(path.replace(ignore, "BCV_Krakatau") + "/" + folder.getName());
            zip.putNextEntry(entry);
            while ((len = in.read(buf)) > 0) {
                zip.write(buf, 0, len);
            }
            in.close();
        }
    }

    public static void addFileToZipAPKTool(String path, String srcFile, ZipOutputStream zip) throws Exception
    {
        File folder = new File(srcFile);

        String check = path.toLowerCase();
        //if(check.startsWith("decoded resources/unknown") || check.startsWith("decoded resources/lib") || check.startsWith("decoded resources/assets") || check.startsWith("decoded resources/original") || check.startsWith("decoded resources/smali") || check.startsWith("decoded resources/apktool.yml"))
        if(check.startsWith("decoded resources/original") || check.startsWith("decoded resources/smali") || check.startsWith("decoded resources/apktool.yml"))
            return;

        //if(path.equals("original") || path.equals("classes.dex") || path.equals("apktool.yml"))
        //    continue;

        if (folder.isDirectory())
        {
            addFolderToZipAPKTool(path, srcFile, zip);
        } else {
            byte[] buf = new byte[1024];
            int len;
            FileInputStream in = new FileInputStream(srcFile);
            ZipEntry entry = null;

            entry = new ZipEntry(path + "/" + folder.getName());
            zip.putNextEntry(entry);

            while ((len = in.read(buf)) > 0)
            {
                zip.write(buf, 0, len);
            }
            in.close();
        }
    }

    public static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip, String ignore)
            throws Exception {
        File folder = new File(srcFolder);

        for (String fileName : folder.list()) {
            if (path.equals("")) {
                addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, ignore);
            } else {
                addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, ignore);
            }
        }
    }

    public static void addFolderToZipAPKTool(String path, String srcFolder, ZipOutputStream zip) throws Exception
    {
        File folder = new File(srcFolder);

        for (String fileName : folder.list())
        {
            if (path.equals(""))
            {
                addFileToZipAPKTool(folder.getName(), srcFolder + "/" + fileName, zip);
            } else {
                addFileToZipAPKTool(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
            }
        }
    }
}