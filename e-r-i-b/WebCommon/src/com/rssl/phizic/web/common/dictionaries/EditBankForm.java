package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author: Pakhomova
 * @created: 15.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class EditBankForm extends EditFormBase
{
	private Comparable synchKey;

	public Comparable getSynchKey()
	{
		return synchKey;
	}
	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	public static final Form EDIT_BANK_FORM = createForm();

	private static Form createForm()
    {
        FormBuilder formBuilder = new FormBuilder();

        FieldBuilder fieldBuilder;

	    fieldBuilder = new FieldBuilder();

        fieldBuilder.setName("name");
        fieldBuilder.setDescription("������������");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new RegexpFieldValidator(".{1,256}","������������ ����� �� ������ ��������� 256 ��������")
		);

	    formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("���");
		fieldBuilder.setName("bic");
		fieldBuilder.setType("string");
        fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new RegexpFieldValidator("\\d{9}", "��� ����� ������ ��������� 9 ����")
		);
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("����.����");
		fieldBuilder.setName("corrAccount");
		fieldBuilder.setType("string");
        fieldBuilder.addValidators (
			        new RequiredFieldValidator(),
			        new RegexpFieldValidator("\\d{20,25}", "����.���� ������ ��������� �� 20 �� 25 ����")
		);
		formBuilder.addField(fieldBuilder.build());

	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("shortName");
        fieldBuilder.setDescription("������� ������������");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (
			        new RegexpFieldValidator(".{1,30}","������� ������������ ����� �� ������ ��������� 30 ��������")
		);
	    formBuilder.addField(fieldBuilder.build());


	    fieldBuilder = new FieldBuilder();
        fieldBuilder.setName("place");
        fieldBuilder.setDescription("���������������");
	    fieldBuilder.setType("string");
	    fieldBuilder.addValidators (
			        new RegexpFieldValidator(".{1,30}","��������������� ����� �� ������ ��������� 30 ��������")
		);
	    formBuilder.addField(fieldBuilder.build());

	    return formBuilder.build();
    }
}
