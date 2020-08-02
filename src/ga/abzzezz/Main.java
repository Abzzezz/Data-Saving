package ga.abzzezz;

import ga.abzzezz.util.SaveObject;

public class Main {

    public static void main(String[] args) {
        SaveObject saveObject = new SaveObject();
        saveObject.putByte("Penos", (byte) 1);
        saveObject.putBoolean("ayy", true);
        saveObject.putString("aes", "{\"ayy\"w:\":\"true\"}");
        System.out.println(saveObject.toString());

        SaveObject saveObject1 = new SaveObject(saveObject.toString());

        System.out.println(saveObject1.getBoolean("ayy"));
        System.out.println(saveObject1.getString("aes"));
    }
}
