package com.rssl.phizic.web.util.browser;

/**
 * @author lepihina
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */
public enum Browser
{
	MSIE("8", "9"), //Internet Explorer
	FIREFOX("15"), //Firefox
	OPERA("11"), //Opera
	CHROME(""), //Chrome
	SAFARI("5.1.6", "5"), //Safari
	UNKNOWN_BROWSER(""); // ����������� ��� ��������

	private final String widgetVersion;
	private final String cssVersion;

	Browser(String widgetVersion)
	{
		this.widgetVersion = widgetVersion;
		this.cssVersion = widgetVersion;
	}

	Browser(String widgetVersion, String cssVersion)
	{
		this.widgetVersion = widgetVersion;
		this.cssVersion = cssVersion;
	}

	/**
	 * @return ������ ��������, �������������� �������
	 */
	public String getWidgetVersion()
	{
		return widgetVersion;
	}

	/**
	 * @return ������ ��������, �������������� CSS3
	 */
	public String getCssVersion()
	{
		return cssVersion;
	}
}
