package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.LongType;
import com.rssl.common.forms.types.MoneyType;
import com.rssl.common.forms.validators.MoneyFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Для случаев, когда в body только Id
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class IdRequestBody extends SimpleRequestBody
{
	private String id;
	public static final Form ID_FORM = createForm();
	public static final String ID = "id";

	@XmlElement(name = "id", required = true)
	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	private static Form createForm()
	{
		FormBuilder fb = new FormBuilder();

		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setDescription(ID);
		fieldBuilder.setName(ID);
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fb.addField(fieldBuilder.build());

		return fb.build();
	}
}
