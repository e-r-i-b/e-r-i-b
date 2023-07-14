package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.GateTimeOutException;
import com.rssl.phizic.gate.exceptions.PostConfirmCalcCommission;
import com.rssl.phizic.gate.longoffer.LongOffer;
import com.rssl.phizic.gate.longoffer.SumType;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.messaging.BackRefInfoRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author krenev
 * @ created 16.07.2010
 * @ $Author$
 * @ $Revision$
 * ������� ������ ��� �������� ����(������ XferAddRq)
 * ������ ����� �������� � 2 �������: ���������� ��������� � ������� ������
 * ������������ ���������� is-long-offer-mode
 */
public abstract class PaymentSenderBase extends DocumentSenderBase
{
	protected static final String REMAIND_IN_RECIP_MESSAGE = "������ ���������� ����� ������� ������ ��� �������� ������ ������ ���������������� ����� ���������. " +
			"����������, �������� ������ ��� ����� ��� ������� ������ ���� ����������.";
	protected static final String OVER_DRAFT_MESSAGE = "������ ���������� ����� ������� ������ ��� �������� ������ ������ ���������������� ����� ���������. " +
			"����������, �������� ������ ���� ����������.";
	public static final String IS_LONG_OFFER_MODE_PARAMETER_NAME = "is-long-offer-mode";
	private static Map<String, String> assignee = new HashMap<String, String>();    //��������������

	static
	{
		TBSAXSource tbsaxSource = new TBSAXSource();
		assignee  = tbsaxSource.getSource();
	}

	private boolean isLongOfferMode;

	public PaymentSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	public void setParameters(Map<String, ?> params)
	{
		Object parameterValue = params.get(IS_LONG_OFFER_MODE_PARAMETER_NAME);
		isLongOfferMode = (parameterValue != null) && Boolean.valueOf((String) parameterValue);
		super.setParameters(params);
	}

	/**
	 * @return ������� ����, ��� ������ �������� � ������ ����������� ���������
	 */

	public boolean isLongOfferMode()
	{
		return isLongOfferMode;
	}

	/**
	 * ������������ ������ �� ���������� ���������.
	 * ��� �������� ��� ������ XferAddRq.
	 * ����������� ��������� � ����� ����.
	 * @param document ������ � ���������
	 * @return ������
	 */
	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractTransfer, � ������ " + document.getClass());
		}

		AbstractTransfer transfer = (AbstractTransfer) document;

		XferAddRq_Type xferAddRq = new XferAddRq_Type();
//��������� �������� ����
		xferAddRq.setRqUID(PaymentsRequestHelper.generateUUID());
		xferAddRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		xferAddRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		xferAddRq.setSPName(SPName_Type.BP_ERIB);
		xferAddRq.setOperName(getOperationName(transfer));
		xferAddRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
//�������� ���� ���������
		XferInfo_Type xferInfo = createBody(transfer);
//��������� �������� �������������
		fillCommissionWriteDownOperation(transfer, xferInfo);
//�������� ����� ��� �������������
		fillAmount(transfer, xferInfo);
		xferAddRq.setXferInfo(xferInfo);
