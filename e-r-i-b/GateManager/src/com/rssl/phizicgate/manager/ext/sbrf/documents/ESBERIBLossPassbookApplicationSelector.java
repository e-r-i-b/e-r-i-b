package com.rssl.phizicgate.manager.ext.sbrf.documents;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;

/**
 * � ����������� �� �����, � ������� ���������� �������� � ����������� ��� ���� � ��� ���������� ������ ������ �� ����� ����������, Sender �������� ���� ��������
 * �� ���� (esb-erib-sender) ��� �� ������� ����(default-sender)
 * todo ������ ����� ���� ��� �� ���� �� �� ��� ����� ���������� ��������� ������ ������ �� ����� ���������� (CHG039252)
 * @author gololobov
 * @ created 17.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ESBERIBLossPassbookApplicationSelector extends ESBERIBPaymetSenderSelector
{
	public ESBERIBLossPassbookApplicationSelector(GateFactory factory)
	{
		super(factory);
	}

	protected boolean esbSupported(GateDocument document) throws GateException
	{
		return super.esbSupported(document) && ESBHelper.isSACSSupported(document.getInternalOwnerId());
	}
}
