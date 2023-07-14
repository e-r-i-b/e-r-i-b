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
 * �������������� ���������� ���� ��������
 * @author Evgrafov
 * @ created 13.09.2007
 * @ $Author: krenev $
 * @ $Revision: 12319 $
 */
public class EditCommissionOperation extends OperationBase
{
	private static final CommissionService commissionService = new CommissionService();

	private CommissionType                 type;
	private Map<String, CommissionRule>    rulesByCurrency; // ����� CommissionRule
	private List<? extends CommissionRule> rules;

	/**
	 * ������������� ��������
	 * @param commissionId id ��������
	 */
	public void initialize(Long commissionId) throws BusinessException
	{
		if(commissionId == null)
			throw new IllegalArgumentException("commissionId �� ����� ���� null");

		CommissionType temp = commissionService.findTypeById(commissionId);

		if(temp == null)
			throw new NullPointerException("CommissionType c ID=" + commissionId + " �� ������");

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
	 * @return ������� ������ CommissionRule
	 */
	public List<? extends CommissionRule> getRules()
	{
		return Collections.unmodifiableList(rules);
	}


	/**
	 * �������� GateRule
	 * @param currencyCode ��� ������
	 */
	public void addGateRule(String currencyCode)
	{
		GateRule rule = new GateRule();
		addRule(currencyCode, rule);
	}

	/**
	 * �������� FixedRule
	 * @param currencyCode ��� ������
	 * @param amount ����� ��������
	 */
	public void addFixedRule(String currencyCode, BigDecimal amount)
	{
		FixedRule rule = new FixedRule();
		rule.setAmount(amount);
		addRule(currencyCode, rule);
	}

	/**
	 * �������� RateRule
	 * @param currencyCode ��� ������
	 * @param rate % ��������
	 * @param minAmount ����������� ����� ��������
	 * @param maxAmount ������������ ����� ��������
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
	 * �������� ������� ������� ��������
	 */
	@Transactional
	public void updateRules() throws BusinessException
	{
		commissionService.updateRules(type, rulesByCurrency.values());
	}
}