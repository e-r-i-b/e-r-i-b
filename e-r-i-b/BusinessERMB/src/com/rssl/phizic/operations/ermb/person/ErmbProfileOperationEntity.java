package com.rssl.phizic.operations.ermb.person;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * User: moshenko
 * Date: 10.10.12
 * Time: 11:41
 * Подключение/редактирование профиля ЕРМБ
 * Обёртка для проноса данных из экшена в операцию и обратно.
 */
public class ErmbProfileOperationEntity
{
	private ErmbProfileImpl profile;

	private List<ErmbTariff> tarifs = new ArrayList<ErmbTariff>();
	private String mainCardId;
	private String selectedTarif;
	private List<String> cardsNotify; //id карт, по которым доступны оповещения
	private List<String> accountsNotify; //id счетов, по которым доступны оповещения
	private List<String> loansNotify; //id кредитов, по которым доступны оповещения
	private List<String> cardsShowInSms; //id карт, которые видимы в смс-канале
	private List<String> accountsShowInSms; //id счетов, которые видимы в смс-канале
	private List<String> loansShowInSms; //id кредитов, которые видимы в смс-канале
	private List<String> cardsAvailableInSmsBank; //id карт, которые доступны в смс-банке
	private List<String> accountsAvailableInSmsBank; //id карт, которые доступны в смс-банке
	private List<String> loansAvailableInSmsBank; //id карт, которые доступны в смс-банке
	private String[] ntfDays;//дни недели по которым будут производится уведомления
	private Set<String> phonesNumber;
	private Set<String> phonesToDelete;//номера телефонов для удаления/признак отправки кода подтверждения
	private Set<String> mbkPhones;//удаляемые из мбк телефоны

	private String mainPhoneNumber; //номер основного телефона
	private boolean connect;

	private Calendar lastRequestTime; //Время последнего запроса в mAPI
	private Calendar lastSmsRequestTime; //Время последнего смс запроса

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public void setProfile(ErmbProfileImpl profile)
	{
		this.profile = profile;
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

	public String getSelectedTarif()
	{
		return selectedTarif;
	}

	public void setSelectedTarif(String selectedTarif)
	{
		this.selectedTarif = selectedTarif;
	}

	public List<String> getCardsNotify()
	{
		return cardsNotify;
	}

	public void setCardsNotify(List<String> cardsNotify)
	{
		this.cardsNotify = cardsNotify;
	}

	public List<String> getAccountsNotify()
	{
		return accountsNotify;
	}

	public void setAccountsNotify(List<String> accountsNotify)
	{
		this.accountsNotify = accountsNotify;
	}

	public List<String> getLoansNotify()
	{
		return loansNotify;
	}

	public void setLoansNotify(List<String> loansNotify)
	{
		this.loansNotify = loansNotify;
	}

	public List<String> getCardsShowInSms()
	{
		return cardsShowInSms;
	}

	public void setCardsShowInSms(List<String> cardsShowInSms)
	{
		this.cardsShowInSms = cardsShowInSms;
	}

	public List<String> getAccountsShowInSms()
	{
		return accountsShowInSms;
	}

	public void setAccountsShowInSms(List<String> accountsShowInSms)
	{
		this.accountsShowInSms = accountsShowInSms;
	}

	public List<String> getLoansShowInSms()
	{
		return loansShowInSms;
	}

	public void setLoansShowInSms(List<String> loansShowInSms)
	{
		this.loansShowInSms = loansShowInSms;
	}

	public List<String> getCardsAvailableInSmsBank()
	{
		return cardsAvailableInSmsBank;
	}

	public void setCardsAvailableInSmsBank(List<String> cardsAvailableInSmsBank)
	{
		this.cardsAvailableInSmsBank = cardsAvailableInSmsBank;
	}

	public List<String> getAccountsAvailableInSmsBank()
	{
		return accountsAvailableInSmsBank;
	}

	public void setAccountsAvailableInSmsBank(List<String> accountsAvailableInSmsBank)
	{
		this.accountsAvailableInSmsBank = accountsAvailableInSmsBank;
	}

	public List<String> getLoansAvailableInSmsBank()
	{
		return loansAvailableInSmsBank;
	}

	public void setLoansAvailableInSmsBank(List<String> loansAvailableInSmsBank)
	{
		this.loansAvailableInSmsBank = loansAvailableInSmsBank;
	}

	public String[] getNtfDays()
	{
		return ntfDays;
	}

	public void setNtfDays(String[] ntfDays)
	{
		this.ntfDays = ntfDays;
	}

	public Set<String> getPhonesNumber()
	{
		return phonesNumber;
	}

	public void setPhonesNumber(Set<String> phonesNumber)
	{
		this.phonesNumber = phonesNumber;
	}

	public String getMainPhoneNumber()
	{
		return mainPhoneNumber;
	}

	public void setMainPhoneNumber(String mainPhoneNumber)
	{
		this.mainPhoneNumber = mainPhoneNumber;
	}

	public boolean isConnect()
	{
		return connect;
	}

	public void setConnect(boolean connect)
	{
		this.connect = connect;
	}

	public Set<String> getPhonesToDelete()
	{
		return phonesToDelete;
	}

	public void setPhonesToDelete(Set<String> phonesToDelete)
	{
		this.phonesToDelete = phonesToDelete;
	}

	public Set<String> getMbkPhones()
	{
		return mbkPhones;
	}

	public void setMbkPhones(Set<String> mbkPhones)
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
}
