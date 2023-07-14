package com.rssl.phizic.business.cards;

import com.rssl.phizic.business.accounts.MockOffice;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.MockObject;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 26.05.2010
 * @ $Author$
 * @ $Revision$
 */


/**
 * моковская карта, для возвращения в случае если их бэка ничего не пришло
 */
public class MockCard implements Card, MockObject
{
	public static String EMPTY_STRING = "";

	private String id = null;
	private String number = null;
	private Calendar expireDate = null;
	private Calendar nextReportDate = null;
	private String   displayedExpireDate = null;
	private boolean main = true;
	private String mainCardNumber;
	protected String primaryAccountNumber = null;
	protected String primaryAccountExternalId = null;
	protected Long kind = null;
	protected Long subkind = null;
	protected AdditionalCardType additionalCardType; //Тип доп. карты

	public String getId()
	{
		if(id==null)
			return EMPTY_STRING;
		else
			return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return EMPTY_STRING;
	}

	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return null;
	}

	public String getType()
	{
		return EMPTY_STRING;
	}

	public String getNumber()
	{
		if(number==null)
			return EMPTY_STRING;
		else
			return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public Calendar getIssueDate()
	{
		return null;
	}

	public Calendar getExpireDate()
	{
		return expireDate;
	}

	public void setDisplayedExpireDate(String displayedExpireDate)
	{
		this.displayedExpireDate = displayedExpireDate;
	}

	public String getDisplayedExpireDate()
	{
		return displayedExpireDate;
	}

	public void setExpireDate(Calendar expireDate)
	{
		this.expireDate = expireDate;
	}

	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

	public CardType getCardType()
	{
		return null;
	}

	public Money getAvailableLimit()
	{
		return null;
	}

	public Office getOffice()
	{
		return new MockOffice();
	}

	public Code getOriginalTbCode()
	{
		return null;
	}

	public AdditionalCardType getAdditionalCardType()
	{
		return additionalCardType;
	}

	public void setAdditionalCardType(AdditionalCardType additionalCardType)
	{
		this.additionalCardType = additionalCardType;
	}

	public String getStatusDescription()
	{
		return EMPTY_STRING;
	}

	public CardState getCardState()
	{
		return null;
	}

	public AccountState getCardAccountState()
	{
		return null;
	}

	public Currency getCurrency()
	{
		return null;
	}

	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	public boolean isVirtual()
	{
		return false;
	}

	public String getPrimaryAccountNumber()
	{
		return primaryAccountNumber;
	}

	public String getPrimaryAccountExternalId()
	{
		return primaryAccountExternalId;
	}

	public Long getKind()
	{
		return kind;
	}

	public void setKind(Long kind)
	{
		this.kind = kind;
	}

	public Long getSubkind()
	{
		return subkind;
	}

	public String getUNICardType()
	{
		return null;
	}

	public String getUNIAccountType()
	{
		return null;  
	}

	public CardLevel getCardLevel()
	{
		return null;
	}

	public CardBonusSign getCardBonusSign()
	{
		return null;
	}

	public String getClientId()
	{
		return null;
	}

	public boolean isUseReportDelivery()
	{
		return false;
	}

	public String getEmailAddress()
	{
		return null;
	}

	public ReportDeliveryType getReportDeliveryType()
	{
		return null;
	}

	public ReportDeliveryLanguage getReportDeliveryLanguage()
	{
		return null;
	}

	public void setSubkind(Long subkind)
	{
		this.subkind = subkind;
	}

	public Money getPurchaseLimit()
	{
		return null;
	}

	public Money getAvailableCashLimit()
	{
		return null;
	}

	public String getHolderName()
	{
		return null;
	}

	public String getContractNumber()
	{
		return EMPTY_STRING;
	}

    public Calendar getNextReportDate()
    {
        return nextReportDate;
    }

    public void setNextReportDate(Calendar nextReportDate)
    {
        this.nextReportDate = nextReportDate;
	}

	public Money getOverdraftLimit()
	{
		return null;
	}

	public Money getOverdraftTotalDebtSum()
	{
		return null;
	}

	public Money getOverdraftMinimalPayment()
	{
		return null;
	}

	public Calendar getOverdraftMinimalPaymentDate()
	{
		return null;
	}

	public Money getOverdraftOwnSum()
	{
		return null;
	}

	public Client getCardClient()
	{
		return null;
	}

	@Override public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("number", number)
				.append("expireDate", DateHelper.formatDateToStringWithPoint(expireDate))
				.append("nextReportDate", DateHelper.formatDateToStringWithPoint(nextReportDate))
				.append("displayedExpireDate", displayedExpireDate)
				.append("main", main)
				.append("mainCardNumber", mainCardNumber)
				.append("primaryAccountNumber", primaryAccountNumber)
				.append("primaryAccountExternalId", primaryAccountExternalId)
				.append("kind", kind)
				.append("subkind", subkind)
				.append("additionalCardType", additionalCardType)
				.toString();
	}
}
