package com.rssl.phizic.ws.currency.sbrf;

import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.*;

/**
 * @author gulov
 * @ created 30.09.2010
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ќбработчик сообщени€ об изменении курсов валют
 */
public class EribRatesHandler extends DefaultHandler
{
	private static final String REQUEST_ID_TAG_NAME = "RqUID";
	private static final String OPERATION_ID_TAG_NAME = "OperUID";
	private static final String ORDER_DATE_TAG_NAME = "OrderDt";
	private static final String ORDER_NUMBER_TAG_NAME = "OrderId";
	private static final String REGION_ID_TAG_NAME = "RegionId";
	private static final String BRANCH_ID_TAG_NAME = "BranchId";
	private static final String AGENCY_ID_TAG_NAME = "AgencyId";
	private static final String START_DATE_TAG_NAME = "EffDt";
	private static final String CURRENCY_ITEM_TAG_NAME = "CurListItem";
	static final String CURRENCY_CODE_TAG_NAME = "Pr_code";
	static final String CURRENCY_CODE_2_TAG_NAME = "Pr_code2";
	static final String STRAIGHT_TAG_NAME = "Pr_Straight";
	static final String QUOTIENT_TAG_NAME = "Pr_quot";
	static final String RATE_CB_TAG_NAME = "Pr_cb";
	static final String RATE_BUY_TAG_NAME = "Pr_buy";
	static final String RATE_SALE_TAG_NAME = "Pr_sale";
	static final String RATE_REMOTE_BUY_TAG_NAME = "Pr_buy3";
	static final String RATE_REMOTE_SALE_TAG_NAME = "Pr_sale3";
	static final String RATE_CB_DELTA_TAG_NAME = "Pr_cb_delta";
	static final String RATE_BUY_DELTA_TAG_NAME = "Pr_buy_delta";
	static final String RATE_SALE_DELTA_TAG_NAME = "Pr_sale_delta";
	static final String RATE_REMOTE_BUY_DELTA_TAG_NAME = "Pr_buy3_delta";
	static final String RATE_REMOTE_SALE_DELTA_TAG_NAME = "Pr_sale3_delta";
	static final String RATE_TARIF_PLAN = "Tarif_plan";

	/**
	 * —трока дл€ временного сохранени€ в ней содержимого тегов
	 */
	private String tagText = "";

	/**
	 * Map дл€ описани€ нужных тегов и сохранени€ в нем их содержимых
	 */
	private final Map<String, String> mapTagValues = new HashMap<String, String>(13);

	/**
	 * ћассив тегов курсов валют
	 */
	private final String[] rateTags = new String[]
	{CURRENCY_CODE_TAG_NAME,
	 CURRENCY_CODE_2_TAG_NAME,
	 STRAIGHT_TAG_NAME,
	 QUOTIENT_TAG_NAME,
	 RATE_CB_TAG_NAME,
	 RATE_BUY_TAG_NAME,
	 RATE_SALE_TAG_NAME,
	 RATE_REMOTE_BUY_TAG_NAME,
	 RATE_REMOTE_SALE_TAG_NAME,
	 RATE_CB_DELTA_TAG_NAME,
	 RATE_BUY_DELTA_TAG_NAME,
	 RATE_SALE_DELTA_TAG_NAME,
	 RATE_REMOTE_BUY_DELTA_TAG_NAME,
	 RATE_TARIF_PLAN};

	/**
	 * —писок курсов валют
	 */
	private List<Map<String, String>> rateList = new ArrayList<Map<String, String>>();

	public EribRatesHandler()
	{
		mapTagValues.put(REQUEST_ID_TAG_NAME, "");
		mapTagValues.put(OPERATION_ID_TAG_NAME, "");
		mapTagValues.put(ORDER_DATE_TAG_NAME, "");
		mapTagValues.put(ORDER_NUMBER_TAG_NAME, "");
		mapTagValues.put(REGION_ID_TAG_NAME, "");
		mapTagValues.put(BRANCH_ID_TAG_NAME, "");
		mapTagValues.put(AGENCY_ID_TAG_NAME, "");
		mapTagValues.put(START_DATE_TAG_NAME, "");

		clearRateTags();
	}

	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException
	{
		tagText = mapTagValues.get(qName);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException
	{
		if (tagText != null)
			mapTagValues.put(qName, tagText);
		tagText = null;

		if (qName.equals(CURRENCY_ITEM_TAG_NAME))
		{
			saveToList();
            clearRateTags();
		}
	}

	public void characters(char ch[], int start, int length) throws SAXException
	{
		if (tagText != null)
			tagText += new String(ch, start, length);
	}

	public String getRequestId()
	{
		return mapTagValues.get(REQUEST_ID_TAG_NAME);
	}

	public String getOperationId()
	{
		return mapTagValues.get(OPERATION_ID_TAG_NAME);
	}

	public Calendar getOrderDate()
	{
		String temp = mapTagValues.get(ORDER_DATE_TAG_NAME);

		return temp != null ? XMLDatatypeHelper.parseDateTime(temp) : null;
	}

	public String getOrderNum()
	{
		return mapTagValues.get(ORDER_NUMBER_TAG_NAME);
	}

	public String getRegionId()
	{
		return mapTagValues.get(REGION_ID_TAG_NAME);
	}

	public String getBranchId()
	{
		return mapTagValues.get(BRANCH_ID_TAG_NAME);
	}

	public String getAgencyId()
	{
		return mapTagValues.get(AGENCY_ID_TAG_NAME);
	}

	public Calendar getStartDate()
	{
		String temp = mapTagValues.get(START_DATE_TAG_NAME);

		return temp != null ? XMLDatatypeHelper.parseDateTime(temp) : null;
	}

	public List<Map<String, String>> getRateList()
	{
		return rateList;
	}

	private void clearRateTags()
	{
		for (String tag : rateTags)
		{
			mapTagValues.put(tag, "");
		}
	}

	private void saveToList()
	{
		Map<String, String> temp = new HashMap<String, String>();

		for (String tag : rateTags)
		{
			temp.put(tag, mapTagValues.get(tag));
		}

		rateList.add(temp);
	}
}
