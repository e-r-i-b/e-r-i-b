package com.rssl.phizic.web.employees;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.payments.forms.validators.FailureValidator;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.List;

/**
 * @author Roshka
 * @ created 05.12.2005
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"JavaDoc", "ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class EmployeeListForm extends ListLimitActionForm
{
	public static final Form FILTER_FORM = createForm();
	private static final String FIO_FIELD_NAME = "fio";
	private static final String LOGIN_FIELD_NAME = "login";
	private static final String EMPLOYE_ID_FIELD_NAME = "id";
	private static final String TB_FIELD_NAME = "terbankCode";
	private static final String OSB_FIELD_NAME = "branchCode";
	private static final String FIELDS_EMPTY_MESSAGE = "Пожалуйста, заполните хотя бы одно из полей фильтра: сотрудник, идентификатор пользователя, логин, ТБ и номер отделения.";
	private String blockReason;

	private String blockStartDate;
	private String blockEndDate;

	private Long accessSchemeId;
	private List<AssignAccessHelper> helpers;
	private List<Department> allowedTB;
	private static final int DEFAULT_PAGE_SIZE = 10;

	private int paginationSize = DEFAULT_PAGE_SIZE;
	private int paginationOffset = 0;

	public int getPaginationSize()
	{
		return paginationSize;
	}

	public void setPaginationSize(int paginationSize)
	{
		this.paginationSize = paginationSize;
	}

	public int getPaginationOffset()
	{
		return paginationOffset;
	}

	public void setPaginationOffset(int paginationOffset)
	{
		this.paginationOffset = paginationOffset;
	}
	public Long getAccessSchemeId()
	{
		return accessSchemeId;
	}

	public void setAccessSchemeId(Long accessSchemeId)
	{
		this.accessSchemeId = accessSchemeId;
	}

	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	public void setHelpers(List<AssignAccessHelper> helpers)
	{
		this.helpers = helpers;
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

	public List<Department> getAllowedTB()
	{
		return allowedTB;
	}

	public void setAllowedTB(List<Department> allowedTB)
	{
		this.allowedTB = allowedTB;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Сотрудник");
		fieldBuilder.setName(FIO_FIELD_NAME);
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("info");
		fieldBuilder.setDescription("Доп. информация");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("state");
		fieldBuilder.setDescription("Статус");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("blocked");
		fieldBuilder.setDescription("Статус");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setValueExpression(new RhinoExpression("form.state"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Тербанк");
		fieldBuilder.setName(TB_FIELD_NAME);
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(OSB_FIELD_NAME);
		fieldBuilder.setDescription("Номер отделения");
		fieldBuilder.setValidators(new RegexpFieldValidator("\\d{1,4}", "Номер отделения должен состоять из 4 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("departmentCode");
		fieldBuilder.setDescription("Номер филиала");
		fieldBuilder.setValidators(new RegexpFieldValidator("\\d{1,7}", "Номер филиала не должен превышать 7 цифр"));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(EMPLOYE_ID_FIELD_NAME);
		fieldBuilder.setDescription("Идентификатор (ID)");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(LOGIN_FIELD_NAME);
		fieldBuilder.setDescription("Логин");
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator multiFieldValidator1 = new RequiredMultiFieldValidator();
		multiFieldValidator1.setBinding(FIO_FIELD_NAME, FIO_FIELD_NAME);
		multiFieldValidator1.setBinding(EMPLOYE_ID_FIELD_NAME, EMPLOYE_ID_FIELD_NAME);
		multiFieldValidator1.setBinding(LOGIN_FIELD_NAME, LOGIN_FIELD_NAME);
		multiFieldValidator1.setBinding(TB_FIELD_NAME, TB_FIELD_NAME);
		multiFieldValidator1.setMessage(FIELDS_EMPTY_MESSAGE);

		RequiredMultiFieldValidator multiFieldValidator2 = new RequiredMultiFieldValidator();
		multiFieldValidator2.setBinding(FIO_FIELD_NAME, FIO_FIELD_NAME);
		multiFieldValidator2.setBinding(EMPLOYE_ID_FIELD_NAME, EMPLOYE_ID_FIELD_NAME);
		multiFieldValidator2.setBinding(LOGIN_FIELD_NAME, LOGIN_FIELD_NAME);
		multiFieldValidator2.setBinding(OSB_FIELD_NAME, OSB_FIELD_NAME);
		multiFieldValidator2.setMessage(FIELDS_EMPTY_MESSAGE);

		FailureValidator branchFailureValidator = new FailureValidator();
		branchFailureValidator.setEnabledExpression(new RhinoExpression("form.terbankCode == '' && form.branchCode != ''"));
		branchFailureValidator.setMessage("Для поиска укажите тербанк, к которому относится сотрудник.»");

		FailureValidator departmentFailureValidator = new FailureValidator();
		departmentFailureValidator.setEnabledExpression(new RhinoExpression("(form.terbankCode == '' || form.branchCode == '') && form.departmentCode != ''"));
		departmentFailureValidator.setMessage("Для поиска укажите тербанк и ОСБ, в котором работает сотрудник.» ");

		formBuilder.addFormValidators(branchFailureValidator, departmentFailureValidator, multiFieldValidator1, multiFieldValidator2);

		return formBuilder.build();
	}
}
