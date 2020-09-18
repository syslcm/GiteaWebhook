package org.james.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TestObject {

    private String str;
    private boolean bln;
    private int i;
    private List<String> list = new ArrayList<>();

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public boolean isBln() {
        return bln;
    }

    public void setBln(boolean bln) {
        this.bln = bln;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
