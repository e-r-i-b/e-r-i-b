package com.rssl.phizic.business.departments;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Предпочтительный способ отправки СМС сообщений
 * @author Pankin
 * @ created 17.11.2011
 * @ $Author$
 * @ $Revision$
 */

public enum SendSMSPreferredMethod
{
	// Анкета клиента
	PROFILE
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					//пытаемся потянуть из анкеты
					Collection<String> phones = getUserMobilePhone(person);
					//если нет, то из мобильного банка
					if (CollectionUtils.isEmpty(phones))
						return MOBILE_BANK_ONLY.getPhones(person,isNotification);
					//если это оповщение о входе, то добавляем номер из МБ
					if (isNotification)
						phones.addAll(MOBILE_BANK_ONLY.getPhones(person,isNotification));
					return phones;
				}
			},
	// Мобильный банк
	MOBILE_BANK
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					//пытаемся потянуть из мобильного банка
					Collection<String> phones = MOBILE_BANK_ONLY.getPhones(person, isNotification);
					//если нет, то из анкеты
					if (CollectionUtils.isEmpty(phones))
						return getUserMobilePhone(person);
					//если это оповщение о входе, то добавляем номер из анкеты
					if (isNotification)
						phones.addAll(getUserMobilePhone(person));

					return phones;
				}
			},
	// Использовать только мобильный банк
	MOBILE_BANK_ONLY
			{
				public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
				{
					try
					{
						//пытаемся потянуть только из мобильного банка
						Collection<String> phones;
						if (isNotification)
							phones = StaticPersonData.getPhones(person.getLogin(), null);
						else
							phones = StaticPersonData.getPhones(person.getLogin(), false);

						if (phones == null)
							return Collections.emptyList();
						return phones;
					}
					catch (BusinessLogicException e)
					{
						throw new BusinessException(e);
					}
				}
			};

	protected Collection<String> getUserMobilePhone(ActivePerson person) throws BusinessException
	{
		String phone = person.getMobilePhone();
		if (StringHelper.isEmpty(phone))
			return Collections.emptyList();
	    Collection<String> phoneList = new ArrayList<String>();
		phoneList.add(phone);
		return phoneList;
	}

	/**
	 * получить номера телефонов в зависимости от стратегии банка
	 * @param person клиент
	 * @param isNotification - true, если отправляем оповещение о входе
	 * @return номера телефонов
	 * @throws BusinessException
	 */
	public Collection<String> getPhones(ActivePerson person, boolean isNotification) throws BusinessException
	{
		throw new UnsupportedOperationException("Не задан метод получения телефона");
	}
}
