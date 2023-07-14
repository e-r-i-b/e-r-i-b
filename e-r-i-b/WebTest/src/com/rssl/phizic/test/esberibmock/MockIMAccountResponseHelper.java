package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.test.webgate.esberib.utils.IMAccountResponseHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 @ author: Egorovaa
 @ created: 22.02.2012
 @ $Author$
 @ $Revision$
 */
public class MockIMAccountResponseHelper extends MockBaseResponseHelper
{
	private static final MockProductService mockProductService = new MockProductService();
	private IMAccountResponseHelper imAccountResponseHelper = new IMAccountResponseHelper();

	/**
	 * Формирование ответа для GFL, содержащего информацию по ОМС
	 * @param bankAcctInqRs bankAcctInqRs ответ для GFL
	 * @param gflId gflId идентификатор запроса GFL, по которому получаем ОМС
	 * @throws BusinessException
	 */
	public void buildMockIMAccounts(BankAcctInqRs_Type bankAcctInqRs, Long gflId) throws BusinessException
	{
		List<MockIMAccount> imAccounts = mockProductService.getIMAccounts(gflId);
		BankAcctRec_Type[] bankRecord = new BankAcctRec_Type[imAccounts.size()];

		for (int i = 0; i < imAccounts.size(); i++)
		{
			MockIMAccount mockIMAccount = imAccounts.get(i);
			BankAcctRec_Type rec = new BankAcctRec_Type();
			rec.setBankInfo(getMockBankInfo(mockIMAccount));
			rec.setAcctBal(buildBalance(mockIMAccount.getAcctBal()));
			rec.setBankAcctId(buildBankImaAccountIdType(mockIMAccount));
			rec.setImsAcctInfo(buildImsAcctInfo(mockIMAccount));
			bankRecord[i] = rec;
		}
		bankAcctInqRs.setBankAcctRec(bankRecord);
	}

	/**
	 * Формирование информации о банке
	 * @param mockIMAccount - ОМС, для которого формируется информация
	 * @return
	 */
	protected BankInfo_Type getMockBankInfo(MockIMAccount mockIMAccount)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();

