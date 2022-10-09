package com.acer.utils;

import java.io.*;
import java.util.HashMap;


/**
 * 获取File、FileInputStream、FileOutStream、BufferedInputStream、BufferedOutStream对象的类
 * 相当于一个连接池，对象的复用
 * @author acer
 */
public class IoObjectUtils {

    /**
     * File
     */
    private final static HashMap<String,File> fileHashMap = new HashMap<>();



    /**
     * 输入流
     */
    private static final HashMap<String,BufferedInputStream> bisList = new HashMap<>();
    private static final HashMap<String,FileInputStream> fileInputStreamHashMap = new HashMap<>();


    /**
     * 输出流
     */
    private static final  HashMap<String,BufferedOutputStream> bosList = new HashMap<>();
    private static final HashMap<String, FileOutputStream> fileOutputStreamHashMap = new HashMap<>();






    /**
     * 获取File
     * @author acer
     * @date 10:43 2022/9/15
     * @param fileName 文件路径
     * @return java.io.File
    **/
    private static File file(String fileName){
        File file = fileHashMap.get(fileName);
        if (file != null){
            return file;
        }
        file = new File(fileName);
        fileHashMap.put(fileName,file);
        return file;
    }
    /**
     * File的get方法
     * @author acer
     * @date 10:44 2022/9/15
     * @param fileName 文件路径
     * @return java.io.File
    **/
    public static File getFile(String fileName){
        return file(fileName);
    }


    /**
     * 获取BufferedInputStream
     * @author acer
     * @date 16:09 2022/8/29
     * @param inputPath 输入路径
     * @return java.io.BufferedInputStream
    **/
    private static BufferedInputStream bufferedInputStream(String inputPath){
        BufferedInputStream bufferedInputStream = bisList.get(inputPath);
        if (bufferedInputStream != null){
            return bufferedInputStream;
        }
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(inputPath));
            bisList.put(inputPath,bufferedInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bufferedInputStream;
    }

    /**
     * 获取FileInputStream
     * @author acer
     * @date 10:37 2022/9/15
     * @param file 输入路径
     * @return java.io.FileInputStream
    **/
    private static FileInputStream fileInputStream(File file){
        FileInputStream fileInputStream = fileInputStreamHashMap.get(file.getName());
        if (fileInputStream != null){
            return fileInputStream;
        }

        try {
            fileInputStream = new FileInputStream(file.getName());
            fileInputStreamHashMap.put(file.getName(),fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }
    /**
     * 获取FileInputStream
     * @author acer
     * @date 10:37 2022/9/15
     * @param fileName 输入路径
     * @return java.io.FileInputStream
     **/
    private static FileInputStream fileInputStream(String fileName){
        FileInputStream fileInputStream = fileInputStreamHashMap.get(fileName);
        if (fileInputStream != null){
            return fileInputStream;
        }

        try {
            fileInputStream = new FileInputStream(fileName);
            fileInputStreamHashMap.put(fileName,fileInputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileInputStream;
    }



    /**
     * 获取BufferedOutputStream
     * @author acer
     * @date 16:12 2022/8/29
     * @param outPath 输出路径
     * @return java.io.BufferedOutputStream
    **/
    private static BufferedOutputStream bufferedOutputStream(String outPath){
        BufferedOutputStream bufferedOutputStream = bosList.get(outPath);
        if (bufferedOutputStream != null){
            return bufferedOutputStream;
        }

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outPath));
            bosList.put(outPath,bufferedOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return bufferedOutputStream;
    }
    /**
     * 获取FileOutputStream
     * @author acer
     * @date 10:40 2022/9/15
     * @param file 输出路径
     * @return java.io.FileOutputStream
    **/
    private static FileOutputStream fileOutputStream(File file){
        FileOutputStream fileOutputStream = fileOutputStreamHashMap.get(file.getName());
        if (fileOutputStream != null){
            return fileOutputStream;
        }

        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStreamHashMap.put(file.getName(),fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }

    /**
     * 获取FileOutputStream
     * @author acer
     * @date 10:40 2022/9/15
     * @param fileName 输出路径
     * @return java.io.FileOutputStream
     **/
    private static FileOutputStream fileOutputStream(String fileName){
        FileOutputStream fileOutputStream = fileOutputStreamHashMap.get(fileName);
        if (fileOutputStream != null){
            return fileOutputStream;
        }

        try {
            fileOutputStream = new FileOutputStream(fileName);
            fileOutputStreamHashMap.put(fileName,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return fileOutputStream;
    }

    /**
     * bufferedInputStream的get方法
     * @author acer
     * @date 16:15 2022/8/29
     * @param inputPath 输入流路径
     * @return java.io.BufferedInputStream
    **/
    public static BufferedInputStream getBufferedInputStream(String inputPath){
        return bufferedInputStream(inputPath);
    }
    /**
     * FileInputStream的git方法
     * @author acer
     * @date 10:39 2022/9/15
     * @param file 输入流路径
     * @return java.io.FileInputStream
    **/
    public static FileInputStream getFileInputStream(File file){
        return fileInputStream(file);
    }
    /**
     * FileInputStream的git方法
     * @author acer
     * @date 10:39 2022/9/15
     * @param fileName 输入流路径
     * @return java.io.FileInputStream
     **/
    public static FileInputStream getFileInputStream(String fileName){
        return fileInputStream(fileName);
    }



    /**
     * bufferedOutputStream的get方法
     * @author acer
     * @date 16:16 2022/8/29
     * @param outPath 输出路径
     * @return java.io.BufferedOutputStream
    **/
    public static BufferedOutputStream getBufferedOutputStream(String outPath){
        return bufferedOutputStream(outPath);
    }



    /**
     * FileOutputStream的get方法
     * @author acer
     * @date 10:41 2022/9/15
     * @param file 输出路径
     * @return java.io.FileOutputStream
    **/
    public static FileOutputStream getFileOutputStream(File file){
        return fileOutputStream(file);
    }

    /**
     * FileOutputStream的get方法
     * @author acer
     * @date 10:41 2022/9/15
     * @param fileName 输出路径
     * @return java.io.FileOutputStream
     **/
    public static FileOutputStream getFileOutputStream(String fileName){
        return fileOutputStream(fileName);
    }




    /**
     * 关闭FileInputStream流
     * @author acer
     * @date 10:57 2022/9/15
     * @param fileInputStream 要关闭的FileInputStream流
    **/
    public static void closeStream(FileInputStream fileInputStream) throws IOException {
        if (fileInputStream != null) {
            fileInputStream.close();
        }
    }
    /**
     * 关闭FileOutputStream流
     * @author acer
     * @date 10:57 2022/9/15
     * @param fileOutputStream 要关闭的FileOutputStream流
    **/
    public static void closeStream(FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            fileOutputStream.close();
        }
    }
    /**
     * 关闭BufferedInputStream流
     * @author acer
     * @date 10:57 2022/9/15
     * @param bufferedInputStream 要关闭的BufferedInputStream流
    **/
    public static void closeStream(BufferedInputStream bufferedInputStream) throws IOException {
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
        }
    }
    /**
     * 关闭BufferedOutputStream流
     * @author acer
     * @date 10:57 2022/9/15
     * @param bufferedOutputStream 要关闭的BufferedOutputStream流
    **/
    public static void closeStream(BufferedOutputStream bufferedOutputStream) throws IOException {
        if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
        }
    }

}
