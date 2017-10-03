package com.maimai.zz.maimai.litepals;

import org.litepal.crud.DataSupport;

/**
 * Created by 92198 on 2017/10/2.
 */

public class BookLibrary extends DataSupport{

    private String StudentID;
    private String ScanCode;
    private byte[] cover;


    public String getStudentID() {
        return StudentID;
    }
    public void setStudentID(String studentID) {
        StudentID = studentID;
    }
    public String getScanCode() {
        return ScanCode;
    }
    public void setScanCode(String scanCode) {
        ScanCode = scanCode;
    }
    public byte[] getCover() {
        return cover;
    }
    public void setCover(byte[] cover) {
        this.cover = cover;
    }

}
