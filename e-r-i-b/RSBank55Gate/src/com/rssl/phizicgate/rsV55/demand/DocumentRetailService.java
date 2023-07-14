package com.rssl.phizicgate.rsV55.demand;

import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.messaging.MessagingLogEntry;
import com.rssl.phizic.logging.messaging.MessageLogService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.writers.MessageLogWriter;
import com.rssl.phizic.gate.payments.PaymentsConfig;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.rsV55.bankroll.AccountImpl;
import com.rssl.phizicgate.rsV55.data.GateRSV55Executor;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Novikov_A
 * Date: 13.03.2007
 * Time: 15:33:20
 * To change this template use File | Settings | File Templates.
 */
//TODO перейти на использование DocumentService
public class DocumentRetailService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final System SYSTEM_IDENTYFICATOR = System.retail;
	private static final String RESPONCE_MESSAGE_TYPE = "base";
	private static final String EMPTY_RESPONCE = "";

	private PaymentDemandBase insertDocument(final PaymentDemandBase document, final Long claimId, final String type ) throws GateException
	{
		try
		{
			 return GateRSV55Executor.getInstance().execute(new HibernateAction<PaymentDemandBase>()
            {
				public PaymentDemandBase run(Session session) throws Exception
				{
					fillDocument(document);
					
					session.save(document);
					session.flush();

					String documentXML = XmlHelper.createXmlFromObject(document);

					writeLog(documentXML, type, document, claimId);

					return document;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new GateException(e);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
    }

	private void writeLog(String documentXML, String type, PaymentDemandBase document, Long claimId) throws GateException
	{
		MessageLogWriter messageLogWriter = MessageLogService.getMessageLogWriter();
				
		MessagingLogEntry logEntry = MessageLogService.createLogEntry();
		logEntry.setMessageRequest(documentXML);  // XML- документ
		logEntry.setMessageType(type);
		logEntry.setMessageRequestId(document.getApplicationKey()); // id документа
		logEntry.setMessageResponse(EMPTY_RESPONCE);
		logEntry.setMessageResponseId("0");
		logEntry.setSystem(SYSTEM_IDENTYFICATOR);
		logEntry.setExecutionTime(0L);

		try
		{
			messageLogWriter.write(logEntry);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

//TODO Убрать отсюда, вынести в сендеры
	private void fillDocument(PaymentDemandBase document) throws GateException, GateLogicException
	{
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
		// Уникальный ключ
	    final Long APP_KIND = propertiesPaymentsConfig.getApplicationKind();
	    // Номер операциониста
	    final Long OPER = propertiesPaymentsConfig.getOperateNumber();

		document.setOper(OPER);
		AccountImpl account = new AccountImpl();
		ClaimId id = new ClaimId();
		id.setApplicationKind(APP_KIND);

		if (document.getId() == null)
		{
		 id.setApplicationKey(ApplicationKeyGenerator.getApplicationKey(APP_KIND));
		 document.setId(id);
		}

	    if (document instanceof ExpandedPaymentDemand)
	    {
		   ExpandedPaymentDemand expandedPayment = (ExpandedPaymentDemand) document;
		   Remittee rceiverDemand = expandedPayment.getReceiver();
		   if (rceiverDemand != null)
		   {
		      rceiverDemand.setBankCode(expandedPayment.getIsCur() == 0L ? 3L : 6L);
		      rceiverDemand.setBranch(account.getBranch());
		      expandedPayment.setReceiver(rceiverDemand);
		   }
		   rceiverDemand = expandedPayment.getDestination();
		   if (rceiverDemand != null)
		   {
		      rceiverDemand.setBranch(account.getBranch());
		      expandedPayment.setDestination(rceiverDemand);
		   }
	    }
	}

	private String getBankCode (final Long branch) throws GateException
	{
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);

		try
		{
			final Long psNum = propertiesPaymentsConfig.getContactRetailId();

			 return GateRSV55Executor.getInstance().execute(new HibernateAction<String>()
            {
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("GetContactCodeCurrentBank");
					query.setReadOnly(true).setFlushMode(FlushMode.NEVER);
					query.setParameter("FNCash", branch);
					query.setParameter("psNum", psNum);

					return query.uniqueResult().toString();
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new GateException(e);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

//TODO Убрать отсюда, вынести в сендеры
	private void fillContactDocument(ContactDocument document) throws GateException
	{
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
		// Уникальный ключ
	    final Long APP_KIND = propertiesPaymentsConfig.getApplicationKind();
	    // Номер операциониста
	    final Long OPER = propertiesPaymentsConfig.getOperateNumber();
		// платёжная система
		final Long CONTACT_ID = propertiesPaymentsConfig.getContactRetailId();

		ClaimId id = new ClaimId();
		id.setApplicationKind(APP_KIND);
		try
		{
		   id.setApplicationKey(ApplicationKeyGenerator.getApplicationKey(APP_KIND));
		}
        catch (GateLogicException e)
		{
			throw new GateException(e);
		}

		document.setId(id);
		document.setOper(OPER);
		document.setPayerPoint(getBankCode (document.getDepartment()));
		document.setPaymentSystemNumber( CONTACT_ID );
	}

	private Long GetMaxReferenc (final long department) throws GateException
	{
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);

        try
		{
		    final Long CONTACT_RETEIL_ID = propertiesPaymentsConfig.getContactRetailId();

			return GateRSV55Executor.getInstance().execute(new HibernateAction<Long>()
			{
			    public Long run(Session session) throws Exception
			    {
			        Query query = session.getNamedQuery("GetMaxReference");
			        query.setReadOnly(true).setFlushMode(FlushMode.NEVER);
					query.setParameter("branch", department);
				    query.setParameter("psNum", CONTACT_RETEIL_ID);

			        return (Long)query.uniqueResult();
			    }
			});
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	private Long GetMaxDocumentNumber (final long department, final Date transferenceDate) throws GateException
	{
		PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);

		Calendar newDate = DateHelper.toCalendar(transferenceDate);
		newDate.set(Calendar.DATE, 1);
		newDate.get(Calendar.MONTH);
		final Date dateStart = newDate.getTime();

	    newDate = DateHelper.toCalendar(transferenceDate);
	    newDate.add(Calendar.MONTH, 1);
		newDate.set(Calendar.DATE, 1);
		newDate.get(Calendar.MONTH);

		final Date dateEnd = newDate.getTime();		

        try
		{
		    final Long CONTACT_RETEIL_ID = propertiesPaymentsConfig.getContactRetailId();

			return GateRSV55Executor.getInstance().execute(new HibernateAction<Long>()
			{
			    public Long run(Session session) throws Exception
			    {
			        Query query = session.getNamedQuery("GetMaxDocumentNumber");
			        query.setReadOnly(true).setFlushMode(FlushMode.NEVER);
					query.setParameter("branch", department);
				    query.setParameter("psNum", CONTACT_RETEIL_ID);
				    query.setParameter("dateStart", dateStart);
				    query.setParameter("dateEnd", dateEnd);

			        return (Long)query.uniqueResult();
			    }
			});
		}
		catch (Exception e)
		{
		   throw new GateException(e);
		}
	}

	private ContactDocument insertContactDocument(final ContactDocument document, final Long claimId, final String type) throws GateException
	{
		Long department = document.getDepartment();
		Long referenc = GetMaxReferenc(department) ;
		if (referenc == null)
		   referenc = department * 10000000L;

		referenc++;
		document.setReferenc(referenc);

		Long transferenceNumber = GetMaxDocumentNumber (department, document.getTransferenceDate());
		if (transferenceNumber == null)
		   transferenceNumber = 0L;

		transferenceNumber++;
		document.setTransferenceNumber(transferenceNumber);
//TODO: если для точки получателя есть запись для pscntfld.FieldNo = 2, то numTransferenceStr должно быть уникальным (16 цифр)		

		try
		{
			 return GateRSV55Executor.getInstance().execute(new HibernateAction<ContactDocument>()
            {
				public ContactDocument run(Session session) throws Exception
				{
					fillContactDocument(document);

					session.save(document);
					session.flush();

					String documentXML = XmlHelper.createXmlFromObject(document);

					writeLog(documentXML, type, document, claimId);

					return document;
				}
			}
			);
		}
		catch(ConstraintViolationException e)
		{
			throw new GateException(e);
		}
		catch (GateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
    }

	private ContactDocument findContactDocument(final String appKey, final Long appKind) throws GateException
	{
       PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);

       try
        {
            final Long CONTACT_RETEIL_ID = propertiesPaymentsConfig.getContactRetailId();

	        return GateRSV55Executor.getInstance().execute(new HibernateAction<ContactDocument>()
            {
                public ContactDocument run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("GetContactPayment");
                    query.setReadOnly(true).setFlushMode(FlushMode.NEVER);
		            query.setParameter("appKey", appKey);
                    query.setParameter("appKind", appKind);
	                query.setParameter("psNum", CONTACT_RETEIL_ID);

                    return (ContactDocument) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
           throw new GateException(e);
        }
	}

	private ContactDocument UpdateContactDocument(final ContactDocument document) throws GateException
	{
        PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
		// Уникальный ключ
	    final Long APP_KIND = propertiesPaymentsConfig.getApplicationKind();

		final ContactDocument doc = findContactDocument(document.getApplicationKey(), APP_KIND);
		if (doc != null)
		{   
			if ("D".equals(doc.getState()) || "G".equals(doc.getState()) || "H".equals(doc.getState()) ||
				"Y".equals(doc.getState()) || "W".equals(doc.getState()))
			{
				doc.setSent(0L);
				doc.setState("G");
				doc.setReceiverSecondName(document.getReceiverSecondName());
				doc.setReceiverLastName(document.getReceiverLastName());
				doc.setReceiverFirstName(document.getReceiverFirstName());
				doc.setReceiverBornDate(document.getReceiverBornDate());
				doc.setAdditionalIinformation(document.getAdditionalIinformation());
				doc.setTransferenceDate(document.getTransferenceDate());
				doc.setTransferenceDateEnd(DateHelper.add(document.getTransferenceDate(), new DateSpan(0,0,30)));
			}
			else
			   if (!"S".equals(doc.getState()) && !"P".equals(doc.getState()) && !"O".equals(doc.getState()))
			   {
				   doc.setReceiverSecondName(document.getReceiverSecondName());
				   doc.setReceiverLastName(document.getReceiverLastName());
				   doc.setReceiverFirstName(document.getReceiverFirstName());
				   doc.setReceiverBornDate(document.getReceiverBornDate());
				   doc.setAdditionalIinformation(document.getAdditionalIinformation());
				   doc.setTransferenceDate(document.getTransferenceDate());
				   doc.setTransferenceDateEnd(DateHelper.add(document.getTransferenceDate(), new DateSpan(0,0,30)));
			   }

		    try
		    {
			   return GateRSV55Executor.getInstance().execute(new HibernateAction<ContactDocument>()
               {
			      public ContactDocument run(Session session) throws Exception
				  {
				     session.update(doc);

					 return doc;
			   	  }
			   });
		    }
		    catch(ConstraintViolationException e)
		    {
			   throw new GateException(e);
		    }
		    catch (GateException e)
		    {
			   throw e;
		    }
		    catch (Exception e)
		    {
			   throw new GateException(e);
		    }	
		}

		return null;
	}

	private ContactDocument RecallContactDocument(final ContactDocument document) throws GateException
	{
	   PaymentsConfig propertiesPaymentsConfig = ConfigFactory.getConfig(PaymentsConfig.class);
	   // Уникальный ключ
	   final Long APP_KIND = propertiesPaymentsConfig.getApplicationKind();

	   final ContactDocument doc = findContactDocument(document.getApplicationKey(), APP_KIND);
	   if (doc != null)
	   {
	      if ("S".equals(doc.getState()) && "P".equals(doc.getState()))
		  {
		     doc.setSent(3L);
			 doc.setState("R");
          }
		  else
		  {
		     doc.setSent(0L);
			 doc.setState("U");
		  }
		  doc.setTransferenceDate(document.getTransferenceDate());

		  try
		  {
		     return GateRSV55Executor.getInstance().execute(new HibernateAction<ContactDocument>()
	         {
			    public ContactDocument run(Session session) throws Exception
				{
				   session.update(doc);

				   return doc;
				}
			  });
		  }
		  catch(ConstraintViolationException e)
		  {
		     throw new GateException(e);
	      }
		  catch (GateException e)
		  {
		     throw e;
		  }
		  catch (Exception e)
		  {
		     throw new GateException(e);
 	      }
	   }

	   return null;
	}

	public PaymentDemandBase sendPaymentDocument(PaymentDemandBase document, Long documentId) throws GateException
	{
		return insertDocument(document, documentId, "payment");
	}

	public PaymentDemandBase sendContactDocument(ContactDocument document, Long documentId) throws GateException
	{
		return insertContactDocument(document, documentId, "payment");
	}

	public  PaymentDemandBase  sendContactEditDocument(ContactDocument document, Long documentId) throws GateException
	{
	   try
	   {
		   ContactDocument contactDocument = UpdateContactDocument(document);
		   log.info("Отредактирован документ PAYMENTS.ID="+documentId);
		   return contactDocument;
	   }
	   catch (GateException e)
	   {
           log.error("Не удалось отредактировать документ PAYMENTS.ID="+documentId);
	   }
	   return null;
	}

	public  PaymentDemandBase  sendContactRecallDocument(ContactDocument document, Long documentId) throws GateException
	{
	   try
	   {
		   ContactDocument contactDocument = RecallContactDocument(document);
		   log.info("Отзван документ PAYMENTS.ID="+documentId);
		   return contactDocument;
	   }
	   catch (GateException e)
	   {
           log.error("Не удалось отозвать документ PAYMENTS.ID="+documentId);
	   }
	   return null;
	}

	public  PaymentDemandBase  sendDocument(PaymentDemandBase document, Long documentId) throws GateException
	{
	    return  insertDocument(document, documentId, "claim");
	}
}
