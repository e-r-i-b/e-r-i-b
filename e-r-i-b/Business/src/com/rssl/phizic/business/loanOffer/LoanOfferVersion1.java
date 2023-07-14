package com.rssl.phizic.business.loanOffer;

import com.rssl.ikfl.crediting.OfferCondition;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.ikfl.crediting.OfferTopUp;
import com.rssl.phizic.business.offers.LoanOffer;
import com.rssl.phizic.business.offers.OfferType;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author Rtischeva
 * @ created 27.01.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanOfferVersion1 implements LoanOffer
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
	 * Условия по предложению
	 */
	private List<OfferCondition> offerConditions;

	/**
	 * Идентификатор участника компании
     */
	private String campaignMemberId;

	public LoanOfferVersion1(Long id)
	{
		this.offerId = new OfferId(id, OfferType.OFFER_VERSION_1);
	}

	/**
	 * Номер паспорта
	 */
	private String passportNumber;

	/**
	 * Серия паспорта
	 */
	private String passportSeries;

	/**
	 * Номер тербанка
	 */
	private Long terbank;

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

	public OfferId getOfferId()
	{
		return offerId;
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
		return passportNumber;
	}

	public void setPasportNumber(String pasportNumber)
	{
		this.passportNumber = pasportNumber;
	}

	public String getPasportSeries()
	{
		return passportSeries;
	}

	public void setPasportSeries(String pasportSeries)
	{
		this.passportSeries = pasportSeries;
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
		this.isViewed = viewed;
	}

	public Long getTerbank()
	{
		return terbank;
	}

	public void setTerbank(Long terbank)
	{
		this.terbank = terbank;
	}

	public Calendar getEndDate()
	{
		return endDate;
	}

	public void setEndDate(Calendar endDate)
	{
		this.endDate = endDate;
	}

	public Long getDuration()
	{
		return duration;
	}

	public void setDuration(Long duration)
	{
	   this.duration = duration;
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

	public List<OfferCondition> getConditions()
	{
		return offerConditions;
	}

	public void setOfferConditions(List<OfferCondition> offerConditions)
	{
		this.offerConditions = offerConditions;
	}

	public int getPriority()
	{
		return 0;
	}

	public Set<OfferTopUp> getTopUps()
	{
		return Collections.emptySet();
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
