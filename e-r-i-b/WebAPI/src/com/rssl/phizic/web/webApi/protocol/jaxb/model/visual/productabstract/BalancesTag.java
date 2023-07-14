package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Тэг "balances" запроса выписки по продукту
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"openingBalance", "closingBalance"})
@XmlRootElement(name = "balances")
public class BalancesTag
{
	private MoneyTag openingBalance;
	private MoneyTag closingBalance;

	@XmlElement(name = "openingBalance", required = false)
	public MoneyTag getOpeningBalance()
	{
		return openingBalance;
	}

	public void setOpeningBalance(MoneyTag openingBalance)
	{
		this.openingBalance = openingBalance;
	}

	@XmlElement(name = "closingBalance", required = false)
	public MoneyTag getClosingBalance()
	{
		return closingBalance;
	}

	public void setClosingBalance(MoneyTag closingBalance)
	{
		this.closingBalance = closingBalance;
	}
}
