package ph.com.filmeapp;

public class Comment {

    // username , text, image


    private String comment, postId, avatarId, name;

    public Comment(){

    }



    public Comment (String comment, String postId, String avatarId, String name)
    {
      this.comment = comment;
      this.postId = postId;
      this.avatarId = avatarId;
      this.name = name;

    }

    public String getAvatarId() {
        return avatarId;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setAvatarId(String avatarId) {
        this.avatarId = avatarId;
    }

    public void setName(String name) {
        this.name = name;
    }

}
