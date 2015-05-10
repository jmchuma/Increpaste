package com.xchuma.increpaste.persistence;

/**
 * An entry in the database
 */
public class Entry {
    /**
     * Name of the ID column for all tables.
     */
    private long _id;

    /**
     * Date of creation.
     */
    private long _date;

    /**
     * Position of the enty.
     */
    private int _pos;

    /**
     * Content of the entry
     */
    private String _text;

    public long getId() {
        return _id;
    }

    public void setId(long id) {
        _id = id;
    }

    public long getDate() {
        return _date;
    }

    public void setDate(long date) {
        _date = date;
    }

    public int getPos() {
        return _pos;
    }

    public void setPos(int pos) {
        _pos = pos;
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }
}
