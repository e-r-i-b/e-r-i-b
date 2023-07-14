package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.constructors.helpers.CommonElementsHelper;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.IdRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.AccountDetailResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productdetail.AccountDetailTag;

/**
 * Заполняет ответ на запрос детальной информации по вкладу
 * @author Jatsky
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */

public class AccountDetailResponseConstructor extends JAXBResponseConstructor<Request, AccountDetailResponse>
{
	@Override
	protected AccountDetailResponse makeResponse(Request request) throws Exception
	{
		IdRequest rqst = (IdRequest) request;
		AccountDetailResponse response = new AccountDetailResponse();
		Long accountId;
		try
		{
			accountId = Long.valueOf(rqst.getBody().getId());
		}
		catch (NumberFormatException e)
		{
			response.setStatus(new Status(StatusCode.ACCESS_DENIED, "неправильный ИД счета: " + rqst.getBody().getId()));
			return response;
		}
		GetAccountAbstractExtendedOperation accountInfoOperation = createOperation(GetAccountAbstractExtendedOperation.class);
		accountInfoOperation.initialize(accountId);
		AccountLink accountLink = accountInfoOperation.getAccount();
		Account     account     = accountLink.getAccount();
		AccountDetailTag accountDetailTag = new AccountDetailTag();
		CommonElementsHelper.fillAccountTag(accountLink, accountDetailTag);

		DateSpan period = account.getPeriod();
		if (period != null)
			accountDetailTag.setPeriod(period.getYears() + "-" + period.getMonths() + "-" + period.getDays());
		if (account.getCloseDate() != null)
			accountDetailTag.setCloseDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(account.getCloseDate()));
		if (account.getCreditCrossAgencyAllowed() != null)
			accountDetailTag.setCreditCrossAgency(account.getCreditCrossAgencyAllowed());
		if (account.getDebitCrossAgencyAllowed() != null)
			accountDetailTag.setDebitCrossAgency(account.getDebitCrossAgencyAllowed());
		if (account.getProlongationAllowed() != null)
			accountDetailTag.setProlongation(account.getProlongationAllowed());
		if (account.getInterestTransferAccount() != null)
			accountDetailTag.setInterestTransferAccount(account.getInterestTransferAccount());
		if (account.getInterestTransferCard() != null)
			accountDetailTag.setInterestTransferCard(account.getInterestTransferCard());

		response.setAccount(accountDetailTag);
		return response;
	}
}
