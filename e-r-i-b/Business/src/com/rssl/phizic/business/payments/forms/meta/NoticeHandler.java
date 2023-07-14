package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.DocumentNotice;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Gulov
 * @ created 11.03.15
 * @ $Author$
 * @ $Revision$
 */

/**
 * Добавление в документ объекта уведомления
 */
public class NoticeHandler extends BusinessDocumentHandlerBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	protected static final String TYPE = "notice-type";
	protected static final String TITLE = "notice-title";
	protected static final String TEXT = "notice-text";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		BusinessDocument claim = (BusinessDocument) document;
		String clazz = getParameter(TYPE);
		String title = getParameter(TITLE);
		String text = getParameter(TEXT);
		if (StringHelper.isEmpty(text))
			return;
		claim.setNotice(new DocumentNotice(clazz, title, text));
		save(claim);
	}

	protected void save(BusinessDocument claim) throws DocumentException
	{
		try
		{
			businessDocumentService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
