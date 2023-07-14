
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Тип - блок данных о супруге
 * 
 * <p>Java class for SpouseInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SpouseInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PersonName"/>
 *         &lt;element ref="{}Birthday"/>
 *         &lt;element ref="{}DependentFlag"/>
 *         &lt;element ref="{}SBCreditFlag"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SpouseInfo_Type", propOrder = {
    "personName",
    "birthday",
    "dependentFlag",
    "sbCreditFlag"
})
public class SpouseInfoType {

    @XmlElement(name = "PersonName", required = true)
    protected PersonNameType personName;
    @XmlElement(name = "Birthday", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar birthday;
    @XmlElement(name = "DependentFlag")
    protected boolean dependentFlag;
    @XmlElement(name = "SBCreditFlag", required = true)
    protected String sbCreditFlag;

    /**
     * Gets the value of the personName property.
     * 
     * @return
     *     possible object is
     *     {@link PersonNameType }
     *     
     */
    public PersonNameType getPersonName() {
        return personName;
    }

    /**
     * Sets the value of the personName property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonNameType }
     *     
     */
    public void setPersonName(PersonNameType value) {
        this.personName = value;
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
     * Gets the value of the dependentFlag property.
     * 
     */
    public boolean isDependentFlag() {
        return dependentFlag;
    }

    /**
     * Sets the value of the dependentFlag property.
     * 
     */
    public void setDependentFlag(boolean value) {
        this.dependentFlag = value;
    }

    /**
     * Gets the value of the sbCreditFlag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBCreditFlag() {
        return sbCreditFlag;
    }

    /**
     * Sets the value of the sbCreditFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBCreditFlag(String value) {
        this.sbCreditFlag = value;
    }

}
