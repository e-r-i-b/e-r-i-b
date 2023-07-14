<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/dictionaries/provider/summRestrictions" onsubmit="return setEmptyAction(event);" enctype="multipart/form-data">
    <tiles:insert definition="recipientDictionariesEdit">
        <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
        <c:set var="isEditable" value="${frm.editable}"/>

        <tiles:put name="submenu" value="Provider/SummRestrictions"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="providerBundle" key="label.provider.summRestr"/>
                </tiles:put>

                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="providerBundle" key="label.provider.minSum"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(minSum)" name="frm" size="10" styleClass="moneyField" disabled="${!isEditable}"/>ð.
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message bundle="providerBundle" key="label.provider.maxSum"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <html:text property="field(maxSum)" name="frm" size="10" styleClass="moneyField" disabled="${!isEditable}"/>ð.
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="action" value="/private/dictionaries/provider/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditServiceProvidersOperation">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="providerBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>