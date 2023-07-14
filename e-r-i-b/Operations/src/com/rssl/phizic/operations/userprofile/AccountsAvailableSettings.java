package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @ author: filimonova
 * @ created: 23.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class AccountsAvailableSettings extends Settings
{
	private Map<String, String> settings = new HashMap<String, String>();

	private String availableProducts;//подключенные продукты
	private String unavailableProducts;//отключенные продукты

	private String viewType;
	private static  String SHOW_IN_SYSTEM = "showInSystem";
	private static  String SHOW_IN_MOBILE = "showInMobile";
	private static  String SHOW_IN_SOCIAL = "showInSocial";
	private static  String SHOW_IN_ES= "showInES";
	private static  String SHOW_IN_ERMB= "showInErmb";
	private static  String ERMB_NOTIFICATION = "ermbNotification";
	private static final String SEPARATOR = "; ";

	/**
	 * измененные признака отбражения продуктов в системе
	 * @param accounts - список счетов клиента
	 * @param cards  - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param depo - список счетов депо клиента
	 * @param ima  - список металлических счетов клиента
	 * @param securityAccountLinks  - список сберегательных сертификатов клиента
	 * @param pfrLink - карточка ПФР клиента
	 */
	public AccountsAvailableSettings(List<AccountLink> accounts,
	                                 List<CardLink> cards, List<LoanLink> loans,
	                                 List<DepoAccountLink> depo, List<IMAccountLink> ima,List<SecurityAccountLink> securityAccountLinks, PFRLink pfrLink)
	{
		List<Pair<String, Boolean>> availableSettingsInSystem = new ArrayList<Pair<String, Boolean>>(); //измененные признака отбражения продуктов в системе

		if (accounts != null)
			for (AccountLink link: accounts)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSystem()));
			}

		if (cards != null)
			for (CardLink link: cards)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
							getResourceName(link.getName(), MaskUtil.getCutCardNumber(link.getNumber())),
							link.getShowInSystem())
				);
			}

		if (loans != null)
			for (LoanLink link: loans)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSystem())
				);
			}


		if (depo != null)
			for (DepoAccountLink link: depo)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getAccountNumber()),
						link.getShowInSystem()));
			}

		if (ima != null)
			for (IMAccountLink link: ima)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSystem()));
			}
		if (securityAccountLinks != null)
			for (SecurityAccountLink link: securityAccountLinks)
			{
				availableSettingsInSystem.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSystem()));
			}

		if (pfrLink != null)
			availableSettingsInSystem.add(new Pair<String, Boolean>(
					pfrLink.getName(),
					pfrLink.getShowInSystem()));

		availableProducts= getAvailableProductsString(availableSettingsInSystem, Boolean.TRUE);
		unavailableProducts = getAvailableProductsString(availableSettingsInSystem, Boolean.FALSE);
		viewType = SHOW_IN_SYSTEM;
		settings.putAll(getMap(availableSettingsInSystem));
	}

	/**
	 * изменение признака отбражения продуктов в УС/мобильных устройствах
	 * @param accounts - список счетов клиента
	 * @param cards - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param ima - список ОМС клиента
     * @param type - тип устройсива
	 */
    public AccountsAvailableSettings(List<AccountLink> accounts, List<CardLink> cards, List<LoanLink> loans, List<IMAccountLink> ima, DeviceType type)
	{

        switch (type) {
            case ATM:
			    accountsAvailableSettingsInES(accounts, cards, loans, ima);
                break;
            case MOBILE:
                accountsAvailableSettingsInMobile(accounts, cards, loans, ima);
                break;
            case SOCIAL:
                accountsAvailableSettingsInSocial(accounts, cards, loans, ima);
                break;
        }
    }

	/**
	 * изменение признака отбражения продуктов в iPhone/iPad
	 * @param accounts - список счетов клиента
	 * @param cards - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param ima - список ОМС клиента
	 */
	private void accountsAvailableSettingsInMobile(List<AccountLink> accounts, List<CardLink> cards, List<LoanLink> loans, List<IMAccountLink> ima)
	{
		List<Pair<String, Boolean>> availableSettingsInMobile = new ArrayList<Pair<String, Boolean>>();   //измененные признака отбражения продуктов в iPhone/iPad

		if (accounts != null)
			for (AccountLink link: accounts)
			{
				availableSettingsInMobile.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInMobile())
				);
			}

		if (cards != null)
			for (CardLink link: cards)
			{
				availableSettingsInMobile.add(new Pair<String, Boolean>(
							getResourceName(link.getName(), MaskUtil.getCutCardNumber(link.getNumber())),
							link.getShowInMobile())
				);
			}

		if (loans != null)
			for (LoanLink link: loans)
			{
				availableSettingsInMobile.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInMobile())
				);
			}

		if (ima != null)
			for (IMAccountLink link: ima)
			{
				availableSettingsInMobile.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInMobile())
				);
			}

		availableProducts = getAvailableProductsString(availableSettingsInMobile, Boolean.TRUE);
		unavailableProducts = getAvailableProductsString(availableSettingsInMobile, Boolean.FALSE);
		viewType = SHOW_IN_MOBILE;
		settings.putAll(getMap(availableSettingsInMobile));
	}

    /**
	 * изменение признака отбражения продуктов в odnoklas/vkontakte/facebook
	 * @param accounts - список счетов клиента
	 * @param cards - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param ima - список ОМС клиента
	 */
	private void accountsAvailableSettingsInSocial(List<AccountLink> accounts, List<CardLink> cards, List<LoanLink> loans, List<IMAccountLink> ima)
	{
		List<Pair<String, Boolean>> availableSettingsInSocial = new ArrayList<Pair<String, Boolean>>();   //измененные признака отбражения продуктов в iPhone/iPad

		if (accounts != null)
			for (AccountLink link: accounts)
			{
                availableSettingsInSocial.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSocial())
				);
			}

		if (cards != null)
			for (CardLink link: cards)
			{
                availableSettingsInSocial.add(new Pair<String, Boolean>(
							getResourceName(link.getName(), MaskUtil.getCutCardNumber(link.getNumber())),
							link.getShowInSocial())
				);
			}

		if (loans != null)
			for (LoanLink link: loans)
			{
                availableSettingsInSocial.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSocial())
				);
			}

		if (ima != null)
			for (IMAccountLink link: ima)
			{
                availableSettingsInSocial.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						link.getShowInSocial())
				);
			}

		availableProducts = getAvailableProductsString(availableSettingsInSocial, Boolean.TRUE);
		unavailableProducts = getAvailableProductsString(availableSettingsInSocial, Boolean.FALSE);
		viewType = SHOW_IN_SOCIAL;
		settings.putAll(getMap(availableSettingsInSocial));
	}

	/**
	 * измененные признака отбражения продуктов в УС
	 * @param accounts - список счетов клиента
	 * @param cards - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param ima - список ОМС клиента
	 */
	private void accountsAvailableSettingsInES(List<AccountLink> accounts, List<CardLink> cards, List<LoanLink> loans, List<IMAccountLink> ima)
	{
		List<Pair<String, Boolean>> availableSettingsInES = new ArrayList<Pair<String, Boolean>>();   //измененные признака отбражения продуктов в УС

		if (accounts != null)
			for (AccountLink link: accounts)
			{
				availableSettingsInES.add(new Pair<String, Boolean>(
					getResourceName(link.getName(), link.getNumber()),
					link.getShowInATM())
				);
			}
		if (cards != null)
			for (CardLink link: cards)
			{
				availableSettingsInES.add(new Pair<String, Boolean>(
					getResourceName(link.getName(), MaskUtil.getCutCardNumber(link.getNumber())),
					link.getShowInATM())
				);
			}
		if (loans != null)
			for (LoanLink link: loans)
			{
				availableSettingsInES.add(new Pair<String, Boolean>(
					getResourceName(link.getName(), link.getNumber()),
					link.getShowInATM())
				);
			}
		if (ima != null)
			for (IMAccountLink link: ima)
			{
				availableSettingsInES.add(new Pair<String, Boolean>(
					getResourceName(link.getName(), link.getNumber()),
					link.getShowInATM())
				);
			}

		availableProducts = getAvailableProductsString(availableSettingsInES, Boolean.TRUE);
		unavailableProducts = getAvailableProductsString(availableSettingsInES, Boolean.FALSE);
		viewType = SHOW_IN_ES;
		settings.putAll(getMap(availableSettingsInES));
	}

	/**
	 * изменение признака отбражения продуктов в смс-канале и признака уведомлений
	 * @param accounts - список счетов клиента
	 * @param cards - список карт клиента
	 * @param loans - список кредитов клиента
	 * @param newProductsSetting - настройка по новым продуктам (null, если не изменилась)
	 * @param notificationSettings - true = изменение признака уведомлений, false = изменение признака отображения продукта
	 */
	public AccountsAvailableSettings(List<AccountLink> accounts, List<CardLink> cards, List<LoanLink> loans, Boolean newProductsSetting, boolean notificationSettings)
	{
		List<Pair<String, Boolean>> settings = new ArrayList<Pair<String, Boolean>>();
		boolean setting;
		if (accounts != null)
			for (AccountLink link: accounts)
			{
				if (notificationSettings)
					setting = link.getErmbNotification();
				else
					setting = link.getShowInSms();

				settings.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						setting)
				);
			}
		if (cards != null)
			for (CardLink link: cards)
			{
				if (notificationSettings)
					setting = link.getErmbNotification();
				else
					setting = link.getShowInSms();
				settings.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), MaskUtil.getCutCardNumber(link.getNumber())),
						setting)
				);
			}
		if (loans != null)
			for (LoanLink link: loans)
			{
				if (notificationSettings)
					setting = link.getErmbNotification();
				else
					setting = link.getShowInSms();
				settings.add(new Pair<String, Boolean>(
						getResourceName(link.getName(), link.getNumber()),
						setting)
				);
			}

		if (newProductsSetting != null)
		{
			if (notificationSettings)
				settings.add(new Pair<String, Boolean>("SMS-оповещения для новых продуктов", newProductsSetting));
			else
				settings.add(new Pair<String, Boolean>("Доступность в SMS-канале для новых продуктов", newProductsSetting));
		}

		availableProducts = getAvailableProductsString(settings, Boolean.TRUE);
		unavailableProducts = getAvailableProductsString(settings, Boolean.FALSE);
		if (notificationSettings)
			viewType = ERMB_NOTIFICATION;
		else viewType = SHOW_IN_ERMB;
		this.settings.putAll(getMap(settings));
	}

	public String getViewType()
	{
		return viewType;
	}

	public void setViewType(String viewType)
	{
		this.viewType = viewType;
	}

	private String getResourceName(String name, String number)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(StringHelper.getEmptyIfNull(name));
		if (builder.length() > 0)
			builder.append(" ");
		builder.append(number);
		return builder.toString();
	}

	private Map<String, String> getMap(List<Pair<String, Boolean>> pairs)
	{
		Map<String, String> result = new HashMap<String, String>();
		for (Pair<String, Boolean> p : pairs)
			result.put(p.getFirst(), p.getSecond().toString());
		return result;
	}

	protected Map<String, String> getSettings()
	{
		return Collections.unmodifiableMap(settings);
	}

	private String getAvailableProductsString(List<Pair<String, Boolean>> availableSettings, Boolean equalsValue)
	{
		StringBuilder builder = new StringBuilder();
		int i=0;
		for(Pair<String, Boolean> pair: availableSettings)
			if (pair.getSecond().equals(equalsValue))
			{
				builder.append(pair.getFirst());
				builder.append(SEPARATOR);
			}
		if (builder.length() > 0)
			builder.delete(builder.lastIndexOf(SEPARATOR), builder.length());
		return builder.toString();
	}

	/**
	 * @return список измененных и отображаемых продуктов
	 */
	public String getAvailableProducts()
	{
		return availableProducts;
	}

	/**
	 * @return список измененных и неотображаемых продуктов
	 */
	public String getUnavailableProducts()
	{
		return unavailableProducts;
	}
}
