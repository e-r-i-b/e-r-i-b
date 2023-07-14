package com.rssl.phizic.business.ermb.bankroll.config;

import java.util.List;
import javax.xml.bind.annotation.*;

/**
 * @author Rtischeva
 * @ created 29.11.13
 * @ $Author$
 * @ $Revision$
 */
@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
class BankrollProductRulesConfigBean
{
	@XmlElementWrapper(name = "rules", required = true)
	@XmlElement(name = "rule")
    private List<BankrollProductRuleBean> rules;

	List<BankrollProductRuleBean> getRules()
	{
		return rules;
	}

	void setRules(List<BankrollProductRuleBean> rules)
	{
		this.rules = rules;
	}
}
