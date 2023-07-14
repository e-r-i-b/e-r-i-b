package com.rssl.phizic.web.common.socialApi.dictionaries;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;
import com.rssl.phizic.business.profile.addressbook.Contact;


/**
 * @ author: Vagin
 * @ created: 22.07.2013
 * @ $Author
 * @ $Revision
 * Форма получения справочника доверенных получателей.
 */
public class ShowRecentlyFilledFieldDataForm extends ActionFormBase
{
	private String type;
	private List<Contact> contacts;
	private List<String> phones;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(List<Contact> contacts)
	{
		this.contacts = contacts;
	}

	public List<String> getPhones()
	{
		return phones;
	}

	public void setPhones(List<String> phones)
	{
		this.phones = phones;
	}
}
