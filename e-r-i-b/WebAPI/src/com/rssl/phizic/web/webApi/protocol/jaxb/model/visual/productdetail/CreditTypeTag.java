package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Òýã "creditType"
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"limit", "ownSum", "minPayment", "minPaymentDate"})
@XmlRootElement(name = "creditType")
public class CreditTypeTag
{
	private MoneyTag limit;
	private MoneyTag ownSum;
	private MoneyTag minPayment;
	private String minPaymentDate;

	@XmlElement(name = "limit", required = false)
	public MoneyTag getLimit()
	{
		return limit;
	}

	public void setLimit(MoneyTag limit)
	{
		this.limit = limit;
	}

	@XmlElement(name = "ownSum", required = false)
	public MoneyTag getOwnSum()
	{
		return ownSum;
	}

	public void setOwnSum(MoneyTag ownSum)
	{
		this.ownSum = ownSum;
	}

	@XmlElement(name = "minPayment", required = false)
	public MoneyTag getMinPayment()
	{
		return minPayment;
	}

	public void setMinPayment(MoneyTag minPayment)
	{
		this.minPayment = minPayment;
	}

	@XmlElement(name = "minPaymentDate", required = false)
	public String getMinPaymentDate()
	{
		return minPaymentDate;
	}

	public void setMinPaymentDate(String minPaymentDate)
	{
		this.minPaymentDate = minPaymentDate;
	}
}
