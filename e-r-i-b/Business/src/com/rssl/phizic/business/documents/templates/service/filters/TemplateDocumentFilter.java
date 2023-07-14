package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * ������ �������� ����������
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocumentFilter
{
	/**
	 * �������� �� ������
	 * @param template ������
	 * @return true - ��������
	 */
	boolean accept(TemplateDocument template);
}
