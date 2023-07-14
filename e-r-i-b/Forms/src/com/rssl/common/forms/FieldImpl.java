package com.rssl.common.forms;

import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.BusinessCategory;
import com.rssl.common.forms.types.StringType;
import com.rssl.common.forms.types.SubType;
import com.rssl.common.forms.types.Type;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.common.types.BusinessFieldSubType;
import com.rssl.phizic.common.types.VersionNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 30.11.2005
 * @ $Author: niculichev $
 * @ $Revision: 52888 $
 */

public class FieldImpl implements Field
{
    private String               name;
    private Type                 type = new StringType();
	private SubType              subType;
    private String               description;
    private List<FieldValidator> validators = new ArrayList<FieldValidator>();
    private FieldValueParser     parser;
    private String               source;
	private boolean              signable;
	private boolean              isChanged;
	private Expression           enabledExpression;
	private Expression           valueExpression;
	private Expression           initalValueExpression;
	private VersionNumber        fromApi;
	private VersionNumber        toApi;
	private String mask;
	private BusinessCategory businessCategory;
	private boolean key;

	/**
     * Имя поля - например имя тега HTML;
      * @return имя
     */
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Тип поля (string|date|money|BIC|account|etc)
     * @return тип
     */
    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

	public SubType getSubType()
	{
		return subType;
	}

	public void setSubType(SubType subType)
	{
		this.subType = subType;
	}

    /**
     * Валидаторы ассоциированные с полем
     *
     * @return массив валидаторов
     */
    public List<FieldValidator> getValidators()
    {
        return Collections.unmodifiableList(validators);
    }

	/**
	 * @deprecated  Использовать addValidators, и clearValidators
	 */
    @Deprecated
    public void setValidators(List<FieldValidator> validators)
    {
	    clearValidators();
	    addValidators(validators);
    }

	public void clearValidators()
	{
		validators.clear();
	}

	public void addValidators(List<FieldValidator> validators)
	{
	    this.validators.addAll(validators);
	}

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public FieldValueParser getParser()
    {
        return parser;
    }

    /**
     * Парсер поля - преобразует введенное пользователем
     * значение во внутреннее представления
     * @param parser
     */
    public void setParser(FieldValueParser parser)
    {
        this.parser = parser;
    }

    /**
     * XPath выражение для получения значеия поля из Payment.xml
     * @return значение поля
     */
    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }


	public boolean isSignable()
	{
		return signable;
	}

	/**
	 * @return Признак "включенности" поля
	 */
	public Expression getEnabledExpression()
	{
		return enabledExpression;
	}

	/**
	 * @param signable включать ли подпись
	 */
	public void setSignable(boolean signable)
	{
		this.signable = signable;
	}

	public void setEnabledExpression(Expression enabledExpression)
	{
		this.enabledExpression = enabledExpression;
	}

	/**
	 * @return вычислимое выражение
	 */
	public Expression getValueExpression()
	{
		return valueExpression; 
	}

	/**
	 * @return Выражение для вычисления начального значения поля.
	 */
	public Expression getInitalValueExpression()
	{
		return initalValueExpression;
	}

	public void setValueExpression(Expression valueExpression)
	{
		this.valueExpression = valueExpression;
	}

	public void setInitalValueExpression(Expression initalValueExpression)
	{
		this.initalValueExpression = initalValueExpression;
	}

	public boolean isChanged()
	{
		return isChanged;
	}

	public void setChanged(boolean changed)
	{
		isChanged = changed;
	}

	public VersionNumber getFromApi()
	{
		return fromApi;
	}

	public void setFromApi(VersionNumber fromApi)
	{
		this.fromApi = fromApi;
	}

	public VersionNumber getToApi()
	{
		return toApi;
	}

	public void setToApi(VersionNumber toApi)
	{
		this.toApi = toApi;
	}

	/**
	 * @return Маска, накладывающаяся на поле
	 */
	public String getMask()
	{
		return mask;
	}

	/**
	 * Установить значение маски
	 * @param mask маска
	 */
	public void setMask(String mask)
	{
		this.mask = mask;
	}

	public BusinessCategory getBusinessCategory()
	{
		return businessCategory;
	}

	public void setBusinessCategory(BusinessCategory businessCategory)
	{
		this.businessCategory = businessCategory;
	}

	public boolean isKey()
	{
		return key;
	}

	public void setKey(boolean key)
	{
		this.key = key;
	}
}
