package com.rssl.phizic.gate.deposit;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.w3c.dom.Document;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;

/**
 * @author Danilov
 * @ created 30.07.2007
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� ���������� � ���������� ���������
 */
public interface DepositProductService extends Service
{
	/**
	 * ��������� ���������� � ���� ���������� ���������.
	 * ������������ �������� �������� ����������, ����������� ��� �������� ����������� ��������.
	 * ������ ������������� xml ��������� ������������ �����������.
	 *
	 * @param office - ����, �� �������� ���������� �������� ����������
	 * @return xml
	 * @exception GateException
	 */
	Document getDepositsInfo(Office office) throws GateException, GateLogicException;
	
	/**
	 * ��������� ���������� � ���������� ��������. ������ ��������� ������������ �����������.
	 *
	 * @param params ������ �� ���������� � ���������� ��������.
	 * @param office - ����, �� �������� ���������� �������� ����������
	 * @return xml
	 * @exception GateException
	 */
	Document getDepositProduct(Document params, Office office) throws GateException, GateLogicException;
}