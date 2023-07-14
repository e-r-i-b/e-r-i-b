package com.rssl.phizic.business.clients;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.services.ServiceService;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.remoteConnectionUDBO.RemoteConnectionUDBOConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.bankroll.CardState;
import com.rssl.phizic.gate.bankroll.CardType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;

import java.io.Serializable;
import java.util.*;

/**
 * Хелпер для работы с удалённым заключением договоров УДБО
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */

public class RemoteConnectionUDBOHelper implements Serializable
{
	public static final String MOSCOW_TB_38 = "38";
	public static final String MOSCOW_TB_99 = "99";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final PersonService personService = new PersonService();
	private static final String STORE_KEY = RemoteConnectionUDBOHelper.class.getName();
	private static final ServiceService serviceService = new ServiceService();

	private Boolean showMoreSboItem; //признак необходимости показа пункта главного меню "Больше Сбербанк Онлайн"
	private boolean showCancelUdboMessage; //переход на форму одключения УДБО
	private boolean redirectToPayment; //переход на форму одключения УДБО

	/**
	 * @return список ТБ, в которых доступна возможность заключения УДБО
	 */
	public static List<String> getAllowedTB()
	{
		String allowedTBString = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getAllowedTB();
		if (StringUtils.isNotEmpty(allowedTBString))
			return new ArrayList<String>(Arrays.asList(StringUtils.split(allowedTBString, ";")));
		return new ArrayList<String>();
	}

	/**
	 * Проверяем входит ли ТБ клиента в список ТБ, в которых доступна возможность заключения УДБО
	 * @param terBank - номер ТБ клиента
	 * @return true - входит, false - не входит
	 */
	private static boolean checkClientAllowedTB(String terBank)
	{
		List<String> allowedTBList = getAllowedTB();
		if (CollectionUtils.isNotEmpty(allowedTBList))
		{
			for (String allowedTB : allowedTBList)
				if (allowedTB.equals(terBank))
					return true;
		}
		return false;
	}

	/**
	 * @return признак: может ли клиент ззаключить договор УДБО
	 */
	public static boolean checkConnectAbility()
	{
		return isShowMoreSbolMenuItem();
	}

	private static boolean checkCards() throws BusinessException, GateLogicException, GateException, BusinessLogicException
	{
		ActivePerson person = getActivePerson();
		if (person == null)
			return false;

		Client client = person.asClient();
		List<CardLink> products = PersonContext.getPersonDataProvider().getPersonData().getCards();
		if (products == null)
			return false;

		for (CardLink cardLink : products)
		{
			Card card = cardLink.getCard();
			if (card.getOffice() != null
					&& clientTbEqualsCardTb(client, card)
					&& card.getCardState() == CardState.active
					&& (card.getCardType() == CardType.debit || card.getCardType() == CardType.overdraft))
			{
				return true;
			}
		}
		return false;
	}

	private static boolean clientTbEqualsCardTb(Client client, Card card)
	{
		String clientRegion = client.getOffice().getCode().getFields().get("region");
		String cardRegion = card.getOffice().getCode().getFields().get("region");
		//Для Московского банка используются два кода ТБ: 38 и 99. Любые комбинации этих кодов интерпретируются как один ТБ
		return clientRegion.equals(cardRegion)
				|| (clientRegion.equals(MOSCOW_TB_38) && cardRegion.equals(MOSCOW_TB_99))
				|| (clientRegion.equals(MOSCOW_TB_99) && cardRegion.equals(MOSCOW_TB_38));
	}

