package com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.alf;

import com.rssl.phizic.utils.StringHelper;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 * Статус услуги «Анализ личных финансов»
 * @author Jatsky
 * @ created 12.05.14
 * @ $Author$
 * @ $Revision$
 */

@XmlEnum
public enum ALFStatus
{
	@XmlEnumValue(value = "notConnected")
	notConnected("notConnected"),
	@XmlEnumValue(value = "noOperations")
	noOperations("waitingClaims"),
	@XmlEnumValue(value = "connected")
	connected("allOk"),
	@XmlEnumValue(value = "noProducts")
	noProducts("noProducts");

	private final String financesStatus;

	ALFStatus(String financesStatus)
	{
		this.financesStatus = financesStatus;
	}

	/**
	 * @param financesStatus название статуса как оно представлено в ЕРИБ
	 * @return ALFStatus
	 */
	public static ALFStatus getTypeByFinancesStatus(String financesStatus)
	{
		if (StringHelper.isNotEmpty(financesStatus))
		{
			for (ALFStatus value : ALFStatus.values())
			{
				if (financesStatus.equals(value.toValue()))
				{
					return value;
				}
			}
		}

		return null;
	}

	/**
	 * @return Возвращает название статуса как оно представлено в ЕРИБ
	 */
	public String toValue()
	{
		return this.financesStatus;
	}

}
