package com.codegym.model;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SongForm {
    private Long id;

    @NotEmpty
    @Max(value = 800, message = "can't enter more than 800 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]*$", message = "do not special characters")
    private String songName;

    @NotEmpty
    @Max(value = 800, message = "can't enter more than 800 characters")
    @Pattern(regexp = "^[a-zA-Z0-9._]*$", message = "do not special characters")
    private String singer;

    @Pattern(regexp = ".*.mp3", message = "Incorrect file format")
    private MultipartFile link;

    private Category category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public MultipartFile getLink() {
        return link;
    }

    public void setLink(MultipartFile link) {
        this.link = link;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
