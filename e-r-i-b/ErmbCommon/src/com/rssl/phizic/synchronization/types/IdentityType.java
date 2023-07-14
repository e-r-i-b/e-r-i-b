
package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Идентификационные данные клиента
 * 
 * <p>Java class for IdentityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="lastname" type="{}StringType"/>
 *         &lt;element name="firstname" type="{}StringType"/>
 *         &lt;element name="middlename" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="birthday" type="{}DateType"/>
 *         &lt;element name="identityCard" type="{}IdentityCardType"/>
 *         &lt;element name="tb" type="{}String3Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityType", propOrder = {
    "lastname",
    "firstname",
    "middlename",
    "birthday",
    "identityCard",
    "tb"
})
public class IdentityType {

    @XmlElement(required = true)
    protected String lastname;
    @XmlElement(required = true)
    protected String firstname;
    protected String middlename;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar birthday;
    @XmlElement(required = true)
    protected IdentityCardType identityCard;
    @XmlElement(required = true)
    protected String tb;

    /**
     * Gets the value of the lastname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the value of the lastname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastname(String value) {
        this.lastname = value;
    }

    /**
     * Gets the value of the firstname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the value of the firstname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFirstname(String value) {
        this.firstname = value;
    }

    /**
     * Gets the value of the middlename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * Sets the value of the middlename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMiddlename(String value) {
        this.middlename = value;
    }

    /**
     * Gets the value of the birthday property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getBirthday() {
        return birthday;
    }

    /**
     * Sets the value of the birthday property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBirthday(Calendar value) {
        this.birthday = value;
    }

    /**
     * Gets the value of the identityCard property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityCardType }
     *     
     */
    public IdentityCardType getIdentityCard() {
        return identityCard;
    }

    /**
     * Sets the value of the identityCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityCardType }
     *     
     */
    public void setIdentityCard(IdentityCardType value) {
        this.identityCard = value;
    }

    /**
     * Gets the value of the tb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTb() {
        return tb;
    }

    /**
     * Sets the value of the tb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTb(String value) {
        this.tb = value;
    }

}
