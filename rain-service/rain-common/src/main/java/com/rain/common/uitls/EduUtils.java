package com.rain.common.uitls;

import java.util.List;

import com.rain.common.ice.model.IceRequest;

public class EduUtils {

    public static String getCurrentUserId(IceRequest iceRequest) {
        return iceRequest.getExtraAsStr("__curentUser");
    }

    public static void setCurrentUserId(IceRequest iceRequest, long userId) {
        iceRequest.getExtraData().put("__curentUser", String.valueOf(userId));
    }

    public static void setCurrentUserId(IceRequest iceRequest, String userId) {
        iceRequest.getExtraData().put("__curentUser", userId);
    }

    public static String getCurrentUserIdStr(IceRequest iceRequest) {
        return getCurrentUserId(iceRequest);
    }

    public static String getCurrentSchoolId(IceRequest iceRequest) {
        //        if (StringUtils.isEmpty(iceRequest.getExtraAsStr("__curentSchool"))) {
        //            return "1";
        //        }
        return iceRequest.getExtraAsStr("__curentSchool");
    }

    public static String getCurrentSchoolIdStr(IceRequest iceRequest) {
        //        if (StringUtils.isEmpty(iceRequest.getExtraAsStr("__curentSchool"))) {
        //            return "1";
        //        }
        return iceRequest.getExtraAsStr("__curentSchool");
    }

    public static void setCurrentSchoolId(IceRequest iceRequest, long schoolId) {
        iceRequest.getExtraData().put("__curentSchool", String.valueOf(schoolId));
    }

    public static void setCurrentSchoolId(IceRequest iceRequest, String schoolId) {
        iceRequest.getExtraData().put("__curentSchool", schoolId);
    }

    public static void setCurrentRoles(IceRequest iceRequest, String roles) {
        iceRequest.getExtraData().put("__curentRoles", roles);
    }

    public static List<?> getCurrentRoles(IceRequest iceRequest) {
        return JsonUtils.toList(iceRequest.getExtraAsStr("__curentRoles"));
    }

    public static void setCurrentSourceId(IceRequest iceRequest, String sourceId) {
        iceRequest.getExtraData().put("__curentSourceId", sourceId);
    }

    public static String getCurrentSourceId(IceRequest iceRequest) {
        return iceRequest.getExtraAsStr("__curentSourceId");
    }

    public static void setCurrentSourceType(IceRequest iceRequest, String sourceType) {
        iceRequest.getExtraData().put("__curentSourceType", sourceType);
    }

    public static String getCurrentSourceType(IceRequest iceRequest) {
        return iceRequest.getExtraAsStr("__curentSourceType");
    }

    public static void setCurrentNickName(IceRequest iceRequest, String sourceType) {
        iceRequest.getExtraData().put("__curentNickName", sourceType);
    }

    public static String getCurrentNickName(IceRequest iceRequest) {
        return iceRequest.getExtraAsStr("__curentNickName");
    }

}
