package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.ListField;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;

import java.util.List;

/**
 * @author Gainanov
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ListFieldImpl extends FieldImpl implements ListField
{
	private List<ListValue> values;

	public List<ListValue> getValues()
	{
		return values;
	}

	public void setValues(List<ListValue> values)
	{
		this.values = values;
	}
}