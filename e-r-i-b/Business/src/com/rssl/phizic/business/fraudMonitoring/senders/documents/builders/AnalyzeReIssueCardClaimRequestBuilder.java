package com.rssl.phizic.business.fraudMonitoring.senders.documents.builders;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ReIssueCardClaim;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.rsa.integration.ws.control.generated.AccountData;
import com.rssl.phizic.rsa.integration.ws.control.generated.ClientDefinedFact;
import com.rssl.phizic.rsa.integration.ws.control.generated.DataType;
import com.rssl.phizic.rsa.senders.ClientDefinedFactBuilder;
import com.rssl.phizic.rsa.senders.types.*;

import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.NO;
import static com.rssl.phizic.rsa.senders.ClientDefinedFactConstants.OFFLINE_LOAD_FIELD_NAME;

/**
 * Билдер запросов заявки перевыпуска карты (тип analyze)
 *
 * @author khudyakov
 * @ created 18.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeReIssueCardClaimRequestBuilder extends AnalyzeDocumentRequestBuilderBase<ReIssueCardClaim>
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private ReIssueCardClaim document;

	@Override
	public AnalyzeDocumentRequestBuilderBase append(ReIssueCardClaim document)
	{
		this.document = document;
		return this;
	}

	@Override
	protected ReIssueCardClaim getBusinessDocument()
	{
		return document;
	}
	@Override
	protected AccountData createOtherAccountData()
	{
		return null;
	}

	@Override
	protected AccountData createMyAccountData()
	{
		AccountData accountData = new AccountData();
		accountData.setAccountNumber(document.getCardNumber());
		accountData.setInternationalAccountNumber(document.getCardNumber());
		accountData.setAccountName(document.getPayerName());
		return accountData;
	}

	@Override
	protected AccountPayeeType getAccountPayeeType()
	{
		return null;
	}

	@Override
	protected BeneficiaryType getBeneficiaryType() throws GateException
	{
		return null;
	}

	@Override
	protected DirectionTransferFundsType getDirectionTransferFundsType()
	{
		return null;
	}

	@Override
	protected ExecutionPeriodicityType getExecutionPeriodicityType()
	{
		return null;
	}

	@Override
	protected WayTransferOfFundsType getWayTransferOfFundsType() throws GateException
	{
		return null;
	}

	@Override
	protected ClientDefinedEventType getClientDefinedEventType() throws GateException
	{
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			CardLink cardLink = resourceService.findLinkByNumber(documentOwner.getLogin(), ResourceType.CARD, document.getCardNumber());
			if (cardLink.getCard().isVirtual())
			{
				return ClientDefinedEventType.REISSUE_VIRTUAL_CARD;
			}
			return ClientDefinedEventType.REISSUE_CARD;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	@Override
	protected EventsType getEventsType()
	{
		return EventsType.REQUEST_NEW_CARD;
	}

	protected ClientDefinedFact[] createClientDefinedAttributeList() throws GateException, GateLogicException
	{
		return new ClientDefinedFactBuilder()
				.append(OFFLINE_LOAD_FIELD_NAME, NO, DataType.STRING)
				.build();
	}
}
