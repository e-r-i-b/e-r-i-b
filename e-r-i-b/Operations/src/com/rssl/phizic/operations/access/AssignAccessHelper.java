package com.rssl.phizic.operations.access;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.schemes.SharedAccessScheme;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.operations.scheme.SchemeOperationHelper;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 17.01.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4221 $
 * ��������������� ���������� ��� ���������� ���� ������������\��������� �����
 * ������������ � AssignPersonAccessOperation
 */
public interface AssignAccessHelper extends SchemeOperationHelper
{
	/**
	 * @return ������ ���� ��������� ��� ����������
	 */
	List<SharedAccessScheme> getSchemes() throws BusinessException;

	/**
	 * @return ������
	 */
	AuthenticationConfig getAuthenticationConfig();
}
