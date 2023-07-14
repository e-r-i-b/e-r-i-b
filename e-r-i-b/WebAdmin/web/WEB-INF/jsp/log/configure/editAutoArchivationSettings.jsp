<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%--
  User: Krenev
  Date: 03.09.2009
  Time: 16:11:48
--%>
<script language="Javascript">
    function setArchiveEnable()
    {
        var check = document.getElementsByName('field(${prefix}archive.enable)')[0];
        var elem;
        elem = document.getElementsByName('field(${prefix}archive.period)')[0];
        elem.disabled = !check.checked;
        elem = document.getElementsByName('field(${prefix}archive.period.type)')[0];
        elem.disabled = !check.checked;
        elem = document.getElementsByName('field(${prefix}archive.archTime)')[0];
        elem.disabled = !check.checked;
        elem = document.getElementsByName('field(${prefix}archive.lastDays)')[0];
        elem.disabled = !check.checked;
        check.value = check.checked;
    }
</script>
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="fields" value="${form.fields}"/>
<span>
    <tiles:insert definition="propertyInput" flush="false">
        <tiles:put name="fieldName" value="${prefix}archive.enable"/>
        <tiles:put name="fieldType" value="checkbox"/>
        <tiles:put name="textMaxLength" value="3"/>
        <tiles:put name="onclickFunc" value="setArchiveEnable();"/>
        <tiles:put name="fieldDesc" value="Архивировать автоматически"/>
    </tiles:insert>
</span>
<tiles:insert definition="tableTemplate" flush="false">
    <tiles:put name="id" value="logLovels"/>
    <tiles:put name="text" value="Архивация протоколов"/>
    <tiles:put name="data">
        <tr>
            <td>
                Периодичность
            </td>
            <td>
                каждые
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="${prefix}archive.period"/>
                    <tiles:put name="textSize" value="3"/>
                    <tiles:put name="textMaxLength" value="3"/>
                </tiles:insert>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="${prefix}archive.period.type"/>
                    <tiles:put name="fieldType" value="select"/>
                    <tiles:put name="selectItems">DAY@<bean:message bundle="loggingBundle" key="archive.period.type.day"/>|MONTH@<bean:message bundle="loggingBundle" key="archive.period.type.month"/></tiles:put>
                </tiles:insert>
                &nbsp;в&nbsp;
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="${prefix}archive.archTime"/>
                    <tiles:put name="textSize" value="5"/>
                    <tiles:put name="textMaxLength" value="5"/>
                </tiles:insert>
            </td>
        </tr>
        <tr>
            <td>
                Оставлять в журнале записи за последние
            </td>
            <td>
                <tiles:insert definition="propertyInput" flush="false">
                    <tiles:put name="fieldName" value="${prefix}archive.lastDays"/>
                    <tiles:put name="textSize" value="4"/>
                    <tiles:put name="textMaxLength" value="3"/>
                    <tiles:put name="fieldDesc" value="дней"/>
                </tiles:insert>
            </td>
        </tr>
        <tr>
            <td>
                Каталог файлов архива
            </td>
            <c:set var="folderKey" value="${prefix}archive.folder"/>
            <td>${fields[folderKey]}&nbsp;</td>
        </tr>
    </tiles:put>
</tiles:insert>
<c:if test="${!form.replication}">
    <script language="Javascript">
        setArchiveEnable();
    </script>
</c:if>