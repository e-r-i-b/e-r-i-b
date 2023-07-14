package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.documents.DepositOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author hudyakov
 * @ created 01.07.2010
 * @ $Author$
 * @ $Revision$
 */
public class OpenAccountPostUpdateDocumentHandler extends PostUpdateDocumentHandler
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof DepositOpeningClaim))
			throw new DocumentException("ќжидаетс€ наследник DepositOpeningClaim");

		DepositOpeningClaim openPayment = (DepositOpeningClaim) document;

		// instanceof не пройдет, т.к. нас есть только класс DepositOpeningClaim (а вклады и счета, возможно, раздел€т)
		if (openPayment.getFormName().equals("AccountOpeningClaim"))
		{
			addAccountLink(openPayment);
		}

		super.process(document, stateMachineEvent);
	}

	private void addAccountLink(DepositOpeningClaim document) throws DocumentLogicException, DocumentException
	{
		try
		{
			BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("ќпераци€ в гостевой сессии не поддерживаетс€");
			externalResourceService.addNewAccountLink(documentOwner.getLogin(), GroupResultHelper.getOneResult(bankrollService.getAccount(document.getAccount())));
		}
		catch (LogicException e)
		{
			throw new DocumentLogicException(e);
		}
		catch (SystemException e)
		{
			throw new DocumentException(e);
		}
	}

}
