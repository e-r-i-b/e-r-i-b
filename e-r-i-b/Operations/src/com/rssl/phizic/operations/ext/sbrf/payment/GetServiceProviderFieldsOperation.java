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
 * �������� ��������� ����� (��� �������� ��� � ����������) � ������� ����������
 */
public class GetServiceProviderFieldsOperation extends OperationBase
{
	private ServiceProviderService serviceProviderService = new ServiceProviderService();
	private BillingServiceProvider provider;

	/**
	 * ����� "��������� ����"
	 */
	private boolean mobilebank = false;

	/**
	 * ������������������� ��������
	 * @param providerId ������������ ����������
	 */
	public void initialize(Long providerId) throws BusinessException, BusinessLogicException
	{
		if (providerId == null || providerId == -1)
		{
			throw new BusinessLogicException("�� ������� �� ������ ����������");
		}
		provider = (BillingServiceProvider) serviceProviderService.findById(providerId);
		if (provider == null)
		{
			throw new BusinessException("�� ������ ��������� � ��������������� " + providerId);
		}
	}

	/**
	 *
	 * @return ��������� �����
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
	 * ���������� �� ������� ���������� ����������� ���� ����������.
	 * @return true - ����������, false - ���.
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
	 * �������� ������ �������� ����� ���������� �� ��.
	 * @return ������ �������� �����.
	 * @throws BusinessException
	 */
	public List<FieldDescription> getKeyFieldDescriptions() throws BusinessException
	{
		SimpleService service = new SimpleService();
		DetachedCriteria criteria = DetachedCriteria.forClass(FieldDescription.class);
		criteria.add(Expression.eq("holderId", provider.getId()));
		criteria.add(Expression.eq("key", true));
		List<FieldDescription> descriptions = service.find(criteria);
		// � ������ �� ����� �������� ���.��������� ��� ��������� ����
		if (mobilebank)
			appendMobileBankValidators(descriptions);
		return descriptions;
	}

	/**
	 * �������� ������ ���� ����� ���������� �� ��.
	 * @return ������ �������� �����.
	 * @throws BusinessException
	 */
	public List<FieldDescription> getFieldDescriptions() throws BusinessException
	{
		SimpleService service = new SimpleService();
		DetachedCriteria criteria = DetachedCriteria.forClass(FieldDescription.class);
		criteria.add(Expression.eq("holderId", provider.getId()));
		List<FieldDescription> descriptions = service.find(criteria);
		// � ������ �� ����� �������� ���.��������� ��� ��������� ����
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
				"��������� ������ ����� ���������� ����� � �����");
		Map<String, Object> fieldValidators = new LinkedHashMap<String, Object>(1);
		fieldValidators.put(REGEXP.name(), new StringFieldValidationParameter("[a-zA-Z0-9]+"));
		rule.setFieldValidators(fieldValidators);
		return rule;
	}

	/**
	 * �������� ������ �������� ����� �� �����.
	 * @param isCard ������� �������� �� �����.
	 * @return ����
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
	 * @return ���������� �� ����������� �������������
	 */
	public boolean isDeptAvailable()
	{
		return provider.isDeptAvailable();
	}

	/**
	 * @return ���������� �� ����������� ��������� ������
	 */
	public boolean isPropsOnline()
	{
		return provider.isPropsOnline();
	}
}
