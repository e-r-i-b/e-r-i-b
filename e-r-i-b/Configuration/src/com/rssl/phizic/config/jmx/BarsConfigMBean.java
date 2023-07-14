package com.rssl.phizic.config.jmx;

/**
 * @author osminin
 * @ created 07.04.2011
 * @ $Author$
 * @ $Revision$
 */
public interface BarsConfigMBean
{
	/**
	 * URL ���-������� ����
	 * @return URL ���-������� ����
	 */
	String getBarsUrl(String tb);

	/**
	 * ���������� ��� ��������� �������� ����
	 * @return ���������� ��� ��������� �������� ����
	 */
	String getBarsPartCode(String tb);
}