	/**
	 * Задать признак первый вход в 16 релизе
	 * @param firstUserEnter16Release - признак
	 */
	public static void setFirstUserEnter16Release(final boolean firstUserEnter16Release)
	{
		if (PersonContext.isAvailable())
			ConfigFactory.getConfig(UserPropertiesConfig.class).setFirstUserEnter16Release(firstUserEnter16Release);
		else
		{
			try
			{
				//если контекст персоны ещё не создался
				UserPropertiesConfig.processUserSettingsWithoutPersonContext(AuthenticationContext.getContext().getLogin(), new SettingsProcessor<Void>()
				{
					public Void onExecute(UserPropertiesConfig userProperties)
					{
						userProperties.setFirstUserEnter16Release(firstUserEnter16Release);
						return null;
					}
				});
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * @return Признак первый вход в 16 релизе
	 */
	public static boolean isFirstUserEnter16Release()
	{
		if (PersonContext.isAvailable())
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isFirstUserEnter16Release();
		else
		{
			try
			{
				//если контекст персоны ещё не создался
				return UserPropertiesConfig.processUserSettingsWithoutPersonContext(AuthenticationContext.getContext().getLogin(), new SettingsProcessor<Boolean>()
				{
					public Boolean onExecute(UserPropertiesConfig userProperties)
					{
						return userProperties.isFirstUserEnter16Release();
					}
				});
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				return false;
			}
		}
	}

	/**
	 * Задать признак показа новинки для пункта Больше сбербанк онлайн
	 * @param showMoreSbolNovelty - признак
	 */
	public static void setShowMoreSbolNovelty(boolean showMoreSbolNovelty)
	{
		UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
		if  (config.isShowMoreSbolNovelty() != showMoreSbolNovelty)
			config.setShowMoreSbolNovelty(showMoreSbolNovelty);
	}

	/**
	 * @return Признак показа новинки для пункта Больше сбербанк онлайн
	 */
	public static boolean isShowMoreSbolNovelty()
	{
		if (PersonContext.isAvailable())
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isShowMoreSbolNovelty();
		else
		{
			try
			{
				//если контекст персоны ещё не создался
				return UserPropertiesConfig.processUserSettingsWithoutPersonContext(AuthenticationContext.getContext().getLogin(), new SettingsProcessor<Boolean>()
				{
					public Boolean onExecute(UserPropertiesConfig userProperties)
					{
						return userProperties.isShowMoreSbolNovelty();
					}
				});
			}
			catch (Throwable e)
			{
				log.error(e.getMessage(), e);
				return false;
			}
		}
	}

	/**
	 * @return текущую персону клиента
	 */
	private static ActivePerson getActivePerson()
	{
		if (PersonContext.isAvailable())
		{
			return PersonContext.getPersonDataProvider().getPersonData().getPerson();
		}
		else
		{
			try
			{
				if (AuthenticationContext.getContext().getLogin() == null)
					return null;
				return personService.findByLogin((Login) AuthenticationContext.getContext().getLogin());
			}
			catch (BusinessException e)
			{
				log.error("Ошибка при получении персоны", e);
				return null;
			}
		}
	}

	/**
	 * @return признак Продолжать работу в СБОЛ при отказе от заключения УДБО
	 */
	public static boolean isWorkWithoutUDBO()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).isWorkWithoutUDBO();
	}

	/**
	 * @return Заголовок сообщения при отказе от подключения УДБО
	 */
	public static String getCancelConnectUDBOMessageTitle()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getCancelConnectUDBOMessageTitle();
	}

