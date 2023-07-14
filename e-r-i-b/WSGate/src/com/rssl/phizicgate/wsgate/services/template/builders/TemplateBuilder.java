package com.rssl.phizicgate.wsgate.services.template.builders;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.wsgate.services.template.generated.GateTemplate;

/**
 * ������ �������� ����������
 *
 * @author khudyakov
 * @ created 04.08.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateBuilder
{
	/**
	 * ������� ������ ��������� �� ����������� �� ���� ������
	 * @param generated ������ �� WS
	 * @return ������
	 */
	TemplateDocument build(GateTemplate generated) throws GateException, GateLogicException;
}
