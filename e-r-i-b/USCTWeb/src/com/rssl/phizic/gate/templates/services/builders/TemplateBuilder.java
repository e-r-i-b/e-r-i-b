package com.rssl.phizic.gate.templates.services.builders;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;

/**
 * ������ �������� ����������
 *
 * @author khudyakov
 * @ created 17.10.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateBuilder<T extends TemplateDocument>
{
	/**
	 * ������� ������ ��������� �� ����������� �� ���� ������
	 * @param generated ������ �� WS
	 * @return ������
	 */
	TemplateDocument build(GateTemplate generated) throws Exception;

	/**
	 * ������� ������ ��������� �� ����������� �� ���� ������
	 * @param template ������� ������
	 * @param generated ������ �� WS
	 * @return ������
	 */
	public TemplateDocument build(T template, GateTemplate generated) throws Exception;
}
