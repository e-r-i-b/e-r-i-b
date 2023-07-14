package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.ColorXmlAdapter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Категория АЛФ
 * @author Jatsky
 * @ created 14.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"id", "name", "incomeType", "canEdit", "hasLinkedOperations", "color"})
@XmlRootElement(name = "alfServiceStatus")
public class CategoryTag
{
	private Long id;
	private String name;
	private IncomeType incomeType;
	private boolean canEdit;
	private boolean hasLinkedOperations;
	private String color;

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

	@XmlElement(name = "incomeType", required = true)
	public IncomeType getIncomeType()
	{
		return incomeType;
	}

	public void setIncomeType(IncomeType incomeType)
	{
		this.incomeType = incomeType;
	}

	@XmlElement(name = "canEdit", required = true)
	public boolean isCanEdit()
	{
		return canEdit;
	}

	public void setCanEdit(boolean canEdit)
	{
		this.canEdit = canEdit;
	}

	@XmlElement(name = "hasLinkedOperations", required = true)
	public boolean isHasLinkedOperations()
	{
		return hasLinkedOperations;
	}

	public void setHasLinkedOperations(boolean hasLinkedOperations)
	{
		this.hasLinkedOperations = hasLinkedOperations;
	}

	@XmlJavaTypeAdapter(ColorXmlAdapter.class)
	@XmlElement(name = "color", required = false)
	public String getColor()
	{
		return color;
	}

	public void setColor(String color)
	{
		this.color = color;
	}
}
