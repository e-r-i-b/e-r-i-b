package com.rssl.phizic.config.loanclaim;

/**
 * @author Erkin
 * @ created 25.02.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� ��������� ������ � Transact SM
 */
public class ETSMLoanClaimStatus
{
	/**
	 * ������
	 */
	public static final int ERROR = -2;
	/**
	 * ������ ��������� � ��������
	 */
	public static final int INVALID = -1;

	/**
	 * �� ������ ������� �����
	 */
	public static final int REFUSED = 0;

	/**
	 * ������ ������� � ���������
	 */
	public static final int REGISTERED = 1;

	/**
	 * ������ ��������
	 */
	public static final int APPROVED = 2;

	/**
	 * ������ ������� � ���������
	 */
	public static final int ADOPT = 3;

	/**
	 * ������ �����
	 */
	public static final int ISSUED = 4;

	/**
	 * ������� ��������������� �������
	 */
	public static final int PREADOPTED = 5;

	/**
	 * �������� ������ �������
	 */
	public static final int WAIT_ISSUE = 6;

	/**
	 * ��������� ����� � ���
	 */
	public static final int NEED_VISIT_VSP = 7;
}
