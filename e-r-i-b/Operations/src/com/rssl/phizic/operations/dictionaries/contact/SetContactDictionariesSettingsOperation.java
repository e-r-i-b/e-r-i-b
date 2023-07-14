package com.rssl.phizic.operations.dictionaries.contact;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.config.Constants;
import com.rssl.phizic.business.dictionaries.config.DictionaryPathConfig;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author Kosyakova
 * @ created 24.11.2006
 * @ $Author$
 * @ $Revision$
 */
public class SetContactDictionariesSettingsOperation extends OperationBase
{
	public String getContactDictionariesPath()
	{
		return contactDictionariesPath;
	}

	public void setContactDictionariesPath(String contactDictionariesPath)
	{
		this.contactDictionariesPath = contactDictionariesPath;
	}

	private String  contactDictionariesPath;


	@Transactional
	public void save() throws BusinessException
	{
		throw new UnsupportedOperationException();
	}

	public void read()
	{
		DictionaryPathConfig dictionaryPathConfig = ConfigFactory.getConfig(DictionaryPathConfig.class);
		setContactDictionariesPath(dictionaryPathConfig.getPath(Constants.CONTACT_DICTIONARY));
	}


}