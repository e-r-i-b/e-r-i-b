package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRulesConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * Операция включения/отключения правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 11.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRuleActivateOrDeactivateOperation extends OperationBase
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

	public Map<String, Exception> activate() throws BusinessLogicException
	{
		Map<String, Exception> exceptions = new HashMap<String, Exception>();
		for (BankrollProductRule bankrollProductRule : selectedRules)
		{
			try
			{
				activateRule(bankrollProductRule);
			}

			catch (BusinessLogicException ex)
			{
				exceptions.put(bankrollProductRule.getName(), ex);
			}
		}

		saveConfig();
		return exceptions;
	}

	private void activateRule(BankrollProductRule bankrollProductRule) throws BusinessLogicException
	{
		if (bankrollProductRule.isActive())
			throw new BusinessLogicException("Правило " + bankrollProductRule.getName() + " уже подключено. Действие не было выполнено.");
		else
			bankrollProductRule.setActive(true);
	}

	public Map<String, Exception> deactivate() throws BusinessLogicException
	{
		Map<String, Exception> exceptions = new HashMap<String, Exception>();
		for (BankrollProductRule bankrollProductRule : selectedRules)
		{
			try
			{
				deactivateRule(bankrollProductRule);
			}
			catch (BusinessLogicException ex)
			{
				exceptions.put(bankrollProductRule.getName(), ex);
			}
		}

		saveConfig();
		return exceptions;
	}

	private void deactivateRule(BankrollProductRule bankrollProductRule) throws BusinessLogicException
	{
		if (!bankrollProductRule.isActive())
			throw new BusinessLogicException("Правило " + bankrollProductRule.getName() + " уже отключено. Действие не было выполнено.");
		else
			bankrollProductRule.setActive(false);
	}

	private void saveConfig()
	{
		BankrollProductRulesConfig config = ConfigFactory.getConfig(BankrollProductRulesConfig.class);
		config.setBankrollProductRules(rules);
		config.save();
	}
}
