package fr.amu.directorymanage.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class PersonMail {
	
//    @Pattern(regexp="^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\\\[[0-9]{1,3}\\\\."
//    		+ "[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\])|(([a-zA-Z\\\\"
//    		+ "-0-9]+\\\\.)+[a-zA-Z]{2,}))$", 
//    		message="Mail is resuired!")
	@NotNull(message = "Email field cannot be null!")
    @Length(max = 50, message = "Not greater than {max} caracteres for Email field!")
	@Email(message="Please provide a valid email address")
    String mail;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}
	
	

}
