package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.RedDetectionOpenCV;
import org.firstinspires.ftc.teamcode.vision.RedDetectionVisionPortal;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;


@Config
@Autonomous(name = "Autonomous Red Right",group = "Autonomous")
public class RedAutoRight extends LinearOpMode {
    public RedDetectionVisionPortal detector = new RedDetectionVisionPortal(telemetry);
    public double TILE = 22;
    OpenCvWebcam webcam;
    RedDetectionOpenCV.SkystoneDeterminationPipeline pipeline;
    RedDetectionOpenCV.SkystoneDeterminationPipeline.NukePosition snapshotAnalysis = RedDetectionOpenCV.SkystoneDeterminationPipeline.NukePosition.CENTER; // default

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
        pipeline = new RedDetectionOpenCV.SkystoneDeterminationPipeline();
        webcam.setPipeline(pipeline);

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
        while (!isStarted() && !isStopRequested())
        {
            telemetry.addData("Realtime analysis", pipeline.getAnalysis());
            telemetry.update();

            // Don't burn CPU cycles busy-looping in this sample
            sleep(50);
        }

        /*
         * The START command just came in: snapshot the current analysis now
         * for later use. We must do this because the analysis will continue
         * to change as the camera view changes once the robot starts moving!
         */
        snapshotAnalysis = pipeline.getAnalysis();

        /*
         * Show that snapshot on the telemetry
         */
        telemetry.addData("Snapshot post-START analysis", snapshotAnalysis);
        telemetry.update();

        switch (snapshotAnalysis)
        {
            case LEFT:
            {
                /* Your autonomous code */
                telemetry.addData("Position: ","Left");
                telemetry.update();
                break;
            }

            case RIGHT:
            {
                /* Your autonomous code */
                telemetry.addData("Position: ","Right");
                telemetry.update();
                break;
            }

            case CENTER:
            {
                /* Your autonomous code*/
                telemetry.addData("Position: ","Center");
                telemetry.update();
                break;
            }
        }
    }
}
