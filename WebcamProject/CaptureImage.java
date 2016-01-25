import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import org.bytedeco.javacpp.opencv_core.IplImage;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class CaptureImage extends TimerTask {

	private static final String IMAGE_FOLDER = "./image/";
	final String OUTPUT_FILE = "PNG";
	final int SECONDS = 5;
	
	IplImage image;
	
	public CaptureImage(IplImage image) {
		this.image = image;
	}

	public void setImage(IplImage image) {
		this.image = image;
	}
	
	@Override
	public void run() {
		try {
			cvSaveImage(imageFileName(), image);
			Thread.sleep(SECONDS * 1000);
		} catch (Exception e) {
		}
	}
	
	private String imageFileName(){
		int index = 1;
		String stringIndex = getStringNumber(index);
		String director = IMAGE_FOLDER + "/" + stringIndex + "." + OUTPUT_FILE;
		File checkFile = new File(director);
        while (index < 999 && checkFile.exists()) {
            index++;
            stringIndex = getStringNumber(index);
            director = IMAGE_FOLDER + "/" + stringIndex + "." + OUTPUT_FILE;
            checkFile = new File(director);
        }
        checkFile.getParentFile().mkdirs();
        return director;
	}
	
	// Chuyen int thanh String 3 ky tu;
    private String getStringNumber(int index) {
        String number = "";
        number += Integer.toString((int) (index % Math.pow(10, 3)));
        switch (number.length()) {
            case 1:
                number = "00" + number;
                break;
            case 2:
                number = "0" + number;
                break;
        }
        return number;
    }
}
