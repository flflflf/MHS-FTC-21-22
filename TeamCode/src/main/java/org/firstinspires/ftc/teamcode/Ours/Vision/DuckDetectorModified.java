package org.firstinspires.ftc.teamcode.Ours.Vision;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.List;

public class DuckDetectorModified extends OpenCvPipeline {

    Mat HSVMat = new Mat();
    Mat contoursOnFrameMat = new Mat();

    List<MatOfPoint> contoursList = new ArrayList<>();

    int numContoursFound = 0;

    private final Scalar lowerHSV = new Scalar(19, 89, 172); // the lower limit for the detection (tune this for camera)
    private final Scalar upperHSV = new Scalar(59, 250, 250); // upper limit also tune this with the camera
    // TODO: tune these parameters
    private final double yAxisTop = 40; // the upper threshold so anything above this imaginary line is not seen by the pipeline

    private final double yAxisBot = 70; // the lower threshold so anything below this imaginary line is not seen by the pipeline

    private final double maxArea = 2000; //the max area a counter can be so anything larger wont effect the duckPos

    private final double minArea = 1500; // the min area like above but min

    private final int leftPosThreshold = 75;

    private final int middlePosThreshold = 150;

    private final double blurConstant = 1; // change this to change the Gaussian blur amount

    private final double dilationConstant = 2; // tune


    int duckPosition = 0; // far left = 0, middle 1 , far right 2

    Telemetry telemetryOpenCV;

    //constructor
    public DuckDetectorModified(Telemetry OpModeTelemetry) {
        telemetryOpenCV = OpModeTelemetry;
    }

    public int getDuckPosition() {
        return duckPosition;
    }

    public int getNumContoursFound() { // kinda a useless but fun method that returns the amount of contours found (it should be 1)
        return contoursList.size();
    }

    @Override
    public Mat processFrame(Mat input) {
        //clear the list
        contoursList.clear();

        Imgproc.cvtColor(input, HSVMat, Imgproc.COLOR_RGB2HSV_FULL); // converts the RGB image (Mat) to the HSV color space and outputs that to HSV Mat
        Core.inRange(HSVMat, lowerHSV, upperHSV, HSVMat); // filters every pixel that is not in our range

        Size kernalSize = new Size(blurConstant, blurConstant);

        // adds blur effect to the image to help image processing
        Imgproc.GaussianBlur(HSVMat, HSVMat, kernalSize, 0);
        Size kernalRectangleSize = new Size(2 * dilationConstant + 1, 2 * dilationConstant + 1);

        Mat kernal = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, kernalRectangleSize); // dialution
        Imgproc.dilate(HSVMat, HSVMat, kernal);

        //finds contours
        Imgproc.findContours(HSVMat, contoursList, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        numContoursFound = contoursList.size(); // finds the amount of contours made

        input.copyTo(contoursOnFrameMat);

        int rectXtotal = 0;
        // TODO: make sure that this new "finding the average x position" thing actually works
        for (int i = 0; i < contoursList.size(); i++) {
            Rect rect = Imgproc.boundingRect(contoursList.get(i));
            Imgproc.rectangle(contoursOnFrameMat, Imgproc.boundingRect(contoursList.get(i)).tl(), Imgproc.boundingRect(contoursList.get(contoursList.size() / 2)).br(), new Scalar(255, 0, 0), 2); // draws the red outline of the counters ??
            if (rect.y >= yAxisTop && rect.y <= yAxisBot) {
                rectXtotal = +rect.x;
            }
        }
        int rectXAv = rectXtotal / contoursList.size();

        Imgproc.putText(contoursOnFrameMat, String.valueOf(rectXAv), Imgproc.boundingRect(contoursList.get(contoursList.size() / 2)).tl(), 0, 0.5, new Scalar(2500, 255, 255)); // prints x value

        if (rectXAv <= leftPosThreshold) {
            duckPosition = 0;
        } else if (rectXAv > leftPosThreshold + 1 && rectXAv <= middlePosThreshold) {
            duckPosition = 1;
        } else {
            duckPosition = 2;
        }
        return input;
    }
}