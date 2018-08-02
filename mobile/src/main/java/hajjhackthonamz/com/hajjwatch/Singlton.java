package hajjhackthonamz.com.hajjwatch;

import java.util.ArrayList;

public class Singlton {

        private static Singlton instance = null;
        public ArrayList<String> name = new ArrayList<String>();

    public ArrayList<String> getName() {
        return name;
    }

    protected Singlton() {

        }
        public static Singlton getInstance() {
            if(instance == null) {
                instance = new Singlton();
            }
            return instance;
        }



    }

