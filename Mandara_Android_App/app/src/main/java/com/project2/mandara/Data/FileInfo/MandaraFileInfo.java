package com.project2.mandara.Data.FileInfo;

import android.graphics.Bitmap;

import java.io.Serializable;

public class MandaraFileInfo implements Serializable {
    private String ArtNumber=null;
    private String MandaraName=null;
    private String MandaraType=null;
    private String ArtName=null;
    private String CommandType=null;


    public String getArtNumber() {
        return ArtNumber;
    }

    public void setArtNumber(String artNumber) {
        ArtNumber = artNumber;
    }




    public String getArtName() {
        return ArtName;
    }

    public void setArtName(String artName) {
        ArtName = artName;
    }


    public String getMandaraName() {
        return MandaraName;
    }

    public void setMandaraName(String mandaraName) {
        MandaraName = mandaraName;
    }

    public String getCommandType() {
        return CommandType;
    }

    public void setCommandType(String commandType) {
        CommandType = commandType;
    }

    public String getMandaraType() {
        return MandaraType;
    }

    public void setMandaraType(String mandaraType) {
        MandaraType = mandaraType;
    }



}
