package com.rssl.auth.csa.back;

import com.rssl.auth.csa.back.exceptions.ServiceUnavailableException;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.LockMode;
import org.hibernate.Session;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Класс утилитных методов ЦСА.
 */

public class Utils
{
	private static final Pattern IPAS_LOGIN_REGEXP = Pattern.compile("^\\d{10}$");
	private static final Pattern DIPOSABLE_LOGIN_REGEXP = Pattern.compile("^Z\\d{10}$");
	public static final String SERVICE_UNAVAILABLE_MESSAGE = "Система временно недоступна, повторите вход через 15 минут.";

	/**
	 * Проверить удовлетворяет ли строка требованиям логина iPas
	 * @param login логин
	 * @return удовлетворяет или нет
	 */
	public static boolean isIPasLogin(String login)
	{
		return !StringHelper.isEmpty(login) && IPAS_LOGIN_REGEXP.matcher(login).matches();
	}

	/**
	 * Проверить удовлетворяет ли строка требованиям временного логина
	 * @param login логин
	 * @return удовлетворяет или нет
	 */
	public static boolean isDisposableLogin(String login)
	{
		return !StringHelper.isEmpty(login) && DIPOSABLE_LOGIN_REGEXP.matcher(login).matches();
	}

	/**
	 * Замаскировать номер карты согласно требованиям безопасности
	 * @param cardNumber номер карты
	 * @return замаскированная карта
	 */
	public static String maskCard(String cardNumber)
	{
		return MaskUtil.getCutCardNumberForLog(cardNumber);
	}

	/**
	 * @return сгенерированный GUID
	 */
	public static String generateGUID()
	{
		return new RandomGUID().getStringValue();
	}

	/**
	 * Номер основного ТБ
	 * @param tb тб
	 * @return Номер основного ТБ
	 */
	public static String getMainTB(String tb)
	{
		return ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonymAndIdentical(tb);
	}

	/**
	 * Номер основного ТБ
	 * @param cbCode CBCode
	 * @return Номер основного ТБ
	 */
	public static String getMainTBByCBCode(String cbCode)
	{
		return getMainTB(getCutTBByCBCode(cbCode));
	}

	/**
	 * получить номер ТБ из CBCode
	 * @param cbCode CBCode
	 * @return номер ТБ
	 */
	public static String getCutTBByCBCode(String cbCode)
	{
		return cbCode.substring(0, 2);
	}

	/**
	 * Получить номер основного ТБ из CB_CODE с учетом синонимов.
	 * Формат поля @CbCode такой:
	 * Код тербанка – 2 символа
	 * Код регионального банка – 2 символа
	 * Код ОСБ – 4 символа
	 * Все коды указываются слитно без пробелов, при необходимости с лидирующими нулями
	 * @param cbCode значие кода
	 * @return номер основого TB с лидирующими нулями.
	 */
	public static String getTBByCbCode(String cbCode)
	{
		if (StringHelper.isEmpty(cbCode))
			return null;

		return StringHelper.addLeadingZeros(ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(getCutTBByCBCode(cbCode)), 2);
	}

