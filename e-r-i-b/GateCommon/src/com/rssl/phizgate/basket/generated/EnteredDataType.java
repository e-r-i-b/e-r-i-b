
package com.rssl.phizgate.basket.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Введенные клиентом данные. В данном запросе группа не возвращается. Если БС ТБ выполняет пересчет или изменение введенных клиентом данных, то пересчитанные значения помещаются в данную группу.
 * 
 * <p>Java class for EnteredData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EnteredData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DataItem" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnteredData_Type", propOrder = {
    "dataItems"
})
public class EnteredDataType {

    @XmlElement(name = "DataItem", required = true)
    protected List<String> dataItems;

    /**
     * Gets the value of the dataItems property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dataItems property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDataItems().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getDataItems() {
        if (dataItems == null) {
            dataItems = new ArrayList<String>();
        }
        return this.dataItems;
    }

}
