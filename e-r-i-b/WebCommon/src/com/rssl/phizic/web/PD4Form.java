package com.rssl.phizic.web;

import com.rssl.phizic.web.common.FilterActionForm;

/**
 * Created by IntelliJ IDEA.
 * @author mihaylov
 * created 26.09.2008
 * @ $Author$
 * @ $Revision$
 */
public class PD4Form extends FilterActionForm
{
	private boolean isLetter = false;
	private String type = "";
	private String summa = "";

	public void setType(String type)
	{
		if(type==null)return;
		this.type = type;
	}

	public String getType()
	{
		return type;
	}

	public void setIsLetter(boolean isLetter)
	{
		this.isLetter = isLetter;
	}

	public boolean getIsLetter()
	{
		return isLetter;
	}

	public void setSumma(String summa)
	{
		this.summa = summa;
	}

	public String getSumma()
	{
		return summa;
	}
}
