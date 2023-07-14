package com.rssl.phizic.web.client.payments;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.web.actions.FilterPeriodHelper;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;

import java.util.List;

/**
 * @author Kosyakova
 * @ created 31.01.2007
 * @ $Author$
 * @ $Revision$
 */
public class ShowCommonPaymentListForm extends ViewDocumentListFormBase
{
	private List<AccountLink> accounts;
	private List<CardLink> cards;
	private List<IMAccountLink> imaccounts;
	private Person person;
	private String account;

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public List getAccounts()
	{
		return accounts;
	}

	public void setAccounts(List accounts)
	{
		this.accounts = accounts;
	}

	public List<IMAccountLink> getImaccounts()
	{
		return imaccounts;
	}

	public void setImaccounts(List<IMAccountLink> imaccounts)
	{
		this.imaccounts = imaccounts;
	}

	public Person getPerson()
	{
		return person;
	}

	public void setPerson(Person person)
	{
		this.person = person;
	}

	public static final Form FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = FilterPeriodHelper.createFilterPeriodFormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("formId");
		fieldBuilder.setDescription("Операция");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("account");
		fieldBuilder.setDescription("Списано с");
		fieldBuilder.setType(UserResourceType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("clientState");
		fieldBuilder.setDescription("Статус");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromAmount");
		fieldBuilder.setType("money");
		fieldBuilder.setDescription("Минимальная сумма");
		MoneyFieldValidator fromAmountValidator = new MoneyFieldValidator();
		fromAmountValidator.setMessage("Неверное значение в поле минимальная сумма");
		fieldBuilder.setValidators(fromAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toAmount");
		fieldBuilder.setType("money");
		fieldBuilder.setDescription("Максимальная сумма");
		MoneyFieldValidator toAmountValidator = new MoneyFieldValidator();
		toAmountValidator.setMessage("Неверное значение в поле максимальная сумма");
		fieldBuilder.setValidators(toAmountValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("amountCurrency");
		fieldBuilder.setType("string");
		fieldBuilder.setDescription("валюта");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("receiverName");
		fieldBuilder.setDescription("Получатель");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("autoPayment");
		fieldBuilder.setDescription("Показать регулярные платежи");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareAmountValidator = new CompareValidator();
		compareAmountValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareAmountValidator.setBinding(CompareValidator.FIELD_O1, "fromAmount");
		compareAmountValidator.setBinding(CompareValidator.FIELD_O2, "toAmount");
		compareAmountValidator.setMessage("Максимальная сумма должна быть больше либо равна минимальной!");

		formBuilder.addFormValidators(compareAmountValidator);

		return formBuilder.build();
	}
}

