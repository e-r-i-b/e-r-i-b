package com.rssl.auth.csa.wsclient.requests.profile.lock;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.auth.csa.wsclient.requests.RequestDataBase;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * Базовый класс билдера запроса на блокирование профиля клиента в ЦСа
 *
 * @author khudyakov
 * @ created 30.06.15
 * @ $Author$
 * @ $Revision$
 */
public abstract class LockProfileRequestDataBase extends RequestDataBase
{
	private Calendar lockFrom;
	private Calendar lockTo;
	private String reason;
	private String blockerFIO;

	/**
	 * конструктор
	 * @param lockFrom начало блокировки
	 * @param lockTo окончание блокировки
	 * @param reason причина блокировки
	 * @param blockerFIO фио блокирующего сотрудника
	 */
	public LockProfileRequestDataBase(Calendar lockFrom, Calendar lockTo, String reason, String blockerFIO)
	{
		this.lockFrom = lockFrom;
		this.lockTo = lockTo;
		this.reason = reason;
		this.blockerFIO = blockerFIO;
	}

	protected abstract void addUserInfo(Element element);

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		addUserInfo(root);
		XmlHelper.appendSimpleElement(root, RequestConstants.LOCK_FROM_PARAM_NAME, XMLDatatypeHelper.formatDateTime(lockFrom));
		if (lockTo != null)
		{
			XmlHelper.appendSimpleElement(root, RequestConstants.LOCK_TO_PARAM_NAME, XMLDatatypeHelper.formatDateTime(lockTo));
		}
		XmlHelper.appendSimpleElement(root, RequestConstants.REASON_PARAM_NAME, reason);
		XmlHelper.appendSimpleElement(root, RequestConstants.LOCKER_FIO_PARAM_NAME, blockerFIO);
		return request;
	}


}
