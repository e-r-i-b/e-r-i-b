package com.rssl.phizic.common.types;

/**
 * @author gololobov
 * @ created 03.08.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��� ��������, � �������� ������� �������
 */

public enum SegmentCodeType
{
	/**
	 * �����������
	 */
	NOTEXISTS(0L),
	/**
	 * ������������ ��� (�������� �������������� �������)
	 */
	MVCPOTENTIAL(2L),
	/**
	 * ���
	 */
	VIP(3L),
	/**
	 * ��� (�������� �������������� �������)
	 */
	MVC(4L);

	private static final String NOT_EXISTS_CODE_TYPE = "�����������";
	private static final String MVC_POTENTIAL_CODE_TYPE = "������������ ���";
	private static final String VIP_CODE_TYPE = "���";
	private static final String MVC_CODE_TYPE = "���";

	private Long value;

	SegmentCodeType(Long value)
	{
		this.value = value;
	}

	/**
	 * @return - �������� ���� ������� ��� ����������� ������������
	 */
	public String getDescription()
	{
		if (value.equals(NOTEXISTS.value))
			return NOT_EXISTS_CODE_TYPE;
		if (value.equals(MVCPOTENTIAL.value))
			return MVC_POTENTIAL_CODE_TYPE;
		if (value.equals(VIP.value))
			return VIP_CODE_TYPE;
		if (value.equals(MVC.value))
			return MVC_CODE_TYPE;
		return null;
	}

	public static SegmentCodeType fromValue(Long value)
	{
		if (value == null || value.equals(NOTEXISTS.value))
			return NOTEXISTS;
		if (value.equals(MVCPOTENTIAL.value))
			return MVCPOTENTIAL;
		if (value.equals(VIP.value))
			return VIP;
		if (value.equals(MVC.value))
			return MVC;
		throw new IllegalArgumentException("����������� ��� ��������, � �������� ������� ������� [" + value + "]");
	}
}
