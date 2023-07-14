package com.rssl.phizic.web.webApi.protocol.jaxb.router;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;

import javax.xml.bind.JAXBException;

/**
 * @author Balovtsev
 * @since 24.04.14
 */
public interface JAXBRequestRouter
{
	/**
	 * ����� ������ ������������ ������ � ���������� �������������� ����� � ���� ������
	 *
	 * @param request ������
	 * @return ����� �� ������
	 */
	String process(Request request, String source) throws JAXBException;

	/**
	 * ������� �� ��� ���� �������/�������� � �������� ������ ��������������
	 */
	void clearRouteContext();
}
