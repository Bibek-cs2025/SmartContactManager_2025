package com.example.contactmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="CONTACT")
public class Contact {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int conid;
	//will create a Join column on the name of user
	@ManyToOne()
	@JsonIgnore
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@NotBlank(message="Name cant be empty")
	@Size(min=2,max=40,message="too long")
	private String name;
	private String secame;
	private String work;
	@Column(unique=true)
	@NotBlank(message="Email cant be empty")
	@Pattern(regexp="^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message="not valid email")
	private String mail;
	@Size(min=10,max=12,message="Give proper Number")
	private String phone;

	@Column(length=5000)
	private String description;
	public int getConid() {
		return conid;
	}
	public void setConid(int conid) {
		this.conid = conid;
	}
	private String imgurl;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSecame() {
		return secame;
	}
	public void setSecame(String secame) {
		this.secame = secame;
	}
	public String getWork() {
		return work;
	}
	public void setWork(String work) {
		this.work = work;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Contact [conid=" + conid + ", name=" + name + ", secame=" + secame + ", work=" + work + ", mail=" + mail
				+ ", phone=" + phone + ", imgurl=" + imgurl + ", description=" + description + "]";
	}
	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Contact(int conid, String name, String secame, String work, String mail, String phone, String imgurl,
			String description) {
		super();
		this.conid = conid;
		this.name = name;
		this.secame = secame;
		this.work = work;
		this.mail = mail;
		this.phone = phone;
		this.imgurl = imgurl;
		this.description = description;
	}
	public Contact(User user) {
		super();
		this.user = user;
	}
	

}
