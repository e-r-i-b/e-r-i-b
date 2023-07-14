package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма редакатирования атрибутов идентификаторов корзины.
 *
 * @author bogdanov
 * @ created 10.11.14
 * @ $Author$
 * @ $Revision$
 */

public class EditBasketIdentAttributeForm extends ActionFormBase
{
	private Long identId;
	private Long id;

	private String name;
	private String systemId;
	private String type;
	private String regexp;
	private boolean mandatory;
	private boolean isInn;
	private boolean isRc;

	private boolean add = false;
	private boolean remove = false;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getIdentId()
	{
		return identId;
	}

	public void setIdentId(Long identId)
	{
		this.identId = identId;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public void setMandatory(boolean mandatory)
	{
		this.mandatory = mandatory;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRegexp()
	{
		return regexp;
	}

	public void setRegexp(String regexp)
	{
		this.regexp = regexp;
	}

	public String getSystemId()
	{
		return systemId;
	}

	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public boolean isAdd()
	{
		return add;
	}

	public void setAdd(boolean add)
	{
		this.add = add;
	}

	public boolean isRemove()
	{
		return remove;
	}

	public void setRemove(boolean remove)
	{
		this.remove = remove;
	}

	public boolean isInn()
	{
		return isInn;
	}

	public void setInn(boolean inn)
	{
		isInn = inn;
	}

	public boolean isRc()
	{
		return isRc;
	}

	public void setRc(boolean rc)
	{
		isRc = rc;
	}
}
