package com.rssl.phizic.web.help;

import org.apache.struts.action.ActionForm;

/**
 * @author akrenev
 * @ created 11.02.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� ����������� �����
 */

public class CSAHelpForm extends ActionForm
{
	private String id;         //������������� �������� �����
	private String path;       //���� � �������� �����
	private String sharp;      //�������� �� ��������

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getPath()
	{
		return path;
	}

	public void setPath(String path)
	{
		this.path = path;
	}

	public String getSharp()
	{
		return sharp;
	}

	public void setSharp(String sharp)
	{
		this.sharp = sharp;
	}
}
