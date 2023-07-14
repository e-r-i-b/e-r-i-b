package com.rssl.phizic.web.dictionaries.provider;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegExpPatternValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.business.fields.FieldDescription;

import java.util.List;

/**
 * @author krenev
 * @ created 05.04.2010
 * @ $Author$
 * @ $Revision$
 */
public class EditFieldValidatorForm extends EditFormBase
{
	public static final Form EDIT_FORM = createForm();
	private Long fieldId;//������������ ����.
	private Long validatorId; // ������������ ����������
	private boolean editable;
	//� id ��������� ������������ ����������.
	private List<FieldDescription> allFields;

	public Long getFieldId()
	{
		return fieldId;
	}

	public void setFieldId(Long fieldId)
	{
		this.fieldId = fieldId;
	}

	public Long getValidatorId()
	{
		return validatorId;
	}

	public void setValidatorId(Long validatorId)
	{
		this.validatorId = validatorId;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ����������");
		fieldBuilder.setName("validatorType");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��������� ����������");
		fieldBuilder.setName("validatorExpression");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(new RequiredFieldValidator(), new RegExpPatternValidator());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���������, ������������ �������� ��� ���������� ����������");
		fieldBuilder.setName("validatorErrorMessage");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("��� ��������� ����������");
		fieldBuilder.setName("validatorRuleType");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());

		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public List<FieldDescription> getAllFields()
	{
		return allFields;
	}

	public void setAllFields(List<FieldDescription> allFields)
	{
		this.allFields = allFields;
	}
}