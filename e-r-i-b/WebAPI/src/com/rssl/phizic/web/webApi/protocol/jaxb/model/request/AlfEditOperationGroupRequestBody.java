package com.rssl.phizic.web.webApi.protocol.jaxb.model.request;

import com.rssl.common.forms.Field;
import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.alf.RecategorizationOperation;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/** Тело запроса на редактирование группы операций
 * @author Jatsky
 * @ created 13.08.14
 * @ $Author$
 * @ $Revision$
 */

public class AlfEditOperationGroupRequestBody extends SimpleRequestBody
{
	private Long oldOperationCategoryId;
	private Long generalCategoryId;
	private List<RecategorizationOperation> operations;

	public static final Form EDIT_FORM = createForm();

	@XmlElement(name = "oldOperationCategoryId", required = true)
	public Long getOldOperationCategoryId()
	{
		return oldOperationCategoryId;
	}

	public void setOldOperationCategoryId(Long oldOperationCategoryId)
	{
		this.oldOperationCategoryId = oldOperationCategoryId;
	}

	@XmlElement(name = "generalCategoryId", required = false)
	public Long getGeneralCategoryId()
	{
		return generalCategoryId;
	}

	public void setGeneralCategoryId(Long generalCategoryId)
	{
		this.generalCategoryId = generalCategoryId;
	}

	@XmlElementWrapper(name = "operations", required = false)
	@XmlElement(name = "operation", required = true)
	public List<RecategorizationOperation> getOperations()
	{
		return operations;
	}

	public void setOperations(List<RecategorizationOperation> operations)
	{
		this.operations = operations;
	}

	private static Form createForm()
	{
		List<Field> fields = new ArrayList<Field>();
		FieldBuilder fieldBuilder;

		// Период
		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("oldOperationCategoryId");
		fieldBuilder.setDescription("Идентификатор удаляемой категории");
		fieldBuilder.clearValidators();
		fieldBuilder.addValidators(new RequiredFieldValidator());
		fields.add(fieldBuilder.build());

		FormBuilder formBuilder = new FormBuilder();
		formBuilder.setFields(fields);

		return formBuilder.build();
	}
}
