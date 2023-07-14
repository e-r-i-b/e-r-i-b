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
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * @author usachev
 * @ created 29.06.15
 * @ $Author$
 * @ $Revision$
 *
 * Хедлер для установки сообщения об отказе заявки на кредит с учётом мораторных кодов
 */

public class LoanClaimMoratoriumCodeHandler extends BusinessDocumentHandlerBase
{
	private static final String TYPE = "notice-type";
	private static final String TITLE = "notice-title";
	private static final String TEXT = "notice-text";
	private static final String NOT_VISIBLE_STRING = "\t";

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof ExtendedLoanClaim))
		{
			 throw new IllegalArgumentException("Ожидается документ типа ExtendedLoanClaim. Пришёл " + document.getClass().getName());
		}

		String clazz = getParameter(TYPE);
		String title = getParameter(TITLE);
		String text = NOT_VISIBLE_STRING;

		ExtendedLoanClaim claim = (ExtendedLoanClaim) document;
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
		List<String> moratoriumCodes = loanClaimConfig.getAllowedRefusalCodes();

		if (claim.getEtsmErrorCode() != null)
		{
			String errorCodeOfClaim = claim.getEtsmErrorCode().toString();
			if (moratoriumCodes.contains(errorCodeOfClaim))
			{
				text = getParameter(TEXT);
			}
		}

		claim.setNotice(new DocumentNotice(clazz, title, text));
	}
}
