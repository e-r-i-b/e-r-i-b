<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:importAttribute/>

<!--��������� ������� "������-������"-->
<tiles:insert definition="leftMenuInsetGroup" service="BusinessReports">
	<tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/TB' and submenu != 'BusinessReport/CountContract/OSB' and submenu != 'BusinessReport/CountContract/VSP' and submenu != 'BusinessReport/CountContract/OKR' and submenu != 'BusinessReport/CountUsers/TB' and submenu != 'BusinessReport/CountUsers/VSP' and submenu != 'BusinessReport/CountOperations/SBRF' and submenu != 'BusinessReport/CountOperations/TB' and submenu != 'BusinessReport/CountOperations/VSP' and submenu != 'BusinessReport'}"/>
	<tiles:put name="text"    value="������-������"/>
	<tiles:put name="title"   value="������-������"/>
    <tiles:put name="name"    value="business_report"/>
    <tiles:put name="data">
        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
            <tiles:put name="text"    value="���������� � ��������� ��������� ����"/>
            <tiles:put name="name"    value="count_contract"/>
            <tiles:put name="parentName" value="business_report"/>
            <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/TB' and submenu != 'BusinessReport/CountContract/OSB' and submenu != 'BusinessReport/CountContract/VSP' and submenu != 'BusinessReport/CountContract/OKR'}"/>
            <tiles:put name="data">
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/TB'}"/>
                    <tiles:put name="action" value="/reports/contract/tb.do"/>
                    <tiles:put name="text" value="���������� ��������� �� ��"/>
                    <tiles:put name="title" value="���������� ��������� �� ��"/>
                    <tiles:put name="parentName" value="count_contract"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/OSB'}"/>
                    <tiles:put name="action" value="/reports/contract/osb.do"/>
                    <tiles:put name="text" value="���������� ��������� �� ���/����"/>
                    <tiles:put name="title" value="���������� ��������� �� ���/����"/>
                    <tiles:put name="parentName" value="count_contract"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/VSP'}"/>
                    <tiles:put name="action" value="/reports/contract/vsp.do"/>
                    <tiles:put name="text" value="���������� ��������� �� ���"/>
                    <tiles:put name="title" value="���������� ��������� �� ���"/>
                    <tiles:put name="parentName" value="count_contract"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountContract/OKR'}"/>
                    <tiles:put name="action" value="/reports/contract/okr.do"/>
                    <tiles:put name="text" value="���������� ��������� �� ���"/>
                    <tiles:put name="title" value="���������� ��������� �� ���"/>
                    <tiles:put name="parentName" value="count_contract"/>
                </tiles:insert>
                
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
            <tiles:put name="text"    value="�������� ������������ ����"/>
            <tiles:put name="name"    value="count_users"/>
            <tiles:put name="parentName" value="business_report"/>
            <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountUsers/TB' and submenu != 'BusinessReport/CountUsers/VSP'
                                                                                         and submenu != 'BusinessReport/Count/IOS'}"/>
            <tiles:put name="data">
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountUsers/TB'}"/>
                    <tiles:put name="action" value="/reports/active_users/tb.do"/>
                    <tiles:put name="text" value="���������� �������� ������������� �� ��"/>
                    <tiles:put name="title" value="���������� �������� ������������� �� ��"/>
                    <tiles:put name="parentName" value="count_users"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountUsers/VSP'}"/>
                    <tiles:put name="action" value="/reports/active_users/vsp.do"/>
                    <tiles:put name="text" value="���������� �������� ������������� �� ���"/>
                    <tiles:put name="title" value="���������� �������� ������������� �� ���"/>
                    <tiles:put name="parentName" value="count_users"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/Count/IOS'}"/>
                    <tiles:put name="action" value="/reports/count/ios/report.do"/>
                    <tiles:put name="text" value="����� � ���������� �������� iOS �� ����"/>
                    <tiles:put name="title" value="����� � ���������� �������� iOS �� ����"/>
                    <tiles:put name="parentName" value="count_users"/>
                </tiles:insert>
            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="leftMenuSubInsetGroup" flush="false">
            <tiles:put name="text"    value="�������� � ���� �� ������"/>
            <tiles:put name="name"    value="count_operations"/>
            <tiles:put name="parentName" value="business_report"/>
            <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountOperations/SBRF' and submenu != 'BusinessReport/CountOperations/TB' and submenu != 'BusinessReport/CountOperations/VSP'}"/>
            <tiles:put name="data">
                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountOperations/SBRF'}"/>
                    <tiles:put name="action" value="/reports/operations/sbrf.do"/>
                    <tiles:put name="text" value="���������� �������� �� ���������"/>
                    <tiles:put name="title" value="���������� �������� �� ���������"/>
                    <tiles:put name="parentName" value="count_operations"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountOperations/TB'}"/>
                    <tiles:put name="action" value="/reports/operations/tb.do"/>
                    <tiles:put name="text" value="���������� �������� �� ��"/>
                    <tiles:put name="title" value="���������� �������� �� ��"/>
                    <tiles:put name="parentName" value="count_operations"/>
                </tiles:insert>

                <tiles:insert definition="leftMenuInset" flush="false">
                    <tiles:put name="enabled" value="${submenu != 'BusinessReport/CountOperations/VSP'}"/>
                    <tiles:put name="action" value="/reports/operations/vsp.do"/>
                    <tiles:put name="text" value="���������� �������� �� ���"/>
                    <tiles:put name="title" value="���������� �������� �� ���"/>
                    <tiles:put name="parentName" value="count_operations"/>
                </tiles:insert>

            </tiles:put>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'BusinessReport'}"/>
	        <tiles:put name="action"  value="/reports/list.do"/>
	        <tiles:put name="text"    value="������ ������-�������"/>
	        <tiles:put name="title"   value="������ ������-�������"/>
            <tiles:put name="parentName" value="business_report"/>
        </tiles:insert>

    </tiles:put>
