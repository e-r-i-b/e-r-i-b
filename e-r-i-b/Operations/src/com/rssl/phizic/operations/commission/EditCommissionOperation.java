package com.rssl.phizic.operations.commission;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.commission.*;
import com.rssl.phizic.operations.OperationBase;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Collections;

/**
 * Редактирование параметров типа комиссии
 * @author Evgrafov
 * @ created 13.09.2007
 * @ $Author: krenev $
 * @ $Revision: 12319 $
 */
public class EditCommissionOperation extends OperationBase
{
	private static final CommissionService commissionService = new CommissionService();

	private CommissionType                 type;
	private Map<String, CommissionRule>    rulesByCurrency; // новые CommissionRule
	private List<? extends CommissionRule> rules;

	/**
	 * Инициализация операции
	 * @param commissionId id комиссии
	 */
	public void initialize(Long commissionId) throws BusinessException
	{
		if(commissionId == null)
			throw new IllegalArgumentException("commissionId не может быть null");

		CommissionType temp = commissionService.findTypeById(commissionId);

		if(temp == null)
			throw new NullPointerException("CommissionType c ID=" + commissionId + " не найден");

		type = temp;
		rules = commissionService.findRules(type);
		rulesByCurrency = new HashMap<String, CommissionRule>();
	}

	/**
	 * @return CommissionType
	 */
	public CommissionType getType()
	{
		return type;
	}

	/**
	 * @return текущий список CommissionRule
	 */
	public List<? extends CommissionRule> getRules()
	{
		return Collections.unmodifiableList(rules);
	}


	/**
	 * Добавить GateRule
	 * @param currencyCode код валюты
	 */
	public void addGateRule(String currencyCode)
	{
		GateRule rule = new GateRule();
		addRule(currencyCode, rule);
	}

	/**
	 * Добавить FixedRule
	 * @param currencyCode код валюты
	 * @param amount сумма комиссии
	 */
	public void addFixedRule(String currencyCode, BigDecimal amount)
	{
		FixedRule rule = new FixedRule();
		rule.setAmount(amount);
		addRule(currencyCode, rule);
	}

	/**
	 * Добавить RateRule
	 * @param currencyCode код валюты
	 * @param rate % комиссии
	 * @param minAmount минимальная сумма комиссии
	 * @param maxAmount максимальная сумма комиссии
	 */
	public void addRateRule(String currencyCode, BigDecimal rate, BigDecimal minAmount, BigDecimal maxAmount)
	{
		RateRule rule = new RateRule();
		rule.setRate(rate);
		rule.setMinAmount(minAmount);
		rule.setMaxAmount(maxAmount);
		addRule(currencyCode, rule);
	}

	private void addRule(String currencyCode, CommissionRule rule)
	{
		rule.setCurrencyCode(currencyCode);
		rulesByCurrency.put(currencyCode, rule);
	}

	/**
	 * Обновить правила расчета комиссии
	 */
	@Transactional
	public void updateRules() throws BusinessException
	{
		commissionService.updateRules(type, rulesByCurrency.values());
	}
}