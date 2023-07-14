package com.rssl.phizic.auth;

/**
 * @author Egorova
 * @ created 25.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class BlockingReasonDescriptor
{
//TODO: ����������� ��������������� �������� ������ �� properties
	public static String getReasonText(BlockingReasonType reasonType)
	{
		switch (reasonType)
		{
			case longInactivity:
			case employee:      return "������������� ���������������";
			case system:        return "��������� ����������";
			case wrongLogons:   return "������������ ���� ������";

			default: return "������� ���������� ����������";
		}

	}
}
