package com.rssl.phizic.web.employees;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.*;
import com.rssl.common.forms.validators.passwords.PasswordStrategyValidator;
import com.rssl.phizic.auth.SecurityService;
import com.rssl.phizic.business.departments.froms.validators.DepartmentByCodeValidator;
import com.rssl.phizic.business.payments.forms.validators.DublicateUserIdValidator;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.employee.Employee;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.web.common.EditFormBase;
import org.apache.commons.lang.ArrayUtils;

import java.math.BigInteger;

/**
 * @author Roshka
 * @ created 28.12.2005
 * @ $Author$
 * @ $Revision$
 */
public class EmployeeEditForm extends EditFormBase
{
	public static final Form CREATE_FORM = createForm(true);
	public static final Form EDIT_FORM = createForm(false);
	public static final Form CHANGE_PASSWORD_FORM = createChangePasswordForm();

	public static final String CA_ADMIN = "CAAdmin";
	public static final String VSP_EMPLOYEE = "VSPEmployee";
	public static final String DEPARTMENT_TB_FIELD_NAME = "departmentTB";
	public static final String DEPARTMENT_OSB_FIELD_NAME = "departmentOSB";
	public static final String DEPARTMENT_VSP_FIELD_NAME = "departmentVSP";

	private Long     employeeId;
    private Employee employee=null;
	private String   blockReason;
	private String blockStartDate;
	private String blockEndDate;
	private boolean needSetLogin;
	private boolean allowEditCA;
	private boolean vspEmployee;
	private int maxLengthLogins;

    public Long getEmployeeId()
    {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId)
    {
        this.employeeId = employeeId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

	public String getBlockReason()
	{
		return blockReason;
	}

	public void setBlockReason(String blockReason)
	{
		this.blockReason = blockReason;
	}

	public String getBlockStartDate()
	{
		return blockStartDate;
	}

	public void setBlockStartDate(String blockStartDate)
	{
		this.blockStartDate = blockStartDate;
	}

	public String getBlockEndDate()
	{
		return blockEndDate;
	}

	public void setBlockEndDate(String blockEndDate)
	{
		this.blockEndDate = blockEndDate;
	}

	public boolean isNeedSetLogin()
	{
		return needSetLogin;
	}

	public void setNeedSetLogin(boolean needSetLogin)
	{
		this.needSetLogin = needSetLogin;
	}

	/**
	 * @return разрешено ли редактирование признака "администратора ЦА"
	 */
	public boolean isAllowEditCA()
	{
		return allowEditCA;
	}

	public void setAllowEditCA(boolean allowEditCA)
	{
		this.allowEditCA = allowEditCA;
	}

	/**
	 * @return создание нового сотрудника происходит сотрудником с пометкой Сотрудник ВСП.
	 */
	public boolean isVspEmployee()
	{
		return vspEmployee;
	}

	/**
	 * @param vspEmployee создание нового сотрудника происходит сотрудником с пометкой Сотрудник ВСП.
	 */
	public void setVspEmployee(boolean vspEmployee)
	{
		this.vspEmployee = vspEmployee;
	}

	public void setMaxLengthLogins(int maxLengthLogins)
	{
		this.maxLengthLogins = maxLengthLogins;
	}

	public int getMaxLengthLogins()
	{
		return maxLengthLogins;
	}

	private static Form createForm(boolean create)
	{
		FormBuilder formBuilder = createFormBuilder(create);
		return formBuilder.build();
	}

	private static Form createChangePasswordForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		addPasswordFields(formBuilder);
		return formBuilder.build();
	}

