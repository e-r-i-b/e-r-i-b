package com.rssl.phizic;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.person.ClientIdentity;

/**
 * ������ ���������� � ��������� ������ ����
 * @author Puzikov
 * @ created 10.09.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("PublicField")
public class ErmbInfoRequest extends Request
{
	public static final String REQUEST_NAME = "ErmbInfoRequest";

	@MandatoryParameter
	public ClientIdentity clientIdentity;

	@Override
	public String getName()
	{
		return REQUEST_NAME;
	}

	@Override
	public String toString()
	{
		return "������ ���������� � ��������� ���� (���-���-��-�� = " + clientIdentity + ")";
	}
}