		bankInfo.setAgencyId(mockIMAccount.getAgencyId());
		bankInfo.setBranchId(mockIMAccount.getBranchId());
		bankInfo.setRbBrchId(mockIMAccount.getRbBrchId());
		bankInfo.setRegionId(mockIMAccount.getRegionId());
		return bankInfo;
	}

	/**
	 * Проверяем корректность статуса (написание)
	 * @param state строка, описывающая статус
	 * @return
	 */
	private IMSStatusEnum_Type getIMAStatus(String state)
	{
		if (IMSStatusEnum_Type._value1.equals(state)) // Opened
			return IMSStatusEnum_Type.value1;
		if (IMSStatusEnum_Type._value2.equals(state)) // Closed
			return IMSStatusEnum_Type.value2;
		if (IMSStatusEnum_Type._value3.equals(state)) // Lost-passbook
			return IMSStatusEnum_Type.value3;
		return null;
	}

	/**
	 * Заполняем информацию по ОМС
	 * @param mockIMAccount - ОМС, для которого формируются данные
	 * @return
	 */
	private ImsAcctInfo_Type buildImsAcctInfo(MockIMAccount mockIMAccount) throws BusinessException
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		ImsAcctInfo_Type imsAcctInfo = new ImsAcctInfo_Type();
		imsAcctInfo.setStatus(getIMAStatus(mockIMAccount.getStatus()));
		if (imsAcctInfo.getStatus().equals(IMSStatusEnum_Type.value2))
		{
			if (mockIMAccount.getEndDate() != null)
				imsAcctInfo.setEndDate(mockDateFormat.format(mockIMAccount.getEndDate().getTime()));
			else throw new BusinessException("Для закрытого ОМС не указана дата закрытия");
		}

		imsAcctInfo.setAgreementNumber(mockIMAccount.getAgreementNumber());
		CustRec_Type custRec = getMockCustRec(mockIMAccount);
		if (custRec.getCustInfo()!=null)
			imsAcctInfo.setCustRec(getMockCustRec(mockIMAccount));
		imsAcctInfo.setBankInfo(getMockBankInfo(mockIMAccount));

		return imsAcctInfo;
	}

	/**
	 * Формирование данных о клиенте
	 * @param mockIMAccount - ОМС, для которого требуется получить информацию
	 * @return
	 */
	private CustRec_Type getMockCustRec(MockIMAccount mockIMAccount)
	{
		CustRec_Type custRec = new CustRec_Type();
		CustInfo_Type custInfo = null;  // вынесли так, потому что инфо м.б. пустой
		if (mockIMAccount.getPersonInfo() != null)
		{
			custInfo = new CustInfo_Type();
			custInfo.setCustId(mockIMAccount.getPersonInfo().getCustId());
			custRec.setCustId(mockIMAccount.getPersonInfo().getCustId());
			PersonInfo_Type personInfo = new PersonInfo_Type();

			PersonName_Type personName = new PersonName_Type();
			personName.setLastName(mockIMAccount.getPersonInfo().getLastName());
			personName.setFirstName(mockIMAccount.getPersonInfo().getFirstName());
			personName.setMiddleName(mockIMAccount.getPersonInfo().getMiddleName());
			personInfo.setPersonName(personName);

			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(mockIMAccount.getPersonInfo().getIdType());
			identityCard.setIdSeries(mockIMAccount.getPersonInfo().getIdSeries());
			identityCard.setIdNum(mockIMAccount.getPersonInfo().getIdNum());
			personInfo.setIdentityCard(identityCard);

			personInfo.setIdentityCard(identityCard);
			custInfo.setPersonInfo(personInfo);
		}

		custRec.setCustInfo(custInfo);
		return custRec;
	}

	/**
	 * Заполнение идентификатора ОМС
	 * @param mockIMAccount - ОМС, для которого формируются данные по идентификатору
	 * @return
	 */
	protected BankAcctId_Type buildBankImaAccountIdType(MockIMAccount mockIMAccount)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		BankAcctId_Type acct = new BankAcctId_Type();
		acct.setAcctCur(mockIMAccount.getAcctCur());
		acct.setAcctId(mockIMAccount.getAcctId());
		acct.setSystemId(mockIMAccount.getSystemId());
		acct.setAcctName(mockIMAccount.getAcctName());
		acct.setStartDate(mockDateFormat.format(mockIMAccount.getStartDate().getTime()));

		return acct;
	}

	/**
	 * Заполняем детальную информацию по ОМС (для IMA_IS)
	 * @param parameters - входные параметры
	 * @return
	 * @throws BusinessException
	 */
	public IFXRs_Type createImaAcctInRs(IFXRq_Type parameters) throws BusinessException
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Status_Type responseStatus = new Status_Type(0L, null, null, null);
		ImaAcctInRq_Type request = parameters.getImaAcctInRq();

		ImaAcctInRs_Type response = new ImaAcctInRs_Type();
		response.setRqTm(request.getRqTm());
		response.setRqUID(request.getRqUID());
		response.setOperUID(request.getOperUID());
		response.setStatus(responseStatus);

		ImsRec_Type[] records = request.getBankAcctRec();

		for (ImsRec_Type record : records)
		{
			RequestIMAIS requestIMAIS = mockProductService.getIMAccountIdByIMAIS(record.getImsAcctId(), parameters.getImaAcctInRq().getBankInfo().getRbTbBrchId());

			if (requestIMAIS == null)   // если объект пустой(ничего не нашли по запросу), то вызываем супер
			{
				return imAccountResponseHelper.createImaAcctInRs(parameters);
			}
			else if (requestIMAIS.getMockIMAccount()==null) // результат есть, ОМС не получен
			{
				responseStatus = new Status_Type(-10L, "Ошибка: По данным параметрам запроса IMA_IS ОМС  не получен.",
						null, "Ошибка получения детальной информации ОМС " + record.getImsAcctId().getAcctId());
				response.setStatus(responseStatus);

				ImsAcctId_Type id_type = new ImsAcctId_Type();
				id_type.setAcctId(record.getImsAcctId().getAcctId());
				id_type.setSystemId(record.getImsAcctId().getSystemId());

				ImsAcctInfo_Type info_type = new ImsAcctInfo_Type();
				info_type.setBankInfo(new BankInfo_Type());

				record.setStatus(responseStatus);
				record.setImsAcctId(id_type);
				record.setImsAcctInfo(info_type);
			}
			else
			{
				MockIMAccount imAccount = requestIMAIS.getMockIMAccount();
				ImsAcctId_Type id_type = new ImsAcctId_Type();
				id_type.setAcctId(imAccount.getAcctId());
				id_type.setSystemId(imAccount.getSystemId());

				ImsAcctInfo_Type info_type = buildImsAcctInfo(imAccount);
				info_type.setAcctCur(imAccount.getAcctCur());
				info_type.setAcctName(imAccount.getAcctName());

				info_type.setStartDate(mockDateFormat.format(imAccount.getStartDate().getTime()));

				AcctBal_Type[] limits = buildBalance(imAccount.getAcctBal());
				for (AcctBal_Type limit : limits)
				{
					if (limit.getBalType().equalsIgnoreCase("Avail"))
						info_type.setAmount(limit.getCurAmt());
					if (limit.getBalType().equalsIgnoreCase("AvailCash"))
						info_type.setMaxSumWrite(limit.getCurAmt());
				}

				record.setStatus(responseStatus);
				record.setImsAcctId(id_type);
				record.setImsAcctInfo(info_type);
			}
		}
		response.setImsRec(records);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setImaAcctInRs(response);

		return ifxRs;
	}

}
