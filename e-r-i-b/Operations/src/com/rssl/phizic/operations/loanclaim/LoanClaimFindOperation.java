package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.auth.csa.wsclient.NodeInfoConfig;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.GuestPersonData;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.GuestPerson;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.person.search.multinode.ChangeNodeLogicException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.math.NumberUtils;

/**
 * Операция поиска/создания кредитной заявки
 * @author Rtischeva
 * @ created 12.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimFindOperation extends OperationBase
{
	private static BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	private ExtendedLoanClaim document;

	public void search(String uid) throws BusinessLogicException, BusinessException
	{
		if (StringHelper.isEmpty(uid))
			throw new BusinessLogicException("Заявка с таким номером не найдена в системе");
		checkNode(getNodeNumberFromUID(uid));
		ExtendedLoanClaim document = businessDocumentService.findExtendedLoanClaimByUID(uid);
		if (document== null)
			throw new BusinessLogicException("Заявка с таким номером не найдена в системе");

		this.document = document;
		setPersonContext();
	}

	public void continueSearch(String uid) throws BusinessException
	{
		ExtendedLoanClaim document = businessDocumentService.findExtendedLoanClaimByUID(uid);
		this.document = document;
		setPersonContext();
	}

	private Long getNodeNumberFromUID(String uid) throws BusinessLogicException
	{
		try
		{
			String nodeNum = StringHelper.removeLeadingZeros(uid.substring(uid.length()-2));
			return NumberUtils.createLong(nodeNum);
		} catch (NumberFormatException nfe)
		{
			throw new BusinessLogicException("Заявка с таким номером не найдена в системе");
		}
	}

	public ExtendedLoanClaim getDocument()
	{
		return document;
	}

	private void checkNode(Long nodeId) throws BusinessLogicException
	{
		NodeInfo node = ConfigFactory.getConfig(NodeInfoConfig.class).getNode(nodeId);
		if (node == null)
			throw new BusinessLogicException("Заявка с таким номером не найдена в системе");
		if (!node.isAdminAvailable())
			throw new BusinessLogicException("Профиль отсутствует.");

		if(!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber().equals(nodeId))
		{
			throw new ChangeNodeLogicException("Заявка в другом блоке системы",nodeId);
		}
	}

	private void setPersonContext() throws BusinessException
	{
		BusinessDocumentOwner documentOwner = document.getOwner();
		ActivePerson person = documentOwner.getPerson();
		Login login = documentOwner.getLogin();
		PersonData personData = null;
		if (person != null)
		{
			if (documentOwner.isGuest())
			{
				GuestLogin guestLogin = (GuestLogin)login;
				GuestPerson guestPerson = (GuestPerson)person;
				person.setLogin(guestLogin);
				personData = new GuestPersonData(guestLogin, guestPerson, document.getOwnerGuestMbk(),null, guestLogin.getGuestCode(), null);
			}
			else
				personData = new StaticPersonData(documentOwner.getPerson());
		}
		PersonContext.getPersonDataProvider().setPersonData(personData);
	}
}
