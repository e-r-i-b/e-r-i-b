<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>

<script type="text/javascript">
    var view = 0;
    var edit = 1;

    function GroupInformationBuilder()
    {
        var groups = [];
        var services = [];
        var topLevelGroups = [];
        var hasDuplicate = false;

        function each(collection, callback)
        {
            var itemKey;
            for (itemKey in collection)
            {
                if (collection.hasOwnProperty(itemKey))
                {
                    var item = collection[itemKey];
                    if (callback.call(item, itemKey, item) === false)
                        break;
                }
            }
        }

        var serviceComparator = function(a,b)
        {
            if (a == b)
            {
                hasDuplicate = true;
                return 0;
            }

            return a > b? 1: -1;
        };

        function unique(collection, comparator)
        {
            hasDuplicate = false;
            collection.sort(comparator);
            if (!hasDuplicate)
                return collection;

            for (var i = 1; i < collection.length; i++)
            {
                if (collection[i] === collection[i-1])
                    collection.splice(i--, 1);
            }
            return collection;
        }

        function concatenationCollections(first, second)
        {
            each(second, function(i,e){first.push(e);});
            return first;
        }

        function updateAllServicesInformation()
        {
            each(topLevelGroups, function(i, id){
                updateServicesInformation(groups[id]);
            });
        }

        function updateServicesInformation(group)
        {
            each(group.children, function(i, e){
                updateServicesInformation(e);
            });

            if (group.children.length > 0)
                return;

            each(group.services[view], function(i, e)
            {
                addServiceInformation(e, group.services[view]);
            });
            each(group.services[edit], function(i, e)
            {
                addServiceInformation(e, group.services[edit]);
            });
        }

        function addTopLevelGroup(group)
        {
            topLevelGroups.push(group.id);
            return group;
        }

        function addServiceInformation(serviceKey, servicesList)
        {
            var service = services[serviceKey];
            if (service == undefined)
                service = new Array();

            services[serviceKey] = unique(concatenationCollections(service, servicesList), serviceComparator);
        }

        function setGroup(group)
        {
            groups[group.id] = group;
        }

        function getGroup(id)
        {
            return groups[id];
        }

        this.addGroup = function(id, key, name, parentId)
        {
            var services = [];
            services[view] = new Array();
            services[edit] = new Array();
            var group = {id: id, key: key, name: name, parentId: parentId, services: services, children: []};

            setGroup(group);

            if (parentId == null)
                return addTopLevelGroup(group);

            var parentGroup = getGroup(parentId);
            parentGroup.children.push(group);
            return group;
        };

        this.addService = function(id, key, mode)
        {
            var group = getGroup(id);
            group.services[mode].push(key);
        };

        this.build = function()
        {
            updateAllServicesInformation();
        };

        this.getServices = function()
        {
            return services;
        };

        this.getGroups = function()
        {
            return groups;
        };

        this.getTopLevelGroups = function()
        {
            return topLevelGroups;
        }
    }

    function SelectGroupManager(groupData)
    {
        var topLevelGroupList = groupData.topLevelGroups;
        var groupsMap = groupData.groups;
        var servicesMap = groupData.services;
        var categoryMap = [];
        categoryMap['E'] = 'employee';
        categoryMap['A'] = 'admin';
        var invertCategoryMap = [];
        invertCategoryMap['E'] = 'admin';
        invertCategoryMap['A'] = 'employee';

        var disable      = "disabledCheckbox";
        var clear        = "clearedCheckbox";
        var partlySelect = "partlySelectedCheckbox";
        var select       = "selectedCheckbox";

        function getCurrentCategory()
        {
            return categoryMap[$('#categorySelector').val()];
        }

        function getGroupInformation(id)
        {
            return groupsMap[id];
        }

        function contains(value, collection)
        {
            return $.inArray(value, collection) >= 0;
        }

        function selectService(key, activate, processed)
        {
            var mode = activate? select: clear;
            var changed = false;
            var services = $.grep(servicesMap[key].slice(), function(a) {
                return !contains(a, processed);
            });

            $.each(services, function(i, e)
            {
                changed = changed || changeCheckbox(e, mode);
            });
            return changed;
        }

        function changeCheckbox(id, mode)
        {
            var checkbox = $('#' + id);
            var previewMode = checkbox.attr('class');
            checkbox.attr('class', mode);
            if (mode == select || mode == partlySelect)
                checkbox.attr('checked', 'checked');
            else if (mode == clear || mode == disable)
                checkbox.removeAttr('checked');

            if (mode == disable)
                checkbox.attr('disabled', 'disabled');
            else
                checkbox.removeAttr('disabled');

            return previewMode == mode;
        }

        function containsAll(first, second)
        {
            var containsAll = true;
            $.each(second, function(i, e){
                if (first.indexOf(e) == -1)
                {
                    containsAll = false;
                    return false;
                }
                return true;
            });
            return containsAll;
        }

        function containsAny(first, second)
        {
            var containsAny = false;
            $.each(second, function(i, e){
                if (first.indexOf(e) != -1)
                {
                    containsAny = true;
                    return false;
                }
                return true;
            });
            return containsAny;
        }

        function getProcessedServices(activate)
        {
            var processedServices = [];
            $('[name=selectedServices]' + (activate? '': ':not(') + ':checked' + (activate? '': ')')).each(function(i, e)
            {
                processedServices.push($(e).attr('id'));
            });
            return processedServices;
        }

        function selectGroupServices(group, mode, activate, processedServices)
        {
            var changed = false;
            if (group.children.length > 0)
            {
                $.each(group.children, function(i, e)
                {
                    changed = changed || selectGroupServices(e, mode, activate, processedServices);
                });
            }
            else
            {
                var realMode = mode;
                if (mode == edit && group.services[mode].length == 0)
                    realMode = view;

                if (group.services[realMode].length == 0)
                    return;
                $.each(group.services[realMode], function(i, e)
                {
                    changed = changed || selectService(e, activate, processedServices);
                });
            }
            return changed;
        }

        function getServicesSubgroupMode(group, mode, selectedServices)
        {
            if (group.services[mode].length == 0)
                return disable;
            if (containsAll(selectedServices, group.services[mode]))
                return select;
            if (containsAny(selectedServices, group.services[mode]))
                return partlySelect;

            return clear;

        }

        function updateGroupSelection(group, selectedServices)
        {
            var modes = [];

            if (group.children.length > 0)
            {
                var allViewSelected = true;
                var allEditSelected = true;

                var viewMode = null;
                var editMode = null;
                var disabledCount = 0;

                $.each(group.children, function(index, group){
                    var childMode = updateGroupSelection(group, selectedServices);

                    var childViewMode = childMode[view];
                    if (viewMode == null || viewMode == disable)
                        viewMode = childViewMode;
                    else if (childViewMode == partlySelect || childViewMode == select && viewMode != select || childViewMode == clear && viewMode == select)
                        viewMode = partlySelect;

                    var childEditMode = childMode[edit];
                    if (childEditMode == disable)
                        disabledCount++;
                    if (childEditMode == disable)
                        childEditMode = childViewMode;

                    if (editMode == null || editMode == disable)
                        editMode = childEditMode;
                    else if (childEditMode == partlySelect || childEditMode == select && editMode != select || childEditMode == clear && editMode == select)
                        editMode = partlySelect;
                });

                modes[view] = viewMode;
                modes[edit] = group.children.length == disabledCount? disable: editMode;
            }
            else
            {
                modes[view] = getServicesSubgroupMode(group, view, selectedServices);
                modes[edit] = getServicesSubgroupMode(group, edit, selectedServices);
            }

            changeCheckbox('group_view_' + group.id, modes[view]);
            changeCheckbox('group_edit_' + group.id, modes[edit]);

            return modes;
        }

        function updateAllGroupsSelection()
        {
            var selectedServices = [];

            $('[name=selectedServices]:checked').each(function(i, e)
            {
                selectedServices.push(e.id);
            });

            $.each(topLevelGroupList, function(index, groupId)
            {
                updateGroupSelection(getGroupInformation(groupId), selectedServices);
            });
        }

        this.selectGroup = function(id, mode, activate)
        {
            <c:if test="${not readOnly}">
                var changed = true;
                while (changed)
                {
                    var processedServices = getProcessedServices(activate);
                    var group = getGroupInformation(id);
                    changed = selectGroupServices(getGroupInformation(id), mode, activate, processedServices);
                }
                updateAllGroupsSelection();
            </c:if>
            return ${not readOnly};
        };

        this.selectServices = function(keys)
        {
            $('[name=selectedServices]').each(function(i, e)
            {
                var serviceKey = $(e).attr('id');
                changeCheckbox(serviceKey, contains(serviceKey, keys)? select: clear);
            });
            updateAllGroupsSelection();
        };

        this.changeCategory = function(category)
        {
            var oldCategory = invertCategoryMap[category];
            var newCategory = categoryMap[category];

            $('.category_' + oldCategory).hide();
            $('.category_' + newCategory).show();

            $('[name=selectedServices]').removeAttr('checked');
            $('[type=checkbox][id^=group_]').removeAttr('checked');
            $('.editControl').removeAttr('checked');

            updateAllGroupsSelection();

            hideOrShowManager.hideOrShowAllGroupData();

            return false;
        };

        this.getCurrentCategory = function()
        {
            return getCurrentCategory();
        };

        this.updateGroupSelection = function()
        {
            return updateAllGroupsSelection();
        }
    }

    function HideOrShowManager(groupData)
    {
        var groupsMap          = groupData.groups;
        var topLevelGroupList  = groupData.topLevelGroups;

        function getGroupInformation(id)
        {
            return groupsMap[id];
        }

        function getCurrentCategory()
        {
            return selectGroupManager.getCurrentCategory();
        }

        function changeVisibilityGroupData(id, hide, openAll)
        {
            var children = getGroupInformation(id).children;
            if (children.length <= 0)
                return;
            var groupRow = $("#group_" + id + '.category_' + getCurrentCategory());
            var groupRowActivityIdentifier = groupRow.find(".groupViewControl");
            groupRowActivityIdentifier.addClass(hide? 'not-active': 'active');
            groupRowActivityIdentifier.removeClass(hide? 'active': 'not-active');
            $.each(children, function(i, e){hideOrShowGroupChildren(e.id, hide, openAll);});
        }

        function hideOrShowGroupChildren(id, hide, openAll)
        {
            var group = getGroupInformation(id);
            var groupRow = $("#group_" + id + '.category_' + getCurrentCategory());
            var groupRowActivityIdentifier = groupRow.find(".groupViewControl");
            if (hide)
            {
                groupRow.hide();
                groupRow.parents(".actions").parent().hide();
                if (group.children.length <= 0)
                    return;

                $.each(group.children, function(i, e){hideOrShowGroupChildren(e.id, hide, openAll);});
                groupRowActivityIdentifier.addClass('not-active');
                groupRowActivityIdentifier.removeClass('active');
            }
            else
            {
                groupRow.show();
                groupRow.parents(".actions").parent().show();
                if (openAll && group.children.length > 0)
                {
                    $.each(group.children, function(i, e){hideOrShowGroupChildren(e.id, hide, openAll);});
                    groupRowActivityIdentifier.addClass('active');
                    groupRowActivityIdentifier.removeClass('not-active');
                }
            }
        }

        this.hideOrShowAllGroupData = function()
        {
            var hide = $('.group.level_1' + '.category_' + getCurrentCategory()).is(':visible');
            $.each(topLevelGroupList, function(i, e){changeVisibilityGroupData(e, hide, true);});
            return false;
        };

        this.hideOrShowGroupData = function(id)
        {
            if ($("#group_" + id).find(".groupViewControl").is('.active'))
                changeVisibilityGroupData(id, true, false);
            else
                changeVisibilityGroupData(id, false, false);

            return false;
        };
    }

    var groupInformationBuilder = new GroupInformationBuilder();

    <c:forEach var="group" items="${form.groups}">
        groupInformationBuilder.addGroup(${group.id}, '${group.key}', '${group.name}', ${empty group.parentId? 'null': group.parentId});
        <c:forEach var="service" items="${group.services}">
            groupInformationBuilder.addService(${group.id}, '${service.key}', ${service.mode});
        </c:forEach>
    </c:forEach>

    groupInformationBuilder.build();

    var hideOrShowManager = new HideOrShowManager({groups: groupInformationBuilder.getGroups(), topLevelGroups: groupInformationBuilder.getTopLevelGroups()});
    var selectGroupManager = new SelectGroupManager({groups: groupInformationBuilder.getGroups(), topLevelGroups: groupInformationBuilder.getTopLevelGroups(), services: groupInformationBuilder.getServices()});

    doOnLoad(function(){
        selectGroupManager.updateGroupSelection();
    });
