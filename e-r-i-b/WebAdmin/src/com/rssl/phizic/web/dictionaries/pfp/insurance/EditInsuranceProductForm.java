package com.rssl.phizic.web.dictionaries.pfp.insurance;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.js.RhinoExpression;
import com.rssl.common.forms.types.BooleanType;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.*;
import com.rssl.phizic.business.claims.forms.validators.RequiredMultiFieldValidator;
import com.rssl.phizic.web.dictionaries.pfp.products.EditProductFormBase;
import com.rssl.phizic.web.dictionaries.pfp.products.PFPProductEditFormHelper;
import org.apache.commons.lang.ArrayUtils;

/**
 * @author akrenev
 * @ created 14.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class EditInsuranceProductForm extends EditProductFormBase
{
	private Long[] periodTypeIds = new Long[]{};  //�������������� ����� ��������
	private Long[] lineNumbers = new Long[]{};    //������ ������ (������������ �� �����)

	public Long[] getPeriodTypeIds()
	{
		return periodTypeIds;
	}

	public void setPeriodTypeIds(Long[] periodTypeIds)
	{
		this.periodTypeIds = periodTypeIds;
	}

	public Long[] getLineNumbers()
	{
		return lineNumbers;
	}

	public void setLineNumbers(Long[] lineNumbers)
	{
		this.lineNumbers = lineNumbers;
	}

	public Form createEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		formBuilder.addFields(PFPProductEditFormHelper.getIncomeFields());
		formBuilder.addFields(PFPProductEditFormHelper.getTargetGroupFields());
		formBuilder.addFields(getBaseFormFields());

		FieldBuilder fieldBuilder = new FieldBuilder();

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new RegexpFieldValidator(".{1,250}", "���� �������� ������ ��������� �� ����� 250 ��������."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceCompanyId");
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("insuranceCompanyName");
		fieldBuilder.setDescription("��������� ��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("typeId");
		fieldBuilder.setDescription("��� �����������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("typeParentId");
		fieldBuilder.setDescription("��� �����������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("forComplex");
		fieldBuilder.setDescription("����������� �������");
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		formBuilder.addFields(getImageField());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("minAge");
		fieldBuilder.setDescription("����������� �������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ��� �������� ����� ��������� ������ �����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("maxAge");
		fieldBuilder.setDescription("������������ �������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RegexpFieldValidator("\\d{0,3}", "���� ��� �������� ����� ��������� ������ �����."));
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("defaultPeriod");
		fieldBuilder.setDescription("������ �� ���������");
		fieldBuilder.setType(LongType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());

		MoneyFieldValidator sumValidator = new MoneyFieldValidator();
		sumValidator.setMessage("���� ����� ����� ��������� ������ �����.");
		CompareValidator compareValidator;
		IntegerIntervalsHelper periodValidator = new IntegerIntervalsHelper();
		periodValidator.setMinValue(1);
		periodValidator.setMaxValue(150);
		periodValidator.setMessage("�� ����������� ������� ���� ���������� ��������. ����������, ������� ����� ��� �������� ���� ����� �������, ��������: 1,4-5,9,12.");
		String fieldName;
		for (Long rowNum: lineNumbers)
		{
			fieldName = "idPeriodType" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("������");
			fieldBuilder.setType(LongType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());

			fieldName = "namePeriodType" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("������");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator());
			formBuilder.addField(fieldBuilder.build());

			fieldName = "period" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("����");
			fieldBuilder.setType(StringType.INSTANCE.getName());
			fieldBuilder.addValidators(new RequiredFieldValidator(),
									   periodValidator);
			formBuilder.addField(fieldBuilder.build());

			fieldName = "minSum" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("����������� ����� ������");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setValidators(sumValidator);
			formBuilder.addField(fieldBuilder.build());

			fieldName = "maxSum" + rowNum;
			fieldBuilder = new FieldBuilder();
			fieldBuilder.setName(fieldName);
			fieldBuilder.setDescription("������������ ����� ������");
			fieldBuilder.setType(MoneyType.INSTANCE.getName());
			fieldBuilder.setValidators(sumValidator);
			formBuilder.addField(fieldBuilder.build());

			compareValidator = new CompareValidator();
		    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
		    compareValidator.setBinding(CompareValidator.FIELD_O1, "minSum" + rowNum);
		    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxSum" + rowNum);
		    compareValidator.setMessage("�� ����������� ������� ����������� �����. ����������� ����� �� ������ ��������� ������������ �����.");

			RequiredMultiFieldValidator requiredSumMultiFieldValidator = new RequiredMultiFieldValidator();
			requiredSumMultiFieldValidator.setBinding("minSum" + rowNum, "minSum" + rowNum);
			requiredSumMultiFieldValidator.setBinding("maxSum" + rowNum, "maxSum" + rowNum);
			requiredSumMultiFieldValidator.setMessage("����������, ������� ����������� � ������������ ����� ������� ��� �������� � ������ ��������� �������.");
			requiredSumMultiFieldValidator.setEnabledExpression(new RhinoExpression("form.forComplex != true"));
			formBuilder.addFormValidators(compareValidator, requiredSumMultiFieldValidator);
		}

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("description");
		fieldBuilder.setDescription("��������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator(),
								   new MultiLineTextValidator("���� �������� ������ ��������� �� �����", 500));
		formBuilder.addField(fieldBuilder.build());

		RequiredFieldValidator validator = new RequiredFieldValidator();
		validator.setEnabledExpression(new ConstantExpression(ArrayUtils.isEmpty(lineNumbers)));
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("idPeriodType");
		fieldBuilder.setDescription("������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.addValidators(validator);
		formBuilder.addField(fieldBuilder.build());

		RequiredMultiFieldValidator requiredMultiFieldValidator = new RequiredMultiFieldValidator();
		requiredMultiFieldValidator.setBinding("minAge", "minAge");
		requiredMultiFieldValidator.setBinding("maxAge","maxAge");
		requiredMultiFieldValidator.setMessage("����������, ������� ����������� � ������������ ������� ��������, ������� ������ ���� �������� ������ ��������� �������.");

		compareValidator = new CompareValidator();
	    compareValidator.setParameter(CompareValidator.OPERATOR, CompareValidator.LESS_EQUAL);
	    compareValidator.setBinding(CompareValidator.FIELD_O1, "minAge");
	    compareValidator.setBinding(CompareValidator.FIELD_O2, "maxAge");
	    compareValidator.setMessage("�� ����������� ������� ����������� �������. ����������� ������� �� ������ ��������� ������������ �������.");

		formBuilder.addFormValidators(PFPProductEditFormHelper.getIncomeFormValidators());
		formBuilder.addFormValidators(compareValidator, requiredMultiFieldValidator);
		formBuilder.addFormValidators(PFPProductEditFormHelper.getTargetGroupValidator(new RhinoExpression("form." + ENABLED_PARAMETER_NAME + " == true && form.forComplex == false")));

		return formBuilder.build();
	}
}
