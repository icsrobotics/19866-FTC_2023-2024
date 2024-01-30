package org.firstinspires.ftc.teamcode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.drive.MecanumDrive;

@Config
@TeleOp
public class POV extends LinearOpMode {
    double multiplier = 0.9;

    ElapsedTime timer = new ElapsedTime();
    boolean isEndgame = false;


    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive robot = new MecanumDrive(hardwareMap);

        waitForStart();
        timer.reset();

        if (isStopRequested()) return;

        while (opModeIsActive() && !isStopRequested()) {

            if (timer.seconds() >= 90) { isEndgame = true; }

            double leftFPower;
            double rightFPower;
            double leftBPower;
            double rightBPower;

            double drive = -gamepad1.left_stick_y * multiplier;
            double turn = gamepad1.right_stick_x * multiplier;
            double strafe = gamepad1.left_stick_x * 0.5;
            // --------------- Calculate drive power --------------- //
            if (drive != 0 || turn != 0) {
                leftFPower = Range.clip(drive + turn, -1.0, 1.0);
                rightFPower = Range.clip(drive - turn, -1.0, 1.0);
                leftBPower = Range.clip(drive + turn, -1.0, 1.0);
                rightBPower = Range.clip(drive - turn, -1.0, 1.0);
            } else if (strafe != 0) {
                // Strafing
                leftFPower = -strafe;
                rightFPower = strafe;
                leftBPower = strafe;
                rightBPower = -strafe;
            } else {
                leftFPower = 0;
                rightFPower = 0;
                leftBPower = 0;
                rightBPower = 0;
            }
            robot.setMotorPowers(leftFPower, leftBPower, rightBPower, rightFPower);

            robot.setScooperPower(gamepad2.right_stick_y);

            if (gamepad2.dpad_up) {
                robot.setClawPosition(1.0);
            } else if (gamepad2.dpad_down) {
                robot.setClawPosition(0.0);
            }

            // ITS THE END OF UR LIFE. ENDGAME!!!
            if (isEndgame) { robot.setArmPower(-gamepad2.left_stick_y); }

            if (gamepad2.y && isEndgame) {
                robot.setShooterPosition(1.0);
            } else {
                robot.setShooterPosition(0.5);
            }


            telemetry.addData("seconds in match: ", timer.seconds());
            telemetry.update();
        }
    }
}

