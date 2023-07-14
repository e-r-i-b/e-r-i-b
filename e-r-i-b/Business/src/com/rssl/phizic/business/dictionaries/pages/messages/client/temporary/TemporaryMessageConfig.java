package com.rssl.phizic.business.dictionaries.pages.messages.client.temporary;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author komarov
 * @ created 18.02.2014
 * @ $Author$
 * @ $Revision$
 */
public class TemporaryMessageConfig extends  Config
{
	private static final String PAGES_PREFIX_TEMPORARY = "com.rssl.temporary-client.page.TEMPORARY.URL";
	private static final String PAGES_PREFIX_MIGRATION = "com.rssl.temporary-client.page.MIGRATION.URL";

	private static final String PAGE_TEMPORARY_MESSAGE = "com.rssl.temporary-client.page.message.TEMPORARY";
	private static final String PAGE_MIGRATION_MESSAGE = "com.rssl.temporary-client.page.message.MIGRATION";

	private Set<String> pagesTemporary;
	private Set<String> pagesMigration;
	private String temporaryMessage;
	private String migrationMessage;

	/**
	 * @param reader �����
	 */
	public TemporaryMessageConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		pagesTemporary = new HashSet<String>();
		for(Map.Entry<Object, Object> entry : getProperties(PAGES_PREFIX_TEMPORARY).entrySet())
			pagesTemporary.add((String)entry.getValue());

		pagesMigration = new HashSet<String>();
		for(Map.Entry<Object, Object> entry : getProperties(PAGES_PREFIX_MIGRATION).entrySet())
			pagesMigration.add((String)entry.getValue());

		temporaryMessage = getProperty(PAGE_TEMPORARY_MESSAGE);
		migrationMessage = getProperty(PAGE_MIGRATION_MESSAGE);
	}

	/**
	 * @return ������ �������
	 */
	public Set<String> getPagesTemporary()
	{
		return Collections.unmodifiableSet(pagesTemporary);
	}

	/**
	 * @return ������ �������
	 */
	public Set<String> getPagesMigration()
	{
		return Collections.unmodifiableSet(pagesMigration);
	}

	/**
	 * @return ��������� ��� ������� � ������ ������������ ����������������.
	 */
	public String getTemporaryMessage()
	{
		return temporaryMessage;
	}

	/**
	 * @return ��������� ��� ����������� ������� �� ���������� ����� � ��������
	 */
	public String getMigrationMessage()
	{
		return migrationMessage;
	}
}
