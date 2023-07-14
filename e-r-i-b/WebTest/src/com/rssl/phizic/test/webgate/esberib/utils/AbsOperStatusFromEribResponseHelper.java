package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;

import java.util.Calendar;
import java.util.Random;
import java.math.BigDecimal;

/**
 * Хелпер для создания ответов при отправке из ЕРИБ запроса на уточнение статуса операции в АБС
 *
 * @ author: Gololobov
 * @ created: 25.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class AbsOperStatusFromEribResponseHelper extends BaseResponseHelper
{
	private static int counter = 0;
	private AccountResponseHelper accountResponseHelper = new AccountResponseHelper();

	/**
	 * Формировние ответа на запрос уточнения статуса операции
	 * @param ifxRq
	 * @return
	 */
	public IFXRs_Type createXferOperStatusInfoRs(IFXRq_Type ifxRq)
	{
		XferOperStatusInfoRq_Type request = ifxRq.getXferOperStatusInfoRq();
		XferOperStatusInfoRs_Type responce = new XferOperStatusInfoRs_Type();
		
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());

		Status_Type status = new Status_Type();
		counter++;
		if (counter % 10 == 5)
		{
			status.setStatusCode(UNKNOW_DOCUMENT_STATE_ERROR_CODE);
			status.setStatusDesc("Тестовая заглушка отказывает каждый 10 запрос");
		}
		else
		{
			status.setStatusCode(0);
			Status_Type statusOriginal = new Status_Type();
			//Для тестирования уточнения стутуса операции
//			statusOriginal.setStatusCode(-10L);
//			statusOriginal.setStatusDesc("Тестовая заглушка отказала документ для которого уточняется статус");

			responce.setStatusOriginalRequest(statusOriginal);

			//Для запроса на открытие вклада переводом с другого вклада
			if (request.getOperName().equals(OperName_Type.TDDO))
			{
				XferOperStatusInfoRs_TypeTDDO tddo = new XferOperStatusInfoRs_TypeTDDO();
				tddo.setAgreemtInfo(getAgreemtDepInfo());

				Calendar dateEndOfSaving = Calendar.getInstance();
				dateEndOfSaving.add(Calendar.DAY_OF_YEAR, 365);
				Random random = new Random();

				DepInfo_Type depInfo = new DepInfo_Type();
				depInfo.setDaysToEndOfSaving(365L);
				depInfo.setDateToEndOfSaving(String.format("%1$td.%1$tm.%1$tY", dateEndOfSaving));
				depInfo.setEarlyTermRate(new IntRate_Type(new BigDecimal(random.nextInt(50))));
				depInfo.setHaveForm190(random.nextInt(10) == 2);
				AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
				agreemtInfo.setDepInfo(depInfo);

				tddo.setAgreemtInfoClose(agreemtInfo);
				tddo.setSrcCurAmt(getRandomDecimal());
				tddo.setDstCurAmt(getRandomDecimal());
				
				responce.setTDDO(tddo);
			}
			//Для запроса на открытие вклада переводом с карты
			else if (request.getOperName().equals(OperName_Type.TCDO))
			{
				XferOperStatusInfoRs_TypeTCDO tcdo = new XferOperStatusInfoRs_TypeTCDO();
				tcdo.setCardAuthorization(getCardAuthorization());
				tcdo.setAgreemtInfo(getAgreemtDepInfo());

				responce.setTCDO(tcdo);
			}
			//Для запроса на открытие ОМС переводом с вклада
			else if (request.getOperName().equals(OperName_Type.TDIO))
			{
				XferOperStatusInfoRs_TypeTDIO tdio = new XferOperStatusInfoRs_TypeTDIO();
				tdio.setAgreemtInfo(getAgreemtIMAInfo());
				responce.setTDIO(tdio);
			}
			//Для запроса на открытие ОМС переводом с карты
			else if (request.getOperName().equals(OperName_Type.TCIO))
			{
				XferOperStatusInfoRs_TypeTCIO tcio = new XferOperStatusInfoRs_TypeTCIO();
				tcio.setCardAuthorization(getCardAuthorization());
				tcio.setAgreemtInfo(getAgreemtIMAInfo());
				responce.setTCIO(tcio);
			}
		}
		responce.setStatus(status);

		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setXferOperStatusInfoRs(responce);
		return ifxRs_type;
	}

	private CardAuthorization_Type getCardAuthorization()
	{
		CardAuthorization_Type cardAuthorization = new CardAuthorization_Type();
		cardAuthorization.setAuthorizationCode(123L);

		return cardAuthorization;
	}

	private AgreemtInfoResponse_Type getAgreemtIMAInfo()
	{
		AgreemtInfoResponse_Type agreemtInfoResponse = new AgreemtInfoResponse_Type();
		IMAInfoResponse_Type imaInfoResponse = new IMAInfoResponse_Type();
		imaInfoResponse.setAcctId(accountResponseHelper.generateNewAccount());
		agreemtInfoResponse.setIMAInfo(imaInfoResponse);
		
		return agreemtInfoResponse;
	}

	private AgreemtInfoResponse_Type getAgreemtDepInfo()
	{
		AgreemtInfoResponse_Type agreemtInfoResponse = new AgreemtInfoResponse_Type();
		DepInfoResponse_Type depInfoResponse = new DepInfoResponse_Type();
		depInfoResponse.setAcctId(accountResponseHelper.generateNewAccount());
		agreemtInfoResponse.setDepInfo(depInfoResponse);

		return agreemtInfoResponse;
	}
}
