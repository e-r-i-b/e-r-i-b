package com.rssl.ikfl.crediting;

/**
 * ����� �������� ������
 *
 * @ author: Gololobov
 * @ created: 05.03.15
 * @ $Author$
 * @ $Revision$
 */
public enum CRMClaimChannel
{
	WEB("1"),
	/**
	 * �� ����������
	 */
	TM_INNER("2"),
	/**
	 * �� �������
	 */
	TM_OUTER("3"),
	/**
	 * ���
 	 */
	VSP("4"),
	/**
	 * ����-����
	 */
	ERIB_SBOL("5"),
	/**
	 * ����-��
	 */
	ERIB_MP("6"),
	/**
	 * ����-��
	 */
	ERIB_US("7"),
	/**
	 * ����-��
	 */
	ERIB_MB("8"),
	/**
	 * ����-��������
	 */
	ERIB_GUEST("9");

	/**
	 * ��� ������ � CRM
	 */
	public final String code;

	private CRMClaimChannel(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
