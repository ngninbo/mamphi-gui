package de.fhdo.master.mi.sms.project.mamphi.model;

import java.util.Objects;

public class User {

	private String username;
	private String password;

	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof User)) return false;
		User user = (User) o;
		return Objects.equals(getUsername(), user.getUsername()) &&
				Objects.equals(getPassword(), user.getPassword());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getUsername(), getPassword());
	}
}
