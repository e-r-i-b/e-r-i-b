package com.rssl.phizic.web.configure.basketinfo;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.BasketIdentifierTypeValidator;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.pages.staticmessages.StaticMessage;
import com.rssl.phizic.business.userDocuments.DocumentType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.web.common.FilterActionForm;

import java.util.List;

/**
 * ����� �������������� � ��� ������������� ������� ������� �� ������������� ������� ��������
 *
 * @author muhin
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EditClientBasketInfoForm extends FilterActionForm
{
	public static final String MESSAGE = "message";

	private String message = "�� ����� ������������� ��������� ������� ������������ �� ������� �� � ���������� ����� ��� ������ � ������� �������� � �������.";

	private static final String messageTittle = "�������������� ���������, ��������� ������� ����� ������������ ��, � ������ ������������ �������� ����������:";


	public String getMessage(){
		return message;
	}

	public String getMessageTittle(){
		return messageTittle;
	}

	public void setMessage(String message){
		this.message = message;
	}

	private List<String> documentTypes;

	public List<String> getDocumentTypes()
	{
		return documentTypes;
	}

	public void setDocumentTypes(List<String> documentTypes)
	{
		this.documentTypes = documentTypes;
	}

	public Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fb = new FieldBuilder();
		fb.setName(MESSAGE);
		fb.setType(StringType.INSTANCE.getName());

		formBuilder.addField(fb.build());


		return formBuilder.build();
	}
}
