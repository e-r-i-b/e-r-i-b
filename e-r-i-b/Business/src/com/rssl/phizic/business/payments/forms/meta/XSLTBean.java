package com.rssl.phizic.business.payments.forms.meta;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * XSLT-���.
 * ������������ ��� ������ ���������� XML �� ������.
 * ����� �������� XSD-����� ��� ��������� ����� XML.
 * ������������ �: ���
 */
public class XSLTBean implements Serializable
{
	private Long id;

	private String xsltTemplate;

	private String xsltName;

	private String xsd;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ID XSLT-����
	 */
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return XSLT-������ (never null)
	 */
	public String getXsltTemplate()
	{
		return xsltTemplate;
	}

	public void setXsltTemplate(String xsltTemplate)
	{
		this.xsltTemplate = xsltTemplate;
	}

	/**
	 * @return ���������� ��� XSLT-���� (never null)
	 */
	public String getXsltName()
	{
		return xsltName;
	}

	public void setXsltName(String xsltName)
	{
		this.xsltName = xsltName;
	}

	/**
	 * @return XSD-����� (never null)
	 */
	public String getXsd()
	{
		return xsd;
	}

	public void setXsd(String xsd)
	{
		this.xsd = xsd;
	}
}
