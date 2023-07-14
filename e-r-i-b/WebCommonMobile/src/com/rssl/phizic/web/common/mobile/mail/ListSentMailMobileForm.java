package com.rssl.phizic.web.common.mobile.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.phizic.business.mail.MailType;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма списка отправленных писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListSentMailMobileForm extends ListMailMobileFormBase
{
	//in
	private String type;

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = createMobileFilter();

		//Тип письма
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("type");
		fieldBuilder.setDescription("Тип письма");
		List<String> mailTypeNames = new ArrayList<String>();
		for(MailType mailType : MailType.values())
			mailTypeNames.add(mailType.name());
		fieldBuilder.addValidators(new ChooseValueValidator(mailTypeNames));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
