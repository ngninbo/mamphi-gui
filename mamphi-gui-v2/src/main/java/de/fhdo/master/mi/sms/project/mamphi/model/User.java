package de.fhdo.master.mi.sms.project.mamphi.model;

public class User {

	private String username;
	private String password;
	
	

	/**
	 * @param username User name
	 * @param password Password
	 */
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		boolean result = false;

		if ((obj == null) || (!(obj instanceof User))) {
			return result;
		}

		User user = (User) obj;
		result = this.username.equals(user.username) && this.password.equals(user.password);

		return result;
	}
}
