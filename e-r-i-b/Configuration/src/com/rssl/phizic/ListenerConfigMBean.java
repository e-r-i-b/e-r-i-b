package com.rssl.phizic;

/**
 * @author Omeliyanchuk
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 */

public interface ListenerConfigMBean
{
	/**
	 *
	 * @return  ����� � ��������� ���-������� �� ������� ����
	 */
	String getUrl();

	/**
	 * ����� � ��������� ���-������� �� ������� ����
	 * @param url
	 */
	void setUrl(String url);

	/**
	 * @return ����� ��� ��������� ��������� ��������� �� �� � ����
	 */
	String getRedirectServiceUrl();

	/**
	 * @param nodeId - ����� �����
	 * @return - ����� ���-�������� ��� �����
	 */
	public String getNodeWebServiceUrl(Long nodeId);
}
