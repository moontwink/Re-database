
package database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nancy
 */
public class tweetHandler {
    
    public String addTweet(tweetModel tm){
        String message = "* Saving Failed.";
        
        try{
            Connection c = DBFactory.getConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO `Tweets` "
                    + "(statusId, username, message, retweetcount, latitude, longhitude, date) VALUES (?,?,?,?,?,?,?)"); 
            
            ps.setString(1, tm.getStatusId());
            ps.setString(2, tm.getUsername());
            ps.setString(3, tm.getMessage());
            ps.setLong(4, tm.getRetweetCount());
            ps.setDouble(5, tm.getLatitude());
            ps.setDouble(6, tm.getLonghitude());
            ps.setString(7, tm.getDate());
            
            int i = ps.executeUpdate();
            
            if (i == 1) {
                message = "* Saving successful.";
            }
            
            ps.close();
            c.close();
            
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return message;
        
    }
    
    public String RewriteTweet(String tweet){
        String filePath = "writetweet.txt";
        String tweetLine = tweet;
        
        //Rewrites tweet to text file
        try{
            Writer write = new Writer(filePath, false);
            write.writeToFile(tweet);
//            System.out.print("__! Rewrite Successful! __");
        }catch(IOException ex){
            System.out.println("__! Sorry, No Can Do!");
        }
      
        //Reades tweet as pure text
        Reader read = new Reader(filePath);
        read.OpenFile();
        tweetLine = read.ReadFile();
        
        return tweetLine;
    }
    
    //Retrieves all Tweets
    public ArrayList<tweetModel> getAllTweets(){
        ArrayList<tweetModel> results = new ArrayList<tweetModel>();
        tweetModel t;
        
        try{
            Connection c = DBFactory.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM tweets " + "LIMIT 0,10");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                t = new tweetModel();
                t.setIdTweets(rs.getInt("idTweets"));
                t.setStatusId(rs.getString("statusId"));
                t.setUsername(rs.getString("username"));
                t.setMessage(rs.getString("message"));
                t.setRetweetCount(rs.getLong("retweetCount"));
                t.setLatitude(rs.getLong("latitude"));
                t.setLonghitude(rs.getLong("longhitude"));
                t.setDate(rs.getString("date"));
                
                results.add(t);
            }
            
            rs.close();
            ps.close();
            c.close();
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
    
    
    public void rewriteDates(){
        ArrayList<tweetModel> results = getAllTweets();
        tweetModel t;
        
        try{
            Connection c = DBFactory.getConnection();
            for(int i = 0; i < results.size(); i++){
            PreparedStatement ps = c.prepareStatement("SELECT date FROM tweets "
                    + "WHERE idtweets = " + results.get(i).getIdTweets());
//                    + "LIMIT 0,10");
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                String olddate = rs.getString("date");
                    System.out.println(olddate);
                String[] newdate = olddate.split(" ");
                
                for(int x = 0; x < 3; x++){
                    System.out.println("\t"+newdate[x]);
                }
                
                String day = newdate[0];    //temp
                newdate[0] = newdate[2];    //
                newdate[2] = newdate[1];
                newdate[1] = day;
            }
            
            rs.close();
            ps.close();
            
            }
            
            c.close();
            
        }catch(ClassNotFoundException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }catch(SQLException ex){
            Logger.getLogger(tweetHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
