package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.mail.MailConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.web.common.ListLimitActionForm;

/**
 * @author Gainanov
 * @ created 26.02.2007
 * @ $Author$
 * @ $Revision$
 */
public class ListMailForm extends ListLimitActionForm
{
	public static final String CATEGORY_INBOX = "i";
	public static final String CATEGORY_OUTBOX = "o";
	private String paginationSize;
	private Boolean fromQuestionary = false;

	private Long id;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * Признак того что перешли из анкеты
	 * @return из анкеты да/нет
	 */
	public Boolean getFromQuestionary()
	{
		return fromQuestionary;
	}

	/**
	 * Признак того что перешли из анкеты
	 * @param fromQuestionary из анкеты да/нет
	 */
	public void setFromQuestionary(Boolean fromQuestionary)
	{
		this.fromQuestionary = fromQuestionary;
	}

	public String getPaginationSize()
	{
		return paginationSize;
	}

	public void set$$pagination_size0(String paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public static Form FILTER_FORM = createForm();

	protected static final FormBuilder getPartlyFormBuilder()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		//Статусы.
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Новое");
		fieldBuilder.setName("showNew");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Прочитано");
		fieldBuilder.setName("showReceived");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Черновик");
		fieldBuilder.setName("showDraft");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ответ дан");
		fieldBuilder.setName("showAnswer");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

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

		//Тип письма
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тип");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Номер письма
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер");
		fieldBuilder.setName("num");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d*", "Поле Номер письма должно содержать только цифры."));
		formBuilder.addField(fieldBuilder.build());

		//ФИО
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("ФИО сотрудника");
		fieldBuilder.setName("fioEmpl");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		//Логин
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setName("employeeLogin");
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
		fieldBuilder.setName("isAttach");
		fieldBuilder.setDescription("Наличие приложенных файлов");
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

        return formBuilder.build();
    }
}
