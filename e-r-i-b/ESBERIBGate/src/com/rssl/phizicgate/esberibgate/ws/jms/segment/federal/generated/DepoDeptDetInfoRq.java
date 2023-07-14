
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Получение детальной информации по задолженности по счету ДЕПО
 * 
 * <p>Java class for DepoDeptDetInfoRqType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDeptDetInfoRqType">
 *   &lt;complexContent>
 *     &lt;extension base="{}DepoAccInfoRqType">
 *       &lt;sequence>
 *         &lt;element name="DeptId" type="{}DeptId_Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoDeptDetInfoRqType", propOrder = {
    "deptIds"
})
@XmlRootElement(name = "DepoDeptDetInfoRq")
public class DepoDeptDetInfoRq
    extends DepoAccInfoRqType
{

    @XmlElement(name = "DeptId", required = true)
    protected List<DeptIdType> deptIds;

    /**
     * Gets the value of the deptIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the deptIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDeptIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DeptIdType }
     * 
     * 
     */
    public List<DeptIdType> getDeptIds() {
        if (deptIds == null) {
            deptIds = new ArrayList<DeptIdType>();
        }
        return this.deptIds;
    }

}
