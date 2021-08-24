package ph.com.filmeapp;

public class Comment {

    // username , text, image
    private int avatarId;
    private String name;
    private String desc;

    public Comment (int avatarId, String name, String desc)
    {
        this.avatarId = avatarId;
        this.name = name;
        this.desc = desc;

    }

    public int getAvatarId(){ return this.avatarId;}
    public String getName(){ return this.name;}
    public String getDesc(){ return this.desc;}


}
