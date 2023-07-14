package com.rssl.phizic.web.client.ext.sbrf.mobilebank.ermb;

import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.web.client.ext.sbrf.mobilebank.MobileBankFormBase;

import java.util.List;

/**
 @author: Egorovaa
 @ created: 28.09.2012
 @ $Author$
 @ $Revision$
 */
public class SmsTemplatesForm extends MobileBankFormBase
{
	private List<CardLink> cards;
	private List<TemplateDocument> templates;

	public List<CardLink> getCards()
	{
		return cards;
	}

	public void setCards(List<CardLink> cards)
	{
		this.cards = cards;
	}

	public List<TemplateDocument> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<TemplateDocument> templates)
	{
		this.templates = templates;
	}
}
