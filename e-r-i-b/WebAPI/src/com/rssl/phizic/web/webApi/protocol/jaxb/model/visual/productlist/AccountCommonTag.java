package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.CurrencyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.OfficeTag;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Класс для корректной работы propOrder в JAXB
 * @author Jatsky
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlTransient
public abstract class AccountCommonTag
{
	private Long id;
	private String name;
	private String number;
	private MoneyTag balance;
	private AccountState state;
	private String description;
	private BigDecimal rate;
	private MoneyTag maxSumWrite;
	private CurrencyTag currency;
	private String openDate;
	private Boolean creditAllowed;
	private Boolean debitAllowed;
	private MoneyTag minimumBalance;
	private OfficeTag office;
	private Boolean demand;
	private Boolean passbook;
	private boolean showOnMain;

	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElement(name = "number", required = true)
	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	@XmlElement(name = "balance")
	public MoneyTag getBalance()
	{
		return balance;
	}

	public void setBalance(MoneyTag balance)
	{
		this.balance = balance;
	}

	@XmlElement(name = "state", required = true)
	public AccountState getState()
	{
		return state;
	}

	public void setState(AccountState state)
	{
		this.state = state;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "description")
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	@XmlElement(name = "rate")
	public BigDecimal getRate()
	{
		return rate;
	}

	public void setRate(BigDecimal rate)
	{
		this.rate = rate;
	}

	@XmlElement(name = "maxSumWrite")
	public MoneyTag getMaxSumWrite()
	{
		return maxSumWrite;
	}

	public void setMaxSumWrite(MoneyTag maxSumWrite)
	{
		this.maxSumWrite = maxSumWrite;
	}

	@XmlElement(name = "currency", required = true)
	public CurrencyTag getCurrency()
	{
		return currency;
	}

	public void setCurrency(CurrencyTag currency)
	{
		this.currency = currency;
	}

	@XmlElement(name = "openDate")
	public String getOpenDate()
	{
		return openDate;
	}

	public void setOpenDate(String openDate)
	{
		this.openDate = openDate;
	}

	@XmlElement(name = "creditAllowed")
	public Boolean getCreditAllowed()
	{
		return creditAllowed;
	}

	public void setCreditAllowed(Boolean creditAllowed)
	{
		this.creditAllowed = creditAllowed;
	}

	@XmlElement(name = "debitAllowed")
	public Boolean getDebitAllowed()
	{
		return debitAllowed;
	}

	public void setDebitAllowed(Boolean debitAllowed)
	{
		this.debitAllowed = debitAllowed;
	}

	@XmlElement(name = "minimumBalance")
	public MoneyTag getMinimumBalance()
	{
		return minimumBalance;
	}

	public void setMinimumBalance(MoneyTag minimumBalance)
	{
		this.minimumBalance = minimumBalance;
	}

	@XmlElement(name = "office", required = true)
	public OfficeTag getOffice()
	{
		return office;
	}

	public void setOffice(OfficeTag office)
	{
		this.office = office;
	}

	@XmlElement(name = "demand")
	public Boolean getDemand()
	{
		return demand;
	}

	public void setDemand(Boolean demand)
	{
		this.demand = demand;
	}

	@XmlElement(name = "passbook")
	public Boolean getPassbook()
	{
		return passbook;
	}

	public void setPassbook(Boolean passbook)
	{
		this.passbook = passbook;
	}

	@XmlElement(name = "showOnMain", required = true)
	public boolean isShowOnMain()
	{
		return showOnMain;
	}

	public void setShowOnMain(boolean showOnMain)
	{
		this.showOnMain = showOnMain;
	}
}
