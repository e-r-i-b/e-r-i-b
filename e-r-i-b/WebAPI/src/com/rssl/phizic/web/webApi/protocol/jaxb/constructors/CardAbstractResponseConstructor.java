package com.rssl.phizic.web.webApi.protocol.jaxb.constructors;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.bankroll.*;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.web.webApi.exceptions.ExtendedAbstractNotAvailableException;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.MoneyTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.Status;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.common.StatusCode;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ProductAbstractRequest;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.ProductAbstractRequestBody;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.request.Request;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.response.ProductAbstractResponse;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract.BalancesTag;
import com.rssl.phizic.web.webApi.protocol.jaxb.model.visual.productabstract.OperationTag;
import org.xml.sax.SAXException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * «аполн€ет ответ на запрос выписки по картам
 * @author Jatsky
 * @ created 07.05.14
 * @ $Author$
 * @ $Revision$
 */

public class CardAbstractResponseConstructor extends JAXBResponseConstructor<Request, ProductAbstractResponse>
{
	private static final long maxOperationCount = 10L;
	private static final String WRONG_DATE_COUNT_MESSAGE = "Ќеобходимо заполнить либо from и to одновременно, либо count.";
	private static final String WRONG_ID_MESSAGE = "неправильный »ƒ карты: ";

	@Override protected ProductAbstractResponse makeResponse(Request request) throws Exception
	{
		ProductAbstractRequest rqst = (ProductAbstractRequest) request;
		ProductAbstractResponse response = new ProductAbstractResponse();
		ProductAbstractRequestBody rqstBody = rqst.getBody();
		if ((StringHelper.isEmpty(rqstBody.getFrom()) || StringHelper.isEmpty(rqstBody.getTo())) && StringHelper.isEmpty(rqstBody.getCount()) || (!StringHelper.isEmpty(rqstBody.getFrom()) || !StringHelper.isEmpty(rqstBody.getTo())) && !StringHelper.isEmpty(rqstBody.getCount()))
		{
			throw new BusinessException(WRONG_DATE_COUNT_MESSAGE);
		}
		Long productId;
		Calendar fromDate = null;
		Calendar toDate = null;
		Long countOperations = null;
		try
		{
			productId = Long.valueOf(rqstBody.getId());
		}
		catch (NumberFormatException e)
		{
			response.setStatus(new Status(StatusCode.ACCESS_DENIED, getWrongIdMessage() + rqstBody.getId()));
			return response;
		}
		if (!StringHelper.isEmpty(rqstBody.getCount()))
		{
			try
			{
				countOperations = Long.valueOf(rqstBody.getCount());
				if (countOperations > maxOperationCount)
					countOperations = maxOperationCount;
			}
			catch (NumberFormatException e)
			{
				response.setStatus(new Status(StatusCode.ACCESS_DENIED, "неправильное количество операций: " + rqstBody.getCount()));
				return response;
			}
		}
		else
		{
			try
			{
				fromDate = DateHelper.parseCalendar(rqstBody.getFrom());
			}
			catch (ParseException e)
			{
				response.setStatus(new Status(StatusCode.ACCESS_DENIED, "неправильный формат даты начала периода: " + rqstBody.getFrom()));
				return response;
			}

			try
			{
				toDate = DateHelper.parseCalendar(rqstBody.getTo());
			}
			catch (ParseException e)
			{
				response.setStatus(new Status(StatusCode.ACCESS_DENIED, "неправильный формат даты конца периода: " + rqstBody.getTo()));
				return response;
			}

			if (toDate.before(fromDate))
			{
				response.setStatus(new Status(StatusCode.ERROR, "ƒата начала не может быть больше даты окончани€."));
				return response;
			}
		}

		AbstractBase productAbstract = getProductAbstract(productId, fromDate, toDate, countOperations);

		return fillProductAbstract(productAbstract, response, countOperations == null);
	}

	protected String getWrongIdMessage() {return WRONG_ID_MESSAGE;}

	protected AbstractBase getProductAbstract(Long productId, Calendar from, Calendar to, Long count) throws BusinessException, BusinessLogicException, ExtendedAbstractNotAvailableException
	{
		GetCardAbstractOperation operation = createOperation(GetCardAbstractOperation.class);
		operation.initialize(productId);

		AbstractBase cardAbstract;
		if (count == null)
		{
			if (operation.getCard().getCard().getCardType() == CardType.credit || !operation.isExtendedCardAbstractAvailable())
			{
				throw new ExtendedAbstractNotAvailableException("ѕо данной карте выписка недоступна");
			}

			operation.setDateFrom(from);
			operation.setDateTo(to);
			cardAbstract = operation.getCardAccountAbstract();
		}
		else
		{
			cardAbstract = operation.getCardAbstract(count).get(operation.getCard());
		}
		return cardAbstract;
	}

	private ProductAbstractResponse fillProductAbstract(AbstractBase productAbstract, ProductAbstractResponse response, boolean isExtended) throws BusinessException, SAXException, BusinessLogicException
	{
		List<OperationTag> operations = new ArrayList<OperationTag>();
		for (TransactionBase transaction : productAbstract.getTransactions())
		{
			OperationTag operationTag = new OperationTag();
			operationTag.setDate(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(transaction.getDate()));
			if (transaction.getCreditSum() != null)
			{
				operationTag.setSum(new MoneyTag(transaction.getCreditSum(), "+"));
			}
			else if (transaction.getDebitSum() != null)
			{
				operationTag.setSum(new MoneyTag(transaction.getDebitSum(), "-"));
			}
			operationTag.setDescription(transaction.getDescription());

			if (transaction instanceof AccountTransaction && isExtended)
			{
				AccountTransaction cardAccountTransaction = (AccountTransaction) transaction;

				if (cardAccountTransaction.getDocumentNumber() != null)
					operationTag.setDocNumber(cardAccountTransaction.getDocumentNumber());
				if (cardAccountTransaction.getOperationCode() != null)
					operationTag.setOperationType(cardAccountTransaction.getOperationCode());
				if (cardAccountTransaction.getCounteragentAccount() != null)
					operationTag.setCorrAccount(cardAccountTransaction.getCounteragentAccount());
				if (cardAccountTransaction.getCounteragent() != null)
					operationTag.setCorrespondent(cardAccountTransaction.getCounteragent());
				if (cardAccountTransaction.getBalance() != null)
					operationTag.setBalance(new MoneyTag(cardAccountTransaction.getBalance()));
			}
			operations.add(operationTag);
		}
		response.setOperation(operations);

		if (productAbstract instanceof AccountAbstract && isExtended)
		{
			BalancesTag balancesTag = new BalancesTag();
			if (productAbstract.getOpeningBalance() != null)
				balancesTag.setOpeningBalance(new MoneyTag(productAbstract.getOpeningBalance()));
			if (productAbstract.getClosingBalance() != null)
				balancesTag.setClosingBalance(new MoneyTag(productAbstract.getClosingBalance()));
			response.setBalances(balancesTag);
		}
		return response;
	}
}
