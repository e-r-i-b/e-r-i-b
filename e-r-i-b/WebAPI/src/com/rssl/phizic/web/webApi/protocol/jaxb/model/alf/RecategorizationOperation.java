package com.rssl.phizic.web.webApi.protocol.jaxb.model.alf;

import javax.xml.bind.annotation.XmlElement;

/** Перепривязываемая операция
 * @author Jatsky
 * @ created 13.08.14
 * @ $Author$
 * @ $Revision$
 */

public class RecategorizationOperation
{
	private Long id;
	private Long newOperationCategoryId;

	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlElement(name = "newOperationCategoryId", required = true)
	public Long getNewOperationCategoryId()
	{
		return newOperationCategoryId;
	}

	public void setNewOperationCategoryId(Long newOperationCategoryId)
	{
		this.newOperationCategoryId = newOperationCategoryId;
	}
}
