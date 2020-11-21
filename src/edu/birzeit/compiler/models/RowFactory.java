package edu.birzeit.compiler.models;

import java.util.List;

public class RowFactory {
    public static Row getInstance(int attributes, String name, List<String> list) {
        if(list.size()!=0) {
            if (attributes == 1) {
                return new RowOne(name, list.get(0));
            } else if (attributes == 2) {
                return new RowTwo(name, list.get(0), list.get(1));
            } else if (attributes == 3) {
                return new RowThree(name, list.get(0), list.get(1), list.get(2));
            } else if (attributes == 4) {
                return new RowFour(name, list.get(0), list.get(1), list.get(2), list.get(3));
            } else {
                return null;
            }
        }else{
            return new RowZero(name);
        }
    }
}
