package com.rssl.phizic.web.common.client.ima;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountInfoForm extends EditFormBase
{
	public static final Form IMALINK_FORM = createForm();
	public static final Form FILTER_FORM = createFilterForm();

	private IMAccountLink imAccountLink;
	private IMAccountAbstract imAccountAbstract;
	private List<IMAccountLink> additionalIMAccountLink;
	//Сообщение об ощибке для отображения пользователю
	private String abstractMsgError;
	
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

	public List<IMAccountLink> getAdditionalIMAccountLink()
	{
		return additionalIMAccountLink;
	}

	public void setAdditionalIMAccountLink(List<IMAccountLink> additionalIMAccountLink)
	{
		this.additionalIMAccountLink = additionalIMAccountLink;
	}

	public String getAbstractMsgError()
	{
		return abstractMsgError;
	}

	public void setAbstractMsgError(String abstractMsgError)
	{
		this.abstractMsgError = abstractMsgError;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название");
		fieldBuilder.setName("imAccountName");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{1,50}", "Наименование должно быть не более 50 символов.")
		);
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	private static Form createFilterForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeAbstract == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате " + format + ".");

		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(format);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

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
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Период");
		fieldBuilder.setName("toAbstract");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromAbstract");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toAbstract");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);

		return formBuilder.build();
	}
}
