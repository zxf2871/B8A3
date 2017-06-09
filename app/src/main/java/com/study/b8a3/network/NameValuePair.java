// Copyright (c) 2016 The hawk Inc. All rights reserved.
// Author: HAWK Browser Team.

package com.study.b8a3.network;

import java.io.Serializable;

public class NameValuePair implements Cloneable, Serializable {
    private static final int HASH_SEED = 17;
    private static final int HASH_OFFSET = 37;

    private final String mName;
    private final String mValue;

    /**
     * Default Constructor taking a name and a value. The value may be null.
     *
     * @param name The name.
     * @param value The value.
     */
    public NameValuePair(final String name, final String value) {
        super();
        if (name == null) {
            throw new IllegalArgumentException("Name may not be null");
        }
        mName = name;
        mValue = value;
    }

    public String getName() {
        return mName;
    }

    public String getValue() {
        return mValue;
    }

    public String toString() {
        // don't call complex default formatting for a simple toString
        if (mValue == null) {
            return mName;
        } else {
            int len = mName.length() + 1 + mValue.length();
            StringBuilder buffer = new StringBuilder(len);
            buffer.append(mName);
            buffer.append("=");
            buffer.append(mValue);
            return buffer.toString();
        }
    }

    public boolean equals(final Object object) {
        if (object == null) return false;
        if (this == object) return true;
        if (object instanceof NameValuePair) {
            NameValuePair that = (NameValuePair) object;
            return mName.equals(that.mName)
                  && equals(mValue, that.mValue);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int hash = HASH_SEED;
        hash = hashCode(hash, mName);
        hash = hashCode(hash, mValue);
        return hash;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    private int hashCode(final int seed, final int hashcode) {
        return seed * HASH_OFFSET + hashcode;
    }

    private int hashCode(final int seed, final boolean b) {
        return hashCode(seed, b ? 1 : 0);
    }

    private int hashCode(final int seed, final Object obj) {
        return hashCode(seed, obj != null ? obj.hashCode() : 0);
    }

    private boolean equals(final Object obj1, final Object obj2) {
        return obj1 == null ? obj2 == null : obj1.equals(obj2);
    }

    private boolean equals(final Object[] a1, final Object[] a2) {
        if (a1 == null) {
            if (a2 == null) {
                return true;
            } else {
                return false;
            }
        } else {
            if (a2 != null && a1.length == a2.length) {
                for (int i = 0; i < a1.length; i++) {
                    if (!equals(a1[i], a2[i])) {
                        return false;
                    }
                }
                return true;
            } else {
                return false;
            }
        }
    }

}
