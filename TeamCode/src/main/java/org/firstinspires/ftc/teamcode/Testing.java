package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.checkerframework.common.util.report.qual.ReportOverride;
import org.firstinspires.ftc.robotcontroller.external.samples.RobotHardware;

@TeleOp(name="Testing 1 2 3", group="TELE OP")
public class Testing extends LinearOpMode {

    Robot robot = new Robot(this);

    @Override
    public void runOpMode() {
        // Initialize robot stuff. Look at /teamcode/Robot/ for details and wait for start (duh)
        robot.init();
        waitForStart();

        while (opModeIsActive()) {
            // ~VIBES~

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

            // DRIVE
            // Mecanum drive is controlled with three axes: drive (front-and-back),
            // strafe (left-and-right), and twist (rotating the whole chassis).
            double drive  = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double twist  = gamepad1.right_stick_x;

            // You may need to multiply some of these by -1 to invert direction of
            // the motor.  This is not an issue with the calculations themselves.
            double[] speeds = {
                    (drive + strafe + twist),
                    (drive - strafe - twist),
                    (drive - strafe + twist),
                    (drive + strafe - twist)
            };

            // Because we are adding vectors and motors only take values between
            // [-1,1] we may need to normalize them.

            // Loop through all values in the speeds[] array and find the greatest
            // *magnitude*.  Not the greatest velocity.
            double max = Math.abs(speeds[0]);
            for(int i = 0; i < speeds.length; i++) {
                if ( max < Math.abs(speeds[i]) ) max = Math.abs(speeds[i]);
            }

            // If and only if the maximum is outside of the range we want it to be,
            // normalize all the other speeds based on the given speed value.
            double LBPower = speeds[2];
            double LFPower = speeds[0];
            double RFPower = speeds[1];
            double RBPower = speeds[3];
            if (max > 1) {
                for (int i = 0; i < speeds.length; i++) speeds[i] /= max;
            }

            // apply the calculated values to the motors.
            robot.setDrivePower(LBPower, LFPower, RBPower, RFPower);
        }
    }
}