package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.AccountCommonTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг "account" запроса детальной информации по вкладу
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "number", "balance", "state", "description", "rate", "maxSumWrite", "currency", "openDate",
		"creditAllowed", "debitAllowed", "minimumBalance", "office", "demand", "passbook", "showOnMain", "period", "closeDate", "creditCrossAgency",
		"debitCrossAgency", "prolongation", "interestTransferAccount", "interestTransferCard"})
@XmlRootElement(name = "account")
public class AccountDetailTag extends AccountCommonTag
{
	private String period;
	private String closeDate;
	private Boolean creditCrossAgency;
	private Boolean debitCrossAgency;
	private Boolean prolongation;
	private String interestTransferAccount;
	private String interestTransferCard;

	@XmlElement(name = "period", required = false)
	public String getPeriod()
	{
		return period;
	}

	public void setPeriod(String period)
	{
		this.period = period;
	}

	@XmlElement(name = "closeDate", required = false)
	public String getCloseDate()
	{
		return closeDate;
	}

	public void setCloseDate(String closeDate)
	{
		this.closeDate = closeDate;
	}

	@XmlElement(name = "creditCrossAgency", required = false)
	public Boolean getCreditCrossAgency()
	{
		return creditCrossAgency;
	}

	public void setCreditCrossAgency(Boolean creditCrossAgency)
	{
		this.creditCrossAgency = creditCrossAgency;
	}

	@XmlElement(name = "debitCrossAgency", required = false)
	public Boolean getDebitCrossAgency()
	{
		return debitCrossAgency;
	}

	public void setDebitCrossAgency(Boolean debitCrossAgency)
	{
		this.debitCrossAgency = debitCrossAgency;
	}

	@XmlElement(name = "prolongation", required = false)
	public Boolean getProlongation()
	{
		return prolongation;
	}

	public void setProlongation(Boolean prolongation)
	{
		this.prolongation = prolongation;
	}

	@XmlElement(name = "interestTransferAccount", required = false)
	public String getInterestTransferAccount()
	{
		return interestTransferAccount;
	}

	public void setInterestTransferAccount(String interestTransferAccount)
	{
		this.interestTransferAccount = interestTransferAccount;
	}

	@XmlElement(name = "interestTransferCard", required = false)
	public String getInterestTransferCard()
	{
		return interestTransferCard;
	}

	public void setInterestTransferCard(String interestTransferCard)
	{
		this.interestTransferCard = interestTransferCard;
	}
}
