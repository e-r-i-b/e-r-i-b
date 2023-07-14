package com.rssl.phizic.gate.dictionaries;

import java.util.List;
import java.util.Set;

/**
 * ��������� �������� �����������
 * @author Pankin
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SynchronizeResult
{
	/**
	 * @return ������ ����� ��������� ��� ������� �����������
	 */
	public Set<String> getMessages();

	/**
	 * �������� ����� ��������� (�� ����������� � ���������� ������)
	 * @param message ����� ���������
	 */
	public void addMessage(String message);

	/**
	 * @return ������ ����� ������ �������� ��� ������� �����������
	 */
	public List<String> getErrors();

	/**
	 * �������� ����� ������ (�� ����������� � ���������� ������)
	 * @param error ����� ������
	 */
	public void addError(String error);

	/**
	 * @return ������ ����������� ��� ���� �������
	 */
	public List<SynchronizeResultRecord> getResultRecords();

	/**
	 * @return ��� �����������
	 */
	public DictionaryType getDictionaryType();
}
