package com.rssl.phizic.business.ant;

import com.rssl.phizic.business.commission.CommissionService;
import com.rssl.phizic.business.commission.CommissionType;
import com.rssl.phizic.business.commission.FixedRule;
import com.rssl.phizic.business.commission.CommissionRule;
import com.rssl.phizic.utils.test.SafeTaskBase;
import org.apache.tools.ant.BuildException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * @author Evgrafov
 * @ created 17.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

public class InitCommissionsTask extends SafeTaskBase
{
	private List<CommissionType> types;
	private CommissionService commissionService = new CommissionService();

	@Override public void init() throws BuildException
	{
		super.init();
		types = new ArrayList<CommissionType>();

		addType("close-account"    , "Заявка на закрытие счета/вклада");
		addType("replenish-account", "Пополнение вклада");
		addType("int-trans-claim"  , "Списание средств со вклада");
		addType("internal-payment" , "Перевод по счетам в системе");
		addType("rus-payment"      , "Перевод в рублях РФ");
		addType("replenish-card"   , "Пополнение пластиковой карты с текущего счета клиента");
	}

	private void addType(String key, String name)
	{
		CommissionType type = new CommissionType();
		type.setKey(key);
		type.setName(name);
		types.add(type);
	}

	public void safeExecute() throws Exception
	{
		for (CommissionType type : types)
		{
			CommissionType dbType = commissionService.findTypeByKey(type.getKey());
			if(dbType != null)  // если тип уже создан ничего не делаем :)
				continue;

			commissionService.saveType(type);

			CommissionRule ruleRUB = createDefaultRule("RUB");
			CommissionRule ruleEUR = createDefaultRule("EUR");
			CommissionRule ruleUSD = createDefaultRule("USD");

			commissionService.updateRules(type, Arrays.asList(ruleRUB, ruleEUR, ruleUSD));
		}
	}

	private CommissionRule createDefaultRule(String currencyCode)
	{
		FixedRule rule = new FixedRule();
		rule.setAmount(new BigDecimal("0"));
		rule.setCurrencyCode(currencyCode);

		return rule;
	}
}