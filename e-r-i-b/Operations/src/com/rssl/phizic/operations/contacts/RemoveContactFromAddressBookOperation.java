package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;

/**
 * ќпераци€ удалени€ контакта из адресной книги
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class RemoveContactFromAddressBookOperation extends OperationBase
{
	private static final AddressBookService addressBookService = new AddressBookService();
	private boolean deleted;

	public void initialize(long id) throws BusinessException
	{
		Contact contact = addressBookService.findContactByLoginAndId(id, PersonContext.getPersonDataProvider().getPersonData().getLogin().getId());
		deleted = false;
		if (contact!= null && contact.getStatus() == ContactStatus.ACTIVE)
		{
			contact.setStatus(ContactStatus.DELETE);
			addressBookService.addOrUpdateContact(contact);
			deleted = true;
		}
	}

	public boolean isDeleted()
	{
		return deleted;
	}
}
