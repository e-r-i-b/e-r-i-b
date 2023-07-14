package com.rssl.phizicgate.wsgate.services.template.updaters;

import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * �������� �������� ����������
 *
 * @author khudyakov
 * @ created 05.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateUpdater
{
	/**
	 * �������� ������ ���������
	 * @param template Gate ������������� �������
	 * @throws GateException
	 */
	void update(GateTemplate template) throws GateException;
}
