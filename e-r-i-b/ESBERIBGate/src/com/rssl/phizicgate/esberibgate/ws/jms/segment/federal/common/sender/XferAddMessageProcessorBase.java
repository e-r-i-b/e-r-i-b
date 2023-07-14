package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.common.sender;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.CurrencyRate;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.ESBSegment;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessor;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.OnlineMessageProcessorBase;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Request;
import com.rssl.phizicgate.esberibgate.ws.jms.common.message.Response;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.ExternalIdGenerator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.RequestHelper;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author akrenev
 * @ created 02.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * Базовый процессор для XferAdd
 */

public abstract class XferAddMessageProcessorBase<D extends AbstractTransfer> extends OnlineMessageProcessorBase<XferAddRs>
{
	protected static final String OK_CODE = "0";
	private static final String UNKNOW_STATE_ERROR_CODE = "-105";
	private static final String COMMISSION_ERROR_CODE = "-433";
	private static final String DISCOUNT_COMMISSION_PREFIX = "для ТП ";

	private static final Set<OperNameType> availableOperNameTypes = new HashSet<OperNameType>();

	private static final String REQUEST_TYPE = XferAddRq.class.getSimpleName();

	private final BackRefInfoRequestHelper requestHelper;
	private final D document;
	private XferAddRq request;

	static
	{
		availableOperNameTypes.add(OperNameType.TDC);
		availableOperNameTypes.add(OperNameType.TCD);
		availableOperNameTypes.add(OperNameType.TDD);
		availableOperNameTypes.add(OperNameType.TCP);
	}

	/**
	 * конструктор
	 * @param factory гейтовая фабрика
	 * @param document документ
	 * @param serviceName имя сервиса
	 */
	protected XferAddMessageProcessorBase(GateFactory factory, D document, String serviceName)
	{
		super(ESBSegment.federal, serviceName);
		requestHelper = new BackRefInfoRequestHelper(factory);
		this.document = document;
	}

	protected D getDocument()
	{
		return document;
	}

	protected abstract OperNameType getOperationName(D document);

	protected abstract XferInfo createBody(D document) throws GateException, GateLogicException;

	protected abstract void updateDocumentRequestData(XferAddRq request, D document);

	@Override
	protected String getRequestMessageType()
	{
		return REQUEST_TYPE;
	}

	@Override
	protected String getRequestId()
	{
		return request.getRqUID();
	}

	@Override
	protected String getRequestSystemId()
	{
		return ExternalSystemHelper.getESBSystemCode();
	}

	@Override
	protected Object initialize() throws GateException, GateLogicException
	{
		request = buildRequestObject(document);

		updateDocumentRequestData(request, document);
		document.setExternalId(ExternalIdGenerator.generateExternalId(request));

		return request;
	}

	@Override
	protected String getResponseId(XferAddRs response)
	{
		return response.getRqUID();
	}

	@Override
	protected String getResponseErrorCode(XferAddRs response)
	{
		return String.valueOf(response.getStatus().getStatusCode());
	}

	@Override
	protected String getResponseErrorMessage(XferAddRs response)
	{
		return response.getStatus().getStatusDesc();
	}

	protected BackRefInfoRequestHelper getRequestHelper()
	{
		return requestHelper;
	}

	@Override
	protected void processResponse(Request<OnlineMessageProcessor<XferAddRs>> request, Response<XferAddRs> response) throws GateException, GateLogicException
	{
		if (OK_CODE.equals(response.getErrorCode()))
			return;

		//таймаут
		if (UNKNOW_STATE_ERROR_CODE.equals(response.getErrorCode()))
			processUnknown(request, response, document);

		if(COMMISSION_ERROR_CODE.equals(response.getErrorCode()) && requestHelper.isCalcCommissionSupport(document))
		{
			//это рассчет комиссии.
			fillCommissions(document, response.getResponse());
			throw new PostConfirmCalcCommission();
		}

		processError(request, response);
	}

	private XferAddRq buildRequestObject(D document) throws GateException, GateLogicException
	{
		XferAddRq xferAddRq = new XferAddRq();

		xferAddRq.setRqUID(RequestHelper.generateUUID());
		xferAddRq.setRqTm(RequestHelper.generateRqTm());
		xferAddRq.setOperUID(RequestHelper.generateOUUID());
		xferAddRq.setSPName(SPNameType.BP_ERIB);
		xferAddRq.setOperName(getOperationName(document));
		BankInfoType bankInfo = RequestHelper.makeBankInfo(requestHelper.getRbTbBrch(document.getInternalOwnerId()));
		xferAddRq.setBankInfo(bankInfo);
		XferInfo xferInfo = createBody(document);
		fillCommissionWriteDownOperation(document, xferInfo);
		fillAmount(document, xferInfo);
		xferAddRq.setXferInfo(xferInfo);

		return xferAddRq;
	}

