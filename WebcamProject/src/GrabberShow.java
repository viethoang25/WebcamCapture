import java.util.Timer;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacv.*;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;

public class GrabberShow implements Runnable {
    
    IplImage image;
    CanvasFrame canvas = new CanvasFrame("Web Cam");
    Timer timer;
    
    public GrabberShow() {
        canvas.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
        timer = new Timer();
    
    }
    @Override
    public void run() {
        FrameGrabber grabber = new VideoInputFrameGrabber(0);
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        AndroidFrameConverter converterToBitmap = new AndroidFrameConverter();
        int i=0;
        try {
            grabber.start();
            IplImage img;
            while (true) {
            	Frame frame = grabber.grab();
                img = converter.convert(frame);
                if (img != null) {
                	timer.schedule(new CaptureImage(img), 0, 5000);
                	image = img;
                    canvas.showImage(frame);
                }
            }
        } catch (Exception e) {
        }
    }
}