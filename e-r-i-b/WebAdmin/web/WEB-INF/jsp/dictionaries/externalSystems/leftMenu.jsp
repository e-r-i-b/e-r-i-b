<%--
  User: malafeevsky
  Date: 01.09.2009
  Time: 14:32:33
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>
<tiles:insert definition="leftMenuInset" operation="ListNodesOperation" service="NodesManager">
    <tiles:put name="enabled" value="${submenu != 'Node'}"/>
    <tiles:put name="action" value="/dictionaries/routing/node/list.do"/>
    <tiles:put name="text"   value="����"/>
    <tiles:put name="title"  value="����"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListAdaptersOperation" service="AdaptersManager">
    <tiles:put name="enabled" value="${submenu != 'Adapter'}"/>
    <tiles:put name="action" value="/dictionaries/routing/adapter/list.do"/>
    <tiles:put name="text"   value="��������"/>
    <tiles:put name="title"  value="��������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" operation="ListBillingsOperation" service="BillingsManager">
    <tiles:put name="enabled" value="${submenu != 'Billing'}"/>
    <tiles:put name="action" value="/dictionaries/billing/list.do"/>
    <tiles:put name="text"   value="����������� �������"/>
    <tiles:put name="title"  value="����������� �������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInset" service="ExternalSystemSettingsManagement">
    <tiles:put name="enabled" value="${submenu != 'Configure'}"/>
    <tiles:put name="action" value="/adapters/configure.do"/>
    <tiles:put name="text"   value="���������"/>
    <tiles:put name="title"  value="���������"/>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" service="GateServicesRestrictionManagment">
    <tiles:put name="enabled" value="${submenu != 'ListGateServicesRestriction' && submenu != 'EditGateServiceRestriction'
                                            && submenu != 'RunGateServiceRestriction' && submenu != 'SeviceIQWaveConfigure'}"/>
    <tiles:put name="name"    value="ServiceIQWave"/>
    <tiles:put name="text"   value="������� IQWave"/>
    <tiles:put name="title"  value="������� IQWave"/>
    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'ListGateServicesRestriction' && submenu != 'EditGateServiceRestriction' && submenu != 'RunGateServiceRestriction'}"/>
            <tiles:put name="action"        value="/configure/gate/services/list.do"/>
            <tiles:put name="text"          value="�����������"/>
            <tiles:put name="title"         value="�����������"/>
            <tiles:put name="parentName"    value="ServiceIQWave"/>
        </tiles:insert>
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'SeviceIQWaveConfigure'}"/>
            <tiles:put name="action"        value="/gate/services/configure.do"/>
            <tiles:put name="text"          value="���������"/>
            <tiles:put name="title"         value="���������"/>
            <tiles:put name="parentName"    value="ServiceIQWave"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
