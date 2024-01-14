package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.vision.RedDetectionVisionPortal;
import org.firstinspires.ftc.vision.VisionPortal;


@Config
@Autonomous(name = "Autonomous Red Right",group = "Autonomous")
public class RedAutoRight extends LinearOpMode {
    public double TILE = 22; // 18in previous
    int color = 0;

    MecanumDrive drive = new MecanumDrive(hardwareMap);
    RedDetectionVisionPortal processor = new RedDetectionVisionPortal(telemetry);
    VisionPortal myVisionPortal;

    @Override
    public void runOpMode() {
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), processor);

        Trajectory trajInit = drive.trajectoryBuilder(new Pose2d())
                .strafeRight(TILE)
                .build();

        while (opModeInInit() && !isStarted()) {
            telemetry.addData("THE COLOR IS Truu", color);
            telemetry.addData("Realtime analysis", RedDetectionVisionPortal.getReadout());
            telemetry.update();

            color = RedDetectionVisionPortal.getReadout();
        }

        waitForStart();

        if (opModeIsActive()) {

            // 1 - left, 3 - right, 0 - none
            switch (color) {
                case 0: {
                    telemetry.addData("Position", "Unknown :(");
                    telemetry.update();

                    drive.followTrajectory(trajInit);

                    break;
                }

                case 1: {
                    /* Your autonomous code */
                    telemetry.addData("Position: ", "Left");
                    telemetry.update();

                    drive.followTrajectory(trajInit);

                    break;
                }

                case 3: {
                    /* Your autonomous code*/
                    telemetry.addData("Position: ", "Right");
                    telemetry.update();

                    drive.followTrajectory(trajInit);

                    break;
                }
            }
        }

        myVisionPortal.close();
    }
}
