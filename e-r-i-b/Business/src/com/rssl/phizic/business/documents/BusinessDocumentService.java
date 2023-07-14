package com.rssl.phizic.business.documents;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.claims.DocumentGuid;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.extendedattributes.ClientHistoryExtendedAttribute;
import com.rssl.phizic.business.login.GuestHelper;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.hibernate.exception.LockAcquisitionException;

import java.util.*;

/**
 * @author Evgrafov
 * @ created 01.11.2005
 * @ $Author: gololobov $
 * @ $Revision:6095 $
 */

public class BusinessDocumentService
{
    //*********************************************************************//
    //***************************  CLASS MEMBERS  *************************//
    //*********************************************************************//

    private static final String QUERY_PREFIX = BusinessDocumentService.class.getName() + ".";
    private static final SimpleService simpleService = new SimpleService();

	private static final String EXECUTED = "EXECUTED";
	private static final String DISPATCHED = "DISPATCHED";

	public BusinessDocument findById(final Long id) throws BusinessException
	{
		return findById(id, null);
	}

	/**
	 * �������� �������� �� ������������� ��������� �������� ����� ����������.
	 * @param id ������������.
	 * @param lockMode ����� ���������� ��� null ���� ��������� ������������ �������� ��������.
	 * @return �������� ��� null.
	 */
	public BusinessDocument findById(final Long id, final LockMode lockMode) throws BusinessException
	{
	    try
	    {
	        return HibernateExecutor.getInstance().execute(new HibernateAction<BusinessDocument>()
	        {
	            public BusinessDocument run(Session session) throws Exception
	            {
		            Map<String, Object> parameters = new HashMap<String, Object>();
		            parameters.put("id",       id);
		            parameters.put("lockMode", lockMode);

		            return new BusinessDocumentServiceChain<BusinessDocument>()
				            .link(new UniqueBusinessDocumentChainElement<BusinessDocument>("com.rssl.phizic.business.documents.BusinessDocumentService.getById"))
				            .link(new UniqueBusinessDocumentChainElement<BusinessDocument>("com.rssl.phizic.business.documents.ExtendedLoanClaim.getById"))
				            .link(new UniqueBusinessDocumentChainElement<BusinessDocument>("com.rssl.phizic.business.documents.LoanCardClaim.getById"))
				            .whileNull(session, parameters);
	            }
	        });
	    }
	    catch (LockAcquisitionException e)
	    {
		    throw e;
	    }
	    catch (Exception e)
	    {
	       throw new BusinessException(e);
	    }
	}

	/**
     * ��������� ��������. ���������� ��� ������ �������������� ������ �������� �� �����������
     * �������������� ������� �������
     * @param document ��������
     * @throws BusinessException
     */
    public BusinessDocument addOrUpdate(BusinessDocument document) throws BusinessException
    {
        return simpleService.addOrUpdate(document);
    }

	//TODO ������� ��� ���������� ������ �� ������ ������� � ��������� �����. �����������: �������� O.E.
	@Deprecated //������� ���������� �������� ���������� �� ������� ���� �� ������, �������� ������� ������� � DELETED
    public void remove(BusinessDocument document) throws BusinessException
    {
        simpleService.remove(document);
    }

