package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.security.crypto.CryptoProvider;
import com.rssl.phizic.security.crypto.CryptoProviderHelper;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.security.certification.CertificateOwnService;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.auth.Login;

/**
 * @author Omeliyanchuk
 * @ created 22.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoadCertificateOperation extends OperationBase<UserRestriction>
{
	private static final CertDemandService certDemandService = new CertDemandService();
	private static final CertificateOwnService certificateOwnService = new CertificateOwnService();
	private static final PersonService personService = new PersonService();

	private CryptoProvider cryptoProvider;
	
	private CertDemand demand;
	private Certificate cert;
	private Certificate certAns;
	private byte[] certData;
	private String certFileName;
	private String certAnswFileName;

	/**
	 * Инициализация операции
	 * @throws BusinessException
	 */
	public void initialize() throws BusinessException
	{
		try
		{
			cryptoProvider = CryptoProviderHelper.getDefaultFactory().getProvider();
		}
		catch (SecurityException ex)
		{
			throw new BusinessException(ex);
		}
	}

	public String getCertFileName()
	{
		return certFileName;
	}

	public void setCertFileName(String certFileName)
	{
		this.certFileName = certFileName;
	}

	public String getCertAnswFileName()
	{
		return certAnswFileName;
	}

	public void setCertAnswFileName(String certAnswFileName)
	{
		this.certAnswFileName = certAnswFileName;
	}

	public CertDemand getDemand()
	{
		return demand;
	}

	public void setDemand(Long demandId)
	{
		this.demand = certDemandService.findDemandById(demandId);
	}

	public void setCertificateFromFile(byte[] certData)
	{
		cert = cryptoProvider.getCertInFileInfo(certData);
		this.certData = certData;
	}

	public void setCeritficateAnswerFromFile(byte[] certAnsData){
		certAns = cryptoProvider.getCertInFileInfo(certAnsData);
	}
	public Certificate getCertificate()
	{
		return cert;
	}

	public void validateCertificate() throws BusinessLogicException, BusinessException
	{
		if(certData == null)
			throw new BusinessException("Не установлен сертификат");

		if(demand == null)
			throw new BusinessException("Не установлен запрос на сертификат");

		if(!demand.getCertDemandCryptoId().equals(certAns.getKeyPairId()))
			throw new InvalidAnswerFileName();

		if(!(demand.getCertDemandCryptoId().equals(cert.getId()) || demand.getCertDemandCryptoId().equals(cert.getKeyPairId())))
			throw new InvalidCertificateFileName();

		Person person = personService.findByLogin( (Login)demand.getLogin() );

		if(! cert.getName().equals( person.getFullName()))
			throw new WrongCertificateOwner();
	}

	public void load(byte[] certAnsData)
			throws BusinessException, BusinessLogicException
	{
		if(certData == null)
			throw new BusinessException("Не установлен сертификат");

		cryptoProvider.loadCertFromFileToStorage( certData );

		if(demand == null)
			throw new BusinessException("Не установлен запрос на сертификат");

		demand.setCertRequestAnswer(new String( certAnsData ));
		demand.setCertRequestAnswerFile(certAnswFileName);
		demand.setStatus(CertDemandStatus.STATUS_CERT_GIVEN);
		certDemandService.update(demand);

		try
		{
			certificateOwnService.add(demand.getLogin(), cert);
		}
		catch(SecurityLogicException ex)
		{
			throw new BusinessLogicException(ex.getMessage());
		}
		catch(SecurityDbException ex)
		{
			throw new BusinessException("Ошибка при назначении сертификата", ex);
		}
		
	}
}
