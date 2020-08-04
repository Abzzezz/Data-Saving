package ga.abzzezz;

import ga.abzzezz.util.SaveObject;

import java.io.Serializable;
import java.util.prefs.Preferences;

public class Main implements Serializable {


    public static void main(String[] args) {
        new Main().x();
    }

    public void x() {

        SaveObject saveObject = new SaveObject();
        saveObject.putByte("2", (byte) 1);
        saveObject.putBoolean("ayy", true).putString("aes", "bitte kein AES :CCCCC").putArray("key", new String[]{"test", "test1", "lol", "I really hate my life"});
        SaveObject saveObject2 = new SaveObject();
        saveObject2.putInt("Penis", 1);
        saveObject.put("nested", saveObject2);



        System.out.println(saveObject.toString());
        SaveObject saveObject1 = new SaveObject(saveObject.toString());

        System.out.println(saveObject1.getString("nested"));
    }
}
