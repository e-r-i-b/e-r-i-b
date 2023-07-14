package com.rssl.phizic.web.common.mobile.contacts;

import com.rssl.phizic.operations.contacts.ContactSyncOperationResult;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Map;
import java.util.Set;

/**
 * ������������� ��������� ���������
 * @author Dorzhinov
 * @ created 27.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class ContactSyncForm extends ActionFormBase
{
	//in
	private String contacts; //xml-������ �� ����� ���������� � ����������
	//out
	private Map<String, Set<String>> sberContactMap; //����� <��� ��������, ��� ������� ���������> ���������, ������������ � ��, �.�. ���������� ��������� �����
	private Map<String, Set<String>> noSberContactMap; //����� <��� ��������, ��� ������� ���������> ���������, ������������ � ��, �.�. ���������� ��������� �����
	private Map<String, Set<String>> incognitoContactMap; //����� <��� ��������, ��� ������� ���������> ���������, ������������ � ��, �.�. ���������� ��������� �����
	private Map<String, Set<String>> unlimitedContactMap; //����� <��� ��������, ��� ������� ���������> ���������, ������������ � ��, �.�. ���������� ��������� �����
	private ContactSyncOperationResult result;

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}

	public Map<String, Set<String>> getSberContactMap()
	{
		return sberContactMap;
	}

	public void setSberContactMap(Map<String, Set<String>> sberContactMap)
	{
		this.sberContactMap = sberContactMap;
	}

	public Map<String, Set<String>> getIncognitoContactMap()
	{
		return incognitoContactMap;
	}

	public void setIncognitoContactMap(Map<String, Set<String>> incognitoContactMap)
	{
		this.incognitoContactMap = incognitoContactMap;
	}

	public Map<String, Set<String>> getNoSberContactMap()
	{
		return noSberContactMap;
	}

	public void setNoSberContactMap(Map<String, Set<String>> noSberContactMap)
	{
		this.noSberContactMap = noSberContactMap;
	}

	public Map<String, Set<String>> getUnlimitedContactMap()
	{
		return unlimitedContactMap;
	}

	public void setUnlimitedContactMap(Map<String, Set<String>> unlimitedContactMap)
	{
		this.unlimitedContactMap = unlimitedContactMap;
	}

	public ContactSyncOperationResult getResult()
	{
		return result;
	}

	public void setResult(ContactSyncOperationResult result)
	{
		this.result = result;
	}
}
