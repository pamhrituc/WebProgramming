package jsp.domain;

public class URL {
    private int id;
    private int userid;
    private String link;
    private int accessed;

    public URL() {
    }

    public URL(int id, int userid, String link, int accessed) {
        this.id = id;
        this.userid = userid;
        this.link = link;
        this.accessed = accessed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getAccessed() {
        return accessed;
    }

    public void setAccessed(int accessed) {
        this.accessed = accessed;
    }
}
