package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.auth.modes.ConfirmStrategy;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.common.type.TimeZone;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.*;

/**
 @author: Egorovaa
 @ created: 25.09.2012
 @ $Author$
 @ $Revision$
 */
public class ErmbListRegistrationsForm extends EditFormBase
{
	//ЕРМБ-профиль клиента
	private ErmbProfileImpl profile;
	// Карты клиента
	private List<CardLink> cards;
	//Вклады клиента
	private List<AccountLink> accounts;
	//Вклады клиента
	private List<LoanLink> loans;
	//Список активных тарифов
	private List<ErmbTariff> tarifs;
	//Выбранный тариф
	private String selectedTarif;
	//Дата окончания льготного периода (вычисляется исходя из даты подключения и льготного периода)
	private Calendar gracePeriodEndDate;
	//Время начала оповещения
	private String ntfStartTimeString;
	//Время окончания оповещения
	private String ntfEndTimeString;
	//Часовой пояс
	private long timeZone;
	//Часовые пояса
	private List<TimeZone> timeZoneList;
	//ID приоритетной для списания средств карты
	private String mainCardId;
	//Код главного моб. телефона
	private String mainPhoneNumberCode;
	//Дни для отправки уведомлений
	private String notificationDays;
	//Признак отправки уведомлений по операциям причисления процентов по счетам
	private boolean depositsTransfer;
	//Признак включения опции «быстрый платеж»
	private boolean fastServiceAvailable;
	//Мапа код телефона - маскированный номер телефона
	private Map<String, String> codesToPhoneNumber = new TreeMap<String, String>();
	// Карты, доступные для выбора в качестве платежной
	private List<CardLink> possiblePaymentCards;

	private ConfirmableObject confirmableObject;
    private ConfirmStrategy confirmStrategy;

	/////////////////////////////////////////////////////////////////////////////////////

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public List<AccountLink> getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List<AccountLink> accounts)
	{
		this.accounts = accounts;
	}

	public List<LoanLink> getLoans()
	{
		return loans;
	}

	public void setLoans(List<LoanLink> loans)
	{
		this.loans = loans;
	}

	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public void setProfile(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

	public Calendar getGracePeriodEndDate()
	{
		return gracePeriodEndDate;
	}

	public void setGracePeriodEndDate(Calendar gracePeriodEndDate)
	{
		this.gracePeriodEndDate = gracePeriodEndDate;
	}

	public List<ErmbTariff> getTarifs()
	{
		return tarifs;
	}

	public void setTarifs(List<ErmbTariff> tarifs)
	{
		this.tarifs = tarifs;
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

	public String getSelectedTarif()
	{
		return selectedTarif;
	}

	public void setSelectedTarif(String selectedTarif)
	{
		this.selectedTarif = selectedTarif;
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

	public String getNotificationDays()
	{
		return notificationDays;
	}

	public void setNotificationDays(String notificationDays)
	{
		this.notificationDays = notificationDays;
	}

	public boolean getDepositsTransfer()
	{
		return depositsTransfer;
	}

	public void setDepositsTransfer(boolean depositsTransfer)
	{
		this.depositsTransfer = depositsTransfer;
	}

	public boolean getFastServiceAvailable()
	{
		return fastServiceAvailable;
	}

	public void setFastServiceAvailable(boolean fastServiceAvailable)
	{
		this.fastServiceAvailable = fastServiceAvailable;
	}

	public List<TimeZone> getTimeZoneList()
	{
		return timeZoneList;
	}

	public void setTimeZoneList(List<TimeZone> timeZoneList)
	{
		this.timeZoneList = timeZoneList;
	}

	public ConfirmableObject getConfirmableObject()
	{
		return confirmableObject;
	}

	public void setConfirmableObject(ConfirmableObject confirmableObject)
	{
		this.confirmableObject = confirmableObject;
	}

	public ConfirmStrategy getConfirmStrategy()
	{
		return confirmStrategy;
	}

	public void setConfirmStrategy(ConfirmStrategy confirmStrategy)
	{
		this.confirmStrategy = confirmStrategy;
	}

	public Map<String, String> getCodesToPhoneNumber()
	{
		return codesToPhoneNumber;
	}

	public void setCodesToPhoneNumber(Map<String, String> codesToPhoneNumber)
	{
		this.codesToPhoneNumber = codesToPhoneNumber;
	}

	public List<CardLink> getPossiblePaymentCards()
	{
		return possiblePaymentCards;
	}

	public void setPossiblePaymentCards(List<CardLink> possiblePaymentCards)
	{
		this.possiblePaymentCards = possiblePaymentCards;
	}
}
