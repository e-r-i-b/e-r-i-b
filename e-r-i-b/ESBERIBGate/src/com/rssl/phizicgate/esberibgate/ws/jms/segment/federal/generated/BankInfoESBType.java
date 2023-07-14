
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ���� ������� �������� �������������� ���������� ��� ������������� �����.
 * 
 * <p>Java class for BankInfoESB_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankInfoESB_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BranchId" minOccurs="0"/>
 *         &lt;element ref="{}AgencyId" minOccurs="0"/>
 *         &lt;element name="RegionId" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}C">
 *               &lt;maxLength value="3"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}RbTbBrchId" minOccurs="0"/>
 *         &lt;element ref="{}RbBrchId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankInfoESB_Type", propOrder = {
    "branchId",
    "agencyId",
    "regionId",
    "rbTbBrchId",
    "rbBrchId"
})
public class BankInfoESBType {

    @XmlElement(name = "BranchId")
    protected String branchId;
    @XmlElement(name = "AgencyId")
    protected String agencyId;
    @XmlElement(name = "RegionId")
    protected String regionId;
    @XmlElement(name = "RbTbBrchId")
    protected String rbTbBrchId;
    @XmlElement(name = "RbBrchId")
    protected String rbBrchId;

    /**
     * ����� �������.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBranchId() {
        return branchId;
    }

    /**
     * Sets the value of the branchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBranchId(String value) {
        this.branchId = value;
    }

    /**
     * ����� ���������.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgencyId() {
        return agencyId;
    }

    /**
     * Sets the value of the agencyId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgencyId(String value) {
        this.agencyId = value;
    }

    /**
     * Gets the value of the regionId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegionId() {
        return regionId;
    }

    /**
     * Sets the value of the regionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegionId(String value) {
        this.regionId = value;
    }

    /**
     *  8-��� ��� ���������������� �����, � ������� ������ ���� ���, �� ������� ������ �������� ������������� �� ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRbTbBrchId() {
        return rbTbBrchId;
    }

    /**
     * Sets the value of the rbTbBrchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRbTbBrchId(String value) {
        this.rbTbBrchId = value;
    }

    /**
     *  6-��� ��� ���������������� �����, � ������� ������� ������� ������� (������, �����, ���, �����)
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRbBrchId() {
        return rbBrchId;
    }

    /**
     * Sets the value of the rbBrchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRbBrchId(String value) {
        this.rbBrchId = value;
    }

}
