package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.parsers.DateParser;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * @author gulov
 * @ created 23.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class EditLimitForm extends EditFormBase
{
	public static final Form EDIT_FORM = createEditForm();

	private Long departmentId;
	private String securityType;

	private List<GroupRisk> groupsRisk;

	public List<GroupRisk> getGroupsRisk()
	{
		return groupsRisk;
	}

	public void setGroupsRisk(List<GroupRisk> groupsRisk)
	{
		this.groupsRisk = groupsRisk;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public String getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(String securityType)
	{
		this.securityType = securityType;
	}

	protected static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		DateParser timeParser = new DateParser("HH:mm:ss");

		FieldBuilder fb = new FieldBuilder();
		fb.setName("status");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Статус лимита");
		formBuilder.addField(fb.build());

		//поле необходимо при редактировании лимита, т.к. restrictionType в этом случае нередактируется и в EnabledExpression не можем получить к нему доступ
		fb = new FieldBuilder();
		fb.setName("restrictionTypeForExpression");
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Статус лимита");
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("groupRiskId");
		fb.setEnabledExpression(new RhinoExpression("form.status == null"));
		fb.setType(LongType.INSTANCE.getName());
		fb.setDescription("Группа риска");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("startDate");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("Дата начала действия лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("dd.MM.yyyy", "Дата должна быть в формате ДД.ММ.ГГГГ"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("startDateTime");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("Время начала действия лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("HH:mm:ss", "Время должно быть в формате ЧЧ:ММ:СС"));
		fb.setParser(timeParser);
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.00");
		rangeValidator.setMessage("Укажите сумму лимита");

		MoneyFieldValidator minAmountValidator = new MoneyFieldValidator();
		minAmountValidator.setParameter("maxValue", "9999999.99");
		minAmountValidator.setMessage("Укажите сумму лимита в правильном формате: #######.##");

		fb = new FieldBuilder();
		fb.setName("restrictionType");
		fb.setEnabledExpression(new RhinoExpression("form.status == null"));
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("Лимит");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("amount");
		fb.setEnabledExpression(new RhinoExpression("(form.status == null && (form.restrictionType == 'AMOUNT_IN_DAY' || form.restrictionType == 'MIN_AMOUNT')) " +
														"|| (form.status == 'DRAFT' && (form.restrictionTypeForExpression == 'AMOUNT_IN_DAY' || form.restrictionTypeForExpression == 'MIN_AMOUNT'))"));
		fb.setType(MoneyType.INSTANCE.getName());
		fb.setDescription("Величина лимита");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(), minAmountValidator, rangeValidator);
		formBuilder.addField(fb.build());

		NumericRangeValidator minOperationCounrValidator = new NumericRangeValidator();
		minOperationCounrValidator.setParameter("minValue", "0");
		minOperationCounrValidator.setMessage("Укажите положительное количество операций");

		fb = new FieldBuilder();
		fb.setName("operationCount");
		fb.setEnabledExpression(new RhinoExpression("(form.status == null && (form.restrictionType == 'OPERATION_COUNT_IN_DAY' || form.restrictionType == 'OPERATION_COUNT_IN_HOUR')) " +
														"|| (form.status == 'DRAFT' && (form.restrictionTypeForExpression == 'OPERATION_COUNT_IN_DAY' || form.restrictionTypeForExpression == 'OPERATION_COUNT_IN_HOUR'))"));
		fb.setType(LongType.INSTANCE.getName());
		fb.setDescription("Количество операций");
		fb.addValidators(new RequiredFieldValidator(), minOperationCounrValidator, new RegexpFieldValidator("\\d{1,10}", "Пожалуйста, укажите в поле Число операций не более 10 цифр."));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("operationType");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(StringType.INSTANCE.getName());
		fb.setDescription("При нарушении условий по лимиту");
		fb.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeValidator.setParameter(DateTimeCompareValidator.CUR_DATE, "to_cleartime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "startDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("Дата начала действия лимита не может быть меньше текущей даты");

		formBuilder.addFormValidators(dateTimeValidator);

		return formBuilder.build();
	}
}
