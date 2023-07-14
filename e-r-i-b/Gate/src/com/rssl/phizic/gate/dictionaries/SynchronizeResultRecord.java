package com.rssl.phizic.gate.dictionaries;

import java.util.Collections;
import java.util.List;

/**
 * ��������� ������������� ������ �����������.
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class SynchronizeResultRecord
{
	private Comparable synchKey;
	private SynchronizeResultStatus status;
	private List<String> errorDescriptions;

	/**
	 * �������� ���� ������
	 * @return ���� ������
	 */
	public Comparable getSynchKey()
	{
		return synchKey;
	}

	/**
	 * ���������� ���� ������
	 * @param synchKey ���� ������
	 */
	public void setSynchKey(Comparable synchKey)
	{
		this.synchKey = synchKey;
	}

	/**
	 * �������� ������ ���������� ������������� ��� ������
	 * @return ������ ���������� �������������
	 */
	public SynchronizeResultStatus getStatus()
	{
		return status;
	}

	/**
	 * ���������� ������ ���������� ������������� ������
	 * @param status ������ ���������� �������������
	 */
	public void setStatus(SynchronizeResultStatus status)
	{
		this.status = status;
	}

	/**
	 * �������� ������ �������� ������ �� ����������� �������������
	 * @return ������ �������� ������
	 */
	public List<String> getErrorDescriptions()
	{
		return errorDescriptions == null ? null : Collections.unmodifiableList(errorDescriptions);
	}

	/**
	 * ���������� ������ ������ �� ����������� �������������
	 * @param errorDescriptions ������ ������
	 */
	@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter"})
	public void setErrorDescriptions(List<String> errorDescriptions)
	{
		this.errorDescriptions = errorDescriptions;
	}
}
