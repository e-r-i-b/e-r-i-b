package com.rssl.phizic.web.component;

import com.rssl.phizic.business.web.Widget;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Erkin
 * @ created 08.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class WidgetForm extends ActionFormBase
{
	/**
	 * ����������� �������
	 * �������� �� ���-��������
	 */
	private String codename;

	/**
	 * ����������� ������-����������
	 * �������� �� ���-�������� ��� �������� ������ �������
	 * ���� ��� ����������� ������� � ������ ���������
	 */
	private String page;

	/**
	 * ����������� ���������
	 * �������� �� ���-�������� ��� �������� ������ �������
	 */
	private String defname;

	/**
	 * ��������� ������� � ������� json
	 * �������� �� ���-�������� ��� ���������� �������
	 */
	private String settings = "{}";
	
	private Widget widget;

	private String title;

	/**
	 * ������ ������� "��������"
	 */
	private boolean forbiddenMode = false;

	///////////////////////////////////////////////////////////////////////////

	public String getCodename()
	{
		return codename;
	}

	public void setCodename(String codename)
	{
		this.codename = codename;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

	public String getDefname()
	{
		return defname;
	}

	public void setDefname(String defname)
	{
		this.defname = defname;
	}

	public String getSettings()
	{
		return settings;
	}

	public void setSettings(String widget)
	{
		this.settings = widget;
	}

	public Widget getWidget()
	{
		return widget;
	}

	public void setWidget(Widget widget)
	{
		this.widget = widget;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public boolean getForbiddenMode()
	{
		return forbiddenMode;
	}

	public void setForbiddenMode(boolean forbiddenMode)
	{
		this.forbiddenMode = forbiddenMode;
	}
}
