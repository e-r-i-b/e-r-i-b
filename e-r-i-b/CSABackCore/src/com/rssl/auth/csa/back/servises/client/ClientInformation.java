package com.rssl.auth.csa.back.servises.client;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.ClientNotFoundException;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.ProfileLock;
import com.rssl.auth.csa.back.servises.ProfileLockCHG071536;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.ermb.ErmbStatus;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author akrenev
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 *
 * информация о клиенте
 */

public class ClientInformation extends ActiveRecord
{
	private static final String LIST_QUERY_NAME = "com.rssl.auth.csa.back.servises.client.list";

	private Long id;
	private String firstname;
	private String surname;
	private String patrname;
	private String document;
	private Calendar birthday;
	private String tb;
	private CreationType creationType;
	private String agreementNumber;
	private String login;
	private String userId;
	private Calendar lastLoginDate;
	private List<ProfileLock> locks;
	private List<ProfileLockCHG071536> locksCHG071536;
	private List<ClientNodeInfo> nodes;
	private List<ERMBConnector> activeErmbConnectors;
	private ErmbStatus ermbStatus = ErmbStatus.NOT_CONNECTED;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return имя
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * @return фамилия
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @return отчество
	 */
	public String getPatrname()
	{
		return patrname;
	}

	/**
	 * @return документ
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * @return ТБ
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @return тип договора
	 */
	public CreationType getCreationType()
	{
		return creationType;
	}

	/**
	 * @return номер договора
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * @return логин
	 */
	public String getLogin()
	{
		return login;
	}

	/**
	 * @return идентификатор в ЦСА
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @return дата последнего входа
	 */
	public Calendar getLastLoginDate()
	{
		return lastLoginDate;
	}

	/**
	 * @return список блокировок
	 */
	public List<ProfileLock> getLocks()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return locks;
	}

	/**
	 * @return Список меток о блокировках пользователя
	 */
	public List<ProfileLockCHG071536> getLocksCHG071536()
	{
		return Collections.unmodifiableList(locksCHG071536);
	}

	/**
	 * @return информация о блоках клиента
	 */
	public List<ClientNodeInfo> getNodes()
	{
		return Collections.unmodifiableList(nodes);
	}

	/**
	 * @return активный телефон ермб
	 */
	public String getErmbPhoneNumber()
	{
		if (CollectionUtils.isEmpty(activeErmbConnectors))
			return null;
		return activeErmbConnectors.get(0).getPhoneNumber();
	}

	/**
	 * @return статус услуги ЕРМБ
	 */
	public ErmbStatus getErmbStatus()
	{
		return ermbStatus;
	}

	void setErmbStatus(ErmbStatus ermbStatus)
	{
		this.ermbStatus = ermbStatus;
	}

	/**
	 * поиск инфы по клиентам
	 * @param fio фио клиента
	 * @param document ДУЛ клиента
	 * @param birthday ДР клиента
	 * @param login логин клиента
	 * @param creationType тип договора
	 * @param agreementNumber номер договора
	 * @param phoneNumber номер телефона
	 * @param tbList список ТБ в которых нужно искать
	 * @param maxResults максимальное количество клиентов
	 * @param firstResult смещение выборки
	 * @return клиенты
	 * @throws ClientNotFoundException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public static final List<ClientInformation> findLike(String fio, String document, Calendar birthday, String login,
	                                                     String creationType, String agreementNumber, String phoneNumber,
	                                                     List<String> tbList, int maxResults, int firstResult) throws ClientNotFoundException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(getHibernateExecutor(), LIST_QUERY_NAME);
			ErmbClientInformationLoader ermbLoader = new ErmbClientInformationLoader();

			if(Utils.isIPasLogin(login))
				query.setParameter("searchType","byIPasLogin");
			else if(StringHelper.isNotEmpty(login))
				query.setParameter("searchType","byLogin");
			else
				query.setParameter("searchType","byProfile");

			query.setParameterList("tbList", tbList);
			query.setParameter("fio", fio);
			query.setParameter("document", document);
			query.setParameter("birthday", birthday);
			query.setParameter("login", login);
			query.setParameter("creationType", creationType);
			query.setParameter("agreementNumber", agreementNumber);
			query.setParameter("phoneNumber", phoneNumber);
			query.setMaxResults(maxResults);
			query.setFirstResult(firstResult);
			List<ClientInformation> csaInfoList = query.executeListInternal();
			return ermbLoader.fillErmbInfo(csaInfoList);
		}
		catch (DataAccessException e)
		{
			throw new ClientNotFoundException(e);
		}
	}

	Profile asProfile()
	{
		Profile profile = new Profile();
		profile.setId(id);
		profile.setFirstname(firstname);
		profile.setPatrname(patrname);
		profile.setSurname(surname);
		profile.setPassport(document);
		profile.setBirthdate(birthday);
		profile.setTb(tb);
		return profile;
	}
}
