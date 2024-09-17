package com.example.contactmanager.controlpanel;

import java.io.File;	
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.contactmanager.dao.ContactRepository;
import com.example.contactmanager.dao.UserRepository;
import com.example.contactmanager.entity.Contact;
import com.example.contactmanager.entity.User;
import com.example.contactmanager.helper.Message;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user")//to access next thing only via this user pattern
public class UserControl {
    static Principal p;
   @Autowired //it allows Spring to automatically wire the required beans (dependencies) into your classes, eliminating the need for manual configuration. 
   private UserRepository userrepo;
   @Autowired
   private ContactRepository contactrepo;

   @GetMapping("/")
   @PreAuthorize("hasAuthority('ROLE_USER')")
  public String dash(Model model,Principal principal)//Spring Security defines the notion of a principal, which is the currently logged in user. When a user authenticates successfully, the principal is stored in Spring's security context, which is thread-bound, thus making it available to the rest of our service.
  {
	  String username=principal.getName() ;
	  this.p=principal;
	  User user=this.userrepo.getUserByUserName(username);
	  //System.out.println(user);
	  model.addAttribute("user", user);
	  return "normal/header";
  }
   @GetMapping("/addContact/{id}")
   public String openContact(Model model,@PathVariable(value="id") String id)
   {
		  
		  User user=this.userrepo.getUserByUserName(id);

		  model.addAttribute("user", user);
		  model.addAttribute("contact", new Contact());
		  
	   return "normal/addContact";
   }
   @PostMapping("/contact_process/{id}")
   public String contactProcess(@RequestParam("img")MultipartFile img,@Valid @ModelAttribute("contact")Contact contact,BindingResult result,Model model,HttpSession session,@PathVariable(value="id") String id)//requestparam used to fetch data from the form
   {
		  
       User user=this.userrepo.getUserByUserName(id);
       
	   try {
		   System.out.println(img.getOriginalFilename());
		   if(!img.isEmpty())   
			{
			   //String name=file.getOriginalFilename();
			    contact.setImgurl(img.getOriginalFilename());
			    //to upload file in a folder destination folder
	            File fil=new ClassPathResource("static/image").getFile();
	            //to create path
	            Path path= Paths.get(fil.getAbsolutePath()+File.separator+img.getOriginalFilename());
			    Files.copy(img.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			}  
		   if(result.hasErrors() )
	   		{
				   model.addAttribute("user", user);
				  // model.addAttribute("contact", new Contact());
	   			//System.out.println(result);
				   System.out.println(result);
	   			return "normal/addContact";
	   		}

		   
		contact.setUser(user)  ; 
	    this.contactrepo.save(contact); 
		user.getCon().add(contact);  
		this.userrepo.save(user);

	   model.addAttribute("user", user);
	   model.addAttribute("contact", new Contact());
	   session.setAttribute("message", new Message("Sucessfully Added","alert-success"));   
	   return "normal/addContact";}
	   catch(Exception e)
	   {
		   model.addAttribute("user", user);
		   model.addAttribute("contact", new Contact());
		   session.setAttribute("message", new Message("something Went Wrong "+e.getMessage(),"alert-danger")); 
	   }
	   return "normal/addContact";   
	   
   }
   @GetMapping("/contact_process/{id}")
   public String contactp(Model model,@PathVariable(value="id") String id)
   {
	   User user=this.userrepo.getUserByUserName(id);

		  model.addAttribute("user", user);
		  model.addAttribute("contact", new Contact());
		  
	   return "normal/addContact";
   }
   @GetMapping("*")
   public String error()
   {
	   return "user_dash";
   }
   @GetMapping("/showContact/{page}/{id}")
   public String showcontact(Model model,@PathVariable(value="id") String id,@PathVariable(value="page") Integer  page)
   {
	  if(page==-1) {
		  page++;
	  } User user=this.userrepo.getUserByUserName(id);
	   SearchController sc=new SearchController();
	   sc.setPrincipal(user);
       model.addAttribute("user", user);
      // List<Contact> contact =user.getCon(); we can do that but we will not
       PageRequest pg=PageRequest.of(page, 5);

       Page<Contact> contact =this.contactrepo.findUserContact(user.getId(),pg);
       model.addAttribute("contact", contact);
       model.addAttribute("curpage", page);
       model.addAttribute("totalpage",contact.getTotalPages() );
       return "normal/showContact";

   }
   
   @GetMapping("/viewcon/{conid}")
   public String viewcon(Model model,@PathVariable(value="conid") Integer  conid)
   {
	  try {
       
	   Optional<Contact> contacts=this.contactrepo.findById(conid);
	   Contact contact=contacts.get();
	   User user=this.userrepo.getUserByUserName(contact.getUser().getEmail());
       //System.out.println(user.getEmail());
       model.addAttribute("user", user);
	   if(contact.getUser().getId()==user.getId())
	   {
		   model.addAttribute("contact", contact);
		   return "normal/viewContact";
		   
	   }
	   else {
		   return "user_dash";
	   }
	  }
	  catch(Exception e)
	  {
		  return "user_dash";
	  }
	  
	  
	   
   }
   @GetMapping("/delete/{id}/{conid}")
   public String delete(Model model,@PathVariable(value="id") String id,@PathVariable(value="conid") Integer  conid)
   {

	   User user=this.userrepo.getUserByUserName(id);

       model.addAttribute("user", user);
       
	   Contact contact=this.contactrepo.findById(conid).get();

	   
	   if(contact.getUser().getId()==user.getId())
	   {
		   contact.setUser(null);
		   user.getCon().remove(contact);
		   //contact is associated with parent so unlink it first
		   this.contactrepo.delete(contact);
	   }
	  
       return "redirect:/user/showContact/0/"+user.getEmail();
   }
   
   
   @GetMapping("/update/{id}/{conid}")
   public String update(Model model,@PathVariable(value="id") String id,@PathVariable(value="conid") Integer  conid)
   {
	   User user=this.userrepo.getUserByUserName(id);

       model.addAttribute("user", user);
       
	   Contact contact=this.contactrepo.findById(conid).get();
	   model.addAttribute("contact", contact);
	  return "normal/update";
	  
	  
	     
   }
   @PostMapping("/updateform/{id}/{conid}")
   public String updateform(@Valid @ModelAttribute("contact")Contact contact,BindingResult result,Model model,@PathVariable(value="id") String id,@PathVariable(value="conid") Integer  conid,HttpSession session)
   {
	   User user=this.userrepo.getUserByUserName(id);
	   Contact contact2=this.contactrepo.findById(conid).get();
	   try {  
		   if(result.hasErrors() )
	   		{
				   model.addAttribute("user", user);
				    model.addAttribute("contact", contact2);
	   			//System.out.println(result);
				   System.out.println(result);
	   			return "normal/update";
	   		}

		user.getCon().remove(contact2);
        contact2.setName(contact.getName());
        contact2.setMail(contact.getMail());
        contact2.setSecame(contact.getSecame());
        contact2.setDescription(contact.getDescription());
        contact2.setPhone(contact.getPhone());
        contact2.setWork(contact.getWork());
        user.getCon().add(contact2);
        this.contactrepo.save(contact2);
	   model.addAttribute("user", user);
	   model.addAttribute("contact", contact2);
	   session.setAttribute("message", new Message("Sucessfully Added","alert-success"));   
	   return "redirect:/user/showContact/0/"+user.getEmail();
			   }
	   catch(Exception e)
	   {
		   model.addAttribute("user", user);
		   model.addAttribute("contact", contact2);
		   session.setAttribute("message", new Message("something Went Wrong "+e.getMessage(),"alert-danger")); 
	   }
	   return "normal/update";   
	   
	   
   }
}
