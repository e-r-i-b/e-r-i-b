package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactHelper;

/**
 * ����� ������� � ����/��� �� ������ �������� �������� �� ��
 * @author gladishev
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class FindMBClientByContactOperation extends FindMBClientOperation
{
	private static final AddressBookService ADDRESS_BOOK_SERVICE = new AddressBookService();
	private Contact contact;

	/**
	 * ������������� ��������
	 * @param contactId - id �������� �� ��
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void initialize(String contactId) throws BusinessException
	{
		try
		{
			contact = ADDRESS_BOOK_SERVICE.findContactById(Long.parseLong(contactId));
			if (contact == null)
				throw new BusinessException("������� � id=" + contactId + " �� ������ � ��");

			initializeResult(contact.getPhone());
		}
		catch (NumberFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return �������������� �������.
	 */
	public Contact getContact()
	{
		return ContactHelper.getContactWithIncognito(contact);
	}
}
