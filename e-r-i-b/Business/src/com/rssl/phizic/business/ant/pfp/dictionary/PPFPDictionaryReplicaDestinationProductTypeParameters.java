package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

import java.util.Collection;

/**
 * @author koptyaev
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */
public class PPFPDictionaryReplicaDestinationProductTypeParameters extends PFPDictionaryReplicaDestination implements ReplicaDestination<PFPDictionaryRecord>
{
	private static final ImageService imageService = new ImageService();

	/**
	 * @param dictionary справочник пфп
	 * @param dbInstanceName имя инстанса БД
	 */
	public PPFPDictionaryReplicaDestinationProductTypeParameters(Collection<PFPDictionaryRecord> dictionary, String dbInstanceName)
	{
		super(dictionary, dbInstanceName);
	}

	public void add(PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			final PFPDictionaryRecordWrapperOneImage wrapper = (PFPDictionaryRecordWrapperOneImage) newValue;
			//вначале надо положить картинкув бд, потом ид картинки положить в сущность и саму сущность засунуть в бд.
			//все это надо осуществить в транзакции, потому что иначе у нас можеть оказаться картинка без сущности
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					PFPDictionaryRecord pfpDictionaryRecord = wrapper.getRecord();
					pfpDictionaryRecord.setImageId(saveImage(wrapper.getImage()));
					simpleService.add(pfpDictionaryRecord, getInstanceName());
					return null;
				}
			}
			);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	private Long saveImage(Image image) throws BusinessException
	{
		if (image == null || image.isEmpty())
			return null;

		return imageService.addOrUpdate(image, getInstanceName()).getId();
	}

	private void removeImage(Long imageId) throws BusinessException
	{
		if (imageId == null)
			return;

		imageService.removeImageById(imageId, getInstanceName());
	}

	public void update(final PFPDictionaryRecord oldValue, PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			//получаем новые данные
			final PFPDictionaryRecordWrapperOneImage wrapper = (PFPDictionaryRecordWrapperOneImage) newValue;
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					ProductTypeParameters pfpDictionaryRecord = (ProductTypeParameters)wrapper.getRecord();
					//обновляем информацию о картинке
					pfpDictionaryRecord.setImageId(saveImage(wrapper.getImage()));
					removeImage(oldValue.getImageId());
					//обновляем сущность
					oldValue.updateFrom(pfpDictionaryRecord);
					simpleService.update(oldValue, getInstanceName());

					//добавляем идентификатор в новую сущность
					//необходимо при добавлении комплексных продуктов
					pfpDictionaryRecord.setId(oldValue.getId());
					pfpDictionaryRecord.setUuid(oldValue.getUuid());
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
