package org.spring.springboot.domain;


import java.util.List;

public class User {
	private String id;
	private String fname;
	private String fcheck;
	private List<String> functionlist;
	private List<String> applylist;

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public List<String> getFunctionlist() {
		return functionlist;
	}

	public void setFunctionlist(List<String> functionlist) {
		this.functionlist = functionlist;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFcheck() {
		return fcheck;
	}

	public void setFcheck(String fcheck) {
		this.fcheck = fcheck;
	}

	public List<String> getApplylist() {
		return applylist;
	}

	public void setApplylist(List<String> applylist) {
		this.applylist = applylist;
	}
}
