package com.rssl.phizic.events;

import java.io.Serializable;

/**
 * @author Krenev
 * @ created 01.06.2009
 * @ $Author$
 * @ $Revision$
 * ������� � �������. ���������� ������ ������� - �����
 */
public interface Event extends Serializable
{
	/**
	 * @return ������ � ���������� ������� ��� ������ � ���
	 */
	public String getStringForLog();
}
