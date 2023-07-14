package com.rssl.phizic.operations.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderType;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Erkin
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Операция получения значений полей персонального поставщика
 */
public class GetPersonalPaymentFieldValuesOperation extends PersonalPaymentCardOperationBase
{
	private static final PaymentRecipientGateService paymentRecipientService
			= GateSingleton.getFactory().service(PaymentRecipientGateService.class);

	private static final ServiceProviderService providerService
			= new ServiceProviderService();

	private Map<String, String> fieldValues;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Инициализация операции
	 * @param personId - ID пользователя
	 * @param serviceProviderId - ID поставщика
	 */
	public void initialize(Long personId, Long serviceProviderId)
			throws BusinessException, BusinessLogicException
	{
		super.initialize(personId);
		if (serviceProviderId == null)
			throw new NullPointerException("Argument 'serviceProviderId' cannot be null");

		// 1. Получаем карту персональных платежей
		// для определения ID клиента во внешней системе
		PersonalPaymentCard card = getCard();

		// 1.1 Карты нет => закругляемся пустым списком
		if (card == null) {
			fieldValues = Collections.emptyMap();
			return;
		}

		// 1.2 Надо проверить, что персональные платежи доступны
		requirePersonalPaymentsAvailable(getLink().getBilling());

		String clientId = card.getCardNumber();

		// 2. Загружаем поставщика для определения:
		//  - кода постащика во внешней системе (= "<идент.поставщика>|<маршрутизация>")
		//  - кода услуги во внешней системе
		BillingServiceProvider provider = getBillingServiceProvider(serviceProviderId);
		String serviceCode = provider.getCodeService();

		// 3. Определяем услугу по коду из п.2

		// 4. Получаем значения полей из шлюза
		fieldValues = getRecipientFieldValues(provider,  clientId);
	}

	private BillingServiceProvider getBillingServiceProvider(Long serviceProviderId)
			throws BusinessException
	{
		ServiceProviderBase provider = providerService.findById(serviceProviderId);
		if (provider == null)
			throw new BusinessException("Не найден поставщик с ID " + serviceProviderId);

		if (provider.getType() != ServiceProviderType.BILLING)
			throw new BusinessException(
					"Поставщик " + serviceProviderId + " не является поставщиком услуг");

		if (!(provider instanceof BillingServiceProvider))
			throw new BusinessException(
					"Поставщик " + serviceProviderId + " не является поставщиком услуг");

		return (BillingServiceProvider) provider;
	}

	private Map<String, String> getRecipientFieldValues(
			Recipient recipient,
			String clientId
	) throws BusinessException, BusinessLogicException
	{
		try
		{
			List<Field> fields = paymentRecipientService
					.getPersonalRecipientFieldsValues(recipient, clientId);

			Map<String, String> values = new HashMap<String, String>(fields.size());
			for (Field field: fields)
			{
				Object value = field.getValue();
				if (value != null)
					values.put(field.getExternalId(), value.toString());
			}
			return values;
		}
		catch (GateException ex)
		{
			throw new BusinessException(ex);
		}
		catch (GateLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	/**
	 * @return мап значений полей
	 */
	public Map<String, String> getFieldValues()
	{
		if (fieldValues == null)
			return null;
		else return Collections.unmodifiableMap(fieldValues);
	}
}
