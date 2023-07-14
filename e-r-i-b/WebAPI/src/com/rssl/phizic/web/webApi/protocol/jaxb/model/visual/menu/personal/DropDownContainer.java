package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.menu.personal;

import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * ���� ��������� ���������� �������
 *
 * @author Balovtsev
 * @since 30.04.2014
 */
@XmlType(propOrder = {"menuItems", "autopayments"})
@XmlRootElement(name = "dropDownLinksList")
public class DropDownContainer
{
	private List<DropDownMenuItem> menuItems;
	private AutoPayments autopayments;

	@XmlElement(name = "dropDownMenuItem", required = false)
	public List<DropDownMenuItem> getMenuItems()
	{
		return menuItems;
	}

	public void setMenuItems(List<DropDownMenuItem> menuItems)
	{
		this.menuItems = menuItems;
	}

	/**
	 * ������� "�����������"
	 * @return AutoPayments
	 */
	@XmlElement(name = "autopayments", required = false)
	public AutoPayments getAutopayments()
	{
		return autopayments;
	}

	public void setAutopayments(AutoPayments autopayments)
	{
		this.autopayments = autopayments;
	}

	public boolean isComplete()
	{
		return !(CollectionUtils.isNotEmpty(menuItems) &&
				 autopayments == null);
	}
}
