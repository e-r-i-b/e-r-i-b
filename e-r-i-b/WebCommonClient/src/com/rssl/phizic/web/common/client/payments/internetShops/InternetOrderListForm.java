package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;

/**
 * форма для страницы мои интернет заказы.
 *
 * @author bogdanov
 * @ created 30.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class InternetOrderListForm extends ListFormBase
{
	public static final Form FILTER_FORM = createForm();
	
	public static final String STATE_CODE = "stateCode";
	public static final String STATE_CODE_ALL = "ALL";
	public static final String FROM_AMOUNT = "fromAmount";
	public static final String TO_AMOUNT = "toAmount";
	public static final String CURRENCY = "currency";
	public static final String TO_DATE = "toDate";
	public static final String FROM_DATE = "fromDate";
	public static final String IS_DEFAULT = "isDefault";
	public static final String TYPE_DATE = "typeDate";

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Состояние");
		fieldBuilder.setName(STATE_CODE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма от");
		fieldBuilder.setName(FROM_AMOUNT);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сумма до");
		fieldBuilder.setName(TO_AMOUNT);
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Валюта");
		fieldBuilder.setName(CURRENCY);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		Expression periodDatesExpression = new RhinoExpression("form.typeDate == 'period' ");
		String format = "dd/MM/yyyy";
		DateParser dataParser = new DateParser(format);

		DateFieldValidator dataValidator = new DateFieldValidator(format);
		dataValidator.setEnabledExpression(periodDatesExpression);
		dataValidator.setMessage("Введите дату в поле Период в формате ДД/ММ/ГГГГ.");

		RequiredFieldValidator requiredValidator = new  RequiredFieldValidator();
		requiredValidator.setEnabledExpression(periodDatesExpression);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Конечная дата периода");
		fieldBuilder.setName(TO_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Начальная дата периода");
		fieldBuilder.setName(FROM_DATE);
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setParser(dataParser);
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, dataValidator);
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Фильтр по умолчанию");
		fieldBuilder.setName(IS_DEFAULT);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип периода");
		fieldBuilder.setName(TYPE_DATE);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(
				new RequiredFieldValidator(),
				new ChooseValueValidator(ListUtil.fromArray(new String[] { "month", "period" } ))
		);
		fb.addField(fieldBuilder.build());

		CompareValidator compareValidator = new CompareValidator();
		compareValidator.setParameter(CompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		compareValidator.setBinding(CompareValidator.FIELD_O1, FROM_DATE);
		compareValidator.setBinding(CompareValidator.FIELD_O2, TO_DATE);
		compareValidator.setMessage("Конечная дата должна быть больше начальной!");
		compareValidator.setEnabledExpression(periodDatesExpression);
		fb.addFormValidators(compareValidator);

		return fb.build();
	}
}
