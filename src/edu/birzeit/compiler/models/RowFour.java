package edu.birzeit.compiler.models;

public class RowFour extends Row{
    private String v1;
    private String v2;
    private String v3;
    private String v4;

    public RowFour() {
    }

    public RowFour(String name, String v1, String v2, String v3, String v4) {
        super(name);
        this.v1 = v1;
        this.v2 = v2;
        this.v3 = v3;
        this.v4 = v4;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }

    public String getV2() {
        return v2;
    }

    public void setV2(String v2) {
        this.v2 = v2;
    }

    public String getV3() {
        return v3;
    }

    public void setV3(String v3) {
        this.v3 = v3;
    }

    public String getV4() {
        return v4;
    }

    public void setV4(String v4) {
        this.v4 = v4;
    }
}
