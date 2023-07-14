package com.rssl.phizic.web.common.client.finances;

import com.rssl.phizic.operations.finances.FinancesOperationBase;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import javax.servlet.http.HttpServletRequest;

/**
 * @author rydvanskiy
 * @ created 22.08.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class FinanceActionBase extends OperationalActionBase
{
	/**
	 * @param frm - �����
	 * @return �������� ��� ����������� Action ��������� �� FinancesOperationBase
	 */
	protected abstract FinancesOperationBase createFinancesOperation(FinanceFormBase frm) throws BusinessLogicException, BusinessException;
	
	/**
	 * ����� ��� �������� � ���������� ������ � ������ ������� ���� ����� ���������� ������
	 * @param operation ��������
	 * @param request ������� �������
	 */
	protected void checkFailedClaims(FinancesOperationBase operation, HttpServletRequest request)
	{
		if (operation.hasFailedClaims())
		{
			ActionMessages msgs = new ActionMessages();
		    msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("�� ��������� ����� ������ ���������� ����������.", false));
		    saveMessages(request, msgs);
		}
	}

}
