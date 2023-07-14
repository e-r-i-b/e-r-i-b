package com.rssl.phizic.esb.ejb.mock.federal;

import com.rssl.phizic.TestModule;
import com.rssl.phizic.esb.ejb.mock.federal.CardBlockClaim.ESBMockCardBlockProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.LongOffer.ESBMockAccountToAccountBankPaymentLongOffer;
import com.rssl.phizic.esb.ejb.mock.federal.LongOffer.ESBMockRefuseLongOfferProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.StopAccountOperationClaim.ESBMockStopAccountOperationProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.Xfer.ESBMockXferTransferProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.basket.AcceptBillBasketExecuteMessageProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.crm.GetCampaignerInfoMockProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.loan.ESBMockGetPrivateEarlyRepaymentProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.loan.ESBMockGetPrivateLoanDetailProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.loan.ESBMockGetPrivateLoanListProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.loan.ESBMockLoanPaymentProcessor;
import com.rssl.phizic.esb.ejb.mock.federal.p2pcomission.ESBMockP2PAutopayCommissionProcessor;
import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

/**
 * @author bogdanov
 * @ created 01.04.15
 * @ $Author$
 * @ $Revision$
 *
 * Маршрутизатор сообщений в шину
 */
public class ESBMockMessageCoordinator extends MessageCoordinator
{
	ESBMockMessageCoordinator()
	{
		TestModule testModule = moduleManager.getModule(TestModule.class);

		// Заглушка СБНКД
		registerProcessor(GetPrivateClientRq.class, testModule.getSbnkdProcessor());
		registerProcessor(ConcludeEDBORq.class, testModule.getSbnkdProcessor());
		registerProcessor(CreateCardContractRq.class, testModule.getSbnkdProcessor());
		registerProcessor(IssueCardRq.class, testModule.getSbnkdProcessor());
		registerProcessor(CustAddRq.class, testModule.getSbnkdProcessor());
		// блокировка карты
		registerProcessor(CardBlockRq.class, new ESBMockCardBlockProcessor(testModule));
		// утеря сберегательной книжки
		registerProcessor(SetAccountStateRq.class, new ESBMockStopAccountOperationProcessor(testModule));
		// xfer
		registerProcessor(XferAddRq.class, new ESBMockXferTransferProcessor(testModule));
		// создание длительного поручения на перевод денежных средств с вклада физическому лицу
		registerProcessor(SvcAddRq.class, new ESBMockAccountToAccountBankPaymentLongOffer(testModule));
		// отмена ДП
		registerProcessor(SvcAcctDelRq.class, new ESBMockRefuseLongOfferProcessor(testModule));
		//комиссия автоплатежи п2п
		registerProcessor(CalcCardToCardTransferCommissionRq.class, new ESBMockP2PAutopayCommissionProcessor(testModule));

        registerProcessor(GetPrivateLoanListRq.class, new ESBMockGetPrivateLoanListProcessor(testModule));
        registerProcessor(GetPrivateLoanDetailsRq.class,  new ESBMockGetPrivateLoanDetailProcessor(testModule));
        registerProcessor(LoanPaymentRq.class,  new ESBMockLoanPaymentProcessor(testModule));

		// акцепт на оплату инвойса корзины
		registerProcessor(AcceptBillBasketExecuteRq.class,  new AcceptBillBasketExecuteMessageProcessor(testModule));
		//crm
		registerProcessor(GetCampaignerInfoRq.class, new GetCampaignerInfoMockProcessor(testModule));
		// заявка на досрочное погашение кредита
		registerProcessor(RequestPrivateEarlyRepaymentRq.class, new ESBMockGetPrivateEarlyRepaymentProcessor(testModule));
	}
}
