class Display {
    // Accepts an integer
    void printData(int a) { 
        System.out.println("Integer: " + a); 
    }
    // Accepts a String
    void printData(String b) { 
        System.out.println("String: " + b); 
    }
}


class MethodOverloading{
    void add(int a,int b){
        System.out.println(a+b);
    }
    void add(int a,int b,int c){
        System.out.println(a+b+c);
    }
    void add(String a,String b){
        System.out.println(a+b);
    }
}
