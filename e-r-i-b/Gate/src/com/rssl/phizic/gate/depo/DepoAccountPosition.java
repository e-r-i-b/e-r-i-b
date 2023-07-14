package com.rssl.phizic.gate.depo;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������� �� ����� ����
 */
public interface DepoAccountPosition extends Serializable
{
	/**
	 * ������ �������� ����� ����
	 * @return depoAccountDivision
	 */
	List<DepoAccountDivision> getDepoAccountDivisions();
}
