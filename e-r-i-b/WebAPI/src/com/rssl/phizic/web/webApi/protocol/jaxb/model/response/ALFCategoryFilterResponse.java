package com.rssl.phizic.web.webApi.protocol.jaxb.model.response;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.CategoryFilterTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf.TreeNodeTag;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/** ќтвет на запрос структуры расходов по категори€м
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"status", "alfServiceStatus", "cardFilter", "categories"})
@XmlRootElement(name = "message")
public class ALFCategoryFilterResponse extends ALFStatusBaseResponse
{
	private TreeNodeTag cardFilter;
	private List<CategoryFilterTag> categories;

	@XmlElement(name = "cardFilter", required = false)
	public TreeNodeTag getCardFilter()
	{
		return cardFilter;
	}

	public void setCardFilter(TreeNodeTag cardFilter)
	{
		this.cardFilter = cardFilter;
	}

	@XmlElementWrapper(name = "categories", required = false)
	@XmlElement(name = "category", required = true)
	public List<CategoryFilterTag> getCategories()
	{
		return categories;
	}

	public void setCategories(List<CategoryFilterTag> categories)
	{
		this.categories = categories;
	}
}
