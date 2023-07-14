package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Информация о бюджете по категории
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"budgetSum", "avg"})
@XmlRootElement(name = "budget")
public class BudgetTag
{
	private MoneyTag budgetSum;
	private Boolean isAvg;

	@XmlElement(name = "budgetSum", required = true)
	public MoneyTag getBudgetSum()
	{
		return budgetSum;
	}

	public void setBudgetSum(MoneyTag budgetSum)
	{
		this.budgetSum = budgetSum;
	}

	@XmlElement(name = "isAvg", required = false)
	public Boolean getAvg()
	{
		return isAvg;
	}

	public void setAvg(Boolean avg)
	{
		isAvg = avg;
	}
}
