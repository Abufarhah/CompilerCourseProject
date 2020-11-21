package edu.birzeit.compiler.models;

public class RowOne extends Row{
    private String v1;

    public RowOne() {
    }

    public RowOne(String name, String v1) {
        super(name);
        this.v1 = v1;
    }

    public String getV1() {
        return v1;
    }

    public void setV1(String v1) {
        this.v1 = v1;
    }
}