//��������� ����� �� ���������� �� ��� �������������
		if (isLongOfferMode)
		{
			xferAddRq.setRegular(paymentsRequestHelper.getRegular((LongOffer) transfer));
		}
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setXferAddRq(xferAddRq);
		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		Status_Type statusType = ifxRs.getXferAddRs().getStatus();
		long statusCode = statusType.getStatusCode();
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		//�������
		if (statusCode == UNKNOW_DOCUMENT_STATE_ERROR_CODE)
		{
			throw new GateTimeOutException(statusType.getStatusDesc());
		}
		if(statusCode == -433 && commissionTBSerivice.isCalcCommissionSupport(document))
		{
			//��� ������� ��������.
			fillCommissions(document, ifxRs);
			throw new PostConfirmCalcCommission();
		}
		if (statusCode != 0)
		{
			//��� ������ ����������������. ���� �������� ������ �� ������, �� ������ ��������� �� ���������
			throwGateLogicException(statusType, XferAddRs_Type.class);
		}
		if (document instanceof SynchronizableDocument)
		{

			SynchronizableDocument synchronizableDocument = (SynchronizableDocument) document;
			synchronizableDocument.setExternalId(ifxRs.getXferAddRs().getRqUID());
		}
	}

	/**
	 * ���������� � ���� ������� ���������� � ��������������(���������)
	 * @param transfer ������ �������
	 * @param xferInfo ������
	 */
	protected void fillCommissionWriteDownOperation(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		//���� ������������� �� ������������ ������ �������� � ��� - ������ �� ���������.
		if(!getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(transfer))
			return;

		xferInfo.setSrcLayoutInfo(getSrcLayoutInfo(transfer));
	}

	protected SrcLayoutInfo_Type getSrcLayoutInfo(AbstractTransfer transfer) throws GateException
	{
		SrcLayoutInfo_Type scrLayoutInfo = new SrcLayoutInfo_Type();

		List<WriteDownOperation> writeDownOperations = transfer.getWriteDownOperations();
		scrLayoutInfo.setIsCalcOperation(writeDownOperations == null || writeDownOperations.isEmpty());
		if (writeDownOperations != null && !writeDownOperations.isEmpty())
		{
			WriteDownOperation_Type[] operations = new WriteDownOperation_Type[writeDownOperations.size()];
			int i = 0;
			for(WriteDownOperation op: writeDownOperations)
			{
				WriteDownOperation_Type operation = new WriteDownOperation_Type();
				operation.setCurAmt(op.getCurAmount().getDecimal());
				operation.setOperationName(op.getOperationName());
				operation.setTurnover(Turnover_Type.fromString(op.getTurnOver()));
				operations[i] = operation; i++;
			}
			scrLayoutInfo.setWriteDownOperation(operations);
		}
		return scrLayoutInfo;
	}

	/**
	 * ���������� ������� ������� ������������� ��������(��������)
	 * @param document - ������
	 * @param ifxRs - �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected void fillCommissions(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * ������� ����� ��������� ������������� �������� �� ������
	 * @param ifxRs - �����
	 * @return ������������ ��������.
	 */
	protected SrcLayoutInfo_Type getSrcLayoutInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getXferAddRs().getSrcLayoutInfo();
	}

	/**
	 * ��������� ����� � �������. � ���������� ����������� ��(� ���������������� ��� ������):
	 * ����� ����������� ��� ���������� �������, � ����� ��� �������� ����������� ���������
	 * � ������ � ���� <SummaKindCode>:
	 *      1)FIXED_SUMMA,
	 *      2)REMAIND_OVER_SUMMA
	 *      3)FIXED_SUMMA_IN_RECIP_CURR
	 * @param transfer ������ �������
	 * @param xferInfo ������
	 * @throws GateException
	 */
	protected void fillAmount(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		Money amount = null;
		if (isLongOfferMode)
		{
			LongOffer longOffer = (LongOffer) transfer;
			SumType sumType = longOffer.getSumType();
			if (sumType != SumType.FIXED_SUMMA && sumType != SumType.REMAIND_OVER_SUMMA && sumType != SumType.FIXED_SUMMA_IN_RECIP_CURR)
			{
				//����������.
				return;
			}
			amount = longOffer.getAmount();
		}
		else
		{
			if(transfer.getInputSumType() == InputSumType.CHARGEOFF)
				amount = transfer.getChargeOffAmount();
			else if (transfer.getInputSumType() == InputSumType.DESTINATION)
				amount = transfer.getDestinationAmount();
			else
				throw new GateException("�� ����� ��� ����� �������");
		}
		if (amount == null)
			throw new GateException("�� ������ ����� �������");

		fillAmount(xferInfo, amount);
	}

	/**
	 * ��������� � ��������� �����(�� ��������� ������������ � ���� CurAmt � AcctCur)
	 * ���������� ����� �������� ����� ��������� �����
	 * @param xferInfo ���������
	 * @param amount �����, �� ����� ���� null
	 */
	protected void fillAmount(XferInfo_Type xferInfo, Money amount)
	{
		xferInfo.setCurAmt(amount.getDecimal());
		xferInfo.setAcctCur(amount.getCurrency().getCode());
	}

	public void validate(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof AbstractTransfer))
		{
			throw new GateException("�������� ��� ���������, ������ ���� - AbstractTransfer, � ������ " + document.getClass());
		}

		AbstractTransfer transfer = (AbstractTransfer) document;
		//��������� ���������� ������ ������� � ���� ���������
		if (isLongOfferMode ^ LongOffer.class.isAssignableFrom(document.getType()))
		{
			throw new GateException("������������ ��� ��������� � ������ ����������:\n ��� ��������� =" + document.getType() + "; isLongOfferMode =" + isLongOfferMode);
		}
		if (!isLongOfferMode)
		{
			return;
		}
		//��������, ��� ����� �������: ���� �����������, ���� <SummaKindCode> = PERCENT_OF_REMAIND.
		LongOffer longOffer = (LongOffer) document;
		if (longOffer.getSumType() == SumType.PERCENT_OF_REMAIND && longOffer.getPercent() == null)
		{
			//���-�� ����� � ������� - ������
			throw new GateException(" �� ����������� ������� �� Percent : ���� �����������, ���� <SummaKindCode> = PERCENT_OF_REMAIND");
		}
		//���������, ��� ������ �����:����� ����������� ��� ���������� �������, � ����� ��� �������� ����������� ��������� � ������
		//1)FIXED_SUMMA,
		//2)REMAIND_OVER_SUMMA
		//3)FIXED_SUMMA_IN_RECIP_CURR
		//� ���� <SummaKindCode>.
		SumType sumType = longOffer.getSumType();
		if ((sumType == SumType.FIXED_SUMMA || sumType == SumType.REMAIND_OVER_SUMMA||sumType == SumType.FIXED_SUMMA_IN_RECIP_CURR) && longOffer.getAmount() == null)
		{
			//���-�� ����� � ������� - ������
			throw new GateException("����� ���������� ��� ���� ����� :" + sumType);
		}
	}

	/**
	 * ��������� ���� ��������� ��� �����
	 * @param document ��������
	 * @return ���� ���������
	 */
	protected abstract XferInfo_Type createBody(AbstractTransfer document) throws GateException, GateLogicException;

	/**
	 * �������� ��� ��������
	 * @param document ��������
	 * @return ��� ��������
	 */
	protected abstract OperName_Type getOperationName(AbstractTransfer document);

	/**
	 * ��������� ��������� ���������� �� �����
	 * @param card �����
	 * @param owner ��������
	 * @param expireDate ���� ��������
	 * @return CardAcctId_Type
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public CardAcctId_Type createCardAcctId(Card card, Client owner, Calendar expireDate) throws GateLogicException, GateException
	{
		CardAcctId_Type cardAcctId = super.createCardAcctId(card, owner, expireDate);
		if (isLongOfferMode)
		{
			String primaryAccountNumber = null;
			try {
				Account account = GroupResultHelper.getOneResult(getFactory().service(BankrollService.class).getCardPrimaryAccount(card));
				primaryAccountNumber = account.getNumber();
			}
			catch (SystemException e)
			{
				throw new GateException(e);
			}
			catch (LogicException e)
			{
				throw new GateLogicException(e);
			}
			if (StringHelper.isEmpty(primaryAccountNumber))
			{
				// ��� ���������� ��������� ����������� �������� ���
				GateLogicException e = new GateLogicException("�� ������ ����� ���������� ��������� ��������. ����������, �������� ������ �����");
				Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
				log.error("�� ������ ��� ��� ����� � " + card.getNumber(),e);
				throw e;
			}

			cardAcctId.setAcctId(primaryAccountNumber);
		}
		return cardAcctId;
	}

	/**
	 * ��������� ������������ �� ���� ��������� �������� � ���� ���������� 1 ��.
	 * @param office ���� ��������� ��������
	 * @param receiverAccount ���� ����������. �����: ������������ ������ ������ ���������� �����
	 * �� ����� ������� ����������� ��������� �������� �� ���� ����������� ���������� �����
	 * @return true - �����������
	 */
	public static boolean isSameTB(Office office, String receiverAccount) throws GateException
	{
		return BackRefInfoRequestHelper.isSameTB(office, receiverAccount);
	}

	@Override
	public void prepare(GateDocument document) throws GateException, GateLogicException {}
}
