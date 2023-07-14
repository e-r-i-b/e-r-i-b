package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.documents.AbstractTransfer;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * Открытие бессрочного вклада без первоначального взноса
 * @author Pankin
 * @ created 27.06.2011
 * @ $Author$
 * @ $Revision$
 */

public class DemandAccountOpeningClaimSender extends AccountOpeningClaimFromAccountSender
{
	private static final String SEPARATOR = "\\|";

	public DemandAccountOpeningClaimSender(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected boolean needRates(AbstractTransfer transfer)
	{
		return false;
	}

	public Currency getChargeOffCurrency(AbstractTransfer document) throws GateException, GateLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new GateException("Неверный тип документа, должен быть - AccountOpeningClaim.");
		}
		AccountOpeningClaim transfer = (AccountOpeningClaim) document;
		return transfer.getCurrency();
	}

	public IFXRq_Type createRequest(GateDocument document) throws GateException, GateLogicException
	{
		AbstractTransfer transfer = (AbstractTransfer) document;

		DepToNewDepAddRq_Type depToNewRq = new DepToNewDepAddRq_Type();

		depToNewRq.setRqUID(PaymentsRequestHelper.generateUUID());
		depToNewRq.setRqTm(PaymentsRequestHelper.generateRqTm());
		depToNewRq.setOperUID(PaymentsRequestHelper.generateOUUID());
		depToNewRq.setSPName(SPName_Type.BP_ERIB);
		depToNewRq.setBankInfo(paymentsRequestHelper.createAuthBankInfo(transfer.getInternalOwnerId()));
		XferInfo_Type xFerInfo = createBody(transfer);
		depToNewRq.setXferInfo(xFerInfo);
		IFXRq_Type ifxRq = new IFXRq_Type();
		ifxRq.setNewDepAddRq(depToNewRq);
		return ifxRq;
	}

	protected XferInfo_Type createBody(AbstractTransfer transfer) throws GateException, GateLogicException
	{
		XferInfo_Type xFerInfo = new XferInfo_Type();
		xFerInfo.setAgreemtInfo(getAgreementInfo(transfer));
		xFerInfo.setBankInfo(fillBankInfoType(transfer.getInternalOwnerId()));
		setDepAcctIdTo(transfer, xFerInfo);
		return xFerInfo;
	}

	protected String parseAccountNumber(IFXRs_Type ifxRs)
	{
		return ifxRs.getNewDepAddRs().getAgreemtInfo().getDepInfo().getAcctId();
	}

	protected Status_Type getStatusType(IFXRs_Type ifxRs)
	{
		return ifxRs.getNewDepAddRs().getStatus();
	}

	protected String getExternalId(IFXRs_Type ifxRs)
	{
		return ifxRs.getNewDepAddRs().getRqUID();
	}

	/**
	 * Заполнения информации о банке для сообщения TDO о тб, всп, осб карты последнего входа клиента
	 * @param loginId - id логина пользователя
	 * @return
	 * @throws GateLogicException
	 * @throws GateException
	 */
	private BankInfo_Type fillBankInfoType(Long loginId) throws GateLogicException, GateException
	{
		BackRefClientService backRefClientService = getFactory().service(BackRefClientService.class);
		String codeDepartmetns = backRefClientService.getClientDepartmentCode(loginId);

		if (codeDepartmetns == null)
			throw new GateException("Ошибка получения кодовых значений департаментов");

		String[] codes = codeDepartmetns.split(SEPARATOR);

		if (codes == null || codes.length < 4  || codes[2] == null || codes[3] == null)
			throw new GateLogicException("Для открытия Сберегательного счета необходимо войти в систему «Сбербанк Онлайн» по паролю, полученному с использованием основной дебетовой карты.");

		String tbCode = codes[1];
		if (tbCode != null)
			tbCode = StringHelper.appendLeadingZeros(tbCode, 3);
		String osbCode = codes[2];
		String vspCode = codes[3];

		BankInfo_Type bankInfoType = new BankInfo_Type();
		bankInfoType.setBranchId(vspCode);
		bankInfoType.setAgencyId(osbCode);
		bankInfoType.setRegionId(tbCode);

		return bankInfoType;
	}

	protected String getOperUid(IFXRs_Type ifxRs)
	{
		return ifxRs.getNewDepAddRs().getOperUID();
	}

	protected Calendar getOperTime(IFXRs_Type ifxRs)
	{
		return parseCalendar(ifxRs.getNewDepAddRs().getRqTm());
	}
}
