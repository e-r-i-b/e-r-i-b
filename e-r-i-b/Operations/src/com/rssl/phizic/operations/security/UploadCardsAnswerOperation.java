package com.rssl.phizic.operations.security;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.auth.passwordcards.*;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.hibernate.Session;

import java.util.List;
import java.util.ArrayList;

/**
 * @author Khivin
 * @ created 01.10.2007
 * @ $Author$
 * @ $Revision$
 */
public class UploadCardsAnswerOperation extends OperationBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final PasswordCardService cardService = new PasswordCardService();

	/**
	 * Загрузить ответ на запрос генерации паролей
	 * @param answerXml ответ
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void upload( String answerXml) throws BusinessException, BusinessLogicException
	{
		final Document document;
		final String number;
		final String regionBank;
		final String branch;
		final String office;
		final int keyCount;
		final NodeList hashes;
		try
		{
			document = XmlHelper.parse(answerXml);
			Element root = document.getDocumentElement();
			number = XmlHelper.getSimpleElementValue(root, "number");
			regionBank = XmlHelper.getSimpleElementValue(root, "regionBank");
			branch = XmlHelper.getSimpleElementValue(root, "branch");
			office = XmlHelper.getSimpleElementValue(root, "office");
			keyCount = Integer.parseInt( XmlHelper.getSimpleElementValue(root, "keyCount"));
     		hashes = XmlHelper.selectNodeList(root, "hashes");
		}
		catch (Exception e)
		{
			throw new BusinessException("Ошибка при обработке xml-файла: " + e.getMessage(), e);
		}

		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					for (int i = 0; i < hashes.getLength(); i++)
					{
						final Element hashesElement = (Element) hashes.item(i);
						final NodeList hashList = XmlHelper.selectNodeList( hashesElement, "hash");
						final PasswordCard card = cardService.findByNumber( hashesElement.getAttribute("card"));
						if (card == null)
						{
							throw new BusinessException("Карта ключей не найдена №" + number);
						}
						if (keyCount != hashList.getLength())
						{
							throw new BusinessException(
									"Некорректный формат файла. "
									+ "Ожидамое количество паролей " + keyCount
								    + ", фактически " + hashList.getLength());	
						}
						// список хэшей из документа в список
						final List<CardPassword> passwords = new ArrayList<CardPassword>();
						for (int k = 0; k < keyCount; k++)
						{
							final Element hashElement = (Element) hashList.item(k);
							final int key;
							try
							{
								key = Integer.parseInt( hashElement.getAttribute("key"));
							}
							catch (NumberFormatException e)
							{
								throw new BusinessException( "Некорректное значение поля \"key\". Ожидается число.", e);								
							}

							final String h = hashElement.getFirstChild().getTextContent();
							final String hash = h.trim().replaceAll("[\t\n\r]", "");
							final CardPassword cardPassword = new CardPassword();

							cardPassword.setNumber( key);
							cardPassword.setValue( hash.toCharArray());

							passwords.add( cardPassword);
						}
						// список паролей сопоставить карте
						try
						{
							cardService.addPasswordsAndMarkAsNew( card, passwords);
						}
						catch(InvalidPasswordCardStateException e)
						{
							throw new BusinessException("Некорректное состояние карты ключей №" + number + ".");														
						}
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
		    throw new BusinessLogicException(e);
		}

		log.info("Обработан ответ на запрос о печати карт паролей № " + number + "контент: " + answerXml);
	}
}
