package com.rssl.phizic.operations.certification;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmStrategyResult;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.business.operations.ConfirmableOperation;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.certification.CertificateOwnService;

/**
 * @author Omeliyanchuk
 * @ created 18.05.2007
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmCertDemandClientOperation extends ConfirmableOperationBase implements ConfirmableOperation
{
	private static final CertDemandService demandService = new CertDemandService();
	private static final PersonService personService = new PersonService();
	private CertificateOwnService certificateOwnService = new CertificateOwnService();

	CertDemand certDemand;
	ActivePerson certPerson;
	Boolean hasActiveCertificate = false;

	public void initialize(Long id) throws BusinessException
	{
		certDemand = demandService.findDemandById( id );
		certPerson = personService.findByLogin( (Login)certDemand.getLogin() );
		try
		{
			hasActiveCertificate = (certificateOwnService.findActive( certPerson.getLogin() ) != null);
		}
		catch(SecurityDbException ex)
		{
			throw new RuntimeException(ex);
		}
		checkOwner();
		setStrategyType();
	}

	private void checkOwner() throws BusinessException
	{
		AuthModule module = AuthModule.getAuthModule();
		CommonLogin currentlogin = module.getPrincipal().getLogin();
		CommonLogin sertOwner = certDemand.getLogin();
		if (!sertOwner.equals(currentlogin)){
			certDemand = null;
			certPerson=null;
			throw new AccessException("Попытка открыть чужую заявку на сертификат");
		}
	}

	public Boolean getHasActiveCertificate()
	{
		return hasActiveCertificate;
	}

	public CertDemand getConfirmableObject()
	{
		if(certDemand == null)
			throw new RuntimeException("Не установлен запрос на сертификат для подтверждения. Используйте initialize");
		return certDemand;
	}

	public ActivePerson getCertPerson()
	{
		if(certPerson == null)
			throw new RuntimeException("Не установлен владелец запроса для подтверждения. Используйте initialize");
		return certPerson;
	}

	public void validateConfirm() throws BusinessException, SecurityLogicException, SecurityException, BusinessLogicException
	{
		if(getHasActiveCertificate())
			super.validateConfirm();

		//return new ConfirmStrategyResult(false);
	}

	@Transactional
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		certDemand.setStatus( CertDemandStatus.STATUS_SENDED );
		certDemand.setSigned( getHasActiveCertificate() );
		demandService.update( certDemand );
	}
}
