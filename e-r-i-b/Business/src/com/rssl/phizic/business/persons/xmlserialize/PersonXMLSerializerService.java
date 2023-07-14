package com.rssl.phizic.business.persons.xmlserialize;

import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 28.09.2006
 * Time: 8:56:36
 * To change this template use File | Settings | File Templates.
 */
public class PersonXMLSerializerService
{
	private static final SimpleService simpleService = new SimpleService();
	private final static String QUERY_PREFIX = PersonXMLSerializerService.class.getName()+".";

	public XMLPersonRepresentation findRepresentationByUserId(String userId) throws BusinessException
	{
		XMLPersonRepresentation criteria = new XMLPersonRepresentation();
		criteria.setUserId(userId);

		return simpleService.executeQuerySingle(QUERY_PREFIX+"findByUserId", criteria);
	}

	public void saveOrUpdateRepresentation(XMLPersonRepresentation repr) throws BusinessException
	{
		if(repr!=null) simpleService.addOrUpdate(repr);
	}
}
