package com.rssl.ikfl.crediting;

import com.rssl.phizic.business.offers.OfferType;
import com.rssl.phizic.utils.StringHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rtischeva
 * @ created 19.01.15
 * @ $Author$
 * @ $Revision$
 */
public class OfferId
{
	private static final Pattern NEW_PATTERN = Pattern.compile("(\\d+)\\|(\\d+)");
	private static final Pattern OLD_PATTERN = Pattern.compile("(\\d+)");

 	private Long id;

	private OfferType offerType;

	public OfferId(Long id, OfferType offerType)
	{
		this.id = id;
		this.offerType = offerType;
	}

	/**
	 * Использовать только для поиска в БД! Для остальных случаев необходимо использовать offerId.toString()
	 * @return
	 */
	public Long getId()
	{
		return id;
	}

	public OfferType getOfferType()
	{
		return offerType;
	}

	@Override
	public String toString()
	{
		return id + "|" + offerType.toValue();
	}

	public static OfferId fromString(String offerIdAsString)
	{
		if (StringHelper.isEmpty(offerIdAsString))
			throw new IllegalArgumentException("Строка с id не может быть пустой!");

		Long id;
		OfferType offerType;
		Matcher matcher = NEW_PATTERN.matcher(offerIdAsString);
		if (!matcher.matches())
		{
			matcher = OLD_PATTERN.matcher(offerIdAsString);
			if (!matcher.matches())
			{
				throw new IllegalArgumentException("Недопустимый формат id предложения: " + offerIdAsString);
			}
			id = Long.parseLong(matcher.group(1));
			offerType = OfferType.OFFER_VERSION_1;
		}
		else
		{
			id = Long.parseLong(matcher.group(1));
			offerType = OfferType.fromValue(matcher.group(2));
		}
		return new OfferId(id, offerType);
	}

	@Override
	public boolean equals(Object obj)
	{
		return (obj.toString().equals(this.toString()));
	}

	@Override
	public int hashCode()
	{
		int result = id.hashCode();
		result = 31 * result + (offerType != null ? offerType.hashCode() : 0);
		return result;
	}
}