	@SuppressWarnings({"OverlyLongMethod"})
	private static FormBuilder createFormBuilder(boolean create)
	{
		FormBuilder formBuilder = new FormBuilder();

		formBuilder.addField(getStringField("firstName", "Имя", new RequiredFieldValidator(), new LengthFieldValidator(BigInteger.valueOf(30))));
		formBuilder.addField(getStringField("patrName", "Отчество", new LengthFieldValidator(BigInteger.valueOf(30))));
		formBuilder.addField(getStringField("surName", "Фамилия", new RequiredFieldValidator(), new LengthFieldValidator(BigInteger.valueOf(30))));
		formBuilder.addField(getStringField("email", "E-Mail", new EmailFieldValidator()));
		formBuilder.addField(getStringField("mobilePhone", "Мобильный телефон", new RegexpFieldValidator("[+][0-9][(][0-9]{3}[)][0-9]{3}[-][0-9]{2}[-][0-9]{2}", "Формат поля Мобильный телефон должен иметь вид +X(XXX)XXX-XX-XX")));

		formBuilder.addField(getStringField("info", "Доп. информация", new MultiLineTextValidator("Поле Доп. информация не должно превышать", 150)));

		formBuilder.addField(getStringField(DEPARTMENT_TB_FIELD_NAME, "Подразделение банка (код)", new RequiredFieldValidator()));
		formBuilder.addField(getStringField(DEPARTMENT_OSB_FIELD_NAME, "Подразделение банка (код)"));
		formBuilder.addField(getStringField(DEPARTMENT_VSP_FIELD_NAME, "Подразделение банка (код)"));
		formBuilder.addField(getStringField("departmentDescription", "Подразделение банка", new RequiredFieldValidator()));

		formBuilder.addField(getLongField("id", "Идентификатор (ID)"));
		formBuilder.addField(getBooleanField(EmployeeEditForm.CA_ADMIN, "Администратор ЦА"));
		formBuilder.addField(getBooleanField(EmployeeEditForm.VSP_EMPLOYEE, "Сотрудник ВСП"));

		if(create)
		{
			int maxLengthLogin = ConfigFactory.getConfig(BusinessSettingsConfig.class).getMaxLengthLogins();
			LengthFieldValidator lengthFieldValidator = new LengthFieldValidator(BigInteger.valueOf(maxLengthLogin));
			lengthFieldValidator.setMessage("Логин пользователя не должен превышать " + lengthFieldValidator + " символов");
			formBuilder.addField(getStringField("userId", "Логин", new RequiredFieldValidator(), lengthFieldValidator, new DublicateUserIdValidator(SecurityService.SCOPE_EMPLOYEE, "Сотрудник с таким логином уже существует")));
			addPasswordFields(formBuilder);
		}

		formBuilder.addField(getStringField("sudirLogin", "Логин в СУДИР"));

		LogicOperatorValidator operatorValidator = new LogicOperatorValidator();
		operatorValidator.setParameter(LogicOperatorValidator.TRUE_OPERATOR_TRUE, "false");
		operatorValidator.setParameter(LogicOperatorValidator.TRUE_OPERATOR_FALSE, "true");
		operatorValidator.setParameter(LogicOperatorValidator.FALSE_OPERATOR_TRUE, "true");
		operatorValidator.setParameter(LogicOperatorValidator.FALSE_OPERATOR_FALSE, "true");
		operatorValidator.setBinding(LogicOperatorValidator.FIELD_VALUE1, EmployeeEditForm.CA_ADMIN);
		operatorValidator.setBinding(LogicOperatorValidator.FIELD_VALUE2, EmployeeEditForm.VSP_EMPLOYEE);
		operatorValidator.setMessage("Поля \"Сотрудник ЦА\" и \"Сотрудник ВСП\" не могут быть выбраны одновременно");
		formBuilder.addFormValidators(operatorValidator);

		DepartmentByCodeValidator departmentByCodeValidator = new DepartmentByCodeValidator();
		departmentByCodeValidator.setBinding(DepartmentByCodeValidator.TB_BINDING_NAME,  DEPARTMENT_TB_FIELD_NAME);
		departmentByCodeValidator.setBinding(DepartmentByCodeValidator.OSB_BINDING_NAME, DEPARTMENT_OSB_FIELD_NAME);
		departmentByCodeValidator.setBinding(DepartmentByCodeValidator.VSP_BINDING_NAME, DEPARTMENT_VSP_FIELD_NAME);
		departmentByCodeValidator.setMessage("Неверное значение в поле Подразделение");
		formBuilder.addFormValidators(departmentByCodeValidator);

		return formBuilder;
	}

	private static void addPasswordFields( FormBuilder formBuilder)
	{
		PasswordStrategyValidator pswValidator = new PasswordStrategyValidator();
		pswValidator.setParameter(PasswordStrategyValidator.STRATEGY_NAME, SecurityService.SCOPE_EMPLOYEE);
		formBuilder.addField(getStringField("password", "Пароль", new RequiredFieldValidator(), pswValidator, new RegexpFieldValidator(".{0,30}", "Пароль пользователя не должен превышать 30 символов")));
		formBuilder.addField(getStringField("confirmPassword", "Подтверждение пароля", new RequiredFieldValidator()));


		CompareValidator passwordsCompareValidator = new CompareValidator();
		passwordsCompareValidator.setBinding(CompareValidator.FIELD_O1, "password");
		passwordsCompareValidator.setBinding(CompareValidator.FIELD_O2, "confirmPassword");
		passwordsCompareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.EQUAL);
		passwordsCompareValidator.setMessage("Введите одинаковые значения в поля Пароль и Подтверждение пароля");

		formBuilder.addFormValidators(passwordsCompareValidator);
	}

	private static Field getStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators (validators);
		return fieldBuilder.build();
	}

	private static Field getBooleanField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators (validators);
		return fieldBuilder.build();
	}

	private static Field getLongField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators (validators);
		return fieldBuilder.build();
	}



}
