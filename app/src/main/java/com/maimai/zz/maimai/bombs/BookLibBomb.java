package com.maimai.zz.maimai.bombs;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 92198 on 2017/10/2.
 */

public class BookLibBomb extends BmobObject{

    private String StudentID;
    private String ScanCode;
    private BmobFile cover;


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
    public BmobFile getCover() {
        return cover;
    }
    public void setCover(BmobFile cover) {
        this.cover = cover;
    }

}
