class Employee{
    String employeeId;
    String doj;
    String designation;
    String role;
}
class Engineer extends Employee{
    String level;
    String[] toolAccess;
    String[] repoAccess;
}
class EngineerManager extends Engineer{
    String[] teamName;
    Engineer[] teamMates;
}
