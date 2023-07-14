package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.common.forms.doc.DocumentSignature;

import java.io.Serializable;
import java.util.Map;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 02.01.2007
 * @ $Author: erkin $
 * @ $Revision: 48487 $
 */
public interface ConfirmStrategy extends Serializable, Cloneable
{
	/**
	 * @return ��� - ��� ���������
	 */
	ConfirmStrategyType getType();

	/**
	 * ��������� �� �������� ��� �������� �������
	 * @return true == ���������
	 */
	boolean hasSignature();

	/**
	 * ������� ������ �� �������������
	 * @param login ����� ��� �������� ��������� ������
	 * @param value �������� ��� �������
	 * @param sessionId ������������� ������� ������
	 * @param preConfirm ��� �������� ��� ���������� �� preConfirmActions
	 * @return ������, � � ������ ������ ���������� ErrorConfirmRequest
	 * @throws SecurityException ���������� ������� ������ (��� ����� ������, ����������� etc)
	 */
	ConfirmRequest createRequest(CommonLogin login, ConfirmableObject value, String sessionId,PreConfirmObject preConfirm) throws SecurityException, SecurityLogicException;;

	/**
	 * ��������� �����
	 * @param login ����� ��� �������� ����������� ��������
	 * @param request ������
	 * @param response �����
	 * @return true - ���� ������ ����� ���� ���������� �� ����, false - ������ �� ���
	 * @throws SecurityLogicException �������� ����� (������, ������� etc)
	 */
	ConfirmStrategyResult validate(CommonLogin login, ConfirmRequest request, ConfirmResponse response) throws SecurityLogicException, SecurityException;

	/**
	 * ��������, ������� ����� ��������� ����� ��������������
	 * @param callBackHandler CallBackHandler
	 * @throws SecurityLogicException, SecurityException �������� ����� (������, ������� etc)
	 */
	PreConfirmObject preConfirmActions(CallBackHandler callBackHandler) throws SecurityLogicException, SecurityException;

	/**
	 * �������� �������
	 * @param request
	 * @param confirmResponse
	 * @return
	 * @throws SecurityLogicException
	 * @throws SecurityException
	 */
	DocumentSignature createSignature(ConfirmRequest request, SignatureConfirmResponse confirmResponse) throws SecurityLogicException, SecurityException;

	/**
	 * �������� ����� ��� ���������
	 * @return
	 */
	ConfirmResponseReader getConfirmResponseReader();

	/**
	 * ����� ��������� ������������� ��� ����������� ������� �������������
	 * @param login - ����� ��������� �������
	 * @param confirmableObject - ������ �������������
	 */
	void reset(CommonLogin login, ConfirmableObject confirmableObject) throws SecurityDbException;

	/**
	 * �������� ��������� �� ������
	 * @return
	 */
	public Exception getWarning();

	/**
	 * ���������� ��������� �� ������
	 * @return
	 */
	public void setWarning(Exception e);

	/**
	 * ������� ����� �������
	 * @return
	 */
	public Object clone() throws CloneNotSupportedException;

	/**
	 * ���������� ��������� � ����������� �� ������� conditions
	 * @param conditions ������� ����������
	 * @param userChoice �������� ���� ��������� ������������� ���������� ������������� (���, ���)
	 * @param object - ������ �������������
	 * @return
	 */
	public boolean filter(Map<ConfirmStrategyType, List<StrategyCondition>> conditions, String userChoice, ConfirmableObject object);
}
