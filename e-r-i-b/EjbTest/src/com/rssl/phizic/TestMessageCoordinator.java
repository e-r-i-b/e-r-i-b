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

		// 1. Заглушка MSS (ЕРМБ)
		// 1.1 ЕРИБ передаёт в MSS обновлённый профиль ЕРМБ
		registerProcessor(UpdateProfilesRq.class, testModule.getUpdateProfileProcessor());
		// 1.2 ЕРИБ передаёт в MSS исходящее СМС клиенту [без проверки IMSI]
		registerProcessor(SendSmsRequest.class, testModule.getTransportSmsProcessor());
		// 1.3 ЕРИБ передаёт в MSS исходящее СМС клиенту с проверкой IMSI
		registerProcessor(SendSmsWithImsiRequest.class, testModule.getTransportSmsProcessor());
		// 1.4 ЕРИБ передаёт в MSS сброс IMSI
		registerProcessor(ResetIMSIRq.class, testModule.getEchoTestProcessor());
		// 1.5 ЕРИБ отвечает MSS на уведомление о списании абон. платы
		registerProcessor(ServiceStatusRes.class, testModule.getEchoTestProcessor());

		// 2. Заглушка TransactSM (Заявка на кредит)
		// 2.1 ЕРИБ передаёт заявку на кредит в TSM
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq.class, testModule.getEsbCreditProcessor());
		registerProcessor(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.ChargeLoanApplicationRq.class, testModule.getEsbCreditProcessor());

		// 3. Заглушка Кредитного Бюро (Отчёт Кредитной Истории)
		// 3.1 ЕРИБ запрашивает КИ из ОКБ
		registerProcessor(EnquiryRequestERIB.class, testModule.getEsbCreditProcessor());

		// 4. Заглушка CRM
		// 4.1 ЕРИБ создаёт заявку на кредит в CRM
		registerProcessor(CRMNewApplRq.class, testModule.getEsbCreditProcessor());
		// 4.2 ЕРИБ передает информацию об изменении статуса заявки на кредит
		registerProcessor(ERIBUpdApplStatusRq.class, testModule.getEsbCreditProcessor());

		//Запрос кредитных заявок (ЕРИБ-ETSM)
		registerProcessor(SearchApplicationRq.class, testModule.getEsbCreditProcessor());
		//Квитанции, в ответ на запрос согласия на оферту
		registerProcessor(OfferTicket.class, testModule.getEsbCreditProcessor());
		//Запрос передачи решения по оферте (ЕРИБ-ETSM)
		registerProcessor(ConsumerProductOfferResultRq.class, testModule.getEsbCreditProcessor());
	}
}
