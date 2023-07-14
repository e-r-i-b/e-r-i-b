package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRule;
import com.rssl.phizic.business.ermb.bankroll.config.BankrollProductRulesConfig;
import com.rssl.phizic.business.util.AllowedDepartmentsUtil;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.*;

/**
 * ќпераци€ создани€/редактировани€ правила включени€ видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 04.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRuleEditOperation extends OperationBase implements EditEntityOperation
{
	private List<BankrollProductRule> rules;
	private BankrollProductRule editRule;
	private List<Department> allowedDepartments;
	private List<ErmbTariff> tariffs;

	private DepartmentService departmentService = new DepartmentService();
	private final static ErmbTariffService tariffService = new ErmbTariffService();

	public BankrollProductRule getEntity() throws BusinessException, BusinessLogicException
	{
		return editRule;
	}

	public void initializeNew() throws BusinessException
	{
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanks();
		tariffs = tariffService.getAllTariffs();

		rules = ConfigFactory.getConfig(BankrollProductRulesConfig.class).getBankrollProductRules();
		editRule = new BankrollProductRule();
	}

	public void initialize(Long id) throws BusinessException
	{
		allowedDepartments = AllowedDepartmentsUtil.getAllowedTerbanks();
		tariffs = tariffService.getAllTariffs();

		rules = ConfigFactory.getConfig(BankrollProductRulesConfig.class).getBankrollProductRules();
		for (BankrollProductRule bankrollProductRule : rules)
		{
			if (id.equals(bankrollProductRule.getId()))
				editRule = bankrollProductRule;
		}
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if (editRule.getId() == null)
			rules.add(editRule);

		BankrollProductRulesConfig config = ConfigFactory.getConfig(BankrollProductRulesConfig.class);
		config.setBankrollProductRules(rules);
		config.save();
	}

	public Set<Department> getTerbanks(Long[] tbIds) throws BusinessException
	{
		Set<Department> terbanks = new HashSet<Department>();

		List<Long> tbIdsList = Arrays.asList(tbIds);
		for (Department tb : allowedDepartments)
		{
			if(tbIdsList.contains(tb.getId()))
				terbanks.add(tb);
		}

		return terbanks;
	}

	public Set<Department> getTerbanksByCodes(List<String> tbCodes) throws BusinessException
	{
		if (tbCodes == null)
			return Collections.emptySet();

		Set<Department> terbanks = new HashSet<Department>();

		for (Department tb : allowedDepartments)
		{
			Code code = tb.getCode();
			String region = code.getFields().get("region");
			if(tbCodes.contains(region))
				terbanks.add(tb);
		}

		return terbanks;
	}

	public List<String> getTerbankCodes(Long[] tbIds) throws BusinessException
	{
		Set<Department> terbanks = getTerbanks(tbIds);

		List<String> terbankCodes = new ArrayList<String>(terbanks.size());

		for (Department terbank : terbanks)
		{
			Code code = terbank.getCode();
			String region = code.getFields().get("region");
			terbankCodes.add(region);
		}

		return terbankCodes;
	}

	public List<ErmbTariff> getTariffs()
	{
		return tariffs;
	}

	public ErmbTariff getSelectedTariff(Long tariffId)
	{
		for (ErmbTariff tariff : tariffs)
		{
			if (tariffId.equals(tariff.getId()))
				return tariff;
		}
		return null;
	}
}
