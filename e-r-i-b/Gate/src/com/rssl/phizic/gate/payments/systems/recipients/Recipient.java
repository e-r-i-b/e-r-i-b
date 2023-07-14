package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.gate.dictionaries.DictionaryRecord;

/**
 * ���������� �������
 * @author Gainanov
 * @ created 03.07.2008
 * @ $Author$
 * @ $Revision$
 */

public interface Recipient extends DictionaryRecord
{
   /**
    * ������, � ������� ������ ���� ����������. �������� "���������", "������".
    *
    * @return ������.
    */
   Service getService();
	
   /**
    * �������� �������� ����������  ��� ����������� ������������. ��������, ��� "���������"
    *
    * @return ��� �������� ����������
    */
   String getName();

    /**
     * ����������( ����������) �� ����������.
     *
     * @return ���������� � ����������.
     */
    String getDescription();

	/**
	 * �������� ������ ��� ���
	 * @return main
	 */
	Boolean isMain();
}
