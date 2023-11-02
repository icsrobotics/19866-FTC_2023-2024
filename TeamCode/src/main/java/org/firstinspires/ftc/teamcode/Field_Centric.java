package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/**
 * This opmode demonstrates how to create a teleop using just the SampleMecanumDrive class without
 * the need for an external robot class. This will allow you to do some cool things like
 * incorporating live trajectory following in your teleop. Check out TeleOpAgumentedDriving.java for
 * an example of such behavior.
 * <p>
 * This opmode is essentially just LocalizationTest.java with a few additions and comments.
 */
@TeleOp(group = "advanced")
public class Field_Centric extends LinearOpMode {

    Robot robot = new Robot(this);
    public double DAMPENER = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize SampleMecanumDrive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // We want to turn off velocity control for teleop
        // Velocity control per wheel is not necessary outside of motion profiled auto
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // Retrieve our pose from the PoseStorage.currentPose static field
        // See AutoTransferPose.java for further details
        drive.setPoseEstimate(org.firstinspires.ftc.teamcode.PoseStorage.currentPose);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * DAMPENER,
                            -gamepad1.left_stick_x * DAMPENER,
                            -gamepad1.right_stick_x * DAMPENER
                    )
            );
            if (gamepad1.a) {
                DAMPENER = 0.3;
                telemetry.addLine("SET TO SUPER!!! SLOw mode");
            } else if (gamepad1.b) {
                DAMPENER = 0.5;
                telemetry.addLine("SET TO SLOw mode");
            } else if (gamepad1.y) {
                DAMPENER = 0.7;
                telemetry.addLine("SET TO SLOwish mode");
            } else if (gamepad1.x) {
                DAMPENER = 1.0;
                telemetry.addLine("NORMAL");
            }

            drive.setArmPower(-gamepad2.left_stick_y);

            // Update everything. Odometry. Etc.
            drive.update();

            // Read pose
            Pose2d poseEstimate = drive.getPoseEstimate();

            // Print pose to telemetry
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }
    }
}


