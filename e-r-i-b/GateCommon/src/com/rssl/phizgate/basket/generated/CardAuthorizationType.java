
package com.rssl.phizgate.basket.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * Информация об авторизации карты в Way4, если операция прошла успешно
 * 
 * <p>Java class for CardAuthorization_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CardAuthorization_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AuthorizationCode" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="AuthorizationDtTm" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardAuthorization_Type", propOrder = {
    "authorizationCode",
    "authorizationDtTm"
})
public class CardAuthorizationType {

    @XmlElement(name = "AuthorizationCode")
    protected long authorizationCode;
    @XmlElement(name = "AuthorizationDtTm", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar authorizationDtTm;

    /**
     * Gets the value of the authorizationCode property.
     * 
     */
    public long getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Sets the value of the authorizationCode property.
     * 
     */
    public void setAuthorizationCode(long value) {
        this.authorizationCode = value;
    }

    /**
     * Gets the value of the authorizationDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getAuthorizationDtTm() {
        return authorizationDtTm;
    }

    /**
     * Sets the value of the authorizationDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationDtTm(Calendar value) {
        this.authorizationDtTm = value;
    }

}
