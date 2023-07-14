package com.rssl.phizic.web.util;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.CurrencyRateType;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 30.10.2007
 * @ $Author$
 * @ $Revision$
 */

public class CurrencyRateUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * ��������� ����� ������ � ���� ��������� ������
	 * @param fromCurr   ������ ������� (���� �������)
	 * @param toCurr     ������ ������� (���� ��������)
	 * @param rateTypeInt ���� ������� ��� �������� - ��� ��������
	 * @param office     ����
	 * @return ���� ������
	 */
	public static CurrencyRate getRate(String fromCurr, String toCurr, CurrencyRateType rateTypeInt, Office office,
	                                   String tarifPlanCodeType) throws GateLogicException, GateException
	{

		CurrencyRateService service = GateSingleton.getFactory().service(CurrencyRateService.class);
		Currency fromCurrency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(fromCurr);
		Currency toCurrency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(toCurr);

		String checkedTariffPlanCodeType = TariffPlanHelper.getTariffPlanCode(tarifPlanCodeType);

		switch (rateTypeInt)
		{
			case CB:
				return service.getRate(fromCurrency, toCurrency, CurrencyRateType.CB, office, checkedTariffPlanCodeType);
			case BUY:
			case BUY_REMOTE:
				return service.getRate(fromCurrency, toCurrency, CurrencyRateType.BUY_REMOTE, office, checkedTariffPlanCodeType);
			case SALE:
			case SALE_REMOTE:
				return service.getRate(fromCurrency, toCurrency, CurrencyRateType.SALE_REMOTE, office, checkedTariffPlanCodeType);
			default:
				throw new RuntimeException("������ ��� ������� �����, ����������� ��� " + rateTypeInt);
		}
	}

	/**
	 * �������� �� ������������ ���������� � ������ �����
	 * @param department ����������� ��� �������� ����� ���������� ��� �����������
	 * @return true - ��������; false - ���
	 */
	public static boolean isRateAccess(Department department)
	{
		try
		{
			if (department == null)
				return false;
			GateInfoService service = GateSingleton.getFactory().service(GateInfoService.class);

			return service.isCurrencyRateAvailable(department);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ����������� ������������ ���������� � ������ �����", e);
			return false;
		}
	}

	/**
	 * �� ����� � �������� �������� (����� ������ ����� �������) ���������� ����
	 * @param rate ����
	 * @param scale �������� (����� ������ ����� �������)
	 *
	 * @return String - �����, ���������� ��������� ����� ������� ������ <rate.from> � <������ rate.to> 
	 * */
	public static String format ( CurrencyRate rate, int  scale)
	{
		try
		{
			if (rate==null)
			{
				return "";
			}

			BigDecimal factor = rate.getToValue().divide(rate.getFromValue(), scale, CurrencyRate.ROUNDING_MODE);
			return factor.toString();
		}
		catch (Exception e)
		{
			log.error("������ �������������� �������� � ������ CurrencyRate", e);

			return "";
		}
	}

	public static CurrencyRateType inverseCurrencyRateType(CurrencyRateType type)
	{
		switch(type)
		{
			case CB:
				return CurrencyRateType.CB;
			case BUY:
				return CurrencyRateType.SALE;
			case SALE:
				return CurrencyRateType.BUY;
			case BUY_REMOTE:
				return CurrencyRateType.SALE_REMOTE;
			case SALE_REMOTE:
				return CurrencyRateType.SALE_REMOTE;
		}
		throw new IllegalArgumentException("����������� ��� �����" + type);
	}

	/**
	 * ��������� ����� ������ � ���� ��������� �������������
	 * @param fromCurr   ������ ������� (���� �������)
	 * @param toCurr     ������ ������� (���� ��������)
	 * @param rateType   ���� ������� ��� �������� - ��� ��������
	 * @param department �����������
	 * @return ���� ������
	 */
	public static CurrencyRate getRate(String fromCurr, String toCurr, String rateType, Department department,
	                                   String tarifPlanCodeType)
	{
		if (department == null)
		{
			log.error("������ ��������� ����� �����: �� ������� �����������.");
			return null;
		}
		try
		{
			return getRate(fromCurr, toCurr, CurrencyRateType.valueOf(rateType), department, tarifPlanCodeType);
		}
		catch (Exception e)
		{
			log.error("������ ��������� ����� �����: fromCurrency:" + fromCurr + "  toCurrency:" + toCurr + "  CurrencyRateType:" + rateType, e);
			return null;
		}
	}
}
