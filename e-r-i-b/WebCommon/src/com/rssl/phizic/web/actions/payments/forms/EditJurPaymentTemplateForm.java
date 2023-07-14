package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.bannedAccount.AccountBanType;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.business.payments.forms.validators.AccountCurrencyValidator;
import com.rssl.phizic.business.payments.forms.validators.AccountKeyValidator;
import com.rssl.phizic.business.payments.forms.validators.BICFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.BannedAccountListValidator;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.util.List;
import java.util.Map;

/**
 * Форма шаблона оплаты по произвольным реквизитам
 *
 * @author khudyakov
 * @ created 11.03.14
 * @ $Author$
 * @ $Revision$
 */
public class EditJurPaymentTemplateForm extends EditTemplateForm
{
	public static final Form EDIT_FORM = createForm();

	private List<Object[]> serviceProviders;
	private Map<String, String[]> providerRegions; //регионы поставщиков
	private List<Recipient> externalProviders;
	private boolean newTemplate;
	private String nextURL;


	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер счета");
		fieldBuilder.setName(Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{20,25}$", "Вы неправильно указали номер счета. Пожалуйста, введите от 20 до 25 цифр без точек и пробелов."),
				new AccountCurrencyValidator("Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИНН");
		fieldBuilder.setName(Constants.RECEIVER_INN_ATTRIBUTE_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{10}|\\d{12}$", "Вы неправильно указали ИНН. Пожалуйста, введите 10 или 12 цифр.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("БИК");
		fieldBuilder.setName(Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{9}$", "Вы неправильно указали БИК. Пожалуйста, введите ровно 9 цифр."),
				new BICFieldValidator("БИК банка получателя не найден в справочнике банков.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Счет списания");
		fieldBuilder.setName(Constants.FROM_RESOURCE_ATTRIBUTE_NAME);
		fieldBuilder.setType(UserResourceType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Внешний (биллинговый) идентификатор получателя");
		fieldBuilder.setName(Constants.EXTERNAL_PROVIDER_KEY_ATTRIBUTE_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор получателя");
		fieldBuilder.setName(Constants.PROVIDER_EXTERNAL_ID_ATTRIBUTE_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		AccountKeyValidator accountKeyValidator = new AccountKeyValidator();
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_BIC, Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME);
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
		accountKeyValidator.setMessage("Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.");

		BannedAccountListValidator bannedAccountListValidator = new BannedAccountListValidator();
		bannedAccountListValidator.setBinding(AccountKeyValidator.FIELD_BIC, Constants.RECEIVER_BANK_BIK_ATTRIBUTE_NAME);
		bannedAccountListValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, Constants.RECEIVER_ACCOUNT_ATTRIBUTE_NAME);
		bannedAccountListValidator.setParameter(BannedAccountListValidator.BAN_TYPE, AccountBanType.JUR.toString());
		bannedAccountListValidator.setMessage("Вы указали недопустимый счет получателя, перевод на который запрещен. Пожалуйста, укажите корректный счет получателя.");
		fb.addFormValidators(accountKeyValidator,bannedAccountListValidator);


		return fb.build();
	}

	public void setExternalProviders(List<Recipient> externalProviders)
	{
		this.externalProviders = externalProviders;
	}

	public List<Recipient> getExternalProviders()
	{
		return externalProviders;
	}

	public String getNextURL()
	{
		return nextURL;
	}

	public void setNextURL(String nextURL)
	{
		this.nextURL = nextURL;
	}

	public List<Object[]> getServiceProviders()
	{
		return serviceProviders;
	}

	public void setServiceProviders(List<Object[]> serviceProviders)
	{
		this.serviceProviders = serviceProviders;
	}

	public boolean isNewTemplate()
	{
		return newTemplate;
	}

	public void setNewTemplate(boolean newTemplate)
	{
		this.newTemplate = newTemplate;
	}

	public Map<String, String[]> getProviderRegions()
	{
		return providerRegions;
	}

	public void setProviderRegions(Map<String, String[]> providerRegions)
	{
		this.providerRegions = providerRegions;
	}
}
