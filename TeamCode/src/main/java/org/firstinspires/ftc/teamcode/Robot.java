package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Robot {

    private static final double INIT_SERVO_POSITION = 0.5;

    /* Declare OpMode members. */
    private LinearOpMode myOpMode = null;   // gain access to methods in the calling OpMode.

    // Define Motor and Servo objects  (Make them private so they can't be accessed externally)
    private DcMotor leftBack   = null;
    private DcMotor rightBack  = null;
    private DcMotor leftFront  = null;
    private DcMotor rightFront  = null;
    private DcMotor leftArm = null;
    private DcMotor rightArm = null;
    private DcMotor intake = null;
    // private Servo claw = null;
    // private Servo clawAngle = null;

    // Define a constructor that allows the OpMode to pass a reference to itself.
    public Robot(LinearOpMode opmode) {
        myOpMode = opmode;
    }

    /**
     * Initialize all the robot's hardware.
     * This method must be called ONCE when the OpMode is initialized.
     * All of the hardware devices are accessed via the hardware map, and initialized.
     */
    public void init()    {
        // Define and Initialize Motors (note: need to use reference to actual OpMode).
        leftBack   =  myOpMode.hardwareMap.get(DcMotor.class, "LEFT_BACK");
        rightBack  = myOpMode.hardwareMap.get(DcMotor.class, "RIGHT_BACK");
        leftFront  = myOpMode.hardwareMap.get(DcMotor.class, "LEFT_FRONT");
        rightFront  = myOpMode.hardwareMap.get(DcMotor.class, "RIGHT_FRONT");

        leftArm = myOpMode.hardwareMap.get(DcMotor.class, "LEFT_ARM");
        rightArm = myOpMode.hardwareMap.get(DcMotor.class, "RIGHT_ARM");

       intake =  myOpMode.hardwareMap.get(DcMotor.class, "INTAKE");

        //  Define and Initialize Servo
        // claw = myOpMode.hardwareMap.get(Servo.class, "SERVO");
        // clawAngle = myOpMode.hardwareMap.get(Servo.class, "SERVO_ANGLE");
        // claw.setPosition(INIT_SERVO_POSITION);
        // clawAngle.setPosition(INIT_SERVO_POSITION);

        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
       // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.FORWARD);
        rightFront.setDirection(DcMotor.Direction.FORWARD);

        leftArm.setDirection(DcMotorSimple.Direction.REVERSE);
        rightArm.setDirection(DcMotorSimple.Direction.FORWARD);


        myOpMode.telemetry.addData("Hello", "Hardware Initialized");
        myOpMode.telemetry.update();
    }

    public void setDrivePower(double power) {
        leftBack.setPower(power);
        leftFront.setPower(power);
        rightBack.setPower(power);
        rightFront.setPower(power);
    }
    
    public void setDrivePower(double LBpower, double LFpower, double RBpower, double RFpower) {
        leftBack.setPower(LBpower);
        leftFront.setPower(LFpower);
        rightBack.setPower(RBpower);
        rightFront.setPower(RFpower);
    }

    public void setArmPower(double leftPower, double rightPower) {
        leftArm.setPower(leftPower);
        rightArm.setPower(rightPower);
    }
    
    public void setArmPower(double power) {
        leftArm.setPower(power);
        rightArm.setPower(power);
    }

    public void setIntakePower(double power){
        intake.setPower(power);
    }

    // public void setClawPosition(double position) { claw.setPosition(position); }
    // public void setClawAnglePosition(double position) { clawAngle.setPosition(position); }
}
