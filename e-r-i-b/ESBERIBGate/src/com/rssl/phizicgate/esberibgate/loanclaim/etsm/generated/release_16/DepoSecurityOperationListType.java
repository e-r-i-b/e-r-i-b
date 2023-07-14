
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Планируемая операция над ценной бумагой
 * 
 * <p>Java class for DepoSecurityOperationList_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoSecurityOperationList_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OperName" type="{}DepoSecurityOperationType_Type" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="CustomOperName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoSecurityOperationList_Type", propOrder = {
    "operNames",
    "customOperName"
})
public class DepoSecurityOperationListType {

    @XmlElement(name = "OperName")
    protected List<String> operNames;
    @XmlElement(name = "CustomOperName")
    protected String customOperName;

    /**
     * Gets the value of the operNames property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the operNames property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOperNames().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getOperNames() {
        if (operNames == null) {
            operNames = new ArrayList<String>();
        }
        return this.operNames;
    }

    /**
     * Gets the value of the customOperName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomOperName() {
        return customOperName;
    }

    /**
     * Sets the value of the customOperName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomOperName(String value) {
        this.customOperName = value;
    }

}
