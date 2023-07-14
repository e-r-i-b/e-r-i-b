package com.rssl.phizic.web.actions.templates;

import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kidyaev
 * @ created 15.08.2006
 * @ $Author$
 * @ $Revision$
 */
public class GoodsAndServicesPaymentForm extends ActionFormBase
{
    private Map<String,Object>  fields    = new HashMap<String, Object>();
    private Map<String, String> receivers = new HashMap<String, String>();
	private String              appointment;
	private String              description;
	private String              detailedDescription;
	private String              type;

	private boolean template;

	public boolean isTemplate()
	{
		return template;
	}

	public void setTemplate(boolean template)
	{
		this.template = template;
	}

	/**
	 * @return ���������� <���� ����������, �������� ����������>
	 */
    public Map<String, String> getReceivers()
    {
        return Collections.unmodifiableMap(receivers);
    }

	/**
	 * @param receivers ���������� <���� ����������, �������� ����������>
	 */
    public void setReceivers(Map<String, String> receivers)
    {
	    //noinspection AssignmentToCollectionOrArrayFieldFromParameter
	    this.receivers = receivers;
    }

	/**
	 * @return ���� ����� <��� ����, �������� ����>
	 */
    public Map<String, Object> getFields()
    {
	    //noinspection ReturnOfCollectionOrArrayField
	    return fields;
    }

	/**
	 * @param fields ���� ����� <��� ����, �������� ����>
	 */
    public void setFields(Map<String, Object> fields)
    {
	    //noinspection AssignmentToCollectionOrArrayFieldFromParameter
	    this.fields = fields;
    }

	/**
	 * @param key ��� ����
	 * @return �������� ����
	 */
    public Object getField(String key)
    {
        return fields.get(key);
    }

	/**
	 * @param key ��� ����
	 * @param obj �������� ����
	 */
    public void setField(String key, Object obj)
    {
        fields.put(key, obj);
    }

	/**
	 * @return ���������� �������
	 */
	public String getAppointment()
	{
		return appointment;
	}

	/**
	 * @param appointment ���������� �������
	 */
	public void setAppointment(String appointment)
	{
		this.appointment = appointment;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getDetailedDescription()
	{
		return detailedDescription;
	}

	public void setDetailedDescription(String detailedDescription)
	{
		this.detailedDescription = detailedDescription;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
