package com.rssl.phizic;

import com.rssl.phizic.messaging.MessageCoordinator;
import com.rssl.phizic.messaging.ermb.SendSmsRequest;
import com.rssl.phizic.messaging.ermb.SendSmsWithImsiRequest;
import com.rssl.phizic.synchronization.types.ResetIMSIRq;
import com.rssl.phizic.synchronization.types.ServiceStatusRes;
import com.rssl.phizic.synchronization.types.UpdateProfilesRq;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryRequestERIB;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.CRMNewApplRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBUpdApplStatusRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.ConsumerProductOfferResultRq;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.OfferTicket;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19.SearchApplicationRq;

/**
 * @author Rtischeva
 * @ created 13.12.14
 * @ $Author$
 * @ $Revision$
 */
class TestMessageCoordinator extends MessageCoordinator
{
	TestMessageCoordinator()
	{
		TestModule testModule = moduleManager.getModule(TestModule.class);

		// 1. �������� MSS (����)
		// 1.1 ���� ������� � MSS ���������� ������� ����
		registerProcessor(UpdateProfilesRq.class, testModule.getUpdateProfileProcessor());
		// 1.2 ���� ������� � MSS ��������� ��� ������� [��� �������� IMSI]
		registerProcessor(SendSmsRequest.class, testModule.getTransportSmsProcessor());
		// 1.3 ���� ������� � MSS ��������� ��� ������� � ��������� IMSI
		registerProcessor(SendSmsWithImsiRequest.class, testModule.getTransportSmsProcessor());
		// 1.4 ���� ������� � MSS ����� IMSI
		registerProcessor(ResetIMSIRq.class, testModule.getEchoTestProcessor());
		// 1.5 ���� �������� MSS �� ����������� � �������� ����. �����
		registerProcessor(ServiceStatusRes.class, testModule.getEchoTestProcessor());

		// 2. �������� TransactSM (������ �� ������)
		// 2.1 ���� ������� ������ �� ������ � TSM
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq.class, testModule.getEsbCreditProcessor());
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq.class, testModule.getEsbCreditProcessor());

		// 3. �������� ���������� ���� (����� ��������� �������)
		// 3.1 ���� ����������� �� �� ���
		registerProcessor(EnquiryRequestERIB.class, testModule.getEsbCreditProcessor());

		// 4. �������� CRM
		// 4.1 ���� ������ ������ �� ������ � CRM
		registerProcessor(CRMNewApplRq.class, testModule.getEsbCreditProcessor());
		// 4.2 ���� �������� ���������� �� ��������� ������� ������ �� ������
		registerProcessor(ERIBUpdApplStatusRq.class, testModule.getEsbCreditProcessor());

		//������ ��������� ������ (����-ETSM)
		registerProcessor(SearchApplicationRq.class, testModule.getEsbCreditProcessor());
		//���������, � ����� �� ������ �������� �� ������
		registerProcessor(OfferTicket.class, testModule.getEsbCreditProcessor());
		//������ �������� ������� �� ������ (����-ETSM)
		registerProcessor(ConsumerProductOfferResultRq.class, testModule.getEsbCreditProcessor());
	}
}
