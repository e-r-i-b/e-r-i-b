<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%--
  User: Zhuravleva
  Date: 18.11.2005
  Time: 18:30:52
--%>
<%@ include file="personData.jsp"%>
<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Edit'}"/>
    <tiles:put name="action"  value="/persons/edit.do?person=${personId}"/>
    <tiles:put name="text"    value="Общие сведения"/>
	<tiles:put name="title"   value="Общие сведения о пользователе"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Resources'}"/>
    <tiles:put name="action"  value="/persons/resources.do?person=${personId}"/>
    <tiles:put name="text"    value="Счета и карты"/>
	<tiles:put name="title"   value="Счета и пластиковые карты пользователя"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset">
	<tiles:put name="enabled" value="${submenu != 'Operations'}"/>
    <tiles:put name="action"  value="/persons/useroperations.do?person=${personId}"/>
	<tiles:put name="text"    value="Права доступа"/>
	<tiles:put name="title"   value="Права пользователя на доступ к операциям системы"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="EmpoweredPersonManagement">
	<tiles:put name="enabled" value="${submenu != 'EmpoweredPersons'}"/>
    <tiles:put name="action"  value="/persons/empowered/list.do?person=${personId}"/>
	<tiles:put name="text"    value="Представители"/>
	<tiles:put name="title"   value="Список доверенных лиц"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PersonGroupManagement">
	<tiles:put name="enabled" value="${submenu != 'Groups'}"/>
    <tiles:put name="action"  value="/persons/groups.do?person=${personId}"/>
	<tiles:put name="text"    value="Группы"/>
	<tiles:put name="title"   value="Работа с группами"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="PersonPasswordCardManagment">
	<tiles:put name="enabled" value="${submenu != 'PasswordCards'}"/>
    <tiles:put name="action"  value="/cardspersons/cards.do?person=${personId}"/>
	<tiles:put name="text"    value="Карты ключей"/>
	<tiles:put name="title"   value="Показать карты ключей пользователя"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="CertDemandControl">
	<tiles:put name="enabled" value="${submenu != 'Certificate'}"/>
    <tiles:put name="action"  value="/persons/certification/list.do?person=${personId}"/>
	<tiles:put name="text"    value="Сертификаты"/>
	<tiles:put name="title"   value="Показать сертификаты пользователя"/>
</tiles:insert>
                                     
<tiles:insert definition="leftMenuInset" service="TemplatesDocumentsManagement">
	<tiles:put name="enabled" value="${submenu != 'PackagesTemplates'}"/>
    <tiles:put name="action"  value="/private/package/list.do?person=${personId}"/>
	<tiles:put name="text"    value="Пакет документов банка"/>
	<tiles:put name="title"   value="Показать пакет документов пользователя"/>
</tiles:insert>

<br>
<script type="text/javascript">
function printClientInfo(event, personId, operation)
		{
			if (personId != null && personId != ''
					<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
			{
				openWindow(event, "${phiz:calculateActionURL(pageContext, '/persons/print.do')}?person=" + personId);
			}
			else
			{
                window.alert("Перед печатью анкеты клиента необходимо ее сохранить");
			}
		}
function printContract (event, personId)
{
    if (personId != null && personId != '' && personId != 'null'
			<c:if test="${(empty ListPersonResourcesForm)}">&& !isDataChanged()</c:if>)
	{
       openWindow(event,'${path}'+'printContract7.do?person=' + personId,'4',null);
       openWindow(event,'${path}'+'printContract2.do?person=' + personId,'3',null);
       openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);
       openWindow(event,'${path}'+'printContract.do?person=' + personId,'1',null);
    }
	else
	{
		callOperation(event, operation, "Перед печатью анкеты клиента необходимо ее сохранить\nСохранить анкету?");
	}
}
function printAdditionalContract (event, personId)
{
    if (personId != null && personId != '')
	{
       openWindow(event,'${path}'+'printContract9.do?person=' + personId,'5',null);
       openWindow(event,'${path}'+'printContract8_pr5.do?person=' + personId,'2',null);
    }
	else
	{
		callOperation(event, operation, "Перед печатью анкеты клиента необходимо ее сохранить\nСохранить анкету?");
	}
}

function printRecession (event, personId)
{
	openWindow(event, "${phiz:calculateActionURL(pageContext, '/persons/printRecession.do')}?person=" + personId,"","resizable=1,menubar=1,toolbar=0,scrollbars=1,width=700,height=600");
}
</script>

<tiles:insert definition="leftMenuInsetGroup">
	<tiles:put name="text"    value="Печать документов"/>
	<tiles:put name="name"    value="Print"/>
	<tiles:put name="data">
	<tr><td>
		<c:if test="${isNewOrTemplate}">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled"       value="true"/>
                <tiles:put name="text"          value="Печать"/>
                <tiles:put name="title"         value="Печать"/>
                <tiles:put name="parentName"    value="Print"/>
                <tiles:put name="onclick">
                    printClientInfo(event,${empty personId ? 'null': personId})
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled"       value="true"/>
                <tiles:put name="text"          value="Печать заявления"/>
                <tiles:put name="title"         value="Печать заявления"/>
                <tiles:put name="parentName"    value="Print"/>
                <tiles:put name="onclick">
                    openWindow(event, '${phiz:calculateActionURL(pageContext, '/documents/agreement.do')}?person=${personId}', '5', null);
                </tiles:put>
            </tiles:insert>

        </c:if>
        <c:if test="${not isNewOrTemplate and not isDelete}">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled"       value="true"/>
                <tiles:put name="text"          value="Прекращение обслуживания"/>
                <tiles:put name="title"         value="Печать заявления прекращении обслуживания"/>
                <tiles:put name="parentName"    value="Print"/>
                <tiles:put name="onclick">
                    printRecession(event,${empty personId ? 'null': personId})
                </tiles:put>
            </tiles:insert>
        </c:if>
        <c:if test="${isDelete}">
            <tiles:insert definition="leftMenuInset" flush="false">
                <tiles:put name="enabled"       value="true"/>
                <tiles:put name="text"          value="Прекращение обслуживания"/>
                <tiles:put name="title"         value="Печать заявления прекращении обслуживания"/>
                <tiles:put name="parentName"    value="Print"/>
                <tiles:put name="onclick">
                    printRecession(event,${empty personId ? 'null': personId})
                </tiles:put>
            </tiles:insert>
            </td></tr>
         </c:if>
	</tiles:put>
</tiles:insert>
