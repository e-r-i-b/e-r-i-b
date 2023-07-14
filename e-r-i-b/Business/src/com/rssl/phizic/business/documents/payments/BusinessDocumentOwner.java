package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.annotation.Immutable;

/**
 * @author Rtischeva
 * @ created 18.02.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ���������
 * ���������� ����� ���������� ����� equals:
 * + ��� ���������-����� ������������, ���� ����� �� ���-����.
 * + ��� ���������-������� ������������, ���� ����� �� �� �������.
 * + ��������-������ ������� �� ������������ ���������-�����.
 */
@Immutable
public interface BusinessDocumentOwner
{
	/**
	 * @return ������ ����� (GuestLogin) ��� ������� (Login) (never null)
	 */
	Login getLogin();

	/**
	 * @return ������ ������� (never null)
	 */
	ActivePerson getPerson() throws BusinessException;

	/**
	 * @return true, ���� �������� - �����
	 */
	boolean isGuest();

	/**
	 * @return ���� ��� ��������� ����������
	 */
	String getSynchKey();
}
