package com.rssl.ikfl.crediting;

import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.phizic.business.offers.OfferType;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 20.01.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanCardOfferVersion2 implements LoanCardOffer
{
	/**
	 * Идентификатор предложения
	 */
	long id;

	/**
	 * Имя
	 */
	private String   firstName;
	/**
	 * фамилия
	 */
	private String   surName;
	/**
	 * Отчество
	 */
	private String   patrName;
	/**
	 * Дата рождения
	 */
	private Calendar birthDay;

	/**
	 * Серия и номер документа
	 */
	private String seriesAndNumber;

	/**
	 * Вид продукта [1]
	 */
	private OfferProductType productType;

	/**
	 * Номер тербанка
	 */
	private Long tb;

	/**
	 *Предложение использовано
	 */
	private boolean  offerUsed;

	/**
	 * Дата загрузки предложения [1]
	 */
	private Calendar loadDate;

	/**
	 * Приоритет предложения [1]
	 */
	private int priority;

	/**
	 * Текст персонализированного сообщения для клиента [0-1]
	 */
	private String personalText;

	/**
	 * Дата окончания действия предложения [1]
	 */
	private Calendar expDate;

	/**
	 * Максимальная сумма [1]
	 */
	private Money limitMax;

	/**
	 * Тип предложения на предодобреную кредитную карту
	 */
	private LoanCardOfferType offerType;

	public LoanCardOfferVersion2(Long id)
	{
		this.id = id;
	}

	public OfferId getOfferId()
	{
		return new OfferId(id, OfferType.OFFER_VERSION_2);
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getPatrName()
	{
		return patrName;
	}

	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
	}

	public String getSeriesAndNumber()
	{
		return seriesAndNumber;
	}

	public void setSeriesAndNumber(String seriesAndNumber)
	{
		this.seriesAndNumber = seriesAndNumber;
	}

	public Money getMaxLimit()
	{
		return limitMax;
	}

	public void setMaxLimit(Money maxLimit)
	{
		this.limitMax = maxLimit;
	}

	public Long getTerbank()
	{
		return tb;
	}

	public void setTerbank(Long terbank)
	{
		this.tb = terbank;
	}

	public String getCardNumber()
	{
		return null;
	}

	public LoanCardOfferType getOfferType()
	{
		return offerType;
	}

	public void setOfferType(LoanCardOfferType offerType)
	{
		this.offerType = offerType;
	}

	public String getNewCardType()
	{
		return null;
	}

	public Long getOsb()
	{
		return null;
	}

	public Long getVsp()
	{
		return null;
	}

	public boolean isOfferUsed()
	{
		return offerUsed;
	}

	public void setOfferUsed(boolean offerUsed)
	{
		this.offerUsed = offerUsed;
	}

	public Long getIdWay()
	{
		return null;
	}

	public int getPriority()
	{
		return priority;
	}

	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	public String getPersonalText()
	{
		return personalText;
	}

	public void setPersonalText(String personalText)
	{
		this.personalText = personalText;
	}

	public Calendar getLoadDate()
	{
		return loadDate;
	}

	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	public Calendar getExpDate()
	{
		return expDate;
	}

	public void setExpDate(Calendar expDate)
	{
		this.expDate = expDate;
	}
}
