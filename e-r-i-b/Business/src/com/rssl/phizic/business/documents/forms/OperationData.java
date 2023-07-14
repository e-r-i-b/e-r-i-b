package com.rssl.phizic.business.documents.forms;

/**
 * @author khudyakov
 * @ created 13.02.14
 * @ $Author$
 * @ $Revision$
 */
public class OperationData
{
	private boolean printCheck;

	public OperationData(boolean printCheck)
	{
		this.printCheck = printCheck;
	}

	public boolean isPrintCheck()
	{
		return printCheck;
	}
}
