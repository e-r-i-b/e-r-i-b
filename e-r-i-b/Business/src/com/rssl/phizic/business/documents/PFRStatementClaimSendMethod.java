package com.rssl.phizic.business.documents;

/**
 * @author Erkin
 * @ created 14.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ �������� ������ �� ������� �� ���
 */
public enum PFRStatementClaimSendMethod
{
	/**
	 * ���������� ������ ��������� ���������� ������ way
	 */
	USING_PASPORT_WAY,

	/**
	 * ���������� ������ ��������� ����� (��������� ����� ��������������� �������� �����)
	 */
	USING_SNILS;

	public static final PFRStatementClaimSendMethod DEFAULT = USING_PASPORT_WAY;
}
