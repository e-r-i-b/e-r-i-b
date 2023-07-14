package com.rssl.phizic.gate.dictionaries;

/**
 * ������ �����������
 */
public interface DictionaryRecord
{
	/**
	 * @return ���� �������������
	 */
    Comparable getSynchKey();

    /**
     * �������� ���������� ������ �� ������
     * @param that ������ �� ������� ���� ��������
     */
    void updateFrom(DictionaryRecord that);
}
