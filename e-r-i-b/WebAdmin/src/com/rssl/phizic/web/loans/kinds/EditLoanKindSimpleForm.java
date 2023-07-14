package com.rssl.phizic.web.loans.kinds;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;

/**
 * @author Dorzhinov
 * @ created 25.05.2011
 * @ $Author$
 * @ $Revision$
 */
public class EditLoanKindSimpleForm extends EditFormBase
{
	public static final Form FORM = createForm();

	private static Form createForm ()
	{
        FormBuilder formBuilder = new FormBuilder();
        FieldBuilder fieldBuilder = new FieldBuilder();

        fieldBuilder.setName("name");
        fieldBuilder.setDescription("��������");
		fieldBuilder.setType("string");
	    fieldBuilder.addValidators
	    (
	        new RequiredFieldValidator(),
	        new RegexpFieldValidator(".{0,25}", "�������� ������ ���� �� ����� 25 ��������")
	    );
        formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
