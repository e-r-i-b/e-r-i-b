$.fx.prototype.cur = function()
{
    if ( this.elem[this.prop] != null && (!this.elem.style || this.elem.style[this.prop] == null) )
    {
        return this.elem[ this.prop ];
    }

    var r = parseFloat( jQuery.css( this.elem, this.prop ) );
    return typeof r == 'undefined' ? 0 : r;
};

/**
 * ������ ������� ���� �������� � ���� ������ � ������ ��� ���������� ������ (LiveSearch).
 * @constructor
 */
var LiveSearchData = function()
{
    var data = [];

    return {

        /**
         * ���������� ��� ������. �������� ������������� �� �� ������� (1,2,3..n) � �� ���������� ����� � ������� ������
         * ������� ��� ��������
         *
         * @returns {Array}
         */
        getAll : function()
        {
            return data;
        },

        /**
         * ���� �� ����� ������� ������
         *
         * @param key ���� �������� ��������
         * @returns {*} � ������ ���� ������� �� ������ ���������� null
         */
        findDataByKey : function (key)
        {
            if (data.hasOwnProperty(key))
            {
                return data[key];
            }

            return null;
        },

        /**
         * ������ ��� ���������� ������ �������� ������
         *
         * @param key ���� ��������
         * @param text �����
         * @param selected �������� �� ���� ������� ��������� ��-���������
         */
        appendData : function(key, text, selected)
        {
            data[key] = {key: key, text: text, selected: (selected ? true : false)};
        }
    }
};

