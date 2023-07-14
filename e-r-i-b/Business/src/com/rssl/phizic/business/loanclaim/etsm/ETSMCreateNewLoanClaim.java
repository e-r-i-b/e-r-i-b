package com.rssl.phizic.business.loanclaim.etsm;

import com.rssl.ikfl.crediting.CRMMessageService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.GateExecutableDocument;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ERIBSendETSMApplRq;

import java.io.Reader;
import java.io.StringReader;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * Создание сигнала отправки заявки ЕРИБ в ETSM
 *
 * @ author: Gololobov
 * @ created: 24.06.15
 * @ $Author$
 * @ $Revision$
 */
public class ETSMCreateNewLoanClaim
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final CRMMessageService crmMessageService = new CRMMessageService();

	private final JAXBContext jaxbContext;

	/**
	 * ctor
	 */
	public ETSMCreateNewLoanClaim()
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(ERIBSendETSMApplRq.class);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM] Сбой на загрузке JAXB-контекста", e);
		}
	}

	/**
	 * Обновление статуса расширенной заявки на кредит
	 * @param requestXML - запрос из TransactSM, по которому производится обновление
	 */
	public void update(String requestXML) throws BusinessException, BusinessLogicException
	{
		ERIBSendETSMApplRq request = readRequest(requestXML);

		ExtendedLoanClaim claim = findLoanClaim(request);
		//заполнение заявки данными из запроса
		initLoanClaimFromRequest(request, claim);
		//Отправка в ETSM
		try
		{
			crmMessageService.initiationCreateNewLoanClaim(claim);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private ERIBSendETSMApplRq readRequest(String requestXML) throws BusinessException
	{
		try
		{
			// Валидацию по XSD-Схеме здесь не проводим, т.к. валидация - задача прокси-листенера
			Reader reader = new StringReader(requestXML);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return (ERIBSendETSMApplRq) unmarshaller.unmarshal(reader);
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск кредитной заявки
	 * @param request
	 * @return
	 * @throws BusinessException
	 */
	private ExtendedLoanClaim findLoanClaim(ERIBSendETSMApplRq request) throws BusinessException
	{
		String loanClaimNumber = request.getApplication().getNumber();
		GateExecutableDocument claim = businessDocumentService.findExtendedLoanClaimByUID(loanClaimNumber);
		if (claim == null)
			throw new IllegalArgumentException("Не найдена кредитная заявка с operUID=" + loanClaimNumber);

		if (!(claim instanceof ExtendedLoanClaim))
			throw new IllegalStateException("Ожидается ExtendedLoanClaim. docid = " + claim.getId());

		return (ExtendedLoanClaim) claim;
	}

	private void initLoanClaimFromRequest(ERIBSendETSMApplRq request, ExtendedLoanClaim claim) throws BusinessException
	{
		if (claim == null)
			return;
		String divisionId = request.getApplication().getDivisionId();
		if (StringHelper.isEmpty(divisionId))
			return;
		int regionLength = 2;
		int branchLength = 4;

		String region = divisionId.substring(0, regionLength);
		String branch = divisionId.substring(regionLength, regionLength + branchLength);
		String office = StringHelper.removeLeadingZeros(divisionId.substring(regionLength + branchLength));
		//Подразделение, где сотрудник ВСП выполнил запрос на создание заявки ЕРИБ в ETSM
		claim.setClaimDrawDepartmentETSMCode(region, office, branch);
		//Логин сотрудника (КИ) в CRM, отправляющего заявку в ETSM из CRM
		claim.setLoginKI(request.getApplication().getCreatedByLogin());

		businessDocumentService.addOrUpdate(claim);
	}
}
