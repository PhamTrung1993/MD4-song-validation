package com.codegym.controller;

import com.codegym.model.Category;
import com.codegym.model.Song;
import com.codegym.model.SongForm;
import com.codegym.service.category.ICategoryService;
import com.codegym.service.song.ISongService;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class SongController {
    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private ISongService songService;
    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }
    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView("/list");
        List<Song> songList = songService.findAll();
        modelAndView.addObject("songs", songList);
        return modelAndView;
    }
    @GetMapping("/create")
    public ModelAndView createForm() {
        return new ModelAndView("/create", "songForm", new SongForm());
    }
    @PostMapping("/save")
    public ModelAndView create(@Validated @ModelAttribute SongForm songForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/create");
        }
        MultipartFile multipartFile = songForm.getLink();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
        Song song = new Song();
        song.setId(songForm.getId());
        song.setLink(fileName);
        song.setCategory(songForm.getCategory());
        song.setSinger(songForm.getSinger());
        song.setSongName(songForm.getSongName());
        songService.save(song);
        return new ModelAndView("/create", "message", "Created!!!");
    }

    @GetMapping("/{id}/edit")
    public ModelAndView formUpdate(@PathVariable("id") Long id) throws IOException {
        Song song = songService.findById(id);
        File file = new File(song.getLink());
        DiskFileItem fileItem = new DiskFileItem("file", "text/plain", false, file.getName(), (int) file.length() , file.getParentFile());
        fileItem.getOutputStream();
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        SongForm songForm = new SongForm();
        ModelAndView modelAndView = new ModelAndView("/edit");
        modelAndView.addObject("songForm", songForm);
        return modelAndView;
    }
    @PostMapping("/saveEdit")
    public ModelAndView saveEdit(@Validated @ModelAttribute SongForm songForm, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasFieldErrors()) {
            return new ModelAndView("/edit");
        }
        MultipartFile multipartFile = songForm.getLink();
        String fileName = multipartFile.getOriginalFilename();
        FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
        Song song = new Song();
        song.setId(songForm.getId());
        song.setLink(fileName);
        song.setCategory(songForm.getCategory());
        song.setSinger(songForm.getSinger());
        song.setSongName(songForm.getSongName());
        songService.save(song);
        return new ModelAndView("/edit", "message", "update!!!");
    }
}
