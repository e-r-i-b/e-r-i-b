package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.security.SecurityAccount;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.SecurityAccountImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public class SecurityAccountResponseSerializer extends BaseResponseSerializer
{
	private  static String SYSTEM_ID = "urn:sbrfsystems:99-ib";
	/**
	 * ���������� ������ �������������� ������������
	 * @param ifxRq ������
	 * @param ifxRs - ���������� �����
	 * @param clientId - Id ������� �������� ����������� �������������� �����������
	 * @return ������ �������������� ������������
	 */
	public List<SecurityAccount> fillSecurityAccounts(IFXRq_Type ifxRq, IFXRs_Type ifxRs,Long clientId) throws GateLogicException
	{
		if (ifxRs == null)
			return Collections.emptyList();

		SecuritiesAcctInfo_Type securitiesAcctInfo = ifxRs.getBankAcctInqRs().getSecuritiesAcctInfo();
		Status_Type status = ifxRs.getBankAcctInqRs().getStatus();
		if (status.getStatusCode() != CORRECT_MESSAGE_STATUS )
		{
			log.error("��������� ��������� � �������. ������ ����� " + status.getStatusCode() + ". " + status.getStatusDesc());
			ESBERIBExceptionStatisticHelper.throwErrorResponse(status, BankAcctInqRs_Type.class, ifxRq);
		}
		if (securitiesAcctInfo == null)
			return Collections.emptyList();
		SecuritiesRecShort_Type[] securitiesRecList = securitiesAcctInfo.getSecuritiesRec();
		if(securitiesRecList == null || securitiesRecList.length == 0)
			return Collections.emptyList();

		List<SecurityAccount> securityAccounts = new ArrayList<SecurityAccount>(securitiesRecList.length);
		for(SecuritiesRecShort_Type securitiesRec : securitiesRecList)
		{
			try
			{
				securityAccounts.add(fillSecurityAccountShort(securitiesRec, clientId));
			}
			catch (Exception e)
			{
				log.error("������ ��� ���������� ���������������", e);
			}
		}
		return securityAccounts;
	}

	private SecurityAccount fillSecurityAccountShort(SecuritiesRecShort_Type securitiesRec, Long clientId) throws GateException, GateLogicException
	{
		SecurityAccountImpl securityAccount = new SecurityAccountImpl();
		securityAccount.setId(EntityIdHelper.createSecurityAccountCompositeId(securitiesRec.getBlankInfo().getSerialNum(),securitiesRec.getBlankInfo().getBlankType(), clientId, SYSTEM_ID));
		securityAccount.setBlankType(securitiesRec.getBlankInfo().getBlankType());
		securityAccount.setSerialNumber(securitiesRec.getBlankInfo().getSerialNum());
		SecuritiesInfo_Type securitiesInfo = securitiesRec.getSecuritiesInfo();
		Currency currency = getCurrencyByString(securitiesInfo.getNominalCurCode());
		securityAccount.setNominalAmount(safeCreateMoney(securitiesInfo.getNominalAmt(),currency));
		securityAccount.setIncomeAmt(safeCreateMoney(securitiesInfo.getIncomeAmt(),currency));
		securityAccount.setComposeDt(parseCalendar(securitiesInfo.getComposeDt()));
		securityAccount.setTermDays(securitiesInfo.getTermDays());
		securityAccount.setTermFinishDt(parseCalendar(securitiesInfo.getTermFinishDt()));
		securityAccount.setTermLimitDt(parseCalendar(securitiesInfo.getTermLimitDt()));
		securityAccount.setTermStartDt(parseCalendar(securitiesInfo.getTermStartDt()));
		securityAccount.setIncomeRate(securitiesInfo.getIncomeRate());
		//���� ��� onStorageInBank ������, �� �������, ��� ���������� �������� "�� �����"
		securityAccount.setOnStorageInBank(securitiesRec.getOnStorageInBank() == null ? false : securitiesRec.getOnStorageInBank());
		return securityAccount;
	}

	/**
	 * ���������� ��������� ���������� �� ��������������� �����������
	 * @param ifxRq ������
	 * @param securitiesInfoInq - ���������� �����
	 * @param clientId - Id ������� �������� ����������� ����������
	 * @return  ��������� ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public SecurityAccount fillSecurityAccount(IFXRq_Type ifxRq, SecuritiesInfoInqRs_Type securitiesInfoInq, Long clientId) throws GateException, GateLogicException
	{
		SecurityAccountImpl securityAccount = new SecurityAccountImpl();
		Status_Type status = securitiesInfoInq.getStatus();
		if(status.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(status.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(status, SecuritiesInfoInqRs_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(status, SecuritiesInfoInqRs_Type.class, ifxRq);
		}
		SecuritiesRec_Type securitiesRec = securitiesInfoInq.getSecuritiesRec()[0];
		securityAccount.setId(EntityIdHelper.createSecurityAccountCompositeId(securitiesRec.getBlankInfo().getSerialNum(),securitiesRec.getBlankInfo().getBlankType(), clientId, securitiesInfoInq.getSystemId()));
		securityAccount.setBlankType(securitiesRec.getBlankInfo().getBlankType());
		securityAccount.setSerialNumber(securitiesRec.getBlankInfo().getSerialNum());
        SecuritiesInfo_Type securitiesInfo = securitiesRec.getSecuritiesInfo();
		Currency currency = getCurrencyByString(securitiesInfo.getNominalCurCode());
		securityAccount.setNominalAmount(safeCreateMoney(securitiesInfo.getNominalAmt(),currency));
		securityAccount.setIncomeAmt(safeCreateMoney(securitiesInfo.getIncomeAmt(), currency));
		securityAccount.setComposeDt(parseCalendar(securitiesInfo.getComposeDt()));
		securityAccount.setTermDays(securitiesInfo.getTermDays());
		securityAccount.setTermFinishDt(parseCalendar(securitiesInfo.getTermFinishDt()));
		securityAccount.setTermLimitDt(parseCalendar(securitiesInfo.getTermLimitDt()));
		securityAccount.setTermStartDt(parseCalendar(securitiesInfo.getTermStartDt()));
		securityAccount.setIncomeRate(securitiesInfo.getIncomeRate());
		 //������, �������� ��
		securityAccount.setIssuerBankName(securitiesRec.getIssuer().getBankInfo().getName());
		securityAccount.setIssuerBankId(securitiesRec.getIssuer().getBankInfo().getBankId());

		// ���������� �� �������� ��������
		if (securitiesRec.getSecuritiesDocument() != null)
		{
			SecuritiesDocument_Type securitiesDocument = securitiesRec.getSecuritiesDocument();
			securityAccount.setDocNum(securitiesDocument.getDocNum());
			securityAccount.setDocDt(parseCalendar(securitiesDocument.getDocDt()));
			if (securitiesDocument.getRegistrar() != null)
			{
				BankInfoExt_Type info = securitiesDocument.getRegistrar().getBankInfo();
				securityAccount.setBankId(info.getBankId());
				securityAccount.setBankName(info.getName());
				if (info.getContactInfo() != null)
				{
					PostAddr_Type[] addrList = info.getContactInfo().getPostAddr();
					if (addrList != null && addrList.length > 0){
						PostAddr_Type addr = addrList[0];
						String address = "";
						if (addr.getAddr1() != null)
						{
							address = addr.getAddr1();
						}
						else
						{
							if (StringHelper.isNotEmpty(addr.getCountry()))
								address = address + addr.getCountry()+", ";
							if (StringHelper.isNotEmpty(addr.getPostalCode()))
								address = address + addr.getPostalCode() + ", ";
							if (StringHelper.isNotEmpty(addr.getDistrict()))
								address = address + addr.getDistrict() + " �-�, ";
							if (StringHelper.isNotEmpty(addr.getCity()))
								address = address + "�. "+ addr.getCity();
							if (StringHelper.isNotEmpty(addr.getPlace()))
								address = address + ", "+ addr.getPlace();
							if (StringHelper.isNotEmpty(addr.getStreet()))
								address = address + ", ��."+ addr.getStreet();
							if (StringHelper.isNotEmpty(addr.getHouse()))
								address = address + ", �. "+ addr.getHouse();
							if (StringHelper.isNotEmpty(addr.getCorpus()))
								address = address + ", ����. "+ addr.getCorpus();
							if (StringHelper.isNotEmpty(addr.getBuilding()))
								address = address + ", ���. "+ addr.getBuilding();
							if (StringHelper.isNotEmpty(addr.getFlat()))
								address = address + ", ��. "+ addr.getFlat();
						}
						securityAccount.setBankPostAddr(address);
					}
				}
			}
		}
		return securityAccount;
	}
}
