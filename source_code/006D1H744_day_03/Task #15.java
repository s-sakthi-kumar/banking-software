class Employee{

    // this class has some data members and methods
    String name;
    String role;
    String employeeID;
    String location;
    void showPrinted(){
        System.out.println(role+" "+name);
    }
    String accessLevel;
    Employee reportsTo;
}

class Manager extends Employee{
    String projectname;
    String client;
    Employee[] teamMembers;
}

class Engineer extends Employee{
    String projectname;
    String[] skills;
}
