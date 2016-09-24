package java多线程学习;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class testThread extends Thread {
    boolean flag = true;
    int timer = 0;

    @Override
    public void run() {
        super.run();
        try {
            while (flag) {
                this.currentThread().sleep(1000);
                timer++;
                System.out.print(timer);
                System.out.println("线程");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        testThread thread = new testThread();
        thread.start();
        for (int i = 0; i < 1000000; i++) {
			System.out.println(i+"启动计时器...");
		}
        
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//    
//        try {
//            String line = reader.readLine();
//            if(line.equalsIgnoreCase("1")){
//                //thread.stop();
//                thread.flag = false;
//            }
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
    }
}
