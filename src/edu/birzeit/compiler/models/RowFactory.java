package edu.birzeit.compiler.models;

import java.util.List;

public class RowFactory {
    public static Row getInstance(int attributes, String name, List<String> list) {
        if (list.size() != 0) {
            Row row = new Row(name);
            if (attributes == 1) {
                row.setV1(list.get(0));
            } else if (attributes == 2) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
            } else if (attributes == 3) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
            } else if (attributes == 4) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
            } else if (attributes == 5) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
            } else if (attributes == 6) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
            } else if (attributes == 7) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
            } else if (attributes == 8) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
            } else if (attributes == 9) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
            } else if (attributes == 10) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
            } else if (attributes == 11) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
            } else if (attributes == 12) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
            } else if (attributes == 13) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
            } else if (attributes == 14) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
            } else if (attributes == 15) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
            } else if (attributes == 16) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
                row.setV16(list.get(15));
            } else if (attributes == 17) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
                row.setV16(list.get(15));
                row.setV17(list.get(16));
            } else if (attributes == 18) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
                row.setV16(list.get(15));
                row.setV17(list.get(16));
                row.setV18(list.get(17));
            } else if (attributes == 19) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
                row.setV16(list.get(15));
                row.setV17(list.get(16));
                row.setV18(list.get(17));
                row.setV19(list.get(18));
            } else if (attributes == 20) {
                row.setV1(list.get(0));
                row.setV2(list.get(1));
                row.setV3(list.get(2));
                row.setV4(list.get(3));
                row.setV5(list.get(4));
                row.setV6(list.get(5));
                row.setV7(list.get(6));
                row.setV8(list.get(7));
                row.setV9(list.get(8));
                row.setV10(list.get(9));
                row.setV11(list.get(10));
                row.setV12(list.get(11));
                row.setV13(list.get(12));
                row.setV14(list.get(13));
                row.setV15(list.get(14));
                row.setV16(list.get(15));
                row.setV17(list.get(16));
                row.setV18(list.get(17));
                row.setV19(list.get(18));
                row.setV20(list.get(19));
            } else {
                return null;
            }
            return row;
        } else {
            return new Row(name);
        }
    }
}
