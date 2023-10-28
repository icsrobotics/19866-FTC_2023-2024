package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Robot;


@Config
@TeleOp(name = "POV Driving", group = "TeleOp")
public class POV extends LinearOpMode {
    Robot robot = new Robot(this);

    @Override public void runOpMode()  throws InterruptedException {
        robot.init();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            double r = Math.hypot(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            double robotAngle = Math.atan2(-gamepad1.left_stick_y, gamepad1.left_stick_x) - Math.PI / 4;
            double rightX = gamepad1.right_stick_x;
            final double v1 = r * Math.cos(robotAngle) + rightX;
            final double v2 = r * Math.sin(robotAngle) - rightX;
            final double v3 = r * Math.sin(robotAngle) + rightX;
            final double v4 = r * Math.cos(robotAngle) - rightX;

            robot.setDrivePower(v3, v1, v4, v2);

            // ARM
            robot.setArmPower(-gamepad2.left_stick_y);

            // CLAW
//            if (gamepad2.a){
//               robot.setClawPosition(0);
//            } else if (gamepad2.y) {
//                robot.setClawPosition(1.0);
//            } else {
//                robot.setClawPosition(0.5);
//            }
//
//            // CLAW ANGLE
//            robot.setClawAnglePosition(gamepad2.left_trigger * SOFTENER);
//            if (gamepad2.b) { robot.setClawAnglePosition(0.5); }

            // INTAKE
            robot.setIntakePower(gamepad2.right_trigger);

            telemetry.update();

        }
    }
}
