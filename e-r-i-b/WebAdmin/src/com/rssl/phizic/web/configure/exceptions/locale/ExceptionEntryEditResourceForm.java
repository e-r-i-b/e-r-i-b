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
	 * @return количество сообщений
	 */
	public Long getMessagesCount()
	{
		return messagesCount;
	}

	/**
	 * @param messagesCount количество сообщений
	 */
	public void setMessagesCount(Long messagesCount)
	{
		this.messagesCount = messagesCount;
	}

	/**
	 * @return форма
	 */
	public Form getEditForm()
	{
		FormBuilder formBuilder = new FormBuilder();

		for(int i = 0; i < getMessagesCount(); i++)
		{
			formBuilder.addField(
					FieldBuilder.buildStringField("message_" + i, "Сообщение", new RequiredFieldValidator("Введите текс сообщения, отображаемого при возникновении ошибки."))
			);
		}



		return formBuilder.build();
	}
}
