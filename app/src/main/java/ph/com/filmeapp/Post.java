package ph.com.filmeapp;

public class Post {

  //  image, title, desc, rating, name , genre



    private String description, genre, image, rating, title, uid, name;





    public Post() {
    }

    public Post(String description, String genre, String image, String rating, String title, String uid, String name) {
        this.description = description;
        this.genre = genre;
        this.image = image;
        this.rating = rating;
        this.title = title;
        this.uid = uid;
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return uid;
    }

    public void setName(String uid) {
        this.uid = uid;
    }
}
