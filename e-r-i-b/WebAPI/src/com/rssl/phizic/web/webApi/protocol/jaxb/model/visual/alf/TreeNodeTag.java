package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.web.webApi.protocol.jaxb.model.adapters.CDATAXmlAdapter;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/** Узел иерархии фильтра карт (для АЛФ)
 * @author Jatsky
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlType(propOrder = {"value", "label", "nodeList"})
@XmlRootElement(name = "cardFilter")
public class TreeNodeTag
{
	private String value;
	private String label;
	private List<TreeNodeTag> nodeList;

	@XmlElement(name = "value", required = true)
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	@XmlJavaTypeAdapter(CDATAXmlAdapter.class)
	@XmlElement(name = "label", required = true)
	public String getLabel()
	{
		return label;
	}

	public void setLabel(String label)
	{
		this.label = label;
	}

	@XmlElementWrapper(name = "nodeList", required = false)
	@XmlElement(name = "node", required = true)
	public List<TreeNodeTag> getNodeList()
	{
		return nodeList;
	}

	public void setNodeList(List<TreeNodeTag> nodeList)
	{
		this.nodeList = nodeList;
	}
}
