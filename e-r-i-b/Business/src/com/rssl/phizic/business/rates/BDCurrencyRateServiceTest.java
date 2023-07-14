package com.rssl.phizic.business.rates;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.DateHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @ author: filimonova
 * @ created: 07.10.2010
 * @ $Author$
 * @ $Revision$
 * Тест для проверки BDCurrencyRateService - сервиса взаимодействия с табличкой Rate
 */
public class BDCurrencyRateServiceTest extends BusinessTestCaseBase
{
	public static BDCurrencyRateService rateService = new BDCurrencyRateService();
	public static DepartmentService departmentService = new DepartmentService();

	/**
	 * Проверка работы кросс-курса. Случай с одинаковой валютой конвертации
	 */
	public void testCrossRateConvertion1() throws BusinessException, GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency from = currencyService.findByAlphabeticCode("USD");
		Currency to = currencyService.findByAlphabeticCode("USD");

		Department department = departmentService.findById(87L);

	    Rate rate = rateService.getRate(DateHelper.getCurrentDate(), from, to, department, CurrencyRateType.BUY_REMOTE,
			    TariffPlanHelper.getUnknownTariffPlanCode());
		if (rate!=null)
		{
			assertEquals(rate.getFromCurrency(), from);
			assertEquals(rate.getToCurrency(), to);
			assertEquals(rate.getFromValue().setScale(4), rate.getToValue().setScale(4));
		}
	}

	/**
	 * Тест на поиск курса: случай с нулевым департаментом
	 */
	public void testGetRate1() throws GateException, BusinessException
	{
		Rate rateWithoutDepartment = getRate(null);
		// Пишем в базу курс с нулевым департаментом
		rateService.addOrUpdate(rateWithoutDepartment);
		// Пытаемся найти курс с нулевым департеменом (должен вернуться rate1)
		Rate rate = rateService.getRate(Calendar.getInstance(), rateWithoutDepartment.getFromCurrency(),
				rateWithoutDepartment.getToCurrency(), null, rateWithoutDepartment.getCurrencyRateType(),
				TariffPlanHelper.getUnknownTariffPlanCode());

		assertEquals(rate.getDepartment(), null);
	}

	/**
	 * Тест на поиск курса: случай с TB-0-0
	 */
	public void testGetRate2() throws GateException, BusinessException
	{
		Rate ordinaryRate = getRate(departmentService.findById(1L));
		rateService.addOrUpdate(ordinaryRate);
		Rate rate = rateService.getRate(Calendar.getInstance(), ordinaryRate.getFromCurrency(), ordinaryRate.getToCurrency(),
				ordinaryRate.getDepartment(), ordinaryRate.getCurrencyRateType(), TariffPlanHelper.getUnknownTariffPlanCode());

		assertEquals(rate.getCurrencyRateType(), ordinaryRate.getCurrencyRateType());
	}

	/**
	 * Тест на поиск курса: поиск по подчиненному департменту
	 */
	public void testGetRate3() throws GateException, BusinessException
	{
		Rate ordinaryRate = getRate(departmentService.findById(1L));
		rateService.addOrUpdate(ordinaryRate);
		Rate rate = rateService.getRate(Calendar.getInstance(), ordinaryRate.getFromCurrency(), ordinaryRate.getToCurrency(),
				departmentService.findById(87L), ordinaryRate.getCurrencyRateType(), TariffPlanHelper.getUnknownTariffPlanCode());

		assertEquals(rate.getCurrencyRateType(), ordinaryRate.getCurrencyRateType());
	}

    /**
     * Тест на поиск курса ЦБ:
     * */
	public void testGetRateCB() throws BusinessException, GateException
    {
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Currency usdCur = currencyService.findByAlphabeticCode("USD");
	    Currency rurCur = currencyService.findByAlphabeticCode("RUR");
	    Currency eurCur = currencyService.findByAlphabeticCode("EUR");

	    Rate rateOne = rateService.getRate(Calendar.getInstance(), usdCur, rurCur, departmentService.findById(1L),
			    CurrencyRateType.CB, TariffPlanHelper.getUnknownTariffPlanCode());

	    // 1. Вернувшийся тип курса = CB
	    assertEquals(rateOne.getCurrencyRateType(), CurrencyRateType.CB);
	    // 2. Валюта from = usdCur
	    assertEquals(rateOne.getFromCurrency().compare(usdCur), true);
	    // 3. Валюта to = rurCur
	    assertEquals(rateOne.getToCurrency().compare(rurCur), true);

	    Rate rateTwo = rateService.getRate(Calendar.getInstance(), rurCur, usdCur, departmentService.findById(1L), CurrencyRateType.CB,
			    TariffPlanHelper.getUnknownTariffPlanCode());

	    // 1. Вернувшийся тип курса = CB
	    assertEquals(rateTwo.getCurrencyRateType(), CurrencyRateType.CB);
	    // 2. Валюта from = rurCur
	    assertEquals(rateTwo.getFromCurrency().compare(rurCur), true);
	    // 3. Валюта to = usdCur
	    assertEquals(rateTwo.getToCurrency().compare(usdCur), true);

	    Rate rateThree = rateService.getRate(Calendar.getInstance(), eurCur, usdCur, departmentService.findById(1L),
			    CurrencyRateType.CB, TariffPlanHelper.getUnknownTariffPlanCode());

	    // 1. Вернувшийся тип курса = CB
	    assertEquals(rateThree.getCurrencyRateType(), CurrencyRateType.CB);
	    // 2. Валюта from = eurCur
	    assertEquals(rateThree.getFromCurrency().compare(eurCur), true);
	    // 3. Валюта to = usdCur
	    assertEquals(rateThree.getToCurrency().compare(usdCur), true);
    }

	private Rate getRate(Department department) throws GateException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		Rate rateWithoutDepartment = new Rate();
		rateWithoutDepartment.setFromCurrency(currencyService.findByAlphabeticCode("USD"));
		rateWithoutDepartment.setToCurrency(currencyService.findByAlphabeticCode("RUR"));
		rateWithoutDepartment.setFromValue(new BigDecimal(1));
		rateWithoutDepartment.setToValue(new BigDecimal(30));
		rateWithoutDepartment.setCurrencyRateType(CurrencyRateType.BUY_REMOTE);
		rateWithoutDepartment.setCreationDate(Calendar.getInstance());
		rateWithoutDepartment.setDepartment(department);
		rateWithoutDepartment.setEffDate(DateHelper.getPreviousMonth());
		return rateWithoutDepartment;
	}

}
