package com.rssl.phizicgate.esberibgate.pfr;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.claims.pfr.PFRStatementClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.pfr.StatementStatus;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.AbstractOfflineClaimSenderBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * @author gulov
 * @ created 17.02.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * Сендер для отправки запроса выписки лицевого счета клиента в ПФР
 */
public class PFRSender extends AbstractOfflineClaimSenderBase
{
	private static final String PASSPORT_TYPE = "300";

	public static final int STATUS_CODE_0 = 0;

	public static final int STATUS_CODE_801 = -801;

	public static final String STATUS_DESC_801 = "Вы не зарегистрированы в системе информационного обмена Пенсионного фонда. " +
		"Для получения выписки необходимо подать заявление в любом подразделении Сбербанка России.";

	public static final int STATUS_CODE_802 = -802;

	public static final String STATUS_DESC_802 = "Вы указали некорректный страховой номер индивидуального лицевого счета";

	public static final int STATUS_CODE_803 = -803;

	public static final String STATUS_DESC_803 = "Есть клиенты с указанным ФИО, но среди них нет ни одного c " +
		"указанной серией и номером паспорта (документа)";

	public static final int STATUS_CODE_804 = -804;

	public static final String STATUS_DESC_804 = "Вы не зарегистрированы в системе информационного обмена Пенсионного фонда. " +
		"Для получения выписки необходимо подать заявление в любом подразделении Сбербанка России.";

	public static final int STATUS_CODE_810 = -810;

	public static final String STATUS_DESC_810 = "На ваш запрос получен отказ из Пенсионного Фонда. %s";

	public static final String NOT_SPECIFIC_ERROR = "Неизвестная ошибка при отправке запроса выписки лицевого счета клиента в ПФР";

	public PFRSender(GateFactory factory)
	{
		super(factory);
	}

	protected void processResponse(GateDocument document, IFXRs_Type response) throws GateException, GateLogicException
	{
		PFRStatementClaim claim = (PFRStatementClaim) document;
		PfrHasInfoInqRs_Type hasInfo = response.getPfrHasInfoInqRs();

		switch ((int) hasInfo.getStatus().getStatusCode())
		{
			case STATUS_CODE_0:
			{
				claim.setReady(hasInfo.getResult() != null && hasInfo.getResult().isResponseExists() ?
					StatementStatus.AVAILABLE : StatementStatus.NOT_YET_AVAILABLE);

				return;
			}
			case STATUS_CODE_801:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(STATUS_DESC_801);

				return;
			}
			case STATUS_CODE_802:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(STATUS_DESC_802);

				return;
			}
			case STATUS_CODE_803:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_INFO_REQUIRED);
				claim.setReadyDescription(STATUS_DESC_803);

				return;
			}
			case STATUS_CODE_804:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_UNKNOWN_PERSON);
				claim.setReadyDescription(STATUS_DESC_804);

				return;
			}
			case STATUS_CODE_810:
			{
				claim.setReadyDescription(String.format(STATUS_DESC_810, hasInfo.getStatus().getStatusDesc()));
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_FAIL);

				return;
			}
			default:
			{
				claim.setReady(StatementStatus.UNAVAILABLE_DUE_FAIL);
				claim.setReadyDescription(NOT_SPECIFIC_ERROR);

				return;
			}
		}
	}

	protected IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		if (!(document instanceof PFRStatementClaim))
		{
			throw new GateException("Ожидается PFRStatementClaim");
		}

		PFRStatementClaim claim = (PFRStatementClaim) document;
		PaymentsRequestHelper paymentsRequestHelper = new PaymentsRequestHelper(GateSingleton.getFactory());

		boolean wayExists = !StringHelper.isEmpty(claim.getDocSeries());
		PfrHasInfoInqRq_Type type = createRequest(wayExists);

		type.setRqUID(PaymentsRequestHelper.generateUUID());
	    type.setRqTm(PaymentsRequestHelper.generateRqTm());
	    type.setOperUID(PaymentsRequestHelper.generateOUUID());
		type.setSPName(SPName_Type.BP_ERIB);
	    type.setBankInfo(paymentsRequestHelper.createAuthBankInfo(claim.getInternalOwnerId()));

		claim.setExternalId(type.getRqUID());

		PersonInfo_Type personInfo = type.getCustInfo().getPersonInfo();

		personInfo.getPersonName().setLastName(claim.getSurName());
		personInfo.getPersonName().setFirstName(claim.getFirstName());
		personInfo.getPersonName().setMiddleName(claim.getPatrName());

		if (wayExists)
		{
			personInfo.getIdentityCard().setIdType(PASSPORT_TYPE);
			personInfo.getIdentityCard().setIdNum(claim.getDocSeries().replace(" ",""));
		}
		else
			personInfo.setPFRAccount(claim.getSNILS());

		IFXRq_Type result = new IFXRq_Type();

		result.setPfrHasInfoInqRq(type);

		return result;
	}

	private PfrHasInfoInqRq_Type createRequest(boolean wayExists)
	{
		PfrHasInfoInqRq_Type result = new PfrHasInfoInqRq_Type();

		PersonInfo_Type personInfo = new PersonInfo_Type();
		CustInfo_Type custInfo = new CustInfo_Type();
		PersonName_Type personName = new PersonName_Type();
		personInfo.setPersonName(personName);
		if (wayExists)
		{
			IdentityCard_Type identityCard = new IdentityCard_Type();
			personInfo.setIdentityCard(identityCard);
		}
		result.setCustInfo(custInfo);
		result.getCustInfo().setPersonInfo(personInfo);

		return result;
	}

}
