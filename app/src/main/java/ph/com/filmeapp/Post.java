package ph.com.filmeapp;

public class Post {

  //  image, title, desc, rating, name , genre

    private int imageId;
    private String title;
    private String desc;
    private String rating;
    private String name;
    private String genre;


    public Post(int imageId, String title, String desc, String rating, String name, String genre)
    {
           this.imageId = imageId;
           this.title = title;
           this.desc = desc;
           this.rating = rating;
           this.name = name;
           this.genre = genre;
    }


    public int getImageId(){ return this.imageId;}
    public String getTitle(){
        return this.title;
    }
    public String getDesc(){
        return this.desc;
    }
    public String getRating(){
        return this.rating;
    }
    public String getName(){return this.name;}
    public String  getGenre(){ return this.genre;}


}
