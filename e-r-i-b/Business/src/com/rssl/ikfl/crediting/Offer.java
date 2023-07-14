package com.rssl.ikfl.crediting;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Erkin
 * @ created 29.12.2014
 * @ $Author$
 * @ $Revision$
 */
@PlainOldJavaObject
@SuppressWarnings("PackageVisibleField")
public class Offer
{
	/**
	 * ������������� ����������� [PK]
	 */
	long id;

	/**
	 * �������
	 */
	String surName;

	/**
	 * ���
	 */
	String firstName;

	/**
	 * ��������
	 */
	String patrName;

	/**
	 * ���� ��������
	 */
	Calendar birthDay;

	/**
	 * ����� ���������
	 */
	String docSeries;

	/**
	 * ����� ���������
	 */
	String docNumber;

	/**
	 * ������������� �������� �� [1]
	 */
	String campaignId;

	/**
	 * ������������ �������� �� [0-1]
	 */
	String campaignName;

	/**
	 * ��� �������� [1]
	 */
	OfferProductType productType;

	/**
	 * ��� ����������� [1]
	 */
	String sourceCode;

	/**
	 * ������������ ����������� [1]
	 */
	String sourceName;

	/**
	 * ������������ �������� �� [1]
	 */
	String productASName;

	/**
	 * ��� ���� �������� TSM [0-1]
	 */
	String productTypeCode;

	/**
	 * ��� �������� TSM [0-1]
	 */
	String productCode;

	/**
	 * ��� ����������� TSM [0-1]
	 */
	String productSubCode;

	/**
	 * ��� ��������� [1]
	 */
	String campaignMemberId;

	/**
	 * ������������� ������� [1]
	 */
	String clientId;

	/**
	 *
	 */
	String tb;

	/**
	 * ��������� ����������� [1]
	 */
	int priority;

	/**
	 * ����� �������������������� ��������� ��� ������� [0-1]
	 */
	String personalText;

	/**
	 * ���� ��������� �������� ����������� [1]
	 */
	Calendar expDate;

	/**
	 * ���������� ��� ������ (RUR, EUR, USD) [1]
	 */
	String currencyCode;

	/**
	 * ����������� ���������� ������ [0-1]
	 */
	BigDecimal rateMin;

	/**
	 * ������������ ���������� ������ [0-1]
	 */
	BigDecimal rateMax;

	/**
	 * ����������� ����� [0-1]
	 */
	BigDecimal limitMin;

	/**
	 * ������������ ����� [0-1]
	 */
	BigDecimal limitMax;

	/**
	 * ����������� ������ (���) [0-1]
	 */
	Integer periodMin;

	/**
	 * ������������ ������ (���) [0-1]
	 */
	Integer periodMax;

	/**
	 * ���� �������� ����������� [1]
	 */
	Calendar loadDate;

	/**
	 * �� ����������� ��������� ������
	 */
	boolean offerUsed;

	/**
	 * ����������� ���������
	 */
	boolean isActive;

	Set<OfferTopUp> topUps = new HashSet<OfferTopUp>();

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public String getSurName()
	{
		return surName;
	}

	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	public String getFirstName()
	{
		return firstName;
	}

	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
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

	public String getDocSeries()
	{
		return docSeries;
	}

	public void setDocSeries(String docSeries)
	{
		this.docSeries = docSeries;
	}

	public String getDocNumber()
	{
		return docNumber;
	}

	public void setDocNumber(String docNumber)
	{
		this.docNumber = docNumber;
	}

	public String getCampaignId()
	{
		return campaignId;
	}

	public void setCampaignId(String campaignId)
	{
		this.campaignId = campaignId;
	}

	public String getCampaignName()
	{
		return campaignName;
	}

	public void setCampaignName(String campaignName)
	{
		this.campaignName = campaignName;
	}

	public OfferProductType getProductType()
	{
		return productType;
	}

	public void setProductType(OfferProductType productType)
	{
		this.productType = productType;
	}

	public String getSourceCode()
	{
		return sourceCode;
	}

	public void setSourceCode(String sourceCode)
	{
		this.sourceCode = sourceCode;
	}

	public String getSourceName()
	{
		return sourceName;
	}

	public void setSourceName(String sourceName)
	{
		this.sourceName = sourceName;
	}

	public String getProductASName()
	{
		return productASName;
	}

	public void setProductASName(String productASName)
	{
		this.productASName = productASName;
	}

	public String getProductTypeCode()
	{
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode)
	{
		this.productTypeCode = productTypeCode;
	}

	public String getProductCode()
	{
		return productCode;
	}

	public void setProductCode(String productCode)
	{
		this.productCode = productCode;
	}

	public String getProductSubCode()
	{
		return productSubCode;
	}

	public void setProductSubCode(String productSubCode)
	{
		this.productSubCode = productSubCode;
	}

	public String getCampaignMemberId()
	{
		return campaignMemberId;
	}

	public void setCampaignMemberId(String campaignMemberId)
	{
		this.campaignMemberId = campaignMemberId;
	}

	public String getClientId()
	{
		return clientId;
	}

	public void setClientId(String clientId)
	{
		this.clientId = clientId;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
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

	public Calendar getExpDate()
	{
		return expDate;
	}

	public void setExpDate(Calendar expDate)
	{
		this.expDate = expDate;
	}

	public String getCurrencyCode()
	{
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode)
	{
		this.currencyCode = currencyCode;
	}

	public BigDecimal getRateMin()
	{
		return rateMin;
	}

	public void setRateMin(BigDecimal rateMin)
	{
		this.rateMin = rateMin;
	}

	public BigDecimal getRateMax()
	{
		return rateMax;
	}

	public void setRateMax(BigDecimal rateMax)
	{
		this.rateMax = rateMax;
	}

	public BigDecimal getLimitMin()
	{
		return limitMin;
	}

	public void setLimitMin(BigDecimal limitMin)
	{
		this.limitMin = limitMin;
	}

	public BigDecimal getLimitMax()
	{
		return limitMax;
	}

	public void setLimitMax(BigDecimal limitMax)
	{
		this.limitMax = limitMax;
	}

	public Integer getPeriodMin()
	{
		return periodMin;
	}

	public void setPeriodMin(Integer periodMin)
	{
		this.periodMin = periodMin;
	}

	public Integer getPeriodMax()
	{
		return periodMax;
	}

	public void setPeriodMax(Integer periodMax)
	{
		this.periodMax = periodMax;
	}

	public Calendar getLoadDate()
	{
		return loadDate;
	}

	public void setLoadDate(Calendar loadDate)
	{
		this.loadDate = loadDate;
	}

	public boolean isOfferUsed()
	{
		return offerUsed;
	}

	public void setOfferUsed(boolean offerUsed)
	{
		this.offerUsed = offerUsed;
	}

	public boolean isActive()
	{
		return isActive;
	}

	public void setActive(boolean active)
	{
		isActive = active;
	}

	public Set<OfferTopUp> getTopUps()
	{
		return topUps;
	}

	public void setTopUps(Set<OfferTopUp> topUps)
	{
		this.topUps = topUps;
	}
}
