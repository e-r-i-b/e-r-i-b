
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о разделе счета (ДЕПО).
 * 
 * <p>Java class for DepoSecurityRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoSecurityRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DivisionNumber" type="{}DivisionNumber_Type"/>
 *         &lt;element name="SectionInfo" type="{}DepoSecuritySectionInfo_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoSecurityRec_Type", propOrder = {
    "divisionNumber",
    "sectionInfos"
})
public class DepoSecurityRecType {

    @XmlElement(name = "DivisionNumber", required = true)
    protected DivisionNumberType divisionNumber;
    @XmlElement(name = "SectionInfo")
    protected List<DepoSecuritySectionInfoType> sectionInfos;

    /**
     * Gets the value of the divisionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link DivisionNumberType }
     *     
     */
    public DivisionNumberType getDivisionNumber() {
        return divisionNumber;
    }

    /**
     * Sets the value of the divisionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link DivisionNumberType }
     *     
     */
    public void setDivisionNumber(DivisionNumberType value) {
        this.divisionNumber = value;
    }

    /**
     * Gets the value of the sectionInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sectionInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSectionInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoSecuritySectionInfoType }
     * 
     * 
     */
    public List<DepoSecuritySectionInfoType> getSectionInfos() {
        if (sectionInfos == null) {
            sectionInfos = new ArrayList<DepoSecuritySectionInfoType>();
        }
        return this.sectionInfos;
    }

}
