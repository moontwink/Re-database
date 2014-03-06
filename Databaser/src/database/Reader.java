/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package database;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Nancy
 */
public class Reader {
    private Scanner scan;
    private String path;
    
    public Reader(String file_path){
        path = file_path;
    }
    
    public void OpenFile(){
        try {
            scan = new Scanner(new File(path));
//            System.out.println("It is working.");
        } catch (FileNotFoundException ex) {
            System.out.println("Not working.");
        }
    }
    
    public String ReadFile(){
        String tweetLine;
        
        scan.reset();
        tweetLine = scan.nextLine();
        
        return tweetLine;
    }
}
