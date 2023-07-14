package com.rssl.phizic.web.util;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.apache.struts.action.ActionForward;

import java.util.Collections;
import java.util.Map;

/**
 * @author Erkin
 * @ created 26.01.2011
 * @ $Author$
 * @ $Revision$
 */
public class ActionForwardHelper
{
	/**
	 * ��������� �������� ���������
	 * @param forward - ������� ���� ��������
	 * @param parameter - ��� ���������
	 * @param value - �������� ���������
	 * @return ������� � ����� ����������
	 */
	public static ActionForward appendParam(ActionForward forward, String parameter, String value)
	{
		if (forward == null)
			throw new NullPointerException("�������� 'forward' �� ����� ���� null");
		if (StringHelper.isEmpty(parameter))
			throw new IllegalArgumentException("�������� 'parameter' �� ����� ���� ������");
		if (value == null)
			throw new NullPointerException("�������� 'value' �� ����� ���� null");

		return appendParams(forward, Collections.singletonMap(parameter, value));
	}

	/**
	 * ��������� ��������� ���������
	 * @param forward - ������� ���� ��������
	 * @param parameters - ��� ���������� "��� -> ��������"
	 * @return ������� � ������ �����������
	 */
	public static ActionForward appendParams(ActionForward forward, Map<String, String> parameters)
	{
		if (forward == null)
			throw new NullPointerException("�������� 'forward' �� ����� ���� null");
		if (!forward.getRedirect())
			return forward;
		if (parameters == null)
			throw new NullPointerException("�������� 'parameters' �� ����� ���� null");
		if (parameters.isEmpty())
			return forward;

		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder.setUrl(forward.getPath());
		urlBuilder.addParameters(parameters);
		return new ActionForward(urlBuilder.getUrl(), true);
	}
}
