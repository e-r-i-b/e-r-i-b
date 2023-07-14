package com.rssl.common.forms.expressions.js;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Evgrafov
 * @ created 06.09.2007
 * @ $Author$
 * @ $Revision$
 */
public class JSMap implements Scriptable
{

    public static JSMap wrap(Map<String, Object> javaMap)
    {
        return new JSMap(javaMap);
    }

    private Map<String, Object> javaMap;
    private Scriptable prototype;
    private Scriptable parent;


    public JSMap() {
        javaMap = new HashMap<String, Object>();
    }

    private JSMap(Map<String, Object> javaMap) {
        this.javaMap = javaMap;
    }


    public String getClassName() {
        return "JSMap";
    }

    public Object get(String name, Scriptable start) {
        return Context.javaToJS(javaMap.get(name), start);
    }

    public Object get(int index, Scriptable start) {
        // not supported
        return null;
    }

    public boolean has(String name, Scriptable start) {
        return true;
    }

    public boolean has(int index, Scriptable start) {
        return false;
    }

    public void put(String name, Scriptable start, Object value) {
        javaMap.put(name, value);
    }

    public void put(int index, Scriptable start, Object value) {
    }

    public void delete(String name) {
        javaMap.remove(name);
    }

    public void delete(int index) {
    }

    public Scriptable getPrototype() {
        return prototype;
    }

    public void setPrototype(Scriptable prototype) {
        this.prototype = prototype;
    }

    public Scriptable getParentScope() {
        return parent;
    }

    public void setParentScope(Scriptable parent) {
        this.parent = parent;
    }

    public Object[] getIds() {
        return new Object[0];
    }

    public Object getDefaultValue(Class hint) {
        return javaMap.toString();
    }

    public boolean hasInstance(Scriptable instance) {
        Scriptable proto = instance.getPrototype();
        while (proto != null) {
            if (proto.equals(this))
                return true;
            proto = proto.getPrototype();
        }

        return false;
    }
}
