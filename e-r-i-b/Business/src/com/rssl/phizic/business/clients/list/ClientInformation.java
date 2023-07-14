package com.rssl.phizic.business.clients.list;

import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.ermb.ErmbStatus;

import java.util.Calendar;
import java.util.List;

/**
 * @author akrenev
 * @ created 25.10.13
 * @ $Author$
 * @ $Revision$
 *
 * информация о клиенте
 */

public class ClientInformation
{
	private Long id;
	private String firstname;
	private String surname;
	private String patrname;
	private String document;
	private Calendar birthday;
	private String tb;
	private String agreementNumber;
	private CreationType creationType;
	private String login;
	private String userId;
	private Calendar lastLoginDate;
	private List<ClientBlock> blocks;
	private List<ClientNodeInfo> nodes;
	private String ermbActivePhone;
	private ErmbStatus ermbStatus;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * задать фамилия
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
	 * @return документ
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * задать документ
	 * @param document документ
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать дату рождения
	 * @param birthday дата рождения
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ТБ
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * задать ТБ
	 * @param tb ТБ
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return номер договора
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * задать номер договора
	 * @param agreementNumber номер договора
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return тип договора
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * задать тип договора
	 * @param creationType тип договора
	 */
	public void setCreationType(CreationType creationType)
	{
		this.creationType = creationType;
	}

	/**
	 * @return логин
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * задать логин
	 * @param login логин
	 */
	public void setLogin(String login)
	{
		this.login = login;
	}

	/**
	 * @return идентификатор в ЦСА
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * задать идентификатор в ЦСА
	 * @param userId идентификатор в ЦСА
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	/**
	 * @return дата последнего входа
	 */
	public Calendar getLastLoginDate()
	{
		return lastLoginDate;
	}

	/**
	 * задать дату последнего входа
	 * @param lastLoginDate дата последнего входа
	 */
	public void setLastLoginDate(Calendar lastLoginDate)
	{
		this.lastLoginDate = lastLoginDate;
	}

	/**
	 * @return список блокировок клиента
	 */
	public List<ClientBlock> getBlocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return blocks;
	}

	/**
	 * задать список блокировок клиента
	 * @param blocks список блокировок клиента
	 */
	public void setBlocks(List<ClientBlock> blocks)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.blocks = blocks;
	}

	/**
	 * @return информация о блоках
	 */
	public List<ClientNodeInfo> getNodes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return nodes;
	}

	/**
	 * задать информацию о блоках
	 * @param nodes информация
	 */
	public void setNodes(List<ClientNodeInfo> nodes)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.nodes = nodes;
	}

	/**
	 * @return активный телефон ЕРМБ
	 */
	public String getErmbActivePhone()
	{
		return ermbActivePhone;
	}

	/**
	 * @param ermbActivePhone активный телефон ЕРМБ
	 */
	public void setErmbActivePhone(String ermbActivePhone)
	{
		this.ermbActivePhone = ermbActivePhone;
	}

	/**
	 * @return статус услуги ЕРМБ
	 */
	public ErmbStatus getErmbStatus()
	{
		return ermbStatus;
	}

	/**
	 * @param ermbStatus статус услуги ЕРМБ
	 */
	public void setErmbStatus(ErmbStatus ermbStatus)
	{
		this.ermbStatus = ermbStatus;
	}
}
