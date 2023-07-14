package com.rssl.phizic.business.commission;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 11.09.2007
 * @ $Author: Evgrafov $
 * @ $Revision: 4951 $
 */

@SuppressWarnings({"JavaDoc"}) public class CommissionTypeTest extends BusinessTestCaseBase
{
	private static final String TEST_KEY = "test.c." + System.currentTimeMillis();
	private static final SimpleService simpleService = new SimpleService();

	private CommissionType ct;
	private List<CommissionRule> allRules;

	@Override protected void setUp() throws Exception
	{
		super.setUp();
		ct = new CommissionType();
		ct.setKey(TEST_KEY);
		allRules = new ArrayList<CommissionRule>();
	}

	@Override protected void tearDown() throws Exception
	{
		simpleService.removeList(allRules);
		if(ct != null && ct.getId() != null)
			simpleService.remove(ct);

		ct = null;
		allRules = null;
		super.tearDown();
	}

	public void testCRUD() throws Exception
	{
		ct.setName("проверка");
		simpleService.add(ct);

		GateRule gateRule = new GateRule();
		gateRule.setCurrencyCode("USD");
		gateRule.setType(ct);
		simpleService.add(gateRule);
		allRules.add(gateRule);

		FixedRule fixedRule = new FixedRule();
		fixedRule.setType(ct);
		fixedRule.setCurrencyCode("RUB");
		fixedRule.setAmount(new BigDecimal("10.8"));
		simpleService.add(fixedRule);
		allRules.add(fixedRule);

		RateRule rateRule = new RateRule();
		rateRule.setType(ct);
		rateRule.setCurrencyCode("EUR");
		rateRule.setRate(new BigDecimal("23.3444"));
		rateRule.setMinAmount(new BigDecimal("12.34"));
		rateRule.setMaxAmount(new BigDecimal("99999.34"));
		simpleService.add(rateRule);
		allRules.add(rateRule);
	}

	public void testService() throws BusinessException
	{
		CommissionService commissionService = new CommissionService();

		ct.setName("from service");
		commissionService.saveType(ct);

		FixedRule fixedRule = new FixedRule();
		fixedRule.setCurrencyCode("RUB");
		fixedRule.setAmount(new BigDecimal("23"));
		commissionService.updateRules(ct, Arrays.asList(fixedRule));

		GateRule gateRule1 = new GateRule();
		gateRule1.setCurrencyCode("RUB");
		GateRule gateRule2 = new GateRule();
		gateRule2.setCurrencyCode("USD");
		commissionService.updateRules(ct, Arrays.asList(gateRule1, gateRule2));
		allRules.add(gateRule1);
		allRules.add(gateRule2);

		assertNotNull(commissionService.findRule(ct, "RUB"));
		assertNotNull(commissionService.findRule(ct, "USD"));
		assertNull(commissionService.findRule(ct, "EUR"));

		List<? extends CommissionRule> rules = commissionService.findRules(ct);
		assertNotNull(rules);

		CommissionType ct2 = commissionService.findTypeById(ct.getId());
		assertNotNull(ct2);

		CommissionType ct3 = commissionService.findTypeByKey(ct.getKey());
		assertNotNull(ct3);
	}

	public void manualCreateTestData() throws BusinessException
	{
		CommissionService commissionService = new CommissionService();

		addFixed(commissionService);
		addGate(commissionService);
		addRate(commissionService);

		ct = null;
	}

	private void addGate(CommissionService commissionService) throws BusinessException
	{
		ct = new CommissionType();
		ct.setKey("comm2");
		ct.setName("Комиссия (Gate)");
		commissionService.saveType(ct);

		GateRule ruleEUR = new GateRule();
		ruleEUR.setCurrencyCode("EUR");

		GateRule ruleRUB = new GateRule();
		ruleRUB.setCurrencyCode("RUB");

		GateRule ruleUSD = new GateRule();
		ruleUSD.setCurrencyCode("USD");

		commissionService.updateRules(ct, Arrays.asList(ruleUSD, ruleRUB, ruleEUR));
	}

	private void addFixed(CommissionService commissionService) throws BusinessException
	{
		ct = new CommissionType();
		ct.setKey("comm1");
		ct.setName("Комиссия (Fixed)");
		commissionService.saveType(ct);

		FixedRule ruleEUR = new FixedRule();
		ruleEUR.setCurrencyCode("EUR");
		ruleEUR.setAmount(new BigDecimal("444.90"));

		FixedRule ruleRUB = new FixedRule();
		ruleRUB.setCurrencyCode("RUB");
		ruleRUB.setAmount(new BigDecimal("23"));

		FixedRule ruleUSD = new FixedRule();
		ruleUSD.setCurrencyCode("USD");
		ruleUSD.setAmount(new BigDecimal("44.90"));

		commissionService.updateRules(ct, Arrays.asList(ruleUSD, ruleRUB, ruleEUR));
	}

	private void addRate(CommissionService commissionService) throws BusinessException
	{
		ct = new CommissionType();
		ct.setKey("comm3");
		ct.setName("Комиссия (Rate)");
		commissionService.saveType(ct);

		RateRule ruleEUR = new RateRule();
		ruleEUR.setCurrencyCode("EUR");
		ruleEUR.setRate(new BigDecimal("12.2345"));
		ruleEUR.setMinAmount(new BigDecimal("444.90"));
		ruleEUR.setMaxAmount(new BigDecimal("1444.90"));

		RateRule ruleRUB = new RateRule();
		ruleRUB.setRate(new BigDecimal("2.2345"));
		ruleRUB.setCurrencyCode("RUB");
		ruleRUB.setMinAmount(new BigDecimal("23"));
		ruleRUB.setMaxAmount(new BigDecimal("1123"));

		RateRule ruleUSD = new RateRule();
		ruleUSD.setRate(new BigDecimal("212.2345"));
		ruleUSD.setCurrencyCode("USD");
		ruleUSD.setMinAmount(new BigDecimal("44.90"));
		ruleUSD.setMaxAmount(new BigDecimal("11144.90"));

		commissionService.updateRules(ct, Arrays.asList(ruleUSD, ruleRUB, ruleEUR));
	}
}