package com.rssl.phizic.gate.templates.metadata;

import com.rssl.phizic.gate.templates.impl.TemplateDocument;

/**
 * ���������� ������� ���������
 *
 * @author khudyakov
 * @ created 30.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface Metadata
{
	/**
	 * @return ������������
	 */
	String getName();

	/**
	 * ������� ������ ���������
	 * @return ������� ���������
	 */
	TemplateDocument createTemplate() throws Exception;
}
