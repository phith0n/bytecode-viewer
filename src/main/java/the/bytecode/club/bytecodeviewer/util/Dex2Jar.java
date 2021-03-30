package the.bytecode.club.bytecodeviewer.util;

import me.konloch.kontainer.io.DiskReader;
import me.konloch.kontainer.io.DiskWriter;
import org.apache.commons.io.IOUtils;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import the.bytecode.club.bytecodeviewer.BytecodeViewer;

import java.io.*;
import java.nio.file.Files;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
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
 * A simple wrapper for Dex2Jar.
 *
 * @author Konloch
 */

public class Dex2Jar {

    /**
     * Converts a .apk or .dex to .jar
     *
     * @param input  the input .apk or .dex file
     * @param output the output .jar file
     */
    public static synchronized void dex2Jar(File input, File output) {
        try {
            com.googlecode.dex2jar.tools.Dex2jarCmd.main(new String[]{input.getAbsolutePath()});
            String realOutput = input.getName().replaceAll("\\.dex", "-dex2jar.jar").replaceAll("\\.apk", "-dex2jar.jar");
            File realOutputF = new File(realOutput);
            realOutputF.renameTo(output);
            File realOutputF2 = new File(realOutput);
            while (realOutputF2.exists())
                realOutputF2.delete();
        } catch (Exception e) {
            new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
        }
    }

    /**
     * Converts a .jar to .dex
     *
     * @param input  the input .jar file
     * @param output the output .dex file
     */
    public static synchronized void saveAsDex(File input, File output) {
        saveAsDex(input, output, true);
    }

    public static synchronized void saveAsDex(File input, File output, boolean delete) {
        try {
            com.googlecode.dex2jar.tools.Jar2Dex.main(new String[]{input.getAbsolutePath()});
            File currentDexLocation = new File("./"+input.getName());

            if(currentDexLocation.getAbsolutePath().toLowerCase().endsWith(".jar"))
            {
                currentDexLocation = new File(currentDexLocation.getAbsolutePath().replaceFirst("\\.jar", "-jar2dex.dex"));
            }
            else if(currentDexLocation.getAbsolutePath().toLowerCase().endsWith(".apk"))
            {
                currentDexLocation = new File(currentDexLocation.getAbsolutePath().replaceFirst("\\.apk", "-jar2dex.dex"));
            }
            else if(currentDexLocation.getAbsolutePath().toLowerCase().endsWith(".dex"))
            {
                currentDexLocation = new File(currentDexLocation.getAbsolutePath().replaceFirst("\\.dex", "-jar2dex.dex"));
            }
            else if(currentDexLocation.getAbsolutePath().toLowerCase().endsWith(".zip"))
            {
                currentDexLocation = new File(currentDexLocation.getAbsolutePath().replaceFirst("\\.zip", "-jar2dex.dex"));
            }
            else if(currentDexLocation.getAbsolutePath().toLowerCase().endsWith(".class"))
            {
                currentDexLocation = new File(currentDexLocation.getAbsolutePath().replaceFirst("\\.class", "-jar2dex.dex"));
            }

            currentDexLocation.renameTo(output);

            if(delete)
                input.delete();
        } catch (Exception e) {
            new the.bytecode.club.bytecodeviewer.api.ExceptionUI(e);
        }
    }
}
