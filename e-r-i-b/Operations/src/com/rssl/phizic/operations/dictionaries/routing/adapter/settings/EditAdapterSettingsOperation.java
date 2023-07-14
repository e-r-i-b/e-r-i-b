package com.rssl.phizic.operations.dictionaries.routing.adapter.settings;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import com.rssl.phizicgate.manager.routing.Node;
import com.rssl.phizicgate.manager.routing.NodeType;

import java.util.*;

import static com.rssl.phizic.logging.messaging.MessageLogConfig.EXCLUDED_MESSAGES_PROPERTY_KEY;
/**
 * User: Balovtsev
 * Date: 13.07.2012
 * Time: 8:32:48
 */
public class EditAdapterSettingsOperation extends EditPropertiesOperation
{
	public static final String WRONG_OFFICE_BRANCHES_ID_KEY = "wrongOfficeBranchesProperties_Id_";
	public static final String WRONG_OFFICE_BRANCHES_CODE_KEY = "wrongOfficeBranchesProperties_osbCode_";
	public static final String OUR_TB_CODES_ID_KEY = "ourTBCodesProperties_Id_";
	public static final String OUR_TB_CODES_CODE_KEY = "ourTBCodesProperties_tbCode_";
	public static final String EXCLUDED_MESSAGES_ID_KEY = "excludedMessagesProperties_Id_";
	public static final String EXCLUDED_MESSAGES_CODE_KEY = "excludedMessagesProperties_message_";

	private static final String OUR_TB_CODES_KEY = "com.rssl.phizic.gate.OurTBcodes";
	private static final AdapterService adapterService = new AdapterService();

	//справочник табличных настроек и хелперов для их конвертаци
	private static final Map<String, ListPropertiesHelper> listProperties = new HashMap<String, ListPropertiesHelper>();

	static
	{
		listProperties.put(EXCLUDED_MESSAGES_PROPERTY_KEY, new ListPropertiesHelper(EXCLUDED_MESSAGES_ID_KEY, EXCLUDED_MESSAGES_CODE_KEY, "", ","));
		listProperties.put(OfficeCodeReplacer.WRONG_BRANCHES_KEY, new ListPropertiesHelper(WRONG_OFFICE_BRANCHES_ID_KEY, WRONG_OFFICE_BRANCHES_CODE_KEY, "", ","));
		listProperties.put(OUR_TB_CODES_KEY, new ListPropertiesHelper(OUR_TB_CODES_ID_KEY, OUR_TB_CODES_CODE_KEY, "", ","));
	}

	private Node adapterNode;

	/**
	 * Инициализация операции
	 * @param adapterId - идентификатор адаптера
	 */
	public void initialize(Long adapterId) throws BusinessException, BusinessLogicException
	{
		PropertyCategory category = initAdapterData(adapterId);
		super.initialize(category);
	}

	/**
	 * Инициализация операции
	 * @param adapterId - идентификатор адаптера
	 * @param nodeType - тип узла
	 * @param keys - ключи формы
	 */
	public void initialize(Long adapterId, NodeType nodeType, Set<String> keys) throws BusinessException, BusinessLogicException
	{
		PropertyCategory category = initAdapterData(adapterId);

		if (nodeType != adapterNode.getType())
			throw new BusinessLogicException("Некорректный тип узла в параметре nodeType");

		boolean loadAdditProps = loadAdditProperties();
		if (loadAdditProps)
		{
			keys.add(OfficeCodeReplacer.WRONG_BRANCHES_KEY);
			keys.add(OUR_TB_CODES_KEY);
		}

		super.initialize(category, keys);

		List<Property> properties = findPropertiesByKey(EXCLUDED_MESSAGES_PROPERTY_KEY);
		addProperties(listProperties.get(EXCLUDED_MESSAGES_PROPERTY_KEY).convertToMap(properties));

		if (loadAdditProps)
		{
			addProperties(parseProperty(OfficeCodeReplacer.WRONG_BRANCHES_KEY));
			addProperties(parseProperty(OUR_TB_CODES_KEY));
		}
	}

	/**
	 * Инициализация операции при репликации
	 * @param adapterId - идентификатор адаптера
	 * @param nodeType - тип узла
	 * @param keys - набор ключей
	 */
	public void initializeReplicate(Long adapterId, NodeType nodeType, Set<String> keys) throws BusinessException, BusinessLogicException
	{
		PropertyCategory category = initAdapterData(adapterId);

		if (nodeType != adapterNode.getType())
			throw new BusinessLogicException("Некорректный тип узла в параметре nodeType");

		boolean excludedMessages = keys.remove(EXCLUDED_MESSAGES_PROPERTY_KEY);

		super.initialize(category, keys);

		if (excludedMessages)
			initializeListProperties(EXCLUDED_MESSAGES_PROPERTY_KEY);
	}

	private PropertyCategory initAdapterData(Long adapterId) throws BusinessException, BusinessLogicException
	{
		if (adapterId == null)
			throw new BusinessLogicException("Не указан идентификатор адаптера");

		try
		{
			Adapter adapter = adapterService.getAdapterById(adapterId);
			adapterNode = adapterService.getNode(adapter.getId());

			return new PropertyCategory(adapterNode.getType() == NodeType.SOFIA ? adapterNode.getPrefix() : adapter.getUUID());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}

	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		List<String> values = listProperties.get(EXCLUDED_MESSAGES_PROPERTY_KEY).convertToList(getEntity());
		DbPropertyService.updateListProperty(EXCLUDED_MESSAGES_PROPERTY_KEY, values, getPropertyCategory(), null);

		if (loadAdditProperties())
		{
			addProperty(OfficeCodeReplacer.WRONG_BRANCHES_KEY, listProperties.get(OfficeCodeReplacer.WRONG_BRANCHES_KEY).convertToString(getEntity()));
			addProperty(OUR_TB_CODES_KEY, listProperties.get(OUR_TB_CODES_KEY).convertToString(getEntity()));
		}

		super.save();
	}

	private Map<String, String> parseProperty(String key) throws BusinessException, BusinessLogicException
	{
		ListPropertiesHelper propertyConverter = listProperties.get(key);
		return propertyConverter.convertToProperties((String) getEntity().remove(key));
	}

	private boolean loadAdditProperties()
	{
		return adapterNode.getType() == NodeType.COD || adapterNode.getType() == NodeType.SOFIA;
	}
}
