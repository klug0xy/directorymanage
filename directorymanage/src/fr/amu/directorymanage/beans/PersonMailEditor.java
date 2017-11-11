package fr.amu.directorymanage.beans;

import java.beans.PropertyEditorSupport;

public class PersonMailEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		Object o = this.getValue();
		if (o instanceof PersonMail) {
			PersonMail personMail = (PersonMail) o;
			return personMail.getMail();
		}
		return super.getAsText();
	}

	@Override
	public void setAsText(String text) {

		PersonMail personMail = new PersonMail();
		super.setValue(personMail);

	}

}
