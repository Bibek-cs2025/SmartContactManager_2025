package com.example.contactmanager.helper;

public class ForgotPass {

	public String msg;
	public boolean result;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ForgotPass [msg=" + msg + ", result=" + result + "]";
	}
	public ForgotPass() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ForgotPass(String msg, boolean result) {
		super();
		this.msg = msg;
		this.result = result;
	}
}
