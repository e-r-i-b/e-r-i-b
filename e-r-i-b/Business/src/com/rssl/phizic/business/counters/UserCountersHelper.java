package com.rssl.phizic.business.counters;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.addressbook.AddressBookService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesMessage;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.*;

import java.util.Calendar;

/**
 * @author lukina
 * @ created 02.11.2014
 * @ $Author$
 * @ $Revision$
 */
public class UserCountersHelper
{
	private static final UserCountersService userCountersService = new UserCountersService();
	private static final AddressBookService addressBookService = new AddressBookService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * проверка превышения лимита запросов в МБК/ЕРМБ по номеру телефона
	 * @param login - login клиента
	 * @param type  - тип счетчика
	 * @param phoneNumber  - номер телефона
	 * @return  true - лимит не превышен false - лимит превышен
	 */
	public static boolean checkUserCounters(final Login login, final CounterType type, final String phoneNumber)
	{

		try
		{
			//если контакт из адресной книги ничего не проверяем
			if (addressBookService.isExistContactClientByPhone(login.getId(), PhoneNumberFormat.P2P_NEW.format(PhoneNumber.fromString(phoneNumber.trim()))))
				return true;

			String countLimitStr = ConfigFactory.getConfig(ClientConfig.class).getProperty(type.toValue());

			// если не задан то и не проверяем
			if(countLimitStr.length() == 0)
			{
				log.warn("Не задан лимит на количество запросов информации о клиенте по номеру телефона");
				return true;
			}
			long countLimit = Long.parseLong(countLimitStr);

			UserCounter counter = userCountersService.findByLoginId(login.getId(), type);
			if (counter == null)
				return true;

			//если дата не соответствует текущей, то значение счётчика устарело
			if (!DateHelper.getCurrentDate().equals(DateHelper.clearTime(DateHelper.toCalendar(counter.getChangeDate()))))
				return true;

			return counter.getValue() <= countLimit;
		}
		catch (BusinessException ex)
		{
			log.warn("Ошибка проверки лимита количества запросов информации о клиенте по номеру телефона", ex);
			return false;
		}
	}

	/**
	 * @return сообщение о заблокированном вводе промокода
	 * @throws BusinessException
	 */
	public static String getPromoCodeBlockedMessage() throws BusinessException
	{
		Calendar promoBlockUntil = PersonContext.getPersonDataProvider().getPersonData().getPromoBlockUntil();
		if (promoBlockUntil == null)
			return null;
		else if (promoBlockUntil.before(Calendar.getInstance()))
		{
			userCountersService.reset(PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin(), CounterType.PROMO_CODE);
			return null;
		}
		else
		{
			PromoCodesMessage msg12 = ConfigFactory.getConfig(PromoCodesDepositConfig.class).getPromoCodesMessagesMap().get("MSG012").clone();
			return String.format(msg12.getText(), DateHelper.formatDateToStringOnPattern(promoBlockUntil, "HH:mm dd.MM.yyyy"));
		}
	}

	/**
	 * @return сообщение о заблокированном вводе промокода
	 * @throws BusinessException
	 */
	public static PromoCodesMessage getPromoMessage(String messageName) throws BusinessException
	{
        if (StringHelper.isEmpty(messageName))
            return null;
        return ConfigFactory.getConfig(PromoCodesDepositConfig.class).getPromoCodesMessagesMap().get(messageName).clone();
	}
}
