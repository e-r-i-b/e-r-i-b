
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Дополнительныe реквизиты
 * 
 * <p>Java class for Requisites_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Requisites_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Requisite" type="{}Requisite_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Requisites_Type", propOrder = {
    "requisites"
})
public class RequisitesType {

    @XmlElement(name = "Requisite", required = true)
    protected List<Requisite> requisites;

    /**
     * Gets the value of the requisites property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the requisites property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRequisites().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Requisite }
     * 
     * 
     */
    public List<Requisite> getRequisites() {
        if (requisites == null) {
            requisites = new ArrayList<Requisite>();
        }
        return this.requisites;
    }

}