	protected CurAmtConv getCurAmtConvType(AbstractTransfer transfer)  throws GateException, GateLogicException
	{
		CurrencyRate debetSaleRate  = transfer.getDebetSaleRate();
		CurrencyRate debetBuyRate   = transfer.getDebetBuyRate();
		CurrencyRate creditSale = transfer.getCreditSaleRate();
		CurrencyRate creditBuy  = transfer.getCreditBuyRate();

		CurAmtConv curAmtConv = new CurAmtConv();
		//курсы передаем в истинном масштабе, а не 1.
		curAmtConv.setDebetSale(debetSaleRate != null ? debetSaleRate.getToValue() : null);
		curAmtConv.setDebetBuy(debetBuyRate != null ? debetBuyRate.getToValue() : null);
		curAmtConv.setCreditSale(creditSale != null ? creditSale.getToValue() : null);
		curAmtConv.setCreditBuy(creditBuy != null ? creditBuy.getToValue() : null);
		return curAmtConv;
	}

	/**
	 * Добавление в тело запроса информации о микрооперациях(комиссиях)
	 * @param document данные платежа
	 * @param xferInfo запрос
	 */
	private void fillCommissionWriteDownOperation(D document, XferInfo xferInfo) throws GateException
	{
		//если подразделение не поддерживает расчет комиссий в цод - ничего не заполняем.
		if(!requestHelper.isCalcCommissionSupport(document))
			return;

		xferInfo.setSrcLayoutInfo(getSrcLayoutInfo(document));
	}

	private void fillAmount(AbstractTransfer transfer, XferInfo xferInfo) throws GateException
	{
		Money amount = getAmount(transfer);

		if (amount == null)
			throw new GateException("не задана сумма платежа");

		xferInfo.setCurAmt(amount.getDecimal());
		xferInfo.setAcctCur(amount.getCurrency().getCode());
	}

	private Money getAmount(AbstractTransfer transfer) throws GateException
	{
		switch (transfer.getInputSumType())
		{
			case CHARGEOFF: return transfer.getChargeOffAmount();
			case DESTINATION: return transfer.getDestinationAmount();
			default: throw new GateException("не задан тип суммы платежа");
		}
	}

	private SrcLayoutInfoType getSrcLayoutInfo(GateDocument transfer) throws GateException
	{
		SrcLayoutInfoType scrLayoutInfo = new SrcLayoutInfoType();

		List<WriteDownOperation> writeDownOperations = transfer.getWriteDownOperations();
		scrLayoutInfo.setIsCalcOperation(writeDownOperations == null || writeDownOperations.isEmpty());
		if (CollectionUtils.isNotEmpty(writeDownOperations))
		{
			List<WriteDownOperationType> operations = scrLayoutInfo.getWriteDownOperations();
			for(WriteDownOperation op: writeDownOperations)
			{
				WriteDownOperationType operation = new WriteDownOperationType();
				operation.setCurAmt(op.getCurAmount().getDecimal());
				operation.setOperationName(op.getOperationName());
				operation.setTurnover(TurnoverType.valueOf(op.getTurnOver()));
				operations.add(operation);
			}
		}
		return scrLayoutInfo;
	}

	protected abstract Currency getChargeOffCurrency(AbstractTransfer transfer) throws GateException, GateLogicException;

	protected void processUnknown(Request request, Response<XferAddRs> response, D document) throws GateLogicException, GateException
	{
		processTimeout(request, response);
	}

	private void fillCommissions(D document, XferAddRs xferAddRs) throws GateLogicException, GateException
	{
		SrcLayoutInfoType srcLayoutInfo = xferAddRs.getSrcLayoutInfo();
		List<WriteDownOperation> result = new ArrayList<WriteDownOperation>();
		OperNameType operationNameType = getOperationName(document);

		if (srcLayoutInfo != null)
		{
			List<WriteDownOperationType> commissions = srcLayoutInfo.getWriteDownOperations();
			if (commissions != null)
			{
				Currency commissionCurrency = getChargeOffCurrency(document);
				for (WriteDownOperationType microOperation : commissions)
				{
					WriteDownOperation op = new WriteDownOperation();
					String operationName = microOperation.getOperationName();
					op.setOperationName(operationName);
					op.setCurAmount(new Money(microOperation.getCurAmt(), commissionCurrency));
					op.setTurnOver(microOperation.getTurnover().value());
					result.add(op);
					if (StringHelper.isNotEmpty(operationName) && operationName.contains(DISCOUNT_COMMISSION_PREFIX) && availableOperNameTypes.contains(operationNameType))
					{
						int prefixIndex = operationName.indexOf(DISCOUNT_COMMISSION_PREFIX);
						document.setTariffPlanESB(operationName.substring(prefixIndex + DISCOUNT_COMMISSION_PREFIX.length()));
					}
				}
			}
		}
		document.setWriteDownOperations(result);
		document.setCommission(WriteDownOperationHelper.getCommissionsSum(result));
	}
}

