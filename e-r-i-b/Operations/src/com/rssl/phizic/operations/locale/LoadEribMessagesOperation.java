package com.rssl.phizic.operations.locale;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.locale.ERIBMessageManager;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.events.UpdateLocaleType;
import com.rssl.phizic.locale.events.UpdateMessagesEvent;
import com.rssl.phizic.locale.replicator.EribMessageReplicator;
import com.rssl.phizic.locale.replicator.SyncMode;
import com.rssl.phizic.locale.services.MultiInstanceEribLocaleService;
import com.rssl.phizic.locale.utils.LocaleHelper;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author koptyaev
 * @ created 24.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class LoadEribMessagesOperation extends OperationBase
{
	private static final String KEY_SEPARATOR = "|";
	private String localeId;
	private Map<String,ERIBStaticMessage> messageMap = new HashMap<String,ERIBStaticMessage>();
	private static final MultiInstanceEribLocaleService localeService = new MultiInstanceEribLocaleService();

	/**
	 * Инициализирует операцию для заданной локали
	 * @param localeId локаль
	 */
	public void initialize(String localeId)
	{
		this.localeId = localeId;
	}

	/**
	 * Читает инпутСтрим и собирает мапу для репликаСорса
	 * @param stream файл как инпутстрим
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void readInputStream(InputStream stream) throws BusinessException, BusinessLogicException
	{

		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();

			Document document = builder.parse(stream);
			Element rootElement = document.getDocumentElement();
			final String localeName = rootElement.getAttributes().item(0).getNodeValue();
			if (StringHelper.isEmpty(localeName))
				throw new BusinessLogicException("В файле для загрузки сообщений не указан язык!");
			if (!StringHelper.equals(localeName,localeId))
				throw new BusinessLogicException("Язык, выбранный для синхронизации не соответствует языку, указанному в файле");
			XmlHelper.foreach(rootElement, LocaleMessageTags.BUNDLE_TAG_NAME, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					String bundleName = element.getAttribute(LocaleMessageTags.BUNDLE_NAME_ATTRIBUTE_NAME);
					getMessages(element,localeName,bundleName);

				}
			});
		}
		catch (BusinessLogicException e)
		{
			throw e;
		}
		catch (SAXParseException e)
		{
			throw new BusinessLogicException("Загружаемый файл не соответствует требуемому формату. Исправьте формат и загрузите документ заново", e);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	private void getMessages(Element element, final String locale, final String bundleName) throws Exception
	{
		XmlHelper.foreach(element, LocaleMessageTags.MESSAGE_TAG_NAME, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				String messageKey = element.getAttribute(LocaleMessageTags.MESSAGE_KEY_ATTRIBUTE_NAME);
				String messageText = element.getTextContent();
				ERIBStaticMessage message = new ERIBStaticMessage();
				message.setLocaleId(locale);
				message.setBundle(bundleName);
				message.setKey(messageKey);
				message.setMessage(messageText);
				if (!LocaleHelper.isDefaultLocale(locale)|| messageKey.endsWith(".atm"))
					messageMap.put(bundleName + KEY_SEPARATOR + messageKey, message);
			}
		});
	}

	/**
	 * Синхронизирует данные из файла с данными в БД
	 * @throws BusinessException
	 */
	public void synch() throws BusinessException
	{
		try
		{
			new EribMessageReplicator(new XmlStaticMessageSource(messageMap), new XmlStaticMessageDestination(localeId, getInstanceName()), SyncMode.UPDATE_ONLY).replicate();
			ERIBLocale locale = localeService.getById(localeId, getInstanceName());
			locale.setActualDate(Calendar.getInstance());
			localeService.update(locale, getInstanceName());
			ERIBMessageManager.update(new UpdateMessagesEvent(UpdateLocaleType.UPDATE_MESSAGES, locale));
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}
}
