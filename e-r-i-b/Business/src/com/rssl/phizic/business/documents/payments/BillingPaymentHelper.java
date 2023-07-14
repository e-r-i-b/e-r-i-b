package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.dictionaries.providers.*;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Хелпер для обработки биллинговых платежей
 *
 * @author bogdanov
 * @ created 21.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class BillingPaymentHelper
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	/**
	 * Возвращает описание поля главной суммы.
	 * 
	 * @param serviceProvider
	 * @return
	 * @throws BusinessException
	 */
	public static FieldDescription getMainSumFieldDescription(BillingServiceProviderBase serviceProvider)
			throws BusinessException
	{
		List<FieldDescription> fields = serviceProvider.getFieldDescriptions();
		for (FieldDescription fd : fields)
		{
			if (fd.isMainSum())
			{
				return fd;
			}
		}
		return null;
	}

	/**
	 * Получить расширенное поле из списка расширенных полей по идентфикатору.
	 * @param extendedFields расширенные поля
	 * @param fieldId имя поля
	 * @return значение поля или null, если отсутсвует.
	 */
	public static Field getFieldById(List<Field> extendedFields, String fieldId)
	{
		if (extendedFields == null)
		{
			return null;
		}
		for (Field field : extendedFields)
		{
			if (fieldId.equals(field.getExternalId()))
			{
				return field;
			}
		}
		return null;
	}

	/**
	 * @param payment платеж.
	 * @return список ключевых полей поставщика.
	 */
	public static List<Field> getKeyFields(JurPayment payment) throws BusinessException
	{
		try
		{
			List<Field> keyFields = new ArrayList<Field>();

			List<Field> extendedFields = payment.getExtendedFields();
			if (extendedFields != null)
			{
				for (Field field : extendedFields)
				{
					if (!field.isKey())
						continue;

					keyFields.add(field);
				}
			}

			return keyFields;
		}
		catch(DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param document платеж.
	 * @return является ли платеж платежом по "своему" номеру телефона в счет поставщика мобильной связи.
	 */
	public static boolean isSelfMobileNumberPayment(BusinessDocument document) throws BusinessException, BusinessLogicException
    {
		try
		{
			//Платеж должен быть JurPayment, если мы оплачиваем услуги мобильной свзяи.
			if (!(document instanceof JurPayment))
				return false;

			JurPayment payment = (JurPayment) document;
			//Оплата в адрес поставщиков мобильной связи идет через iqwave.
			if (!ESBHelper.isIQWavePayment(payment))
				return false;

			ServiceProviderShort serviceProvider = ServiceProviderHelper.getServiceProvider(payment.getReceiverInternalId());
			//Должен быть биллинговый поставщик.
			if (serviceProvider == null || !(serviceProvider.getKind().equals("B")))
				return false;

			ProvidersConfig providerConfig = ConfigFactory.getConfig(ProvidersConfig.class);
			//Поставщик услуг должен содержаться в списке доступных поставщиков услуг.
			if (!providerConfig.getMobileProviderIds().contains(getProviderIdentCode(serviceProvider)))
	            return false;

			List<Field> keyFields = getKeyFields(payment);
			//У поставщика iqwave должно быть одно ключевое поле.
			if (keyFields.size() != 1)
				return false;

			//Проверяем, есть ли среди номеров мобильного банка введенный пользователем номер для оплаты.
			PhoneNumber phoneNumber = PhoneNumber.fromString((String) keyFields.get(0).getValue());
			BusinessDocumentOwner documentOwner = payment.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			Collection<String> phones = StaticPersonData.getPhones(documentOwner.getLogin(), false);
			if(CollectionUtils.isEmpty(phones))
				return false;

			for (String phone : phones)
			{
				if (PhoneNumber.fromString(phone).equals(phoneNumber))
				{
					payment.setSelfMobileNumberPayment(true);
					return true;
				}
			}

			return false;
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param provider поставщик услуг.
	 * @return код_поставщика;код_услуги.
	 */
	private static String getProviderIdentCode(ServiceProviderShort provider)
	{
		return provider.getCode() + ProvidersConfig.PROVIDER_PROPERTY_DELIMITER + provider.getCodeService();
	}

	/**
	 * Из всех полей поставщика вытянуть только ключевые
	 * @param provider поставщик
	 * @return список ключевых полей поставщика
	 */
	public static List<FieldDescription> getKeyFieldDescription(ServiceProviderBase provider)
	{
		List<FieldDescription> fields = provider.getFieldDescriptions();
		List<FieldDescription> result = new ArrayList<FieldDescription>();

		if(CollectionUtils.isEmpty(fields))
			return result;

		for(FieldDescription field : fields)
		{
			if(field.isKey())
				result.add(field);
		}

		return result;
	}
}
