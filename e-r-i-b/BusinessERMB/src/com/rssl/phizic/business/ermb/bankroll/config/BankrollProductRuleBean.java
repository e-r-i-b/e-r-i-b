package com.rssl.phizic.business.ermb.bankroll.config;

import javax.xml.bind.annotation.*;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Правило подключения банковских продуктов клиенту
 */
@XmlType(name = "Rule")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductRuleBean
{
	@XmlElement(required = true)
    private String name;

	@XmlElement(required = true)
    private BankrollProductRuleConditionBean condition;

	@XmlElement(required = true)
    private BankrollProductRuleActionBean action;

	@XmlElement(name = "status", required = true)
    private boolean isActive;

	String getName()
	{
		return name;
	}

	void setName(String name)
	{
		this.name = name;
	}

	BankrollProductRuleConditionBean getCondition()
	{
		return condition;
	}

	void setCondition(BankrollProductRuleConditionBean condition)
	{
		this.condition = condition;
	}

	BankrollProductRuleActionBean getAction()
	{
		return action;
	}

	void setAction(BankrollProductRuleActionBean action)
	{
		this.action = action;
	}

	Boolean isActive()
	{
		return isActive;
	}

	void setActive(boolean active)
	{
		isActive = active;
	}
}
