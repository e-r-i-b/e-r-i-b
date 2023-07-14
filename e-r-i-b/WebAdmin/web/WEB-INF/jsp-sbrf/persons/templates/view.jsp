<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/persons/temlates/view">
	<c:set var="form"   value="${phiz:currentForm(pageContext)}"/>
    <c:set var="bundle" value="employeeTemplatesBundle"/>
	<tiles:insert definition="personEdit">
        <tiles:put name="submenu" type="string" value="TemplatesList"/>
        <tiles:put name="needSave" value="false"/>

        <tiles:put name="data" type="string">
            <input type="hidden" name="id" value="${form.id}">
            <input type="hidden" name="person" value="${form.person}">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="audit_document"/>
                <tiles:put name="name" value="${form.template.templateInfo.name}"/>
                <tiles:put name="data">
                   ${form.html}
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove.help"/>
                        <tiles:put name="bundle"         value="${bundle}"/>
                    </tiles:insert>
                    <c:if test="${form.template.state.code == 'WAIT_CONFIRM_TEMPLATE'}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.confirm"/>
                            <tiles:put name="commandHelpKey" value="button.confirm.help"/>
                            <tiles:put name="bundle"         value="${bundle}"/>
                        </tiles:insert>
                    </c:if>
                    <c:if test="${form.template.templateInfo.state.code == 'REMOVED' && form.template.state.code !='DRAFTTEMPLATE' && form.template.state.code !='SAVED_TEMPLATE'}">
                        <tiles:insert definition="commandButton" flush="false">
                            <tiles:put name="commandKey"     value="button.recover"/>
                            <tiles:put name="commandHelpKey" value="button.recover.help"/>
                            <tiles:put name="bundle"         value="${bundle}"/>
                        </tiles:insert>
                    </c:if>
                </tiles:put>
            </tiles:insert>
            <script>
                if (document.getElementById("headerClaim") && document.getElementById("bodyClaim"))
                {
                    width = document.getElementById("bodyClaim").offsetWidth - 36;
                    document.getElementById("headerClaim").style[width] = width;
                }
            </script>
        </tiles:put>
	</tiles:insert>

</html:form>

