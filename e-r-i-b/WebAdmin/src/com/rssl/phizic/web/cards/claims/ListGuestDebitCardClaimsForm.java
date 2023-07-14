package com.rssl.phizic.web.cards.claims;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * Форма для списка заявок гостя на дебетовые карты
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ListGuestDebitCardClaimsForm extends ListFormBase<Object[]>
{
	public static final Form FILTER_FORM = createForm();

	public static final String SEARCH_BY_PHONE_FIELD_NAME = "searchByPhone";
	public static final String PHONE_FIELD_NAME = "phone";
	public static final String LOGIN_FIELD_NAME = "login";
	public static final String FROM_DATE_FIELD_NAME = "fromDate";
	public static final String TO_DATE_FIELD_NAME = "toDate";

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		//Поиск по
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SEARCH_BY_PHONE_FIELD_NAME);
		fieldBuilder.setDescription("Поиск по");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator(StrutsUtils.getMessage("message.need.searchByPhone", "cardsBundle")));
		formBuilder.addField(fieldBuilder.build());

		//Номер телефона
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер телефона");
		fieldBuilder.setName(PHONE_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,11}", StrutsUtils.getMessage("message.wrong.phone", "cardsBundle")));
		formBuilder.addField(fieldBuilder.build());

		//Логин
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setName(LOGIN_FIELD_NAME);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		//Дата начала периода поиска
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(FROM_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период с");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator("dd.MM.yyyy", StrutsUtils.getMessage("message.wrong.date", "cardsBundle")));
		fieldBuilder.addValidators(new RequiredFieldValidator(StrutsUtils.getMessage("message.need.fromDate", "cardsBundle")));
		formBuilder.addField(fieldBuilder.build());

		//Дата окончания периода поиска
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(TO_DATE_FIELD_NAME);
		fieldBuilder.setDescription("Период по");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new DateFieldValidator("dd.MM.yyyy", StrutsUtils.getMessage("message.wrong.date", "cardsBundle")));
		fieldBuilder.addValidators(new RequiredFieldValidator(StrutsUtils.getMessage("message.need.toDate", "cardsBundle")));
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator multiFieldValidator = new RequiredMultiFieldValidator();
		multiFieldValidator.setBinding(PHONE_FIELD_NAME, PHONE_FIELD_NAME);
		multiFieldValidator.setBinding(LOGIN_FIELD_NAME, LOGIN_FIELD_NAME);
		multiFieldValidator.setMessage(StrutsUtils.getMessage("message.wrong.fields", "cardsBundle"));

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, FROM_DATE_FIELD_NAME);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, TO_DATE_FIELD_NAME);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeCompareValidator.setMessage(StrutsUtils.getMessage("message.wrong.date.period", "cardsBundle"));

		DateTimePeriodMultiFieldValidator periodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		periodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM, FROM_DATE_FIELD_NAME);
		periodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM, "");
		periodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO, TO_DATE_FIELD_NAME);
		periodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO, "");
		periodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.YAER_TYPE_PERIOD);
		periodMultiFieldValidator.setLengthPeriod(1);
		periodMultiFieldValidator.setMessage(StrutsUtils.getMessage("message.wrong.date.period.year", "cardsBundle"));

		formBuilder.addFormValidators(multiFieldValidator, dateTimeCompareValidator, periodMultiFieldValidator);

		return formBuilder.build();
	}
}
