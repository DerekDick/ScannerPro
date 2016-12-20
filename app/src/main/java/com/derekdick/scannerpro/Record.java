package com.derekdick.scannerpro;

public class Record {
    private String time;
    private String type;
    private String content;
    private int imageId;

    public Record(String time, String type, String content, int imageId) {
        this.time = time;
        this.type = type;
        this.content = content;
        this.imageId = imageId;
    }

    public String getTime() {
        return this.time;
    }

    public String getType() {
        return this.type;
    }

    public String getContent() {
        return this.content;
    }

    public int getImageId() {
        return this.imageId;
    }
}
