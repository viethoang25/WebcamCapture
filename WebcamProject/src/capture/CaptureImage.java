package capture;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import org.bytedeco.javacpp.opencv_core.IplImage;

import com.box.sdk.BoxFolder;

import utilities.DateUtils;
import utilities.SessionUtils;
import api.BoxApi;
import config.Constants;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class CaptureImage extends TimerTask {

	private static final String IMAGE_FOLDER = Constants.IMAGE_FOLDER;
	final String OUTPUT_FILE = "PNG";
	
	
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
			String name = imageFileName();
			cvSaveImage(name, image);
			uploadToBox(name);
			Thread.sleep(Constants.TIME_DELAY * 1000);
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
    
    private void uploadToBox(String name) {
    	BoxApi api = BoxApi.getInstance();
		BoxFolder.Info folderInfo = api.createFolder(api.getBoxFolder(SessionUtils.unitFolderId), DateUtils.getCurrentDate());
		SessionUtils.dateFolderId = folderInfo.getID();
		folderInfo = api.createFolder(api.getBoxFolder(SessionUtils.dateFolderId), DateUtils.getCurrentTime());
		SessionUtils.timeFolderId = folderInfo.getID();
		try {
			api.uploadFile(api.getBoxFolder(SessionUtils.timeFolderId), name);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
