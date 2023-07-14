package com.rssl.phizic.web.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author komarov
 * @ created 17.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListMailSubjectForm  extends ListFormBase
{

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();
		// ��������
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("subject");
		fieldBuilder.setType("string");
		fieldBuilder.addValidators(
				new RegexpFieldValidator(".{0,50}", "������ ���� ������������ ������ ���� �� ����� 50 ��������")
		);
		fb.addField(fieldBuilder.build());
		return fb.build();
	}	
}