	/**
	 * @return Текст сообщения при отказе от подключения УДБО
	 */
	public static String getCancelConnectUDBOMessageText()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getCancelConnectUDBOMessageText();
	}

	/**
	 * Поучить данные из сессии, чтоб не вычислять их каждый раз.
	 *
	 * @return помощника подключения УДБО
	 */
	public static RemoteConnectionUDBOHelper getFromSession()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalArgumentException("Нет сессии.");

		Object obj = store.restore(STORE_KEY);

		if (obj == null)
		{
			RemoteConnectionUDBOHelper helper = new RemoteConnectionUDBOHelper();
			store.save(STORE_KEY, helper);
			return helper;
		}

		return (RemoteConnectionUDBOHelper) obj;
	}

	private RemoteConnectionUDBOHelper()
	{
		showMoreSboItem = null;
		redirectToPayment = false;
	}

	/**
	 * @return действует ли сейчас холодный период
	 */
	public static boolean isColdPeriod(CommonLogin login)
	{
		try
		{
			Calendar dateConnectionUDBO = UserPropertiesConfig.processUserSettingsWithoutPersonContext(login, new SettingsProcessor<Calendar>()
			{
				public Calendar onExecute(UserPropertiesConfig userProperties)
				{
					return userProperties.getDateConnectionUDBO();
				}
			});

			if (dateConnectionUDBO != null)
			{
				return checkDateConnectionUDBO(dateConnectionUDBO);
			}
			else
			{
				ProfileType type = AuthenticationContext.getContext().getProfileType();
				//если находимся в резервном блоке то делаем холодный режим всем недавно зарегистрированным
				if (type == ProfileType.TEMPORARY)
				{
					ActivePerson person = null;
					if (PersonContext.isAvailable())
						person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
					else
						person = personService.findByLoginId(login.getId());

					if (person == null)
						return false;

					if (person.getCreationType() != CreationType.UDBO)
						return false;

					Calendar serviceInsertionDate = person.getAgreementDate();
					return checkDateConnectionUDBO(serviceInsertionDate);
				}
				return false;
			}
		}
		catch (BusinessException e)
		{
			log.error("ошибка во время получения даты удаленного подключения УДБО", e);
			return false;
		}
	}

	private static boolean checkDateConnectionUDBO(Calendar dateConnectionUDBO)
	{
		int timeColdPeriod = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getTimeColdPeriod();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, - timeColdPeriod);
		return dateConnectionUDBO.after(calendar);
	}

	/**
	 * @return показывать только подключение УДБО. Остальной функионал не доступен
	 */
	public static boolean isShowOnlyConnectionUDBO()
	{
		return isShowMoreSbolMenuItem() && !isWorkWithoutUDBO();
	}

	/**
	 * @return признак необходимости показа пункта главного меню "Больше Сбербанк Онлайн"
	 */
	public static boolean isShowMoreSbolMenuItem()
	{
		RemoteConnectionUDBOHelper sessionData = getFromSession();
		if (sessionData.isShowMoreSbolItem() != null)
			return sessionData.isShowMoreSbolItem();
		else
		{
			boolean showMoreSbolMenuItem = checkShowMoreSbolMenuItem();
			sessionData.setShowMoreSboItem(showMoreSbolMenuItem);
			return showMoreSbolMenuItem;
		}

	}

	private Boolean isShowMoreSbolItem()
	{
		return showMoreSboItem;
	}

	private void setShowMoreSboItem(boolean showMoreSboItem)
	{
		this.showMoreSboItem = showMoreSboItem;
	}

	private static boolean checkShowMoreSbolMenuItem()
	{
		if (AuthenticationContext.getContext().isAuthGuest())
			return false;
		ActivePerson person = getActivePerson();
		if (person == null)
			return false;
		if (SegmentCodeType.VIP == person.getSegmentCodeType())
			return false;
		if (!(CreationType.SBOL == person.getCreationType() || CreationType.CARD == person.getCreationType()))
			return false;

		try
		{
			if (!serviceService.isPersonServices(person.getLogin().getId(),"RemoteConnectionUDBOClaim"))
				return false;

			if (!checkClientAllowedTB(((ExtendedCodeImpl)person.asClient().getOffice().getCode()).getRegion()))
				return false;

			Collection<String> phones = MobileBankManager.getPhones(person.getLogin());
			if (CollectionUtils.isEmpty(phones))
				return false;

			if (StringHelper.isEmpty(person.getLogin().getLastLogonCardOSB()) || StringHelper.isEmpty(person.getLogin().getLastLogonCardVSP()))
				return false;

			if (!checkCards())
				return false;
		}
		catch (Exception e)
		{
			log.error("Ошибка при определении необходимости показа предложения подключить УДБО", e);
			return false;
		}
		return true;
	}

	/**
	 * Задать признак перехода на форму подключния УДБО
	 * @param redirectToPayment - признак
	 */
	public void setRedirectToPayment(boolean redirectToPayment)
	{
		this.redirectToPayment = redirectToPayment;
	}

	/**
	 * @return перейти на форму подключния УДБО
	 */
	public boolean isRedirectToPayment()
	{
		return redirectToPayment;
	}

	public boolean isShowCancelUdboMessage()
	{
		return showCancelUdboMessage;
	}

	public void setShowCancelUdboMessage(boolean showCancelUdboMessage)
	{
		this.showCancelUdboMessage = showCancelUdboMessage;
	}
}
