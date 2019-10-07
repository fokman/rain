[["java:package:com.rain.common.ice.v1"]]
module message{
	dictionary<string, string> tmap;
 	struct MsgRequest {
 		 string service;
 		 string method;
 		 tmap extraData;
 		 int  code;
 		 string msg; 
 		 int total;  
		 tmap data;
		 tmap attr;
	}; 
	interface MessageService {  
		  string doInvoke(MsgRequest context);
	
	};
};
