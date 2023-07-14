package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactCategory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

/**
 * ќпераци€ редактировани€ контакта в адресной книге.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditAddressBookContactOperation extends OperationBase
{
	private static final AddressBookService addressBookServicce = new AddressBookService();
	private Contact contact;
	private String error;

	public void initialize(long id, String name, String alias, String smallalias, String cardnumber, ContactCategory category, Boolean trusted) throws BusinessException
	{
		contact = addressBookServicce.findContactByLoginAndId(id, PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		if (contact == null)
			return;

		if (!StringHelper.isEmpty(name))
			contact.setFullName(name);

		if (!StringHelper.isEmpty(alias))
			contact.setAlias(alias);

		if (!StringHelper.isEmpty(smallalias))
			contact.setCutAlias(smallalias);

		if (!StringHelper.isEmpty(cardnumber) && cardnumber.matches("\\d{16,20}"))
			contact.setCardNumber(cardnumber);

		if (category != null)
			contact.setCategory(category);

		if (trusted != null)
			contact.setTrusted(trusted);

		addressBookServicce.addOrUpdateContact(contact);
	}

	public Contact getContact()
	{
		return contact;
	}

	public String getError()
	{
		return error;
	}
}

