package com.rssl.phizic.gate.depo;

import java.util.List;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������ ������ �� ����� ����
 */
public interface DepoAccountSecurity extends SecurityBase
{
	/**
	 * @return ������ �������� ��� ������ ������ ������
	 */
	List<SecurityMarker> getSecurityMarkers();

	/**
	 * �������, ���-�� ������ ����� �� �����(��.)
	 * @return remainder
	 */
	Long getRemainder();

	/**
	 * ����� �������� ������ ������ �� ����� ����
	 * @return storageMethod
	 */
	DepoAccountSecurityStorageMethod getStorageMethod();
}
