package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.dictionaries.providers.BillingServiceProviderBase;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.utils.StringHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 18.03.2011
 * @ $Author$
 * @ $Revision$
 * Билдер (модификатор) полей основных полей - банковских реквизитов вычислимыми полями
 */
class ReceiverInfoFieldsBuilder implements FieldsBuilder
{
	public static final BankDictionaryService bankService = new BankDictionaryService();

	public static final String BANK_DETAILS_ATTRIBUTE_NAME = "bankDetails";
	private Form form;
	private String providerExternalId;
	private Long providerInternalId;
	private boolean needBankDetails = true; //признак необходимости выводить банковские реквизиты.
	private String receiverINN;
	private String receiverAccount;
	private String receiverBIC;
	private String receiverName;
	private String receiverKPP;
	private String receiverBankName;
	private String receiverCorAccount;
	private String billingCode;
	private String nameOnBill;
	private String depoLinkId; //поле добавлено в рамках запроса BUG032392
	private String receiverDescription;
	private String receiverPhoneNumber;

	ReceiverInfoFieldsBuilder(Metadata metadata, BillingServiceProviderBase provider)
	{
		form = metadata.getForm();
		providerExternalId = (String) provider.getSynchKey();
		providerInternalId = provider.getId();
		needBankDetails = provider.isBankDetails();
		receiverName = provider.getName();
		receiverDescription = provider.getDescription();
		receiverINN = provider.getINN();
		receiverKPP = provider.getKPP();
		receiverAccount = provider.getAccount();
		receiverBIC = provider.getBIC();
		receiverBankName = provider.getBankName();
		receiverCorAccount = provider.getCorrAccount();
		receiverPhoneNumber = provider.getPhoneNumber();
		billingCode = provider.getBilling().getCode();
		nameOnBill = provider.getNameOnBill();
	}

	ReceiverInfoFieldsBuilder(Metadata metadata, String externalProviderKey, String receiverINN, String receiverAccount, String receiverBIC, String receiverName, String billingCode, Long receiverInternalId) throws BusinessException
	{
		form = metadata.getForm();
		providerExternalId = externalProviderKey;
		providerInternalId = receiverInternalId;
		this.receiverName = receiverName;
		this.receiverINN = receiverINN;
		this.receiverAccount = receiverAccount;
		this.receiverBIC = receiverBIC;
		this.nameOnBill = receiverName;
		this.billingCode = billingCode;

		ResidentBank bank = bankService.findByBIC(receiverBIC);
		if (bank == null)
		{
			throw new BusinessException("Не найден банк в справочнике с БИКом " + receiverBIC);
		}
		receiverBankName = bank.getName();
		receiverCorAccount = bank.getAccount();
	}

	ReceiverInfoFieldsBuilder(Metadata metadata, String externalProviderKey, String receiverINN, String receiverAccount, String receiverName, String billingCode, Long receiverInternalId, ResidentBank resBank) throws BusinessException
	{
		form = metadata.getForm();
		providerExternalId = externalProviderKey;
		providerInternalId = receiverInternalId;
		this.receiverName = receiverName;
		this.receiverINN = receiverINN;
		this.receiverAccount = receiverAccount;
		this.receiverBIC = resBank.getBIC();
		this.nameOnBill = receiverName;
		this.billingCode = billingCode;
		receiverBankName = resBank.getName();
		receiverCorAccount = resBank.getAccount();
	}

	ReceiverInfoFieldsBuilder(Metadata metadata, JurPayment payment)
	{
		form = metadata.getForm();
		providerExternalId = payment.getReceiverPointCode();
		providerInternalId = payment.getReceiverInternalId();
		receiverName = payment.getReceiverName();
		receiverDescription = payment.getReceiverDescription();
		receiverINN = payment.getReceiverINN();
		receiverKPP = payment.getReceiverKPP();
		receiverAccount = payment.getReceiverAccount();
		receiverBIC = payment.getReceiverBIC();
		receiverBankName = payment.getReceiverBankName();
		receiverCorAccount = payment.getReceiverCorAccount();
		receiverPhoneNumber = payment.getReceiverPhone();
		needBankDetails = !payment.isNotVisibleBankDetails();
		billingCode = payment.getBillingCode();
		nameOnBill = payment.getReceiverNameForBill();
		depoLinkId = payment.getDepoLinkId();
	}

	public List<Field> buildFields() throws BusinessException
	{
		List<Field> fields = new ArrayList<Field>();
		for (Field field : form.getFields())
		{
			if (PaymentFieldKeys.PROVIDER_EXTERNAL_KEY.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, providerExternalId));
			}
			else if (PaymentFieldKeys.PROVIDER_KEY.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, providerInternalId));
			}
			else if (PaymentFieldKeys.RECEIVER_NAME.equals(field.getName()))
			{
				if (!StringHelper.isEmpty(receiverName))
				{
					fields.add(FieldBuilderHelper.modifyExpressions(field, receiverName));
				}
				else
				{
					fields.add(field);
				}
			}
			else if (PaymentFieldKeys.RECEIVER_DESCRIPTION.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, StringHelper.getEmptyIfNull(receiverDescription)));
			}
			else if (PaymentFieldKeys.RECEIVER_ACCOUNT.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverAccount));
			}
			else if (PaymentFieldKeys.RECEIVER_INN.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverINN));
			}
			else if (PaymentFieldKeys.RECEIVER_KPP.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverKPP));
			}
			else if (PaymentFieldKeys.RECEIVER_BIC.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverBIC));
			}
			else if (PaymentFieldKeys.RECEIVER_BANK_NAME.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverBankName));
			}
			else if (PaymentFieldKeys.RECEIVER_COR_ACCOUNT.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, receiverCorAccount));
			}
			else if (PaymentFieldKeys.RECEIVER_PHONE_NUMBER.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, StringHelper.getEmptyIfNull(receiverPhoneNumber)));
			}
			else if (BANK_DETAILS_ATTRIBUTE_NAME.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, needBankDetails));
			}
			else if (PaymentFieldKeys.BILLING_CODE.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, billingCode));
			}
			else if (PaymentFieldKeys.RECEIVER_NAME_ON_BILL.equals(field.getName()))
			{
				fields.add(FieldBuilderHelper.modifyExpressions(field, nameOnBill));
			}
			else
			{
				fields.add(field);
			}
		}
		return fields;
	}

	public Element buildFieldsDictionary()
	{
		return null;
	}
}
