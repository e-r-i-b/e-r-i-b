package com.rssl.phizic.web.auth;

import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.auth.csa.front.operations.Operation;
import com.rssl.common.forms.Form;

import java.util.Map;

/**
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Stage
{
	/**
	 * @return ��� ������
	 */
	String getName();

	/**
	 * @param info ������� ��������
	 * @return ��������� ������
	 */
    Stage next(OperationInfo info);

	/**
	 * @param info �������� ��������
	 * @param params ��������� ��� ������������� ��������
	 * @return ��������������������� ��������
	 */
    Operation getOperation(OperationInfo info, Map<String, Object> params) throws FrontLogicException;

	/**
	 * @param info �������� ��������
	 * @return ����������� ����� ������� ������
	 */
	Form getForm(OperationInfo info);
}
