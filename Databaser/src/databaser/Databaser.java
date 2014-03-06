
package databaser;

import database.tweetHandler;

/**
 *
 * @author Nancy
 */
public class Databaser {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        tweetHandler th = new tweetHandler();
        th.rewriteDates();
    }
}
