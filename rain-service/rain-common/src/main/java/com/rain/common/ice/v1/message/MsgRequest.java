//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.6
//
// <auto-generated>
//
// Generated from file `Message.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.rain.common.ice.v1.message;

import java.util.Map;

public class MsgRequest implements java.lang.Cloneable,
        java.io.Serializable {
    public String service;

    public String method;

    public java.util.Map<java.lang.String, java.lang.String> extData;

    public int code;

    public String msg;

    public int total;

    public java.util.Map<java.lang.String, java.lang.String> data;

    public java.util.Map<java.lang.String, java.lang.String> attr;

    public MsgRequest() {
        this.service = "";
        this.method = "";
        this.msg = "";
    }

    public MsgRequest(String service, String method, java.util.Map<java.lang.String, java.lang.String> extData, int code, String msg, int total, java.util.Map<java.lang.String, java.lang.String> data, java.util.Map<java.lang.String, java.lang.String> attr) {
        this.service = service;
        this.method = method;
        this.extData = extData;
        this.code = code;
        this.msg = msg;
        this.total = total;
        this.data = data;
        this.attr = attr;
    }

    public MsgRequest(String service, String method, Map<String, String> extraData, Map<String, String> attr) {
        this.service = service;
        this.method = method;
        this.data = extraData;
        this.attr = attr;
    }

    public boolean equals(java.lang.Object rhs) {
        if (this == rhs) {
            return true;
        }
        MsgRequest r = null;
        if (rhs instanceof MsgRequest) {
            r = (MsgRequest) rhs;
        }

        if (r != null) {
            if (this.service != r.service) {
                if (this.service == null || r.service == null || !this.service.equals(r.service)) {
                    return false;
                }
            }
            if (this.method != r.method) {
                if (this.method == null || r.method == null || !this.method.equals(r.method)) {
                    return false;
                }
            }
            if (this.extData != r.extData) {
                if (this.extData == null || r.extData == null || !this.extData.equals(r.extData)) {
                    return false;
                }
            }
            if (this.code != r.code) {
                return false;
            }
            if (this.msg != r.msg) {
                if (this.msg == null || r.msg == null || !this.msg.equals(r.msg)) {
                    return false;
                }
            }
            if (this.total != r.total) {
                return false;
            }
            if (this.data != r.data) {
                if (this.data == null || r.data == null || !this.data.equals(r.data)) {
                    return false;
                }
            }
            if (this.attr != r.attr) {
                if (this.attr == null || r.attr == null || !this.attr.equals(r.attr)) {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode() {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::message::MsgRequest");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, service);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, method);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, extData);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, code);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, msg);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, total);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, data);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, attr);
        return h_;
    }

    public MsgRequest clone() {
        MsgRequest c = null;
        try {
            c = (MsgRequest) super.clone();
        } catch (CloneNotSupportedException ex) {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr) {
        ostr.writeString(this.service);
        ostr.writeString(this.method);
        tmapHelper.write(ostr, this.extData);
        ostr.writeInt(this.code);
        ostr.writeString(this.msg);
        ostr.writeInt(this.total);
        tmapHelper.write(ostr, this.data);
        tmapHelper.write(ostr, this.attr);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr) {
        this.service = istr.readString();
        this.method = istr.readString();
        this.extData = tmapHelper.read(istr);
        this.code = istr.readInt();
        this.msg = istr.readString();
        this.total = istr.readInt();
        this.data = tmapHelper.read(istr);
        this.attr = tmapHelper.read(istr);
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, MsgRequest v) {
        if (v == null) {
            _nullMarshalValue.ice_writeMembers(ostr);
        } else {
            v.ice_writeMembers(ostr);
        }
    }

    static public MsgRequest ice_read(com.zeroc.Ice.InputStream istr) {
        MsgRequest v = new MsgRequest();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<MsgRequest> v) {
        if (v != null && v.isPresent()) {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, MsgRequest v) {
        if (ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize)) {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<MsgRequest> ice_read(com.zeroc.Ice.InputStream istr, int tag) {
        if (istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize)) {
            istr.skip(4);
            return java.util.Optional.of(MsgRequest.ice_read(istr));
        } else {
            return java.util.Optional.empty();
        }
    }

    private static final MsgRequest _nullMarshalValue = new MsgRequest();

    /**
     * @hidden
     */
    public static final long serialVersionUID = 272395013574936902L;
}
