package com.notesapp.notesapp.controller;

import com.notesapp.notesapp.dao.NoresDao;
import com.notesapp.notesapp.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class NotesController {

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
        NoresDao noresDao = new NoresDao();
        int count = noresDao.getUserName(user.getUsername());
        if(count == 1){
            model.addAttribute("badUserName","User Name already existed");
            return "registerUser";
        }else {
            int output = (int) noresDao.insertRecord(user);
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
        NoresDao dao = new NoresDao();
        int count = dao.validUser(user.getUsername(),user.getPassword());
        if(count == 1){
            session.setAttribute("userName",user.getUsername());
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

}
