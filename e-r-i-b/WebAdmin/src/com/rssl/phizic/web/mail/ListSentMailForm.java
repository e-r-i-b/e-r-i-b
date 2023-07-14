package com.rssl.phizic.web.mail;

import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.common.ListLimitActionForm;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.parsers.DateParser;

/**
 * @author gladishev
 * @ created 27.08.2007
 * @ $Author$
 * @ $Revision$
 */

public class ListSentMailForm extends ListLimitActionForm
{
	private String paginationSize;

	public static final Form FILTER_FORM = createForm();

	public String getPaginationSize()
	{
		return paginationSize;
	}

	public void set$$pagination_size0(String paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	protected static final FormBuilder getPartlyFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		// Тема
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тема");
		fieldBuilder.setName("subject");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		// ТБ клиента
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ТБ");
		fieldBuilder.setName("user_TB");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Площадка сотрудника
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Площадка КЦ");
		fieldBuilder.setName("area_uuid");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Тип получателя
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип получателя");
		fieldBuilder.setName("recipientType");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//ФИО сотрудника
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ФИО сотрудника");
		fieldBuilder.setName("fioEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Логин сотрудника
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setName("login");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Имя группы
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Имя группы");
		fieldBuilder.setName("groupName");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("дата");
		fieldBuilder.setType("date");
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toDate");
		fieldBuilder.setType("date");
		fieldBuilder.setDescription("дата");
		fieldBuilder.addValidators(new DateFieldValidator());
		fieldBuilder.setParser(new DateParser());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("type");
		fieldBuilder.setDescription("Тип письма");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("isAttach");
		fieldBuilder.setDescription("Наличие приложенных файлов");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("num");
		fieldBuilder.setDescription("Номер письма");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "Поле Номер письма должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Способ получения ответа");
		fieldBuilder.setName("response_method");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тематика обращения");
		fieldBuilder.setName("theme");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

		DateInPeriodValidator dateInPeriodValidator= new DateInPeriodValidator();
		dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_FROM_DATE, "fromDate");
		dateInPeriodValidator.setBinding(DateInPeriodValidator.FIELD_TO_DATE, "toDate");
		formBuilder.addFormValidators(dateInPeriodValidator);

		return formBuilder;
	}

	private static Form createForm()
    {
        FormBuilder formBuilder = getPartlyFormBuilder();

        FieldBuilder fieldBuilder;

	    //ФИО
	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setDescription("ФИО");
	    fieldBuilder.setName("fio");
	    fieldBuilder.setType("string");
	    formBuilder.addField(fieldBuilder.build());

        return formBuilder.build();
    }
}