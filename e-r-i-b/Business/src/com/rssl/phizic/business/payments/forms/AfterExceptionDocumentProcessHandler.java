package com.rssl.phizic.business.payments.forms;

import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;

/**
 * �������, ���������� ����� ����������.
 *
 * @author bogdanov
 * @ created 02.07.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class AfterExceptionDocumentProcessHandler
{
	/**
	 * @param document �������������� ��������.
	 * @return ����� ���������.
	 */
	public abstract String process(StateObject document) throws DocumentException, DocumentLogicException;

	/**
	 * @return ��������� �� ������.
	 */
	public abstract String getErrorMessage();
}
