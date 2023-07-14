<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<c:set var="widgetForm" value="${phiz:currentForm(pageContext)}"/>
<c:set var="title" value="${widgetForm.title}"/>

<div data-dojo-type="widget.ViceWidget" class="widget ViceWidget">
    <script type="dojo/connect">
        this.codename = "${widgetForm.codename}";
        this.settings = ${widgetForm.settings};
        this.sizeable = ${widgetForm.widget.definition.sizeable};
        this.forbiddenMode = ${widgetForm.forbiddenMode};
    </script>
    <%@ include file="/WEB-INF/jsp/widget/viceWidgetBody.jsp"%>
</div>
