package com.rssl.phizic.gate.insurance;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @author lukina
 * @ created 06.03.2013
 * @ $Author$
 * @ $Revision$
 * �������� ���������� �������� �����������: �����, ����� � ���� ������ ������
 */

public interface PolicyDetails  extends Serializable
{
	/**
	 * @return �����
	 */
	String getSeries();

	/**
	 * @return �����
	 */
	String getNum();

	/**
	 * @return ���� ������
	 */
	Calendar getIssureDt();
}
