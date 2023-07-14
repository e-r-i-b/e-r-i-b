package com.rssl.phizic.web.common.client.news;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.web.common.ListFormBase;

/**
 *  User: Zhuravleva
 *  Date: 24.12.2006
 *  Time: 17:24:53
 */
public class ListNewsForm extends ListFormBase<News>
{
	public static String DATESTAMP = "dd/MM/yyyy";
	public static final Form FORM = createForm();

	private static Form createForm()
	{
		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period' ");
		DateParser dataParser = new DateParser(DATESTAMP);

		DateFieldValidator dataValidator = new DateFieldValidator(DATESTAMP);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		DateNotInFutureValidator dateNotInFutureValidator = new DateNotInFutureValidator(DATESTAMP);
		dateNotInFutureValidator.setEnabledExpression(periodDatesExpression);

		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("important");
		fieldBuilder.setDescription("Искать среди важных новостей");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName("typeDate");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "week", "month", "period" } ))
		);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("fromDate");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator, dateNotInFutureValidator);
	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setDescription("Период");
	    fieldBuilder.setName("toDate");
	    fieldBuilder.setType("date");
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
	    formBuilder.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, "fromDate");
		compareValidator.setBinding(CompareValidator.FIELD_O2, "toDate");
		compareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);

		formBuilder.addFormValidators(compareValidator);



		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("search");
		fieldBuilder.setDescription("Слова для поиска");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
