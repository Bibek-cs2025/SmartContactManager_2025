package com.example.contactmanager.controlpanel;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.contactmanager.dao.ContactRepository;
import com.example.contactmanager.dao.UserRepository;
import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.entity.Otpsession;
import com.example.contactmanager.entity.User;
import com.example.contactmanager.helper.ForgotPass;
import com.example.contactmanager.helper.Mail1;

@RestController //it does not return a view it return a body
public class SearchController {
	
	 static User us;
	 static int date;
	 static String s;
    @Autowired
    private ContactRepository contactrepo;
    
    @Autowired
    private UserRepository userrepo;
    
    @Autowired
	private PasswordEncoder passwordEncoder;
    
	
	@GetMapping("/search/{query}")
	public List<Contact> search(@PathVariable("query") String query)//principal take sthe current user
	{
		//User user=this.userrepo.getUserByUserName(p.getName());
		System.out.println(us.getEmail());
		System.out.println(query);
		List<Contact> contacts=this.contactrepo.findBynameContainingAndUser(query, us);
		System.out.println(contacts.isEmpty());
		return contacts;
	}
	public void setPrincipal(User user)
	{
		this.us=user;
	}
	
	@GetMapping("/sendotp/{query}")
	public Otpsession sendotp(@PathVariable("query") String query)
	{
		System.out.println(query);
		User user=userrepo.getUserByUserName(query);
		if(user==null)
		{
			Otpsession otpsession =new Otpsession();
	         otpsession.setName(null);
            return otpsession;
		}
		Random r=new Random();
		int a=r.nextInt(10000,99999);
		s=String.valueOf(a);
		new Mail1(query,s);
		Date d=new Date();
         date=d.getMinutes();
         
         Otpsession otpsession =new Otpsession();
         otpsession.setEmail(user.getEmail());
         otpsession.setName(user.getName());
         otpsession.setOtp(s);
         otpsession.setTime(date);
         otpsession.setMaxtime(date+7);
		return otpsession;
	}
	
	@GetMapping("pas/{newpas}/{email}")
	public ForgotPass passwordset (@PathVariable("newpas") String query, @PathVariable("email") String email)
	{
		ForgotPass fp=new ForgotPass();
		try {
		User user=userrepo.getUserByUserName(email);
		user.setPass(passwordEncoder.encode(query));
		this.userrepo.save(user);
		System.out.println("passowrd chnaged");
		fp.setMsg("successfully changed password");
		fp.setResult(true);
		return fp;
		}
		catch(Exception e)
		{
			fp.setMsg("Can't change password");
			fp.setResult(false);
			return fp;
		}
		
		
    			
	}

}
