package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Ответ на запрос получения справочника категорий АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "alfServiceStatus", "categories"})
@XmlRootElement(name = "message")
public class ALFCategoryListResponse extends ALFStatusBaseResponse
{
	List<CategoryTag> categories;

	@XmlElementWrapper(name = "categories", required = false)
	@XmlElement(name = "category", required = true)
	public List<CategoryTag> getCategories()
	{
		return categories;
	}

	public void setCategories(List<CategoryTag> categories)
	{
		this.categories = categories;
	}
}
