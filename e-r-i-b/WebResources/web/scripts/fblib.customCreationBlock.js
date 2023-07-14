/**
 * Смотреть как используется на ExtendedLoanClaim
 */
var BlockObject = function ()
{
    var container = [];

    return {
        container : function ()
        {
            return container;
        },

        append : function (id, key, value)
        {
            if (id in container)
            {
                var containerArray  = container[id];
                containerArray[key] = value;
            }
            else
            {
                var newContainerArray  = [];
                newContainerArray[key] = value;

                container[id] = newContainerArray;
            }
        }
    }
};

/**
 * Смотреть как используется на ExtendedLoanClaim
 */
var Block = function (mock, block, remove, blocksCount) {

    var delimiter = '_';
    var mockId    = mock;
    var blockId   = block;
    var removeId  = remove;
    var removable = 'customSelect';
    var container = [];

    function handleClone(element, attrValue, postfix, isChild)
    {
        if (element.hasChildNodes())
        {
            var elemChildes = element.childNodes;
            var trash       = [];

            if (element.id && !isChild)
            {
                element.id = element.id + delimiter + postfix;
            }

            for (var i=0; i < elemChildes.length; i++)
            {
                var child = elemChildes[i];
                var name  = child.name;
                var id    = child.id;

                if (name && name.indexOf(attrValue) > -1)
                {
                    child.name = name + delimiter + postfix;
                }

                if (id && id.indexOf(attrValue) > -1)
                {
                    child.id = id + delimiter + postfix;
                }

                if (child.hasChildNodes())
                {
                    handleClone(child, attrValue, postfix, true);
                }

                if (id && id.indexOf(removable) > -1)
                {
                    trash.push(child);
                }

                if (child.tagName == 'SELECT')
                {
                    if (child.inited)
                    {
                        child.inited = false;
                    }
                }
            }

            trash.forEach( function(item)
            {
                element.removeChild(item);
            });
        }
    }

    function releaseSlot(index)
    {
        delete container[index-1];
    }

    function slot()
    {
        if (container.length == 0)
        {
            return 1;
        }

        var value = -1;
        for (var i=0; i<container.length; i++)
        {
            if(!(i in container))
            {
                value = i + 1;
                break;
            }
        }

        if (value == -1)
        {
            if(container.length < blocksCount)
            {
                return container.length + 1;
            }
        }

        return value;
    }

    return {
        create : function(properties)
        {
            var freeSlot = slot();
            if (freeSlot == -1)
            {
                return;
            }

            if (properties)
            {
                if (properties.hasOwnProperty('postfix'))
                {
                    freeSlot = properties.postfix;
                }
            }

            container[freeSlot - 1] = freeSlot;

            var block = document.getElementById(blockId);
            var blank = document.getElementById(mockId);
            var clone = blank.cloneNode(true);

            handleClone(clone, mockId, freeSlot, false);
            selectCore.init(clone);

            $(clone).appendTo($(block));

            $(clone).find("*[id^='" + removeId + "']").click(function() {
                $(clone).remove();

                var begin = this.id.indexOf(delimiter) + 1;
                var end   = this.id.length;

                releaseSlot( this.id.substring(begin, end) );
                return false;
            });
        },

        reestablish : function (blockObject)
        {
            if (!blockObject)
            {
                return;
            }

            var container = blockObject.container();
            for (var oKey in container)
            {
                if (container.hasOwnProperty(oKey))
                {
                    var empty = true;
                    var data  = container[oKey];

                    /*
                     * Если все значения пусты, ничего создавать не нужно
                     */
                    for (var iKey in data)
                    {
                        if (data.hasOwnProperty(iKey))
                        {
                            if (data[iKey] != null && data[iKey] != '' && !(typeof data[iKey] === 'function'))
                            {
                                empty = false;
                                break;
                            }
                        }
                    }
                    if (!empty)
                    {
                        var postfix = oKey.substring(oKey.indexOf(delimiter) + 1, oKey.length);
                        this.create({postfix : postfix});

                        var blank = document.getElementById(oKey);
                        if (blank)
                        {
                            for (var key in data)
                            {
                                if (data.hasOwnProperty(key))
                                {
                                    var found = document.getElementById(key);
                                    if (found)
                                    {
                                        switch (found.tagName)
                                        {
                                            case 'SELECT':
                                            {
                                                var option = $(found).find('option[value=' + data[key] + ']')[0];
                                                if (!option)
                                                {
                                                    continue;
                                                }
                                                else
                                                {
                                                    option.selected = true;
                                                    $(found).change();
                                                }

                                                break;
                                            }
                                            default :
                                            {
                                                $(found).val(data[key]);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
};
