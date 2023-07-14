<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<html:form action="/deposits/editProlongationDateAlgorithm" onsubmit="return setEmptyAction(event);">
    <tiles:importAttribute/>

    <tiles:insert definition="propertiesForm">
        <tiles:put name="data" type="string">
            <tiles:put name="tilesDefinition" type="string" value="deposits"/>
            <tiles:put name="submenu" type="string" value="ProlongationDate"/>
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="additionalStyle" value="propertiesForm"/>
                <tiles:put name="name"><bean:message bundle="depositsBundle" key="prolongation.date.algorithm.name"/></tiles:put>
                <tiles:put name="description"><bean:message bundle="depositsBundle" key="prolongation.date.algorithm.message"/></tiles:put>
                <tiles:put name="data">
                    <table>
                        <tiles:insert definition="propertyField" flush="false">
                            <tiles:put name="fieldName" value="com.rssl.iccs.depositproduct.prolongationDate.newAlgorithm"/>
                            <tiles:put name="requiredField" value="true"/>
                            <tiles:put name="showHint" value="none"/>
                            <tiles:put name="fieldType" value="radio"/>
                            <tiles:put name="selectItems" value="false@Расчет даты пролонгации|true@Значение из КСШ"/>
                        </tiles:insert>
                    </table>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="formButtons">
            <tiles:insert definition="commandButton" flush="false" operation="EditProlongationDateAlgorithmOperation">
                <tiles:put name="commandKey" value="button.save"/>
                <tiles:put name="commandHelpKey" value="button.save.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="isDefault" value="true"/>
            </tiles:insert>
        </tiles:put>

    </tiles:insert>
</html:form>