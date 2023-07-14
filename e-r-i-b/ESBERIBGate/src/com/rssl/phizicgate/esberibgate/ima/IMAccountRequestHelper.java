package com.rssl.phizicgate.esberibgate.ima;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;

import java.util.Calendar;

/**
 * @author Balovtsev
 * @created 15.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountRequestHelper extends ClientRequestHelperBase
{

	public IMAccountRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Составление запроса для получения списка ОМС клиента
	 * @param client - владелец ОМС
	 * @param clientDocument - документ клиента
	 * @return IFXRq_Type - сообщение для запроса
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public ProductContainer createIMAccountsListRequest(Client client, ClientDocument clientDocument) throws GateLogicException, GateException
	{
		return createBankAcctInqRq(client, clientDocument, BankProductType.IMA);
	}


	/**
	 * Составление запроса для получения детальной информации по ОМС по идентификатору во внешней системе
	 * @param externalId - идентификатор клиента во внешней системе
	 * @return IFXRq_Type - сообщение для запроса
	 */
	public IFXRq_Type createIMAccountDetailsRequest(String externalId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

	    ImaAcctInRq_Type request_type = new ImaAcctInRq_Type();
		request_type.setRqTm( generateRqTm() );
		request_type.setSPName(getSPName());
		request_type.setRqUID(generateUUID());
		request_type.setOperUID(generateOUUID());
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(externalId);
		request_type.setBankInfo(getBankInfo(getRbTbBrch(compositeId), null));

		ImsAcctId_Type id_type = new ImsAcctId_Type();
		id_type.setAcctId(compositeId.getEntityId());
		id_type.setSystemId(compositeId.getSystemIdActiveSystem());
		id_type.setBankInfo(getRbBranchBankInfo(compositeId));

		ImsRec_Type record_type = new ImsRec_Type();
		record_type.setImsAcctId(id_type);

		request_type.setBankAcctRec(new ImsRec_Type[] {record_type});
		ifxRq.setImaAcctInRq(request_type);
		return ifxRq;
	}


	/**
	 * Составление запроса для получения полной выписки по ОМС
	 * @param imAccount - ОМС клиента
	 * @param from - дата с которой формировать выписку
	 * @param to - дата по которую формировать выписку
	 * @return IFXRq_Type - сообщение для запроса
	 */
	public IFXRq_Type createFullIMAccountAbstractRequest(IMAccount imAccount, Calendar from, Calendar to) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctFullStmtInqRq_Type request_type = new BankAcctFullStmtInqRq_Type();
		request_type.setRqTm( generateRqTm() );
		request_type.setSPName( getSPName() );
		request_type.setRqUID( generateUUID() );
		request_type.setOperUID( generateOUUID() );
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		request_type.setBankInfo(getBankInfo(getRbTbBrch(compositeId), null));

		request_type.setFromDate( getStringDate(from) );
		request_type.setToDate( getStringDate(to) );

		BankAcctId_Type  id_type = new BankAcctId_Type();
		id_type.setAcctId( imAccount.getNumber() );
		id_type.setSystemId(compositeId.getSystemIdActiveSystem());
		id_type.setBankInfo(getRbBranchBankInfo(compositeId));

		BankAcctRec_Type record_type = new BankAcctRec_Type();
		record_type.setBankAcctId(id_type);

		request_type.setBankAcctRec(record_type);
		ifxRq.setBankAcctFullStmtInqRq(request_type);
		return ifxRq;
	}

	/**
	 * Составление запроса для получения короткой выписки по ОМС
	 * @param imAccount - ОМС клиента
	 * @return IFXRq_Type - сообщение для запроса
	 */
	public IFXRq_Type createShortIMAccountAbstractRequest(IMAccount imAccount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctStmtInqRq_Type request_type = new BankAcctStmtInqRq_Type();
		request_type.setRqTm( generateRqTm() );
		request_type.setSPName( getSPName() );
		request_type.setRqUID( generateUUID() );
		request_type.setOperUID( generateOUUID() );
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		request_type.setBankInfo(getBankInfo(getRbTbBrch(compositeId), null));
		request_type.setStmtType(StmtType_Type.Short10);

		BankAcctId_Type  id_type = new BankAcctId_Type();
		id_type.setAcctId( imAccount.getNumber() );
		id_type.setSystemId(compositeId.getSystemIdActiveSystem());
		id_type.setBankInfo(getRbBranchBankInfo(compositeId));

		BankAcctRec_Type record_type = new BankAcctRec_Type();
		record_type.setBankAcctId(id_type);

		request_type.setBankAcctRec(record_type);
		ifxRq.setBankAcctStmtInqRq(request_type);
		return ifxRq;
	}

	private BankInfo_Type getRbBranchBankInfo(EntityCompositeId compositeId) throws GateLogicException, GateException
	{
		BankInfo_Type bankInfo_type = new BankInfo_Type();
		bankInfo_type.setRbBrchId(compositeId.getRbBrchId());
		return bankInfo_type;
	}

	/**
	 * Составление запроса для получения остатка по ОМС.
	 *
	 * НЕ ИСПОЛЬЗУЕТСЯ!
	 *
	 * @param office - офис в котором зарегистрирован клиент
	 * @param imAccount - ОМС клиента
	 * @return IFXRq_Type - сообщение для запроса
	 */
	public IFXRq_Type createIMAccountBalanceRequest(Office office, IMAccount imAccount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq_type = new IFXRq_Type();

		AcctInqRq_Type request_type = new AcctInqRq_Type();
		request_type.setRqTm( generateRqTm() );
		request_type.setSPName( getSPName() );
		request_type.setRqUID( generateUUID() );
		request_type.setOperUID( generateOUUID() );
		EntityCompositeId compositeId = EntityIdHelper.getCommonCompositeId(imAccount.getId());
		request_type.setBankInfo(getBankInfo(office, getRbTbBrch(compositeId), null));

		BankAcctId_Type id_type = new BankAcctId_Type();
		id_type.setAcctId(imAccount.getNumber());
		id_type.setSystemId(compositeId.getSystemIdActiveSystem());
		id_type.setBankInfo(getRbBranchBankInfo(compositeId));

		return ifxRq_type;
	}

	/**
	 * получить клиента - владельца объекта(через BackRefClientService)
	 * @param entityCompositeId идентифкатор объета
	 * @return владелец
	 */
	public Client getEntityOwner(EntityCompositeId entityCompositeId) throws GateLogicException, GateException
	{
		BackRefClientService backRefClientService = getFactory().service(BackRefClientService.class);
		return backRefClientService.getClientById(entityCompositeId.getLoginId());
	}
}
