package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.business.profile.addressbook.Contact;
import org.apache.struts.action.ActionForm;

import java.util.List;

/**
 * Форма для отображения адресной книги.
 *
 * @author bogdanov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewAddressBookForm extends ActionForm
{
	private List<Contact> contactList;

	public void setContactList(List<Contact> contactList)
	{
		this.contactList = contactList;
	}

	public List<Contact> getContactList()
	{
		return contactList;
	}
}