	/**
	 * получить текущую активную(доступную для входа) связку профиля с узлом
	 * Если связи нет, она может быть создана.
	 * @param profileId идентификатор профиля
	 * @param createProfileNodeMode Признак создания привязки профиля к блоку
	 * @return связь профиля с ущлом
	 * @throws Exception
	 */
	public static ProfileNode getActiveProfileNode(final Long profileId, final CreateProfileNodeMode createProfileNodeMode) throws Exception
	{
		if (profileId == null)
		{
			throw new IllegalArgumentException("идентификатор профиля не может быть null");
		}

		return ActiveRecord.executeAtomic(new HibernateAction<ProfileNode>()
		{
			public ProfileNode run(Session session) throws Exception
			{
				Profile profile = Profile.findById(profileId, LockMode.UPGRADE_NOWAIT);
				List<ProfileNode> nodes = ProfileNode.getByProfile(profile);
				if (nodes.isEmpty())
				{
					if (CreateProfileNodeMode.CREATION_DENIED == createProfileNodeMode)
						return null;

					//ни одной нет, создадим новую
					return ProfileNode.create(profile);
				}

				//какие-то связи есть. ищем подходящую связь
				ProfileNode mainProfileNode = getMainProfileNode(nodes);
				if (mainProfileNode == null)
				{
					throw new IllegalStateException("Невалидное состояние системы: профиль " + profile.getId() + " имеет связи с блоками, но ни один из них не хранит остновной профиль");
				}

				//Шаг 1: ищем немигрирующуюся связь основного профиля с неотключенным блоком
				if (mainProfileNode.getNode().isExistingUsersAllowed() && ProfileNode.State.ACTIVE == mainProfileNode.getState())
				{
					//нашли немигрирующуюся связь основного профиля с неотключенным блоком. это нам и надо. основной кейс.
					return mainProfileNode;
				}

				//Шаг 2: вход в основной блок по некоторым причинам недоступен. смотрим разрещено ли входить во временные блоки?
				if (!mainProfileNode.getNode().isUsersTransferAllowed())
				{
					//Клиентам запрещено покидать этот блок. Отказываем в обслуживании
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				//Шаг 3: ищем подходящий временный блок из существующих связей
				boolean hasTemporaryNode = false; //вообще клиент имеет ли временные блоки.
				for (ProfileNode profileNode : nodes)
				{
					hasTemporaryNode |= profileNode.getProfileType() == ProfileType.TEMPORARY;

					if (!profileNode.getNode().isTemporaryUsersAllowed())
					{
						continue; //пропускаем отключенные блоки, содержащие временные профили
					}
					if (ProfileNode.State.PROCESS_MIGRATION != profileNode.getState())
					{
						//нашли немигрирующуюся связь временного профиля с неотключенным блоком.
						//устанавливаем статус "ожидание миграции"
						profileNode.setState(ProfileNode.State.WAIT_MIGRATION);
						profileNode.save();
						return profileNode;
					}
				}
				if (hasTemporaryNode)
				{
					//профиль временный есть, но блок, соотетсвующий ему, отключен. Запрещаем вход клиенту, дабы "не размазывать" временные профили по блокам(СБТ так хочет)
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				//Шаг 4: пробуем отправить клиента в резервный блок.
				Node tempNode = getTempNode(nodes);
				if (tempNode == null)
				{
					//Нет подходящего временного узла для клиента.
					throw new ServiceUnavailableException(SERVICE_UNAVAILABLE_MESSAGE);
				}

				if (CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES == createProfileNodeMode)
					//Создаем связь со временным блоком
					return ProfileNode.create(profile, tempNode, ProfileType.TEMPORARY);

				return null;
			}
		});
	}

	/**
	 * Получение блока, принимающего на обслуживание временных клиентов, который не содержится в уже существющих связях (nodes) клиента.
	 * @param profileNodes список существующих связей клиента сблоками
	 * @return блок дл обслуживания временных клиентов или null
	 * @throws Exception
	 */
	private static Node getTempNode(List<ProfileNode> profileNodes) throws Exception
	{
		if (profileNodes == null)
		{
			throw new IllegalArgumentException("Список связей не может быть null");
		}
		List<Node> temporaryNodes = Node.getTemporaryNodes();
		outerLoop: for (Node temporaryNode : temporaryNodes)
		{
			for (ProfileNode profileNode : profileNodes)
			{
				if (profileNode.getNode().getId().equals(temporaryNode.getId()))
				{
					continue outerLoop;
				}
			}
			return temporaryNode;
		}
		return null;
	}

	/**
	 * по профилю найти основной блок (First) клиента и резервный (Second) в котором он работает
	 * @param profile профиль клиента
	 * @return блоки клиента
	 * @throws Exception
	 */
	public static Pair<ProfileNode, ProfileNode> getFullNodeInfo(Profile profile) throws Exception
	{
		Pair<ProfileNode, ProfileNode> result = new Pair<ProfileNode, ProfileNode>();
		List<ProfileNode> nodes = ProfileNode.getByProfile(profile);
		result.setFirst(getMainProfileNode(nodes));
		result.setSecond(getWaitMigrationNodeProfileNode(nodes));
		return result;
	}

	private static ProfileNode getMainProfileNode(List<ProfileNode> nodes)
	{
		if (nodes == null)
		{
			throw new IllegalArgumentException("Список связей не может быть null");
		}
		for (ProfileNode profileNode : nodes)
		{
			if (profileNode.getProfileType() == ProfileType.MAIN)
			{
				return profileNode;
			}
		}
		return null;
	}

	private static ProfileNode getWaitMigrationNodeProfileNode(List<ProfileNode> nodes)
	{
		if (nodes == null)
		{
			throw new IllegalArgumentException("Список связей не может быть null");
		}
		for (ProfileNode profileNode : nodes)
		{
			if (profileNode.getState() == ProfileNode.State.WAIT_MIGRATION)
			{
				return profileNode;
			}
		}
		return null;
	}
}
