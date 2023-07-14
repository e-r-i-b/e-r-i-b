package com.rssl.phizic.web.common.exception;

import com.rssl.phizic.business.BusinessLogicException;
import org.apache.struts.action.ActionMessages;

/**
 * @author akrenev
 * @ created 19.03.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������ ��������� ����� ����-�����������
 */

public class FormProcessorException extends BusinessLogicException
{
	private final ActionMessages errors;

	/**
	 * �����������
	 * @param errors ������
	 */
	public FormProcessorException(ActionMessages errors)
	{
		super("������ ��������� ����� �����.");
		this.errors = errors;
	}

	/**
	 * @return ������ ���������
	 */
	public ActionMessages getErrors()
	{
		return errors;
	}
}