</tiles:insert>

<!--��������� ������� "IT-������"-->
<tiles:insert definition="leftMenuInsetGroup" service="ITReports">
	<tiles:put name="enabled" value="${submenu !=  'ITReport' and submenu !=  'ITReport/BusinessParamsItReport' and submenu != 'ITReport/QualityPeriodItReport' and submenu != 'ITReport/QualityDateItReport' and submenu != 'ITReport/SystemIdleItReport' and submenu != 'ITReport/ProactiveReport' }"/>
	<tiles:put name="text"    value="IT-������"/>
	<tiles:put name="title"   value="IT-������"/>
    <tiles:put name="name"    value="it_report"/>
    <tiles:put name="data">
       <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport/BusinessParamsItReport'}"/>
	        <tiles:put name="action"  value="/reports/it/business_params.do"/>
	        <tiles:put name="text"    value="����� �� ���������������� �� ������"/>
	        <tiles:put name="title"   value="����� �� ���������������� �� ������"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

         <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport/QualityPeriodItReport'}"/>
	        <tiles:put name="action"  value="/reports/it/quality_period.do"/>
	        <tiles:put name="text"    value="����� � �������� ���������� �������� �� ������"/>
	        <tiles:put name="title"   value="����� � �������� ���������� �������� �� ������"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

         <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport/QualityDateItReport'}"/>
	        <tiles:put name="action"  value="/reports/it/quality_date.do"/>
	        <tiles:put name="text"    value="����� � �������� ���������� �������� �� ���� (����������)"/>
	        <tiles:put name="title"   value="����� � �������� ���������� �������� �� ���� (����������)"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

         <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport/ProactiveReport'}"/>
	        <tiles:put name="action"  value="/reports/it/proactive.do"/>
	        <tiles:put name="text"    value="����� �� ������������ �����������"/>
	        <tiles:put name="title"   value="����� �� ������������ �����������"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport/SystemIdleItReport'}"/>
	        <tiles:put name="action"  value="/reports/it/system_idle.do"/>
	        <tiles:put name="text"    value="����� � ������� ����������� �������"/>
	        <tiles:put name="title"   value="����� � ������� ����������� �������"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

         <tiles:insert definition="leftMenuInset" flush="false">
	        <tiles:put name="enabled" value="${submenu != 'ITReport'}"/>
	        <tiles:put name="action"  value="/reports/it/list.do"/>
	        <tiles:put name="text"    value="������ IT-�������"/>
	        <tiles:put name="title"   value="������ IT-�������"/>
            <tiles:put name="parentName" value="it_report"/>
        </tiles:insert>

    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInset" flush="false" service="Monitoring">
    <tiles:put name="enabled" value="${submenu != 'Monitoring'}"/>
    <tiles:put name="action"  value="/monitoring/view.do"/>
    <tiles:put name="text"    value="����������"/>
    <tiles:put name="title"   value="����������"/>
</tiles:insert>