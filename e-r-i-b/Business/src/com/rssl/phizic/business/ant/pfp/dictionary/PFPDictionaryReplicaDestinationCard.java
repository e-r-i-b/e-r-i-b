package com.rssl.phizic.business.ant.pfp.dictionary;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.PFPDictionaryRecord;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.dictionaries.ReplicaDestination;
import com.rssl.phizic.gate.exceptions.GateException;
import org.hibernate.Session;

import java.util.Collection;

/**
 * ����� ��� ���������� �������� ����� � ��
 * @author koptyaev
 * @ created 26.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class PFPDictionaryReplicaDestinationCard extends PFPDictionaryReplicaDestination implements ReplicaDestination<PFPDictionaryRecord>
{
	private static final ImageService imageService = new ImageService();

	/**
	 * @param dictionary ���������� ���
	 * @param dbInstanceName ��� �������� ��
	 */
	public PFPDictionaryReplicaDestinationCard(Collection<PFPDictionaryRecord> dictionary, String dbInstanceName)
	{
		super(dictionary, dbInstanceName);
	}

	public void add(PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			final PFPDictionaryRecordWrapperMultiImage wrapper = (PFPDictionaryRecordWrapperMultiImage) newValue;
			//������� ���� �������� ��������� ��, ����� �� �������� �������� � �������� � ���� �������� �������� � ��.
			//��� ��� ���� ����������� � ����������, ������ ��� ����� � ��� ������ ��������� �������� ��� ��������
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Card pfpDictionaryRecord = (Card)wrapper.getRecord();
					pfpDictionaryRecord.getDiagramParameters().setImageId(saveImage(wrapper.getImage("imageUrl")));
					pfpDictionaryRecord.setCardIconId(saveImage(wrapper.getImage("cardIconUrl")));
					pfpDictionaryRecord.setProgrammIconId(saveImage(wrapper.getImage("programmIconUrl")));
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

	public void update(final PFPDictionaryRecord oldValue, final PFPDictionaryRecord newValue) throws GateException
	{
		try
		{
			HibernateExecutor.getInstance(getInstanceName()).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					//�������� ����� ������
					PFPDictionaryRecordWrapperMultiImage wrapper = (PFPDictionaryRecordWrapperMultiImage) newValue;
					Card pfpDictionaryRecord = (Card)wrapper.getRecord();

					//��������� ���������� � ��������
					pfpDictionaryRecord.setCardIconId(saveImage(wrapper.getImage("cardIconUrl")));
					pfpDictionaryRecord.setProgrammIconId(saveImage(wrapper.getImage("programmIconUrl")));
					pfpDictionaryRecord.getDiagramParameters().setImageId(saveImage(wrapper.getImage("imageUrl")));
					removeImage(((Card)oldValue).getCardIconId());
					removeImage(((Card)oldValue).getProgrammIconId());
					removeImage(((Card)oldValue).getDiagramParameters().getImageId());

					//��������� ��������
					oldValue.updateFrom(pfpDictionaryRecord);
					simpleService.update(oldValue, getInstanceName());

					//��������� ������������� � ����� ��������
					//���������� ��� ���������� ����������� ���������
					pfpDictionaryRecord.setId(oldValue.getId());
					pfpDictionaryRecord.setUuid(oldValue.getUuid());
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
}
