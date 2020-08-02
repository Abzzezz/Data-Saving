package ga.abzzezz.util;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Simple data saving api to use in my projects.
 * Nothing compared to libraries such as JSON, GSON etc.
 * TODO: maybe format blocks instantly? then store full blocks based on the key. -> Block length etc. (actually really unnecessary)
 */

public class SaveObject {

    private final Hashtable<String, String> stringValueHashtable = new Hashtable<>();
    private final Hashtable<String, String> map = new Hashtable<>();

    public SaveObject(final String string) {
        this.load(string);
    }

    public SaveObject() {

    }

    public void putInt(final String key, final int value) {
        put(key, value);
    }

    public void putString(final String key, final String value) {
        put(key, value);
    }

    public void putDouble(final String key, final double value) {
        put(key, value);
    }

    public void putFloat(final String key, final float value) {
        put(key, value);
    }

    public void putLong(final String key, final long value) {
        put(key, value);
    }

    public void putByte(final String key, final byte value) {
        put(key, value);
    }

    public void putChar(final String key, final char value) {
        //Generally same as put int.
        putInt(key, value);
    }

    public void putShort(final String key, final short value) {
        put(key, value);
    }

    public void putBoolean(final String key, final boolean value) {
        put(key, value);
    }

    public void put(final String key, final Object value) {
        //Replace quotes
        stringValueHashtable.put(key, value.toString().replace("\"", "\\\"\\"));
    }

    public Object get(final String key) {
        return stringValueHashtable.get(key);
    }

    public int getInt(final String key) {
        return Integer.parseInt(getString(key));
    }

    public String getString(final String key) {
        return get(key).toString();
    }

    public char getChar(final String key) {
        return (char) get(key);
    }

    public short getShort(final String key) {
        return Short.parseShort(getString(key));
    }

    public byte getByte(final String key) {
        return Byte.parseByte(getString(key));
    }

    public boolean getBoolean(final String key) {
        return Boolean.parseBoolean(getString(key));
    }

    public float getFloat(final String key) {
        return Float.parseFloat(getString(key));
    }

    public double getDouble(final String key) {
        return Double.parseDouble(getString(key));
    }

    public long getLong(final String key) {
        return Long.parseLong(getString(key));
    }

    private String decode(final String string) {
        try {
            return URLDecoder.decode(string, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String toString() {
        return stringValueHashtable.entrySet().stream().map(this::format).collect(Collectors.joining());
    }

    private String format(final Map.Entry<String, String> entry) {
        final String s = decode(entry.getKey()).concat(SEPARATOR).concat(decode(entry.getValue())).concat(BLOCK_END);
        return BLOCK_START.concat(s.length() + "\"" + s);
    }

    private static final String BLOCK_START = "{";
    private static final String SEPARATOR = "\":\"";
    private static final String BLOCK_END = "\"}";

    private void load(final String string) {
        final StringBuilder blocks = new StringBuilder(string);
        while (blocks.length() > 0) {
            //Find next start
            final int startBlock = blocks.indexOf(BLOCK_START);
            //Find the start of the key, also to calculate the blocks length
            final int keyStart = blocks.indexOf("\"", startBlock);
            //Get the index of the colon, to separate key and value
            final int indexColon = blocks.indexOf(SEPARATOR, keyStart);
            //Calculate length
            final int blockLength = Integer.parseInt(blocks.substring(startBlock + 1, keyStart));
            //Get end, based on blocks length
            final int endBlock = blocks.indexOf(BLOCK_END, startBlock + blockLength);
            //Get key and value, also replace quotation marks
            final String key = decode(blocks.substring(keyStart + 1, indexColon)).replace("\\\"\\", "\"");
            final String value = decode(blocks.substring(indexColon + SEPARATOR.length(), endBlock)).replace("\\\"\\", "\"");
            //Put
            this.stringValueHashtable.put(key, value);
            this.map.put(key, value);
            //Delete from blocks list
            blocks.delete(startBlock, endBlock + BLOCK_END.length());
        }
    }

    public Hashtable<String, String> getLoaded() {
        return map;
    }

    public Hashtable<String, String> getAll() {
        return stringValueHashtable;
    }
}
