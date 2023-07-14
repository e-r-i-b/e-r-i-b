package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRulesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Операция отображения списка правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRuleListOperation extends OperationBase
{
	List<BankrollProductRule> rules = new ArrayList<BankrollProductRule>();

	public void initialize()
	{
		rules = ConfigFactory.getConfig(BankrollProductRulesConfig.class).getBankrollProductRules();
	}

	public List<BankrollProductRule> getRules()
	{
		return rules;
	}
}
