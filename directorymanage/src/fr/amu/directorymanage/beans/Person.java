package fr.amu.directorymanage.beans;

import java.util.Date;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

public class Person {
	
	@NotNull
    @Min(value = 1, message = "Minimal person id is 1")
	private Long id;
	
	@NotNull
	@Size(min = 1, message = "First Name is mandatory!")
	private String firstName;
	
	@NotNull
	@Size(min = 1, message = "Last Name is mandatory!")
	private String lastName;
	
	@NotNull
	@Past
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date birthday;
	
	@NotNull
	@Valid
	private PersonMail personMail = new PersonMail();
	
	@NotNull(message = "Email field cannot be null!")
    @Length(max = 50, message = "Not greater than {max} caracteres for Email field!")
	@Email(message="Please provide a valid email address")
    private String mail;
	
	@NotNull(message = "The group id may not be null")
	@Min(value = 1, message = "The minimal group id is 1")
	private Long groupId;
	
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
	public void setId(Long id) {
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

	public PersonMail getPersonMail() {
		return personMail;
	}

	public void setPersonMail(PersonMail personMail) {
		this.personMail = personMail;
	}
	
	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

}
