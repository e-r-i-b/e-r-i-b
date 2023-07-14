package com.rssl.phizic.operations.access;

import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToAccount;
import com.rssl.phizic.gate.payments.AccountClosingPaymentToCard;
import com.rssl.phizic.gate.payments.AccountToCardTransfer;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.gate.payments.longoffer.ClientAccountsLongOffer;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.AbstractAccountsTransfer;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.LoanPayment;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.gate.loans.PersonLoanRole;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @ author: filimonova
 * @ created: 10.06.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentDestinationStrategyCondition implements StrategyCondition
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();

	public boolean checkCondition(ConfirmableObject object)
	{
		if (object instanceof AbstractAccountsTransfer && !(object instanceof AccountOpeningClaim))
		{
			AbstractAccountsTransfer transfer = (AbstractAccountsTransfer) object;
			switch (transfer.getDestinationResourceType()) {
				case EXTERNAL_ACCOUNT:
				case EXTERNAL_CARD:
					return true;
				case ACCOUNT:
				case CARD:
					return checkNeedConfirm((AbstractAccountsTransfer) object);
				default:
					return false;
			}
		}
		else if (object instanceof LoanPayment)
		{
			try
			{
				LoanPayment payment = (LoanPayment) object;
				BusinessDocumentOwner documentOwner = payment.getOwner();
				if (documentOwner.isGuest())
					throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
				Login login = documentOwner.getLogin();
				ResourceType resourceType = ResourceType.LOAN;
				String accountNumber = payment.getAccountNumber();
				LoanLink link = externalResourceService.findLinkByNumber(login, resourceType, accountNumber);
				if (link == null)
					return false;

				return !link.getPersonRole().equals(PersonLoanRole.borrower);
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				return false;
			}
		}
		else
			return true;
	}

	private boolean checkNeedConfirm(AbstractAccountsTransfer payment)
	{
		Class<? extends GateDocument> type = payment.getType();
		if (type == AccountToCardTransfer.class || type == ClientAccountsTransfer.class || type == ClientAccountsLongOffer.class)
		{
			return ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountCardPayment();
		}
		else if (type == AccountClosingPaymentToCard.class || type == AccountClosingPaymentToAccount.class)
		{
			return ConfigFactory.getConfig(PaymentsConfig.class).isNeedConfirmSelfAccountClosingPayment();
		}

		return false;
	}

	public String getWarning()
	{
		return null;
	}
}
