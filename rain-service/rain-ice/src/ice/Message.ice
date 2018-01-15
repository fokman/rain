[["java:package:com.rain.ice"]]
module message{
	dictionary<string, string> tmap;
 	struct MsgRequest {
 		 string service;
 		 string method;
 		 int  code;
 		 string msg;
 		 tmap extraData;
 		 int total;
		 tmap data;
		 tmap attr;
	};

	interface MessageService {  
		  string doInvoke(MsgRequest msgRequest);
	};
};