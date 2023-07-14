
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип статус для уведомления об ошибке при создании заявки
 * 
 * <p>Java class for ErrorStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ErrorStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StatusCRM" type="{}StatusCRM_Type"/>
 *         &lt;element name="StatusDescCRM" type="{}StatusDescCRM_Type" minOccurs="0"/>
 *         &lt;element name="Number" type="{}Number_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorStatus_Type", propOrder = {
    "statusCRM",
    "statusDescCRM",
    "number"
})
public class ErrorStatusType {

    @XmlElement(name = "StatusCRM", required = true)
    protected String statusCRM;
    @XmlElement(name = "StatusDescCRM")
    protected String statusDescCRM;
    @XmlElement(name = "Number", required = true)
    protected String number;

    /**
     * Gets the value of the statusCRM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusCRM() {
        return statusCRM;
    }

    /**
     * Sets the value of the statusCRM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusCRM(String value) {
        this.statusCRM = value;
    }

    /**
     * Gets the value of the statusDescCRM property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDescCRM() {
        return statusDescCRM;
    }

    /**
     * Sets the value of the statusDescCRM property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDescCRM(String value) {
        this.statusDescCRM = value;
    }

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumber() {
        return number;
    }

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumber(String value) {
        this.number = value;
    }

}
