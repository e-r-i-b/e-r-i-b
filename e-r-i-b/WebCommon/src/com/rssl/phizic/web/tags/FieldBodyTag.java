package com.rssl.phizic.web.tags;

import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.gate.payments.systems.recipients.ListValue;
import com.rssl.phizic.gate.payments.systems.recipients.ValidatorField;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Erkin
 * @ created 09.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class FieldBodyTag extends TagSupport
{
	private static final String KNOWN_FIELD_NAMES_REQUEST_ATTRIBUTE = "knownFieldNames";
    private static final String DATE_TIME_FORMAT = "dd.MM.yyyy'T'HH:mm:ss.S0"; // Пример даты: 01.10.1994T13:02:13.0010

	private String name;

	private String title;

	private String description;

	private String hint;

	private String type;

	private String maxLength;

	private String minLength;

	private boolean required;

	private boolean editable;

	private boolean visible;

	private boolean isSum;

	private String value;

	private String defaultValue;

	private String extendedDescId;

	private List<ListValue> listItems = null;

	private boolean changed;

	private String subType;

	private List<ValidatorField> validatorItems = null;

	private String fieldDictType;

	private String fieldInfoType;

	private String dictTypeFieldName;

	///////////////////////////////////////////////////////////////////////////

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getHint()
	{
		return hint;
	}

	public void setHint(String hint)
	{
		this.hint = hint;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getMaxLength()
	{
		return maxLength;
	}

	public void setMaxLength(String maxLength)
	{
		this.maxLength = maxLength;
	}

	public String getMinLength()
	{
		return minLength;
	}

	public void setMinLength(String minLength)
	{
		this.minLength = minLength;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public boolean isEditable()
	{
		return editable;
	}

	public void setEditable(boolean editable)
	{
		this.editable = editable;
	}

	public boolean isVisible()
	{
		return visible;
	}

	public void setVisible(boolean visible)
	{
		this.visible = visible;
	}

	public boolean getIsSum()
	{
		return isSum;
	}

	public void setIsSum(boolean sum)
	{
		isSum = sum;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

	public void addListItem(ListValue item)
	{
		if (listItems == null)
			listItems = new LinkedList<ListValue>();
		listItems.add(item);
	}

	public boolean isChanged()
	{
		return changed;
	}

	public void setChanged(boolean changed)
	{
		this.changed = changed;
	}

	public void addValidatorItems(ValidatorField validatorItem)
	{
		if (validatorItems == null)
			validatorItems = new LinkedList<ValidatorField>();
		validatorItems.add(validatorItem);
	}

	///////////////////////////////////////////////////////////////////////////

	public int doStartTag()
	{
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException
	{
		if (StringHelper.isEmpty(name))
			throw new JspException("Не указан атрибут name");
		if (type == null)
			throw new JspException("Не указан атрибут type");

		XmlWriter out = new XmlWriter(pageContext.getOut());
		try
		{
			out.printTag("name", name);
			if (!StringHelper.isEmpty(title))
				out.printTag("title", title);
			if (!StringHelper.isEmpty(description))
				out.printTag("description", description);
			if (!StringHelper.isEmpty(hint))
				out.printTag("hint", hint);
			out.printTag("type", type);
			if (!StringHelper.isEmpty(maxLength))
				out.printTag("maxLength", maxLength);
			if (!StringHelper.isEmpty(minLength))
				out.printTag("minLength", minLength);
			out.printTag("required", required);
			out.printTag("editable", editable);
			out.printTag("visible", visible);
			out.printTag("isSum", isSum);

			writeTypeSpecificData(out);

			out.printTag("changed", changed);
			writeDictFieldsType(out);

			writeValidatorType(out);

			writeFieldSubTupe(out);

			rememberFieldName();
		}
		catch (IOException e)
		{
			throw new JspException(e);
		}

		return EVAL_PAGE;
	}

	private void writeFieldSubTupe(XmlWriter out) throws IOException, JspException
	{
		if(StringHelper.isNotEmpty(subType)) {
            out.openTag("subTypes");
                if(subType.equals("phone")) {
                    out.printTag("subType", "mobile");
                }
                else
                    out.printTag("subType", subType);
            out.closeTag();
		}
	}

	private void writeTypeSpecificData(XmlWriter out) throws IOException, JspException
	{
		if ("calendar".equals(type))
			writeSimpleType(out, "calendarType");

		else if ("date".equals(type))
			writeSimpleType(out, "dateType");

		else if ("integer".equals(type))
			writeSimpleType(out, "integerType");

		else if ("list".equals(type))
			writeListType(out);

		else if ("money".equals(type))
			writeSimpleType(out, "moneyType");

		else if ("number".equals(type))
			writeSimpleType(out, "numberType");

		else if ("string".equals(type))
			writeStringType(out);

		else if ("set".equals(type))
			writeSetType(out);

		else if ("resource".equals(type))
			writeResourceType(out);

		else if("agreement".equals(type))
			writeAgreementType(out);

		else if ("dict".equals(type))
			writeSimpleType(out, "dictType");

		else throw new JspException("Неожиданный тип поля: " + type);
	}

	private void writeSimpleType(XmlWriter out, String typeTag) throws IOException
	{
		out.openTag(typeTag);
		if (!StringHelper.isEmpty(value))
			out.printTag("value", value);
		out.closeTag();
	}

	private void writeStringType(XmlWriter out) throws IOException
	{
		out.openTag("stringType");
		if (!StringHelper.isEmpty(value))
			out.printTag("value", XmlHelper.escapeXML(value));
		out.closeTag();
	}

	private void writeAgreementType(XmlWriter out) throws IOException
	{
		out.openTag("agreementType");

		if (!StringHelper.isEmpty(value))
			out.printTag("value", BooleanUtils.toBoolean(value));

		if (!StringHelper.isEmpty(extendedDescId))
			out.printTag("agreementId", extendedDescId);

		out.closeTag();
	}

	private void writeListType(XmlWriter out) throws IOException
	{
		out.openTag("listType");
		if (!CollectionUtils.isEmpty(listItems))
		{
			out.openTag("availableValues");
			for (ListValue item : listItems)
			{
				out.openTag("valueItem");
				out.printTag("value", item.getId().trim());
				out.printTag("title", item.getValue());
				out.printTag("selected", value != null && item.getId().trim().equals(value.trim()));
				if (defaultValue != null)
					out.printTag("default", value != null && item.getId().trim().equals(defaultValue.trim()));
				out.closeTag();
			}
			out.closeTag();
			listItems = null;
		}
		out.closeTag();
	}

	private void writeSetType(XmlWriter out) throws IOException
	{
		out.openTag("setType");
		if (!CollectionUtils.isEmpty(listItems))
		{
			Set<String> checkedValues = Collections.emptySet();
			if (!StringHelper.isEmpty(value))
				checkedValues = new HashSet<String>(Arrays.asList(StringUtils.split(value, '@')));

			out.openTag("availableValues");
			for (ListValue item : listItems)
			{
				out.openTag("valueItem");
				out.printTag("value", item.getId().trim());
				out.printTag("title", item.getValue());
				out.printTag("selected", checkedValues.contains(item.getId().trim()));
				out.closeTag();
			}
			out.closeTag();
			listItems = null;
		}
		out.closeTag();
	}

	private void writeResourceType(XmlWriter out) throws IOException
	{
		out.openTag("resourceType");
		if (!CollectionUtils.isEmpty(listItems))
		{
			out.openTag("availableValues");
			for (ListValue item : listItems)
			{
				out.openTag("valueItem");
				out.printTag("value", item.getId());
                if(item instanceof ResourceListValue && (((ResourceListValue) item).getLink() != null)) {
                    writeProductType(out, ((ResourceListValue) item).getLink());
                }
				out.printTag("selected", item.getId().equals(value));
				if (showDisplayedValue())
					out.printTag("displayedValue", item.getValue());
				out.closeTag();
			}
			out.closeTag();
		}
		out.closeTag();
	}

    private void writeProductType(XmlWriter out, ExternalResourceLink link) throws IOException {
        out.openTag("productValue");
        switch (link.getResourceType()) {
            case CARD:
                writeCardTag(out, (CardLink) link);
                break;
            case ACCOUNT:
                writeAccountTag(out, (AccountLink) link);
                break;
            case IM_ACCOUNT:
                writeIMAccounttTag(out, (IMAccountLink) link);
                break;
            case LOAN:
                writeLoanTag(out, (LoanLink) link);
                break;
            default:
                break;
        }
        out.closeTag();
    }

    private void writeAccountTag(XmlWriter out, AccountLink link) throws IOException {
        out.openTag("accounts");
            out.openTag("status");
                out.printTag("code", 0);
            out.closeTag();
            out.openTag("account");
                out.printTag("id", link.getId());
                out.printTag("code", link.getCode());
                out.printTag("name", XmlHelper.escapeXML(link.getName()));
                out.printTag("number", link.getNumber());
                writeMoneyType(out, link.getAccount().getBalance(), "balance");
                out.printTag("state", link.getAccount().getAccountState());
            out.closeTag();
        out.closeTag();
    }

    private void writeCardTag(XmlWriter out, CardLink link) throws IOException {
        out.openTag("cards");
            out.openTag("status");
                out.printTag("code", 0);
            out.closeTag();
            out.openTag("card");
                out.printTag("id", link.getId());
                out.printTag("code", link.getCode());
                out.printTag("name", XmlHelper.escapeXML(link.getName()));
                out.printTag("description", link.getDescription());
                out.printTag("number", MaskUtil.getCutCardNumber(link.getNumber()));
                out.printTag("isMain", link.isMain());
                out.printTag("type", link.getCard().getCardType());
                writeMoneyType(out, link.getCard().getAvailableLimit(), "availableLimit");
                out.printTag("state", link.getCard().getCardState());
			    if (StringHelper.isNotEmpty(link.getMainCardNumber()))
				    out.printTag("mainCardNumber", MaskUtil.getCutCardNumber(link.getMainCardNumber()));
			    if (link.getAdditionalCardType() != null)
				    out.printTag("additionalCardType", link.getAdditionalCardType());
            out.closeTag();
        out.closeTag();
    }

    private void writeLoanTag(XmlWriter out, LoanLink link) throws IOException {
        out.openTag("loans");
            out.openTag("status");
                out.printTag("code", 0);
            out.closeTag();
            out.openTag("loan");
                out.printTag("id", link.getId());
                out.printTag("code", link.getCode());
                out.printTag("name", XmlHelper.escapeXML(link.getName()));
                writeMoneyType(out, link.getLoan().getLoanAmount(), "amount");
                writeMoneyType(out, link.getLoan().getNextPaymentAmount(), "nextPayAmount");
                writeDateTimeType(out, link.getLoan().getNextPaymentDate(), "nextPayDate");
                out.printTag("state", link.getLoan().getState());
            out.closeTag();
        out.closeTag();
    }

    private void writeIMAccounttTag(XmlWriter out, IMAccountLink link) throws IOException {
        out.openTag("imaccounts");
            out.openTag("status");
                out.printTag("code", 0);
            out.closeTag();
            out.openTag("imaccount");
                out.printTag("id", link.getId());
                out.printTag("code", link.getCode());
                out.printTag("name", XmlHelper.escapeXML(link.getName()));
                out.printTag("number", link.getNumber());
                writeDateTimeType(out, link.getImAccount().getOpenDate(), "openDate");
                writeDateTimeType(out, link.getImAccount().getClosingDate(), "closeDate");
                writeMoneyType(out, link.getImAccount().getBalance(), "balance");
                out.printTag("currency", link.getState());
                out.printTag("agreementNumber", link.getAgreementNumber());
                out.printTag("state", link.getImAccount().getState());
            out.closeTag();
        out.closeTag();
    }

    private void writeMoneyType(XmlWriter out, Money balance, String label) throws IOException {
        out.openTag(label);
            out.printTag("amount", balance.getDecimal());
            out.openTag("currency");
                out.printTag("code", balance.getCurrency().getCode());
                out.printTag("name", balance.getCurrency().getName());
            out.closeTag();
        out.closeTag();
    }

    private void writeDateTimeType(XmlWriter out, Calendar calendar, String label) throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        out.printTag(label, dateFormat.format(calendar.getTime()));
	}

	private void writeValidatorType(XmlWriter out) throws IOException
	{

		if (!CollectionUtils.isEmpty(validatorItems))
		{
			out.openTag("validators");
			for (ValidatorField validatorItem : validatorItems)
			{
				out.openTag("validator");
				out.printTag("type", validatorItem.getType());
				out.printTag("message", validatorItem.getMessage());
				out.printTag("parameter", validatorItem.getParameter());
				out.closeTag();
			}
			out.closeTag();
			validatorItems = null;
		}

	}

	private void writeDictFieldsType(XmlWriter out) throws IOException
	{
		if (StringHelper.isNotEmpty(fieldDictType))
		{
			out.openTag("dictFields");
			out.printTag("fieldDictType", fieldDictType);
			out.printTag("fieldInfoType", fieldInfoType);
			if (StringHelper.isNotEmpty(dictTypeFieldName))
				out.printTag("dictTypeFieldName", dictTypeFieldName);
			out.closeTag();
		}
	}

	private boolean showDisplayedValue()
	{
		return MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00) || ApplicationUtil.isATMApi();
	}

	/**
	 * Сохраняет имя поля в реквесте для того, чтобы воспользоваться им в статусе
	 */
	private void rememberFieldName()
	{
		ServletRequest request = pageContext.getRequest();
		Set<String> fieldNames = (Set<String>) request.getAttribute(KNOWN_FIELD_NAMES_REQUEST_ATTRIBUTE);
		if (fieldNames == null) {
			fieldNames = new HashSet<String>();
			request.setAttribute(KNOWN_FIELD_NAMES_REQUEST_ATTRIBUTE, fieldNames);
		}
		fieldNames.add(name);
	}

	public void release()
	{
		super.release();
		
		name = null;
		title = null;
		description = null;
		hint = null;
		type = null;
		maxLength = null;
		minLength = null;
		value = null;
		defaultValue = null;
		listItems = null;
	}

	public String getExtendedDescId()
	{
		return extendedDescId;
	}

	public void setExtendedDescId(String extendedDescId)
	{
		this.extendedDescId = extendedDescId;
	}

	public String getFieldDictType()
	{
		return fieldDictType;
	}

	public void setFieldDictType(String fieldDictType)
	{
		this.fieldDictType = fieldDictType;
	}

	public String getFieldInfoType()
	{
		return fieldInfoType;
	}

	public void setFieldInfoType(String fieldInfoType)
	{
		this.fieldInfoType = fieldInfoType;
	}

	public String getDictTypeFieldName()
	{
		return dictTypeFieldName;
	}

	public void setDictTypeFieldName(String dictTypeFieldName)
	{
		this.dictTypeFieldName = dictTypeFieldName;
	}

	public String getSubType()
	{
		return subType;
	}

	public void setSubType(String subType)
	{
		this.subType = subType;
	}
}
