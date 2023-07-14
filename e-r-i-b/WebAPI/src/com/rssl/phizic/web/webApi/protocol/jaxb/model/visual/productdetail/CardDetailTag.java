package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productlist.CardCommonTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг "card" запроса детальной информации по карте
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "description", "number", "main", "type", "availableLimit", "state", "virtual", "statusDescExternalCode", "issueDate",
		"expireDate", "office", "additionalCardType", "currency", "mainCardNumber", "primaryAccountNumber", "cardLevel", "cardBonusSign", "showOnMain",
		"holderName", "availableCashLimit", "purchaseLimit", "creditType"})
@XmlRootElement(name = "card")
public class CardDetailTag extends CardCommonTag
{
	private String holderName;
	private MoneyTag availableCashLimit;
	private MoneyTag purchaseLimit;
	private CreditTypeTag creditType;

	@XmlElement(name = "holderName", required = true)
	public String getHolderName()
	{
		return holderName;
	}

	public void setHolderName(String holderName)
	{
		this.holderName = holderName;
	}

	@XmlElement(name = "availableCashLimit", required = false)
	public MoneyTag getAvailableCashLimit()
	{
		return availableCashLimit;
	}

	public void setAvailableCashLimit(MoneyTag availableCashLimit)
	{
		this.availableCashLimit = availableCashLimit;
	}

	@XmlElement(name = "purchaseLimit", required = false)
	public MoneyTag getPurchaseLimit()
	{
		return purchaseLimit;
	}

	public void setPurchaseLimit(MoneyTag purchaseLimit)
	{
		this.purchaseLimit = purchaseLimit;
	}

	@XmlElement(name = "creditType", required = false)
	public CreditTypeTag getCreditType()
	{
		return creditType;
	}

	public void setCreditType(CreditTypeTag creditType)
	{
		this.creditType = creditType;
	}
}
