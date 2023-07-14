package com.rssl.phizic.web.client.ext.sbrf.cards;

import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.business.resources.external.CardLink;

/**
 @author Pankin
 @ created 23.09.2010
 @ $Author$
 @ $Revision$
 */
public class PrintCardInfoForm  extends EditFormBase
{
	private CardLink cardLink;
	private CardAbstract cardAbstract;
	private boolean printAbstract;

	public CardLink getCardLink()
	{
		return cardLink;
	}

	public void setCardLink(CardLink cardLink)
	{
		this.cardLink = cardLink;
	}

	public CardAbstract getCardAbstract()
	{
		return cardAbstract;
	}

	public void setCardAbstract(CardAbstract cardAbstract)
	{
		this.cardAbstract = cardAbstract;
	}

	public boolean isPrintAbstract()
	{
		return printAbstract;
	}

	public void setPrintAbstract(boolean printAbstract)
	{
		this.printAbstract = printAbstract;
	}
}
