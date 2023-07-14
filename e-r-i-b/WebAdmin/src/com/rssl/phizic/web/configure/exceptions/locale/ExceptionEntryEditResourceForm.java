package com.rssl.phizic.web.configure.exceptions.locale;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.locale.EditLanguageResourcesBaseForm;

/**
 * @author komarov
 * @ created 14.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ExceptionEntryEditResourceForm extends EditLanguageResourcesBaseForm
{
	private Long messagesCount;

	/**
	 * @return ���������� ���������
	 */
	public Long getMessagesCount()
	{
		return messagesCount;
	}

	/**
	 * @param messagesCount ���������� ���������
	 */
	public void setMessagesCount(Long messagesCount)
	{
		this.messagesCount = messagesCount;
	}

	/**
	 * @return �����
	 */
	public Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		for(int i = 0; i < getMessagesCount(); i++)
		{
			formBuilder.addField(
					FieldBuilder.buildStringField("message_" + i, "���������", new RequiredFieldValidator("������� ���� ���������, ������������� ��� ������������� ������."))
			);
		}



		return formBuilder.build();
	}
}
