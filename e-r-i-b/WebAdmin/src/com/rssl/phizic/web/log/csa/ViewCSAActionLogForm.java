package com.rssl.phizic.web.log.csa;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.csa.CSAOperationKind;
import com.rssl.phizic.common.types.csa.IdentificationType;
import com.rssl.phizic.web.log.DownloadLogFilterBaseForm;

/**
 * @author vagin
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 * Форма журнала входов в ЦСА.
 */
public class ViewCSAActionLogForm extends DownloadLogFilterBaseForm
{
	public static final String EMPLOYEE_FIO_FIELD_NAME = "employeeFio";
	public static final String EMPLOYEE_LOGIN_FIELD_NAME = "employeeLogin";
	public static final String IDENTIFICATION_TYPE_FIEDL_NAME = "identificationType";
	public static final String OPERATION_TYPE_FIELD_NAME = "operationType";
	public static final String CONFIRMATION_TYPE_FIELD_NAME = "confirmationType";
	public static final String IP_ADDRESS_FIELD_NAME = "ipAddress";

	private Long id;
	private ActivePerson activePerson;
	private String passport;

	public static final Form CSA_ACTION_FILTER_FORM = createFilterForm();

	private static Form createFilterForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EMPLOYEE_FIO_FIELD_NAME);
		fieldBuilder.setDescription("Сотрудник");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,150}","Значение поля \"Сотрудник\" не должно превышать 150 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EMPLOYEE_LOGIN_FIELD_NAME);
		fieldBuilder.setDescription("Логин сотрудника");
		fieldBuilder.addValidators(new RegexpFieldValidator(".{0,50}","Значение поля \"Логин сотрудника\" не должно превышать 50 символов."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IP_ADDRESS_FIELD_NAME);
		fieldBuilder.setDescription("IP-адрес");
		fieldBuilder.setValidators(new RegexpFieldValidator("^\\d{0,3}.\\d{0,3}.\\d{0,3}.\\d{0,3}$","Неверный формат ip-адреса"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(IDENTIFICATION_TYPE_FIEDL_NAME);
		fieldBuilder.setDescription("Способ аутентификации");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(OPERATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Операция");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CONFIRMATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Подтверждение");
		formBuilder.addField(fieldBuilder.build());

		DateParser dateParser = new DateParser(DATESTAMP_FORMAT);
		DateParser timeParser = new DateParser(TIMESTAMP_FORMAT);

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(	new RequiredFieldValidator("Введите начальную дату"),
									new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators( new RequiredFieldValidator("Введите конечную дату"),
									new DateFieldValidator(DATESTAMP_FORMAT, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType("date");
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP_FORMAT, "Время должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addField(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");

		formBuilder.setFormValidators(dateTimeCompareValidator);

		return formBuilder.build();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public IdentificationType[] getIdentificationTypes()
	{
		return IdentificationType.values();
	}

	public CSAOperationKind[] getOperationTypes()
	{
		return CSAOperationKind.values();
	}

	/**
	 * @return редактируемый клиент
	 */
	public ActivePerson getActivePerson()
	{
		return activePerson;
	}

	/**
	 * задать редактируемого клиента
	 * @param activePerson редактируемый клиент
	 */
	public void setActivePerson(ActivePerson activePerson)
	{
		this.activePerson = activePerson;
	}

	/**
	 * @return нужды /WEB-INF/jsp-sbrf/persons/personData.jsp
	 */
	public boolean isModified()
	{
		return false;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}
}
