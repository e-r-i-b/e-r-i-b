package com.rssl.phizic.gate.depo;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.List;

/**
 * @author mihaylov
 * @ created 21.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * �������� ������ ��� ������ �� ������������ ������ �����
 */
public interface BackRefSecurityService extends Service
{
	/**
	 * @param insideCodes - ������ ����� ������� ������ �����
	 * @return ������ ������ ����� �� �����������
	 */
	List<SecurityBase> getSecuritiesByCodes(List<String> insideCodes) throws GateException;
}
