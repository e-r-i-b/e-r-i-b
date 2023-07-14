package com.rssl.phizic.gate.claims.sbnkd;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.StringHelper;
import org.hibernate.Session;

import java.util.Calendar;
import java.util.List;

/**
 * Сервис по обработке заявок на выпуск карт и подключение УДБО.
 *
 * @author bogdanov
 * @ created 23.12.14
 * @ $Author$
 * @ $Revision$
 */

public class IssueCardService
{
	private IssueCardDocumentImpl linkWithCards(IssueCardDocumentImpl document) throws GateException
	{
		if (document == null)
		{
			return null;
		}

		List<CardInfoImpl> cards = getCardForDoc(document);
		document.setCardInfos(cards);
		for (CardInfoImpl card : cards)
		{
			card.setParent(document);
		}
		return document;
	}

	/**
	 * @param id идентификатор.
	 * @return получение заявки по id.
	 */
	public IssueCardDocumentImpl getClaim(final Long id) throws GateException
	{
		try
		{
			return linkWithCards(HibernateExecutor.getInstance().execute(new HibernateAction<IssueCardDocumentImpl>()
			{
				public IssueCardDocumentImpl run(Session session) throws Exception
				{
					return (IssueCardDocumentImpl) session.get(IssueCardDocumentImpl.class, id);
				}
			}));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param uid идентфиикатор документа.
	 * @return поиск по ид.
	 */
	public IssueCardDocumentImpl getClaimByUuid(final String uid) throws GateException
	{
		try
		{
			return linkWithCards(HibernateExecutor.getInstance().execute(new HibernateAction<IssueCardDocumentImpl>()
			{
				public IssueCardDocumentImpl run(Session session) throws Exception
				{
					return (IssueCardDocumentImpl) session.getNamedQuery("com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl.getByUUID")
							.setParameter("UUID", uid)
							.uniqueResult();
				}
			}));
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param uuid uuid.
	 * @return поиск инфомрации о выпускаемой карте по uuid.
	 */
	public CardInfoImpl getCardInfoByUuid(final String uuid) throws GateException
	{
		try
		{
			CardInfoImpl cardInfo = HibernateExecutor.getInstance().execute(new HibernateAction<CardInfoImpl>()
			{
				public CardInfoImpl run(Session session) throws Exception
				{
					return (CardInfoImpl) session.getNamedQuery("com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl.getByUUID")
							.setParameter("UUID", uuid)
							.uniqueResult();
				}
			});
			cardInfo.setParent(getClaim(cardInfo.getClaimId()));
			for (int i = 0; i < cardInfo.getParent().getCardInfos().size(); i++)
			{
				if (cardInfo.getUID().equals(cardInfo.getParent().getCardInfos().get(i).getUID()))
					cardInfo.getParent().getCardInfos().set(i, cardInfo);
			}
			return cardInfo;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param document документ для связи.
	 * @return поиск инфомрации о выпускаемой карте по uuid.
	 */
	public List<CardInfoImpl> getCardForDoc(final IssueCardDocumentImpl document) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardInfoImpl>>()
			{
				public List<CardInfoImpl> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.gate.claims.sbnkd.impl.CardInfoImpl.link")
							.setParameter("ID", document.getId())
							.setParameter("creationDate", document.getCreationDate())
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * @param doc добавление или изменения документа.
	 */
	public void addOrUpdate(final IssueCardDocumentImpl doc) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					if (doc.getCreationDate() == null)
						doc.setCreationDate(Calendar.getInstance());
					session.saveOrUpdate(doc);
					for (CardInfoImpl cardInfo : doc.getCardInfos())
					{
						cardInfo.setClaimId(doc.getId());
						cardInfo.setCreationDate(doc.getCreationDate());
						if (StringHelper.isEmpty(cardInfo.getUID()))
						{
							cardInfo.setUID(new RandomGUID().getStringValue());
						}
						session.saveOrUpdate(cardInfo);
					}

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void addOrUpdate(final CardInfoImpl doc) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					addOrUpdate(doc.getParent());

					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void addOrUpdate(final GateDocument document) throws GateException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.saveOrUpdate(document);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public ConcludeEDBODocument getDocument(final long id) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ConcludeEDBODocument>()
			{
				public ConcludeEDBODocument run(Session session) throws Exception
				{
					return (ConcludeEDBODocument) session.get(ClassHelper.loadClass("com.rssl.phizic.business.documents.RemoteConnectionUDBOClaim"), id);
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск заявок
	 * @param ownerId - идентификатор владельца (логина клиента или гостя)
	 * @param isGuest - признак гостя
	 * @return список c данными заявок {id, дата создания, статус, имя 1-ой карты, валюта 1-ой карты, количество карт в заявке}
	 * @throws GateException
	 */
	public List<Object[]> findClaimDataByOwnerId(final Long ownerId, final boolean isGuest) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					return session.getNamedQuery("com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl.findClaimDataByOwnerId")
							.setParameter("ID", ownerId)
							.setParameter("GUEST", isGuest)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Поиск заявок
	 * @param ownerId идентификатор владельца (логина клиента или гостя)
	 * @param isGuest признак гостя
	 * @param period период, за который надо искать заявки (в днях)
	 * @return список c данными заявок {id, дата создания, статус, имя 1-ой карты, валюта 1-ой карты, количество карт в заявке}
	 * @throws GateException
	 */
	public List<Object[]> findClaimDataByOwnerIdAndPeriod(final Long ownerId, final boolean isGuest, final int period) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
			{
				public List<Object[]> run(Session session) throws Exception
				{
					Calendar currDate = DateHelper.getCurrentDate();
					Calendar startDate = DateHelper.addDays(currDate, -period);

					return session.getNamedQuery("com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl.findClaimDataByOwnerIdAndDate")
							.setParameter("ID", ownerId)
							.setParameter("GUEST", isGuest)
							.setParameter("startDate", startDate)
							.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/** Удаление карты
	 * @param cardInfo
	 * @throws GateException
	 */
	public void removeCardInfo(final CardInfo cardInfo) throws GateException
	{
		try
		{
			HibernateExecutor trnExecutor = HibernateExecutor.getInstance();
			trnExecutor.execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					session.delete(cardInfo);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
