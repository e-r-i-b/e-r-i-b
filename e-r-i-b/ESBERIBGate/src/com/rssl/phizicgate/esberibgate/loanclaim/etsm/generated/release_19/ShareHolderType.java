
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - информация об акционере СБ
 * 
 * <p>Java class for ShareHolder_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShareHolder_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CommonShareAmount">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *               &lt;totalDigits value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="PreferenceShareAmount">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}unsignedInt">
 *               &lt;totalDigits value="10"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShareHolder_Type", propOrder = {
    "commonShareAmount",
    "preferenceShareAmount"
})
public class ShareHolderType {

    @XmlElement(name = "CommonShareAmount")
    protected long commonShareAmount;
    @XmlElement(name = "PreferenceShareAmount")
    protected long preferenceShareAmount;

    /**
     * Gets the value of the commonShareAmount property.
     * 
     */
    public long getCommonShareAmount() {
        return commonShareAmount;
    }

    /**
     * Sets the value of the commonShareAmount property.
     * 
     */
    public void setCommonShareAmount(long value) {
        this.commonShareAmount = value;
    }

    /**
     * Gets the value of the preferenceShareAmount property.
     * 
     */
    public long getPreferenceShareAmount() {
        return preferenceShareAmount;
    }

    /**
     * Sets the value of the preferenceShareAmount property.
     * 
     */
    public void setPreferenceShareAmount(long value) {
        this.preferenceShareAmount = value;
    }

}
