package com.rssl.auth.csa.front.operations.auth;

import org.w3c.dom.Document;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;

/**
 * �������� �� ������������� �������������� ������
 * @author niculichev
 * @ created 05.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class RecoverPasswordConfirmOperation extends ConfirmOperationBase
{
	protected Document doConfirm() throws BackLogicException, BackException
	{
		RecoverPasswordOperationInfo operationInfo = (RecoverPasswordOperationInfo) info;
		// ���� ��� ���������� � ����������, ������ �������� �����
		if(operationInfo.getConnectorType() == null)
		{
			return CSABackRequestHelper.sendGuestConfirmOperationRq(info.getOUID(), confirmationCode);
		}
		else
		{
			return CSABackRequestHelper.sendConfirmOperationRq(info.getOUID(), confirmationCode);
		}
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		// �������� ������ �� �������������
		super.doRequest();

		// �.� ����� Ipas, �� ����� ������ � SMS ���������, ������� ����� ��������� �������� ��������������
		RecoverPasswordOperationInfo operationInfo = (RecoverPasswordOperationInfo) info;
		if(ConnectorInfo.Type.TERMINAL == operationInfo.getConnectorType())
			CSABackRequestHelper.sendFinishRecoverPasswordRq(info.getOUID(), null);

		// ��������� ������ �� ���������
		return null;
	}
}
