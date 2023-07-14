package com.rssl.auth.csa.back.integration.erib.types;

import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.phizgate.common.services.types.ClientDocumentImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о клиенте
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class ClientInformation
{
	private String firstname;
	private String surname;
	private String patrname;
	private Calendar birthdate;
	private List<ClientDocumentImpl> documents;
	private List<Address> postAddr;
	private List<Contact> contactData;
	private boolean UDBO;

	/**
	 * дефолтный конструктор
	 */
	public ClientInformation()
	{}

	/**
	 * конструктор по профилю клиента в цса
	 * @param profile профиль клиента в цса
	 */
	public ClientInformation(Profile profile)
	{
		firstname = profile.getFirstname();
		surname = profile.getSurname();
		patrname = profile.getPatrname();
		birthdate = profile.getBirthdate();

		documents = new ArrayList<ClientDocumentImpl>(1);
		ClientDocumentImpl document = new ClientDocumentImpl();
		document.setDocumentType(ClientDocumentType.PASSPORT_WAY);
		document.setDocSeries(profile.getPassport());
		documents.add(document);
	}

	/**
	 * @return имя
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * задать имя
	 * @param firstname имя
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return фамилия
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * задать фамилию
	 * @param surname фамилия
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return отчество
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * задать отчество
	 * @param patrname отчество
	 */
	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthdate()
	{
		return birthdate;
	}

	/**
	 * задать дату рождения
	 * @param birthdate дата рождения
	 */
	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	/**
	 * @return список документов
	 */
	public List<ClientDocumentImpl> getDocuments()
	{
		return documents;
	}

	/**
	 * задать список документов
	 * @param documents список документов
	 */
	public void setDocuments(List<ClientDocumentImpl> documents)
	{
		this.documents = documents;
	}

	/**
	 * @return адреса клиента
	 */
	public List<Address> getPostAddr()
	{
		return postAddr;
	}

	/**
	 * задать адреса клиента
	 * @param postAddr адреса клиента
	 */
	public void setPostAddr(List<Address> postAddr)
	{
		this.postAddr = postAddr;
	}

	/**
	 * @return контактная информация
	 */
	public List<Contact> getContactData()
	{
		return contactData;
	}

	/**
	 * задать контактную информацию
	 * @param contactData контактная информация
	 */
	public void setContactData(List<Contact> contactData)
	{
		this.contactData = contactData;
	}

	/**
	 * @return заключен ли УДБО
	 */
	public boolean isUDBO()
	{
		return UDBO;
	}

	/**
	 * задать признае заключения УДБО
	 * @param UDBO заключен ли УДБО
	 */
	public void setUDBO(boolean UDBO)
	{
		this.UDBO = UDBO;
	}
}
