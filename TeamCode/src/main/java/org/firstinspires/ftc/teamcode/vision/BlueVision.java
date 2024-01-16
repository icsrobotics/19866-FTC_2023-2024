package org.firstinspires.ftc.teamcode.vision;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class BlueVision implements VisionProcessor {
    Telemetry telemetry;
    Mat mat = new Mat();
    Mat matExtra = new Mat();

    Scalar lowBlueHSV = new Scalar(90, 50, 50);
    Scalar highBlueHSV = new Scalar(130, 255, 255);

    static int readout = 0;

    public static double leftValue;
    public static double rightValue;
    public static double leftPixels;
    public static double rightPixels;

    public static double thresh = 0.022;

    // Could remove clutter with REGION_WIDTH and REGION_HEIGHT constants
    // This is shown in the SkystoneDeterminationExample.java for easyopencv
    static final Rect LEFT_ROI = new Rect(
            new Point(150, 50),
            new Point(150+150, 50+200));
    static final Rect RIGHT_ROI = new Rect(
            new Point(375, 50),
            new Point(375+150, 50+200));

    // For what color  
    // Red
    Scalar No = new Scalar(255, 0, 0);
    // Green
    Scalar Yes = new Scalar(0, 255, 0);


    public BlueVision(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        // Empty... foreva
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        //Core.rotate(frame, frame, Core.ROTATE_180);


        Imgproc.cvtColor(frame, mat, Imgproc.COLOR_RGB2HSV);


        Core.inRange(mat, lowBlueHSV, highBlueHSV, mat);

        Core.bitwise_or(mat,matExtra,mat);


        Mat left = mat.submat(LEFT_ROI);
        Mat right = mat.submat(RIGHT_ROI);

        leftPixels = Core.countNonZero(left);
        rightPixels = Core.countNonZero(right);

        leftValue = leftPixels / LEFT_ROI.area();
        rightValue = rightPixels / RIGHT_ROI.area();

        left.release();
        right.release();

        if (leftPixels>rightPixels && (leftValue>thresh)) {
            readout = 2;
        } else if (rightPixels>leftPixels && (rightValue>thresh)) {
            readout = 3;
        } else {
            readout = 1;
        }

        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGB);

        Imgproc.rectangle(mat, LEFT_ROI, readout == 1? Yes:No);
        Imgproc.rectangle(mat, RIGHT_ROI, readout == 3? Yes:No);

        mat.copyTo(frame);
        mat.release();
        return null;
    }

    public static int getReadout(){
        return readout;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        // Not useful either
    }
}