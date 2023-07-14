package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.StaticATMPersonData;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogActionBase;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

/**
 * Получение списка категорий услуг
 * или редирект на список подуслуг услуги (группы услуг) либо список поставщиков услуги.
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 * Запрос: /private/dictionary/servicesPayments.do
 * Элемент сообщения	Тип	Комментарий	Кратность
 * id	string	Идентификатор категории или услуги	[0-1]
 * region	integer	Идентификатор региона	[0-1]
 * regionGuid	string	Кросблочный идентификатор региона		[0-1]
 *      Идентификаторы регионов можно получить используя справочник регионов. Если оба параметра отсутствуют, поиск производится с учетом установленного региона пользователя.
 * autoPaymentOnly	boolean	Признак, отвечающий за вывод поставщиков, поддерживающих создание автоплатежа. Если true – возвращаем только те категории, услуги, поставщики, которые позволяют создать автоплатеж, если false либо не передано, возвращаем всех.	[0-1]
 * paginationSize	integer	Размер результирующей выборки. При отсутствии данного параметра вернутся все результаты соответствующие запросу.	[0-1]
 * paginationOffset	integer	Смещение относительно начала выборки. По умолчанию равен нулю.	[0-1]
 */
public class ListCategoryServiceProviderATMAction extends ApiCatalogActionBase
{

	protected boolean getFinalDescendants(IndexForm frm)
	{
		return !ConfigFactory.getConfig(AtmApiConfig.class).isShowServices();
	}

	protected String getChanel()
	{
		return "ATMAPI";
	}

	protected Long getRegionId(ListPaymentServiceFormBase form) throws BusinessException
	{
		ListCategoryATMForm frm = (ListCategoryATMForm) form;
		//TODO Костыль для BUG082486: [ISUP] АТМ : поиск поставщика. Прихранивает в ID региона при получении cправочника поставщиков.
		// Убрать в следующем релизе и актуализировать спеку по ATM, добавив регион для "Получения списка услуг поставщика".
		Long regionId = ListATMActionBase.getRegionIdWithGuid(frm);
		if (PersonContext.isAvailable() &&
			(PersonContext.getPersonDataProvider().getPersonData() instanceof StaticATMPersonData))
		{
			StaticATMPersonData personData = (StaticATMPersonData)PersonContext.getPersonDataProvider().getPersonData();
			personData.setServProvRegionID(regionId);
		}

		return regionId;
	}
}
