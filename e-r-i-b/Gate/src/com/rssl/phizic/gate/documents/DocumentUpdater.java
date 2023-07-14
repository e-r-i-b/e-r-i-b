package com.rssl.phizic.gate.documents;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Map;

/**
 * @author Maleyev
 * @ created 07.04.2008
 * @ $Author$
 * @ $Revision$
 */
public interface DocumentUpdater
{
	/**
	 * �������� ��������
	 * @param document ��������
	 * @return StateUpdateInfo ���������
	 */
	StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException;

	/**
	 * ���������� ���������
	 * @param params ���������
	 */
	void setParameters(Map<String,?> params);
}
