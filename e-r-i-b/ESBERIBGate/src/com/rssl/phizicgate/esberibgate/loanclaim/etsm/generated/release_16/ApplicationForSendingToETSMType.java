
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип заявка для отправки в ETSM
 * 
 * <p>Java class for ApplicationForSendingToETSM_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ApplicationForSendingToETSM_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Number" type="{}Number_Type"/>
 *         &lt;element name="CreatedByLogin" type="{}CreatedByLogin_Type"/>
 *         &lt;element name="CreatedByFullName" type="{}CreatedByFullName_Type"/>
 *         &lt;element name="DivisionId" type="{}DivisionId_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ApplicationForSendingToETSM_Type", propOrder = {
    "number",
    "createdByLogin",
    "createdByFullName",
    "divisionId"
})
public class ApplicationForSendingToETSMType {

    @XmlElement(name = "Number", required = true)
    protected String number;
    @XmlElement(name = "CreatedByLogin", required = true)
    protected String createdByLogin;
    @XmlElement(name = "CreatedByFullName", required = true)
    protected String createdByFullName;
    @XmlElement(name = "DivisionId", required = true)
    protected String divisionId;

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

    /**
     * Gets the value of the createdByLogin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByLogin() {
        return createdByLogin;
    }

    /**
     * Sets the value of the createdByLogin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByLogin(String value) {
        this.createdByLogin = value;
    }

    /**
     * Gets the value of the createdByFullName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByFullName() {
        return createdByFullName;
    }

    /**
     * Sets the value of the createdByFullName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByFullName(String value) {
        this.createdByFullName = value;
    }

    /**
     * Gets the value of the divisionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDivisionId() {
        return divisionId;
    }

    /**
     * Sets the value of the divisionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDivisionId(String value) {
        this.divisionId = value;
    }

}
