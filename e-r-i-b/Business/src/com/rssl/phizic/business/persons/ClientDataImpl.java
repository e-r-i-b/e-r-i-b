package com.rssl.phizic.business.persons;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.person.ClientData;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * реализация обертки для Business
 * @author basharin
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 */

public class ClientDataImpl extends ClientData implements ShopProfile
{
	private static final DepartmentService departmentService = new DepartmentService();

	public ClientDataImpl(Person person) throws IKFLMessagingException
	{
		List<PersonDocument> personDocuments = new ArrayList<PersonDocument>(person.getPersonDocuments());

		Collections.sort(personDocuments, new PersonDocumentTypeComparator());
		PersonDocument document = personDocuments.get(0);

		loginId = person.getLogin().getId();
		surName = person.getSurName();
		firstName = person.getFirstName();
		patrName = person.getPatrName();
		documentSeriesNumber = document.getDocumentSeries() + document.getDocumentNumber();
		birthDay = person.getBirthDay();
		try
		{
			tb = departmentService.getNumberTB(person.getDepartmentId());
		}
		catch (BusinessException e)
		{
			throw new IKFLMessagingException("Ошибка при получении ТБ клиента", e);
		}
	}

	public ClientDataImpl(AuthenticationContext authenticationContext) throws BusinessException
	{
		loginId = authenticationContext.getLogin().getId();
		surName = authenticationContext.getLastName();
		firstName = authenticationContext.getFirstName();
		patrName = authenticationContext.getMiddleName();
		documentSeriesNumber = authenticationContext.getDocumentNumber();
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			calendar.setTime(dateFormat.parse(authenticationContext.getBirthDate()));
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка при преобразовании даты рождения клиента", e);
		}
		birthDay = calendar;
		tb = authenticationContext.getTB();
	}

	/**
	 * ctor
	 * @param userInfo информация о пользователе
	 */
	public ClientDataImpl(UserInfo userInfo)
	{
		surName = userInfo.getSurname();
		firstName = userInfo.getFirstname();
		patrName = userInfo.getPatrname();
		documentSeriesNumber = userInfo.getPassport();
		birthDay = userInfo.getBirthdate();
		tb = userInfo.getTb();
	}

	public ClientDataImpl() {}

	public Long getId()
	{
		return null;
	}

	public String getPassport()
	{
		return getDocumentSeriesNumber();
	}

	public Calendar getBirthdate()
	{
		return getBirthDay();
	}

	public void setPassport(String passport)
	{
		setDocumentSeriesNumber(passport);
	}

	public void setBirthdate(Calendar birthdate)
	{
		setBirthDay(birthdate);
	}
}
