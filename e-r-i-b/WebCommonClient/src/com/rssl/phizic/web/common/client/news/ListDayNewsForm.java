package com.rssl.phizic.web.common.client.news;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.business.news.News;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author lukina
 * @ created 06.06.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListDayNewsForm  extends ListFormBase<News>
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
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "today", "yesterday", "period" } ))
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

		formBuilder.addFormValidators();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("search");
		fieldBuilder.setDescription("Слова для поиска");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
