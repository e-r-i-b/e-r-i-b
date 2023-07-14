package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.insurance.InsuranceLink;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author lukina
 * @ created 11.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class InsuranceHelper   extends BaseResponseHelper
{
	private static final MultiInstanceExternalResourceService service = new MultiInstanceExternalResourceService();
	//детальная информация по продукту
	protected IFXRs_Type createGetInsuranceAppRs(IFXRq_Type parameters)
	{
		GetInsuranceAppRq_Type request = parameters.getGetInsuranceAppRq();

		GetInsuranceAppRs_Type insuranceAppRs = new GetInsuranceAppRs_Type();
		insuranceAppRs.setRqTm(getRqTm());
		insuranceAppRs.setRqUID(request.getRqUID());
		insuranceAppRs.setOperUID(request.getOperUID());
		insuranceAppRs.setStatus(getStatus());
		InsuranceApp_Type insuranceApp = request.getInsuranceApp();
		String businessProcess = "";
		try
		{
			//если линк уже был добавлен, то смотрим какой у него бизнес-процесс
			InsuranceLink insuranceLink = service.getInsuranceLinksByReference(insuranceApp.getReference(), null);
			if (insuranceLink!= null)
				businessProcess = insuranceLink.getBusinessProcess().toString();
		}
		catch (BusinessException e)
		{		}

		insuranceAppRs.setInsuranceApp(getInsuranceApp(insuranceApp.getReference(), businessProcess));

		IFXRs_Type response = new IFXRs_Type();
		response.setGetInsuranceAppRs(insuranceAppRs);
		return response;
	}

	private InsuranceApp_Type getInsuranceApp(String reference, String businessProcess )
	{
		InsuranceApp_Type insuranceApp = new InsuranceApp_Type();
		insuranceApp.setReference(reference);
		insuranceApp.setAdditionalInfo("дополнительная информация");
		insuranceApp.setAmount(getRandomDecimal());
		insuranceApp.setAmountCur("RUR");
		String newBusinessProcess  = businessProcess;
//		если пришли первый раз, заполняем businessProcess рандомом
		if (StringHelper.isEmpty(businessProcess))
		{
			Random rand = new Random();
			int i = rand.nextInt(3);
			if (i == 0)
				newBusinessProcess = "NPF";
			else
				newBusinessProcess = "Insurance";
		}
		insuranceApp.setBusinessProcess(newBusinessProcess);
		insuranceApp.setEndDate(getStringDate(getRandomDate()));
		insuranceApp.setCompany("ОАО СК \"Альянс\"");

		PolicyDetails_Type policyDetails = new PolicyDetails_Type();
		policyDetails.setIssueDt(getStringDate(getRandomDate()));
		policyDetails.setNum("00001");
		policyDetails.setSeries("15");
		insuranceApp.setPolicyDetails(policyDetails);
		insuranceApp.setProductType("Тип страхового продукта");
		insuranceApp.setProgram("Добровольное страхование жизни и здоровья");
		insuranceApp.setRisks("Утрата трудоспособности в результате несчастного случая");
		insuranceApp.setSNILS("111-111-111 11");
		insuranceApp.setStartDate(getStringDate(getRandomDate()));
		insuranceApp.setStatus("Действует");

		return  insuranceApp;
	}

   //список страховых продуктов
   protected GetInsuranceListRs_Type createGetInsuranceListRs(IFXRq_Type parameters, Login login)
	{
		GetInsuranceListRq_Type request = parameters.getGetInsuranceListRq();

		GetInsuranceListRs_Type insuranceListRs = new GetInsuranceListRs_Type();
		insuranceListRs.setRqTm(getRqTm());
		insuranceListRs.setRqUID(request.getRqUID());
		insuranceListRs.setOperUID(request.getOperUID());
		insuranceListRs.setStatus(getStatus());
		if (insuranceListRs.getStatus().getStatusCode() != 0)
			return insuranceListRs;
		
		List<InsuranceLink> insuranceLinks;
		try
		{
			insuranceLinks = resourceService.getLinks(login, InsuranceLink.class);
		}
		catch (BusinessException e)
		{
			insuranceLinks = new ArrayList<InsuranceLink>();
		}
		catch (BusinessLogicException e)
		{
			insuranceLinks = new ArrayList<InsuranceLink>();
		}
		int count = 5 - insuranceLinks.size();

		List<InsuranceApp_Type> result = new ArrayList<InsuranceApp_Type>();
		for(int i = 0; i < count; i++ )
		{
			String reference = RandomHelper.rand(10, RandomHelper.DIGITS);
			result.add(getInsuranceApp(reference, ""));
		}
		for(InsuranceLink link : insuranceLinks)
		{
			String reference = link.getNumber();
			result.add(getInsuranceApp(reference, link.getBusinessProcess().toString()));
		}
		insuranceListRs.setInsuranceAppList(result.toArray((new InsuranceApp_Type[result.size()])));
		return insuranceListRs;
	}
}
