package com.rssl.phizic.business.loanOffer;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 23.05.2011
 * Time: 18:18:16
 *
 * Заявка на  предодобренный кредит
 */
public class LoanOffer
{
	/**
	 * Уникальный код
	 */
	private Long id;
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
	 * Срок на который вадыется кредит(максимальный) в месецах
	 */
	private Long  duration;
  /**
   * Максимальная сумма кредитного лимита
   */
	private Money maxLimit;

	/**
	 * Признак того что продукт показан
     */
	private boolean  isViewed;

	/**
	 * Имя кредитного продукта
 	 */
	private String productName;

	/**
	 * Процентрная ставка
	 */
	private Double percentRate;

	/**
	 * Номер паспорта
	 */
	private String pasportNumber;

	/**
	 * Серия паспорта
	 */
	private String pasportSeries;

	/**
	 * Номер тербанка
	 */
	private Long terbank;

	/**
	 * суммы сроки
	 */
	private Long month6;

	private Long month12;

	private Long month18;

	private Long month24;

	private Long month36;

	private Long month48;

	private Long month60;

	/**
	 * Код продукта.
	 */
	private String  productCode;
	/**
	 * Код субпродукта.
	 */
	private String subProductCode;
	/**
	 * Код типа.
	 */
	private String productTypeCode;
	/**
	 * Дата окончания действия предложения
	 */
	private Calendar endDate;

	/**
	 * Идентификатор участника кампании
	 */
	private String campaignMemberId;

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Money getMaxLimit()
	{
		return maxLimit;
	}

	public void setMaxLimit(Money maxLimit)
	{
		this.maxLimit = maxLimit;
	}

	public Long getId ()
	{
		return id;
	}

	public void setId ( Long id )
	{
		this.id = id;
	}


	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
		this.duration = duration;
	}


	public boolean isIsViewed()
	{
		return isViewed;
	}

	public void setIsViewed(boolean viewed)
	{
		isViewed = viewed;
	}

	public String getProductName()
	{
		return productName;
	}

	public void setProductName(String productName)
	{
		this.productName = productName;
	}

	public Double getPercentRate()
	{
		return percentRate;
	}

	public void setPercentRate(Double percentRate)
	{
		this.percentRate = percentRate;
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

	public String getPasportNumber()
	{
		return pasportNumber;
	}

	public void setPasportNumber(String pasportNumber)
	{
		this.pasportNumber = pasportNumber;
	}

	public String getPasportSeries()
	{
		return pasportSeries;
	}

	public void setPasportSeries(String pasportSeries)
	{
		this.pasportSeries = pasportSeries;
	}

	public Long getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Long terbank)
	{
		this.terbank = terbank;
	}

	public Long getMonth6()
	{
		return month6;
	}

	public void setMonth6(Long month6)
	{
		this.month6 = month6;
	}

	public Long getMonth12()
	{
		return month12;
	}

	public void setMonth12(Long month12)
	{
		this.month12 = month12;
	}

	public Long getMonth18()
	{
		return month18;
	}

	public void setMonth18(Long month18)
	{
		this.month18 = month18;
	}

	public Long getMonth24()
	{
		return month24;
	}

	public void setMonth24(Long month24)
	{
		this.month24 = month24;
	}

	public Long getMonth36()
	{
		return month36;
	}

	public void setMonth36(Long month36)
	{
		this.month36 = month36;
	}

	public Long getMonth48()
	{
		return month48;
	}

	public void setMonth48(Long month48)
	{
		this.month48 = month48;
	}

	public Long getMonth60()
	{
		return month60;
	}

	public void setMonth60(Long month60)
	{
		this.month60 = month60;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getSubProductCode()
	{
		return subProductCode;
	}

	public void setSubProductCode(String subProductCode)
	{
		this.subProductCode = subProductCode;
	}

	public String getProductTypeCode()
	{
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode)
	{
		this.productTypeCode = productTypeCode;
	}

	public String getCampaignMemberId()
	{
		return campaignMemberId;
	}

	public void setCampaignMemberId(String campaignMemberId)
	{
		this.campaignMemberId = campaignMemberId;
	}
}
