package org.firstinspires.ftc.teamcode.auto;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.drive.MecanumDrive;
import org.firstinspires.ftc.teamcode.vision.RedVision;
import org.firstinspires.ftc.vision.VisionPortal;


@Config
@Autonomous(name = "PARK",group = "Autonomous")
public class JustPark extends LinearOpMode {
    public double TILE = 20; // 18in previous
    int color = 0;

    RedVision processor = new RedVision(telemetry);
    VisionPortal myVisionPortal;

    @Override
    public void runOpMode() {
        MecanumDrive drive = new MecanumDrive(hardwareMap);
        myVisionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "Webcam 1"), processor);

        Trajectory trajInit = drive.trajectoryBuilder(new Pose2d())
                .forward(TILE * 2)
                .build();



        while (opModeInInit() && !isStarted()) {
            telemetry.addData("THE COLOR IS Truu", color);
            telemetry.addData("Realtime analysis", RedVision.getReadout());
            telemetry.update();

            color = RedVision.getReadout();
        }

        waitForStart();

        if (opModeIsActive()) {
                drive.followTrajectory(trajInit);

        }

        myVisionPortal.close();
    }
}
