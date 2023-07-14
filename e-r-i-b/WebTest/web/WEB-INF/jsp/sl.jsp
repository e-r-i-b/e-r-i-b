<%@ page import="com.rssl.phizic.business.operations.OperationDescriptorService" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html>
<head>
	<link rel="stylesheet" type="text/css" href="${skinUrl}/style.css">
    <link rel="stylesheet" type="text/css" href="${globalUrl}/systemAll.css">
</head>

<body>
<%
	OperationDescriptorService ods = new OperationDescriptorService();
	List descriptors = new ArrayList(10);
	List all = ods.getAll();
	for (int i = 0; i < 10; i++)
	{
		descriptors.add(all.get(i));
	}
	pageContext.setAttribute("descriptors", descriptors);
%>
<sl:collection id="id" name="descriptors" model="list" view="test-list">
	<sl:collectionItem styleId="col1" title="title1" property="id" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col2" title="title2" styleClass="lmBgHeader" property="key">
		<c:out value="${id.key}"/>
		qqqqq
		<sl:collectionItemParam id="styleClass" condition="${id.key=='GetCardAbstractOperation'}" value="222">
			qqq
		</sl:collectionItemParam>
		<sl:collectionItemParam id="styleClass" condition="${id.key=='CardInfoPrintOperation'}" value="333">
			qqq
		</sl:collectionItemParam>
	</sl:collectionItem>
	<sl:collectionItem styleId="col3" title="title3" property="operationClassName"/>
	<sl:collectionItem styleId="col4" title="title4" property="name">aaa</sl:collectionItem>
	<sl:collectionItem styleId="col5" title="title5">
		<sl:collectionItemParam id="styleClass" condition="${id.key=='GetCardAbstractOperation'}" value="222">
			qqq
		</sl:collectionItemParam>
		<sl:collectionItemParam id="styleClass" condition="${id.key=='CardInfoPrintOperation'}" value="333"/>
	</sl:collectionItem>
</sl:collection>

Table With Footer

<sl:collection id="id" name="descriptors" model="list" view="test-list">
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"
	                   footer="TEST"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col11" title="title11" property="id" width="30" styleClass="ListLine1"
	                   footerType="sum"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col11" title="title11" width="30" styleClass="ListLine1" footerType="sum">
		<c:out value="${id.id}"/>
	</sl:collectionItem>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col11" title="title11" property="id" width="30" styleClass="ListLine1"
	                   footerType="sum"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
</sl:collection>

Table With first row
<sl:collection id="id" name="descriptors" model="list" view="test-list">
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30"/>
	<sl:collectionItem styleId="col11" title="title11" property="id" width="30" beforeData="qqqqqqq"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" footer="TEST"/>
</sl:collection>
<%--
Limited
<sl:collection id="id" name="descriptors" model="list" view="test-list" size="5">
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col11" title="title11" property="id" width="30" styleClass="ListLine1"
	                   beforeData="qqqqqqq"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" styleClass="ListLine1"
	                   footer="TEST"/>
</sl:collection>
--%>

Table with hidden column
<sl:collection id="id" name="descriptors" model="list" view="test-list">
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" beforeData="qqqqqqq1"/>
	<sl:collectionItem styleId="col11" title="title11" property="id" width="30" beforeData="qqqqqqq" hidden="true"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30"/>
	<sl:collectionItem styleId="col12" title="title12" property="name" width="30" footer="TEST"/>
</sl:collection>
</body>
</html>