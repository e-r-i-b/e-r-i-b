package com.rssl.phizic.operations.contacts;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactStatus;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Операция поиска контакта в адресной книге.
 *
 * @author bogdanov
 * @ created 28.10.14
 * @ $Author$
 * @ $Revision$
 */

public class SearchContactInAddressBookOperation extends OperationBase
{
	private static final AddressBookService addressBookService = new AddressBookService();

	private List<Contact> contacts;

	public void initialize(String byName, String byAlias, String bySmallAlias, String byPhone, boolean useLike) throws BusinessException
	{
		contacts = new LinkedList<Contact>(addressBookService.getClientContacts(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId()));
		Iterator<Contact> iter = contacts.iterator();
		while (iter.hasNext())
		{
			Contact c = iter.next();
			if (c.getStatus() != ContactStatus.ACTIVE)
			{
				iter.remove();
				continue;
			}

			if (
				!stringEqual(byName, c.getFullName(), useLike)
				&& !stringEqual(byPhone, c.getPhone(), useLike)
				&& !stringEqual(byAlias, c.getAlias(), useLike)
				&& !stringEqual(bySmallAlias, c.getCutAlias(), useLike))
			{
				iter.remove();
			}
		}
	}

	private boolean stringEqual(String s1, String s2, boolean useLike)
	{
		if (StringHelper.isEmpty(s1))
			return false;

		if (StringHelper.isEmpty(s2))
			return false;

		String lc1 = s1.toLowerCase();
		String lc2 = s2.toLowerCase();

		return useLike ? lc2.contains(lc1) : lc2.equals(lc1);
	}

	public List<Contact> getContacts()
	{
		return contacts;
	}
}
