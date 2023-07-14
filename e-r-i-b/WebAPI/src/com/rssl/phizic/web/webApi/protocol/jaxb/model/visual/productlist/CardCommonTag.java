package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.CurrencyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.OfficeTag;

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
public class CardCommonTag
{
	private Long id;
	private String name;
	private String description;
	private String number;
	private boolean isMain;
	private CardTypeWebAPI type;
	private MoneyTag availableLimit;
	private CardState state;
	private boolean isVirtual;
	private StatusDescExternalCode statusDescExternalCode;
	private String issueDate;
	private String expireDate;
	private OfficeTag office;
	private AdditionalCardType additionalCardType;
	private CurrencyTag currency;
	private String mainCardNumber;
	private String primaryAccountNumber;
	private CardLevel cardLevel;
	private CardBonusSign cardBonusSign;
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

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "description", required = true)
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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

	@XmlElement(name = "isMain", required = true)
	public boolean isMain()
	{
		return isMain;
	}

	public void setMain(boolean main)
	{
		isMain = main;
	}

	@XmlElement(name = "type", required = true)
	public CardTypeWebAPI getType()
	{
		return type;
	}

	public void setType(CardTypeWebAPI type)
	{
		this.type = type;
	}

	@XmlElement(name = "availableLimit")
	public MoneyTag getAvailableLimit()
	{
		return availableLimit;
	}

	public void setAvailableLimit(MoneyTag availableLimit)
	{
		this.availableLimit = availableLimit;
	}

	@XmlElement(name = "state", required = true)
	public CardState getState()
	{
		return state;
	}

	public void setState(CardState state)
	{
		this.state = state;
	}

	@XmlElement(name = "isVirtual", required = true)
	public boolean isVirtual()
	{
		return isVirtual;
	}

	public void setVirtual(boolean virtual)
	{
		isVirtual = virtual;
	}

	@XmlElement(name = "statusDescExternalCode", required = true)
	public StatusDescExternalCode getStatusDescExternalCode()
	{
		return statusDescExternalCode;
	}

	public void setStatusDescExternalCode(StatusDescExternalCode statusDescExternalCode)
	{
		this.statusDescExternalCode = statusDescExternalCode;
	}

	@XmlElement(name = "issueDate", required = true)
	public String getIssueDate()
	{
		return issueDate;
	}

	public void setIssueDate(String issueDate)
	{
		this.issueDate = issueDate;
	}

	@XmlElement(name = "expireDate", required = true)
	public String getExpireDate()
	{
		return expireDate;
	}

	public void setExpireDate(String expireDate)
	{
		this.expireDate = expireDate;
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

	@XmlElement(name = "additionalCardType")
	public AdditionalCardType getAdditionalCardType()
	{
		return additionalCardType;
	}

	public void setAdditionalCardType(AdditionalCardType additionalCardType)
	{
		this.additionalCardType = additionalCardType;
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

	@XmlElement(name = "mainCardNumber")
	public String getMainCardNumber()
	{
		return mainCardNumber;
	}

	public void setMainCardNumber(String mainCardNumber)
	{
		this.mainCardNumber = mainCardNumber;
	}

	@XmlElement(name = "primaryAccountNumber")
	public String getPrimaryAccountNumber()
	{
		return primaryAccountNumber;
	}

	public void setPrimaryAccountNumber(String primaryAccountNumber)
	{
		this.primaryAccountNumber = primaryAccountNumber;
	}

	@XmlElement(name = "cardLevel")
	public CardLevel getCardLevel()
	{
		return cardLevel;
	}

	public void setCardLevel(CardLevel cardLevel)
	{
		this.cardLevel = cardLevel;
	}

	@XmlElement(name = "cardBonusSign")
	public CardBonusSign getCardBonusSign()
	{
		return cardBonusSign;
	}

	public void setCardBonusSign(CardBonusSign cardBonusSign)
	{
		this.cardBonusSign = cardBonusSign;
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
