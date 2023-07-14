package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 05.05.2010
 * @ $Author$
 * @ $Revision$
 * Операция получения списка задолженностей клиента перед поставщиком услуг
 */
public class GetDebtsOperation extends OperationBase
{
	private static final ServiceProviderService providerService = new ServiceProviderService();
	private BillingServiceProvider provider;

	/**
	 * Инициализация операции
	 * @param providerId идентификатор поставщика
	 * @throws BusinessException
	 */
	public void initialize(Long providerId) throws BusinessException, BusinessLogicException
	{
		if (providerId == null)
		{
			throw new IllegalArgumentException("Не задан идентифкатор поставщика услуг");
		}
		provider = (BillingServiceProvider) providerService.findById(providerId);
		if (provider == null)
		{
			throw new BusinessException("Не найден поставщик услуг с идентфикатором " + providerId);
		}
	}

	/**
	 *
	 * @return поставщик услуг
	 */
	public BillingServiceProvider getProvider()
	{
		return provider;
	}

	/**
	 * Получить список задолженностей
	 * @param data значения ключевых полей
	 * @return список задолженностей или пустой список в при их отсутсвии.
	 */
	public List<Debt> getDebts(Map<String, String> data) throws BusinessException, BusinessLogicException
	{
		if (!provider.isDeptAvailable())
		{
			//поставщик не поддерживает задолженноть -> задолженностей нет
			return new ArrayList<Debt>();
		}
		DebtService debtService = GateSingleton.getFactory().service(DebtService.class);
		List<Field> fields = new ArrayList<Field>();
		for (Map.Entry<String, String> entry : data.entrySet())
		{
			FieldImpl field = new FieldImpl();
			field.setExternalId(entry.getKey());
			field.setKey(true);
			field.setRequired(true);
			field.setValue(entry.getValue());
			fields.add(field);
		}
		try
		{
			return debtService.getDebts(provider, fields);
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}
}
