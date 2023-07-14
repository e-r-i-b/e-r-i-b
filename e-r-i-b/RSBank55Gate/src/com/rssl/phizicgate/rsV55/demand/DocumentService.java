package com.rssl.phizicgate.rsV55.demand;

import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.gate.messaging.impl.MessageHeadImpl;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.Query;
import org.hibernate.Session;
import org.w3c.dom.Document;

/**
 * @author Krenev
 * @ created 27.08.2007
 * @ $Author$
 * @ $Revision$
 */
public class DocumentService
{
	/**
	 * найти отложенный документ по ID документа
	 * @param id ID документа
	 * @return документ
	 * @throws GateException
	 */
	public PaymentDemandBase findById(final String id, final Class clazz) throws GateException
	{
	    try
	    {
	        return GateRSV55Executor.getInstance().execute(new HibernateAction<PaymentDemandBase>()
	        {
	            public PaymentDemandBase run(Session session) throws Exception
	            {
	                Query query = session.getNamedQuery(clazz.getName() + ".getById");
		            ClaimId claimId = ClaimId.valueOf(id);
		            query.setParameter("appKind", claimId.getApplicationKind());
		            query.setParameter("appKey", claimId.getApplicationKey());
	                return (PaymentDemandBase) query.uniqueResult();
	            }
	        });
	    }
	    catch (Exception e)
	    {
	       throw new GateException(e);
	    }
	}

	/**
	 * Добавить документ в отложенные
	 * @param payment документ
	 * @throws GateException
	 */
	public void add(final PaymentDemandBase  payment) throws GateException
	{
		try
		{
		    GateRSV55Executor.getInstance().execute(new HibernateAction<PaymentDemandBase>()
		    {
		        public PaymentDemandBase run(Session session) throws Exception
		        {
		            session.save(payment);
			        return payment;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	/**
	 * Удалить отложенный документ
	 * @param payment доумент
	 * @throws GateException
	 */
	public void remove(final PaymentDemandBase payment) throws GateException
	{
		try
		{
		    GateRSV55Executor.getInstance().execute(new HibernateAction<PaymentDemandBase>()
		    {
		        public PaymentDemandBase run(Session session) throws Exception
		        {
		            session.delete(payment);
			        return payment;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	/**
	 * Обновить отложеный документ
	 * @param payment документ
	 * @throws GateException
	 */
	public void update(final PaymentDemandBase payment) throws GateException
	{
		try
		{
		    GateRSV55Executor.getInstance().execute(new HibernateAction<PaymentDemandBase>()
		    {
		        public PaymentDemandBase run(Session session) throws Exception
		        {
		            session.update(payment);
			        return payment;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	/**
	 * Провести отложенный документ
	 * @param id оложенного документа
	 * @return XML- ответ
	 * @throws GateException
	 */
	public Document transact(final String id) throws GateException{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);
		ClaimId claimId = ClaimId.valueOf(id);
		GateMessage message = service.createRequest("onLinePaymentTransact_q");
		message.addParameter("appKind", claimId.getApplicationKind());
		message.addParameter("applicationKey",claimId.getApplicationKey());
		try
		{
			return service.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, id, null, null));
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * Удалить проведенный документ
	 * @param id проведенного документа
	 * @return XML- ответ
	 * @throws GateException
	 */
	public Document removeTransact(final String id) throws GateException{
		WebBankServiceFacade service = GateSingleton.getFactory().service(WebBankServiceFacade.class);

		ClaimId claimId = ClaimId.valueOf(id);
		GateMessage message = service.createRequest("deleteDocument_q");
		message.addParameter("applicationKey",claimId.getApplicationKey());

		try
		{
			return service.sendOnlineMessage(message, new MessageHeadImpl(null, null, null, id, null, null));
		}
		catch (GateLogicException e)
		{
			throw new GateException(e);
		}
	}

}
