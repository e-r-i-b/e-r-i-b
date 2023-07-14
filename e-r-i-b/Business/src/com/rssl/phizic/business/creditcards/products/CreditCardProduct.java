package com.rssl.phizic.business.creditcards.products;

import com.rssl.phizic.business.creditcards.conditions.CreditCardCondition;
import com.rssl.phizic.business.loans.products.Publicity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * ��������� ��������� �������
 * @author Dorzhinov
 * @ created 14.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class CreditCardProduct
{
	private Long id;
	private String name;                        //������������
	private Boolean allowGracePeriod;           //���� �������� ������
	private Boolean useForPreapprovedOffers;         //�������� ��� �������������� �����������
	private Boolean defaultForPreapprovedOffers;           //������������ �� ��������� � �������������� ������������
	private Integer gracePeriodDuration;        //�������� ������ ��
	private BigDecimal gracePeriodInterestRate; //% ������ � �������� ������
	private String additionalTerms;             //���. �������
	private Publicity publicity;                //�������� �������
	private List<CreditCardCondition> conditions = new ArrayList<CreditCardCondition>();    //������� � ������� �����
	private Calendar changeDate;
	private Integer cardTypeCode; //��� ����� �� ������ʻ.

	private Boolean guestPreapproved = Boolean.FALSE;
	private Boolean guestLead        = Boolean.FALSE;
	private Boolean commonLead       = Boolean.FALSE;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean isAllowGracePeriod()
	{
		return allowGracePeriod;
	}

	public Boolean getAllowGracePeriod()
	{
		return isAllowGracePeriod();
	}

	public void setAllowGracePeriod(Boolean allowGracePeriod)
	{
		this.allowGracePeriod = allowGracePeriod;
	}

	public Integer getGracePeriodDuration()
	{
		return gracePeriodDuration;
	}

	public void setGracePeriodDuration(Integer gracePeriodDuration)
	{
		this.gracePeriodDuration = gracePeriodDuration;
	}

	public BigDecimal getGracePeriodInterestRate()
	{
		return gracePeriodInterestRate;
	}

	public void setGracePeriodInterestRate(BigDecimal gracePeriodInterestRate)
	{
		this.gracePeriodInterestRate = gracePeriodInterestRate;
	}

	public String getAdditionalTerms()
	{
		return additionalTerms;
	}

	public void setAdditionalTerms(String additionalTerms)
	{
		this.additionalTerms = additionalTerms;
	}

	public Publicity getPublicity()
	{
		return publicity;
	}

	public void setPublicity(Publicity publicity)
	{
		this.publicity = publicity;
	}

	public List<CreditCardCondition> getConditions()
	{
		return conditions;
	}

	public void setConditions(List<CreditCardCondition> conditions)
	{
		this.conditions = conditions;
	}

	public Calendar getChangeDate()
	{
		return changeDate;
	}

	public void setChangeDate(Calendar changeDate)
	{
		this.changeDate = changeDate;
	}

	public Integer getCardTypeCode()
	{
		return cardTypeCode;
	}

	public void setCardTypeCode(Integer cardTypeCode)
	{
		this.cardTypeCode = cardTypeCode;
	}

	public Boolean getUseForPreapprovedOffers()
	{
		return useForPreapprovedOffers;
	}

	public void setUseForPreapprovedOffers(Boolean useForPreapprovedOffers)
	{
		this.useForPreapprovedOffers = useForPreapprovedOffers;
	}

	public Boolean getDefaultForPreapprovedOffers()
	{
		return defaultForPreapprovedOffers;
	}

	public void setDefaultForPreapprovedOffers(Boolean defaultForPreapprovedOffers)
	{
		this.defaultForPreapprovedOffers = defaultForPreapprovedOffers;
	}

	public void setGuestPreapproved(Boolean guestPreapproved)
	{
		this.guestPreapproved = guestPreapproved;
	}

	public Boolean getGuestPreapproved()
	{
		return guestPreapproved;
	}

	public void setGuestLead(Boolean guestLead)
	{
		this.guestLead = guestLead;
	}

	public Boolean getGuestLead()
	{
		return guestLead;
	}

	public void setCommonLead(Boolean commonLead)
	{
		this.commonLead = commonLead;
	}

	public Boolean getCommonLead()
	{
		return commonLead;
	}
}
