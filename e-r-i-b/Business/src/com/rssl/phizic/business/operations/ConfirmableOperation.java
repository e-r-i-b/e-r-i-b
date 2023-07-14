package com.rssl.phizic.business.operations;

import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.ConfirmableObject;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: khudyakov $
 * @ $Revision: 83241 $
 */

public interface ConfirmableOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * @return ������ ��� �������������.
	 */

	ConfirmableObject getConfirmableObject();

	/**
	 * ������� ������ �� ������������� ��������
	 * @param sessionId ������������� ������� ������
	 * @return ����� ������
	 */
	ConfirmRequest createConfirmRequest(String sessionId) throws BusinessException, BusinessLogicException;

	/**
	 * @param request ���������� ����� ��������� ������ �� �������������
	 */
	void setConfirmRequest(ConfirmRequest request);

	/**
	 * @return ������� �� ������������� ��������
	 */
	ConfirmRequest getRequest();

	/**
	 * @param confirmResponse ������������� ��������
	 */
	void setConfirmResponse(ConfirmResponse confirmResponse);

	/**
	 * @return ��������� �������������
	 */
	ConfirmStrategy getConfirmStrategy();

	/**
	 * @return ��� ��������� ��������������
	 */
	ConfirmStrategyType getStrategyType();

	/**
	 * �������� ��������� �������������
	 * @throws BusinessException
	 */
	void resetConfirmStrategy() throws BusinessException;

	/**
	 * @return ����� ���������
	 */
	ConfirmResponseReader getConfirmResponseReader();

	/**
	 * @param callBackHandler �������
	 * @return PreConfirmObject
	 */
	PreConfirmObject preConfirm(CallBackHandler callBackHandler) throws SecurityLogicException, BusinessException, BusinessLogicException;

	/**
	 * ���������� ������������.
	 * @throws SecurityLogicException
	 * @throws BusinessException
	 */
	public void confirm() throws SecurityLogicException, BusinessException, BusinessLogicException;
}
