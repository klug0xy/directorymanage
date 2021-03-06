/*
 * Copyright December 2017 the original author or authors.
 * 
 * Project released in an university setting
 *
 */

package fr.amu.directorymanage.beans;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 
 * La classe qui definit le bean Group
 * 
 * @author Houssem Mjid
 * @author Mohamad Abdelnabi
 *  
 */

public class Group {
	
	@NotNull
    @Min(value = 1, message = "Minimal group id is 1")
	public Long id;
	
	@NotNull
	@Size(min = 1, message = "Name field is mandatory!")
	public String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
