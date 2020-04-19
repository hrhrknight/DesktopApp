//fortes changed
public class fortes {
    static public void main(String arg[]) {
        
        th n_th = new th();
        Thread t = new Thread(n_th);
        t.start();
        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
        return;
    }
    

}

class th implements Runnable {
    public void gold(){
        System.out.println("gold");
    }
    
    public void run() {
        System.out.println("running");
        gold();
        while (true) {
        }
    }
}