package fr.amu.directorymanage.bus;

import java.util.Collection;
import java.util.HashMap;

import fr.amu.directorymanage.beans.Person;

public class PersonManager {

    final private HashMap<Long, Person> persons;
    

    public PersonManager() {
    	persons = new HashMap<>();
    	Person p1 = new Person();
    	p1.setId(1);
    	p1.setFirstName("Houssem");
    	p1.setLastName("Mjid");
    	p1.setMail("mjidhoussem@gmail.com");
    	Person p2 = new Person();
    	p2.setId(2);
    	p2.setFirstName("Mohamed");
    	p2.setLastName("Abdelnabi");
    	p2.setMail("momo@gmail.com");
    	Person p3 = new Person();
    	p3.setId(3);
    	p3.setFirstName("Massat");
    	p3.setFirstName("Jean Luc");
    	p3.setMail("massat@gmail.com");
    	persons.put(p1.getId(), p1);
    	persons.put(p2.getId(), p2);
    	persons.put(p3.getId(), p3);
    	
    }

    public Collection<Person> findAll() {
        throw new IllegalStateException("Not yet implemented");
    }

    public void save(Person p) {
        persons.put(p.getId(), p);    }
    
    public void check(Person p) {
    	p.setValid(true);
    	
    	if (p.getFirstName() == null || p.getFirstName() == "")  {
    		p.setValid(false);
    		p.setNameMessage("Name is required");    		
    	} else {
    		p.setNameMessage("");
    	}
    	String email = p.getMail();
    	String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern pa = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = pa.matcher(email);
        if( !m.matches() || email == "") {
        	p.setValid(false);
        	p.setMailMessage("Mail is required");
        }
        else {
        	p.setMailMessage("");
        }
        if (p.getId() == 0) {
        	p.setValid(false);
        	p.setNumberMessage("0 n'est pas valide comme Numéro");
        }
        else p.setNumberMessage("");
    }

	public HashMap<Long, Person> getPersons() {
		return persons;
	}

}
