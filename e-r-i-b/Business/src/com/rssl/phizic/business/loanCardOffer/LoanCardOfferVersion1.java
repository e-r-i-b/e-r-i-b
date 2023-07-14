
package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.business.offers.LoanCardOffer;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.offers.OfferType;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 11:29:40
 * заявка на предодобренную кредитную карту
 */
public class LoanCardOfferVersion1 implements LoanCardOffer
{
	/**
	 * Уникальный код
	 */
	private OfferId offerId;

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
	 * Номер кредитной карты
	 */
	private String cardNumber;
	/**
	 * Тип предложения на предодобреную кредитную карту
	 */
	private LoanCardOfferType offerType;
	 /**
	  * Сумма кредитного лимита
      */
	private Money maxLimit;
	/**
	 * Тип кредитной карты
	 */
	private String newCardType;
	/**
	 * Признак того что продукт показан
     */
	private boolean  isViewed;

	/**
	 * Серия и номер документа
	 */
	private String seriesAndNumber;
	/**
	 * ID клиента в БД WAY4
	 */
	private Long  idWay;

	/**
	 * TB
	 */
	private Long  terbank;

	/**
	 * Отделение
	 */
	private Long  osb;

	/**
	 * ВСП
	 */
	private Long  vsp;

	/**
	 * Дата загрузки предложения
	 */
	private Calendar loadDate;

	/**
	 * Дата просмотра баннера предложения (То есть дата-время входа клиента, в который первый раз привязали эту заявку )
	 */
	private Calendar viewDate;
	/**
	 * Дата выполненного перехода на страницу оформления заявки
	 */
	private Calendar transitionDate;
	/**
	 * Дата оформления заявки
	 */
	private Calendar registrationDate;
	/**
	 *Предложение использовано
	 */
	private boolean  offerUsed;

	public LoanCardOfferVersion1(Long id)
	{
		this.offerId = new OfferId(id, OfferType.OFFER_VERSION_1);
	}

	public OfferId getOfferId()
	{
		return offerId;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
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
		return newCardType;
	}

	public void setNewCardType(String newCardType)
	{
		this.newCardType = newCardType;
	}

	public Calendar getBirthDay()
	{
		return birthDay;
	}

	public void setBirthDay(Calendar birthDay)
	{
		this.birthDay = birthDay;
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


	public Money getMaxLimit()
	{
		return maxLimit;
	}

	public void setMaxLimit(Money maxLimit)
	{
		this.maxLimit = maxLimit;
	}

	public boolean isIsViewed()
	{
		return isViewed;
	}

	public void setIsViewed(boolean viewed)
	{
		isViewed = viewed;
	}

	public Long getIdWay()
	{
		return idWay;
	}

	public void setIdWay(Long idWay)
	{
		this.idWay = idWay;
	}

	public Long getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Long terbank)
	{
		this.terbank = terbank;
	}

	public Long getOsb()
	{
		return osb;
	}

	public void setOsb(Long osb)
	{
		this.osb = osb;
	}

	public Long getVsp()
	{
		return vsp;
	}

	public void setVsp(Long vsp)
	{
		this.vsp = vsp;
	}

	public String getSeriesAndNumber()
	{
		return seriesAndNumber;
	}

	public void setSeriesAndNumber(String seriesAndNumber)
	{
		this.seriesAndNumber = seriesAndNumber;
	}

	public Calendar getLoadDate()
	{
		return loadDate;
	}

	public Calendar getExpDate()
	{
		return null;
	}

	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	public Calendar getViewDate()
	{
		return viewDate;
	}

	public void setViewDate(Calendar bannerViewDate)
	{
		this.viewDate = bannerViewDate;
	}

	public boolean isOfferUsed()
	{
		return offerUsed;
	}

	public void setOfferUsed(boolean offerUsed)
	{
		this.offerUsed = offerUsed;
	}

	public int getPriority()
	{
		return 0;
	}

}
