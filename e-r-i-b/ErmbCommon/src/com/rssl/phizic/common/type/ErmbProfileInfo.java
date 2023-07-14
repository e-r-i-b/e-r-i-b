package com.rssl.phizic.common.type;

import com.rssl.phizic.common.types.DaysOfWeek;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.person.ErmbProfile;
import com.rssl.phizic.gate.loans.Loan;

import java.math.BigInteger;
import java.sql.Time;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 @author: EgorovaA
 @ created: 24.12.2012
 @ $Author$
 @ $Revision$
 */

/**
 * Класс, использующийся для формирования сообщения об измененных профилях ЕРМБ
 */
public class ErmbProfileInfo implements ErmbProfile
{
	private Long id;
	private Long profileVersion;
	private Long confirmProfileVersion;
	private Set<Card> cards;
	private Set<Account> accounts;
	private Set<Loan> loans;
	private String mainPhoneNumber;
	private Set<String> phoneNumbers;
	//Список, содержащий информацию о продуктах, по которым требуется оправлять уведомления
	private List<Pair<String, String>> informProducts;
	//Мапа с номерами дополнительных карт и признаком, опр. находится ли карта в профиле владельца
	private Map<String, Boolean> additionalCadrs;
	private String serviceStatus;   //active, blocked, nonpayed, null - не подключен
	private boolean newProductNotification;
	private boolean suppressAdv;
	private boolean depositsTransfer;
	private boolean isUDBO;
	//Идентификационные данные клиента
	private ErmbProfileIdentity identity;
	//Устаревшие идентификационные данные клиента
	private List<ErmbProfileIdentity> oldIdentityList;
	//Время начала периода отправки уведомлений
	private Time notificationStartTime;
	//Время окончания периода отправки уведомлений
	private Time notificationEndTime;
	//Часовой пояс
	private Long timeZone;
	//Дни недели, в которые отправляются уведомления
	private DaysOfWeek daysOfWeek;
	//Признак транслитерации сообщения
	private boolean transliterateSms;
	//Категория клиента
	private BigInteger clientCategory;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getMainPhoneNumber()
	{
		return mainPhoneNumber;
	}

	public void setMainPhoneNumber(String mainPhoneNumber)
	{
		this.mainPhoneNumber = mainPhoneNumber;
	}

	public Set<String> getPhoneNumbers()
	{
		return phoneNumbers;
	}

	public void setPhoneNumbers(Set<String> phoneNumbers)
	{
		this.phoneNumbers = phoneNumbers;
	}

	public Long getProfileVersion()
	{
		return profileVersion;
	}

	public void setProfileVersion(Long profileVersion)
	{
		this.profileVersion = profileVersion;
	}

	public Long getConfirmProfileVersion()
	{
		return confirmProfileVersion;
	}

	public void setConfirmProfileVersion(Long confirmProfileVersion)
	{
		this.confirmProfileVersion = confirmProfileVersion;
	}

	public Set<Card> getCards()
	{
		return cards;
	}

	public void setCards(Set<Card> cards)
	{
		this.cards = cards;
	}

	public Set<Account> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(Set<Account> accounts)
	{
		this.accounts = accounts;
	}

	public Set<Loan> getLoans()
	{
		return loans;
	}

	public void setLoans(Set<Loan> loans)
	{
		this.loans = loans;
	}

	public List<Pair<String, String>> getInformProducts()
	{
		return informProducts;
	}

	public void setInformProducts(List<Pair<String, String>> informProducts)
	{
		this.informProducts = informProducts;
	}

	public ErmbProfileIdentity getIdentity()
	{
		return identity;
	}

	public void setIdentity(ErmbProfileIdentity identity)
	{
		this.identity = identity;
	}

	public List<ErmbProfileIdentity> getOldIdentityList()
	{
		return oldIdentityList;
	}

	public void setOldIdentityList(List<ErmbProfileIdentity> oldIdentityList)
	{
		this.oldIdentityList = oldIdentityList;
	}

	public boolean isServiceStatus()
	{
		return serviceStatus != null;
	}

	public String getServiceStatus()
	{
		return serviceStatus;
	}

	public void setServiceStatus(String serviceStatus)
	{
		this.serviceStatus = serviceStatus;
	}

	public boolean getNewProductNotification()
	{
		return newProductNotification;
	}

	public void setNewProductNotification(boolean newProductNotification)
	{
		this.newProductNotification = newProductNotification;
	}

	public boolean isSuppressAdv()
	{
		return suppressAdv;
	}

	public void setSuppressAdv(boolean suppressAdv)
	{
		this.suppressAdv = suppressAdv;
	}

	public boolean getDepositsTransfer()
	{
		return depositsTransfer;
	}

	public void setDepositsTransfer(boolean depositsTransfer)
	{
		this.depositsTransfer = depositsTransfer;
	}

	public boolean isUDBO()
	{
		return isUDBO;
	}

	public void setUDBO(boolean UDBO)
	{
		isUDBO = UDBO;
	}

	public Time getNotificationStartTime()
	{
		return notificationStartTime;
	}

	public void setNotificationStartTime(Time notificationStartTime)
	{
		this.notificationStartTime = notificationStartTime;
	}

	public Time getNotificationEndTime()
	{
		return notificationEndTime;
	}

	public void setNotificationEndTime(Time notificationEndTime)
	{
		this.notificationEndTime = notificationEndTime;
	}

	public Long getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(Long timeZone)
	{
		this.timeZone = timeZone;
	}

	public DaysOfWeek getDaysOfWeek()
	{
		return daysOfWeek;
	}

	public void setDaysOfWeek(DaysOfWeek daysOfWeek)
	{
		this.daysOfWeek = daysOfWeek;
	}

	public Map<String, Boolean> getAdditionalCadrs()
	{
		return additionalCadrs;
	}

	public void setAdditionalCadrs(Map<String, Boolean> additionalCadrs)
	{
		this.additionalCadrs = additionalCadrs;
	}

	public boolean getTransliterateSms()
	{
		return transliterateSms;
	}

	public void setTransliterateSms(boolean transliterateSms)
	{
		this.transliterateSms = transliterateSms;
	}

	public BigInteger getClientCategory()
	{
		return clientCategory;
	}

	public void setClientCategory(BigInteger clientCategory)
	{
		this.clientCategory = clientCategory;
	}
}
