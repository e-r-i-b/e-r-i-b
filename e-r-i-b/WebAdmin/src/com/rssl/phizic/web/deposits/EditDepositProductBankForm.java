package com.rssl.phizic.web.deposits;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;

/**
 * @author Evgrafov
 * @ created 12.05.2006
 * @ $Author: osminin $
 * @ $Revision: 11448 $
 */

@SuppressWarnings({"JavaDoc"})
public class EditDepositProductBankForm extends EditFormBase
{
	private String html;
	private Long   departmentId;

	public String getHtml()
	{
		return html;
	}

	public void setHtml(String html)
	{
		this.html = html;
	}

	public static Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb;

		fb = new FieldBuilder();
		fb.setName("name");
		fb.setDescription("��� ����������� ��������");
		fb.setValidators(
				new RequiredFieldValidator(),
				new RegexpFieldValidator(".{0,256}", "��� ����������� �������� ������ ���� �� ����� 256 ��������")
		);

		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("productDescription");
		fb.setDescription("�������� ����������� ��������");

		formBuilder.addField(fb.build());

		fb = new FieldBuilder();
		fb.setName("selectedIds");
		fb.setDescription("ID ����� �������");
		fb.setValidators( new RequiredFieldValidator("�������� ���� �� ���� ��� ������") );

		formBuilder.addField(fb.build());

		return formBuilder.build();
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}
}