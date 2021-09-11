package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dao.NoresDao;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NotesController {

    @GetMapping("/")
    public String home() throws ClassNotFoundException {
        NoresDao dao = new NoresDao();
        System.out.println(dao.getCount());
        return "index";
    }
}
