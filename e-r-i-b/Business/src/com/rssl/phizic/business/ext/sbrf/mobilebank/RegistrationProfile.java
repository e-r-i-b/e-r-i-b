package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.gate.mobilebank.MobileBankRegistrationStatus;
import com.rssl.phizic.gate.mobilebank.MobileBankTariff;
import com.rssl.phizic.gate.mobilebank.QuickServiceStatusCode;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Бин "подключение"
 * @author Erkin
 * @ created 01.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationProfile implements Serializable, ConfirmableObject
{
	/**
	 * Номер управляющего телефона
	 */
	private String mainPhoneNumber;

	/**
	 * Статус услуги: активна или приостановлена
	 */
	private MobileBankRegistrationStatus status;

	private MobileBankTariff tariff;

	/**
	 * Управляющая (платёжная) карта
	 */
	private CardShortcut mainCard;

	/**
	 * Признак отображения номера карты в списке SMS-шаблонов на оплату
	 */
	private boolean showCardNumberInSms = false;

	/**
	 * Информационные карты
	 */
	private List<CardShortcut> infoCards;

	/**
	 * Статус Быстрых сервисов для данного профайла
	 */
	private QuickServiceStatusCode quickServiceStatusCode;

	///////////////////////////////////////////////////////////////////////////

	public String getMainPhoneNumber()
	{
		return mainPhoneNumber;
	}

	public void setMainPhoneNumber(String mainPhoneNumber)
	{
		this.mainPhoneNumber = mainPhoneNumber;
	}

	public MobileBankRegistrationStatus getStatus()
	{
		return status;
	}

	public void setStatus(MobileBankRegistrationStatus status)
	{
		this.status = status;
	}

	public MobileBankTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MobileBankTariff tariff)
	{
		this.tariff = tariff;
	}

	public CardShortcut getMainCard()
	{
		return mainCard;
	}

	public void setMainCard(CardShortcut mainCard)
	{
		this.mainCard = mainCard;
	}

	public boolean isShowCardNumberInSms()
	{
		return showCardNumberInSms;
	}

	public void setShowCardNumberInSms(boolean showCardNumberInSms)
	{
		this.showCardNumberInSms = showCardNumberInSms;
	}

	public List<CardShortcut> getInfoCards()
	{
		if (infoCards == null)
			return null;
		else return unmodifiableList(infoCards);
	}

	public void setInfoCards(List<CardShortcut> infoCards)
	{
		if (infoCards == null)
			this.infoCards = null;
		else this.infoCards = new ArrayList<CardShortcut>(infoCards);
	}

	public QuickServiceStatusCode getQuickServiceStatusCode()
	{
		return quickServiceStatusCode;
	}

	public void setQuickServiceStatusCode(QuickServiceStatusCode quickServiceStatusCode)
	{
		this.quickServiceStatusCode = quickServiceStatusCode;
	}

	public Long getId()
	{
		return getMainCard().getCardlink().getId();
	}

	public byte[] getSignableObject()
	{
		return null;
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{

	}

}
