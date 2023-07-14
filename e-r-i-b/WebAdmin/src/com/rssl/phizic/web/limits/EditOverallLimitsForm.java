package com.rssl.phizic.web.limits;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.DateType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.*;

/**
 * @author sergunin
 * @ created: 26.01.2015
 * @ $Author$
 * @ $Revision$
 * ����� ��� �������������� ��������� ������������� ������
 */
public class EditOverallLimitsForm extends EditLimitForm
{
	public static final Form EDIT_FORM = createEditForm();

	protected static Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName("startDate");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("���� ������ �������� ������");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
                new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("endDate");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(DateType.INSTANCE.getName());
		fb.setDescription("���� ��������� �������� ������");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(),
				new DateFieldValidator("dd.MM.yyyy", "���� ������ ���� � ������� ��.��.����"));
		formBuilder.addField(fb.build());

		NumericRangeValidator rangeValidator = new NumericRangeValidator();
		rangeValidator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "0.01");
		rangeValidator.setMessage("������� ����� ������");

		MoneyFieldValidator minAmountValidator = new MoneyFieldValidator();
		minAmountValidator.setParameter("maxValue", "9999999.99");
		minAmountValidator.setMessage("������� ����� ������ � ���������� �������: #######.##");

		fb = new FieldBuilder();
		fb.setName("amount");
		fb.setEnabledExpression(new RhinoExpression("form.status == null || form.status == 'DRAFT'"));
		fb.setType(MoneyType.INSTANCE.getName());
		fb.setDescription("�������� ������");
		fb.clearValidators();
		fb.addValidators(new RequiredFieldValidator(), minAmountValidator, rangeValidator);
		formBuilder.addField(fb.build());

		DateTimeCompareValidator dateTimeValidator = new DateTimeCompareValidator();
		dateTimeValidator.setParameter(DateTimeCompareValidator.OPERATOR, DateTimeCompareValidator.GREATE);
		dateTimeValidator.setParameter(DateTimeCompareValidator.CUR_DATE, "to_cleartime");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_DATE, "startDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_FROM_TIME, "");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_DATE, "endDate");
		dateTimeValidator.setBinding(DateTimeCompareValidator.FIELD_TO_TIME, "");
		dateTimeValidator.setMessage("�������������� ������ ����������, ��� ��� ������� ���� ������ � ��������� ���� �/��� ������ ��������� � �������");

        CompareValidator compareValidator = new CompareValidator();
        compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS);
        compareValidator.setBinding(CompareValidator.FIELD_O1, "startDate");
        compareValidator.setBinding(CompareValidator.FIELD_O2, "endDate");
        compareValidator.setMessage("�������������� ������ ����������, ��� ��� ������� ���� ������ � ��������� ���� �/��� ������ ��������� � �������");

		formBuilder.addFormValidators(dateTimeValidator, compareValidator);

		return formBuilder.build();
	}
}
