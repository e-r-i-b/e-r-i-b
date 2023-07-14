package com.rssl.phizic.web.configure.gate;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ���������� ������� ����������� ��������
 */

public class RunGateMonitoringForm extends EditFormBase
{
	public static final String SERVICE_NAME_FIELD_NAME  = "serviceName";

	private static final Form FORM = createForm();

	private String[] gateUrls;

	/**
	 * @return ���������� �����
	 */
	public static Form getForm()
	{
		return FORM;
	}

	/**
	 * @return ������ �� �����
	 */
	public String[] getGateUrls()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return gateUrls;
	}

	/**
	 * ������ ������ �� �����
	 * @param gateUrls ������ �� �����
	 */
	public void setGateUrls(String[] gateUrls)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.gateUrls = gateUrls;
	}

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName(SERVICE_NAME_FIELD_NAME);
		fieldBuilder.setDescription("������������ �������");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		formBuilder.addField(fieldBuilder.build());
		return formBuilder.build();
	}

}
