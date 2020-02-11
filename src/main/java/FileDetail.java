public class FileDetail {
    private String path;
    private long size;
    private String content;
    public boolean repeatFlag = false;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
