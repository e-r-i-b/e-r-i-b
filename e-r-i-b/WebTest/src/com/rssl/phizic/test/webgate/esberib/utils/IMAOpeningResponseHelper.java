package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.test.webgate.esberib.generated.*;

import java.util.Random;

/**
 * @author Mescheryakova
 * @ created 31.08.2012
 * @ $Author$
 * @ $Revision$
 */

public class IMAOpeningResponseHelper extends BaseResponseHelper
{
	public IFXRs_Type createDepToNewIMAAddRs(IFXRq_Type ifxRq)
	{
		DepToNewIMAAddRq_Type depToNewIMAAddRq =  ifxRq.getDepToNewIMAAddRq();
		DepToNewIMAAddRs_Type depToNewIMAAddRs = new DepToNewIMAAddRs_Type();
		depToNewIMAAddRs.setRqUID(depToNewIMAAddRq.getRqUID());
		depToNewIMAAddRs.setRqTm(depToNewIMAAddRq.getRqTm());
		depToNewIMAAddRs.setOperUID(depToNewIMAAddRq.getOperUID());
		//если это предварительный расчет комиссий - добалвяем микрооперации.
		SrcLayoutInfo_Type srcLayoutInfo_type = depToNewIMAAddRq.getXferInfo().getSrcLayoutInfo();
		int rand = new Random().nextInt(100);
		if (srcLayoutInfo_type != null && srcLayoutInfo_type.getIsCalcOperation() && rand % 10 != 3)
		{
			SrcLayoutInfo_Type srcLayoutInfo_typeRs = new SrcLayoutInfo_Type();
			srcLayoutInfo_typeRs.setWriteDownOperation(PaymentsHelper.generateWriteDownOperations(OperName_Type.TDIO));
			depToNewIMAAddRs.setSrcLayoutInfo(srcLayoutInfo_typeRs);
			depToNewIMAAddRs.setStatus(new Status_Type(-433L, null, null, null));
		}
		else
			depToNewIMAAddRs.setStatus(getStatus());

		if (depToNewIMAAddRs.getStatus().getStatusCode() == 0)
			depToNewIMAAddRs.setAgreemtInfo(getAgreemtInfo());
		AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
		agreemtInfo.setAgreemtType(depToNewIMAAddRq.getXferInfo().getAgreemtInfo().getAgreemtType());
		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setDepToNewIMAAddRs(depToNewIMAAddRs);

		return ifxRs_type;
	}

	public IFXRs_Type createCardToNewIMAAddRs(IFXRq_Type ifxRq)
	{
		CardToNewIMAAddRq_Type cardToNewIMAAddRq =  ifxRq.getCardToNewIMAAddRq();
		CardToNewIMAAddRs_Type cardToNewIMAAddRs = new CardToNewIMAAddRs_Type();

		cardToNewIMAAddRs.setRqUID(cardToNewIMAAddRq.getRqUID());
		cardToNewIMAAddRs.setRqTm(cardToNewIMAAddRq.getRqTm());
		cardToNewIMAAddRs.setOperUID(cardToNewIMAAddRq.getOperUID());
		cardToNewIMAAddRs.setStatus(getStatus());

		if (cardToNewIMAAddRs.getStatus().getStatusCode() == 0)
		{
			CardAuthorization_Type cardAuthorization = new CardAuthorization_Type();
			cardAuthorization.setAuthorizationCode(1L);
			cardToNewIMAAddRs.setCardAuthorization(cardAuthorization);
			cardToNewIMAAddRs.setAgreemtInfo(getAgreemtInfo());
		}

		AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();
		agreemtInfo.setAgreemtType(cardToNewIMAAddRq.getXferInfo().getAgreemtInfo().getAgreemtType());
		IFXRs_Type ifxRs_type = new IFXRs_Type();
		ifxRs_type.setCardToNewIMAAddRs(cardToNewIMAAddRs);

		return ifxRs_type;
	}

	private com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type getAgreemtInfo()
	{
		com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type agreemtInfoResponse = new  com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfoResponse_Type();
		agreemtInfoResponse.setIMAInfo(getIMAInfoResponse());
		return agreemtInfoResponse;
	}

	private com.rssl.phizic.test.webgate.esberib.generated.IMAInfoResponse_Type getIMAInfoResponse()
	{
		IMAInfoResponse_Type imaInfoResponse = new IMAInfoResponse_Type();
		imaInfoResponse.setAcctId("00099930393");
		return imaInfoResponse;
	}
}
