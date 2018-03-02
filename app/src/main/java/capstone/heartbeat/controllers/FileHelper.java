package capstone.heartbeat.controllers;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileHelper {
    final static String fileName = "access-log.txt";
    final static String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/capstone/heartbeat" ;
    final static String TAG = FileHelper.class.getName();

    public static  String ReadFile( Context context){
        String line = null;

        try {

            FileInputStream fileInputStream = new FileInputStream (new File(path + fileName));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile( String data,String timestamp){
        try {
           // new File(path).mkdir();
            File file = new File(path+ fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data +"    "+timestamp+ System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }
    public static boolean saveErrorToFile( String data,String timestamp,String error){
        String files = "unsuccessful.txt";
        try {
            // new File(path).mkdir();
            File file = new File(path+ files);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data +"    "+timestamp + "   " +error+ System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }

    public static boolean saveChangeToFile( String username,String name,String birth,String sleep,String time){
        String files = "account-change.txt";
        try {
            // new File(path).mkdir();
            File file = new File(path+ files);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((username +"    "+name + "   " +birth+ "   " +sleep+ System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;


    }

}