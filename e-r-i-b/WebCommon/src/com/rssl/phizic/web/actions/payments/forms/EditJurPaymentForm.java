package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.bannedAccount.AccountBanType;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.business.payments.forms.validators.AccountCurrencyValidator;
import com.rssl.phizic.business.payments.forms.validators.AccountKeyValidator;
import com.rssl.phizic.business.payments.forms.validators.BICFieldValidator;
import com.rssl.phizic.business.payments.forms.validators.BannedAccountListValidator;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;

import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 06.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditJurPaymentForm extends EditPaymentForm
{
	private List<Object[]> serviceProviders;
	private Map<String, String[]> providerRegions; //регионы поставщиков
	private List<Recipient> externalProviders;
	private boolean newTemplate;

	public static final Form EDIT_FORM = createForm();
	public static final String FROM_RESOURCE_FIELD = "fromResource";
	public static final String RECEIVER_ACCOUNT_FIELD = "receiverAccount";
	public static final String RECEIVER_INN_FIELD = "receiverINN";
	public static final String RECEIVER_BIC_FIELD = "receiverBIC";
	public static final String EXTERNAL_PROVIDER_KEY_FIELD = "externalReceiverId";
	public static final String PROVIDER_KEY_FIELD = "receiverId";

	private String nextURL;

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер счета");
		fieldBuilder.setName(RECEIVER_ACCOUNT_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{20,25}$", "Вы неправильно указали номер счета. Пожалуйста, введите от 20 до 25 цифр без точек и пробелов."),
				new AccountCurrencyValidator("Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ИНН");
		fieldBuilder.setName(RECEIVER_INN_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{10}|\\d{12}$", "Вы неправильно указали ИНН. Пожалуйста, введите 10 или 12 цифр.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("БИК");
		fieldBuilder.setName(RECEIVER_BIC_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator("^\\d{9}$", "Вы неправильно указали БИК. Пожалуйста, введите ровно 9 цифр."),
				new BICFieldValidator("БИК банка получателя не найден в справочнике банков.")
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Счет списания");
		fieldBuilder.setName(FROM_RESOURCE_FIELD);
		fieldBuilder.setType(UserResourceType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Внешний (биллинговый) идентификатор получателя");
		fieldBuilder.setName(EXTERNAL_PROVIDER_KEY_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Идентификатор получателя");
		fieldBuilder.setName(PROVIDER_KEY_FIELD);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		AccountKeyValidator accountKeyValidator = new AccountKeyValidator();
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_BIC, RECEIVER_BIC_FIELD);
		accountKeyValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, RECEIVER_ACCOUNT_FIELD);
		accountKeyValidator.setMessage("Вы неправильно указали счет получателя. Пожалуйста, проверьте номер счета.");

		BannedAccountListValidator bannedAccountListValidator = new BannedAccountListValidator();
		bannedAccountListValidator.setBinding(AccountKeyValidator.FIELD_BIC, RECEIVER_BIC_FIELD);
		bannedAccountListValidator.setBinding(AccountKeyValidator.FIELD_RECEIVER_ACCOUNT, RECEIVER_ACCOUNT_FIELD);
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
