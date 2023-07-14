package com.rssl.phizic.web.client.ima;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.utils.ListUtil;

/**
 * @ author Balovtsev
 * @ created 31.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountPrintForm extends EditFormBase
{
	public static final Form PRINT_FORM = createPrintForm();
	
	private String mode;
	private Client client;
	private ActivePerson user;    // текущий пользователь
	private Office office;
	private IMAccountLink imAccountLink;
	private IMAccountAbstract imAccountAbstract;

	private Money totalCreditAmount;
	private Money totalDebitAmount;
	private Money totalCreditAmountInPhys;
	private Money totalDebitAmountInPhys;
	private Money outboxBalance;
	private Money incomingBalance;

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}

	public ActivePerson getUser()
	{
		return user;
	}

	public void setUser(ActivePerson user)
	{
		this.user = user;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}

	public IMAccountLink getImAccountLink()
	{
		return imAccountLink;
	}

	public void setImAccountLink(IMAccountLink imAccountLink)
	{
		this.imAccountLink = imAccountLink;
	}

	public IMAccountAbstract getImAccountAbstract()
	{
		return imAccountAbstract;
	}

	public void setImAccountAbstract(IMAccountAbstract imAccountAbstract)
	{
		this.imAccountAbstract = imAccountAbstract;
	}

	public Money getTotalCreditAmount()
	{
		return totalCreditAmount;
	}

	public void setTotalCreditAmount(Money totalCreditAmount)
	{
		this.totalCreditAmount = totalCreditAmount;
	}

	public Money getTotalDebitAmount()
	{
		return totalDebitAmount;
	}

	public void setTotalDebitAmount(Money totalDebitAmount)
	{
		this.totalDebitAmount = totalDebitAmount;
	}

	public Money getTotalCreditAmountInPhys()
	{
		return totalCreditAmountInPhys;
	}

	public void setTotalCreditAmountInPhys(Money totalCreditAmountInPhys)
	{
		this.totalCreditAmountInPhys = totalCreditAmountInPhys;
	}

	public Money getTotalDebitAmountInPhys()
	{
		return totalDebitAmountInPhys;
	}

	public void setTotalDebitAmountInPhys(Money totalDebitAmountInPhys)
	{
		this.totalDebitAmountInPhys = totalDebitAmountInPhys;
	}

	public Money getOutboxBalance()
	{
		return outboxBalance;
	}

	public void setOutboxBalance(Money outboxBalance)
	{
		this.outboxBalance = outboxBalance;
	}

	public Money getIncomingBalance()
	{
		return incomingBalance;
	}

	public void setIncomingBalance(Money incomingBalance)
	{
		this.incomingBalance = incomingBalance;
	}
	
	private static Form createPrintForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeAbstract == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате " + format + ".");

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeAbstract");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"week", "month", "period"}))
		);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период");
		fieldBuilder.setName("fromAbstract");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период");
		fieldBuilder.setName("toAbstract");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(dataValidator);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
