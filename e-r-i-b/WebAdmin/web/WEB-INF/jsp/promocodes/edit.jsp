<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/promocodes/edit" onsubmit="return setEmptyAction(event);">
<tiles:insert definition="configEdit">
    <c:set var="bundle" value="promocodesBundle"/>
    <tiles:put name="submenu" type="string" value="PromoCodeSettings"/>

    <tiles:put name="menu" type="string">
	    <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey"     value="button.close"/>
            <tiles:put name="commandHelpKey" value="button.close"/>
            <tiles:put name="bundle"  value="commonBundle"/>
            <tiles:put name="image"   value=""/>
            <tiles:put name="action" value="/promocodes/list.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>
    </tiles:put>

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:put name="data" type="string">

        <script type="text/javascript">
            function openAllowedTBDictionary(callback)
            {
                window.setDepartmentInfo = callback;
                win = window.open(document.webRoot+'/dictionaries/allowedTerbanks.do?type=oneSelection',
                               'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=1020px,height=800px");
                win.moveTo(screen.width / 2 - 350, screen.height / 2 - 400);
            }

            function setDepartmentInfo(resource)
            {
               setElement('field(TBName)', resource['name']);
               setElement('TBNameTextField', resource['name']);
               setElement('field(synchKey)', resource['sKey']);
            }

            function setTermless()
            {
                var check = getRadioValue(document.getElementsByName("field(termless)"));
                var disable = (check != null && check);
                var date = getElement("field(endDate)");
                var time = getElement("field(endTime)");
                date.disabled = disable;
                time.disabled = disable;
            }
        </script>

	    <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="page.edit.title" bundle="${bundle}"/>
                </tiles:put>
                <tiles:put name="description">
                    <bean:message key="page.edit.description" bundle="${bundle}"/>
                </tiles:put>

                <tiles:put name="data">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Тербанк
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:hidden  property="field(TBName)"/>
                            <html:hidden  property="field(synchKey)"/>
                            <html:text    property="TBNameTextField" value="${form.fields.TBName}" disabled="true" size="40"/>
                            <input  type="button" class="buttWhite" onclick="openAllowedTBDictionary(setDepartmentInfo);" value="..."/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            Период акции
                        </tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <c:set var="startDate"><bean:write name="form" property="fields.startDate" format="dd.MM.yyyy"/></c:set>
                            <c:set var="startTime"><bean:write name="form" property="fields.startTime" format="HH:mm:ss"/></c:set>
                            с&nbsp;&nbsp;
                            <html:text property="field(startDate)" value="${startDate}" styleClass="dot-date-pick"/>
                            <html:text property="field(startTime)" value="${startTime}" styleClass="time-template" size="8"/>
                            &nbsp;
                            <html:checkbox property="field(termless)" onclick="setTermless()"/><bean:message bundle="${bundle}" key="label.termless"/>
                            <br>
                            <br>
                            по
                            <c:set var="endDate"><bean:write name="form" property="fields.endDate" format="dd.MM.yyyy"/></c:set>
                            <c:set var="endTime"><bean:write name="form" property="fields.endTime" format="HH:mm:ss"/></c:set>
                            <html:text property="field(endDate)" value="${endDate}" disabled="true" styleClass="dot-date-pick"/>
                            <html:text property="field(endTime)" value="${endTime}" disabled="true" styleClass="time-template" size="8"/>
                        </tiles:put>
                    </tiles:insert>

                    <script type="text/javascript">
                         setTermless();
                    </script>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle" value="${bundle}"/>
                        <tiles:put name="action"  value="/promocodes/list.do"/>
                    </tiles:insert>
                    <tiles:insert definition="commandButton" flush="false" operation="EditPromoCodeSettingsOperation">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
	</tiles:put>
</tiles:insert>
</html:form>
