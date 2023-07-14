package com.rssl.phizic.web.ermb;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 * User: moshenko
 * Date: 08.10.2012
 * Time: 11:40:26
 * Форма Подключение/редактирование профиля ЕРМБ
 */
public class ErmbProfileConnectionForm extends EditFormBase
{
	private ErmbProfileImpl profile;
	private Long profileOldVersion;
	private Long personId;
	private List<ErmbTariff> tarifs;
	private String selectedTarif;
	private String mainCardId;
	private String mainPhoneNumberCode;
	private String ntfStartTimeString;
	private String ntfEndTimeString;
	private boolean fastServiceAvailable;
	private boolean depositsTransferAvailable;
	private boolean promotionalMailingAvailable;
	private long timeZone;
	private List<TimeZone> timeZoneList;
	private String[] ntfDays;
	private String[] cardsNtf;
	private String[] accountsNtf;
	private String[] loansNtf;
	private String[] cardsShowInSms;
	private String[] accountsShowInSms;
	private String[] loansShowInSms;
	private String[] cardsAvailableInSmsBank;
	private String[] accountsAvailableInSmsBank;
	private String[] loansAvailableInSmsBank;
	private boolean newProductsAvailableInSmsBank;
	private boolean newProductsShowInSms;
	private boolean newProductsNtf;
	private ActivePerson activePerson;
	private boolean modified = false;
	private String lockDescription;
	private boolean connect;
	//Мапа код телефона - маскированный номер телефона
	private Map<String, String> codesToPhoneNumber = new TreeMap<String, String>();

	//Мапа код телефона для удаления - маскированный номер телефона
	private Map<String, String> codePhonesToDelete = new LinkedHashMap<String, String>();
	//телефоны, которые нужно удалить из мбк при сохранении
	private String[] mbkPhones = new String[]{};

	//Время последнего запроса в mAPI
	private Calendar lastRequestTime;
	//Время последнего смс запроса
	private Calendar lastSmsRequestTime;

	public String getSelectedTarif()
	{
		return selectedTarif;
	}

