package com.rssl.phizic.operations.userprofile.addressbook;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

/**
 * �������� �������������� ��������
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactOperation  extends ConfirmableOperationBase
{
	private static final AddressBookService addressBookService = new AddressBookService();
	private Contact contact;

	/**
	 * ������������� ��������
	 *
	 * @param id ������������� ��������
	 */

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		contact = addressBookService.findContactById(id);
		if(!PersonContext.getPersonDataProvider().getPersonData().getLogin().getId().equals(contact.getOwner().getId()))
			throw new BusinessLogicException("��������� ������� �� ����������� ���.");
	}

	/**
	 * ������������� ��������
	 *
	 * @param contactId ������������� ��������
	 * @param loginId ������������� ������
	 */

	public void initialize(final Long contactId, final Long loginId) throws BusinessException
	{
		contact = addressBookService.findContactByLoginAndId(contactId, loginId);
	}

	/**
	 * ���������� ��������
	 *
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		addressBookService.addOrUpdateContact(contact);
	}

	/**
	 * ��������� ��������
	 *
	 * @return �������
	 */

	public Contact getEntity() throws BusinessException, BusinessLogicException
	{
		return this.contact;
	}

	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		save();
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return new SmsPasswordConfirmStrategy();
	}

	public ConfirmableObject getConfirmableObject()
	{
		return contact;
	}
}
