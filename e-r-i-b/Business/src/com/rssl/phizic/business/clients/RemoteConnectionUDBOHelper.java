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
 * ������ ��� ������ � �������� ����������� ��������� ����
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

	private Boolean showMoreSboItem; //������� ������������� ������ ������ �������� ���� "������ �������� ������"
	private boolean showCancelUdboMessage; //������� �� ����� ���������� ����
	private boolean redirectToPayment; //������� �� ����� ���������� ����

	/**
	 * @return ������ ��, � ������� �������� ����������� ���������� ����
	 */
	public static List<String> getAllowedTB()
	{
		String allowedTBString = ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getAllowedTB();
		if (StringUtils.isNotEmpty(allowedTBString))
			return new ArrayList<String>(Arrays.asList(StringUtils.split(allowedTBString, ";")));
		return new ArrayList<String>();
	}

	/**
	 * ��������� ������ �� �� ������� � ������ ��, � ������� �������� ����������� ���������� ����
	 * @param terBank - ����� �� �������
	 * @return true - ������, false - �� ������
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
	 * @return �������: ����� �� ������ ���������� ������� ����
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
		//��� ����������� ����� ������������ ��� ���� ��: 38 � 99. ����� ���������� ���� ����� ���������������� ��� ���� ��
		return clientRegion.equals(cardRegion)
				|| (clientRegion.equals(MOSCOW_TB_38) && cardRegion.equals(MOSCOW_TB_99))
				|| (clientRegion.equals(MOSCOW_TB_99) && cardRegion.equals(MOSCOW_TB_38));
	}

	/**
	 * ������ ������� ������ ���� � 16 ������
	 * @param firstUserEnter16Release - �������
	 */
	public static void setFirstUserEnter16Release(final boolean firstUserEnter16Release)
	{
		if (PersonContext.isAvailable())
			ConfigFactory.getConfig(UserPropertiesConfig.class).setFirstUserEnter16Release(firstUserEnter16Release);
		else
		{
			try
			{
				//���� �������� ������� ��� �� ��������
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
	 * @return ������� ������ ���� � 16 ������
	 */
	public static boolean isFirstUserEnter16Release()
	{
		if (PersonContext.isAvailable())
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isFirstUserEnter16Release();
		else
		{
			try
			{
				//���� �������� ������� ��� �� ��������
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
	 * ������ ������� ������ ������� ��� ������ ������ �������� ������
	 * @param showMoreSbolNovelty - �������
	 */
	public static void setShowMoreSbolNovelty(boolean showMoreSbolNovelty)
	{
		UserPropertiesConfig config = ConfigFactory.getConfig(UserPropertiesConfig.class);
		if  (config.isShowMoreSbolNovelty() != showMoreSbolNovelty)
			config.setShowMoreSbolNovelty(showMoreSbolNovelty);
	}

	/**
	 * @return ������� ������ ������� ��� ������ ������ �������� ������
	 */
	public static boolean isShowMoreSbolNovelty()
	{
		if (PersonContext.isAvailable())
			return ConfigFactory.getConfig(UserPropertiesConfig.class).isShowMoreSbolNovelty();
		else
		{
			try
			{
				//���� �������� ������� ��� �� ��������
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
	 * @return ������� ������� �������
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
				log.error("������ ��� ��������� �������", e);
				return null;
			}
		}
	}

	/**
	 * @return ������� ���������� ������ � ���� ��� ������ �� ���������� ����
	 */
	public static boolean isWorkWithoutUDBO()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).isWorkWithoutUDBO();
	}

	/**
	 * @return ��������� ��������� ��� ������ �� ����������� ����
	 */
	public static String getCancelConnectUDBOMessageTitle()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getCancelConnectUDBOMessageTitle();
	}

	/**
	 * @return ����� ��������� ��� ������ �� ����������� ����
	 */
	public static String getCancelConnectUDBOMessageText()
	{
		return ConfigFactory.getConfig(RemoteConnectionUDBOConfig.class).getCancelConnectUDBOMessageText();
	}

	/**
	 * ������� ������ �� ������, ���� �� ��������� �� ������ ���.
	 *
	 * @return ��������� ����������� ����
	 */
	public static RemoteConnectionUDBOHelper getFromSession()
	{
		Store store = StoreManager.getCurrentStore();
		if (store == null)
			throw new IllegalArgumentException("��� ������.");

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
	 * @return ��������� �� ������ �������� ������
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
				//���� ��������� � ��������� ����� �� ������ �������� ����� ���� ������� ������������������
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
			log.error("������ �� ����� ��������� ���� ���������� ����������� ����", e);
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
	 * @return ���������� ������ ����������� ����. ��������� ��������� �� ��������
	 */
	public static boolean isShowOnlyConnectionUDBO()
	{
		return isShowMoreSbolMenuItem() && !isWorkWithoutUDBO();
	}

	/**
	 * @return ������� ������������� ������ ������ �������� ���� "������ �������� ������"
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
			log.error("������ ��� ����������� ������������� ������ ����������� ���������� ����", e);
			return false;
		}
		return true;
	}

	/**
	 * ������ ������� �������� �� ����� ���������� ����
	 * @param redirectToPayment - �������
	 */
	public void setRedirectToPayment(boolean redirectToPayment)
	{
		this.redirectToPayment = redirectToPayment;
	}

	/**
	 * @return ������� �� ����� ���������� ����
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