var LiveSearch = function()
{
    var hasSearchResult = false;

    var searchInput;
    var liveSearchValueInput;

    var dropDownContainer;
    var dropDownContainerList;

    var searchResultContainer;
    var searchResultContainerList;

    var searchSelectList;
    var liveSearchComponent;
    var searchSelectTopText;
    var searchSelect;
    var data;

    var onSelectCallback = function(selected) {};

    /**
     * �������� ��� �������� ���������.
     * @param element
     */
    function enableHighlight(element)
    {
        if (element)
        {
            $(element).mouseenter(function ()
            {
                $(this).addClass('needLightHover');

            }).mouseout(function()
            {
                $(this).removeClass('needLightHover');
            });
        }
    }

    /**
     * �������� ��� ���������� div`a ������������� ���������.
     * ���������� �������� ������ ��� ��� div ������������, ���� �� ������� ����������.
     *
     * @param element
     */
    function enableScrolls(element)
    {
        if (element)
        {
            element.mCustomScrollbar("vertical", 0, "easeOutCirc", 1.05, "auto", "yes", "no", 0);
        }
    }

    /**
     * ���� ����� ������, ��������, � ������� �������� text ��������� ��� ������� � ���������� text, ��� ��������.
     *
     * @param text ������ ������� ���������� ������
     * @returns {Array} ������ ��������� ������
     */
    function search(text)
    {
        var found   = [];
        var isEmpty = true;

        if (!text)
        {
            found['empty'] = isEmpty;
            return found;
        }

        /*
         * �������� �� ������ ������ + ����������� �� �������� ����� ����
         */
        var words = text.split(/\s+/).sort(function (current, next)
        {
            if (!current)
            {
                return 1;
            }

            if (!next)
            {
                return -1;
            }

            return current.length > next.length ? -1 : 1;
        });

        var test    = String();
        var replace = String();
        var group   = 1;

        for (var i=0; i<words.length; i++)
        {
            if (words[i])
            {
                if (i==0)
                {
                    test    += ('(' + words[i] + ')');
                    replace += ('<b>$' + group++);
                }
                else
                {
                    test    += ('|(' + words[i] + ')');
                    replace += ('$'  + group++);
                }

                if (i == (words.length - 1))
                {
                    replace += '</b>';
                }
            }
            else
            {
                replace += '</b>';
            }
        }

        if (test.length > 0)
        {
            var all    = data.getAll();
            var regexp = new RegExp(test, 'gi');

            for (var key in all)
            {
                if (all.hasOwnProperty(key))
                {
                    var element = all[key];
                    var match   = regexp.exec(element.text);

                    if (match)
                    {
                        isEmpty = false;

                        found[key] = {
                            key:      element.key,
                            text:     element.text.replace(regexp, replace),
                            selected: element.selected
                        };
                    }
                }
            }
        }

        /*
         * � ������������� ������� ����� ����� length �� ��������, ���������� ���
         */
        found['empty'] = isEmpty;
        return found;
    }

    /**
     * ������������ � ������ ��������� ���������.
     * ��������! ���������� ����������� � ���, ����� ��������� ��������� �������� � ��� <b>, ����������, ����� � �����������
     * ����������� � �������, �������, ���� ������ ������������ ����������, �� ������� ������ ��� ����������.
     *
     * @param text ����� � ������� ���������� �������� ���������
     * @param substring ���������� ���������
     *
     * @returns {string}
     */
    function highlight(text, substring)
    {
        if (!substring)
        {
            return text;
        }

        var begin = text.indexOf(substring);
        var   end = begin + substring.length;
        var  bold = $(document.createElement('b')).text( text.substring(begin, end) )[0];

        return ''.concat(text.substring(0,   begin))
                 .concat(bold.outerHTML)
                 .concat(text.substring(end, text.length));
    }

    /**
     * ����� ��������� ����������:
     *
     * 1. ����� ������������ ������ ��������� ������ ������� ���������� ��������� ������;
     * 2. ��� ������� �� ������ ������� ����� ������� ������ ������.
     *
     * @param clearSearch �������� ��������� �� ��� ���������� �������� ����� ���� ��� ����� �������.
     */
    function clear(clearSearch)
    {
        hasSearchResult = false;

        if (clearSearch)
        {
            searchInput.val('�����');
            searchInput.addClass("inputPlaceholder");
        }

        /*
         * ������ ���� ����������� � ������� ��������� ������
         */
        searchResultContainer.css('display', 'none');
        searchResultContainerList.children().remove();
    }

    /**
     * ������������ ��� ������ ������-���� �������� � ������
     */
    function listElementClick(id, parent)
    {
        var selected = data.findDataByKey(id);
        if (selected)
        {
            if (parent)
            {
                parent.css('display', 'none');
            }

            liveSearchValueInput.val(selected.key);
            searchSelectTopText.text(selected.text);

            onSelectCallback(selected);
            clear(true);
        }
    }

    /**
     * ������������ ��� ������� �������.
     * �������� ��������� ������������� ������ � �������� ��� ������, � ������ ��������� ����������� ��������� ����
     * �����������.
     *
     * @param value ��������� ������������� ������
     */
    function searchInputOnKeyUp(value)
    {
        clear(false);

        var found = search( value );
        if (found['empty'] == true)
        {
            hasSearchResult = false;
            return;
        }
        else
        {
            hasSearchResult = true;
        }

        for (var key in found)
        {
            if (found.hasOwnProperty(key))
            {
                if (key == 'empty')
                {
                    continue;
                }

                var element = createResultElement(key, found[key].text);

                $(element).appendTo(searchResultContainerList);
                $(element).click(function()
                {
                    listElementClick(this.id, searchSelectList);
                });

                enableHighlight(element);
            }
        }

        /*
         * ���������� ���� � ������������ ���� ���-������ �������
         */
        searchResultContainer.css('display', '');
        enableScrolls(searchResultContainer);
    }

    /**
     * ��� ������ ������ ����������� ��������� ������, ���� ���� ����������� ��� - �� ������ ������ ���������, � ���������
     * ������ ������� ������ ������ �� ������������.
     *
     * @param forceClear ��������� ������� ������ ������ � ����� ������ ������� ���-���� ��� ���
     */
    function searchFocusOut(forceClear)
    {
        if (!forceClear && hasSearchResult)
        {
            return;
        }

        clear(true);
    }

    /**
     * ������������ ����� ������ �������� ���������, ��� ���� ������ ����������� ��������� ��������:
     *
     * 1. ���� ���� ������ ��������� � ���� ��������� ������ ���� ��������� ��������� ������ � ������ ������ �� ����������;
     * 2. ���� ���� ������ ��������� � ��� ��������� ������ ������ ������ ����������, ��� ���� ��� ������������� ������ �����
     * �������;
     * 3. ���� ���� ������ �� ���������� ������ ������ ���������� (� �������� ���� ��� � 2�� �����).
     */
    function liveSearchComponentLeave()
    {
        if (!hasSearchResult)
        {
            searchSelectList.css('display', 'none');
            clear(true);
        }
    }

    /**
     * ������ ���������� ���� �� ������� ������ � ������������ ������
     */
    function liveSearchComponentEnter()
    {
        if (dropDownContainer.css('display') == 'none')
        {
            searchSelectList.css('display', '');
        }
    }

    /**
     * ������� ������������ ����� ������ ������� ������������� select'� � ��������� ��������� ��������:
     * 1. ���� ������ ������ � ��������� ������ �������� ������������, �� ��� ���������� ���������� � ��� ���� �����������
     * �� �������;
     * 2. ���� ������ ������ � ��������� ������ ���� ������, �� ��� ������������ ������������.
     */
    function searchSelectClick()
    {
        if (searchSelectList.css('display') == 'none')
        {
            searchSelectList.css('display',  '');
            dropDownContainer.css('display', 'none');
        }
        else
        {
            searchSelectList.css ('display', 'none');
            dropDownContainer.css('display', '');

            enableScrolls(dropDownContainer);
            clear(true);
        }
    }

    /**
     * ������� ������� ������
     *
     * @param id
     * @param text
     * @returns {HTMLElement}
     */
    function createResultElement(id, text)
    {
        var li = document.createElement('li');
        var a  = document.createElement('a');

        $(li).attr('id', id);
        $(a).html( text );
        $(a).appendTo(li);

        return li;
    }

    return {

        /**
         *
         * @param parentId
         * @param liveSearchData ������ ��� ���������� ����������. ������ �������� ������ ���� �������� ���� LiveSearchData
         * @param properties �������������� �������� ����������. ������ ��������� ������ ���� ��������. ����� ��������
         * ���������� ����:
         *
         * liveSearchValueInputName - � ������ ���� �������� �������� ��� ��������� name hidden ��������, ������� �����
         * ���������� �������� ���������� � ���������� ��������.
         */
        init : function(parentId, liveSearchData, properties)
        {
            if (!parentId)
            {
                throw new Error('���������� ������� id �������� � ������� ������ ���������!');
            }

            if (liveSearchData && typeof liveSearchData !== 'object')
            {
                throw new Error('�������� liveSearchData ������ ���� ��������!');
            }

            if (properties && typeof properties !== 'object')
            {
                throw new Error('�������� properties ������ ���� ��������!');
            }

            liveSearchComponent       = $('#' + parentId).find('.liveSearchComponent');
            liveSearchValueInput      = $(liveSearchComponent).find('.liveSearchValueInput');

            searchSelectList          = $(liveSearchComponent).find('.liveSearchSelectList');
            searchInput               = $(liveSearchComponent).find('.searchInput');
            searchResultContainer     = $(liveSearchComponent).find('.liveSearchResultContainer');
            searchResultContainerList = $(searchResultContainer).find('.searchBlockList > ul');

            searchSelect              = $(liveSearchComponent).find('.liveSearchSelect');
            searchSelectTopText       = $(liveSearchComponent).find('.liveSearchSelectTopText');
            dropDownContainer         = $(liveSearchComponent).find('.liveSearchDropDownContainer');
            dropDownContainerList     = $(dropDownContainer).find('.searchBlockList > ul');

            if (liveSearchData)
            {
                this.setLiveSearchData(liveSearchData);
            }

            if (properties)
            {
                if (properties.hasOwnProperty('liveSearchValueInputName'))
                {
                    liveSearchValueInput.attr('name', properties.liveSearchValueInputName);
                }
            }

            liveSearchComponent.mouseenter(function ()
            {
                liveSearchComponentEnter();

            }).mouseleave(function()
            {
                liveSearchComponentLeave();
            });

            searchSelect.click(function ()
            {
                searchSelectClick();
            });

            searchInput.keyup(function()
            {
                searchInputOnKeyUp($(this).val());

            }).focusout(function()
            {
                searchFocusOut(false);
            });

            $('.roundCloseIcon').click(function()
            {
                clear(true);
            });

            customPlaceholder.init();
        },

        setLiveSearchData : function (liveSearchData)
        {
            data = liveSearchData;

            if (data)
            {
                var all = data.getAll();
                var sel = null;

                for (var key in all)
                {
                    if (all.hasOwnProperty(key))
                    {
                        var element = createResultElement(key, all[key].text);

                        if (all[key].selected)
                        {
                            sel = element;
                        }

                        $(element).appendTo(dropDownContainerList);
                        $(element).click(function()
                        {
                            listElementClick(this.id, dropDownContainer);
                        });

                        enableHighlight(element);
                    }
                }

                if (sel)
                {
                    listElementClick(sel.id, null);
                }
            }
        },

        setOnSelectCallback : function(callback)
        {
            if (callback && typeof callback == 'function')
            {
                onSelectCallback = callback;
            }
        }
    }
};