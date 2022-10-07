package ktgkid.spring.mvc.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class BoardVO {

    /*private int bno;
    private String title;
    private String userid;
    private Date regdate;
    private int view;
    private String content;*/

    private String bno;
    private String title;
    private String userid;
    private String regdate;
    private String view;
    private String content;

    /* cmd + n */
    /*@Override
    public String toString() {
        String frm = "BoardVO{bno=%s, title=%s, contents=%s, userid=%s, regdate=%s, views=%s}";

        String result = String.format(frm, bno, title, userid, regdate, view, content);

        return result;

        *//*return "BoardVO{" +
                "bno='" + bno + '\'' +
                ", title='" + title + '\'' +
                ", userid='" + userid + '\'' +
                ", regdate='" + regdate + '\'' +
                ", view='" + view + '\'' +
                ", content='" + content + '\'' +
                '}';*//*
    }

    public String getBno() {
        return bno;
    }

    public void setBno(String bno) {
        this.bno = bno;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRegdate() {
        return regdate;
    }

    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }*/
}
