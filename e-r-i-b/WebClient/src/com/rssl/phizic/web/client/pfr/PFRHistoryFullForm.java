package com.rssl.phizic.web.client.pfr;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.forms.types.UserResourceType;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * форма запроса справки о видах и размерах пенсий
 * @author Jatsky
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 */

public class PFRHistoryFullForm extends EditFormBase
{
	private static final String DATE_FORMAT = "dd/MM/yyyy";

	private boolean isWindow;
	private String fromResource;
	private List<PaymentAbilityERL> chargeOffResources;
	private boolean printAbstract;
	private boolean accountHasPFRRecords;

	public static final String FROM_RESOURCE_FIELD = "fromResource";
	public static final Form EDIT_FORM = createForm();

	public boolean getIsWindow()
	{
		return isWindow;
	}

	public void setIsWindow(boolean window)
	{
		isWindow = window;
	}

	public String getFromResource()
	{
		return fromResource;
	}

	public void setFromResource(String fromResource)
	{
		this.fromResource = fromResource;
	}

	public List<PaymentAbilityERL> getChargeOffResources()
	{
		return chargeOffResources;
	}

	public void setChargeOffResources(List<PaymentAbilityERL> chargeOffResources)
	{
		this.chargeOffResources = chargeOffResources;
	}

	public boolean isPrintAbstract()
	{
		return printAbstract;
	}

	public void setPrintAbstract(boolean printAbstract)
	{
		this.printAbstract = printAbstract;
	}

	public boolean isAccountHasPFRRecords() {
		return accountHasPFRRecords;
	}

	public void setAccountHasPFRRecords(boolean accountHasPFRRecords) {
		this.accountHasPFRRecords = accountHasPFRRecords;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		DateParser dataParser = new DateParser(DATE_FORMAT);

		MultiFieldsValidator comparePeriodValidator = new CompareValidator();
		comparePeriodValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O1, "fromPeriod");
		comparePeriodValidator.setBinding(CompareValidator.FIELD_O2, "toPeriod");
		comparePeriodValidator.setMessage("Конечная дата должна быть больше или равна начальной.");

		DateFieldValidator datePeriodValidator = new DateFieldValidator(DATE_FORMAT);
		datePeriodValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		Expression periodDatesExpression = new RhinoExpression("form.typePeriod == 'period'");

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Выберите счет");
		fieldBuilder.setName(FROM_RESOURCE_FIELD);
		fieldBuilder.setType(UserResourceType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typePeriod");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{"month", "period"}))
		);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromPeriod");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(periodDatesExpression);
		fieldBuilder.addValidators(new RequiredFieldValidator(), datePeriodValidator, datePeriodValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toPeriod");
		fieldBuilder.setDescription("Период");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.setEnabledExpression(periodDatesExpression);
		fieldBuilder.addValidators(new RequiredFieldValidator(), datePeriodValidator, datePeriodValidator);
		fb.addField(fieldBuilder.build());

		fb.addFormValidators(comparePeriodValidator);
		return fb.build();
	}
}
