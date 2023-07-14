package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

/**
* @author Erkin
* @ created 02.08.2014
* @ $Author$
* @ $Revision$
*/
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class QueryErmbProfileOptions
{
	/**
	 * ������: ������ ������� �� ���+���+��+�� � ������
	 */
	public boolean findByActualIdentity;

	/**
	 * ������: ������ ������� �� ���+���+��+�� � ������� ����� ��� ���
	 */
	public boolean findByOldIdentity;
}
