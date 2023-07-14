
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип - семейное положение
 * 
 * <p>Java class for MaritalCondition_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MaritalCondition_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{}MaritalStatus_Type"/>
 *         &lt;element name="MarriageContractFlag" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ChildrenFlag" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SpouseInfo" type="{}SpouseInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MaritalCondition_Type", propOrder = {
    "status",
    "marriageContractFlag",
    "childrenFlag",
    "spouseInfo"
})
public class MaritalConditionType {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "MarriageContractFlag")
    protected Boolean marriageContractFlag;
    @XmlElement(name = "ChildrenFlag")
    protected boolean childrenFlag;
    @XmlElement(name = "SpouseInfo")
    protected SpouseInfoType spouseInfo;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the marriageContractFlag property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getMarriageContractFlag() {
        return marriageContractFlag;
    }

    /**
     * Sets the value of the marriageContractFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMarriageContractFlag(Boolean value) {
        this.marriageContractFlag = value;
    }

    /**
     * Gets the value of the childrenFlag property.
     * 
     */
    public boolean isChildrenFlag() {
        return childrenFlag;
    }

    /**
     * Sets the value of the childrenFlag property.
     * 
     */
    public void setChildrenFlag(boolean value) {
        this.childrenFlag = value;
    }

    /**
     * Gets the value of the spouseInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SpouseInfoType }
     *     
     */
    public SpouseInfoType getSpouseInfo() {
        return spouseInfo;
    }

    /**
     * Sets the value of the spouseInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SpouseInfoType }
     *     
     */
    public void setSpouseInfo(SpouseInfoType value) {
        this.spouseInfo = value;
    }

}
