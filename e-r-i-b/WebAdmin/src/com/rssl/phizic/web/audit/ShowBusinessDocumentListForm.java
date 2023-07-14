package com.rssl.phizic.web.audit;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.actions.payments.forms.ViewDocumentListFormBase;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 28.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowBusinessDocumentListForm extends ViewDocumentListFormBase
{
	public static final String DATESTAMP = "dd.MM.yyyy";
	public static final String TIMESTAMP = "HH:mm:ss";

	public static final String CLIENT_FIO_FIELD_NAME = "fio";
	public static final String DUL_FIELD_NAME = "dul";
	public static final String LOGIN_ID_FIELD_NAME = "loginId";
	public static final String EMPLOYEE_FIO_FIELD_NAME = "employeeFIO";
	public static final String STATE_FIELD_NAME = "state";
	public static final String NUMBER_FIELD_NAME = "number";
	public static final String CREATION_TYPE_FIELD_NAME = "creationType";

	public static final Form FORM = createForm();

	protected static FormBuilder getPartlyFormBuilder()
	{
		DateParser dateParser = new DateParser(DATESTAMP);
		DateParser timeParser = new DateParser(TIMESTAMP);

		FormBuilder formBuilder = new FormBuilder();
	    FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(NUMBER_FIELD_NAME);
	    fieldBuilder.setDescription("Номер платежа/заявки");
		formBuilder.addFields(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName("formId");
	    fieldBuilder.setDescription("Операция");
		formBuilder.addFields(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(STATE_FIELD_NAME);
	    fieldBuilder.setDescription("Статус");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("fromDate");
		fieldBuilder.setDescription("Начальная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Введите начальную дату"),
				new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("fromTime");
		fieldBuilder.setDescription("Начальное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ:CC"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("toDate");
		fieldBuilder.setDescription("Конечная дата");
		fieldBuilder.addValidators(new RequiredFieldValidator("Введите конечную дату"),
				new DateFieldValidator(DATESTAMP, "Дата должна быть в формате ДД.ММ.ГГГГ"));
		fieldBuilder.setParser(dateParser);
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName("toTime");
		fieldBuilder.setDescription("Конечное время");
		fieldBuilder.setValidators(new DateFieldValidator(TIMESTAMP, "Время должно быть в формате ЧЧ:ММ:СС"));
		fieldBuilder.setParser(timeParser);
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("fromAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Минимальная сумма");
		MoneyFieldValidator fromAmountValidator = new MoneyFieldValidator();
		fromAmountValidator.setMessage("Неверное значение в поле минимальная сумма");
		fieldBuilder.setValidators( fromAmountValidator );
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("toAmount");
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setDescription("Максимальная сумма");
		MoneyFieldValidator toAmountValidator = new MoneyFieldValidator();
		toAmountValidator.setMessage("Неверное значение в поле максимальная сумма");
		fieldBuilder.setValidators( toAmountValidator );
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("receiverName");
		fieldBuilder.setDescription("Наименование получателя");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("autoPayment");
		fieldBuilder.setDescription("Показать регулярные платежи");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showInitialPayments");
		fieldBuilder.setDescription("Показать операции со статусом 'Введен'/'Частично заполнен'");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("showDeleted");
		fieldBuilder.setDescription("Показать операции со статусом 'Удалён'");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Название подразделения в котором заведен поставщик услуг, в адрес которого был отправлен платеж");
		fieldBuilder.setName("nameOSB");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Номер операции в БС");
		fieldBuilder.setName("madeOperationId");
		formBuilder.addFields(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(CREATION_TYPE_FIELD_NAME);
		fieldBuilder.setDescription("Канал создания");
		formBuilder.addFields(fieldBuilder.build());

		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.LESS_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "fromDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "fromTime");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "toDate");
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "toTime");
		dateTimeCompareValidator.setMessage("Конечная дата должна быть больше либо равна начальной!");
		formBuilder.addFormValidators(dateTimeCompareValidator);

		CompareValidator compareAmountValidator = new CompareValidator();
		compareAmountValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		compareAmountValidator.setBinding(CompareValidator.FIELD_O1, "fromAmount");
		compareAmountValidator.setBinding(CompareValidator.FIELD_O2, "toAmount");
		compareAmountValidator.setMessage("Максимальная сумма должна быть больше либо равна минимальной!");
		formBuilder.addFormValidators(compareAmountValidator);

		DateTimePeriodMultiFieldValidator yearPeriodMultiFieldValidator = new DateTimePeriodMultiFieldValidator();
		yearPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_FROM, "fromDate");
		yearPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_FROM, "fromTime");
		yearPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_DATE_TO, "toDate");
		yearPeriodMultiFieldValidator.setBinding(DateTimePeriodMultiFieldValidator.FIELD_TIME_TO, "toTime");
		yearPeriodMultiFieldValidator.setTypePeriod(DateTimePeriodMultiFieldValidator.YAER_TYPE_PERIOD);
		yearPeriodMultiFieldValidator.setLengthPeriod(1);
		yearPeriodMultiFieldValidator.setMessage("Пожалуйста, укажите период не более 1 года.");
		formBuilder.addFormValidators(yearPeriodMultiFieldValidator);

		return formBuilder;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = getPartlyFormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Дата рождения");
		fieldBuilder.setName("birthday");
		fieldBuilder.setType(DateType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
	    fieldBuilder.setName(CLIENT_FIO_FIELD_NAME);
	    fieldBuilder.setDescription("Клиент");
		FieldValidator reqFieldValidator = new RequiredFieldValidator("Пожалуйста, заполните поле клиент.");
		reqFieldValidator.setEnabledExpression(new RhinoExpression("form.birthday != '' && form.birthday != null"));
		fieldBuilder.addValidators(reqFieldValidator);
		formBuilder.addField(fieldBuilder.build());


		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(DUL_FIELD_NAME);
		fieldBuilder.setDescription("Документ");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,32}", "Поле документ не должно превышать 32 символов"),
				new RegexpFieldValidator("[^<>!#$%^&*{}|\\]\\[''\"~=]*","Поле документ не должно содержать спецсимволов")
				);
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Login_ID");
		fieldBuilder.setName(LOGIN_ID_FIELD_NAME);
		fieldBuilder.addValidators(new RegexpFieldValidator("^\\d*$","Login_ID может содержать только цифры"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сотрудник");
		fieldBuilder.setName(EMPLOYEE_FIO_FIELD_NAME);
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding(CLIENT_FIO_FIELD_NAME, CLIENT_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(DUL_FIELD_NAME, DUL_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(LOGIN_ID_FIELD_NAME, LOGIN_ID_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(STATE_FIELD_NAME, STATE_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(NUMBER_FIELD_NAME, NUMBER_FIELD_NAME);
		requiredMultiFieldValidator.setBinding(EMPLOYEE_FIO_FIELD_NAME, EMPLOYEE_FIO_FIELD_NAME);
		requiredMultiFieldValidator.setMessage("Пожалуйста, заполните хотя бы одно из полей фильтра: клиент, документ, Login_ID, статус операции, № платежа/заявки, сотрудник.");
		formBuilder.addFormValidators(requiredMultiFieldValidator);

		return formBuilder.build();
	}
}
