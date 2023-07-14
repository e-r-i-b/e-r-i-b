package com.rssl.phizic.business.ext.sbrf.mobilebank;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.gate.mobilebank.MobileBankCardStatus;

import java.io.Serializable;
import java.util.ArrayList;
import static java.util.Collections.unmodifiableList;
import java.util.List;

/**
 * Бин с краткой информацией по карте
 * @author Erkin
 * @ created 01.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class CardShortcut implements Serializable
{
	private CardLink cardlink;

	/**
	 * Номер карты
	 */
	private String cardNumber;

	private MobileBankCardStatus cardStatus;

	/**
	 * Управляющие SMS-запросы по карте
	 */
	private List<SmsCommand> controlSmsRequests;

	/**
	 * SMS-шаблоны на оплату
	 */
	private List<SmsCommand> paymentSmsTemplates;

	/**
	 * SMS-запросы на оплату с карты
	 */
	private List<SmsCommand> paymentSmsRequests;

	/**
	 * Избранные SMS-запросы (для главной страницы)
	 * Здесь также содержатся SMS-запросы общие для услуги
	 * (заблокировать подключение, запросить список всех карт и т.п.)
	 * Изменение от 24.03.2011 (BUG026781)
	 */
	private List<SmsCommand> favoriteSmsRequests;

	///////////////////////////////////////////////////////////////////////////

	public CardLink getCardlink()
	{
		return cardlink;
	}

	public void setCardlink(CardLink cardlink)
	{
		this.cardlink = cardlink;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public MobileBankCardStatus getCardStatus()
	{
		return cardStatus;
	}

	public void setCardStatus(MobileBankCardStatus cardStatus)
	{
		this.cardStatus = cardStatus;
	}

	public List<SmsCommand> getControlSmsRequests()
	{
		if (controlSmsRequests == null)
			return null;
		else return unmodifiableList(controlSmsRequests);
	}

	public void setControlSmsRequests(List<SmsCommand> controlSmsRequests)
	{
		if (controlSmsRequests == null)
			this.controlSmsRequests = null;
		else this.controlSmsRequests = new ArrayList<SmsCommand>(controlSmsRequests);
	}

	public List<SmsCommand> getPaymentSmsTemplates()
	{
		if (paymentSmsTemplates == null)
			return null;
		else return unmodifiableList(paymentSmsTemplates);
	}

	public void setPaymentSmsTemplates(List<SmsCommand> paymentSmsTemplates)
	{
		if (paymentSmsTemplates == null)
			this.paymentSmsTemplates = null;
		else this.paymentSmsTemplates = new ArrayList<SmsCommand>(paymentSmsTemplates);
	}

	public List<SmsCommand> getPaymentSmsRequests()
	{
		if (paymentSmsRequests == null)
			return null;
		else return unmodifiableList(paymentSmsRequests);
	}

	public void setPaymentSmsRequests(List<SmsCommand> paymentSmsRequests)
	{
		if (paymentSmsRequests == null)
			this.paymentSmsRequests = null;
		else this.paymentSmsRequests = new ArrayList<SmsCommand>(paymentSmsRequests);
	}

	public List<SmsCommand> getFavoriteSmsRequests()
	{
		if (favoriteSmsRequests == null)
			return null;
		else return unmodifiableList(favoriteSmsRequests);
	}

	public void setFavoriteSmsRequests(List<SmsCommand> favoriteSmsRequests)
	{
		if (favoriteSmsRequests == null)
			this.favoriteSmsRequests = null;
		else this.favoriteSmsRequests = new ArrayList<SmsCommand>(favoriteSmsRequests);
	}
}