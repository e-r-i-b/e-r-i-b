package com.rssl.phizic.business.payments.forms.meta;

import java.io.Serializable;

/**
 * @author Erkin
 * @ created 24.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * XSLT-бин.
 * Используется для показа некоторого XML на экране.
 * Также содержит XSD-схему для валидации этого XML.
 * Используется в: ПФР
 */
public class XSLTBean implements Serializable
{
	private Long id;

	private String xsltTemplate;

	private String xsltName;

	private String xsd;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return ID XSLT-бина
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
	 * @return XSLT-шаблон (never null)
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
	 * @return уникальное имя XSLT-бина (never null)
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
	 * @return XSD-схема (never null)
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
