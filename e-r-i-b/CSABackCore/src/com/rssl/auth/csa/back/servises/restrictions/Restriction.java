package com.rssl.auth.csa.back.servises.restrictions;

import com.rssl.auth.csa.back.exceptions.RestrictionException;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public interface Restriction<T>
{
	/**
	 * ��������� ����������� �� ���-����
	 * @param object ����� ��� ��������
	 * @throws RestrictionException � ������ ��������� �����������.
	 */
	void check(T object) throws Exception;
}
