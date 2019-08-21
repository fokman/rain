[["java:package:com.rain.common.ice"]]
#include <Context.ice>
module message{
	dictionary<string, string> tmap;
 	struct Context { 
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
		  Context doInvoke(Context context);
	
	};
};