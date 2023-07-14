package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Список категорий для запроса структуры расходов по категориям
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "nationalSum", "budget"})
@XmlRootElement(name = "categories")
public class CategoryFilterTag
{
	private Long id;
	private String name;
	private MoneyTag nationalSum;
	private BudgetTag budget;

	@XmlElement(name = "id", required = true)
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "name", required = true)
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@XmlElement(name = "nationalSum", required = true)
	public MoneyTag getNationalSum()
	{
		return nationalSum;
	}

	public void setNationalSum(MoneyTag nationalSum)
	{
		this.nationalSum = nationalSum;
	}

	@XmlElement(name = "budget", required = false)
	public BudgetTag getBudget()
	{
		return budget;
	}

	public void setBudget(BudgetTag budget)
	{
		this.budget = budget;
	}
}
