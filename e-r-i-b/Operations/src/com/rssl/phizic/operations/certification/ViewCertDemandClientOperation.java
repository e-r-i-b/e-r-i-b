package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;

/**
 * @author Omeliyanchuk
 * @ created 22.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ViewCertDemandClientOperation extends OperationBase<UserRestriction> implements ViewEntityOperation
{
	private static final CertDemandService demandService = new CertDemandService();
	private static final PersonService personService = new PersonService();

	private ActivePerson person;
	private CertDemand certDemand;

	public void initialize(Long certDemandId) throws WrongCertificateStatusException, BusinessException
	{
		certDemand = demandService.findDemandById( certDemandId );
		String status = certDemand.getStatus();
		if(status.equals(CertDemandStatus.STATUS_ENTERED))
			throw new WrongCertificateStatusException();

		try
		{
			person = personService.findByLogin((Login) certDemand.getLogin() );
		}
		catch(BusinessException ex)
		{
			throw new RuntimeException(ex);
		}
		checkOwner();
	}

	private void checkOwner() throws BusinessException
	{
		AuthModule module = AuthModule.getAuthModule();
		CommonLogin currentlogin = module.getPrincipal().getLogin();
		CommonLogin sertOwner = certDemand.getLogin();
		if (!sertOwner.equals(currentlogin)){
			certDemand = null;
			person=null;
			throw new AccessException("Попытка открыть чужую заявку на сертификат");
		}
	}

	public ActivePerson getCertPerson() throws BusinessException
	{
		if(person == null)
			throw new BusinessException("Не установлены данные операции");
		return person;
	}

	public CertDemand getEntity() throws BusinessException
	{
		if(certDemand == null)
			throw new BusinessException("Не установлены данные операции");

		return certDemand;
	}


	/**
	 * инсталяции сертификата пользователем
	 * @throws BusinessException
	 */
	public void install() throws BusinessException
	{
		if( certDemand == null )
			throw new BusinessException("Не установлен запрос для инсталяции");

		if( certDemand.getStatus().equals( CertDemandStatus.STATUS_CERT_GIVEN) )
		{
			certDemand.setStatus(CertDemandStatus.STATUS_CERT_INSTALED);
			demandService.update(certDemand);
		}
		else
			if( !certDemand.getStatus().equals( CertDemandStatus.STATUS_CERT_INSTALED) )
			{
				throw new BusinessException("Нельзя инсталировать не выданный сертификат id:" + certDemand.getId());
			}
	}
}
