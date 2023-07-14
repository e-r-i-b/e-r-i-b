package com.rssl.phizic.business.ermb.bankroll.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфиг для чтения/записи правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 02.12.13
 * @ $Author$
 * @ $Revision$
 */
public class BankrollProductRulesConfig extends BeanConfigBase<BankrollProductRulesConfigBean>
{
	public static final String CODENAME = "BankrollProductRulesConfig";

	private static final ErmbTariffService ermbTariffService = new ErmbTariffService();

	private List<BankrollProductRule> bankrollProductRules;

	private Integer counter;

	public BankrollProductRulesConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected String getCodename()
	{
		return CODENAME;
	}

	@Override
	protected Class<BankrollProductRulesConfigBean> getConfigDataClass()
	{
		return BankrollProductRulesConfigBean.class;
	}

	public List<BankrollProductRule> getBankrollProductRules()
	{
		return bankrollProductRules;
	}

	public void setBankrollProductRules(final List<BankrollProductRule> bankrollProductRules)
	{
		BankrollProductRulesConfig.this.bankrollProductRules = bankrollProductRules;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		try
		{
			// 1. Читаем данные конфига из базы или из файла
			BankrollProductRulesConfigBean configBean = getConfigData();
			// 2. Строим список всех правил установления видимости продуктам
			bankrollProductRules = buildRules(configBean);
		}
		catch (RuntimeException e)
		{
			bankrollProductRules = new ArrayList<BankrollProductRule>();
			counter = 0;
		}
	}

	private List<BankrollProductRule> buildRules(BankrollProductRulesConfigBean configBean)
	{
		List<BankrollProductRuleBean> ruleBeans = configBean.getRules();

		List<BankrollProductRule> rules = new ArrayList<BankrollProductRule>(ruleBeans.size());

		int i = 1;
		for (BankrollProductRuleBean bean : ruleBeans)
		{
			rules.add(buildRule(bean, i));
			i++;
		}

		counter = ruleBeans.size();

		return rules;
	}

	private BankrollProductRule buildRule(BankrollProductRuleBean ruleBean, long id)
	{
		BankrollProductRule rule = new BankrollProductRule();

		rule.setId(id);
		rule.setName(ruleBean.getName());
		rule.setActive(ruleBean.isActive());

		buildRuleCondition(ruleBean, rule);

		buildRuleAction(ruleBean, rule);

		return rule;
	}

	private void buildRuleCondition(BankrollProductRuleBean ruleBean, BankrollProductRule rule)
	{
		BankrollProductRuleConditionBean ruleConditionBean = ruleBean.getCondition();

		rule.setAgeFrom(ruleConditionBean.getAge().getFromValue());
		rule.setAgeUntil(ruleConditionBean.getAge().getUntilValue());
		rule.setClientCategory(ruleConditionBean.getClientCategory());

		rule.setTerbankCodes(ruleConditionBean.getTerbanks());

		BankrollProductCriteriaBean productCriteriaBean = ruleConditionBean.getBankrollProductCriteria();
		rule.setCreditCardCriteria(productCriteriaBean.getCreditCardCriteria());
		rule.setDepositCriteria(productCriteriaBean.getDepositCriteria());
		rule.setLoanCriteria(productCriteriaBean.getLoanCriteria());
	}

	private void buildRuleAction(BankrollProductRuleBean ruleBean, BankrollProductRule rule)
	{
		BankrollProductRuleActionBean ruleActionBean = ruleBean.getAction();

		try
		{
			ErmbTariff ermbTariff = null;
			String tariffCode = ruleActionBean.getTariff().getCode();
			if (tariffCode != null)
				ermbTariff = ermbTariffService.getTariffByCode(tariffCode);

			Long tariffId = null;
			if (ermbTariff == null)
			{
				tariffId = ruleActionBean.getTariff().getId();
				if (tariffId != null)
					ermbTariff = ermbTariffService.findById(tariffId);
			}

			if (ermbTariff == null)
				throw new ConfigurationException("Тариф ЕРМБ с кодом (code  или id) " + tariffCode != null ? tariffCode : tariffId + " не найден");

			rule.setErmbTariff(ermbTariff);
		}
		catch(BusinessException e)
		{
			throw new InternalErrorException(e);
		}

		BankrollProductDefaultsBean productDefaultBeans = ruleActionBean.getProductDefaults();

		rule.setCardsVisibility(productDefaultBeans.getCardDefault().isVisibility());
		rule.setCardsNotification(productDefaultBeans.getCardDefault().isNotification());

		rule.setDepositsVisibility(productDefaultBeans.getDepositDefault().isVisibility());
		rule.setDepositsNotification(productDefaultBeans.getDepositDefault().isNotification());

		rule.setLoansVisibility(productDefaultBeans.getLoanDefault().isVisibility());
		rule.setLoansNotification(productDefaultBeans.getLoanDefault().isNotification());

		rule.setNewProductsVisibility(productDefaultBeans.getNewProductDefault().isVisibility());
		rule.setNewProductsNotification(productDefaultBeans.getNewProductDefault().isNotification());
	}

