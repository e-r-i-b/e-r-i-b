
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип для разбивки микроопераций списания
 * 
 * <p>Java class for SrcLayoutInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SrcLayoutInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsCalcOperation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="WriteDownOperation" type="{}WriteDownOperation_Type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SrcLayoutInfo_Type", propOrder = {
    "isCalcOperation",
    "writeDownOperations"
})
public class SrcLayoutInfoType {

    @XmlElement(name = "IsCalcOperation")
    protected Boolean isCalcOperation;
    @XmlElement(name = "WriteDownOperation")
    protected List<WriteDownOperationType> writeDownOperations;

    /**
     * Gets the value of the isCalcOperation property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsCalcOperation() {
        return isCalcOperation;
    }

    /**
     * Sets the value of the isCalcOperation property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsCalcOperation(Boolean value) {
        this.isCalcOperation = value;
    }

    /**
     * Gets the value of the writeDownOperations property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the writeDownOperations property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getWriteDownOperations().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link WriteDownOperationType }
     * 
     * 
     */
    public List<WriteDownOperationType> getWriteDownOperations() {
        if (writeDownOperations == null) {
            writeDownOperations = new ArrayList<WriteDownOperationType>();
        }
        return this.writeDownOperations;
    }

}
