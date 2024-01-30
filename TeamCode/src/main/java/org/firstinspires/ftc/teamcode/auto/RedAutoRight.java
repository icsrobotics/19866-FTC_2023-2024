package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.vision.RedVision;
import org.firstinspires.ftc.vision.VisionPortal;


@Config
@Autonomous(name = "Autonomous Red Right",group = "Autonomous")
public class RedAutoRight extends LinearOpMode {
    public double TILE = 22; // 18in previous
    int color = 0;

    RedVision processor = new RedVision(telemetry);
    VisionPortal myVisionPortal;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), processor);

        TrajectorySequence trajLeft = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> drive.setClawPosition(1.0))
                .waitSeconds(2)
                .back(10)
                .strafeRight(TILE)
                .addTemporalMarker(() -> drive.setClawPosition(0.0))
                .waitSeconds(2)
                .forward(8)
                .build();

        TrajectorySequence trajCenter = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> drive.setClawPosition(1.0))
                .waitSeconds(2)
                .strafeRight(TILE + 3)
                .addTemporalMarker(() -> drive.setClawPosition(0.0))
                .waitSeconds(2)
                .strafeLeft(5)
                .build();

        TrajectorySequence trajRight = drive.trajectorySequenceBuilder(new Pose2d())
                .addTemporalMarker(() -> drive.setClawPosition(1.0))
                .waitSeconds(2)
                .strafeRight(TILE)
                .forward(10)
                .addTemporalMarker(() -> drive.setClawPosition(0.0))
                .waitSeconds(2)
                .back(10)
                .build();

        TrajectorySequence trajParkL = drive.trajectorySequenceBuilder(trajLeft.end())
                .strafeLeft(TILE)
                .forward(TILE * 3)
                .build();

        TrajectorySequence trajParkC = drive.trajectorySequenceBuilder(trajCenter.end())
                .strafeLeft(TILE)
                .forward(TILE * 3)
                .build();

        TrajectorySequence trajParkR = drive.trajectorySequenceBuilder(trajRight.end())
                .strafeLeft(TILE)
                .forward(TILE * 3)
                .build();

        while (opModeInInit() && !isStarted()) {
            telemetry.addData("THE COLOR IS Truu", color);
            telemetry.addData("Realtime analysis", RedVision.getReadout());
            telemetry.update();

            color = RedVision.getReadout();
        }

        waitForStart();

        if (opModeIsActive()) {

            // 1 - left, 2 - center, 3 - right
            if (color == 1){
                telemetry.addData("Position", "Left");
                telemetry.update();

                drive.followTrajectorySequence(trajLeft);
                drive.followTrajectorySequence(trajParkL);

            } else if (color == 2) {
                telemetry.addData("Position: ", "Center");
                telemetry.update();

                drive.followTrajectorySequence(trajCenter);
                drive.followTrajectorySequence(trajParkC);

            } else if (color == 3) {
                telemetry.addData("Position: ", "Right");
                telemetry.update();

                drive.followTrajectorySequence(trajRight);
                drive.followTrajectorySequence(trajParkR);

            } else {
                telemetry.addData("Position: ", "Unknown (Running def.)");
                telemetry.update();

                drive.followTrajectorySequence(trajCenter);
            }
        }

        myVisionPortal.close();
    }
}
