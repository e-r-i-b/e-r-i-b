
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdentityCardRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdentityCardRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}IdType"/>
 *         &lt;element ref="{}IdSeries"/>
 *         &lt;element ref="{}IdNum"/>
 *         &lt;element ref="{}IdStatus" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdentityCardRq_Type", propOrder = {
    "idType",
    "idSeries",
    "idNum",
    "idStatus"
})
public class IdentityCardRqType {

    @XmlElement(name = "IdType", required = true)
    protected String idType;
    @XmlElement(name = "IdSeries", required = true)
    protected String idSeries;
    @XmlElement(name = "IdNum", required = true)
    protected String idNum;
    @XmlElement(name = "IdStatus")
    protected String idStatus;

    /**
     * “ип документа удостовер€ющего личность
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdType() {
        return idType;
    }

    /**
     * Sets the value of the idType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdType(String value) {
        this.idType = value;
    }

    /**
     * Gets the value of the idSeries property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdSeries() {
        return idSeries;
    }

    /**
     * Sets the value of the idSeries property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdSeries(String value) {
        this.idSeries = value;
    }

    /**
     * Gets the value of the idNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdNum() {
        return idNum;
    }

    /**
     * Sets the value of the idNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdNum(String value) {
        this.idNum = value;
    }

    /**
     * Gets the value of the idStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdStatus() {
        return idStatus;
    }

    /**
     * Sets the value of the idStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdStatus(String value) {
        this.idStatus = value;
    }

}
