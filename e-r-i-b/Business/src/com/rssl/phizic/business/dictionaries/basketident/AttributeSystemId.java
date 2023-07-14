package com.rssl.phizic.business.dictionaries.basketident;

import com.rssl.phizic.business.userDocuments.DocumentType;

import java.util.*;

/**
 * Системный ид атрибутов.
 *
 * @author bogdanov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */

public enum AttributeSystemId
{
	SERIES(DocumentType.DL, "Series",  DocumentType.RC, "Series"),
	NUMBER(DocumentType.INN, "Inn", DocumentType.DL, "Number", DocumentType.RC, "Number"),
	ISSUE_DATE(DocumentType.DL, "Idate"),
	EXPIRE_DATE(DocumentType.DL, "Edate"),
	ISSUE_BY(DocumentType.DL, "Issuer");

	private static final Map<String, AttributeSystemId> nameToAttribute = new HashMap<String, AttributeSystemId>();
	static
	{
		for (AttributeSystemId as : AttributeSystemId.values())
		{
			for (DocumentType dt : DocumentType.values())
			{
				if (as.getNameFor(dt) != null)
					nameToAttribute.put(as.getNameFor(dt), as);
			}
		}
	}
	private Map<DocumentType, String> availableDocumentType = new HashMap<DocumentType, String>();

	private AttributeSystemId(Object... types)
	{
		for (int i = 0; i < types.length; i += 2)
		{
			availableDocumentType.put((DocumentType) types[i], (String) types[i + 1]);
		}
	}

	/**
	 * Отображаемое имя.
	 * @param type тип.
	 * @return отображаемое имя.
	 */
	public String getNameFor(DocumentType type)
	{
		return availableDocumentType.get(type);
	}

	/**
	 * Получить атрибут по имени.
	 *
	 * @param name имя.
	 * @return атрибут.
	 */
	public static AttributeSystemId getByName(String name) {
		return nameToAttribute.get(name);
	}
}
