package com.rssl.phizic.operations.userprofile.addressbook;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;

/**
 * �������� ��������� �������� �����.
 *
 * @author bogdanov
 * @ created 19.09.14
 * @ $Author$
 * @ $Revision$
 */

public class ViewAddressBookOperation extends OperationBase
{
	private static final AddressBookService ADDRESS_BOOK_SERVICE = new AddressBookService();
	private List<Contact> contacts;

	/**
	 * ������������� ��������.
	 *
	 * @param owner ������.
	 */
	public void initialize(CommonLogin owner) throws BusinessException
	{
		contacts = ADDRESS_BOOK_SERVICE.getClientContacts(owner.getId());
	}

	/**
	 * ������ ���������.
	 *
	 * @return ������ ���������.
	 */
	public List<Contact> getContacts()
	{
		return contacts;
	}

	/**
	 * ������ ��������� ������� � ������ ����� ������ ����� ������� ����� � �� ����� �����
	 * @param client
	 * @return List<Contact> - ������ ��������� ������� � ������ ����� ������ ����� ������� ����� � �� �����
	 * @throws BusinessException
	 */
	public List<Contact> getClientAddressBookByPhoneMaxCountPay(CommonLogin client) throws BusinessException
	{
		MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
		Integer recordCount = mobileApiConfig.getMobilePhoneNumberCount();
		return ADDRESS_BOOK_SERVICE.getClientsContactsByPhoneMaxCountPay(client, recordCount);
	}

	/**
	 * ����� ��������� �� ������ ��������.
	 *
	 * @param login ����� �������.
	 * @param ids ������ ��������������� ���������.
	 * @param phone �������.
	 * @return ������ ��������������� ���������, ��������������� ������.
	 */
	public List<String> findByPhone(CommonLogin login, String ids, String phone) throws BusinessException
	{
		String tids[] = StringHelper.isEmpty(ids) ? new String[]{} : ids.split("\\|");
		String tphone = phone.replaceAll("\\D", "");
		return ADDRESS_BOOK_SERVICE.findContactClientByPhone(login.getId(), tids, tphone);
	}
}
