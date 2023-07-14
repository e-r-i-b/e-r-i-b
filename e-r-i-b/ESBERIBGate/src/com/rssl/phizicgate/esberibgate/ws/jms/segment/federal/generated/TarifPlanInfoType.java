
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о тарифном плане и сегменте клиента
 * 
 * <p>Java class for TarifPlanInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TarifPlanInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Code" type="{}TarifPlanCodeType" minOccurs="0"/>
 *         &lt;element name="ConnectionDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="SegmentCode" type="{}SegmentCodeType" minOccurs="0"/>
 *         &lt;element name="ManagerId" type="{}String" minOccurs="0"/>
 *         &lt;element name="ManagerOsb" type="{}AgencyId_Type" minOccurs="0"/>
 *         &lt;element name="ManagerTb" type="{}RegionId_Type" minOccurs="0"/>
 *         &lt;element name="ManagerFilial" type="{}BranchId_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TarifPlanInfo_Type", propOrder = {
    "code",
    "connectionDate",
    "segmentCode",
    "managerId",
    "managerOsb",
    "managerTb",
    "managerFilial"
})
public class TarifPlanInfoType {

    @XmlElement(name = "Code")
    protected String code;
    @XmlElement(name = "ConnectionDate")
    protected String connectionDate;
    @XmlElement(name = "SegmentCode")
    protected String segmentCode;
    @XmlElement(name = "ManagerId")
    protected String managerId;
    @XmlElement(name = "ManagerOsb")
    protected String managerOsb;
    @XmlElement(name = "ManagerTb")
    protected String managerTb;
    @XmlElement(name = "ManagerFilial")
    protected String managerFilial;

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCode(String value) {
        this.code = value;
    }

    /**
     * Gets the value of the connectionDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionDate() {
        return connectionDate;
    }

    /**
     * Sets the value of the connectionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionDate(String value) {
        this.connectionDate = value;
    }

    /**
     * Gets the value of the segmentCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSegmentCode() {
        return segmentCode;
    }

    /**
     * Sets the value of the segmentCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSegmentCode(String value) {
        this.segmentCode = value;
    }

    /**
     * Gets the value of the managerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerId() {
        return managerId;
    }

    /**
     * Sets the value of the managerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerId(String value) {
        this.managerId = value;
    }

    /**
     * Gets the value of the managerOsb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerOsb() {
        return managerOsb;
    }

    /**
     * Sets the value of the managerOsb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerOsb(String value) {
        this.managerOsb = value;
    }

    /**
     * Gets the value of the managerTb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerTb() {
        return managerTb;
    }

    /**
     * Sets the value of the managerTb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerTb(String value) {
        this.managerTb = value;
    }

    /**
     * Gets the value of the managerFilial property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagerFilial() {
        return managerFilial;
    }

    /**
     * Sets the value of the managerFilial property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagerFilial(String value) {
        this.managerFilial = value;
    }

}
