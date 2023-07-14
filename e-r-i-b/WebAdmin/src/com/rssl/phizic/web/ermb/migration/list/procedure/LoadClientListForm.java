package com.rssl.phizic.web.ermb.migration.list.procedure;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntegerType;
import com.rssl.common.forms.validators.NumericRangeValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.common.FilterActionForm;
import org.apache.struts.upload.FormFile;

/**
 * @author Gulov
 * @ created 06.12.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����� �������� ������ �������� ��� ��������
 */
public class LoadClientListForm extends FilterActionForm
{
	public static final String MONTH_COUNT = "monthCount";
	public static final Form LOAD_FORM = createForm();

	private static final String HISTORY_MONTHS = "3";
	private static final String INCORRECT_MONTH_COUNT = "������� ���������� �������� ���-�� ������� ���������� ������������ ��������. " +
														"���������� ���������� ������� ����������� �� 1 �� "+HISTORY_MONTHS+" �������";

	/**
	 * ���� ������ ��������
	 */
	private FormFile content;

	/**
	 * ������ ������
	 */
	private String status;

	public FormFile getContent()
	{
		return content;
	}

	public void setContent(FormFile content)
	{
		this.content = content;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(MONTH_COUNT);
		fieldBuilder.setDescription("���-�� ������� ���������� ������������ ��������");
		fieldBuilder.setType(IntegerType.INSTANCE.getName());
		fieldBuilder.addValidators(new RequiredFieldValidator());
		NumericRangeValidator validator = new NumericRangeValidator();
		validator.setParameter(NumericRangeValidator.PARAMETER_MIN_VALUE, "1");
		validator.setParameter(NumericRangeValidator.PARAMETER_MAX_VALUE, HISTORY_MONTHS);
		validator.setMessage(INCORRECT_MONTH_COUNT);
		fieldBuilder.addValidators(validator);
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
