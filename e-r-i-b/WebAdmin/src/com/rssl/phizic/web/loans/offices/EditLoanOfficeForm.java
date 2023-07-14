package com.rssl.phizic.web.loans.offices;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.gate.dictionaries.officies.LoanOffice;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;

/**
 * @author Krenev
 * @ created 12.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditLoanOfficeForm extends EditFormBase
{
	private String synchKey;
	private LoanOffice office;

	public LoanOffice getOffice()
	{
		return office;
	}

	public void setOffice(LoanOffice office)
	{
		this.office = office;
	}

	public static final Form FORM  = createForm();

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("������������");
		fieldBuilder.setName("name");
		fieldBuilder.setType("string");
		fieldBuilder.setValidators(new RequiredFieldValidator());
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�����");
		fieldBuilder.setName("address");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("T������");
		fieldBuilder.setName("telephone");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("�������������� ����������");
		fieldBuilder.setName("description");
		fieldBuilder.setType("string");
		fb.addField(fieldBuilder.build());

		return fb.build();
	}

	public String getSynchKey()
	{
		return synchKey;
	}

	public void setSynchKey(String synchKey)
	{
		this.synchKey = synchKey;
	}
}
