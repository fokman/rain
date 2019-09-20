[["java:package:com.yujie.ice"]]
module info{

struct EmployeeInfo{
string name;
int age;
bool isLeave;
double salary;
string remark;
};
	interface QueryEmployee{
	EmployeeInfo query(EmployeeInfo msg);
	};

};
