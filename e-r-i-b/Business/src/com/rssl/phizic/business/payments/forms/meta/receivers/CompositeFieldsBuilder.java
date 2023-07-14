package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import com.rssl.phizic.business.BusinessException;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Krenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */
class CompositeFieldsBuilder implements FieldsBuilder
{
	private List<FieldsBuilder> builders = new ArrayList<FieldsBuilder>();

	public List<Field> buildFields() throws BusinessException
	{
		List<Field> fields = new ArrayList<Field>();
		for (FieldsBuilder builder : builders)
		{
			fields.addAll(builder.buildFields());
		}
		return fields;
	}

	public Element buildFieldsDictionary()
	{
		Element result = null;

		for (FieldsBuilder builder : builders)
		{
			Element element = builder.buildFieldsDictionary();
			if (element == null)
			{
				continue;
			}
			if (result == null)
			{
				result = element;
				continue;
			}
			result.getOwnerDocument().appendChild(element);
		}
		return result;
	}

	void addBuilder(FieldsBuilder builder)
	{
		builders.add(builder);
	}
}
