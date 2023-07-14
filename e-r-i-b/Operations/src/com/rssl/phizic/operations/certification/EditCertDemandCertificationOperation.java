package com.rssl.phizic.operations.certification;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.operations.restrictions.UserRestriction;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.certification.CertDemand;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.business.certification.CertDemandService;
import com.rssl.phizic.business.certification.CertDemandStatus;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.auth.Login;

import java.util.Date;

/**
 * Created by IntelliJ IDEA. User: Omeliyanchuk Date: 20.11.2006 Time: 9:39:01 To change this template use
 * File | Settings | File Templates.
 */
public class EditCertDemandCertificationOperation extends OperationBase<UserRestriction>
{
	private static final CertDemandService demandService = new CertDemandService();
	private static final PersonService personService = new PersonService();

	private Date issueDate;
	private ActivePerson person;
	private Boolean isNew;
	private String requestString;
	private String requestFileName;
	private CertDemand certDemand;

	/**
	 * получение пользователя по логину
	 *
	 * @param login
	 * @return
	 * @throws BusinessException
	 */
	public ActivePerson getCurrentPerson(CommonLogin login) throws BusinessException
	{
		return personService.findByLoginId(login.getId());
	}

	public void initialize(Long certDemandId)
	{
		certDemand = demandService.findDemandById( certDemandId );		
		isNew = false;
	}

	public void intializeNew() throws BusinessException
	{
		isNew = true;
	}

	public ActivePerson getCertPerson() throws BusinessException
	{
		if(certDemand == null)
			throw new BusinessException("Не установлены данные операции");
		return personService.findByLogin((Login) certDemand.getLogin() );
	}

	public void setPerson(Long personId) throws BusinessException
	{
		person =(ActivePerson) personService.findById( personId );
	}

	public void setDate(Date issueDate)
	{
		this.issueDate = issueDate;
	}

	public void setRequest(String requestString, String requestFileName)
	{
		this.requestString = requestString;
		this.requestFileName = requestFileName;
	}


	public CertDemand getCertDemand()
	{
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

	/**
	 * создание нового сертификата
	 * @return
	 * @throws BusinessException
	 */
	public CertDemand add() throws BusinessException
	{
		checkData();
		
		if(isNew)
		{
			if(issueDate==null)
				throw new BusinessException("Не установлена дата создания запроса");
			if(person == null)
				throw new BusinessException("Не установлен пользователь");
			if( (requestString == null) && (requestFileName == null) )
				throw new BusinessException("Не установлен запрос на сертификат");

			CertDemand certDemand = new CertDemand();
			certDemand.setLogin( person.getLogin() );

			certDemand.setIssueDate( DateHelper.toCalendar( issueDate) );
			certDemand.setCertRequest( requestString );
			certDemand.setCertRequestFile( requestFileName );

			this.certDemand = demandService.add( certDemand );
			return certDemand;
		}
		else throw new BusinessException("Невозможно добавить заявку еще раз");
	}

	/**
	 * оправка запроса
	 * @return запрос с новым статусом
	 * @throws BusinessException исключение БД
	 */
	public CertDemand send() throws BusinessException
	{
		if(certDemand == null)
			throw new BusinessException("Не установлен запрос для отправки");

		certDemand.setStatus( CertDemandStatus.STATUS_SENDED );
		demandService.update( certDemand );
		return certDemand;
	}

	private void checkData() throws BusinessException
	{
		if( (issueDate == null) || (person == null) )
		{
			throw new BusinessException("Не установлены данные операции");
		}
	}
}