	public GateExecutableDocument findByExternalId(final String externalId) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance(). execute(new HibernateAction<GateExecutableDocument>()
		    {
		        public GateExecutableDocument run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getByExternalId");
		            query.setParameter("externalId", externalId);
			        GateExecutableDocument document = (GateExecutableDocument) query.uniqueResult();
			        if (document != null)
				        return document;

			        query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.getByExternalId");
			        query.setParameter("externalId", externalId);
			        return (ExtendedLoanClaim) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ����� ������� �� �������
	 * @param stateCode ������ �������
	 * @return ������ �������� �� �������� state
	 * @throws BusinessException
	 */
	public List<BusinessDocument> findByState(final String stateCode) throws BusinessException
	{
		 try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocument>>()
		    {
		        public List<BusinessDocument> run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getByState");
		            query.setParameter("state", stateCode);

		            return (List<BusinessDocument>) query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �������������� �������� �� ������� ��� Job
	 * @param stateCode - ������
	 * @param maxRows - ������������ ���������� ����� (��� �������� ��������� � Job)
	 * @param jobName - ��������� �����
	 * @param fromDate - �����, ����� �������� ������������ ��������� ���������� �� �����
	 * @param paymentSortOrder - ������� ���������� ��������
	 * @param excludingProviders - �������������� �����������, ������� ������� �������� �� ������, null - �� �����������
	 * @param iqwUuid - uuid �������� Iqwave
	 * @return ������ �������������� ��������
	 */
	public List<Long> findByStateForJob(final String stateCode, final int maxRows, final String jobName,final Calendar fromDate, final String paymentSortOrder, final List<Long> excludingProviders, String iqwUuid) throws BusinessException
	{
		try
		{
			String queryName = QUERY_PREFIX + "getByStateForJobByDocumentIdTable";
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(null), queryName);
			query.setParameter("orderType", paymentSortOrder);
			query.setParameter("state", stateCode);
			query.setParameter("fromDate", fromDate);
			query.setParameter("nextProcessDate", Calendar.getInstance());
			if (maxRows != -1)
				query.setMaxResults(maxRows);
			query.setParameter("jobName",jobName);
			if (CollectionUtils.isNotEmpty(excludingProviders))
				query.setParameterList("excludingProviders", excludingProviders);

			if (StringHelper.isNotEmpty(iqwUuid))
				query.setParameter("iqwUuid", iqwUuid);

			return query.executeListInternal();
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ����� ���������� �� ��������������� �����������
	 * @param providerIds ������ ��������������� �����������
	 * @param states ���������, � ������� ��������� �������
	 * @param formNames ����� ���� ��������, null - �� �����������
	 * @param fromDate �����, ��������� ����� ����, ��� ������ ���������� ������
	 * @param firstResult ����� ������� ��������
	 * @param listLimit ���������� ��������� � �������
	 * @return ������ ��������
	 * @throws BusinessException
	 */
	public List<BusinessDocument> findPaymentsByProviderIds(final List<Long> providerIds, final String[] states, final String[] formNames, final Calendar fromDate, final int firstResult, final int listLimit) throws BusinessException
	{
		if(CollectionUtils.isEmpty(providerIds))
			throw new IllegalArgumentException("������ ��������������� ����������� �� ����� ���� ������");

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocument>>()
			{
				public List<BusinessDocument> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findPaymentsByProviderId");
					query.setParameterList("providerIds", providerIds);
					query.setParameter("fromDate", fromDate);
					query.setParameterList("states", states);
					query.setParameterList("formNames", formNames);
					query.setFirstResult(firstResult);
					query.setMaxResults(listLimit);

					//noinspection unchecked
					return (List<BusinessDocument>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �������������� �������� �� ������� ��� Job,
	 * �����: ����� ���������� � ������ ����������� ������� ��� �������� ��������������� ����������,
	 * ������� ��� �������� ����������
	 * @param stateCode ������ �������
	 * @param maxRows ������������ ���������� ����� (��� �������� ��������� � Job)
	 * ���� maxRows == -1, �� ����������� ���������� ������� �� ����������
	 * @param paymentSortOrder ������� ���������� ��������
	 * @return ������ �������������� �������� �� �������� state
	 * @throws BusinessException
	 */
	public List<Long> findByStateForJob(final String stateCode, final int maxRows, final String jobName, final String paymentSortOrder) throws BusinessException
	{
		try
		{
	        String queryName = QUERY_PREFIX + "getByStateForJobByDocumentIdTable";
	        ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(null), queryName);
	        query.setParameter("orderType", paymentSortOrder);
	        query.setParameter("state", stateCode);
	        if (maxRows != -1)
	            query.setMaxResults(maxRows);
	        query.setParameter("jobName",jobName);

	        return query.executeListInternal();
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ �������������� �������� ������ �� ������� ��� Job,
	 * �����: ����� ���������� � ������ ����������� ������� ��� �������� ��������������� ����������,
	 * ������� ��� �������� ����������
	 * @param stateCode ������ �������
	 * @param maxRows ������������ ���������� ����� (��� �������� ��������� � Job)
	 * ���� maxRows == -1, �� ����������� ���������� ������� �� ����������
	 * @param paymentSortOrder ������� ���������� ��������
	 * @param numberOfHourAfterDocumentIsExpired ����� �����, ����� �������� �������� ��������� ����������.
	 * @param numberOfResendPayment ����� ������� ������.
	 * @return ������ �������������� �������� �� �������� state
	 * @throws BusinessException
	 */
	public List<Long> findByStateForJob(final String stateCode, final int maxRows, final String jobName,
	                                    final String iqwaveAdapterName, final String paymentSortOrder,
	                                    final Integer numberOfHourAfterDocumentIsExpired,
	                                    final Integer numberOfResendPayment) throws BusinessException
	{
		try
		{
			String queryName = QUERY_PREFIX + "getByStateForJobByDocumentIdTable";
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(null), queryName);
			query.setParameter("orderType", paymentSortOrder);
			query.setParameter("state", stateCode);
			if (maxRows != -1)
				query.setMaxResults(maxRows);
			query.setParameter("jobName",jobName);
			query.setParameter("iqwaveAdapter", iqwaveAdapterName);
			query.setParameter("needESBPaymentOnly", "1");
			query.setParameter("expireHour", numberOfHourAfterDocumentIsExpired);
			query.setParameter("numberOfResendPayment", numberOfResendPayment);

			return query.executeListInternal();
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ������ ���������� �� ������� ��� ��������� ������
	 * �����: ����� ���������� � ������ ����������� ������� ��� �������� ��������������� ����������,
	 * ������� ��� �������� ����������
	 * @param stateCode ������� �������
	 * @param maxRows ������������ ���������� ����� (��� �������� ��������� � Job)
	 * ���� maxRows == -1, �� ����������� ���������� ������� �� ����������
	 * @param jobName ����
	 * @return
	 * @throws BusinessException
	 */
	public List<BusinessDocument> findDocumentsByStateAndFormForJob(final String[] stateCode, final String[] formNames, final int maxRows, final String jobName) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocument>>()
		    {
		        public List<BusinessDocument> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getDocumentsByStateAndFormForJob");
		            query.setParameterList("states", stateCode);
			        query.setParameterList("formNames", formNames);
			        if (maxRows != -1)
			            query.setMaxResults(maxRows);
			        query.setParameter("jobName", jobName);

		            return query.list();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public Long findIdByGuid(final String guid) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Long>()
		    {
		        public Long run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getIdByGuid");
		            query.setParameter("guid", guid);
			        return query.uniqueResult() != null ? ((DocumentGuid)query.uniqueResult()).getDocumentId() : null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public DocumentGuid findIdByGuidAndPerson(final String guid, final Long personId) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<DocumentGuid>()
		    {
		        public DocumentGuid run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getDocumentGuidByGuidAndPerson");
		            query.setParameter("guid", guid);
			        query.setParameter("personId", personId);
		            return (DocumentGuid) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public String findGuidById(final Long id) throws BusinessException
	{
		try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
		    {
		        public String run(Session session) throws Exception
		        {
		            Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getGuidById");
		            query.setParameter("id", id);
		            return ((DocumentGuid)query.uniqueResult()).getGuid();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	public List<BusinessDocument> findByloginAndState(final Login login, final String stateCode) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocument>>()
			{
				public List<BusinessDocument> run(Session session)
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findByLoginAndState");
					query.setParameter("owner", login.getId());
					query.setParameter("state", stateCode);
					return (List<BusinessDocument>) query.list();
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������������� ��������� �� ���������� ������������� ������. ��������� ������ � ���������� ���������
	 * @param mainLogin - �������� ����� - ���, � �������� ������ ������������� ��� �������, ��� �������
	 * @param logins - ������, ������� ������� �����������������
	 * @param stateCodes - ������� ����������, � ������� �� �������� "����������������"
	 * @return ���������� ��������������� ����������. 0 - ���� �� ���� �������� �� ������������
	 * @throws BusinessException
	 */
	public Integer findAndUpdateLogin(final Long mainLogin, final List<Long> logins, final List<String> stateCodes) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Integer>()
			{
				public Integer run(Session session) throws Exception
				{
					ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.documents.BusinessDocumentService.findAndUpdateLogin");
					query.setParameter("mainOwner", mainLogin);
					query.setParameterList("owners", logins);
					query.setParameterList("states", stateCodes);
					Integer documentsCount = query.executeUpdate();

					query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.business.documents.ExtendedLoanClaim.findAndUpdateLogin");
					query.setParameter("mainOwner", mainLogin);
					query.setParameterList("owners", logins);
					query.setParameterList("states", stateCodes);
					Integer claimsCount = query.executeUpdate();

					return documentsCount + claimsCount;
				}
			});
		}
		catch(Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������� �� ������� ���������� ���������
	 * ���� ������� ��������� ����������, �� ������������ ������� ����������  �� ����. �����������:
	 * 1. EXECUTED - ��������
	 * 2. ���� ��� �����������, �� ���������� DISPATCHED (��������������)
	 * 3. ���� ��� �����������, �� ���������� null
	 * @param indexTaxPayments - ������� ��������� ����������
	 * @param login - ����� ������������
	 * @return - ��� ������������ ������� ���������� ��������� � ������� �������
	 * @throws BusinessException
	 */
	public Map<String, String> findStateByIndexTaxPayment(final String[] indexTaxPayments, final CommonLogin login) throws BusinessException
	{
		 try
		{
		    return HibernateExecutor.getInstance().execute(new HibernateAction<Map<String, String>>()
		    {
		        public Map<String, String> run(Session session) throws Exception
		        {
		            Map<String, String> statusTaxDocuments = new HashMap<String, String>();

			        Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getByStateByTaxIndex");
		            query.setParameterList("taxDocumentNumbers", indexTaxPayments);
			        query.setParameter("form_name", FormConstants.FNS_PAYMENT_FORM);
			        query.setParameter("loginId", login.getId());

			        List<Object[]> results = (List<Object[]>) query.list();
			        if (results.isEmpty())
			            return statusTaxDocuments;

			        for(Object[] result : results)
			        {
				        String taxIndex = (String) result[0];
				        String state = (String) result[1];
				        String stateUpdate = state.equals(EXECUTED) || state.equals(DISPATCHED) ? state : null;				        
				        String taxIndexState = statusTaxDocuments.get(taxIndex);

				        boolean isNeedChangeState;
				        if (taxIndexState == null)
				            isNeedChangeState = true;  // ���������� � ������ ��������� �������� ��� �� �������, �������� ���
				        else if (taxIndexState.equals(EXECUTED))
				            isNeedChangeState = false; // ������  EXECUTED ��������� �� �����
				        else if(taxIndexState.equals(DISPATCHED))
				        {
					        if (stateUpdate.equals(EXECUTED))
						        isNeedChangeState = true;    // ������� ����������� �������� �� ������� ���������� ��������� - ������� ������
					        else
						        isNeedChangeState = false;  // � ������ ������� (null,DISPATCHED) �� ���������� ������, ����� �� ��������
				        }
				        else
				            isNeedChangeState = true;   // �����-�� ������ ������  - ��������

				        if (isNeedChangeState)
					        statusTaxDocuments.put(taxIndex, stateUpdate);
			        }

		            return statusTaxDocuments;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new BusinessException(e);
		}
	}

	/**
	 * ����� ���������� ���������� ������ �� ������� �� ��������� ������� �� ���
	 * @param stateCode - ������ ������
	 * @param startFromRow  - ����� � ������ ������
	 * @param max - ���������� ����������� �������
	 * @return ������ ������
	 */
	public List<PFRStatementClaim> findPFRClaimByState(final String stateCode, final int startFromRow,final int max) throws Exception
	{
		return HibernateExecutor.getInstance().execute(new HibernateAction<List<PFRStatementClaim>>()
		{
			public List<PFRStatementClaim> run(Session session) throws Exception
			{
				Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findPFRClaimByState");
				query.setParameter("stateCode", stateCode);
				query.setMaxResults(max);
				query.setFirstResult(startFromRow);

				return (List<PFRStatementClaim>) query.list();

			}
		});
	}

	/**
	 * �������� ������ ������ �� ����������� ����� �� ��������� ������.
	 * @param fromDate ���� �
	 * @param toDate ���� ��
	 * @param maxResults ������������ ���-�� ������������ ������� (null ���� ������� ���)
	 * @return ������ ������ �� ���������� �����
	 * @throws BusinessException
	 */
	public List<VirtualCardClaim> getVirtualCardClaims(final Calendar fromDate, final Calendar toDate , final Integer maxResults) throws BusinessException
	{
		try
		{
			//��� ������������� ������, ����������� ������� BUG032930, �������� ����������. �������� ������ - BUG032949.
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<VirtualCardClaim>>()
			{
				public List<VirtualCardClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getVirtualCardClaims");
					query.setParameter("fromDate", fromDate);
					query.setParameter("toDate", toDate);
					//���� ������� � ����� �� �������, �� ���������� ���
					if (maxResults != null)
					{
						query.setMaxResults(maxResults);
					}
					return query.list();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ������ ���-�������� �������
	 * @param person - �������
	 * @param maxResults - ������������ ���-�� ������������ ������� (null ���� ������� ���)
	 * @return ����������� ���-��������
	 * @throws BusinessException
	 */
	public List<BusinessDocumentBase> getSmsPayments(final Person person, final Integer maxResults) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<BusinessDocumentBase>>()
			{
				public List<BusinessDocumentBase> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findPaymentsInChannel");
					query.setMaxResults(maxResults);
					query.setParameter("loginId", person.getLogin().getId());
					List<BusinessDocumentBase> documents = query.list();

					query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.findPaymentsInChannel");
					query.setMaxResults(maxResults);
					query.setParameter("loginId", person.getLogin().getId());
					query.setParameter("channel", CreationType.sms.toValue());
					List<ExtendedLoanClaim> claims = query.list();

					documents.addAll(claims);
					return documents;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������, ���� �� ��� ������������ � ����:
	 * - ������ �� ��������� ������� ������ �� ����
	 * - ������ �� ��������� ��������� �����  ������ �� ����
	 * - �������������� ������ �� ������
	 * - �������������� ������ �� ��������� �����
	 * @param claimBase - ������
	 * @return true - ����, false - ���
	 * @throws BusinessException
	 */
	public boolean isExistDispatchedClaim(LoanClaimBase claimBase) throws BusinessException
	{
		DetachedCriteria criteria = getExistDispatchedCriteria(claimBase.getClass());
		if(!StringHelper.isEmpty(claimBase.getIdentityFieldValue()))
		{
			criteria.createAlias("attributes", "attribut");
			criteria.add(Expression.eq("attribut.name", claimBase.getIdentityFieldName()));
			criteria.add(Expression.eq("attribut.stringValue", claimBase.getIdentityFieldValue()));
		}
		List<BusinessDocument> result = simpleService.find(criteria);
		return !CollectionUtils.isEmpty(result);
	}
	/**
	 *
	 * @param claim ������� ������ �� ������.
	 * @return ���������, ���� �� ��� ������������ � ����.
	 * @throws BusinessException
	 */
	public boolean isExistDispatchedClaim(ShortLoanClaim claim) throws BusinessException
	{
		DetachedCriteria criteria = getExistDispatchedCriteria(claim.getClass());
		List<BusinessDocument> result = simpleService.find(criteria);
		return !CollectionUtils.isEmpty(result);
	}
	
	private DetachedCriteria getExistDispatchedCriteria(Class clazz) throws BusinessException
	{
		Login login  = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		DetachedCriteria criteria = DetachedCriteria.forClass(clazz);
		criteria.add(Expression.eq("state.code", DISPATCHED));
		criteria.add(Expression.eq("clientLogin", login));
		return criteria;
	}

	/**
	 * ��������� ���� ������� ������� �� �������������� �������
	 * @param documentId ������������� �������
	 * @param loginId ��������� ���������
	 * @return ��� ������� �������
	 * @throws BusinessException
	 */
	public String getPaymentStateById(final Long documentId, final Long loginId) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<String>()
			{
				public String run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.getPaymentStateById");
					query.setParameter("id", documentId);
					query.setParameter("loginId", loginId);
					return (String) query.uniqueResult();
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}


	/**
	 * ��������� ����������� �������� ����������� ������ �� ������
	 *
	 * @param  login ����� �������
	 * @return true - �������� ������ ���������, � ��������� ������ false
	 * @throws BusinessException
	 */
	public boolean isExtendedLoanClaimAvailable(final Login login, final Person person) throws BusinessException
	{
		LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);

		final List<String> errorCodes      = loanClaimConfig.getAllowedRefusalCodes();
		final Calendar     maxDispatchDate = DateHelper.getOnlyDate(DateHelper.addDays(Calendar.getInstance(), -loanClaimConfig.getExtendedLoanClaimLifetimeDays()));

		//��� ��� ���������� ���������� ������ ��� �����, ������ ����� ������� ������ �� ������
		if (ApplicationUtil.isAdminApplication() && login instanceof GuestLogin)
		{
			GuestLogin guestLogin = (GuestLogin) login;
			if (StringHelper.isEmpty(guestLogin.getAuthPhone()))
				return true;
		}

		//��� ������� ��������� ������� �������� ��. ���� ��� ���, �� ��������� ��������� ������ �� ������
		if (!(login instanceof GuestLogin))
		{
			if (!PersonHelper.hasRegularPassportRF(person))
				return false;
		}

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = null;
					if (login instanceof GuestLogin)
					{
						GuestLogin guestLogin = (GuestLogin) login;
						String phoneNumber = GuestHelper.PHONE_FORMAT.translate(guestLogin.getAuthPhone());
						query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.allowExtendedLoanClaimForGuest");
						query.setParameter("guestId", guestLogin.getGuestCode());
						query.setParameter("phone", phoneNumber);
					}
					else
					{
						query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.allowExtendedLoanClaimForClient");
						query.setParameter("loginId", login.getId());
					}

					query.setMaxResults(1);
					query.setParameter("maxDispatchDate", maxDispatchDate);

					if (CollectionUtils.isNotEmpty(errorCodes))
					{
						query.setParameterList("errorCodes", errorCodes);
					}
					else
					{
						query.setParameter("errorCodes", "");
					}

					return (query.uniqueResult() == null);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ����������� ������ �� ������ �� �������������� �������
	 * @param login - ����� �������
	 * @param period - ������ � ������� �� ������� �������� ������
	 * @param maxResult ���������� �������, ������� ����� ��������
	 * @return - ������ ����������� ������ �� ������
	 * @throws BusinessException
	 */
	public List<ExtendedLoanClaim> findExtendedLoanClaimByLogin(final Login login, final int period, final int maxResult) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedLoanClaim>>()
			{
				public List<ExtendedLoanClaim> run(Session session) throws Exception
				{
					Query query = null;
					if (login instanceof GuestLogin)
					{
						GuestLogin guestLogin = (GuestLogin) login;
						String phoneNumber = GuestHelper.PHONE_FORMAT.translate(guestLogin.getAuthPhone());
						Calendar currDate = DateHelper.getCurrentDate();
						Calendar startDate = DateHelper.addDays(currDate, -period);
						query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.findByGuestLogin")
							.setLong("guestId", guestLogin.getGuestCode())
							.setString("phone", phoneNumber)
							.setCalendar("startDate", startDate);
					}
					else
					{
						query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.findByClientLogin")
							.setLong("loginId", login.getId())
							.setInteger("period", period);
					}
					if (maxResult > 0)
					{
						query.setMaxResults(maxResult);
					}
					return (List<ExtendedLoanClaim>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� ����������� ������ �� ������ �� �������������� �������
	 * @param login ����� �������
	 * @param period ������ � ������� �� ������� �������� ������
	 * @return ������ ����������� ������ �� ������
	 * @throws BusinessException
	 */
	public List<ExtendedLoanClaim> findExtendedLoanClaimByLogin(final Login login, final int period) throws BusinessException
	{
		return findExtendedLoanClaimByLogin(login, period, -1);
	}

    /**
     * ����� ������ �� ��������� �����
     * @param login - ����� �������
     * @return ������ c ������� ������
     * @throws com.rssl.phizic.gate.exceptions.GateException
     */
    public List<Object[]> findCardClaimByGuestId(final Login login, final int period) throws GateException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<Object[]>>()
            {
                public List<Object[]> run(Session session) throws Exception
                {
                    GuestLogin guestLogin = (GuestLogin) login;
                    Calendar currDate = DateHelper.getCurrentDate();
                    Calendar startDate = DateHelper.addDays(currDate, -period);
                    return session.getNamedQuery("com.rssl.phizic.business.documents.LoanCardClaim.getByGuestId")
                        .setParameter("ownerGuestId", guestLogin.getGuestCode())
                        .setParameter("phone", guestLogin.getAuthPhone())
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

	/**
     * ����� ������ �� ��������� ����� �� �� ������ �������
     * @param login - ����� �������
	 * @param period - ������ � �������, �� ������� ������ ������ �� ����. �����
	 * @param maxResult - ���������� �������, ������� ���������� ��������
     * @return ������ c ������� ������
     * @throws com.rssl.phizic.gate.exceptions.GateException
     */
    public List<LoanCardClaim> findCardClaimByOwnerLoginId(final Login login, final int period, final int maxResult) throws GateException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<LoanCardClaim>>()
            {
                public List<LoanCardClaim> run(Session session) throws Exception
                {
	                Query query = session.getNamedQuery("com.rssl.phizic.business.documents.payments.LoanCardClaim.findByClientLogin")
                            .setLong("loginId", login.getId())
                            .setInteger("period", period);
                    if (maxResult > 0)
                    {
                        query.setMaxResults(maxResult);
                    }
                    return (List<LoanCardClaim>) query.list();
                }
            });
        }
        catch (Exception e)
        {
            throw new GateException(e);
        }
    }

    /**
     * ���� ��������� ��������� �������� ������ �� ������ �� �������� "�������������� ������".
     *
     * @param loginId ����� id �������
     * @return ShortLoanClaim
     * @throws BusinessException
     */
    public ShortLoanClaim findDispatchedShortLoanClaimByLatestCreationDate(final Long loginId) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<ShortLoanClaim>()
            {
                public ShortLoanClaim run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findDispatchedShortLoanClaimByLatestCreationDate");
                          query.setLong("loginId", loginId);
                          query.setMaxResults(1);

                    return (ShortLoanClaim) query.uniqueResult();
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * �������� ������ �������������� �������� ������������ � �� "���" �� �������
	 * @param stateCode - ������ ���������
	 * @param maxRows - ������������ ���������� ����� (��� �������� ��������� � �����)
	 * @param jobName - �������� �����
	 * @return ������ �������������� ��������
	 */
	public List<Long> getDepoCODDocuments(final String stateCode, final int maxRows, final String jobName) throws BusinessException
	{
		try
		{
			String queryName = QUERY_PREFIX + "getDepoCodDocuments";
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(null), queryName);
			query.setParameter("state", stateCode);
			query.setParameter("jobName", jobName);
			if (maxRows != -1)
				query.setMaxResults(maxRows);
			return query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public List<Object[]> getCreateInvoiceSubscriptionPayments(final Long loginId, final Calendar fromDate,List<String> stateCodes) throws BusinessException
	{
		try
		{
			String queryName = QUERY_PREFIX + "selectCreateInvoiceSubscriptionPayments";
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(null), queryName);
			query.setParameter("login_id", loginId);
			query.setParameter("fromDate", fromDate);
			query.setParameter("state_codes", stateCodes);
			return query.executeListInternal();
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������ �� ������ ��� ��������� ���������
	 * @param stateCode ������ ������
	 * @param dateCreated ���� ��������
	 * @return ������ ������
	 * @throws BusinessException
	 */
	public List<ExtendedLoanClaim> findOldClaim(final String stateCode, final Calendar dateCreated) throws BusinessException
	{
		throw new UnsupportedOperationException("������������ sql-������, ���������� ����������!");
	}

	/**
	 * ����� ����������� ������ �� ������ �� operationUID
	 * @param operationUID
	 * @return - ����������� ������ �� ������
	 * @throws BusinessException
	 */
	public ExtendedLoanClaim findExtendedLoanClaimByUID(final String operationUID) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<ExtendedLoanClaim>()
			{
				public ExtendedLoanClaim run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.getByUID");
					query.setParameter("operation_uid", operationUID);
					ExtendedLoanClaim document = (ExtendedLoanClaim) query.uniqueResult();
					return document;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������ �� ������ �� ������ � ������ ���� ��������
	 * @param bkiNumber - ������� ������ �� ������
	 * @param minConfirmDate - ���� ������ �������� ������������� ����� ������ ������ (��� ����������� ��������� �������)
	 * @return List<ExtendedLoanClaim> - ������ ������ �� ������
	 * @throws BusinessException
	 */
	public List<ExtendedLoanClaim> findLoanClaimsByNumbers(final String bkiNumber, final Calendar minConfirmDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ExtendedLoanClaim>>()
			{
				public List<ExtendedLoanClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.findClaimsByNumbers");
					query.setParameter("bkiConfirmDate", minConfirmDate);
					query.setParameter("bkiNumber", bkiNumber);
					//noinspection unchecked
					return (List<ExtendedLoanClaim>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ��� ����������� ���� ����������
	 * @param documentIds - �������������� ����������, ����������� ���� ������� ��������
	 * @return ����������� ����
	 * @throws BusinessException
	 */
	public Collection<ClientHistoryExtendedAttribute> findExtendedFields(final List<Long> documentIds) throws BusinessException
	{
		try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientHistoryExtendedAttribute>>()
            {
                public List<ClientHistoryExtendedAttribute> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.documents.BusinessDocumentService.findExtendedFields");
                    query.setParameterList("ids", documentIds);

                    //noinspection unchecked
                    return ((List<ClientHistoryExtendedAttribute>) query.list());
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
	}

	/**
	 * �������� ��� ����������� ���� ��������� ������
	 * @param documentIds - �������������� ������, ����������� ���� ������� ��������
	 * @param fromDate - ����������� ���� �������� ���������
	 * @param toDate - ������������ ���� �������� ���������
	 * @return ����������� ����
	 * @throws BusinessException
	 */
	public Collection<ClientHistoryExtendedAttribute> findLoanClaimsExtendedFields(final List<Long> documentIds, final Calendar fromDate, final Calendar toDate) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientHistoryExtendedAttribute>>()
            {
                public List<ClientHistoryExtendedAttribute> run(Session session) throws Exception
                {
                    Query query = session.getNamedQuery("com.rssl.phizic.business.documents.ExtendedLoanClaim.findExtendedFields");
                    query.setParameter("fromDate", fromDate);
                    query.setParameter("toDate", toDate);
                    query.setParameterList("ids", documentIds);

                    //noinspection unchecked
                    return ((List<ClientHistoryExtendedAttribute>) query.list());
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * �������� ��� ����������� ���� ������ LoanCardClaim
	 * @param documentIds - �������������� ������, ����������� ���� ������� ��������
	 * @param fromDate    - �����������  ���� �������� ���������
	 * @param toDate      - ������������ ���� �������� ���������
	 * @return ����������� ����
	 * @throws BusinessException
	 */
	public Collection<ClientHistoryExtendedAttribute> findLoanCardClaimsExtendedFields(final List<Long> documentIds, final Calendar fromDate, final Calendar toDate) throws BusinessException
    {
        try
        {
            return HibernateExecutor.getInstance().execute(new HibernateAction<List<ClientHistoryExtendedAttribute>>()
            {
                public List<ClientHistoryExtendedAttribute> run(Session session) throws Exception
                {
	                Query query = session.getNamedQuery("com.rssl.phizic.business.documents.payments.LoanCardClaim.findExtendedFields");
	                query.setParameter    ("fromDate", fromDate);
	                query.setParameter    ("toDate",   toDate);
	                query.setParameterList("ids",      documentIds);

	                List<ClientHistoryExtendedAttribute> attributes = new ArrayList<ClientHistoryExtendedAttribute>();
	                for (Object result : query.list())
	                {
		                Object[] items = (Object[]) result;

		                attributes.add(new ClientHistoryExtendedAttribute((Long)   items[0],   // id
				                                                          (Long)   items[0],   // paymentId
				                                                          (String) items[1],   // name
				                                                          (String) items[2],   // type
				                                                                   items[3])); // value
	                }

	                return attributes;
                }
            });
        }
        catch (Exception e)
        {
            throw new BusinessException(e);
        }
    }

	/**
	 * �������� ����������� �������� ������ �� ��������� �����
	 * @param login - �����
	 * @return true, ���� �������� ������ ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public boolean isLoanCardClaimAvailable(final Login login) throws BusinessException, BusinessLogicException
	{
		if (CardsUtil.hasClientActiveCreditCard())
			return false;

		return !isExistLoanCardClaim(login);
	}

	/**
	 * @param  login ������
	 * @return ���������, ���� �� ��� ������������ � ���� ������ � �������� "�������" ��� "��������������".
	 * @throws BusinessException
	 */
	public boolean isExistLoanCardClaim(final Login login) throws BusinessException
	{
		final Calendar maxDispatchDate = DateHelper.getOnlyDate(DateHelper.addDays(Calendar.getInstance(), -31));

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = null;
					if (login instanceof GuestLogin)
					{
						GuestLogin guestLogin = (GuestLogin) login;
						String phoneNumber = GuestHelper.PHONE_FORMAT.translate(guestLogin.getAuthPhone());
						query = session.getNamedQuery("com.rssl.phizic.business.documents.LoanCardClaim.isExistLoanCardClaimForGuest");
						query.setParameter("phone", phoneNumber);
					}
					else
					{
						query = session.getNamedQuery("com.rssl.phizic.business.documents.LoanCardClaim.isExistLoanCardClaimForClient");
						query.setParameter("loginId", login.getId());
						query.setMaxResults(1);
					}
					query.setParameter("maxDispatchDate", maxDispatchDate);
					return (query.uniqueResult() != null);
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ������� ������� �� e-mail
	 * @param login   - ����� �������
	 * @param cardNumber - ����� �����, �� ������� �������� ������
	 * @param fromDate - ������� � ����� ���� ����
	 * @return List<ReportByCardClaim> - ������ ������� �������
	 * @throws BusinessException
	 */
	public List<ReportByCardClaim> findReportByCardClaim(final  CommonLogin login,final String cardNumber, final Calendar fromDate) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<ReportByCardClaim>>()
			{
				public List<ReportByCardClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.documents.reportByCardClaim.history");
					query.setParameter("loginId", login.getId());
					query.setParameter("cardNumber", cardNumber);
					query.setParameter("fromDate", fromDate);
					//noinspection unchecked
					return (List<ReportByCardClaim>) query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
