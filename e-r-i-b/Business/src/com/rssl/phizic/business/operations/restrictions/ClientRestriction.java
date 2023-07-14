package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.gate.clients.Client;

/**
 * @author mihaylov
 * @ created 07.08.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �������� ����������� �� �������
 */
public interface ClientRestriction extends Restriction
{

	/**
	 * �������� ����������� ������ � ��������
	 * @param client ������ ��� ��������
	 * @return ����� �� �������� � ��������
	 */
	boolean accept(Client client) throws BusinessException;

}
