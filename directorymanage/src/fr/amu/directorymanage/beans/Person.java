package fr.amu.directorymanage.beans;

import java.sql.Date;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import fr.amu.directorymanage.annotations.EmailWithTld;
import fr.amu.directorymanage.annotations.ExtendedEmailValidator;

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
	
	@NotNull(message = "Email field cannot be null!")
    @Length(max = 50, message = "Not greater than {max} caracteres for Email"
    		+ "field!")
	@ExtendedEmailValidator(message="Please provide a valid email address")
    private String mail;
	
	@NotNull
	@Size(min = 10, max = 100, message = "Website must be between [10-100] "
			+ "characters")
	private String website;
	
	@NotNull
	@Past
	/*@DateTimeFormat(pattern="dd/MM/yyyy")*/
	private Date birthday;
	
	@NotNull(message = "Password may not be null")
	@Size(min = 8, message = "Password must contain at least 8 characters")
	//@Min(value = 8 ,)
	private String password;
	
	@NotNull(message = "The group id may not be null")
	@Min(value = 1, message = "The minimal group id is 1")
	private Long groupId;

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
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

}
