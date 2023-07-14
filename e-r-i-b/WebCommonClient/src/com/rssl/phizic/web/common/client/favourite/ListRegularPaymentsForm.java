package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.business.resources.external.AutoPaymentLink;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLinkBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author basharin
 * @ created 08.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListRegularPaymentsForm extends ListFormBase
{
	private List<EditableExternalResourceLink> regularPayments;
	private List<EditableExternalResourceLink> p2pRegularPayments;   //Список всех автопереводов
	private Set<EditableExternalResourceLink> activePaymentSet;
	private List<EditableExternalResourceLink> activePayments;
	private List<EditableExternalResourceLink> suspendedPayments;
	private List<EditableExternalResourceLink> waitingConfirmPayments;
	private List<EditableExternalResourceLink> archivePayments;
	private List<EditableExternalResourceLink> activeP2PPayments;  //автоплатежи карта-карта активные
	private List<EditableExternalResourceLink> suspendedP2PPayments;  //автоплатежи карта-карта приостановленные
	private List<EditableExternalResourceLink> waitingConfirmP2PPayments;  //автоплатежи карта-карта ожидающие подтверждения
	private List<EditableExternalResourceLink> archiveP2PPayments;  //автоплатежи карта-карта архивные
	private List<AutoPaymentLink> autoPayments;
	private boolean showIrrelevantRegularPayments;
	private Long productId;
	private boolean card;
	private boolean account;
	private String emptyListMessage;
	private String[] type;    //Типы запрашиваемых автоплатежей

	@Deprecated
	public boolean getShowIrrelevantRegularPayments()
	{
		return showIrrelevantRegularPayments;
	}

	//Все активные линки для отображения на странице "Личное меню"
	//(так как согласно документации отображаем там и биллинговые и p2p активные автоплатежи)
	public List<EditableExternalResourceLink> getActivePaymentsForPersonalMenu()
	{
		List<EditableExternalResourceLink> result = new ArrayList<EditableExternalResourceLink>();
		result.addAll(activePayments);
		result.addAll(activeP2PPayments);
		return result;
	}

	@Deprecated
	public void setShowIrrelevantRegularPayments(boolean showIrrelevantRegularPayments)
	{
		this.showIrrelevantRegularPayments = showIrrelevantRegularPayments;
	}

	public List<EditableExternalResourceLink> getRegularPayments()
	{
		return regularPayments;
	}

	public void setRegularPayments(List<EditableExternalResourceLink> regularPayments)
	{
		this.regularPayments = regularPayments;
	}

	public List<AutoPaymentLink> getAutoPayments()
	{
		return autoPayments;
	}

	public void setAutoPayments(List<AutoPaymentLink> autoPayments)
	{
		this.autoPayments = autoPayments;
	}

	public Set<EditableExternalResourceLink> getActivePaymentSet()
	{
		return activePaymentSet;
	}

	public void setActivePaymentSet(Set<EditableExternalResourceLink> activePaymentSet)
	{
		this.activePaymentSet = activePaymentSet;
	}

	public List<EditableExternalResourceLink> getActivePayments()
	{
		return activePayments;
	}

	public void setActivePayments(List<EditableExternalResourceLink> activePayments)
	{
		this.activePayments = activePayments;
	}

	public List<EditableExternalResourceLink> getSuspendedPayments()
	{
		return suspendedPayments;
	}

	public void setSuspendedPayments(List<EditableExternalResourceLink> suspendedPayments)
	{
		this.suspendedPayments = suspendedPayments;
	}

	public List<EditableExternalResourceLink> getWaitingConfirmPayments()
	{
		return waitingConfirmPayments;
	}

	public void setWaitingConfirmPayments(List<EditableExternalResourceLink> waitingConfirmPayments)
	{
		this.waitingConfirmPayments = waitingConfirmPayments;
	}

	public List<EditableExternalResourceLink> getArchivePayments()
	{
		return archivePayments;
	}

	public void setArchivePayments(List<EditableExternalResourceLink> archivePayments)
	{
		this.archivePayments = archivePayments;
	}

	public List<EditableExternalResourceLink> getActiveP2PPayments()
	{
		return activeP2PPayments;
	}

	public void setActiveP2PPayments(List<EditableExternalResourceLink> activeP2PPayments)
	{
		this.activeP2PPayments = activeP2PPayments;
	}

	public List<EditableExternalResourceLink> getSuspendedP2PPayments()
	{
		return suspendedP2PPayments;
	}

	public void setSuspendedP2PPayments(List<EditableExternalResourceLink> suspendedP2PPayments)
	{
		this.suspendedP2PPayments = suspendedP2PPayments;
	}

	public List<EditableExternalResourceLink> getWaitingConfirmP2PPayments()
	{
		return waitingConfirmP2PPayments;
	}

	public void setWaitingConfirmP2PPayments(List<EditableExternalResourceLink> waitingConfirmP2PPayments)
	{
		this.waitingConfirmP2PPayments = waitingConfirmP2PPayments;
	}

	public List<EditableExternalResourceLink> getArchiveP2PPayments()
	{
		return archiveP2PPayments;
	}

	public void setArchiveP2PPayments(List<EditableExternalResourceLink> archiveP2PPayments)
	{
		this.archiveP2PPayments = archiveP2PPayments;
	}

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public boolean isCard()
	{
		return card;
	}

	public void setCard(boolean card)
	{
		this.card = card;
	}

	public boolean isAccount()
	{
		return account;
	}

	public void setAccount(boolean account)
	{
		this.account = account;
	}

	public String getEmptyListMessage()
	{
		return emptyListMessage;
	}

	public void setEmptyListMessage(String emptyListMessage)
	{
		this.emptyListMessage = emptyListMessage;
	}

	public String[] getType()
	{
		return type;
	}

	public void setType(String[] type)
	{
		this.type = type;
	}

	public List<EditableExternalResourceLink> getP2pRegularPayments()
	{
		return p2pRegularPayments;
	}

	public void setP2pRegularPayments(List<EditableExternalResourceLink> p2pRegularPayments)
	{
		this.p2pRegularPayments = p2pRegularPayments;
	}
}
