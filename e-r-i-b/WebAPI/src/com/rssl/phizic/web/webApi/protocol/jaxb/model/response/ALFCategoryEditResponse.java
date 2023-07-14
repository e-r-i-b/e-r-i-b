package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryToLinkOperationsTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос на работу с расходной категорией АЛФ
 * @author Jatsky
 * @ created 15.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "id", "categoriesToLinkOperations"})
@XmlRootElement(name = "message")
public class ALFCategoryEditResponse extends Response
{
	private Long id;
	private List<CategoryToLinkOperationsTag> categoriesToLinkOperations;

	@XmlElement(name = "id", required = false)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlElementWrapper(name = "categoriesToLinkOperations", required = false)
	@XmlElement(name = "categoryToLinkOperations", required = true)
	public List<CategoryToLinkOperationsTag> getCategoriesToLinkOperations()
	{
		return categoriesToLinkOperations;
	}

	public void setCategoriesToLinkOperations(List<CategoryToLinkOperationsTag> categoriesToLinkOperations)
	{
		this.categoriesToLinkOperations = categoriesToLinkOperations;
	}
}
