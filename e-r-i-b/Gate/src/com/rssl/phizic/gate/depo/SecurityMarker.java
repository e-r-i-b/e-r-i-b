package com.rssl.phizic.gate.depo;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 20.01.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ������ ������
 */
public interface SecurityMarker extends Serializable
{
	/**
	 * @return ������ ������ ������
	 */
	String getMarker();

	/**
	 * @return ���-�� ������ ����� � ������ �������� �� �����(��.) 
	 */
	Long getRemainder();
}
