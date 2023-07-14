package com.rssl.phizic.business.persons.csa;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSAResponseConstants;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.ProfileNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.clients.*;
import com.rssl.phizic.gate.csa.Profile;
import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Calendar;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author mihaylov
 * @ created 03.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Сервис для работы с профилем клиента в ЦСА Бек
 */
public class ProfileService
{
	private static final String BIRTH_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * Поиск профиля клиента в ЦСА по шаблону
	 * @param template - шаблон профиля клиента
	 * @return профиль клиента с историей изменений
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public Profile findProfileWithHistory(Profile template) throws BusinessLogicException, BusinessException
	{
		return findProfileWithHistory(template.getSurName(),template.getFirstName(),template.getPatrName(),
									  template.getPassport(),template.getBirthDay(),template.getTb());
	}

	/**
	 * Поиск профиля клиента в ЦСА по клиенту
	 * @param client - клиент
	 * @return профиль клиента с историей изменений
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public Profile findProfileWithHistory(Client client) throws BusinessLogicException, BusinessException
	{
		try
		{
			return findProfileWithHistory(client.getSurName(), client.getFirstName(), client.getPatrName(), ClientHelper.getClientWayDocument(client).getDocSeries(), client.getBirthDay(), new SBRFOfficeCodeAdapter(client.getOffice().getCode()).getRegion());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск профиля клиента в ЦСА по клиенту
	 * @param guid - клиент
	 * @return профиль клиента с историей изменений
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public Profile findProfileWithHistory(GUID guid) throws BusinessLogicException, BusinessException
	{
		return findProfileWithHistory(guid.getSurName(), guid.getFirstName(), guid.getPatrName(), guid.getPassport(), guid.getBirthDay(), guid.getTb());
	}

	/**
	 * Поиск профиля клиента в ЦСА
	 * @param surName - фамилия
	 * @param firstName - имя
	 * @param patrName - отчество
	 * @param passport - данные паспорта
	 * @param birthDate - дата рождения
	 * @param tb - тербанк
	 * @return профиль клиента с историей изменений
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	public Profile findProfileWithHistory(String surName, String firstName, String patrName, String passport, Calendar birthDate, String tb) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.findProfileInformationRq(surName, firstName, patrName, passport, birthDate, tb);

			return parseResult(result, new Profile());
		}
		catch (ProfileNotFoundException ignore)
		{
			return null;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создать профиль клиента в ЦСА
	 * @param newProfile - новый профиль
	 * @return созданный профиль с привязкой к блоку. Если профиль с переданными данными был в ЦСА, то возвращаем его.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public Profile createProfileByTemplate(Profile newProfile) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.createProfileRq(newProfile.getSurName(),newProfile.getFirstName(),newProfile.getPatrName(),
					newProfile.getPassport(),newProfile.getBirthDay(),newProfile.getTb());

			return parseResult(result, new Profile());
		}
		catch (ProfileNotFoundException ignore)
		{
			return null;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Создать профиль клиента в ЦСА
	 * @param client - клиент
	 * @return созданный профиль с привязкой к блоку. Если профиль с переданными данными был в ЦСА, то возвращаем его.
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public Profile createProfileByClient(Client client) throws BusinessLogicException, BusinessException
	{
		return createProfileByTemplate(fillProfileTemplate(client));
	}

	/**
	 * Поиск профиля клиента в ЦСА по шаблону
	 * @param template шаблон профиля клиента
	 * @return профиль клиента с историей изменений и полной информацией об узлах в которых работал клиент
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public ProfileWithFullNodeInfo findProfileWithFullNodeInfo(Profile template) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.findProfileInformationWithNodeInfoRq(template.getSurName(), template.getFirstName(), template.getPatrName(),
					template.getPassport(), template.getBirthDay(), template.getTb());

			return parseResult(result, new ProfileWithFullNodeInfo());
		}
		catch (ProfileNotFoundException ignore)
		{
			return null;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск списка карт клиента для профиля
	 * @param template шаблон профиля клиента
	 * @return список карт клиента
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public List<String> findProfileCardNumberList(Profile template) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.findProfileCardNumberListRq(template.getSurName(),template.getFirstName(),template.getPatrName(),
																	template.getPassport(),template.getBirthDay(),template.getTb());
			return parseCardList(result);
		}
		catch (ProfileNotFoundException ignore)
		{
			return Collections.emptyList();
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Поиск списка карт клиента для профиля
	 * @param userInfo информация профиля клиента
	 * @return список карт клиента
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public List<String> findProfileCardNumberList(UserInfo userInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.findProfileCardNumberListRq(userInfo.getSurname(),userInfo.getFirstname(),userInfo.getPatrname(),
					userInfo.getPassport(),userInfo.getBirthdate(), userInfo.getTb());
			return parseCardList(result);
		}
		catch (ProfileNotFoundException ignore)
		{
			return Collections.emptyList();
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * поставить лок на профиль для исполнения документа сотрудником
	 * @param userInfo информация о клиенте
	 * @return поставился ли лок
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public boolean lockProfileForExecuteDocument(UserInfo userInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.sendLockProfileForExecuteDocument(userInfo);
			return Boolean.parseBoolean(XmlHelper.getSimpleElementValue(result.getDocumentElement(), "lock"));
		}
		catch (ProfileNotFoundException ignore)
		{
			return false;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * снять лок с профиля после исполнения документа сотрудником
	 * @param userInfo информация о клиенте
	 * @return снялся ли лок
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public boolean unlockProfileForExecuteDocument(UserInfo userInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document result = CSABackRequestHelper.sendUnlockProfileForExecuteDocument(userInfo);
			return Boolean.parseBoolean(XmlHelper.getSimpleElementValue(result.getDocumentElement(), "unlock"));
		}
		catch (ProfileNotFoundException ignore)
		{
			return false;
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	private Profile fillProfileTemplate(Client client)
	{
		Profile profileTemplate = new Profile();
		profileTemplate.setSurName(client.getSurName());
		profileTemplate.setFirstName(client.getFirstName());
		profileTemplate.setPatrName(client.getPatrName());
		profileTemplate.setBirthDay(client.getBirthDay());
		List<? extends ClientDocument> documents = client.getDocuments();
		Collections.sort(documents, new DocumentTypeComparator());
		ClientDocument document = documents.get(0);
		profileTemplate.setPassport(StringUtils.deleteWhitespace(document.getDocSeries() + document.getDocNumber()));
		profileTemplate.setTb(client.getOffice().getCode().getFields().get("region"));
		return profileTemplate;
	}

	private <P extends Profile> P parseResult(Document result, P destination) throws BusinessException
	{
		try
		{
			Element profileElement = XmlHelper.selectSingleNode(result.getDocumentElement(), CSAResponseConstants.PROFILE_INFO_TAG);
			P profile = parseProfile(profileElement, destination);

			Element profileHistoryElement = XmlHelper.selectSingleNode(result.getDocumentElement(), CSAResponseConstants.PROFILE_HISTORY_TAG);
			if(profileHistoryElement != null)
			{
				NodeList profileHistoryList = XmlHelper.selectNodeList(profileHistoryElement, CSAResponseConstants.PROFILE_HISTORY_INFO_TAG);
				for(int i = 0; i < profileHistoryList.getLength(); i++)
				{
					Element node = (Element)profileHistoryList.item(i);
					profile.addHistory(parseProfile(node, new Profile()));
				}
			}

			profile.setNodeId(getLong(result, CSAResponseConstants.NODE_ID_TAG));
			if (destination instanceof ProfileWithFullNodeInfo)
				((ProfileWithFullNodeInfo) destination).setWaitMigrationNodeId(getLong(result, CSAResponseConstants.WAIT_MIGRATION_NODE_ID_TAG));

			return profile;
		}
		catch (TransformerException e)
		{
			throw new BusinessException(e);
		}
	}

	private Long getLong(Document result, String tagName)
	{
		String value = XmlHelper.getSimpleElementValue(result.getDocumentElement(), tagName);
		if (StringHelper.isEmpty(value))
			return null;
		return Long.valueOf(value);
	}

	private <P extends Profile> P parseProfile(Element profileElement, P destination)
	{
		destination.setSurName(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.SURNAME_TAG));
		destination.setFirstName(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.FIRSTNAME_TAG));
		destination.setPatrName(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.PATRNAME_TAG));
		destination.setPassport(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.PASSPORT_TAG));
		destination.setBirthDay(XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.BIRTHDATE_TAG)));
		destination.setTb(XmlHelper.getSimpleElementValue(profileElement, CSAResponseConstants.TB_TAG));
		return destination;
	}

	private List<String> parseCardList(Document result) throws BusinessException
	{
		final List<String> cardList = new ArrayList<String>();
		try
		{
			Element nodesElement = XmlHelper.selectSingleNode(result.getDocumentElement(), CSAResponseConstants.CARD_NUMBER_LIST_TAG);
			XmlHelper.foreach(nodesElement, CSAResponseConstants.CARD_NUMBER_TAG, new ForeachElementAction()
				{
					public void execute(Element element) throws GateException, GateLogicException
					{
						cardList.add(element.getTextContent());
					}
				});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
		return cardList;
	}
}
