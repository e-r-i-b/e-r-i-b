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
 * Хелпер по работе с контактами адресной книги
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
	 * Контакт с настройкой инкогнито.
	 *
	 * @param contact контакт.
	 * @return контакт с настройкой инкогнито.
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
	 * Обновление признака Клиент Сбербанка.
	 *
	 * @param mobilePhoneFound телефон найден в мобильном банке.
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
	 * @return значение настройки "Отображение ссылки для подтверждения контакта для Android-устройств"
	 */
	public static boolean isShowLinkConfirmContactAndroid()
	{
		return ConfigFactory.getConfig(AddressBookConfig.class).isShowLinkConfirmContactAndroid();
	}
}
