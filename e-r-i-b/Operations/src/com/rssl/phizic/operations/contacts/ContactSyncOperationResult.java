package com.rssl.phizic.operations.contacts;

import java.util.Map;
import java.util.Set;

/**
 * ��������� ������������� ���������.
 *
 * @author bogdanov
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 */

public class ContactSyncOperationResult
{
	private final Map<String, Set<String>> sberbankContacts;
	private final Map<String, Set<String>> noSberbankContacts;
	private final Map<String, Set<String>> incognitoContacts;
	private final Map<String, Set<String>> unlimitedContacts;

	private final Long firstSyncLimit;
	private final Long weekLimit;
	private final Long dayLimit;

	public ContactSyncOperationResult(Map<String, Set<String>>[] maps, Long[] limits)
	{
		sberbankContacts = maps[0];
		noSberbankContacts = maps[1];
		incognitoContacts = maps[2];
		unlimitedContacts = maps[3];
		firstSyncLimit = limits[0];
		dayLimit = limits[1];
		weekLimit = limits[2];
	}

	/**
	 * @return �������� ���������� ����� �� �������������.
	 */
	public Long getDayLimit()
	{
		return dayLimit;
	}

	/**
	 * @return ����� �� ������ �������� ����������� ���������.
	 */
	public Long getFirstSyncLimit()
	{
		return firstSyncLimit;
	}

	/**
	 * @return ������ ��������� �� �������� ���������.
	 */
	public Map<String, Set<String>> getIncognitoContacts()
	{
		return incognitoContacts;
	}

	/**
	 * @return ������ ��������� �� �������� ���������.
	 */
	public Map<String, Set<String>> getNoSberbankContacts()
	{
		return noSberbankContacts;
	}

	/**
	 * @return ������ ��������� �������� ���������.
	 */
	public Map<String, Set<String>> getSberbankContacts()
	{
		return sberbankContacts;
	}

	/**
	 * @return ������ �������������� ��������� ��-�� ���������� ������.
	 */
	public Map<String, Set<String>> getUnlimitedContacts()
	{
		return unlimitedContacts;
	}

	/**
	 * @return ��������� ����� �� ������������� ���������.
	 */
	public Long getWeekLimit()
	{
		return weekLimit;
	}
}

