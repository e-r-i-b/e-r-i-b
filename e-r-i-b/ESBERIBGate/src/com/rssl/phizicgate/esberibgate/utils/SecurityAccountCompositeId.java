package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;

/**
 * @author lukina
 * @ created 05.09.13
 * @ $Author$
 * @ $Revision$
 * Объектное представление компоситного идентификатора сберегательного сертификата
 * <entityId>^<loginId>
 */

public class SecurityAccountCompositeId
{
	public static final String ID_DELIMETER = "^";

	private String entityId;
	private String secType;
	private Long loginId;
	private String systemId;

	public SecurityAccountCompositeId(String id)
	{
		String[] strings = id.trim().split("\\^");
		entityId = strings.length > 0 ? strings[0] : null;
		secType = strings.length > 1 ? strings[1] : null;
		systemId = strings.length > 2 ? strings[2] : null;
		loginId = strings.length > 3 ? Long.parseLong(strings[3]) : null;
	}

	public SecurityAccountCompositeId(String entityId,String secType, Long loginId, String systemId)
	{
		this.entityId = entityId;
		this.secType = secType;
		this.loginId = loginId;
		this.systemId = systemId;
	}

	/**
	 * @return Идентифкатор продукта во внешней системе.
	 */
	public String getEntityId()
	{
		return entityId;
	}

	/**
	 * @return идентификатор владельца продукта в ИКФЛ
	 */
	public Long getLoginId()
	{
		return loginId;
	}

	public String getSystemIdActiveSystem() throws GateException
	{
		return ExternalSystemHelper.getCode(systemId);
	}

	public String getSecType()
	{
		return secType;
	}

	public String toString()
		{
			StringBuilder compositeId = new StringBuilder();
			compositeId.append(entityId).append(ID_DELIMETER);
			compositeId.append(secType).append(ID_DELIMETER);
			compositeId.append(systemId).append(ID_DELIMETER);
			compositeId.append(loginId);
			return compositeId.toString();
		}
}
