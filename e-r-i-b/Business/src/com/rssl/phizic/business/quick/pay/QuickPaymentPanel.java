package com.rssl.phizic.business.quick.pay;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * Сущность Панель Быстрой Оплаты(ПБО).
 * Отображается слева на всех страницах клиентского приложения.
 * @author komarov
 * @ created 06.07.2012
 * @ $Author$
 * @ $Revision$
 */

public class QuickPaymentPanel extends MultiBlockDictionaryRecordBase
{
	private Long id;
	private QuickPaymentPanelState state = QuickPaymentPanelState.ACTIVE;//Статус.
	private String name; //Название Панели Быстрой Оплаты(ПБО).
	private Calendar periodFrom; //Дата начала отображения.
	private Calendar periodTo; //Дата окончания отображения.
	private Set<String> departments;//Департаменты
	private List<PanelBlock> panelBlocks;//Блоки поставщиков услуг.

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public QuickPaymentPanelState getState()
	{
		return state;
	}

	public void setState(QuickPaymentPanelState state)
	{
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

	public Calendar getPeriodFrom()
	{
		return periodFrom;
	}

	public void setPeriodFrom(Calendar periodFrom)
	{
		this.periodFrom = periodFrom;
	}

	public Calendar getPeriodTo()
	{
		return periodTo;
	}

	public void setPeriodTo(Calendar periodTo)
	{
		this.periodTo = periodTo;
	}

	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public List<PanelBlock> getPanelBlocks()
	{
		return panelBlocks;
	}

	public void setPanelBlocks(List<PanelBlock> panelBlocks)
	{
		this.panelBlocks = panelBlocks;
	}
}
