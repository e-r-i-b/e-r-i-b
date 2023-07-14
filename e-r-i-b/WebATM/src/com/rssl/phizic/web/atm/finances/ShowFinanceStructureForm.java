package com.rssl.phizic.web.atm.finances;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.common.forms.validators.CompareValidator;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.client.finances.FinanceFormBase;
import com.rssl.phizic.web.component.DatePeriodFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mescheryakova
 * @ created 23.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class ShowFinanceStructureForm extends FinanceFormBase
{
	public static final Form ATM_FILTER_FORM  = createATMFilterForm();

	private int paginationSize; //параметры запроса
	private int paginationOffset; //параметры запроса

	public int getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}

	protected static Form createATMFilterForm()
	{
	    List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		Expression periodDatesExpression = new RhinoExpression("form."+ DatePeriodFilter.TYPE_PERIOD+" == '"+DatePeriodFilter.TYPE_PERIOD_PERIOD+"' ");
		String format = "dd.MM.yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
        dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в формате ДД.ММ.ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);
		// Период
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(DatePeriodFilter.TYPE_PERIOD);
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
                new RequiredFieldValidator(),
                new ChooseValueValidator(ListUtil.fromArray(new String[]{DatePeriodFilter.TYPE_PERIOD_MONTH, DatePeriodFilter.TYPE_PERIOD_PERIOD}))
        );
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.FROM_DATE);
		fieldBuilder.setDescription("Дата с");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DatePeriodFilter.TO_DATE);
		fieldBuilder.setDescription("Дата по");
		fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fields.add(fieldBuilder.build());

		// показать операции совещённые по доп картам к чужим счетам
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showOtherAccounts");
		fieldBuilder.setDescription("показать операции совещённые по дополнительным картам к чужим счетам");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());

		// Тип операции
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип операции");
		fieldBuilder.setName("incomeType");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[]{INCOME_TYPE, OUTCOME_TYPE}))
		);
		fields.add(fieldBuilder.build());

		// включать операции с наличными
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showCash");
		fieldBuilder.setDescription("показать операции совершенные за наличные");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator);
		fields.add(fieldBuilder.build());


		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);


		CompareValidator compareDateValidator = new CompareValidator();
		compareDateValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
		compareDateValidator.setBinding(CompareValidator.FIELD_O1, DatePeriodFilter.FROM_DATE);
		compareDateValidator.setBinding(CompareValidator.FIELD_O2, DatePeriodFilter.TO_DATE);
		compareDateValidator.setMessage("Конечная дата должна быть больше начальной!");

		formBuilder.setFormValidators(compareDateValidator);
		return formBuilder.build();
	}
}
