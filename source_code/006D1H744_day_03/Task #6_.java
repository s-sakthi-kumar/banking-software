class Main{
    public static void main(String[] args){

        Example obj_1 = new Example();
        Example obj_2 = new Example();
        // here we are initializing both the objects of the class Example

        obj_1.init();
        obj_2.init();

        // Here since the var b is shared by both of the objs (obj_1 and obj_2)
        // we will notice the b's value to be 2 when incrementing individual methods
        obj_1.increment();
        obj_1.printVars();
        obj_2.increment();
        obj_2.printVars();
    }
}


class Example{
    int a;
    static int b;
    void init(){
        a = 0;
        b = 0;
    }
    void increment(){
        a = a+1;
        b = b+1;
    }
    void printVars(){
        System.out.println("a = "+a);
        System.out.println("b ="+b);
    }
}
