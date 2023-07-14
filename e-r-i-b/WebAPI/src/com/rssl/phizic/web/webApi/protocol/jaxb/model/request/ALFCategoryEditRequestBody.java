package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.validators.RegexpFieldValidator;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.OperationType;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Тело запроса на работу с расходной категорией АЛФ
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlRootElement(name = "body")
public class ALFCategoryEditRequestBody extends SimpleRequestBody
{
	private Long id;
	private String name;
	private OperationType operationType;

	public static final Form NEW_CATEGORY_FORM = createForm();

	@XmlElement(name = "id", required = false)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlElement(name = "name", required = false)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElement(name = "operationType", required = true)
	public OperationType getOperationType()
	{
		return operationType;
	}

	public void setOperationType(OperationType operationType)
	{
		this.operationType = operationType;
	}

	public static Form createForm()
	{
		RequiredFieldValidator requiredValidator = new RequiredFieldValidator();
		FormBuilder formBuilder = new FormBuilder();

		FieldBuilder fieldBuilder;
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("name");
		fieldBuilder.setDescription("Название категории");
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(requiredValidator, new RegexpFieldValidator(".{0,100}", "Название должно быть не более 100 символов."));
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}
}
