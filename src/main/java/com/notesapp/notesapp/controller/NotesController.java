package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dao.NoresDao;
import com.notesapp.notesapp.model.Notes;
import com.notesapp.notesapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NotesController {
    @Autowired
    public NoresDao dao;

    @GetMapping("/")
    public String home() throws ClassNotFoundException {
        return "index";
    }
    @GetMapping("/register")
    public String registerUser(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "registerUser";
    }
    @PostMapping("/save")
    public String saveUser(@ModelAttribute User user,Model model){
        int count = dao.getUserName(user.getUsername());
        if(count == 1){
            model.addAttribute("badUserName","User Name already existed");
            return "registerUser";
        }else {
            int output = (int) dao.insertRecord(user);
        }
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginUser(Model model){
        User user = new User();
        model.addAttribute("user",user);
        return "loginUser";
    }

    @PostMapping("/login")
    public String loginToHome(@ModelAttribute User user, Model model, HttpSession session){
        int count = dao.validUser(user.getUsername(),user.getPassword());
        if(count == 1){
            session.setAttribute("userName",user.getUsername());
            List<Notes> notesList = null;
            notesList = (List<Notes>) dao.getAllNotes(user.getUsername());
            model.addAttribute("notesList",notesList);
            return "homePage";
        }
        else{
            model.addAttribute("wrongCredentials","UserName or Password is wrong !!");
            return "loginUser";
        }
    }
    @GetMapping("/logout")
    public ModelAndView userLogout(HttpServletRequest request){
        HttpSession session= request.getSession(false);
        if(session != null)
        {
            session.invalidate();
        }
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }
    @GetMapping("/home/{user}")
    public String navigate(@PathVariable String user,Model model){
        List<Notes> notesList = null;
        notesList = (List<Notes>) dao.getAllNotes(user);
        model.addAttribute("notesList",notesList);
        return "homePage";
    }
    @GetMapping("new/{title}/{userName}/{noteId}")
    public String editNote(@PathVariable String noteId,@PathVariable String userName,@PathVariable String title, Model model){
        System.out.println(noteId+" "+userName);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        Notes notes = new Notes();
        notes.setId(noteId);
        notes.setUser(userName);
        notes.setTitle(title);
        notes.setCreatedAt(formatter.format(date));
        notes.setUpdatedAt(formatter.format(date));
        notes.setBody("Enter your Notes");
        dao.insertNotes(notes);
        List<Notes> notesList = null;
        notesList = (List<Notes>) dao.getAllNotes(userName);
        model.addAttribute("notesList",notesList);
        return "homePage";
    }
    @GetMapping("/delete/{userName}/{noteId}")
    public String delete(@PathVariable String userName, @PathVariable String noteId, Model model){
        dao.deleteNote(noteId,userName);
        List<Notes> notesList = null;
        notesList = (List<Notes>) dao.getAllNotes(userName);
        model.addAttribute("notesList",notesList);
        return "redirect:/home/"+userName;
    }
    @GetMapping("/edit/{userName}/{noteId}")
    public String editNote(@PathVariable String userName, @PathVariable String noteId, Model model){
        String[] note = dao.getNote(noteId,userName).split("#");
        List<Notes> notesList = null;
        notesList = (List<Notes>) dao.getAllNotes(userName);
        model.addAttribute("notesList",notesList);
        model.addAttribute("notesTitle",note[0]);
        model.addAttribute("notes",note[1]);
        model.addAttribute("noteId",noteId);
        return "homePage";
    }
    @PostMapping("/saveNote")
    public String saveNote(@RequestBody String note){
        System.out.println("TeSTing ----------");
        System.out.println(note);
        String[] notes = note.split("#");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        Notes n = new Notes();
        n.setUser(notes[3]);
        n.setBody(notes[2].trim());
        n.setTitle(notes[1]);
        n.setId(notes[0]);
        n.setUpdatedAt(formatter.format(date));
        dao.updateNote(n);
        return "homePage";
    }
    @GetMapping("/share/{userName}/{noteId}")
    public String shareNote(@PathVariable String userName, @PathVariable String noteId, Model model){
        String[] note = dao.getNote(noteId,userName).split("#");
        if(note.length == 4) {
            model.addAttribute("notesTitle", note[0]);
            model.addAttribute("notes", note[1]);
            model.addAttribute("createdAt",note[2]);
            model.addAttribute("updatedat",note[3]);
        }
        else{
            model.addAttribute("No","Oops !! The Notes you are looking for is deleted by "+userName);
        }
        return "edit";
    }
}
