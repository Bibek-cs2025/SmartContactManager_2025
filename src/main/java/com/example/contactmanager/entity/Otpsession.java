package com.example.contactmanager.entity;

public class Otpsession {

	private String name;
	private String email;
	private int time;
	private int maxtime;
	private String otp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getMaxtime() {
		return maxtime;
	}
	public void setMaxtime(int maxtime) {
		this.maxtime = maxtime;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	@Override
	public String toString() {
		return "Otpsession [name=" + name + ", email=" + email + ", time=" + time + ", maxtime=" + maxtime + ", otp="
				+ otp + "]";
	}
	public Otpsession() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Otpsession(String name, String email, int time, int maxtime, String otp) {
		super();
		this.name = name;
		this.email = email;
		this.time = time;
		this.maxtime = maxtime;
		this.otp = otp;
	}
}
