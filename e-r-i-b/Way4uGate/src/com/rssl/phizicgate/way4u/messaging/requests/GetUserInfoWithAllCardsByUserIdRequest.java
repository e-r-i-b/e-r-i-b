package com.rssl.phizicgate.way4u.messaging.requests;

import com.rssl.phizic.utils.xml.XMLWriter;

/**
 * @author sergunin
 * @ created 04.06.2014
 * @ $Author$
 * @ $Revision$
 * ������ ���������� �� ���������� � �������, ����������������� �� ������
 */

public class GetUserInfoWithAllCardsByUserIdRequest extends GetUserInfoByUserIdRequest
{
	/**
	 * ����������� �������
	 * @param userId ����� �������
	 */
	public GetUserInfoWithAllCardsByUserIdRequest(String userId)
	{
		super(userId);
	}

	protected void buildFilter(XMLWriter writer){}
}
