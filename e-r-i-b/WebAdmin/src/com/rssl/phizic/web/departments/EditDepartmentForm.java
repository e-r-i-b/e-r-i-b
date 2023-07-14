package com.rssl.phizic.web.departments;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.EnumParser;
import com.rssl.common.forms.parsers.SqlTimeParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.EnumFieldValidator;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.dictionaries.offices.extended.DepartmentAutoType;
import com.rssl.phizic.web.common.EditFormBase;

import java.sql.Time;
import java.text.SimpleDateFormat;

/**
 * @author akrenev
 * @ created 19.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования подразделения
 */

public class EditDepartmentForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();

	public static final String EXTERNAL_SYSTEM_ID_FIELD_NAME = "externalSystemId";
	public static final String EXTERNAL_SYSTEM_NAME_FIELD_NAME = "externalSystemName";
	public static final String EXTERNAL_SYSTEM_UUID_FIELD_NAME = "externalSystemUUID";
	public static final String IS_EXTERNAL_SYSTEM_OFFICE_FIELD_NAME = "externalSystemOffice";
	public static final String OFFICE_ID_FIELD_NAME = "officeId";
	public static final String OFFICE_NAME_FIELD_NAME = "officeName";

	public static final String ESB_SUPPORTED = "esbSupported";
	public static final String AUTOMATION = "automation";
	public static final String CREDIT_CARD_OFFICE = "creditCardOffice";
	public static final String OPEN_IMA_OFFICE = "openIMAOffice";

	public static final String REGION = "region";
	public static final String BRANCH = "branch";
	public static final String OFFICE = "office";

	public static final String TIME_ZONE = "timeZone";
	public static final String SERVICE_FIELD_NAME = "service";
	public static final String POSSIBLE_LOANS_OPERATION_FIELD_NAME = "possibleLoansOperation";
	public static final String MAIN_DEPARTMENT_FIELD_NAME = "mainDepartment";
	public static final String MONTHLY_CHARGE_FIELD_NAME = "monthlyCharge";
	public static final String CONNECTION_CHARGE_FIELD_NAME = "connectionCharge";
	public static final String NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME = "notifyContractCancellation";
	public static final String TIME_SCALE_FIELD_NAME = "timeScale";
	public static final String NAME_FIELD_NAME = "name";
	public static final String CITY_FIELD_NAME = "city";
	public static final String ADDRESS_FIELD_NAME = "address";
	public static final String LOCATION_FIELD_NAME = "location";
	public static final String TELEPHONE_FIELD_NAME = "telephone";
	public static final String WEEK_OPERATION_TIME_BEGIN_FIELD_NAME = "weekOperationTimeBegin";
	public static final String WEEK_OPERATION_TIME_END_FIELD_NAME = "weekOperationTimeEnd";
	public static final String BILLING_ID_FIELD_NAME = "billingId";
	public static final String BILLING_NAME_FIELD_NAME = "billingName";
	public static final String NOT_ACTIVE = "notActive";

	private String mode;      // "branch" или "department"
	private Department department;
	private Department parentDepartment;    //родительский департамент, если есть
	private Long parentId;    //id родительского подразделения

	private static final String TIME_FORMAT = "HH:mm";

	/**
	 * Время преобразует в строку ЧЧ:ММ
	 * @param time время
	 * @return строковое представление времени
	 */
	public static String formatTime(Time time)
	{
		if (time == null)
			return null;
		return new SimpleDateFormat(TIME_FORMAT).format(time);
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public String getMode()
	{
		return mode;
	}

	public void setMode(String mode)
	{
		this.mode = mode;
	}

	public Long getParentId()
	{
		return parentId;
	}

	public void setParentId(Long parentId)
	{
		this.parentId = parentId;
	}

	public Department getParentDepartment()
	{
		return parentDepartment;
	}

	public void setParentDepartment(Department parentDepartment)
	{
		this.parentDepartment = parentDepartment;
	}

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getBooleanField(String name, String description)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		return fieldBuilder.build();
	}

	private static Field getTimeField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new SqlTimeParser());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getLongField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getIntField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static Field getMoneyField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(MoneyType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	private static RequiredFieldValidator getRequiredFieldValidator(String enabledExpression)
	{
		RequiredFieldValidator fieldValidator = new RequiredFieldValidator();
		fieldValidator.setEnabledExpression(new RhinoExpression(enabledExpression));
		return fieldValidator;
	}

	@SuppressWarnings("OverlyLongMethod")
	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField(NAME_FIELD_NAME, "Наименование", new RequiredFieldValidator(), new RegexpFieldValidator(".{0,256}", "Наименование должно быть не более 256 символов")));
		formBuilder.addField(getBooleanField(SERVICE_FIELD_NAME, "Обслуживается в системе"));
		formBuilder.addField(getBooleanField(MAIN_DEPARTMENT_FIELD_NAME, "Признак головного подразделения"));
		formBuilder.addField(getStringField(EXTERNAL_SYSTEM_UUID_FIELD_NAME, "Внешняя система", new RequiredFieldValidator()));
		formBuilder.addField(getStringField(CITY_FIELD_NAME, "Город", new RegexpFieldValidator(".{0,256}", "Поле город должно быть не более 256 символов")));
		formBuilder.addField(getStringField(ADDRESS_FIELD_NAME, "Почтовый адрес", new RegexpFieldValidator(".{0,256}", "Почтовый адрес должен быть не более 256 символов")));
		formBuilder.addField(getStringField(LOCATION_FIELD_NAME, "Местонахождение", new RegexpFieldValidator(".{0,256}", "Местонахождение должно быть не более 256 символов")));
		formBuilder.addField(getStringField(TELEPHONE_FIELD_NAME, "Телефон", new RegexpFieldValidator(".{1,15}", "Телефон должен быть не более 15 символов")));

		formBuilder.addField(getTimeField(WEEK_OPERATION_TIME_BEGIN_FIELD_NAME, "Операционное время в рабочие дни (начало)",
				getRequiredFieldValidator("form.service == true"), new RegexpFieldValidator("\\d{1,2}:\\d{2}", "Операционное время в рабочие дни (начало) должно быть в формате ЧЧ:ММ")));
		formBuilder.addField(getTimeField(WEEK_OPERATION_TIME_END_FIELD_NAME, "Операционное время в рабочие дни (завершение)",
				getRequiredFieldValidator("form.service == true"), new RegexpFieldValidator("\\d{1,2}:\\d{2}", "Операционное время в рабочие дни (завершение) должно быть в формате ЧЧ:ММ")));
		formBuilder.addField(getStringField(TIME_SCALE_FIELD_NAME, "Шкала времени",
				new RegexpFieldValidator(".{1,30}", "Шкала времени должна быть не более 30 символов")));
		formBuilder.addField(getLongField(NOTIFY_CONTRACT_CANCELLATION_FIELD_NAME, "Уведомлять о расторжении договора",
				new RegexpFieldValidator("\\d{1,2}", "Уведомлять о расторжении договора должна числом от 1 до 99")));
		formBuilder.addField(getBooleanField(ESB_SUPPORTED, "Поддерживает \"Базовый продукт\""));

		formBuilder.addField(getStringField(REGION, "Номер тербанка", new RequiredFieldValidator(), new RegexpFieldValidator("[1-9]\\d*", "Пожалуйста, введите номер ТБ без лидирующих нулей.")));
		formBuilder.addField(getStringField(BRANCH, "Номер отделения", new RegexpFieldValidator("[1-9]\\d*", "Пожалуйста, введите номер отделения без лидирующих нулей.")));
		formBuilder.addField(getStringField(OFFICE, "Номер филиала", new RegexpFieldValidator("[1-9]\\d*", "Пожалуйста, введите номер филиала без лидирующих нулей.")));

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Признак автоматизации");
		fieldBuilder.setName(AUTOMATION);
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setParser(new EnumParser<DepartmentAutoType>(DepartmentAutoType.class));
		fieldBuilder.addValidators(new RequiredFieldValidator(), new EnumFieldValidator<DepartmentAutoType>(DepartmentAutoType.class, "Неверное значение признака автоматизации."));
		fieldBuilder.setEnabledExpression(new RhinoExpression("form." + BRANCH + " == null || form." + BRANCH + "== ''"));
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addField(getStringField(OFFICE_ID_FIELD_NAME, "Офис", getRequiredFieldValidator("!form.region && (form.service == true || form.mainDepartment == true)")));
		formBuilder.addField(getBooleanField(CREDIT_CARD_OFFICE, "Подразделение выдаёт кредитные карты"));
		formBuilder.addField(getBooleanField(OPEN_IMA_OFFICE, "Возможность открытия ОМС в офисе"));
		formBuilder.addField(getMoneyField(CONNECTION_CHARGE_FIELD_NAME, "Тариф за организацию расчетного обслуживания", getRequiredFieldValidator("form.service == true")));
		formBuilder.addField(getMoneyField(MONTHLY_CHARGE_FIELD_NAME, "Ежемесячная плата", getRequiredFieldValidator("form.service == 'true'")));
		formBuilder.addField(getIntField(TIME_ZONE, "Часовой пояс", getRequiredFieldValidator("!form.useParentTimeZone")));
		formBuilder.addField(getLongField(BILLING_ID_FIELD_NAME, "Биллинг"));
		formBuilder.addField(getBooleanField(POSSIBLE_LOANS_OPERATION_FIELD_NAME, "Возможны операции по кредитованию физ. лиц"));
		formBuilder.addField(getBooleanField(NOT_ACTIVE, "Неактивное"));

		return formBuilder.build();
	}
}
