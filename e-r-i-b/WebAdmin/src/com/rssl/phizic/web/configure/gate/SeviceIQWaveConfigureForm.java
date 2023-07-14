package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.IntType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.web.settings.EditPropertiesFormBase;

/**
 * ����� �������������� �������� ��� �������� IQWave
 * @author basharin
 * @ created 18.02.2013
 * @ $Author$
 * @ $Revision$
 */

public class SeviceIQWaveConfigureForm extends EditPropertiesFormBase
{
	private static final Form FORM = createForm();

	/**
	 * @return ���������� �����
	 */
	public Form getForm()
	{
		return FORM;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(JobRefreshConfig.NUM_REQUESTS_OPERATION_PREFIX + JobRefreshConfig.CHECK_PAYMENTS_EXEC_JOB);
		fieldBuilder.setType(IntType.INSTANCE.getName());
		fieldBuilder.setDescription("����������� ���������� ��������");
		fieldBuilder.addValidators(new RequiredFieldValidator(),
				new RegexpFieldValidator("\\d{1,9}", "���� \" " + fieldBuilder.getDescription() + "\" ������ ��������� �� ������ 9 ����."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}