package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Категория для перепривязки операций
 * @author Jatsky
 * @ created 01.08.14
 * @ $Author$
 * @ $Revision$
 */
@XmlRootElement(name = "categoryToLinkOperations")
public class CategoryToLinkOperationsTag
{
	private Long id;

	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
