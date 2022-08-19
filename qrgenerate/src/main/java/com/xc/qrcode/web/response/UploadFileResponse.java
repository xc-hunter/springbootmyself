package com.xc.qrcode.web.response;

public class UploadFileResponse {

    private String fielname;

    private String fileDownloadUri;

    private String fileType;

    private long size;

    public UploadFileResponse(String fielname, String fileDownloadUri, String fileType, long size) {
        this.fielname = fielname;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFielname() {
        return fielname;
    }

    public void setFielname(String fielname) {
        this.fielname = fielname;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
