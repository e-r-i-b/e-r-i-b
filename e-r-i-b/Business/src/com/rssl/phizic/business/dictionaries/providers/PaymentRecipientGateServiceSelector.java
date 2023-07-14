package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.fields.FieldImpl;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateInfoService;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Krenev
 * @ created 29.01.2010
 * @ $Author$
 * @ $Revision$
 * сервис-селектор.
 * Все что связано с получением поставщиков дергается из бизнеса.
 * Поля поставщика - в зависимости от возможностей адаптера.
 * В дальнейшем необходимо сделать что-то похожее на BankrollServicesXmlRouter
 */
public class PaymentRecipientGateServiceSelector extends AbstractService implements PaymentRecipientGateService
{
	private static final BankDictionaryService bankDictionaryService = new BankDictionaryService();
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();
	private PaymentRecipientGateService businessDelegate;
	private PaymentRecipientGateService gateDelegate;

	public PaymentRecipientGateServiceSelector(GateFactory factory)
	{
		super(factory);
		businessDelegate = (PaymentRecipientGateService) getDelegate(PaymentRecipientGateService.class.getName() + BUSINESS_DELEGATE_KEY);
		gateDelegate = (PaymentRecipientGateService) getDelegate(PaymentRecipientGateService.class.getName() + GATE_DELEGATE_KEY);
	}

	/**
	 * Найти получателей в биллинге по счету, БИКу и ИНН
	 * @param account счет получателя
	 * @param bic бик банка получателя
	 * @param inn инн получателя
	 * @param billing биллинг, в котором нуджно найти получателя
	 * @return список получателей удовлетворяющих результату. если получтели не найдены - пустой список
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		return gateDelegate.getRecipientList(account, bic, inn,billing);
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		return gateDelegate.getPersonalRecipientList(billingClientId, billing);
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		return gateDelegate.getPersonalRecipientFieldsValues(recipient, billingClientId);
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		ServiceProviderBase serviceProvider = getServiceProvider(recipient);
		if ((serviceProvider instanceof BillingServiceProvider) && ((BillingServiceProvider)serviceProvider).isPropsOnline())
		{
			//получаем реквизиты из гейта(онлайн).
			RecipientInfo recipientInfo = gateDelegate.getRecipientInfo(recipient, fields, debtCode);

			fillBankInfo(recipientInfo.getBank());
			return recipientInfo;
		}
		return businessDelegate.getRecipientInfo(recipient, fields, debtCode);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		return businessDelegate.getRecipient(recipientId);
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		return businessDelegate.getRecipientList(billing);
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		BillingServiceProvider provider = getServiceProvider(recipient);
		com.rssl.phizic.business.dictionaries.billing.Billing billing = provider.getBilling();
		if (billing == null )
		{
			return businessDelegate.getRecipientFields(recipient, keyFields);
		}
		GateInfoService gateService = GateSingleton.getFactory().service(GateInfoService.class);
		if (gateService.isRecipientExtedendAttributesAvailable(billing))
		{
			return mergeFields(businessDelegate.getRecipientFields(recipient, keyFields), gateDelegate.getRecipientFields(recipient, keyFields));
		}
		return businessDelegate.getRecipientFields(recipient, keyFields);
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		BillingServiceProvider provider = getServiceProvider(recipient);
		GateInfoService gateService = GateSingleton.getFactory().service(GateInfoService.class);
		com.rssl.phizic.business.dictionaries.billing.Billing billing = provider.getBilling();
		if (billing!=null && gateService.isRecipientExtedendAttributesAvailable(billing))
		{
			return mergeFields(businessDelegate.getRecipientKeyFields(recipient), gateDelegate.getRecipientKeyFields(recipient));
		}
		return businessDelegate.getRecipientKeyFields(recipient);
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		return gateDelegate.getCardOwner(cardId, billing);
	}

	/**
	 * склеить описания полей: текстовки для пользователя из бизнеса переопределяют гейтовые значения 
	 * @param businessFields поля из бизнесового справочника
	 * @param gateFields поля из адаптера
	 * @return склеенный список полей
	 */
	protected List<Field> mergeFields(List<Field> businessFields, List<Field> gateFields)
	{
		Map<String, Field> temp = new HashMap<String, Field>();
		for (Field field : businessFields)
		{
			temp.put(field.getExternalId(), field);
		}

		List<Field> fields = new ArrayList<Field>();
		for (Field field : gateFields)
		{
			Field businessField = temp.get(field.getExternalId());

			String name = null;
			if (field.getName() == null && businessField != null)
				name = businessField.getName();

			String hint = null;
			if (field.getHint() == null && businessField != null)
				hint = businessField.getHint();

			String description = null;
			if (field.getDescription() == null && businessField != null)
				description = businessField.getDescription();

			fields.add(new FieldImpl(field, name, hint, description));
		}
		return fields;
	}

	/**
	 * получить поставщика соотвествующего гейтовому представлению из БД
	 * @param recipient гейтовое представление поставщика
	 * @return BillingServiceProvider
	 * @throws GateException
	 */
	protected BillingServiceProvider getServiceProvider(Recipient recipient) throws GateException
	{
		if (recipient instanceof BillingServiceProvider)
		{
			//оптимизируем на поиске в БД.
			return (BillingServiceProvider) recipient;
		}
		try
		{
			return (BillingServiceProvider) serviceProviderService.findBySynchKey((String) recipient.getSynchKey());
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	private void fillBankInfo(ResidentBank bank) throws GateException
	{
		if (StringHelper.isEmpty(bank.getName()) && StringHelper.isEmpty(bank.getShortName()))
		{
			try
			{
				ResidentBank newBank = bankDictionaryService.findByBIC(bank.getBIC());
				if (newBank != null)
				{
					bank.setName(newBank.getName());
					bank.setShortName(newBank.getShortName());
				}
			}
			catch (BusinessException e)
			{
				throw new GateException(e);
			}
		}
	}

	/**
	 * @return инстанс бизнесовой реализации сервиса
	 */
	protected PaymentRecipientGateService getBusinessDelegate()
	{
		return businessDelegate;
	}
}
