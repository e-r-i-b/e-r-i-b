package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.commission.WriteDownOperation;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.ClientAccountsTransfer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRq_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.IFXRs_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.SrcLayoutInfo_Type;
import com.rssl.phizicgate.esberibgate.ws.generated.XferInfo_Type;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;

import static org.apache.commons.lang.StringUtils.strip;

/**
 * @author Balovtsev
 * @created 10.02.2011
 * @ $Author$
 * @ $Revision$
 */

public class AccountClosingPaymentSender extends AccountClosingPaymentSenderBase
{
	public AccountClosingPaymentSender(GateFactory factory) throws GateException
	{
		super(factory);
	}
	
	protected void appendCommonBody(AbstractTransfer document, XferInfo_Type xferInfo, boolean execute) throws GateException, GateLogicException
	{
		if (!execute)
		{
			Money amount = document.getChargeOffAmount();
			/*
			 *	Ќа первом этапе или при предварительном расчете всегда 0
			 */
			Money copyOfAmount = new Money(BigDecimal.ZERO, amount.getCurrency());
			super.fillAmount(xferInfo, copyOfAmount);
		}
		else
		{
			fillAmount(document, xferInfo);
		}

		xferInfo.setExecute(execute);
		xferInfo.setPurpose(document.getGround());
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return !StringHelper.isEmpty(((ClientAccountsTransfer) transfer).getReceiverAccount());
	}

	protected IFXRq_Type createPrepareRequest(GateDocument document) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = createRequest(document);
		appendCommonBody((AbstractTransfer)document, ifxRq.getXferAddRq().getXferInfo(), false);
		return ifxRq;
	}

	protected void processResponse(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		super.processResponse(document, ifxRs);
		fillAmounts(document, ifxRs);
	}

	protected void fillAmounts(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException
	{
		ClientAccountsTransfer transfer = (ClientAccountsTransfer) document;

		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		BigDecimal srcCurAmt;
		SrcLayoutInfo_Type srcLayoutInfo_type = getSrcLayoutInfo(ifxRs);
		//в случае xBank и —офи€, если StatusCode=0, то тег SrcCurAmt возвращаетс€ всегда; в случае ÷ќƒ, SrcCurAmt возвращаетс€ только в случае, если StatusCode=0 и не приходит SrcLayoutInfo.
		/*SrcCurAmt подсчитываем по следующему алгоритму:
		  a). в случае TDDC : сумму всех микроопераций с caption='«акрытие счета'
		  b). в случае TDCC : сумму всех микроопераций с caption='«акрытие счета'
		  ...
		 */
		if (commissionTBSerivice.isCalcCommissionSupport(document) && srcLayoutInfo_type != null)
		{
			srcCurAmt = getSrcAmountFromSrcLayoutInfo(srcLayoutInfo_type);
		}
		else
			srcCurAmt = ifxRs.getXferAddRs().getSrcCurAmt();

		BigDecimal dstCurAmt = ifxRs.getXferAddRs().getDstCurAmt();
		Money chargeOffAmount = srcCurAmt == null ? null :
				new Money(srcCurAmt, transfer.getChargeOffAmount().getCurrency());
		transfer.setChargeOffAmount(chargeOffAmount);
		/*
		 *  ≈сли счет зачислени€ null значит пытаемс€ закрыть вклад с нулевым остатком
		 */
		Money destinationAmount = transfer.getDestinationAmount();
		if ( !StringHelper.isEmpty(transfer.getReceiverAccount()) && dstCurAmt != null)
		{
			transfer.setDestinationAmount( new Money(dstCurAmt, destinationAmount.getCurrency()) );
		}
	}

	protected void fillAmount(AbstractTransfer transfer, XferInfo_Type xferInfo) throws GateException
	{
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		if (commissionTBSerivice.isCalcCommissionSupport(transfer))
		{
			/*
			CurAmt в запросе на исполнение нужно формировать также как и при выключенном режиме отображени€ комиссий, а именно:
			...
			b) в случае TDDC, TDCC передаем сумму в валюте списани€ (как без расчета комиссий)-полученную из микроопераций по алгоритму подсчета SrcCurAmt(сумму всех микроопераций с caption='«акрытие счета')
			*/
			fillAmount(xferInfo, transfer.getChargeOffAmount());
			return;
		}
		super.fillAmount(transfer, xferInfo);
	}

}
