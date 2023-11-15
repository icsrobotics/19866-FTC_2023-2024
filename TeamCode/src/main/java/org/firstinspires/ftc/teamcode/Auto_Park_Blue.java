package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Robot: Auto Drive To Backstage (Blue, Short)", group="Robot")
public class Auto_Park_Blue extends LinearOpMode {

    /* Declare OpMode members. */
    Robot robot = new Robot(this);

    /** The variable to store a reference to our color sensor hardware object */
    static final double     APPROACH_SPEED  = 0.5;

    @Override
    public void runOpMode() {
        robot.init();
        waitForStart();

        // Wait for driver to press PLAY)
        // Abort this loop is started or stopped.
        while (opModeIsActive() && !isStopRequested()) {
            robot.setDrivePower(APPROACH_SPEED);
            sleep(500);
            robot.setDrivePower(-APPROACH_SPEED, -APPROACH_SPEED, APPROACH_SPEED, APPROACH_SPEED);
            sleep(500);
            robot.setDrivePower(APPROACH_SPEED);
            sleep(500);
            robot.setDrivePower(0);
        }
    }
}