package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.billing.Billing;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProvider;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.fields.FieldDescription;
import com.rssl.phizic.business.fields.FieldValidationRuleImpl;
import com.rssl.phizic.business.fields.StringFieldValidationParameter;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRule;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.operations.OperationBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.rssl.phizic.gate.payments.systems.recipients.FieldValidationRuleType.REGEXP;

/**
 * @author krenev
 * @ created 08.05.2010
 * @ $Author$
 * @ $Revision$
 * операция получения полей (как гейтовых так и бизнесовых) в разрезе поставщика
 */
public class GetServiceProviderFieldsOperation extends OperationBase
{
	private ServiceProviderService serviceProviderService = new ServiceProviderService();
	private BillingServiceProvider provider;

	/**
	 * Режим "Мобильный банк"
	 */
	private boolean mobilebank = false;

	/**
	 * Проинициализировать операцию
	 * @param providerId идентифкатор поставщика
	 */
	public void initialize(Long providerId) throws BusinessException, BusinessLogicException
	{
		if (providerId == null || providerId == -1)
		{
			throw new BusinessLogicException("Не выбрано ни одного поставщика");
		}
		provider = (BillingServiceProvider) serviceProviderService.findById(providerId);
		if (provider == null)
		{
			throw new BusinessException("Не найден поставщик с идентификатором " + providerId);
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

	public boolean isMobilebank()
	{
		return mobilebank;
	}

	public void setMobilebank(boolean mobilebank)
	{
		this.mobilebank = mobilebank;
	}

	/**
	 * Возвращает ли биллинг поставщика расширенные поля поставщика.
	 * @return true - возвращает, false - нет.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public boolean isGateRecipientExtedendAttributesEnabled() throws BusinessException, BusinessLogicException
	{
		GateInfoService gateInfoService = GateSingleton.getFactory().service(GateInfoService.class);
		try
		{
			Billing billing = provider.getBilling();
			if (billing == null)
				return false;
			return gateInfoService.isRecipientExtedendAttributesAvailable(billing);
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

	/**
	 * Получить список ключевых полей поставщика из БД.
	 * @return список ключевых полей.
	 * @throws BusinessException
	 */
	public List<FieldDescription> getKeyFieldDescriptions() throws BusinessException
	{
		SimpleService service = new SimpleService();
		DetachedCriteria criteria = DetachedCriteria.forClass(FieldDescription.class);
		criteria.add(Expression.eq("holderId", provider.getId()));
		criteria.add(Expression.eq("key", true));
		List<FieldDescription> descriptions = service.find(criteria);
		// в случае МБ нужно добавить доп.валидатор для ключевого поля
		if (mobilebank)
			appendMobileBankValidators(descriptions);
		return descriptions;
	}

	/**
	 * Получить список всех полей поставщика из БД.
	 * @return список ключевых полей.
	 * @throws BusinessException
	 */
	public List<FieldDescription> getFieldDescriptions() throws BusinessException
	{
		SimpleService service = new SimpleService();
		DetachedCriteria criteria = DetachedCriteria.forClass(FieldDescription.class);
		criteria.add(Expression.eq("holderId", provider.getId()));
		List<FieldDescription> descriptions = service.find(criteria);
		// в случае МБ нужно добавить доп.валидатор для ключевого поля
		if (mobilebank)
			appendMobileBankValidators(descriptions);
		return descriptions;
	}

	private void appendMobileBankValidators(List<FieldDescription> descriptions)
	{
		for (FieldDescription description : descriptions)
		{
			if (description.isKey())
			{
				List<FieldValidationRule> rules = description.getFieldValidationRules();
				if (rules == null) {
					rules = new LinkedList<FieldValidationRule>();
					description.setFieldValidationRules(rules);
				}
				rules.add(createMobileBankValidatorRule());
			}
		}
	}

	private FieldValidationRule createMobileBankValidatorRule()
	{
		FieldValidationRuleImpl rule = new FieldValidationRuleImpl();
		rule.setFieldValidationRuleType(REGEXP);
		rule.setErrorMessage(
				"Допустимы только буквы латинского языка и цифры");
		Map<String, Object> fieldValidators = new LinkedHashMap<String, Object>(1);
		fieldValidators.put(REGEXP.name(), new StringFieldValidationParameter("[a-zA-Z0-9]+"));
		rule.setFieldValidators(fieldValidators);
		return rule;
	}

	/**
	 * Получить список ключевых полей из шлюза.
	 * @param isCard признак перевода на карту.
	 * @return поля
	 */
	public List<Field> getGateKeysFields(boolean isCard) throws BusinessException, BusinessLogicException
	{
		PaymentRecipientGateService paymentRecipientGateService = GateSingleton.getFactory().service(PaymentRecipientGateService.class);
		try
		{
			return paymentRecipientGateService.getRecipientKeyFields(provider);
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

	/**
	 * @return необходимо ли запрашивать задолженность
	 */
	public boolean isDeptAvailable()
	{
		return provider.isDeptAvailable();
	}

	/**
	 * @return необходимо ли запрашивать реквизиты онлайн
	 */
	public boolean isPropsOnline()
	{
		return provider.isPropsOnline();
	}
}
