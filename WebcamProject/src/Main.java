import java.util.Timer;

import com.box.sdk.BoxFolder;

import api.BoxApi;
import capture.GrabberShow;


public class Main {
	public static void main(String[] args) {
        GrabberShow gs = new GrabberShow();
        Thread th = new Thread(gs);
        th.start();
		/*BoxApi api = BoxApi.getInstance();
		BoxFolder.Info info = api.createFolder(api.getRootFolder(), "testfolder");
		System.out.println(info.getID() +  " : " + info.getName());*/
    }
}
