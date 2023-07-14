package com.rssl.phizic.business.loanCardOffer;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * @author Rtischeva
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanCardOffer
{
	/**
	 * ���������� ���
	 */
	private Long id;

	/**
	 * ���
	 */
	private String   firstName;
	/**
	 * �������
	 */
    private String   surName;
	/**
	 * ��������
	 */
    private String   patrName;
	/**
	 * ���� ��������
	 */
    private Calendar birthDay;

	/**
	 * ����� ��������� �����
	 */
	private String cardNumber;
	/**
	 * ��� ����������� �� ������������� ��������� �����
	 */
	private LoanCardOfferType offerType;
	 /**
	  * ����� ���������� ������
      */
	private Money maxLimit;
	/**
	 * ��� ��������� �����
	 */
	private String newCardType;
	/**
	 * ������� ���� ��� ������� �������
     */
	private boolean  isViewed;

	/**
	 * ����� � ����� ���������
	 */
	private String seriesAndNumber;
	/**
	 * ID ������� � �� WAY4
	 */
	private Long  idWay;

	/**
	 * TB
	 */
	private Long  terbank;

	/**
	 * ���������
	 */
	private Long  osb;

	/**
	 * ���
	 */
	private Long  vsp;

	/**
	 * ���� �������� �����������
	 */
	private Calendar loadDate;

	/**
	 * ���� ��������� ������� ����������� (�� ���� ����-����� ����� �������, � ������� ������ ��� ��������� ��� ������ )
	 */
	private Calendar viewDate;
	/**
	 * ���� ������������ �������� �� �������� ���������� ������
	 */
	private Calendar transitionDate;
	/**
	 * ���� ���������� ������
	 */
	private Calendar registrationDate;
	/**
	 *����������� ������������
	 */
	private boolean  offerUsed;

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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public Calendar getTransitionDate()
	{
		return transitionDate;
	}

	public void setTransitionDate(Calendar transitionDate)
	{
		this.transitionDate = transitionDate;
	}

	public Calendar getRegistrationDate()
	{
		return registrationDate;
	}

	public void setRegistrationDate(Calendar registrationDate)
	{
		this.registrationDate = registrationDate;
	}

	public boolean isOfferUsed()
	{
		return offerUsed;
	}

	public void setOfferUsed(boolean offerUsed)
	{
		this.offerUsed = offerUsed;
	}
}
