package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� "����������� �����"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "family-relation")
public class FamilyRelation extends AbstractDictionaryEntry
{
	/**
	 * true, ���� ���� ��� ���
	 */
	@XmlElement(name = "children", required = true)
	private boolean children;

	/**
	 * @return true, ���� ������
	 */
	public boolean isChildren()
	{
		return children;
	}

	public void setChildren(boolean children)
	{
		this.children = children;
	}
}
