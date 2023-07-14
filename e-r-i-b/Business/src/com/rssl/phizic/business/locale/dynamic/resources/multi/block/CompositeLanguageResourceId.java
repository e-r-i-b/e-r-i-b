package com.rssl.phizic.business.locale.dynamic.resources.multi.block;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author komarov
 * @ created 28.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class CompositeLanguageResourceId
{
	private static final String DELIMITER = "|";
	private static final Pattern pattern = Pattern.compile("([^\\"+DELIMITER+"]*)\\"+DELIMITER+"?(.*)");
	private final String localeId;
	private final String uuid;

	/**
	 * @param localeId ������������� ������
	 * @param uuid �������� ������������� ��������
	 */
	private CompositeLanguageResourceId(String localeId, String uuid)
	{
		this.localeId = localeId;
		this.uuid = uuid;
	}

	/**
	 * @return ������������� ������
	 */
	public String getLocaleId()
	{
		return localeId;
	}

	/**
	 * @return �������� ������������� ��������
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param identifier ��������� �������������
	 * @return CompositeLanguageResourceId
	 */
	public static CompositeLanguageResourceId parse(String identifier)
	{
		Matcher matcher = pattern.matcher(identifier);
		if(matcher.find())
			return new CompositeLanguageResourceId(matcher.group(1), matcher.group(2));
		return null;
	}

	/**
	 * @param resource ������������� �������� ������
	 * @return ��������� �������������
	 */
	public static String format(MultiBlockLanguageResources resource)
	{
		return resource.getLocaleId()+DELIMITER+resource.getUuid();
	}

}
