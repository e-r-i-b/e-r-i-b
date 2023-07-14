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
 * ���������� � �������
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
	 * ��������� �����������
	 */
	public ClientInformation()
	{}

	/**
	 * ����������� �� ������� ������� � ���
	 * @param profile ������� ������� � ���
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
	 * @return ���
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * ������ ���
	 * @param firstname ���
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return �������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * ������ �������
	 * @param surname �������
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return ��������
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * ������ ��������
	 * @param patrname ��������
	 */
	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthdate()
	{
		return birthdate;
	}

	/**
	 * ������ ���� ��������
	 * @param birthdate ���� ��������
	 */
	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	/**
	 * @return ������ ����������
	 */
	public List<ClientDocumentImpl> getDocuments()
	{
		return documents;
	}

	/**
	 * ������ ������ ����������
	 * @param documents ������ ����������
	 */
	public void setDocuments(List<ClientDocumentImpl> documents)
	{
		this.documents = documents;
	}

	/**
	 * @return ������ �������
	 */
	public List<Address> getPostAddr()
	{
		return postAddr;
	}

	/**
	 * ������ ������ �������
	 * @param postAddr ������ �������
	 */
	public void setPostAddr(List<Address> postAddr)
	{
		this.postAddr = postAddr;
	}

	/**
	 * @return ���������� ����������
	 */
	public List<Contact> getContactData()
	{
		return contactData;
	}

	/**
	 * ������ ���������� ����������
	 * @param contactData ���������� ����������
	 */
	public void setContactData(List<Contact> contactData)
	{
		this.contactData = contactData;
	}

	/**
	 * @return �������� �� ����
	 */
	public boolean isUDBO()
	{
		return UDBO;
	}

	/**
	 * ������ ������� ���������� ����
	 * @param UDBO �������� �� ����
	 */
	public void setUDBO(boolean UDBO)
	{
		this.UDBO = UDBO;
	}
}
