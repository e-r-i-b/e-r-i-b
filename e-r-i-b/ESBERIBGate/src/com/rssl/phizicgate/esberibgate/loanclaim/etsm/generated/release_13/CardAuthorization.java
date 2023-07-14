
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * Тип информация об авторизации карты
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
 *         &lt;element name="AuthorizationCode" type="{}Long"/>
 *         &lt;element name="AuthorizationDtTm" type="{}DateTime" minOccurs="0"/>
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
@XmlRootElement(name = "CardAuthorization")
public class CardAuthorization {

    @XmlElement(name = "AuthorizationCode")
    protected long authorizationCode;
    @XmlElement(name = "AuthorizationDtTm", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
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
