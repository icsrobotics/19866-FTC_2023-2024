package org.firstinspires.ftc.teamcode.auto;

import android.telephony.IccOpenLogicalChannelResponse;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.DetectionOpenCV;
import org.firstinspires.ftc.teamcode.vision.RedDetectionVisionPortal;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Config
@Autonomous(name = "Autonomous Red Right",group = "Autonomous")
public class RedAutoRight extends LinearOpMode {
    public double TILE = 22;
    OpenCvWebcam webcam;

    public RedDetectionVisionPortal detecter = new RedDetectionVisionPortal(telemetry);
    int color = 0;

    @Override
    public void runOpMode()
    {
        /**
         * NOTE: Many comments have been omitted from this sample for the
         * sake of conciseness. If you're just starting out with EasyOpenCv,
         * you should take a look at {@link InternalCamera1Example} or its
         * webcam counterpart, {@link WebcamExample} first.
         */

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(320,240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {}
        });

        /*
         * The INIT-loop:
         * This REPLACES waitForStart!
         */
        while (opModeInInit() && !isStarted())
        {
            telemetry.addData("Realtime analysis", RedDetectionVisionPortal.getReadout());
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        /*
         * The START command just came in: snapshot the current analysis now
         * for later use. We must do this because the analysis will continue
         * to change as the camera view changes once the robot starts moving!
         */
        snapshotAnalysis = pipelsis();
        ine.getAnaly
        /*
         * Show that snapshot on the telemetry
         */
        telemetry.addData("Snapshot post-START analysis", snapshotAnalysis);
        telemetry.update();

        switch (readout)
        {
            case 1:
            {
                /* Your autonomous code */
                telemetry.addData("Position: ","Left");
                telemetry.update();
                break;
            }

            case 2:
            {
                /* Your autonomous code */
                telemetry.addData("Position: ","Right");
                telemetry.update();
                break;
            }

            case 3:
            {
                /* Your autonomous code*/
                telemetry.addData("Position: ","Center");
                telemetry.update();
                break;
            }
        }
    }
}
