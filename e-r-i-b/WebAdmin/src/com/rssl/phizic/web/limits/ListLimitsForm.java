package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.parsers.BigDecimalParser;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.DateFieldValidator;
import com.rssl.common.forms.validators.DateTimeCompareValidator;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.business.limits.GroupRisk;
import com.rssl.phizic.business.limits.Limit;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.List;

/**
 * @author gulov
 * @ created 19.08.2010
 * @ $Authors$
 * @ $Revision$
 */
public class ListLimitsForm extends ListFormBase<Pair<String, List<Limit>>>
{
	public static Form FILTER_FORM = createForm();

	private Long departmentId;
	private String securityType;
	private List<GroupRisk> groupsRiskForFilter;

	public List<GroupRisk> getGroupsRiskForFilter()
	{
		return groupsRiskForFilter;
	}

	public void setGroupsRisForFilter(List<GroupRisk> groupsRiskForFilter)
	{
		this.groupsRiskForFilter = groupsRiskForFilter;
	}


	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long id)
	{
		this.departmentId = id;
	}

	public String getSecurityType()
	{
		return securityType;
	}

	public void setSecurityType(String securityType)
	{
		this.securityType = securityType;
	}
	
	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

	    FieldBuilder fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromCreationDate");
		fb.setDescription("���� ����� �");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toCreationDate");
		fb.setDescription("���� ����� ��");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("fromStartDate");
		fb.setDescription("���� ������ �������� ������ �");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setType(DateType.INSTANCE.getName());
		fb.setName("toStartDate");
		fb.setDescription("���� ������ �������� ������ ��");
		fb.clearValidators();
		fb.addValidators(new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setDescription("�����");
		fb.setName("restrictionType");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setMessage("������� ������������� �������� ������");

		fb = new FieldBuilder();
		fb.setDescription("�������� ������");
	    fb.setEnabledExpression(new RhinoExpression("form.restrictionType == '' || form.restrictionType == 'AMOUNT_IN_DAY' || form.restrictionType == 'MIN_AMOUNT'"));
		fb.setName("amount");
		fb.setType(StringType.INSTANCE.getName());
		fb.clearValidators();
		fb.setParser(new BigDecimalParser());
	    fb.clearValidators();
		fb.addValidators(new RegexpFieldValidator("^\\d{1,7}+((\\.|,)\\d{0,2})?$",
				"������� �������� ������/���-�� �������� � ���������� �������: #######.##"), rangeValidator);
	    formBuilder.addField(fb.build());

	    rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0");
		rangeValidator.setMessage("������� ������������� ����� ��������");

	    fb = new FieldBuilder();
		fb.setName("operationCount");
	    fb.setEnabledExpression(new RhinoExpression("form.restrictionType == '' || form.restrictionType == 'OPERATION_COUNT_IN_DAY' || form.restrictionType == 'OPERATION_COUNT_IN_HOUR'"));
		fb.setType(LongType.INSTANCE.getName());
		fb.setDescription("���-�� ��������");
	    fb.addValidators(new RegexpFieldValidator("\\d{1,10}", "���� \"R��-�� ��������\" ������ ��������� �� ������ 10 ����."));
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
	    fb.setDescription("������");
	    fb.setName("status");
	    fb.setType(StringType.INSTANCE.getName());
	    formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setDescription("��� ��������� ������� �� ������");
		fb.setName("operationType");
		fb.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fb.build());

	    fb = new FieldBuilder();
		fb.setName("groupRisk");
		fb.setType(LongType.INSTANCE.getName());
		fb.setDescription("������ �����");
	    fb.addValidators(new RegexpFieldValidator("\\d*", "���� \"������ �����\" ������ ��������� ������ �����."));
		formBuilder.addField(fb.build());

	    formBuilder.setFormValidators(
			createCompareValidator("toCreationDate", "fromCreationDate", "�������� ���� ����� ������ ���� ������ ���� ����� ���������!"),
			createCompareValidator("toStartDate", "fromStartDate","�������� ���� ������ �������� ������ ���� ������ ���� ����� ���������!")
	    );

	    return formBuilder.build();
    }

	private static DateTimeCompareValidator createCompareValidator(String fieldFromDate, String fieldToDate, String message)
	{
		DateTimeCompareValidator dateTimeCompareValidator = new DateTimeCompareValidator();
		dateTimeCompareValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE_EQUAL);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, fieldFromDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, fieldFromDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, fieldToDate);
		dateTimeCompareValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, fieldToDate);
		dateTimeCompareValidator.setMessage(message);

		return dateTimeCompareValidator;
	}
}
