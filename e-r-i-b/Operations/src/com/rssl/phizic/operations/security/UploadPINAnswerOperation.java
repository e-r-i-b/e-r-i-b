package com.rssl.phizic.operations.security;

import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.auth.pin.PINService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.hibernate.Session;

/**
 * @author Roshka
 * @ created 18.08.2006
 * @ $Author$
 * @ $Revision$
 */

public class UploadPINAnswerOperation extends OperationBase
{
	private static final String STATE_UPLOADED = "U";

	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final PINService pinService = new PINService();
	private static final SimpleService simpleService = new SimpleService();

	private String answerXml;
	private static final String STATE_NEW = "N";

	public String getAnswerXml()
	{
		return answerXml;
	}

	public void setAnswerXml(String answerXml)
	{
		this.answerXml = answerXml;
	}

	@Transactional
	public void upload() throws BusinessException, BusinessLogicException
	{
		Document document;
		final String number;
		final String regionBank;
		final String branch;
		final String office;
		final NodeList hashNodes;
		try
		{
			document = XmlHelper.parse(answerXml);
			if (document.getElementsByTagName("answer").getLength()!=1)
			{
				throw new BusinessException("Некорректный файл с результатами.");
			}
			Element root = document.getDocumentElement();
			number = XmlHelper.getSimpleElementValue(root, "number");
			regionBank = XmlHelper.getSimpleElementValue(root, "regionBank");
			branch = XmlHelper.getSimpleElementValue(root, "branch");
			office = XmlHelper.getSimpleElementValue(root, "office");
			hashNodes = XmlHelper.selectNodeList(root, "hashes/hash");
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
					for (int i = 0; i < hashNodes.getLength(); i++)
					{
						Element element = (Element) hashNodes.item(i);

						final String userId = element.getAttribute("login");
						final String hash = XmlHelper.getElementText(element);
						//todo тут так можно, т.к. испольуем только латиницу.
						processEnvelope( number, userId, regionBank, hash.trim().replaceAll("[\t\n\r]","").toUpperCase() );
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
		   throw new BusinessLogicException(e);
		}

		log.info("Обработан ответ на запрос о печати PIN-конвертов № " + number + "контент: " + answerXml);

	}

	private void processEnvelope(String number, String userId, String departmentId, String hash) throws Exception
	{
		//todo сейчас формат xml не совпадает с реально передаваемыми данными, необходимо разделить на две
		PINEnvelope envelope = pinService.findEnvelope( Long.valueOf(number), userId );
		Department department = simpleService.findById(Department.class, Long.parseLong( departmentId ));
		if( envelope == null || department == null || ( !department.getId().equals(envelope.getDepartmentId()) ) )
		{
			String notFoundMessage = "Не найден PIN-конверт, номером запроса: " +
					number + ", пользователь: " + userId;
			UnknownPINEnvelopeException notFoundException = new UnknownPINEnvelopeException(notFoundMessage);
			log.error(notFoundMessage, notFoundException);

			throw notFoundException;
		}

		if ( !envelope.getState().equals(STATE_NEW) )
		{
			String wrongStateMessage = "PIN-конверт с номером запроса " +
					number + " для пользователя " + userId + " уже был обработан ранее.";
			OldPINEnvelopeException wrongStateException = new OldPINEnvelopeException(wrongStateMessage);
			log.error(wrongStateMessage, wrongStateException);
			throw wrongStateException;
		}

		//set hash and save hashes
		envelope.setValue(hash);
		envelope.setState(STATE_UPLOADED);

		pinService.update( envelope );
	}
}