	@Override
	protected <T> T doSave()
	{
		BankrollProductRulesConfigBean configBean = new BankrollProductRulesConfigBean();
		List<BankrollProductRuleBean> ruleBeans = new ArrayList<BankrollProductRuleBean>();

		for (BankrollProductRule rule : bankrollProductRules)
		{
			BankrollProductRuleBean ruleBean = new BankrollProductRuleBean();

			if (rule.getId() == null)
				rule.setId(Long.valueOf(counter) + 1);

			ruleBean.setName(rule.getName());
			ruleBean.setActive(rule.isActive());

			buildRuleConditionBean(rule, ruleBean);
			buildRuleActionBean(rule, ruleBean);

			ruleBeans.add(ruleBean);
		}

		configBean.setRules(ruleBeans);

		return (T) configBean;
	}

	private void buildRuleActionBean(BankrollProductRule rule, BankrollProductRuleBean ruleBean)
	{
		BankrollProductRuleActionBean actionBean = new BankrollProductRuleActionBean();
		BankrollProductDefaultsBean productDefaultsBean = new BankrollProductDefaultsBean();

		productDefaultsBean.setCardDefault(new BankrollProductDefaultsBean.ProductDefaultParameters(rule.isCardsVisibility(), rule.isCardsNotification()));
		productDefaultsBean.setDepositDefault(new BankrollProductDefaultsBean.ProductDefaultParameters(rule.isDepositsVisibility(), rule.isDepositsNotification()));
		productDefaultsBean.setLoanDefault(new BankrollProductDefaultsBean.ProductDefaultParameters(rule.isLoansVisibility(), rule.isLoansNotification()));
		productDefaultsBean.setNewProductDefault(new BankrollProductDefaultsBean.ProductDefaultParameters(rule.isNewProductsVisibility(), rule.isNewProductsNotification()));

		actionBean.setProductDefaults(productDefaultsBean);

		ErmbTariff ermbTariff = rule.getErmbTariff();
		String tariffCode = ermbTariff.getCode();
		if (tariffCode != null)
			actionBean.setTariff(new BankrollProductRuleActionBean.Tariff(String.valueOf(tariffCode)));
		else
			actionBean.setTariff(new BankrollProductRuleActionBean.Tariff(rule.getErmbTariff().getId()));

		ruleBean.setAction(actionBean);
	}

	private void buildRuleConditionBean(BankrollProductRule rule, BankrollProductRuleBean ruleBean)
	{
		BankrollProductRuleConditionBean conditionBean = new BankrollProductRuleConditionBean();
		conditionBean.setClientCategory(rule.getClientCategory());
		BankrollProductRuleConditionBean.Age age = new BankrollProductRuleConditionBean.Age();
		age.setFromValue(rule.getAgeFrom());
		age.setUntilValue(rule.getAgeUntil());
		conditionBean.setAge(age);

		conditionBean.setTerbanks(rule.getTerbankCodes());

		BankrollProductCriteriaBean productCriteriaBean = new BankrollProductCriteriaBean();
		productCriteriaBean.setCreditCardCriteria(rule.getCreditCardCriteria());
		productCriteriaBean.setDepositCriteria(rule.getDepositCriteria());
		productCriteriaBean.setLoanCriteria(rule.getLoanCriteria());

		conditionBean.setBankrollProductCriteria(productCriteriaBean);

		ruleBean.setCondition(conditionBean);
	}
}
