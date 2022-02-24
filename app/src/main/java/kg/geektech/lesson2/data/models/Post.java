package kg.geektech.lesson2.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Post implements Serializable {


    @SerializedName("id")
    Integer id;
    @SerializedName("title")
    String title;
    @SerializedName("content")
    String content;
    @SerializedName("group")
    Integer groupId;
    @SerializedName("user")
    Integer userId;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public Integer getUserId() {
        return userId;
    }
}

