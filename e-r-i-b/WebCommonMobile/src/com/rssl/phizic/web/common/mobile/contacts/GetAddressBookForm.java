package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * Форма получения адресной книги.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class GetAddressBookForm extends ActionFormBase
{
	private String phones;
	private Boolean showBookmark;
	private List<String> errorPhones;
	private List<Contact> contacts;

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public void setContacts(List<Contact> contacts)
	{
		this.contacts = contacts;
	}

	public List<String> getErrorPhones()
	{
		return errorPhones;
	}

	public void setErrorPhones(List<String> errorPhones)
	{
		this.errorPhones = errorPhones;
	}

	public String getPhones()
	{
		return phones;
	}

	public void setPhones(String phones)
	{
		this.phones = phones;
	}

	public Boolean getShowBookmark()
	{
		return showBookmark;
	}

	public void setShowBookmark(Boolean showBookmark)
	{
		this.showBookmark = showBookmark;
	}
}
