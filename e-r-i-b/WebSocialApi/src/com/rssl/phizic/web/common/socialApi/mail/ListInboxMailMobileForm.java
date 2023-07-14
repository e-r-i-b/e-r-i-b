package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.phizic.business.mail.RecipientMailState;

import java.util.Arrays;

/**
 * Форма списка входящих писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListInboxMailMobileForm extends ListMailMobileFormBase
{
	//in
	private String recipientState;
	private Long id; //id для удаления

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = createMobileFilter();

		//Статус
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("state");
		fieldBuilder.setDescription("Статус");
		fieldBuilder.addValidators(
				new ChooseValueValidator(Arrays.asList(
						RecipientMailState.READ.name(),
						RecipientMailState.NEW.name(),
						RecipientMailState.ANSWER.name(),
						RecipientMailState.DRAFT.name()
				)));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getRecipientState()
	{
		return recipientState;
	}

	public void setRecipientState(String recipientState)
	{
		this.recipientState = recipientState;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
