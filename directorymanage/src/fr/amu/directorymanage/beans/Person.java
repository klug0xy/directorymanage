package fr.amu.directorymanage.beans;

import java.util.Date;

public class Person {
	
	public long id;
	public String firstName;
	public String lastName;
	public Date birthday;
	public String mail;
	public long groupId;
	
	public boolean valid = false;
	public String numberMessage;
	
	public String getNumberMessage() {
		return numberMessage;
	}

	public void setNumberMessage(String numberMessage) {
		this.numberMessage = numberMessage;
	}

	public String nameMessage;
	public String mailMessage;
	
	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public String getNameMessage() {
		return nameMessage;
	}

	public void setNameMessage(String nameMessage) {
		this.nameMessage = nameMessage;
	}

	public String getMailMessage() {
		return mailMessage;
	}

	public void setMailMessage(String mailMessage) {
		this.mailMessage = mailMessage;
	}

	public Person() {}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

}
