package com.rssl.phizic.business.payments.forms.meta.receivers;

import com.rssl.common.forms.Field;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author Krenev
 * @ created 25.12.2009
 * @ $Author$
 * @ $Revision$
 */
class EmptyFieldsBuilder implements FieldsBuilder
{
	private List<Field> fields;

	EmptyFieldsBuilder()
	{
	}

	EmptyFieldsBuilder(List<Field> fields)
	{

		this.fields = fields;
	}

	public List<Field> buildFields()
	{
		return fields;
	}

	public Element buildFieldsDictionary()
	{
		return null;
	}
}
