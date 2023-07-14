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
 * �����������/�������������� ������� ����
 * ������ ��� ������� ������ �� ������ � �������� � �������.
 */
public class ErmbProfileOperationEntity
{
	private ErmbProfileImpl profile;

	private List<ErmbTariff> tarifs = new ArrayList<ErmbTariff>();
	private String mainCardId;
	private String selectedTarif;
	private List<String> cardsNotify; //id ����, �� ������� �������� ����������
	private List<String> accountsNotify; //id ������, �� ������� �������� ����������
	private List<String> loansNotify; //id ��������, �� ������� �������� ����������
	private List<String> cardsShowInSms; //id ����, ������� ������ � ���-������
	private List<String> accountsShowInSms; //id ������, ������� ������ � ���-������
	private List<String> loansShowInSms; //id ��������, ������� ������ � ���-������
	private List<String> cardsAvailableInSmsBank; //id ����, ������� �������� � ���-�����
	private List<String> accountsAvailableInSmsBank; //id ����, ������� �������� � ���-�����
	private List<String> loansAvailableInSmsBank; //id ����, ������� �������� � ���-�����
	private String[] ntfDays;//��� ������ �� ������� ����� ������������ �����������
	private Set<String> phonesNumber;
	private Set<String> phonesToDelete;//������ ��������� ��� ��������/������� �������� ���� �������������
	private Set<String> mbkPhones;//��������� �� ��� ��������

	private String mainPhoneNumber; //����� ��������� ��������
	private boolean connect;

	private Calendar lastRequestTime; //����� ���������� ������� � mAPI
	private Calendar lastSmsRequestTime; //����� ���������� ��� �������

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
