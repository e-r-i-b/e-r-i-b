package com.rssl.phizic.operations.sberbankForEveryDay;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.claims.sbnkd.IssueCardService;
import com.rssl.phizic.gate.claims.sbnkd.impl.IssueCardDocumentImpl;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;

/**
 * �������� ��� ��������� ������ "�������� �� ������ ����"
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewSberbankForEveryDayClaimOperation extends OperationBase
{
	private static final IssueCardService issueCardService = new IssueCardService();

	private IssueCardDocumentImpl issueCardClaim;

	/**
	 * �������������
	 * @param id - ������������� ������
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		try
		{
			issueCardClaim = issueCardService.getClaim(id);
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			if (login instanceof GuestLogin)
			{
				if(!((GuestLogin) login).getGuestCode().equals(issueCardClaim.getOwnerId()))
					throw new BusinessException("������������ �� �������� ���������� ���������");
			}
			else
			{
				if(!login.getId().equals(issueCardClaim.getOwnerId()))
					throw new BusinessException("������������ �� �������� ���������� ���������");
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return ������ �������
	 */
	public IssueCardDocumentImpl getIssueCardClaim()
	{
		return issueCardClaim;
	}
}
