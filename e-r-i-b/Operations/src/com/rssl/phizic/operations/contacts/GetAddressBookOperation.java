package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactCategory;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * Операция получения адресной книги клиента.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class GetAddressBookOperation extends OperationBase
{
	private static final AddressBookService addressBookService = new AddressBookService();
	private List<Contact> contacts;
	private List<String> errorsPhones;

	public void initialize(String phones, boolean showBookmark) throws BusinessException
	{
		contacts = new LinkedList<Contact>(addressBookService.getClientContacts(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId()));
		Set<String> errorPhones = parsePhones(phones);
		Iterator<Contact> iter = contacts.iterator();
		while (iter.hasNext()) {
			Contact c = iter.next();
			boolean hasNumber = errorPhones.remove(c.getPhone());
			if (c.getStatus() != ContactStatus.ACTIVE) {
				iter.remove();
				continue;
			}

			if (hasNumber)
				continue;

			//Выдаем только те контакты, по которым указаны номера телефонов или категория Избранный контакт.
			if (!showBookmark || c.getCategory() != ContactCategory.BOOKMARK)
				iter.remove();
		}

		errorsPhones = new LinkedList<String>(errorPhones);
	}

	private Set<String> parsePhones(String phones) throws BusinessException
	{
		Set<String> result = new HashSet<String>();
		if (StringHelper.isEmpty(phones))
			return result;

		String phs[] = phones.split(",");
		for (String phn : phs)
		{
			String ph = phn.replaceAll("\\D", "");
			if (!StringHelper.isEmpty(ph))
				result.add(ph);
		}

		return result;
	}

	public List<Contact> getContacts()
	{
		return contacts;
	}

	public List<String> getErrors()
	{
		return errorsPhones;
	}
}
