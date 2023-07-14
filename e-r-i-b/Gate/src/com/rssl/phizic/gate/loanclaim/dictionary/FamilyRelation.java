package com.rssl.phizic.gate.loanclaim.dictionary;

import javax.xml.bind.annotation.*;

/**
 * @author Nady
 * @ created 25.12.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Запись справочника "Родственная связь"
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "family-relation")
public class FamilyRelation extends AbstractDictionaryEntry
{
	/**
	 * true, если дочь или сын
	 */
	@XmlElement(name = "children", required = true)
	private boolean children;

	/**
	 * @return true, если ребёнок
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
