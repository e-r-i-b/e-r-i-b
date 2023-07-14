<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>

<tiles:insert definition="leftMenuInset" service="ErmbMigrationSettingsService">
    <tiles:put name="enabled" value="${submenu != 'MigrationSettings'}"/>
    <tiles:put name="action"  value="/ermb/migration/settings.do"/>
    <tiles:put name="text">
        <bean:message bundle="migrationBundle" key="left.menu.label.settings"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message bundle="migrationBundle" key="left.menu.label.settings"/>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" service="ErmbMigrationService">
    <tiles:put name="enabled" value="${submenu != 'Load' && submenu != 'Sms' && submenu != 'Migration' && submenu != 'Rollback'}"/>
    <tiles:put name="text">
        <bean:message bundle="migrationBundle" key="left.menu.group.procedure"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message bundle="migrationBundle" key="left.menu.group.procedure"/>
    </tiles:put>
    <tiles:put name="name"    value="actions"/>

    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'Load'}"/>
            <tiles:put name="action"        value="/ermb/migration/loading.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.load"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.load"/>
            </tiles:put>
            <tiles:put name="parentName"    value="actions"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'Sms'}"/>
            <tiles:put name="action"        value="/ermb/migration/sms.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.sms"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.sms"/>
            </tiles:put>
            <tiles:put name="parentName"    value="actions"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'Migration'}"/>
            <tiles:put name="action"        value="/ermb/migration/migration.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.migration"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.migration"/>
            </tiles:put>
            <tiles:put name="parentName"    value="actions"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'Rollback'}"/>
            <tiles:put name="action"        value="/ermb/migration/rollback.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.rollback"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.rollback"/>
            </tiles:put>
            <tiles:put name="parentName"    value="actions"/>
        </tiles:insert>
    </tiles:put>

</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" service="ErmbMigrationService">
    <tiles:put name="enabled" value="${submenu != 'ManagerReport' && submenu != 'CallCenterReport'}"/>
    <tiles:put name="text">
        <bean:message bundle="migrationBundle" key="left.menu.group.reports"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message bundle="migrationBundle" key="left.menu.group.reports"/>
    </tiles:put>
    <tiles:put name="name"    value="reports"/>

    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'ManagerReport'}"/>
            <tiles:put name="action"        value="/ermb/migration/clientManager.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.report.manager"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.report.manager"/>
            </tiles:put>
            <tiles:put name="parentName"    value="reports"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'CallCenterReport'}"/>
            <tiles:put name="action"        value="/ermb/migration/callCentre.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.report.cc"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.report.cc"/>
            </tiles:put>
            <tiles:put name="parentName"    value="reports"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="leftMenuInsetGroup" service="ErmbMigrationService">
    <tiles:put name="enabled" value="${submenu != 'VipClients' && submenu != 'Clients'}"/>
    <tiles:put name="text">
        <bean:message bundle="migrationBundle" key="left.menu.group.conflict"/>
    </tiles:put>
    <tiles:put name="title">
        <bean:message bundle="migrationBundle" key="left.menu.group.conflict"/>
    </tiles:put>
    <tiles:put name="name"    value="conflicts"/>

    <tiles:put name="data">
        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'VipClients'}"/>
            <tiles:put name="action"        value="/ermb/migration/vip.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.conflict.vip"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.conflict.vip"/>
            </tiles:put>
            <tiles:put name="parentName"    value="conflicts"/>
        </tiles:insert>

        <tiles:insert definition="leftMenuInset" flush="false">
            <tiles:put name="enabled"       value="${submenu != 'Clients'}"/>
            <tiles:put name="action"        value="/ermb/migration/list.do"/>
            <tiles:put name="text">
                <bean:message bundle="migrationBundle" key="left.menu.label.conflict"/>
            </tiles:put>
            <tiles:put name="title">
                <bean:message bundle="migrationBundle" key="left.menu.label.conflict"/>
            </tiles:put>
            <tiles:put name="parentName"    value="conflicts"/>
        </tiles:insert>
    </tiles:put>
</tiles:insert>
