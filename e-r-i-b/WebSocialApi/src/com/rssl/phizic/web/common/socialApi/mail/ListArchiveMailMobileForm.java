package com.rssl.phizic.web.common.socialApi.mail;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.ChooseValueValidator;
import com.rssl.phizic.business.mail.MailDirection;

import java.util.ArrayList;
import java.util.List;

/**
 * Форма списка удаленных писем
 * @author Dorzhinov
 * @ created 08.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListArchiveMailMobileForm extends ListMailMobileFormBase
{
	//in
	private String direction;
	private Long id; //id для восстановления

	public static final Form FILTER_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = createMobileFilter();

		//Направление письма
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("mailType");
		fieldBuilder.setDescription("Тип письма");
		List<String> mailDirectionNames = new ArrayList<String>();
		for(MailDirection mailDirection : MailDirection.values())
			mailDirectionNames.add(mailDirection.name());
		fieldBuilder.addValidators(new ChooseValueValidator(mailDirectionNames));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	public String getDirection()
	{
		return direction;
	}

	public void setDirection(String direction)
	{
		this.direction = direction;
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
