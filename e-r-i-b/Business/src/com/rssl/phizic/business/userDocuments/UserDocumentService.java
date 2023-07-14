package com.rssl.phizic.business.userDocuments;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.cache.CacheProvider;
import com.rssl.phizic.utils.StringHelper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import java.util.*;

/**
 * @author lukina
 * @ created 17.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class UserDocumentService
{
	private static final SimpleService simpleService = new SimpleService();
	private final Cache userDocuments;

	private static volatile UserDocumentService it;
	private static Object LOCK = new Object();

	public static UserDocumentService get()
	{
		if (it != null)
			return it;

		synchronized (LOCK)
		{
			if (it != null)
				return it;

			it = new UserDocumentService();
			return it;
		}
	}

	private UserDocumentService()
	{
		userDocuments = CacheProvider.getCache("UserDocumentsForLogin");
	}

	private void removeDocument(long loginId)
	{
		userDocuments.remove(Long.toString(loginId));
	}

	/**
	 * ќбновление пользовательского документа.
	 *
	 * @param login логин клиента.
	 * @param type тип документа.
	 * @param value значение.
	 * @throws BusinessException
	 */
	public void resetUserDocument(CommonLogin login, DocumentType type, String value) throws BusinessException
	{
		if (StringHelper.isEmpty(value) || login == null)
			return;

		UserDocument doc = getUserDocumentByLoginAndType(login.getId(), type);
		if (doc != null && doc.getNumber().equals(value))
			return;

		if (doc != null && !doc.getNumber().equals(value))
        {
            InvoiceSubscriptionService service = new InvoiceSubscriptionService();
            service.removeUserDocumentsLinks(login.getId(), type.name());
        }
        else
        {
            doc = new UserDocument();
            doc.setDocumentType(type);
            doc.setLoginId(login.getId());
        }
        doc.setNumber(value);
        addOrUpdate(doc);
	}

	public void addOrUpdate(final UserDocument userDocument) throws BusinessException
	{
		simpleService.addOrUpdate(userDocument);
		removeDocument(userDocument.getLoginId());
	}

	/**
	 * ”даление пользовательского документа.
	 *
	 * @param userDocument пользовательский документ.
	 * @throws BusinessException
	 */
	public void remove(final UserDocument userDocument) throws BusinessException
	{
		simpleService.remove(userDocument);
		removeDocument(userDocument.getLoginId());
	}

	/**
	 * —писок документов клиента
	 * @param loginId - логин клиента
	 * @return - список документов
	 * @throws BusinessException
	 */
	public List<UserDocument> getUserDocumentByLogin(Long loginId)  throws BusinessException
	{
		Element elem = userDocuments.get(Long.toString(loginId));
		Map<DocumentType, UserDocument> d = elem != null ? (Map<DocumentType, UserDocument>) elem.getObjectValue() : null;
		if (d != null)
		{
			List<UserDocument> docs = new ArrayList<UserDocument>(d.size());
			docs.addAll(d.values());
			return docs;
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(UserDocument.class);
		criteria.add(Expression.eq("loginId", loginId));
		criteria.addOrder(Order.asc("id"));

		List<UserDocument> docs = simpleService.find(criteria);
		Map<DocumentType, UserDocument> docMap = new HashMap<DocumentType, UserDocument>();
		for (UserDocument doc : docs)
			docMap.put(doc.getDocumentType(), doc);

		userDocuments.put(new Element(Long.toString(loginId), docMap));
		return docs;
	}

	/**
	 * @param loginId логин.
	 * @param type тип документа.
	 * @return документ по логину и типу.
	 * @throws BusinessException
	 */
	public UserDocument getUserDocumentByLoginAndType(Long loginId, DocumentType type)
	{
		try
		{
			Element elem = userDocuments.get(Long.toString(loginId));
			if (elem == null)
			{
				for (UserDocument doc : getUserDocumentByLogin(loginId))
				{
					if (doc.getDocumentType() == type)
						return doc;
				}
			}
			else
			{
				Map<DocumentType, UserDocument> docMap = (Map<DocumentType, UserDocument>) elem.getObjectValue();
				return docMap.get(type);
			}

			return null;
		}
		catch(BusinessException e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * ѕолучение документа клиента.
	 *
	 * @param login логин.
	 * @param type тип документа.
	 * @return сери€ и номер документа.
	 */
	public String getUserDocumentNumber(CommonLogin login, DocumentType type)
	{
		if (login == null || login instanceof GuestLogin)
			return "";

		UserDocument doc = getUserDocumentByLoginAndType(login.getId(), type);
		if (doc != null)
			return (StringHelper.isEmpty(doc.getSeries()) ? "" : (doc.getSeries() + " ")) + doc.getNumber();
		else
			return "";
	}

	/**
	 * ѕоиск документа по идентификатору
	 * @param id  - идентификатор доукмента
	 * @return документ
	 * @throws BusinessException
	 */
	public UserDocument getUserDocumentById(Long id)  throws BusinessException
	{
		return simpleService.findById(UserDocument.class, id);
	}
}
