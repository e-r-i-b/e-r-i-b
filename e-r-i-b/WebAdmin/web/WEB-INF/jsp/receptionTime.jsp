<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/documents/receptiontime/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${EditDocumentsReceptionTimeForm}"/>
    <c:set var="useRetailCalendar" value="${frm.useRetailCalendar}"/>
    <c:set var="useParentSettingsDisabled" value="${not frm.haveParentDepartment}"/>

<tiles:insert definition="departmentsEdit">
	<tiles:put name="submenu" type="string" value="EditDocumentsReceptionTime"/>
    <c:if test="${not phiz:impliesOperation('EditDocumentsReceptionTimeOperation', 'DepartmentsManagement')}">
        <tiles:put name="needSave" value="false"/>
    </c:if>


    <tiles:put name="data" type="string">
        <script type="text/javascript">

            function setParentSettings(formName)
            {
                var name=formName+'useParentSettings';
                var start = formName+'timeStart';
                var end = formName+'timeEnd';
                var calendar = formName + 'calendarId';

                var parentChecked = getRadioValue(document.getElementsByName('field('+name+')')) != null;
                var calendarId    = getElementValue('field(parent'+calendar+')');

                if(calendarId == "")
                    calendarId = "0";

                if ( parentChecked )
                {
                    setElement('field('+start+')',getElementValue('field(parent'+start+')'));
                    setElement('field('+end+')',getElementValue('field(parent'+end+')'));

                    document.getElementsByName('field('+start+')')[0].disabled = true;
                    document.getElementsByName('field('+end+')')[0].disabled = true;
                    if (!${useRetailCalendar})
                    {
                        document.getElementsByName('field('+calendar+')')[0].disabled = true;
                        setElement('field('+calendar+')',calendarId);                                                    
                    }
                }
                else
                {
                    document.getElementsByName('field('+start+')')[0].disabled = false;
                    document.getElementsByName('field('+end+')')[0].disabled = false;
                    if (!${useRetailCalendar})
                        document.getElementsByName('field('+calendar+')')[0].disabled = false;
                }
            }
			</script>

        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="editDepartments"/>
		    <tiles:put name="name" value="Задание времени приема документов"/>
		    <tiles:put name="description" value="Используйте данную форму для задания времени приема документов."/>            
            <tiles:put name="data" type="string">
            <input type="hidden" id="calendarId" value=""/>

				<c:forEach items="${frm.payments}" var="paymentType">

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <html:hidden property="payments(${paymentType.key})"/>
                            <bean:write name="frm" property="payments(${paymentType.key})"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            C&nbsp;<html:text property="field(${paymentType.key}timeStart)" size="5"/>
                            <html:hidden property="field(parent${paymentType.key}timeStart)"/>
                            По&nbsp;<html:text property="field(${paymentType.key}timeEnd)" size="5"/>
                            <html:hidden property="field(parent${paymentType.key}timeEnd)"/>
                            <html:select disabled="${useRetailCalendar}" property="field(${paymentType.key}calendarId)" styleId="field(${paymentType.key}calendarId)" styleClass="select">
                                <c:choose>
                                    <c:when test="${useRetailCalendar}">
                                        <html:option value="">Календарь внешней системы</html:option>
                                    </c:when>
                                    <c:otherwise>
                                        <html:option value="0">Стандартный календарь</html:option>
                                        <c:forEach items="${frm.calendars}" var="calendar">
                                            <html:option value="${calendar.id}"><c:out value="${calendar.name}"/></html:option>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </html:select>
                            <html:hidden property="field(parent${paymentType.key}calendarId)"/>
                            <html:checkbox property="field(${paymentType.key}useParentSettings)" value="true" onclick="setParentSettings('${paymentType.key}');" disabled="${useParentSettingsDisabled}"/>
                            Использовать настройку вышестоящего
                        </tiles:put>
                    </tiles:insert>
                    <script type="text/javascript">
                        setParentSettings('${paymentType.key}');
                    </script>


                    <script type="text/javascript">
                        setParentSettings('${paymentType.key}');
                    </script>
				</c:forEach>

            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false" operation="EditDocumentsReceptionTimeOperation">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="employeesBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
	       </tiles:put>
        </tiles:insert>

    </tiles:put>

</tiles:insert>
</html:form>
