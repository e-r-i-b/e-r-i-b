package com.rssl.phizic.web.loanclaim;

/**
 * @author Moshenko
 * @ created 26.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class Constants
{
	public static final String NAME = "name";
	public static final String CODE = "code";
	public static final String ENSURING = "ensuring";
	public static final String DESCRIPTION = "description";
	public static final String RUB_CODE_NUMBER = "810";
	public static final String RUB_ALT_CODE_NUMBER = "643";
	public static final String USD_CODE_NUMBER = "840";
	public static final String EUR_CODE_NUMBER = "978";
	public static final String RUB = "RUB";
	public static final String USD = "USD";
	public static final String EUR = "EUR";
	public static final String TB_NAME = "tbName";
	public static final String TB = "tb";
	public static final String TRANSACT_SM_USE = "transactSMUse";
	public static final String SELECTION_AVALIABLE = "selectionAvaliable";
	public static final String PRODUCT_TYPE_ID = "productTypeId";
	public static final String PRODUCT_TYPE_CODE = "productTypeCode";
	public static final String PRODUCT_ID = "productId";
	public static final String PRODUCT_CODE = "productCode";
	public static final String PRODUCT_DATE_FROM_MONTH = "productDateFromMonth";
	public static final String PRODUCT_DATE_FROM_YEAR = "productDateFromYear";
	public static final String PRODUCT_DATE_TO_YEAR = "productDateToYear";
	public static final String PRODUCT_DATE_TO_MONTH = "productDateToMonth";
	public static final String PRODUCT_MAX_DATE_INCLUDE = "productMaxDateInclude";
	public static final String USE_INITIAL_FEE = "useInitialFee";
	public static final String MIN_INITIAL_FEE = "minInitialFee";
	public static final String MAX_INITIAL_FEE = "maxInitialFee";
	public static final String MAX_INITIAL_FEE_INCLUDE = "includeMaxInitialFee";
	public static final String ADDITIONAL_TERMS = "additionalTerms";
	public static final String CURR_COND_AVALIABLE = "currCondAvailable";
	public static final String CURR_COND_ID = "currCondId";
	public static final String CURR_COND_FROM_DATE = "currCondFromDate";
	public static final String CURR_COND_MIN_LIMIT = "minLimitAmount";
	public static final String CURR_COND_MAX_LIMIT = "maxLimitAmount";
	public static final String CURR_COND_MAX_LIMIT_PERCENT = "maxLimitPercent";
	public static final String CURR_COND_MAX_LIMIT_PERCENT_USE = "maxLimitPercentUse";
	public static final String CURR_COND_MAX_LIMIT_INCLUDE = "maxLimitInclude";
	public static final String CURR_COND_MIN_PERCENT_RATE = "minPercentRate";
	public static final String CURR_COND_MAX_PERCENT_RATE = "maxPercentRate";
	public static final String CURR_COND_MAX_PERCENT_RATE_INCLUDE = "maxPercentRateInclude";

	//filter param
	public static final String STATUS = "status";
	public static final String CREDIT_PRODUCT_TYPE_ID = "creditProductTypeId";
	public static final String CREDIT_PRODUCT_ID = "creditProductId";

	//regex: Денежный тип в формате NNNNNNN.NN (десятичная запятая), c разделителем разрядов – пробел
	public static final String MONEY_REGEX = "^\\d{1,7}((\\.|,)\\d{0,2})?$";
	public static final String PERCENT_REGEX = "^\\d{1,3}((\\.|,)\\d{0,2})?$";
}
