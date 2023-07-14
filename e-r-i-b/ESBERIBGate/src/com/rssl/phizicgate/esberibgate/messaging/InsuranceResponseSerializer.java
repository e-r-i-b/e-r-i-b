package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.insurance.InsuranceApp;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.types.InsuranceAppImpl;
import com.rssl.phizicgate.esberibgate.types.PolicyDetailsImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 04.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceResponseSerializer extends BaseResponseSerializer
{
	/**
	 * «аполнение списка страховых продуктов
	 * @param ifxRq запрос
	 * @param ifxRs - полученный ответ
	 * @param clientId - Id клиента которому принадлежат страховые продукты
	 * @return —писок страховых продуктов
	 */
	public List<InsuranceApp> fillInsurance(IFXRq_Type ifxRq, IFXRs_Type ifxRs,Long clientId)   throws GateLogicException
	{
		if (ifxRs == null)
			return null;

		GetInsuranceListRs_Type insuranceListRs = ifxRs.getGetInsuranceListRs();
		Status_Type statusType = insuranceListRs.getStatus();

		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
		{
			if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, GetInsuranceListRs_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, GetInsuranceListRs_Type.class, ifxRq);
		}

		InsuranceApp_Type[] insuranceAppList = insuranceListRs.getInsuranceAppList();
		if (ArrayUtils.isEmpty(insuranceAppList))
			return null;

		Map<String, InsuranceApp> insurances = new HashMap<String, InsuranceApp>();
		for (InsuranceApp_Type insuranceRec: insuranceAppList)
		{
			try
			{
				InsuranceApp insuranceApp = fillInsuranceApp(insuranceRec, clientId);

				insurances.put(insuranceApp.getReference(), insuranceApp);
			}
			catch(Exception e)
			{
				log.error("ќшибка при заполнении страхового продукта", e);
			}
		}
		return new ArrayList<InsuranceApp>(insurances.values());
	}

	/**
	 * заполнение информации о страховом продукте
	 * @param insuranceRec - запись о страховом продукте
	 * @return кредит
	 */
	private InsuranceApp fillInsuranceApp(InsuranceApp_Type insuranceRec, Long clientId) throws GateException, GateLogicException
	{
		InsuranceAppImpl insurance = new InsuranceAppImpl();
		insurance.setId(EntityIdHelper.createInsuranceCompositeId(insuranceRec.getReference(), clientId));
		insurance.setAdditionalInfo(insuranceRec.getAdditionalInfo());
		insurance.setBusinessProcess(insuranceRec.getBusinessProcess());
		insurance.setCompany(insuranceRec.getCompany());
		insurance.setEndDate(parseCalendar(insuranceRec.getEndDate()));


		insurance.setProductType(insuranceRec.getAdditionalInfo());
		insurance.setProgram(insuranceRec.getProgram());
		insurance.setReference(insuranceRec.getReference());
		insurance.setRisk(insuranceRec.getRisks());
		insurance.setSNILS(insuranceRec.getSNILS());
		insurance.setStartDate(parseCalendar(insuranceRec.getStartDate()));
		insurance.setStatus(insuranceRec.getStatus());

		Currency currency = getCurrencyByString(insuranceRec.getAmountCur());
		insurance.setAmount(safeCreateMoney(insuranceRec.getAmount(),currency));
		PolicyDetails_Type policyDetails = insuranceRec.getPolicyDetails();
		if (policyDetails != null){
			PolicyDetailsImpl policyDetailsImpl = new PolicyDetailsImpl();
			policyDetailsImpl.setSeries(policyDetails.getSeries());
			policyDetailsImpl.setNum(policyDetails.getNum());
			policyDetailsImpl.setIssureDt(parseCalendar(policyDetails.getIssueDt()));
			insurance.setPolicyDetails(policyDetailsImpl);
		}

		return insurance;
	}

	/**
	 * «аполнение детальной информации по страховому/Ќѕ‘ продукту
	 * @param ifxRq запрос
	 * @param ifxRs - полученный ответ
	 * @param clientId - Id клиента которому принадлежит страховой продукт
	 * @return  детальна€ информаци€
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public InsuranceApp getInsuranceApp(IFXRq_Type ifxRq, IFXRs_Type ifxRs, Long clientId) throws GateException, GateLogicException
	{
		GetInsuranceAppRs_Type getInsuranceAppRs = ifxRs.getGetInsuranceAppRs();
		Status_Type statusType = getInsuranceAppRs.getStatus();
		if (statusType.getStatusCode() != CORRECT_MESSAGE_STATUS)
			if (OFFLINE_SYSTEM_STATUSES.contains(statusType.getStatusCode()))
				ESBERIBExceptionStatisticHelper.throwOfflineResponse(statusType, GetInsuranceAppRs_Type.class, ifxRq);
			else
				ESBERIBExceptionStatisticHelper.throwErrorResponse(statusType, GetInsuranceAppRs_Type.class, ifxRq);

		return fillInsuranceApp(getInsuranceAppRs.getInsuranceApp(), clientId);
	}
}
