package com.rssl.phizic.web.common.dictionaries;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.web.common.ListLimitActionForm;

import java.util.List;

/**
 * @author mihaylov
 * @ created 09.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowSecuritiesListForm extends ListLimitActionForm
{
	private List<String> securityTypes;

	public List<String> getSecurityTypes()
	{
		return securityTypes;
	}

	public void setSecurityTypes(List<String> securityTypes)
	{
		this.securityTypes = securityTypes;
	}

	public static final Form FILTER_FORM = createForm();

	private static Form createForm(){
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("Ёмитент");
		fieldBuilder.setName("issuer");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("“ип");
		fieldBuilder.setName("type");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription("–егистрационный номер");
		fieldBuilder.setName("registrationNumber");
		fieldBuilder.setType("string");
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
