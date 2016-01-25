import java.util.Timer;


public class Main {
	public static void main(String[] args) {
        GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
    }
}
