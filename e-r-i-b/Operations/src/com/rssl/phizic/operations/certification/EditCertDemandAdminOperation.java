package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.auth.Login;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 21.11.2006 Time: 9:01:51 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandAdminOperation extends OperationBase implements EditEntityOperation
{
	private static final CertDemandService demandService = new CertDemandService();
	private static final PersonService personService = new PersonService();
	private static final SimpleService simpleService = new SimpleService();

	private ActivePerson person;
	private CertDemand certDemand;

	public void initialize(Long certDemandId) throws BusinessException
	{
		certDemand = demandService.findDemandById( certDemandId );
		person = personService.findByLogin((Login) certDemand.getLogin() );
	}

	public ActivePerson getPerson()
	{
		return person;
	}

	public CertDemand getEntity()
	{
		return certDemand;
	}

	public void save() throws BusinessLogicException, BusinessException	{}

	public void setDemandToProcesed() throws BusinessException
	{
		certDemand.setStatus(CertDemandStatus.STATUS_PROCESSING);
		simpleService.update(certDemand);
	}

	public void refuseDemand(String message) throws BusinessException,WrongCertificateStatusException
	{
		if(certDemand == null)
			throw new BusinessException("Не установлен запрос для отказа");
		if(certDemand.getStatus().equals(CertDemandStatus.STATUS_PROCESSING) || 
				certDemand.getStatus().equals(CertDemandStatus.STATUS_SENDED) )
		{
			certDemand.setStatus( CertDemandStatus.STATUS_REFUSED);
			certDemand.setRefuseReason(message);
			simpleService.update(certDemand);
		}
		else throw new WrongCertificateStatusException();
	}
}
