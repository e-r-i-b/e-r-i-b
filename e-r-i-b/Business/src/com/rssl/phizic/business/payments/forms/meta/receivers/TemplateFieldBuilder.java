package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.payments.forms.TemplateFieldBuilderHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author khudyakov
 * @ created 03.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class TemplateFieldBuilder implements FieldsBuilder
{
	private Metadata metadata;
	private Map<String, String> formData = new HashMap<String, String>();


	public TemplateFieldBuilder(Metadata metadata, TemplateDocument template) throws BusinessException
	{
		this.metadata = metadata;
		this.formData.putAll(template.getFormData());
	}

	public List<Field> buildFields() throws BusinessException
	{
		List<Field> fields = new ArrayList<Field>();
		for (Field field : metadata.getForm().getFields())
		{
			fields.add(TemplateFieldBuilderHelper.adaptField(field, formData.get(field.getName())));
		}
		return fields;
	}

	public Element buildFieldsDictionary()
	{
		return null;
	}
}
