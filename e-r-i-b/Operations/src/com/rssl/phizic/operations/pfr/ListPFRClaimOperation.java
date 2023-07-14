package com.rssl.phizic.operations.pfr;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.PFRStatementClaim;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.resources.external.ShowOperationLinkFilter;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 14.02.2011
 * @ $Author $
 * @ $Revision $
 */
public class ListPFRClaimOperation extends OperationBase implements ListEntitiesOperation
{
	private static final PFRLinkService pfrLinkService = new PFRLinkService();
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	private ShowOperationLinkFilter showOperationLinkFilter = new ShowOperationLinkFilter(); //������, ������������ ���������� �� ����-�������

	private Login login;
	private PFRLink pfrLink; //�������� ���
	private boolean isError; //�������� ������ ��� ��������� ������

	/**
	 * ������������� ��������
	 */
	public void initialize() throws BusinessException
	{
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		//���� �������� ��� � ��
		pfrLink = pfrLinkService.findByLoginId(login.getId());
		//���� �� �����, �� ������� �� � ���������� �����������
		if(pfrLink == null)
		{
			pfrLink = new PFRLink();
			pfrLink.setLoginId(login.getId());
			pfrLink.setShowInMain(true);
			pfrLink.setShowOperations(false);
			pfrLink.setShowInSystem(true);
			pfrLinkService.addOrUpdate(pfrLink);
		}
	}

	/**
	 * @return ��������� ������������� ������ �������, � ��������� �������� ����������� ��������
	 */
	public Long getLoginId()
	{
		return login.getId();
	}
	/**
	 * �������� �������� ���
	 * @return
	 */
	public PFRLink getPFRLink()
	{
		return pfrLink;
	}

	/**
	 * ���������� �� ����-������� �� �������
	 * @return
	 */
	public boolean isShowOperations()
	{
		return showOperationLinkFilter.evaluate(pfrLink);
	}

	/**
	 * ������� ������ �� ��������� ������� �� ��� �������� ������������ (������� ������ 1 �����).
	 * @param count ������������ ���-�� ������ � ������. ���� �� ������, �� ������ ������ �� ���������
	 * @return ������ ������ �� ��������� ������� �� ���, ��������������� � �������� ������� �� ���� ��������.
	 */
	public List<PFRStatementClaim> getPfrClaims(Integer count)
	{
		// ������� ��� �� ��������� �����, �� �� ����� this.count
		Calendar dateTo = DateHelper.endOfDay(DateHelper.getCurrentDate());
		Calendar dateFrom = DateHelper.startOfDay(DateHelper.getPreviousMonth(dateTo));

		List<PFRStatementClaim> claimList = new ArrayList<PFRStatementClaim>();
		try
		{
			com.rssl.phizic.dataaccess.query.Query query = createQuery("list");
			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateTo", dateTo);
			if (count != null)
			    query.setMaxResults(count);

			claimList = query.executeList();
		}
		catch (DataAccessException e)
		{
			LOG.error(e);
			isError = true;
		}
		return claimList;
	}

	/**
	 * ������� ������ �� ��������� ������� �� ��� �������� ������������ �� �������� ������(���� �� ����� - ������� ������ 1 �����).
	 * @param fromDate ��������� ���� �������
	 * @param toDate �������� ���� �������
	 * @return ������ ������ �� ��������� ������� �� ���, ��������������� � �������� ������� �� ���� ��������.
	 */
	public List<PFRStatementClaim> getPfrClaims(Calendar fromDate, Calendar toDate) throws BusinessLogicException
	{
		if (fromDate == null || toDate == null)
			return getPfrClaims(null);

		List<PFRStatementClaim> claimList = new ArrayList<PFRStatementClaim>();
		try
		{
			com.rssl.phizic.dataaccess.query.Query query = createQuery("list");
			query.setParameter("dateFrom", fromDate);
			query.setParameter("dateTo", toDate);

			claimList = query.executeList();
		}
		catch (DataAccessException e)
		{
			LOG.error(e);
			isError = true;
		}
		return claimList;
	}

	/**
	 * �������� �� ������ ��� ��������� ������
	 * @return
	 */
	public boolean isError()
	{
		return isError;
	}

	/**
	 * @return ������ ��������, �� ������� ���������� ���������� ����������
	 */
	public List<String> getStateCodes()
	{
		List<String> stateCodes = new ArrayList<String>();
		stateCodes.add("INITIAL");
		stateCodes.add("SAVED");
		stateCodes.add("DRAFT");
		stateCodes.add("DISPATCHED");
		stateCodes.add("STATEMENT_READY");
		stateCodes.add("EXECUTED");
		stateCodes.add("REFUSED");
		return stateCodes;
	}
}
