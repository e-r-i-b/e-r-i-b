package com.rssl.phizic.gate.templates.impl;

import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.payments.template.TemplateInfo;

/**
 * Дополнительная информация по шаблону
 *
 * @author khudyakov
 * @ created 05.05.14
 * @ $Author$
 * @ $Revision$
 */
public class TemplateInfoImpl implements TemplateInfo
{
	private String name;                                //название шаблона
	private boolean useInMAPI;                          //доступность в мАПИ
	private boolean useInATM;                           //доступность в УС
	private boolean useInERMB;                          //доступность в ЕРМБ
	private boolean useInERIB;                          //доступность в ЕРИБ
	private int orderInd;                               //индекс очередности
	private boolean visible;                            //видимость меню, списках
	private State state;                                //статус шаблона (активен, удален клиентом)


	public TemplateInfoImpl() {}

	public TemplateInfoImpl(String name, boolean useInMAPI, boolean useInATM, boolean useInERMB, boolean useInERIB, int orderInd, boolean visible, State state)
	{
		this.name = name;
		this.useInMAPI = useInMAPI;
		this.useInATM  = useInATM;
		this.useInERMB = useInERMB;
		this.useInERIB = useInERIB;
		this.orderInd  = orderInd;
		this.visible   = visible;
		this.state = state;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public boolean isUseInMAPI()
	{
		return useInMAPI;
	}

	public void setUseInMAPI(boolean useInMAPI)
	{
		this.useInMAPI = useInMAPI;
	}

	public boolean isUseInATM()
	{
		return useInATM;
	}

	public void setUseInATM(boolean useInATM)
	{
		this.useInATM = useInATM;
	}

	public boolean isUseInERMB()
	{
		return useInERMB;
	}

	public void setUseInERMB(boolean useInERMB)
	{
		this.useInERMB = useInERMB;
	}

	public boolean isUseInERIB()
	{
		return useInERIB;
	}

	public void setUseInERIB(boolean useInERIB)
	{
		this.useInERIB = useInERIB;
	}

	public int getOrderInd()
	{
		return orderInd;
	}

	public void setOrderInd(int orderInd)
	{
		this.orderInd = orderInd;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public State getState()
	{
		return state;
	}

	public void setState(State state)
	{
		this.state = state;
	}

}
