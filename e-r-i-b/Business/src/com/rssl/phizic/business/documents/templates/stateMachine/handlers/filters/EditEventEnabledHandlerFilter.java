package com.rssl.phizic.business.documents.templates.stateMachine.handlers.filters;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.HandlerFilterBase;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.attributes.Attributable;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import org.apache.commons.lang.BooleanUtils;

/**
 * ������ ��������� ������ ��������������
 *
 * @author khudyakov
 * @ created 03.08.14
 * @ $Author$
 * @ $Revision$
 */
public class EditEventEnabledHandlerFilter extends HandlerFilterBase<Attributable>
{
	public boolean isEnabled(Attributable template) throws DocumentException
	{
		ExtendedAttribute attribute = template.getAttribute(Constants.EDIT_EVENT_ATTRIBUTE_NAME);
		if (attribute == null)
		{
			return false;
		}

		return BooleanUtils.toBoolean(attribute.getStringValue());
	}
}
