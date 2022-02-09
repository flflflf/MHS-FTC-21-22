package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Ours.Vision.DuckDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;

@Autonomous
public class Autobackup extends OpMode {
    OpenCvCamera webcam; // webcam object
    DuckDetector detector; // duck pos object

    DcMotor FL = null;
    DcMotor FR  = null;
    DcMotor BL = null;
    DcMotor BR = null;

    static final double TICK_PER_REV = 1000;

    static final double wheelDiameter = 3.78;

    static final double TICKS_PER_INCH  = TICK_PER_REV / (wheelDiameter * Math.PI);

    @Override
    public void init() {
        FL = hardwareMap.get(DcMotor.class, "frontLeft");
        FR = hardwareMap.get(DcMotor.class, "frontRight");
        BL = hardwareMap.get(DcMotor.class, "backLeft");
        BR = hardwareMap.get(DcMotor.class, "backRight");

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()); // creates the webcam object
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId); // change the device name with the hardwaremap
        webcam.setPipeline(detector);
    }
    @Override
    public void init_loop(){
        telemetry.addData("Duck Position",detector.getDuckPosition());
    }

    @Override
    public void loop() {
        if(detector.getDuckPosition() == 1){
            //run code if duck is in the middle
        }else if (detector.getDuckPosition() == 0){
            //run code if duck is on the left
        }else {
            //run code if duck is on the right
            encoderDrive(.6, 6,6);
            //elapsed time 500 mili
            encoderDrive(.4,-19, 19); // left turn
            //elapsed time 250 mili
            encoderDrive(.4,60,60);
            //elapsed time 300 mili

            //run code to spin the plate thing
            encoderDrive(.4,-40,-40);
            //elapsed time 300 milli
            encoderDrive(.4,19,-19); // right turn
            //eleapsed time 250 mili
            encoderDrive(.6, 30, 30);

            //run code to drop cube into level

            encoderDrive(.6, 30, 30);
            //eleapsed time 250 milli
            encoderDrive(.4,19,-19); // right turn
            //elapsed time 250 mili
            encoderDrive(.8, 35,35);
        }

    }

    public void encoderDrive(double speed, double leftInches, double rightInches){
        int leftFrontTarget = 0;
        int rightFrontTarget = 0;
        int leftBackTarget = 0;
        int rightBackTarget = 0;

        leftFrontTarget  = FL.getCurrentPosition() + (int)(leftInches * TICKS_PER_INCH);
        rightFrontTarget  = FR.getCurrentPosition() + (int)(rightInches * TICKS_PER_INCH);
        leftBackTarget  = BL.getCurrentPosition() + (int)(leftInches * TICKS_PER_INCH);
        rightBackTarget  = BR.getCurrentPosition() + (int)(rightInches * TICKS_PER_INCH);

        //telling the motors how many ticks I want them to go and then stop.
        FR.setTargetPosition(leftFrontTarget);
        BR.setTargetPosition(rightBackTarget);
        BL.setTargetPosition(leftBackTarget);
        FL.setTargetPosition(leftFrontTarget);

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //send power to motors
        FR.setPower(speed);
        BR.setPower(speed);
        BL.setPower(speed);
        FL.setPower(speed);

        FR.setPower(0);
        BR.setPower(0);
        BL.setPower(0);
        FL.setPower(0);

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}