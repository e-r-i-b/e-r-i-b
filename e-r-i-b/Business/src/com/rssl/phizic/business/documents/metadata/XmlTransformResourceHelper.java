package com.rssl.phizic.business.documents.metadata;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.Field;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Хелпер получения информации
 *
 * @author khudyakov
 * @ created 07.11.14
 * @ $Author$
 * @ $Revision$
 */
public class XmlTransformResourceHelper
{
	private static final String VALUE_DELIMITER                         = "|";
	private static final String PARAM_DELIMITER                         = ";";
	private static final String NAME_PARAM_NAME                         = "name";
	private static final String NUMBER_PARAM_NAME                       = "number";
	private static final String CURRENCY_CODE_PARAM_NAME                = "currencyCode";

	private static final Map<String, Class<? extends ExternalResourceLink>> RESOURCES = new HashMap<String, Class<? extends ExternalResourceLink>>();
	static
	{
		RESOURCES.put(CardLink.CODE_PREFIX,         CardLink.class);
		RESOURCES.put(AccountLink.CODE_PREFIX,      AccountLink.class);
	}

	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	/**
	 * Получить линк по коду линка
	 * @param code код
	 * @return линк
	 * @throws BusinessException
	 */
	public static ExternalResourceLink findResourceLinkByCode(String code) throws BusinessException
	{
		if (StringHelper.isEmpty(code))
		{
			return null;
		}

		String[] linkId = code.split(ExternalResourceLinkBase.CODE_DELIMITER);
		if (linkId.length != 2)
		{
			throw new IllegalArgumentException();
		}

		return externalResourceService.findLinkById(RESOURCES.get(linkId[0]), Long.valueOf(linkId[1]));
	}

	/**
	 * Получить xml описание ресурса по коду линка
	 * @param code код
	 * @return xml описание
	 */
	public String getResourceAsXmlByLinkCode(String code) throws BusinessException
	{
		ExternalResourceLink resource = findResourceLinkByCode(code);
		if (resource == null)
		{
			return null;
		}
		return buildXmlInfo(resource);
	}

	private String buildXmlInfo(ExternalResourceLink resource)
	{
		Entity entity = new Entity(resource.getExternalId(), null);
		entity.addField(new Field(NAME_PARAM_NAME, resource.getName()));
		entity.addField(new Field(NUMBER_PARAM_NAME, resource.getNumber()));
		entity.addField(new Field(CURRENCY_CODE_PARAM_NAME, resource.getCurrency().getCode()));

		return entity.toString();
	}

	/**
	 * Получить xml описание ресурса по коду линка
	 * @param code код
	 * @return xml описание
	 */
	public String getResourceAsStringByLinkCode(String code) throws BusinessException
	{
		ExternalResourceLink resource = findResourceLinkByCode(code);
		if (resource == null)
		{
			return null;
		}
		return buildStringInfo(resource);
	}

	private String buildStringInfo(ExternalResourceLink resource)
	{
		return NAME_PARAM_NAME + VALUE_DELIMITER + resource.getName() + PARAM_DELIMITER + NUMBER_PARAM_NAME + VALUE_DELIMITER + resource.getNumber() + PARAM_DELIMITER + CURRENCY_CODE_PARAM_NAME + VALUE_DELIMITER + resource.getCurrency().getCode();
	}
}

