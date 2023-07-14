package com.rssl.phizic.operations.userprofile.addressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.business.profile.addressbook.ContactHelper;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * Получение контакта из АК клиента по номеру телефона
 * @author gladishev
 * @ created 09.10.2014
 * @ $Author$
 * @ $Revision$
 */

public class GetContactOperation extends OperationBase implements ViewEntityOperation<Contact>
{
	private Contact contact;
	private AddressBookService addressBookService = new AddressBookService();

	public void initialize(String contactPhone) throws BusinessException
	{
		try
		{
			String formatPhone = PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(contactPhone.trim()));
			contact = addressBookService.findContactClientByPhone(PersonContext.getPersonDataProvider().getPersonData().getLogin().getId(), formatPhone);
		}
		catch (NumberFormatException e)
		{
			throw new BusinessException(e);
		}
	}

	public Contact getEntity() throws BusinessException, BusinessLogicException
	{
		return ContactHelper.getContactWithIncognito(contact);
	}
}
