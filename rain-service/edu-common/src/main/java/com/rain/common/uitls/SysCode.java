package com.rain.common.uitls;

public class SysCode {
	public final static int SUCCESS=0;
	
	//系统错误编码
	
	//网关错误
	public final static int ERR_API_SERVICE=100;//	网关验证服务名不正确
	public final static int ERR_API_METHOD =101;//	网关验证方法名不正确
	public final static int ERR_API_JSON =102;//	网关验证Json解析错误
	public final static int ERR_API_SYS  =103;//网关验证系统错误
	public final static int ERR_API_ICECALL=104;//网关验证ICE调用服务错误
	public final static int ERR_API_TOKEN_NULL=105;//	网关验证Token为空
	public final static int ERR_API_TOKEN_EXPIRE=106;//	网关验证Token失效
	//ICE验证
	public final static int ERR_ICE_SERVICE =300;//	ICE验证无效的服务名
	public final static int ERR_ICE_METHOD  =301;//	ICE验证无效的方法名
	public final static int ERR_ICE_validate=302;//	ICE验证数据校验不通过
	public final static int ERR_ICE_INVOKE=303;//	ICE验证系统错误（调用服务方法错误）
	public final static int ERR_ICE_REDIS=304;//	ICE验证缓存错误

	
}
