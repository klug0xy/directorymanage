static private Person personMapper(ResultSet resultSet, int rank) throws SQLException {
		Person person = new Person();
		Long number = resultSet.getLong("id");
		String firstName = resultSet.getString("firstName");
		String lastName = resultSet.getString("lastName");
		String mail = resultSet.getString("mail");
		String website = resultSet.getString("website");
		Date birthday = resultSet.getDate("birthday");
		String password = resultSet.getString("password");
		

		person.setId(number);
		person.setFirstName(firstName);
		person.setLastName(lastName);
		person.setMail(mail);
		person.setWebsite(website);
		person.setBirthday(birthday);
		person.setPassword(password);
		return person;
	}

// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("Password reset for directory manage");

			// Now set the actual message
			message.setContent("<html><body>Hello,<br/><a href='http://localhost:" 
			+ "8080/directorymanage/newPassword/"+emailId + "/'> "
			+ "Click here</a> to reset password</body></html>", "text/html");

			// Send message
			Transport.send(message);
			logger.info("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}


				<form action="index.html">
					<div class="input-group">
						<input type="text" placeholder="search ..." class="form-control">
						<span class="input-group-btn">
							<button class="btn btn-default" type="button">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>


@Test
	public void testFindAllGroupPersonsById(){
		
	}
	
	@Test
	public void testfindLimitGroupPersonsByIdSize(){
		
		Collection<Person> limitGroupPersons;
		Integer offset = 0;
		Integer maxRows = 10; 
		Long searchedGroupId = new Long(2);
		Integer expectedSize = 1;
		limitGroupPersons = directoryManager.findLimitGroupPersonsById
				(searchedGroupId, offset, maxRows);
		Integer actualSize = limitGroupPersons.size();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void testfindLimitGroupPersonsById(){
		
		Collection<Person> limitGroupPersons;
		Integer offset = 0;
		Integer maxRows = 10; 
		Long searchedGroupId = new Long(2);
		Integer expectedSize = 1;
		String expectedFirstName = "Oumaima";
		String expectedLastName = "Nammassi";
		limitGroupPersons = directoryManager.findLimitGroupPersonsById
				(searchedGroupId, offset, maxRows);
		int actualSize = limitGroupPersons.size();
		assertEquals(expectedSize, actualSize);
		for (Person actualPerson : limitGroupPersons){
			String actualFirstName = actualPerson.getFirstName();
			String actualLastName = actualPerson.getLastName();
			assertEquals(expectedFirstName, actualFirstName);
			assertEquals(expectedLastName, actualLastName);
		}
	}
	

