package com.rssl.phizic.business.smsbanking.pseudonyms;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.*;

/**
 * @author eMakarov
 * @ created 25.10.2008
 * @ $Author$
 * @ $Revision$
 */
public class PseudonymGenerator extends Config
{
	private final String PSEUDONYM_CONFIG_PREFIX = "com.rssl.iccs.pseudonyms";
	private final String SEPARATOR = ".";
	private final String PSEUDONYM_PREFIX = "prefix";
	private final String PSEUDONYM_LENGTH = "substringParameters";

	public PseudonymGenerator(PropertyReader reader)
	{
		super(reader);
	}

	public String generate(Pseudonym pseudonym) throws BusinessException, BusinessLogicException
	{
		String className = pseudonym.getClass().getSimpleName();
		String prefix = getProperty(PSEUDONYM_CONFIG_PREFIX+SEPARATOR+className+SEPARATOR+PSEUDONYM_PREFIX);
		int count = getIntProperty(PSEUDONYM_CONFIG_PREFIX+SEPARATOR+PSEUDONYM_LENGTH);

		String number = pseudonym.getNumber();
		int length = number.length();
		while (length < count)
		{
			number = "0"+number;
			length++;
		}
		return prefix + number.substring(length-count, length);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
	}
}
