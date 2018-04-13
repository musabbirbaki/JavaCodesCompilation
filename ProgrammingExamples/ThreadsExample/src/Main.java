class MyClass extends Thread{
    @Override
    public void run() {
        for(int i = 0; i < 10; i++){
            System.out.println(Thread.currentThread().getId() +" Value " + i);
        }
    }
}

public class Main {

    public static void main(String[] args) {
//        System.out.println("Hello World!");

        MyClass myClass = new MyClass();
        myClass.start();

        MyClass myClass1 = new MyClass();
        myClass1.start();
    }

}
