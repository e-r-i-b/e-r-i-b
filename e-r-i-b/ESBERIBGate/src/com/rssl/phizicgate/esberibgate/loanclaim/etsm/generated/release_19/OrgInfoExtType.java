
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - Описание организацции
 * 
 * <p>Java class for OrgInfoExt_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrgInfoExt_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FullName">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="120"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TaxId">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="12"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="IndustSector" type="{}IndustSector_Type"/>
 *         &lt;element name="NumEmployeesCode" type="{}NumEmployeesCode_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrgInfoExt_Type", propOrder = {
    "fullName",
    "taxId",
    "industSector",
    "numEmployeesCode"
})
public class OrgInfoExtType {

    @XmlElement(name = "FullName", required = true)
    protected String fullName;
    @XmlElement(name = "TaxId", required = true)
    protected String taxId;
    @XmlElement(name = "IndustSector", required = true)
    protected IndustSectorType industSector;
    @XmlElement(name = "NumEmployeesCode", required = true)
    protected String numEmployeesCode;

    /**
     * Gets the value of the fullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Sets the value of the fullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFullName(String value) {
        this.fullName = value;
    }

    /**
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Gets the value of the industSector property.
     * 
     * @return
     *     possible object is
     *     {@link IndustSectorType }
     *     
     */
    public IndustSectorType getIndustSector() {
        return industSector;
    }

    /**
     * Sets the value of the industSector property.
     * 
     * @param value
     *     allowed object is
     *     {@link IndustSectorType }
     *     
     */
    public void setIndustSector(IndustSectorType value) {
        this.industSector = value;
    }

    /**
     * Gets the value of the numEmployeesCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumEmployeesCode() {
        return numEmployeesCode;
    }

    /**
     * Sets the value of the numEmployeesCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumEmployeesCode(String value) {
        this.numEmployeesCode = value;
    }

}