</script>

<table id="servicesGroups">
    <tr class="title">
        <td class="name">
            <bean:message key="scheme.access.form.edit.table.title.name" bundle="schemesBundle"/>
            <div class="allGroupViewControl" onclick="return hideOrShowManager.hideOrShowAllGroupData();">&nbsp;</div>
        </td>
        <td class="view"><bean:message key="scheme.access.form.edit.table.title.view" bundle="schemesBundle"/></td>
        <td class="edit"><bean:message key="scheme.access.form.edit.table.title.edit" bundle="schemesBundle"/></td>
    </tr>

    <c:set var="prevIsAction" value="${false}"/>
    <c:set var="prevLevel" value="${0}"/>

    <c:forEach var="currentGroup" items="${form.groups}">
        <c:set var="isAction" value="${currentGroup.action}"/>
        <c:set var="currentLevel" value="${currentGroup.level}" scope="page"/>
        <c:set var="currentPadding" value="14" scope="page"/>
        <c:set var="currentNameStyle" value="14" scope="page"/>
        <c:if test="${currentLevel > 0}">
            <c:set var="currentNameStyle" value="30" scope="page"/>
            <c:set var="currentPadding" value="${(currentLevel - 1) * 35 + 14}" scope="page"/>
        </c:if>
        <c:set var="displayMarker" value=""/>
        <c:set var="currentGroupCategory">${currentGroup.category}</c:set>
        <c:if test="${currentLevel > 0 or currentSchemeCategory eq 'E' and currentGroupCategory ne 'employee' or currentSchemeCategory eq 'A' and currentGroupCategory ne 'admin'}">
            <c:set var="displayMarker" value=" style='display:none;'"/>
        </c:if>

        <c:if test="${isAction}">
            <c:set var="currentPadding" value="0" scope="page"/>
            <c:set var="currentNameStyle" value="30" scope="page"/>
        </c:if>

        <c:if test="${prevIsAction and (not isAction or currentLevel ne prevLevel)}">
                            <tr class="action lastRow">
                                <td colspan="3"></td>
                            </tr>
                        </table>
                    </div>
                </td>
            </tr>
        </c:if>

        <c:if test="${isAction and (not prevIsAction or currentLevel ne prevLevel)}">
            <tr class="group category_${currentGroup.category}"${displayMarker}>
                <td colspan="3" class="actions" style="padding-left: ${(currentLevel - 1) * 35 + 15}px;">
                    <div class="actionsContainerDiv">
                        <table class="actionsContainer">
                            <tr class="title">
                                <td class="name">
                                    <div class="groupViewControl">&nbsp;</div>
                                    <bean:message key="scheme.access.form.edit.table.title.actions" bundle="schemesBundle"/>
                                </td>
                                <td class="view">&nbsp;</td>
                                <td class="edit">&nbsp;</td>
                            </tr>
        </c:if>

        <tr id="group_${currentGroup.id}" class="group level_${currentLevel} category_${currentGroup.category}"${displayMarker}>
            <c:set var="currentSubgroups" value="${currentGroup.subgroups}" scope="page"/>
            <c:set var="currentActions" value="${currentGroup.actions}" scope="page"/>

            <td class="groupName" style="padding-left: ${currentPadding}px;">
                <div class="groupNameContainer" style="padding-left: ${currentNameStyle}px;">
                    <c:set var="groupName" value="${currentGroup.name}" scope="page"/>
                    <c:set var="additionalClass" value="" scope="page"/>
                    <c:set var="onclickParameter" value="" scope="page"/>
                    <c:if test="${not empty currentSubgroups or not empty currentActions}">
                        <c:set var="groupName" value="<a href='#' onclick='return hideOrShowManager.hideOrShowGroupData(${currentGroup.id});'>${groupName}</a>" scope="page"/>
                        <c:set var="additionalClass" value=" not-active" scope="page"/>
                        <c:set var="onclickParameter" value=" onclick='return hideOrShowManager.hideOrShowGroupData(${currentGroup.id});'" scope="page"/>
                    </c:if>
                    <div class="groupViewControl${additionalClass}"${onclickParameter}>&nbsp;</div>
                        ${groupName}
                </div>
            </td>
            <td class="viewControl">
                <c:choose>
                    <c:when test="${not isAction}">
                        <input type="checkbox" id="group_view_${currentGroup.id}" onclick="return selectGroupManager.selectGroup(${currentGroup.id}, view, $(this).is(':checked'));"/>
                    </c:when>
                    <c:otherwise>&nbsp;</c:otherwise>
                </c:choose>
            </td>
            <td class="editControl"><input type="checkbox" id="group_edit_${currentGroup.id}" onclick="return selectGroupManager.selectGroup(${currentGroup.id}, edit, $(this).is(':checked'));"/></td>
        </tr>
        <c:set var="prevIsAction" value="${isAction}"/>
        <c:set var="prevLevel" value="${currentLevel}"/>
    </c:forEach>

    <c:if test="${prevIsAction}">
                        <tr class="action lastRow">
                            <td colspan="3"></td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </c:if>
    <tr class="group lastRow">
        <td colspan="3"></td>
    </tr>
</table>

<c:forEach var="helper" items="${helpers}">
    <div class="clear"></div>
    <c:forEach var="service" items="${helper.services}">
        <html:multibox styleId="${service.key}" property="selectedServices" style="display: none;">
            <bean:write name="service" property="id"/>
        </html:multibox>
    </c:forEach>
</c:forEach>
