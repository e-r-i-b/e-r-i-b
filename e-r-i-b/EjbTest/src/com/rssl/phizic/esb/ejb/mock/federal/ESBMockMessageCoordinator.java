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
 * ������������� ��������� � ����
 */
public class ESBMockMessageCoordinator extends MessageCoordinator
{
	ESBMockMessageCoordinator()
	{
		TestModule testModule = moduleManager.getModule(TestModule.class);

		// �������� �����
		registerProcessor(GetPrivateClientRq.class, testModule.getSbnkdProcessor());
		registerProcessor(ConcludeEDBORq.class, testModule.getSbnkdProcessor());
		registerProcessor(CreateCardContractRq.class, testModule.getSbnkdProcessor());
		registerProcessor(IssueCardRq.class, testModule.getSbnkdProcessor());
		registerProcessor(CustAddRq.class, testModule.getSbnkdProcessor());
		// ���������� �����
		registerProcessor(CardBlockRq.class, new ESBMockCardBlockProcessor(testModule));
		// ����� �������������� ������
		registerProcessor(SetAccountStateRq.class, new ESBMockStopAccountOperationProcessor(testModule));
		// xfer
		registerProcessor(XferAddRq.class, new ESBMockXferTransferProcessor(testModule));
		// �������� ����������� ��������� �� ������� �������� ������� � ������ ����������� ����
		registerProcessor(SvcAddRq.class, new ESBMockAccountToAccountBankPaymentLongOffer(testModule));
		// ������ ��
		registerProcessor(SvcAcctDelRq.class, new ESBMockRefuseLongOfferProcessor(testModule));
		//�������� ����������� �2�
		registerProcessor(CalcCardToCardTransferCommissionRq.class, new ESBMockP2PAutopayCommissionProcessor(testModule));

        registerProcessor(GetPrivateLoanListRq.class, new ESBMockGetPrivateLoanListProcessor(testModule));
        registerProcessor(GetPrivateLoanDetailsRq.class,  new ESBMockGetPrivateLoanDetailProcessor(testModule));
        registerProcessor(LoanPaymentRq.class,  new ESBMockLoanPaymentProcessor(testModule));

		// ������ �� ������ ������� �������
		registerProcessor(AcceptBillBasketExecuteRq.class,  new AcceptBillBasketExecuteMessageProcessor(testModule));
		//crm
		registerProcessor(GetCampaignerInfoRq.class, new GetCampaignerInfoMockProcessor(testModule));
		// ������ �� ��������� ��������� �������
		registerProcessor(RequestPrivateEarlyRepaymentRq.class, new ESBMockGetPrivateEarlyRepaymentProcessor(testModule));
	}
}
