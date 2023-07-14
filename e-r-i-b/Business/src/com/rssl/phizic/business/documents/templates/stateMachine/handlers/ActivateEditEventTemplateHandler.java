package com.rssl.phizic.business.documents.templates.stateMachine.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;

import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.attributes.Attributable;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactory;
import com.rssl.phizic.business.documents.templates.attributes.ExtendedAttributeFactoryImpl;
import com.rssl.phizic.gate.documents.attribute.ExtendedAttribute;
import com.rssl.phizic.gate.documents.attribute.Type;
import org.apache.commons.lang.BooleanUtils;

/**
 * Хендлер активизации режима редактирования шаблона документа
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class ActivateEditEventTemplateHandler extends TemplateHandlerBase<Attributable>
{
	private static final ExtendedAttributeFactory attributeFactory = new ExtendedAttributeFactoryImpl();

	public void process(Attributable attributable, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		ExtendedAttribute attribute = attributable.getAttribute(Constants.EDIT_EVENT_ATTRIBUTE_NAME);
		if (attribute != null)
		{
			attribute.setValue(BooleanUtils.toBoolean(getParameter("value")));
			return;
		}

		attributable.addAttribute(attributeFactory.create(Constants.EDIT_EVENT_ATTRIBUTE_NAME, Type.BOOLEAN, BooleanUtils.toBoolean(getParameter("value"))));
	}
}
