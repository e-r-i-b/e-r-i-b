package com.rssl.phizgate.common.payments.systems.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.MultiLineField;

/**
 * @author Gainanov
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class MultiLineFieldImpl extends FieldImpl implements MultiLineField
{
	private int linesNumber;

	public int getLinesNumber()
	{
		return linesNumber;
	}

	public void setLinesNumber(int linesNumber)
	{
		this.linesNumber = linesNumber;
	}
}
