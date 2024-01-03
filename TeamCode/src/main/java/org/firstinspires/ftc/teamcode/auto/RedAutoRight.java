package org.firstinspires.ftc.teamcode.auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.vision.RedDetection;

import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;


@Autonomous(name = "Autonomous Red Right",group = "Autonomous")
public class RedAutoRight extends LinearOpMode {
 private VisionPortal visionPortal;
 private AprilTagProcessor aprilTagProcessor;
 public RedDetection detector = new RedDetection(telemetry);
 public int color = 0;

    public void runOpMode(){

while(opModeInInit() &&!isStarted()){
color = RedDetection.getReadout();

telemetry.addData("Useless sees ",color);
telemetry.addData("LeftValue",RedDetection.leftValue);
telemetry.addData("CenterValue", RedDetection.centerValue);
telemetry.addData("RightValue",RedDetection.rightValue);
telemetry.update();
}

waitForStart();
    }
}