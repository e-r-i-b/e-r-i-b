package com.rssl.phizic.business.profile.addressbook;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.business.profile.addressbook.Contact;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.addressbook.AddressBookConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;

/**
 * ������ �� ������ � ���������� �������� �����
 *
 * @author bogdanov
 * @ created 25.11.14
 * @ $Author$
 * @ $Revision$
 */

public class ContactHelper
{
	private static final AddressBookService addressBookService = new AddressBookService();

	/**
	 * ������� � ���������� ���������.
	 *
	 * @param contact �������.
	 * @return ������� � ���������� ���������.
	 */
	public static Contact getContactWithIncognito(Contact contact)
	{
		boolean incognito = PersonContext.isAvailable() && PersonContext.getPersonDataProvider().getPersonData().isIncognito();
		if (contact != null && (incognito || contact.isIncognito()))
		{
			contact.setSberbankClient(false);
			contact.setAvatarPath("");
			contact.setIncognito(true);
		}
		if (contact != null && !ConfigFactory.getConfig(AddressBookConfig.class).getShowSbClientAttribute())
		{
			contact.setSberbankClient(false);
		}
		return contact;
	}

	/**
	 * ���������� �������� ������ ���������.
	 *
	 * @param mobilePhoneFound ������� ������ � ��������� �����.
	 */
	public static void updateSberbankClient(Pair<Client, Card> mobilePhoneFound, Contact contact) throws BusinessException
	{
		if (contact == null)
			return;

		if (mobilePhoneFound != null ^ contact.isSberbankClient())
		{
			if (!contact.isIncognito())
				contact.setSberbankClient(!contact.isSberbankClient());

			addressBookService.addOrUpdateContact(contact);
		}
	}

	/**
	 * @return �������� ��������� "����������� ������ ��� ������������� �������� ��� Android-���������"
	 */
	public static boolean isShowLinkConfirmContactAndroid()
	{
		return ConfigFactory.getConfig(AddressBookConfig.class).isShowLinkConfirmContactAndroid();
	}
}
