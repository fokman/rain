//
// Copyright (c) ZeroC, Inc. All rights reserved.
//
//
// Ice version 3.7.2
//
// <auto-generated>
//
// Generated from file `query.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package com.yujie.ice.info;

public class EmployeeInfo implements java.lang.Cloneable,
                                     java.io.Serializable
{
    public String name;

    public int age;

    public boolean isLeave;

    public double salary;

    public String remark;

    public EmployeeInfo()
    {
        this.name = "";
        this.remark = "";
    }

    public EmployeeInfo(String name, int age, boolean isLeave, double salary, String remark)
    {
        this.name = name;
        this.age = age;
        this.isLeave = isLeave;
        this.salary = salary;
        this.remark = remark;
    }

    public boolean equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        EmployeeInfo r = null;
        if(rhs instanceof EmployeeInfo)
        {
            r = (EmployeeInfo)rhs;
        }

        if(r != null)
        {
            if(this.name != r.name)
            {
                if(this.name == null || r.name == null || !this.name.equals(r.name))
                {
                    return false;
                }
            }
            if(this.age != r.age)
            {
                return false;
            }
            if(this.isLeave != r.isLeave)
            {
                return false;
            }
            if(this.salary != r.salary)
            {
                return false;
            }
            if(this.remark != r.remark)
            {
                if(this.remark == null || r.remark == null || !this.remark.equals(r.remark))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int hashCode()
    {
        int h_ = 5381;
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, "::info::EmployeeInfo");
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, name);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, age);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, isLeave);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, salary);
        h_ = com.zeroc.IceInternal.HashUtil.hashAdd(h_, remark);
        return h_;
    }

    public EmployeeInfo clone()
    {
        EmployeeInfo c = null;
        try
        {
            c = (EmployeeInfo)super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return c;
    }

    public void ice_writeMembers(com.zeroc.Ice.OutputStream ostr)
    {
        ostr.writeString(this.name);
        ostr.writeInt(this.age);
        ostr.writeBool(this.isLeave);
        ostr.writeDouble(this.salary);
        ostr.writeString(this.remark);
    }

    public void ice_readMembers(com.zeroc.Ice.InputStream istr)
    {
        this.name = istr.readString();
        this.age = istr.readInt();
        this.isLeave = istr.readBool();
        this.salary = istr.readDouble();
        this.remark = istr.readString();
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, EmployeeInfo v)
    {
        if(v == null)
        {
            _nullMarshalValue.ice_writeMembers(ostr);
        }
        else
        {
            v.ice_writeMembers(ostr);
        }
    }

    static public EmployeeInfo ice_read(com.zeroc.Ice.InputStream istr)
    {
        EmployeeInfo v = new EmployeeInfo();
        v.ice_readMembers(istr);
        return v;
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, java.util.Optional<EmployeeInfo> v)
    {
        if(v != null && v.isPresent())
        {
            ice_write(ostr, tag, v.get());
        }
    }

    static public void ice_write(com.zeroc.Ice.OutputStream ostr, int tag, EmployeeInfo v)
    {
        if(ostr.writeOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            int pos = ostr.startSize();
            ice_write(ostr, v);
            ostr.endSize(pos);
        }
    }

    static public java.util.Optional<EmployeeInfo> ice_read(com.zeroc.Ice.InputStream istr, int tag)
    {
        if(istr.readOptional(tag, com.zeroc.Ice.OptionalFormat.FSize))
        {
            istr.skip(4);
            return java.util.Optional.of(EmployeeInfo.ice_read(istr));
        }
        else
        {
            return java.util.Optional.empty();
        }
    }

    private static final EmployeeInfo _nullMarshalValue = new EmployeeInfo();

    /** @hidden */
    public static final long serialVersionUID = 1341383293L;
}
