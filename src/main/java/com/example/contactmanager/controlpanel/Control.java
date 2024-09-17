package com.example.contactmanager.controlpanel;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.contactmanager.dao.UserRepository;
import com.example.contactmanager.entity.User;
import com.example.contactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Control {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userrepo;
	
    @RequestMapping("/")
	public String test()
	{
		return "mine";
	}
    @GetMapping("/about")
    public String about()
	{
		return "about";
	}
    @GetMapping("/Signup")
    public String signup(Model m)
	{
    	m.addAttribute("user", new User());
		return "signup";
	} 
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("user")User user,BindingResult result,@RequestParam(value="agreement",defaultValue="false")boolean agreement,Model model,HttpSession session)
    {
    	try {
    		if(!agreement)
        	{
                throw new Exception("you have not agreed terms and condition");
        	}
    		if(result.hasErrors())
    		{
    			model.addAttribute("user", user);
    			//System.out.println(result);
    			return "signup";
    		}
        	user.setRole("ROLE_USER");
        	user.setEnable(true);
        	user.setPass(passwordEncoder.encode(user.getPass()));
        	this.userrepo.save(user);
        	
        	model.addAttribute("user", new User());
        	
        	session.setAttribute("message", new Message("Sucessfully restored","alert-success"));    	
            return "signup"; 	
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		model.addAttribute("user",user);
    		session.setAttribute("message", new Message("something Went Wrong "+e.getMessage(),"alert-danger"));    	}
           return "signup"; 	
    }
    @GetMapping("/register")
    public String reg(Model m)
    {
    	m.addAttribute("user", new User());
    	return "signup";
    }
    
    @GetMapping("/user_dash")
    public String login(Model m)
    {
    	return "user_dash";
    }
    @GetMapping("/forgot")
    public String forgot()
    {
    	return "forgot";
    }
}
