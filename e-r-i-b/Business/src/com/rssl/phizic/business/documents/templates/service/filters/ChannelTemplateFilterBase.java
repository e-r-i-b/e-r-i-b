package com.rssl.phizic.business.documents.templates.service.filters;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.documents.templates.TemplateDocument;

/**
 * ������� ����� �������� �������� ���������� �� ���� ������
 *
 * @author khudyakov
 * @ created 01.07.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class ChannelTemplateFilterBase implements TemplateDocumentFilter
{
	/**
	 * @return ����� �� �������
	 */
	protected abstract CreationType getChannelType();

	/**
	 * @return �������� �� �������
	 */
	protected abstract CreationType getValue(TemplateDocument template);

	public boolean accept(TemplateDocument template)
	{
		CreationType channelType = getChannelType();
		if (channelType == null)
		{
			return true;
		}

		return channelType == getValue(template);
	}
}