	public void setSelectedTarif(String selectedTarif)
	{
		this.selectedTarif = selectedTarif;
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public void setProfile(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

	public Long getPersonId()
	{
		return personId;
	}

	public void setPersonId(Long personId)
	{
		this.personId = personId;
	}

	public List<ErmbTariff> getTarifs()
	{
		return tarifs;
	}

	public void setTarifs(List<ErmbTariff> tarifs)
	{
		this.tarifs = tarifs;
	}

	public String getMainCardId()
	{
		return mainCardId;
	}

	public void setMainCardId(String mainCardId)
	{
		this.mainCardId = mainCardId;
	}

	public String getMainPhoneNumberCode()
	{
		return mainPhoneNumberCode;
	}

	public void setMainPhoneNumberCode(String mainPhoneNumberCode)
	{
		this.mainPhoneNumberCode = mainPhoneNumberCode;
	}

	public boolean isFastServiceAvailable()
	{
		return fastServiceAvailable;
	}

	public void setFastServiceAvailable(boolean fastServiceAvailable)
	{
		this.fastServiceAvailable = fastServiceAvailable;
	}

	public String getNtfStartTimeString()
	{
		return ntfStartTimeString;
	}

	public void setNtfStartTimeString(String ntfStartTimeString)
	{
		this.ntfStartTimeString = ntfStartTimeString;
	}

	public String getNtfEndTimeString()
	{
		return ntfEndTimeString;
	}

	public void setNtfEndTimeString(String ntfEndTimeString)
	{
		this.ntfEndTimeString = ntfEndTimeString;
	}

	public long getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(long timeZone)
	{
		this.timeZone = timeZone;
	}

	public String[] getNtfDays()
	{
		return ntfDays;
	}

	public void setNtfDays(String[] days)
	{
		this. ntfDays = days;
	}

	public String[] getCardsNtf()
	{
		return cardsNtf;
	}

	public void setCardsNtf(String[] cardsNtf)
	{
		this.cardsNtf = cardsNtf;
	}

	public String[] getAccountsNtf()
	{
		return accountsNtf;
	}

	public void setAccountsNtf(String[] accountsNtf)
	{
		this.accountsNtf = accountsNtf;
	}

	public String[] getLoansNtf()
	{
		return loansNtf;
	}

	public void setLoansNtf(String[] loansNtf)
	{
		this.loansNtf = loansNtf;
	}

	public String[] getCardsShowInSms()
	{
		return cardsShowInSms;
	}

	public void setCardsShowInSms(String[] cardsShowInSms)
	{
		this.cardsShowInSms = cardsShowInSms;
	}

	public String[] getAccountsShowInSms()
	{
		return accountsShowInSms;
	}

	public void setAccountsShowInSms(String[] accountsShowInSms)
	{
		this.accountsShowInSms = accountsShowInSms;
	}

	public String[] getLoansShowInSms()
	{
		return loansShowInSms;
	}

	public void setLoansShowInSms(String[] loansShowInSms)
	{
		this.loansShowInSms = loansShowInSms;
	}

	public String[] getCardsAvailableInSmsBank()
	{
		return cardsAvailableInSmsBank;
	}

	public void setCardsAvailableInSmsBank(String[] cardsAvailableInSmsBank)
	{
		this.cardsAvailableInSmsBank = cardsAvailableInSmsBank;
	}

	public String[] getAccountsAvailableInSmsBank()
	{
		return accountsAvailableInSmsBank;
	}

	public void setAccountsAvailableInSmsBank(String[] accountsAvailableInSmsBank)
	{
		this.accountsAvailableInSmsBank = accountsAvailableInSmsBank;
	}

	public String[] getLoansAvailableInSmsBank()
	{
		return loansAvailableInSmsBank;
	}

	public void setLoansAvailableInSmsBank(String[] loansAvailableInSmsBank)
	{
		this.loansAvailableInSmsBank = loansAvailableInSmsBank;
	}

	public boolean isNewProductsAvailableInSmsBank()
	{
		return newProductsAvailableInSmsBank;
	}

	public void setNewProductsAvailableInSmsBank(boolean newProductsAvailableInSmsBank)
	{
		this.newProductsAvailableInSmsBank = newProductsAvailableInSmsBank;
	}

	public boolean isNewProductsShowInSms()
	{
		return newProductsShowInSms;
	}

	public void setNewProductsShowInSms(boolean newProductsShowInSms)
	{
		this.newProductsShowInSms = newProductsShowInSms;
	}

	public boolean isNewProductsNtf()
	{
		return newProductsNtf;
	}

	public void setNewProductsNtf(boolean newProductsNtf)
	{
		this.newProductsNtf = newProductsNtf;
	}

	public boolean isDepositsTransferAvailable()
	{
		return depositsTransferAvailable;
	}

	public void setDepositsTransferAvailable(boolean depositsTransferAvailable)
	{
		this.depositsTransferAvailable = depositsTransferAvailable;
	}

	public boolean isPromotionalMailingAvailable()
	{
		return promotionalMailingAvailable;
	}

	public void setPromotionalMailingAvailable(boolean promotionalMailingAvailable)
	{
		this.promotionalMailingAvailable = promotionalMailingAvailable;
	}

	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	public boolean isModified()
	{
		return modified;
	}

	public void setModified(boolean modified)
	{
		this.modified = modified;
	}

	public String getLockDescription()
	{
		return lockDescription;
	}

	public void setLockDescription(String lockDescription)
	{
		this.lockDescription = lockDescription;
	}

	public boolean getConnect()
	{
		return connect;
	}

	public void setConnect(boolean connect)
	{
		this.connect = connect;
	}

	public Map<String, String> getCodesToPhoneNumber()
	{
		return codesToPhoneNumber;
	}

	public void setCodesToPhoneNumber(Map<String, String> codesToPhoneNumber)
	{
		this.codesToPhoneNumber = codesToPhoneNumber;
	}

	public Map<String, String> getCodePhonesToDelete()
	{
		return codePhonesToDelete;
	}

	public void setCodePhonesToDelete(Map<String, String> codePhonesToDelete)
	{
		this.codePhonesToDelete = codePhonesToDelete;
	}

	public Long getProfileOldVersion()
	{
		return profileOldVersion;
	}

	public void setProfileOldVersion(Long profileOldVersion)
	{
		this.profileOldVersion = profileOldVersion;
	}

	public String[] getMbkPhones()
	{
		return mbkPhones;
	}

	public void setMbkPhones(String[] mbkPhones)
	{
		this.mbkPhones = mbkPhones;
	}

	public Calendar getLastRequestTime()
	{
		return lastRequestTime;
	}

	public void setLastRequestTime(Calendar lastRequestTime)
	{
		this.lastRequestTime = lastRequestTime;
	}

	public Calendar getLastSmsRequestTime()
	{
		return lastSmsRequestTime;
	}

	public void setLastSmsRequestTime(Calendar lastSmsRequestTime)
	{
		this.lastSmsRequestTime = lastSmsRequestTime;
	}

	public List<TimeZone> getTimeZoneList()
	{
		return timeZoneList;
	}

	public void setTimeZoneList(List<TimeZone> timeZoneList)
	{
		this.timeZoneList = timeZoneList;
	}
}
