package com.rssl.phizic.operations.certification;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.security.certification.CertOwn;
import com.rssl.phizic.security.certification.CertificateOwnService;
import com.rssl.phizic.security.certification.CertOwnStatus;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Calendar;

/**
 * @author Omeliyanchuk
 * @ created 27.03.2007
 * @ $Author$
 * @ $Revision$
 */

public class EditCertificateOperation extends OperationBase implements RemoveEntityOperation, EditEntityOperation
{
	private static CertificateOwnService certificateOwnService = new CertificateOwnService();

	private CertOwn certificate;

	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		certificate = certificateOwnService.findById(id);
		if(certificate==null)
			throw new BusinessException("В системе не найден сертификат с id:"+id);
	}

	public CertOwn getEntity()
	{
		return certificate;
	}

	public void activate() throws BusinessException, BusinessLogicException
	{
		if(certificate == null)
			throw new BusinessException("Не установлен сертификат для активации");

		if( !(certificate.getStatus().equals(CertOwnStatus.STATUS_NOT_ACTIVE)||
				certificate.getStatus().equals(CertOwnStatus.STATUS_ACTIVE)) )
			throw new WrongCertificateStatusException();
		try
		{
			this.certificate = certificateOwnService.setActive(certificate.getOwner(), certificate.getCertificate());
		}
		catch(SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}
		catch(SecurityLogicException ex)
		{
			throw new BusinessLogicException(ex);
		}
	}

	public void remove() throws WrongCertificateStatusException, BusinessException
	{
		if(!certificate.getStatus().equals(CertOwnStatus.STATUS_NOT_ACTIVE) )
			throw new WrongCertificateStatusException();

		certificate.setStatus( CertOwnStatus.STATUS_DELETED);
		try
		{
			certificate = certificateOwnService.update(certificate);
		}
		catch(SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}
	}

	public void block() throws WrongCertificateStatusException, BusinessException
	{
		if(!certificate.getStatus().equals(CertOwnStatus.STATUS_ACTIVE) )
			throw new WrongCertificateStatusException();

		certificate.setStatus( CertOwnStatus.STATUS_BLOCKED);
		certificate.setEndDate(Calendar.getInstance());
		try
		{
			certificate = certificateOwnService.update(certificate);
		}
		catch(SecurityDbException ex)
		{
			throw new BusinessException(ex);
		}
	}

	public void save()
	{

	}
}
