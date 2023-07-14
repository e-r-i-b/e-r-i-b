package com.rssl.phizic.gate.documents;

import com.rssl.phizic.common.types.documents.State;

import java.util.Calendar;

/**
 * ��������� ��� �������� ���������� �� ���������� ��������� �� ������� �������.
 * @author Maleyev
 * @ created 07.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface StateUpdateInfo
{
	/**
	 * ����� ������ ���������
	 * @return State
	 */
	State getState();

	/**
	 * @return ���� ��������� ��������� ���������
	 */
	Calendar getNextProcessDate();
}
