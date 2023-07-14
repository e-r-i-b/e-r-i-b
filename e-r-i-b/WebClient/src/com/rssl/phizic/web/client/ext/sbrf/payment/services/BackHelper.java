package com.rssl.phizic.web.client.ext.sbrf.payment.services;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.WebContext;
import org.apache.struts.action.ActionForward;

/**
 * @author krenev
 * @ created 07.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class BackHelper
{
	/**
	 * ����� ��������� ��� ������������� � �������� ������������ ������� ��� ������������� �� ���� �����
	 * @param forward �������
	 * @return ��������� �������
	 */
	static ActionForward appendPaymentId(ActionForward forward)
	{
		String id = WebContext.getCurrentRequest().getParameter("id");
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		if (!StringHelper.isEmpty(id) && Long.decode(id) > 0)
		{
			//���� ��� �������� ����������� ������� - ������������ ��� ������
			urlBuilder.addParameter("id", id);
		}
		return new ActionForward(urlBuilder.getUrl(), true);
	}
}
