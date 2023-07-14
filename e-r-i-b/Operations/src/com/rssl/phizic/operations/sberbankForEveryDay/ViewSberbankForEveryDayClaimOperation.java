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
 * Операция для просмотра заявки "Сбербанк на каждый день"
 * User: miklyaev
 * @ $Author$
 * @ $Revision$
 */
public class ViewSberbankForEveryDayClaimOperation extends OperationBase
{
	private static final IssueCardService issueCardService = new IssueCardService();

	private IssueCardDocumentImpl issueCardClaim;

	/**
	 * Инициализация
	 * @param id - идентификатор заявки
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
					throw new BusinessException("Пользователь не является владельцем документа");
			}
			else
			{
				if(!login.getId().equals(issueCardClaim.getOwnerId()))
					throw new BusinessException("Пользователь не является владельцем документа");
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @return заявку клиента
	 */
	public IssueCardDocumentImpl getIssueCardClaim()
	{
		return issueCardClaim;
	}
}
