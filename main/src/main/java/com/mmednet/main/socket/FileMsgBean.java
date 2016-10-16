package com.mmednet.main.socket;

/**
 * socket 发送过来的文件类型的 bean
 * 
 * @author xiaowei
 *
 */
public class FileMsgBean {
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 文件大小
     */
    
    private long fileSize;
    /**
     * 自定义语音类型
     */
    private int customType;

    /**
     * 问题
     */
    private String question;

    public int getCustomType() {
        return customType;
    }

    public void setCustomType(int customType) {
        this.customType = customType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }


    public FileMsgBean() {
        super();
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public FileMsgBean(String fileName, long fileSize, int customType,
            String question) {
        super();
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.customType = customType;
        this.question = question;
    }

}
