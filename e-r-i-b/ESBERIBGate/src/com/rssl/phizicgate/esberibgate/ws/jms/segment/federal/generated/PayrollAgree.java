
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Зарплатный договор
 * 
 * <p>Java class for PayrollAgree_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayrollAgree_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgreeId" type="{}AgreeId_Type" minOccurs="0"/>
 *         &lt;element ref="{}DirectSaler" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayrollAgree_Type", propOrder = {
    "agreeId",
    "directSalers"
})
@XmlRootElement(name = "PayrollAgree")
public class PayrollAgree {

    @XmlElement(name = "AgreeId")
    protected String agreeId;
    @XmlElement(name = "DirectSaler")
    protected List<DirectSaler> directSalers;

    /**
     * Gets the value of the agreeId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreeId() {
        return agreeId;
    }

    /**
     * Sets the value of the agreeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreeId(String value) {
        this.agreeId = value;
    }

    /**
     * Gets the value of the directSalers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the directSalers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDirectSalers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DirectSaler }
     * 
     * 
     */
    public List<DirectSaler> getDirectSalers() {
        if (directSalers == null) {
            directSalers = new ArrayList<DirectSaler>();
        }
        return this.directSalers;
    }

}
