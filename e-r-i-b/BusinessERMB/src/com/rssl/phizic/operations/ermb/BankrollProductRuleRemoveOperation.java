package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRulesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Операция удаления правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRuleRemoveOperation extends OperationBase
{
	private List<BankrollProductRule> rules;
	private List<BankrollProductRule> selectedRules;

	public void initialize(Long[] ids) throws BusinessLogicException
	{
		if (ArrayUtils.isEmpty(ids))
			throw new BusinessLogicException("Для выполнения действия необходимо указать одну или несколько записей из списка");

		List<Long> selectedIds = Arrays.asList(ids);

		rules = ConfigFactory.getConfig(BankrollProductRulesConfig.class).getBankrollProductRules();
		selectedRules = new ArrayList<BankrollProductRule>(selectedIds.size());
		for (BankrollProductRule bankrollProductRule : rules)
		{
			if (selectedIds.contains(bankrollProductRule.getId()))
				selectedRules.add(bankrollProductRule);
		}

	}

	public void remove() throws BusinessLogicException
	{
		for (BankrollProductRule bankrollProductRule : selectedRules)
			rules.remove(bankrollProductRule);

		BankrollProductRulesConfig config = ConfigFactory.getConfig(BankrollProductRulesConfig.class);
		config.setBankrollProductRules(rules);
		config.save();
	}

}
