package com.rssl.phizicgate.esberibgate.depo;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.claims.DepositorFormClaim;
import com.rssl.phizic.gate.claims.RecallDepositaryClaim;
import com.rssl.phizic.gate.claims.SecuritiesTransferClaim;
import com.rssl.phizic.gate.claims.SecurityRegistrationClaim;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoDebtItem;
import com.rssl.phizic.gate.depo.SecurityOperationType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizicgate.esberibgate.messaging.ClientRequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.clients.ProductContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 19.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class DepoAccountRequestHelper extends ClientRequestHelperBase
{
	public DepoAccountRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Составление запроса для получения списка счетов депо клиента
	 * @param client - клиент
	 * @param clientDocument - документ клиента
	 * @return IFXRq_Type - сообщение для запроса
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public ProductContainer createDepoAccountsListRequest(Client client, ClientDocument clientDocument) throws GateLogicException, GateException
	{
		return createBankAcctInqRq(client, clientDocument, BankProductType.DepoAcc);
	}

	/**
	 * Составление запроса на перевод/прием перевода ценных бумаг со счета Депо
	 * @param claim - заявка на перевод/прием перевода
	 * @param rbTbBrchId - код тербанка
	 * @return запрос на перевод/прием перевода ценных бумаг
	 */
	public IFXRq_Type createDepoAccTranRq(SecuritiesTransferClaim claim,String rbTbBrchId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccTranRqType depoAccTransferRequest = new DepoAccTranRqType();

		depoAccTransferRequest.setRqUID(generateUUID());
		depoAccTransferRequest.setRqTm(generateRqTm());
		depoAccTransferRequest.setOperUID(generateOUUID());
		depoAccTransferRequest.setSPName(SPName_Type.BP_ERIB);

		depoAccTransferRequest.setBankInfo(getDepoBankInfo(rbTbBrchId,null));

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(claim.getDepoExternalId());
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(claim.getDepoAccountNumber());

		depoAcctId.setBankInfo(getDepoBankInfo(null,externalId.getRbBrchId()));

		depoAccTransferRequest.setDepoAcctId(depoAcctId);

		TransferInfo_Type transferInfo = new TransferInfo_Type();
		transferInfo.setDocumentDate(getStringDate(claim.getClientCreationDate()));
		transferInfo.setDocumentNumber(Long.valueOf(claim.getDocumentNumber()));
		transferInfo.setOperType(DepoOperType_Type.fromString(claim.getOperType().getValue()));
		transferInfo.setOperationSubType(DepoOperationSubType_Type.fromString(claim.getOperationSubType().name()));
		transferInfo.setOperationDesc(claim.getOperationDesc());
		transferInfo.setDivisionNumber(getDivisionNumber(claim));
		transferInfo.setInsideCode(claim.getInsideCode());
		transferInfo.setSecurityCount(claim.getSecurityCount());
		transferInfo.setOperationReason(claim.getOperationReason());

		TransferRcpInfo_Type transferRcpInfo = new TransferRcpInfo_Type();
		transferRcpInfo.setCorrDepositary(claim.getCorrDepositary());
		transferRcpInfo.setCorrDepoAcctId(claim.getCorrDepoAccount());
		transferRcpInfo.setCorrOwner(claim.getCorrDepoAccountOwner());
		transferRcpInfo.setAdditionalInfo(claim.getAdditionalInfo());
		transferInfo.setTransferRcpInfo(transferRcpInfo);

		depoAccTransferRequest.setTransferInfo(transferInfo);

		ifxRq.setDepoAccTranRq(depoAccTransferRequest);
		return ifxRq;
	}

	private DivisionNumber_Type getDivisionNumber(SecuritiesTransferClaim claim)
	{
		DivisionNumber_Type divisionNumber = new DivisionNumber_Type();
		divisionNumber.setType(claim.getDivisionType());
		divisionNumber.setNumber(claim.getDivisionNumber());
		return divisionNumber;
	}

	private DepoSecurityOperationType_Type[] getNewDepoSecurityOperationType_Type(Enum operId)
	{
		return new DepoSecurityOperationType_Type[]{DepoSecurityOperationType_Type.fromString(getSecurityOperationTypeMap().get(operId))};
	}

	/**
	 * Составление запроса на регистрацию ценной бумаги
	 * @param claim - заявка на регистрацию ценной бумаги
	 * @param rbTbBrchId - код тербанка
	 * @return запрос на регистрацию цб
	 */
	public IFXRq_Type createDepoAccSecRegRq(SecurityRegistrationClaim claim,String rbTbBrchId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccSecRegRqType depoAccSecRegRqType = new DepoAccSecRegRqType();

		depoAccSecRegRqType.setRqUID(generateUUID());
		depoAccSecRegRqType.setRqTm(generateRqTm());
		depoAccSecRegRqType.setOperUID(generateOUUID());
		depoAccSecRegRqType.setSPName(SPName_Type.BP_ERIB);

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(rbTbBrchId);
		depoAccSecRegRqType.setBankInfo(bankInfo);

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(claim.getDepoExternalId());
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(claim.getDepoAccountNumber());
		bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(externalId.getRbBrchId());
		depoAcctId.setBankInfo(bankInfo);

		depoAccSecRegRqType.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		DepoSecurityOperationInfo_Type depoSecurityOperationInfo = new DepoSecurityOperationInfo_Type();
		depoSecurityOperationInfo.setDepositary(claim.getCorrDepositary());
		depoSecurityOperationInfo.setIssuer(claim.getIssuer());
		depoSecurityOperationInfo.setSecurityName(claim.getSecurityName());
		depoSecurityOperationInfo.setSecurityNumber(claim.getSecurityNumber());
		depoSecurityOperationInfo.setDocumentDate(getStringDate(claim.getClientCreationDate()));
		depoSecurityOperationInfo.setDocumentNumber(Long.valueOf(claim.getDocumentNumber()));

		List<SecurityOperationType> operations = claim.getOperations();
		List<DepoSecurityOperationList_Type> depoSecurityOperationsList = new ArrayList<DepoSecurityOperationList_Type>();

		for (SecurityOperationType operationCode : operations)
		{
			DepoSecurityOperationList_Type depoSecurityOperation = new DepoSecurityOperationList_Type();
			if (operationCode != SecurityOperationType.CLIENT_OPERATION)
			{
				depoSecurityOperation.setOperName(getNewDepoSecurityOperationType_Type(operationCode));
				depoSecurityOperationsList.add(depoSecurityOperation);
			}
			else
			{
				List<String> clientOperations = claim.getClientOperationDescriptionsList();
				for (String clientOperation: clientOperations)
				{
					depoSecurityOperation = new DepoSecurityOperationList_Type();
					depoSecurityOperation.setOperName(getNewDepoSecurityOperationType_Type(operationCode));
					depoSecurityOperation.setCustomOperName(clientOperation);
					depoSecurityOperationsList.add(depoSecurityOperation);
				}
			}
		}
		depoSecurityOperationInfo.setOperations(depoSecurityOperationsList.toArray(new DepoSecurityOperationList_Type[depoSecurityOperationsList.size()]));
		depoAccSecRegRqType.setOperationInfo(depoSecurityOperationInfo);

		ifxRq.setDepoAccSecRegRq(depoAccSecRegRqType);
		return ifxRq;
	}

	/**
	 * Запрос на получение анкеты депонента
	 * @param claim  - поручение на получение анкеты депонента
	 * @param rbTbBrchId - код тербанка
	 * @return запрос на получение анкеты депонента
	 */
	public IFXRq_Type createDepoArRq(DepositorFormClaim claim,String rbTbBrchId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		OperInfo_Type operInfo = new OperInfo_Type();
		operInfo.setDocumentDate(getStringDate(claim.getClientCreationDate()));
		operInfo.setDocumentNumber(Long.valueOf(claim.getDocumentNumber()));

		DepoAccInfoRqType request = new DepoAccInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setOperInfo(operInfo);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(rbTbBrchId);
		request.setBankInfo(bankInfo);

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(claim.getDepoExternalId());
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(claim.getDepoAccountNumber());
		bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(externalId.getRbBrchId());
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		ifxRq.setDepoArRq(request);
		return ifxRq;
	}

	/**
	 * Оповещение о приеме сообщения
 	 * @param status - статус документа
	 * @param docNumber - номер документа
	 * @return  оповещение о приеме сообщения
	 */
	public IFXRq_Type createMessageRecvRq(Status_Type status, String docNumber) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		MessageRecvRqType request = new MessageRecvRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setDocNumber(docNumber);
		request.setStatus(status);

		ifxRq.setMessageRecvRq(request);
		return ifxRq;
	}

	private static  Map<SecurityOperationType,  String> getSecurityOperationTypeMap()
	{
		Map<SecurityOperationType,  String> operationTypeMap = new HashMap<SecurityOperationType,  String>();

		operationTypeMap.put(SecurityOperationType.DEPOSITARY_OPERATION, "1");
		operationTypeMap.put(SecurityOperationType.DEPOSIT_OPERATION, "2");
		operationTypeMap.put(SecurityOperationType.ACCOUNT_OPERATION, "4");
		operationTypeMap.put(SecurityOperationType.CLIENT_OPERATION, "8");
		operationTypeMap.put(SecurityOperationType.TRADE_OPERATION, "16");

		return operationTypeMap;
	}

	/**
	 * Составление запроса на регистрацию клиента в депозитарии
	 * @param client - клиент
	 * @param document - документ клиента
	 * @return запрос к шине
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoClientRegRq(Client client,ClientDocument document) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepoClientRegRqType registrationRequest = new DepoClientRegRqType();

		registrationRequest.setRqUID(generateUUID());
		registrationRequest.setRqTm(generateRqTm());
		registrationRequest.setOperUID(generateOUUID());
		registrationRequest.setSPName(SPName_Type.BP_ERIB);

		String rbTbBrchId = getRbTbBrch(client.getInternalOwnerId());
		registrationRequest.setBankInfo(getDepoBankInfo(rbTbBrchId,null));
		registrationRequest.setCustInfo(getCustInfo(client, document));

		ifxRq.setDepoClientRegRq(registrationRequest);
		return ifxRq;
	}

	/**
	 * Составление запроса на получение информации по счетам депо
	 * @param depoAccount - счета Депо
	 * @return запрос к шине
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoAccInfoRq(DepoAccount... depoAccount) throws GateLogicException, GateException
	{
		return createDepoAccInfoRq(getDepoAccountIds(depoAccount));
	}

	/**
	 * Составление запроса на получение информации по счетам депо
	 * @param depoAccountId - иденитификаторы счетов депо
	 * @return запрос к шине
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoAccInfoRq(String... depoAccountId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepoAccInfoRqType depoAccountInfo = new DepoAccInfoRqType();
		depoAccountInfo.setRqUID(generateUUID());
		depoAccountInfo.setRqTm(generateRqTm());
		depoAccountInfo.setOperUID(generateOUUID());
		depoAccountInfo.setSPName(SPName_Type.BP_ERIB);
		String rbTbBrchId = getRbTbBrch(EntityIdHelper.getCommonCompositeId(depoAccountId[0]));
		BankInfo_Type bankInfo = getDepoBankInfo(rbTbBrchId, null);
		depoAccountInfo.setBankInfo(bankInfo);

		List<DepoAcctId_Type> list = new ArrayList<DepoAcctId_Type>();
		for(String depoId : depoAccountId)
		{
			DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
			EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(depoId);
			depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
			depoAcctId.setAcctId(externalId.getEntityId());
			depoAcctId.setBankInfo(getDepoBankInfo(null,externalId.getRbBrchId()));
			list.add(depoAcctId);
		}
		depoAccountInfo.setDepoAcctId(list.toArray(new DepoAcctId_Type[list.size()]));
		ifxRq.setDepoAccInfoRq(depoAccountInfo);
		return ifxRq;
	}

	/**
	 * Заполнение запроса на получение расшифровки задолженности по счету депо
	 * @param depoAccount - счет депо
	 * @return запрос на получение инф-ии
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoDeptsInfoRq(DepoAccount depoAccount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepoAccInfoRqType depoAccountInfo = new DepoAccInfoRqType();
		depoAccountInfo.setRqUID(generateUUID());
		depoAccountInfo.setRqTm(generateRqTm());
		depoAccountInfo.setOperUID(generateOUUID());
		depoAccountInfo.setSPName(SPName_Type.BP_ERIB);
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(depoAccount.getId());
		BankInfo_Type bankInfo = getDepoBankInfo(getRbTbBrch(externalId), null);
		depoAccountInfo.setBankInfo(bankInfo);

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(externalId.getEntityId());
		depoAcctId.setBankInfo(getDepoBankInfo(null,externalId.getRbBrchId()));

		DepoAcctId_Type[] depoAcctIdArray = new DepoAcctId_Type[1];
		depoAcctIdArray[0] = depoAcctId;
		depoAccountInfo.setDepoAcctId(depoAcctIdArray);

		ifxRq.setDepoDeptsInfoRq(depoAccountInfo);
		return ifxRq;
	}

	/**
	 * Создание запроса на получение списка ценых бумаг на счете депо (информация по позиции).
	 * @param depoAccount - счет депо
	 * @return запрос к шине
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoAccountPositionRq(DepoAccount depoAccount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepoAccInfoRqType depoAccountInfo = new DepoAccInfoRqType();
		depoAccountInfo.setRqUID(generateUUID());
		depoAccountInfo.setRqTm(generateRqTm());
		depoAccountInfo.setOperUID(generateOUUID());
		depoAccountInfo.setSPName(SPName_Type.BP_ERIB);
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(depoAccount.getId());
		BankInfo_Type bankInfo = getDepoBankInfo(getRbTbBrch(externalId), null);
		depoAccountInfo.setBankInfo(bankInfo);

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(externalId.getEntityId());
		depoAcctId.setBankInfo(getDepoBankInfo(null,externalId.getRbBrchId()));

		DepoAcctId_Type[] depoAcctIdArray = new DepoAcctId_Type[1];
		depoAcctIdArray[0] = depoAcctId;
		depoAccountInfo.setDepoAcctId(depoAcctIdArray);
		ifxRq.setDepoAccSecInfoRq(depoAccountInfo);
		return ifxRq;
	}

	/**
	 * Заполнение запроса на получение детальной информации по задолжностям на счете депо
	 * @param depoAccount - счет депо
	 * @param debtItem - список задолженностей, по которым получаем информацию
	 * @return запрос к шине
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public IFXRq_Type createDepoDeptDetInfoRq(DepoAccount depoAccount,DepoDebtItem... debtItem) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();
		DepoDeptDetInfoRqType depoDeptDetInfoRq = new DepoDeptDetInfoRqType();

		depoDeptDetInfoRq.setRqUID(generateUUID());
		depoDeptDetInfoRq.setRqTm(generateRqTm());
		depoDeptDetInfoRq.setOperUID(generateOUUID());
		depoDeptDetInfoRq.setSPName(SPName_Type.BP_ERIB);
		EntityCompositeId externalId = EntityIdHelper.getCommonCompositeId(depoAccount.getId());
		depoDeptDetInfoRq.setBankInfo(getDepoBankInfo(getRbTbBrch(externalId), null));

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId(externalId.getSystemIdActiveSystem());
		depoAcctId.setAcctId(externalId.getEntityId());
		depoAcctId.setBankInfo(getDepoBankInfo(null,externalId.getRbBrchId()));

		DepoAcctId_Type[] depoAcctIdArray = new DepoAcctId_Type[1];
		depoAcctIdArray[0] = depoAcctId;
		depoDeptDetInfoRq.setDepoAcctId(depoAcctIdArray);

		List<DeptId_Type> deptIdList = new ArrayList<DeptId_Type>();
		for(DepoDebtItem item : debtItem)
		{
			DeptId_Type deptId = new DeptId_Type();
			deptId.setRecNumber(item.getRecNumber());
			deptId.setEffDt(getStringDate(item.getRecDate()));
			deptIdList.add(deptId);
		}

		depoDeptDetInfoRq.setDeptId(deptIdList.toArray(new DeptId_Type[deptIdList.size()]));
		ifxRq.setDepoDeptDetInfoRq(depoDeptDetInfoRq);
		return ifxRq;
	}

	/**
	 * Заполнение запроса на отзыв документа из депозитария
	 * @param claim - заявка на отзыв документа
	 * @param rbTbBrchId - код тербанка
	 * @return запрос к шине
	 */
	public IFXRq_Type createDepoRevokeDocRq(RecallDepositaryClaim claim, String rbTbBrchId)
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		OperInfo_Type operInfo = new OperInfo_Type();
		operInfo.setDocumentDate(getStringDate(claim.getClientCreationDate()));
		operInfo.setDocumentNumber(Long.valueOf(claim.getDocumentNumber()));

		DepoRevokeDocRqType revokeDocRequest = new DepoRevokeDocRqType();
		revokeDocRequest.setRqUID(generateUUID());
		revokeDocRequest.setRqTm(generateRqTm());
		revokeDocRequest.setOperUID(generateOUUID());
		revokeDocRequest.setSPName(SPName_Type.BP_ERIB);
		revokeDocRequest.setBankInfo(getDepoBankInfo(rbTbBrchId,null));
		revokeDocRequest.setDocNumber(claim.getDocNumber());
		revokeDocRequest.setRevokePurpose(claim.getRevokePurpose());
		revokeDocRequest.setRevokeDate(getStringDate(claim.getClientCreationDate()));
		revokeDocRequest.setOperInfo(operInfo);
		ifxRq.setDepoRevokeDocRq(revokeDocRequest);
		return ifxRq;
	}

	private BankInfo_Type getDepoBankInfo(String rbTbBrchId, String rbBrchId)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		if(rbTbBrchId != null)
			bankInfo.setRbTbBrchId(rbTbBrchId);
		if(rbBrchId != null)
			bankInfo.setRbBrchId(rbBrchId);
		return bankInfo;
	}

	private CustInfo_Type getCustInfo(Client client, ClientDocument document)
	{
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(getPersonInfo(client, document, false, false));
		return custInfo;
	}

	/**
	 * Получение идентификаторов счетов Депо
	 * @param depoAccount - список счетов Депо
	 * @return идентификаторы счетов депо
	 */
	private String[] getDepoAccountIds(DepoAccount... depoAccount)
	{
		String[] depoAccountIds = new String[depoAccount.length];
		for(int i=0; i< depoAccount.length; i++)
			depoAccountIds[i] = depoAccount[i].getId();
		return depoAccountIds;
	}
}
