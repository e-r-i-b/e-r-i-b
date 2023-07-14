package com.rssl.phizic.web.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

/**
 * ����� ��� �������� ����, ������������ � ������ LinksList ������� � ������������ html-����� ������
 * @author tisov
 * @ created 15.11.13
 * @ $Author$
 * @ $Revision$
 */

public class LinksListCommonItemTag extends BodyTagSupport
{
	private String body = null;

	/**
	 * ������ ���� ����
	 * @return - ���� ����
	 */
	public String getBody()
	{
		return this.body;
	}

	/**
	 * ������ ���� ����
	 * @param newBody - ����� ���� ����
	 */
	public void setBody(String newBody)
	{
		this.body = newBody;
	}

	public int doEndTag () throws JspException
	{
		StringBuffer buff = new StringBuffer();
		buff.append(this.body);
		LinksListTag linksList = (LinksListTag)findAncestorWithClass (this, LinksListTag.class);
		linksList.addItem(buff);
		return super.doEndTag();
	}

	public void release() {
		super.release();
		this.body = null;
	}

}
