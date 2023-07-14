package com.rssl.phizic.web.webApi.protocol.jaxb.model.common;

import javax.xml.bind.annotation.XmlEnum;

/**
 * Тип подключение клиента
 *
 * @author Balovtsev
 * @since 29.04.2014
 */
@XmlEnum
public enum CreationType
{
	SBOL,
	CARD,
	UDBO;

	public static CreationType getCreationTypeByName(final String name)
	{
		for (CreationType type : CreationType.values())
		{
			if (type.name().equals(name))
			{
				return type;
			}
		}

		return null;
	}
}
