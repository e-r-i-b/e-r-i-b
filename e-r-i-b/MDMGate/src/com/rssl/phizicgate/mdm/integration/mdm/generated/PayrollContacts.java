
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PayrollContacts_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PayrollContacts_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}PayrollInfo" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PayrollContacts_Type", propOrder = {
    "payrollInfos"
})
@XmlRootElement(name = "PayrollContacts")
public class PayrollContacts {

    @XmlElement(name = "PayrollInfo", required = true)
    protected List<PayrollInfo> payrollInfos;

    /**
     * Gets the value of the payrollInfos property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the payrollInfos property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPayrollInfos().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PayrollInfo }
     * 
     * 
     */
    public List<PayrollInfo> getPayrollInfos() {
        if (payrollInfos == null) {
            payrollInfos = new ArrayList<PayrollInfo>();
        }
        return this.payrollInfos;
    }

}
