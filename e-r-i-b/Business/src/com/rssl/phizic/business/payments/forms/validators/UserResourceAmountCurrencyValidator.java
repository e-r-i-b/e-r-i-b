package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.*;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.currency.CurrencyRateService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.CurrencyUtils;
import com.rssl.phizic.utils.DocumentConfig;

import java.util.Map;

/**
 * ��������, ��� ����� ���������(�����) �� ������, ��� �����������
 * �������� �������� � ������, ��� ����� ����� ���� � ������ �������
 * @author Mescheryakova
 * @ created 24.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class UserResourceAmountCurrencyValidator  extends  UserResourceAmountValidator
{
	private static final String FIELD_CURRENCY = "currency";
	private static final String FIELD_DEPARTMENT_ID = "currentDepartmentId";

	private final CurrencyRateService currencyRateService= GateSingleton.getFactory().service(CurrencyRateService.class);
	private final DepartmentService departmentService = new DepartmentService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		ExternalResourceLink resourceLink = (ExternalResourceLink) retrieveFieldValue(FIELD_RESOURCE, values);
		if (isStoredResource(resourceLink))
			return true;

		String currencyAmount = (String) retrieveFieldValue(FIELD_CURRENCY, values);
		if (currencyAmount == null)
			throw new TemporalDocumentException("�� ������ ������ �����");
		
		CardLink cardLink = (CardLink) retrieveFieldValue(FIELD_RESOURCE, values);
		Currency currencyCard = getCardCurrency(cardLink);

		// ���� ������ ����� � ������ ���������, �� ������� �������������� �� ���������
		if (currencyAmount.equals(currencyCard.getCode()))
		{
			return super.validate(values);
		}

		// ����������� ��������� ����� � ������ ��� ������
		try
		{
			CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
			Currency currency = currencyService.findByAlphabeticCode(currencyAmount);
			Money money = new Money(getExpectedAmount(values), currency);
			Long departmentId = Long.valueOf((String) retrieveFieldValue(FIELD_DEPARTMENT_ID, values));
			Department department = departmentService.findById(departmentId);

			if (money == null || currencyCard == null || department == null)
				throw new TemporalDocumentException("������������ ������ ��� ����������� ����� � ������ �����");

			CurrencyRate currencyRate = convertByCB(money, currencyCard, department);
			if (currencyRate == null)
				throw new TemporalDocumentException("�� ������� �������� ���� �����");

			Money moneyConvert = new Money(currencyRate.getToValue(), currencyRate.getToCurrency());

			if (moneyConvert == null)
				throw new TemporalDocumentException("����� ����������� ����� � ������ ����� ��������� null");

			return getCardAmount(cardLink).compareTo(moneyConvert.getDecimal()) >= 0;
		}
		catch(BusinessException e)
		{
			throw new TemporalDocumentException("������ ����������� ����� � ������ �����", e);
		}
		catch(GateException e)
		{
			throw new TemporalDocumentException("������ ��������� ������ �����", e);
		}
	}

	protected Currency getCardCurrency(CardLink cardLink) throws TemporalDocumentException
	{
		Card card = getCard(cardLink);
		return card.getCurrency();

	}

	/**
	 * ���������� ���� �� �� �� + 5%, ���� ���� ����� ��� �� �� �������, �� ���� ��� �� �. ������ + 5%
	 * @param from  - ������, �� ������� ������������
	 * @param to  - ������, � ������� ������������
	 * @param office - ����, ��� �� �������� ����� �������� ����
	 * @return ��������� ���� �����, ���� null
	 * @throws BusinessException
	 */	
	private CurrencyRate convertByCB(Money from, Currency to, Department office) throws BusinessException
	{
		if (office == null)
			return null;

		Department department;
		try
		{
			// �������� ��
			department = departmentService.getTB(office);
		}
		catch(BusinessException e)
		{
			return null;
		}

		CurrencyRate rate = null;

		try
		{
			String tarifPlanCodeType = !PermissionUtil.impliesService("ReducedRateService") ?
					TariffPlanHelper.getUnknownTariffPlanCode() : PersonHelper.getActivePersonTarifPlanCode();
			// �������� ���� ��������� �� �� �� � ��������� �����
			rate = currencyRateService.getRate(from.getCurrency(), to, CurrencyRateType.CB, department,
					tarifPlanCodeType);
		}
		catch(GateLogicException e)
		{
			throw new BusinessException("������ ��������� ������ �����", e);
		}
		catch(GateException e)
		{
			throw new BusinessException("������ ��������� ������ �����", e);
		}

		String regionCode = ConfigFactory.getConfig(DocumentConfig.class).getCbTbMoscow();

		if (rate == null && !department.getCode().getFields().get("region").equals(regionCode))
		{
			try
			{
				// �������� �� ������
				department = departmentService.getDepartmentTBByTBNumber(regionCode);
			}
			catch(BusinessException e)
			{
				return null;
			}

			// ����������� ��� �����
			return convertByCB(from, to, department);
		}

		if (rate != null)
		{
			rate = CurrencyUtils.convertAddProcent(rate);
			rate = CurrencyUtils.getFromCurrencyRate(from.getDecimal(), rate);
		}
		return rate;
	}
}
