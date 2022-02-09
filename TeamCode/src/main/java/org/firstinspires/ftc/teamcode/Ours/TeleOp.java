package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@ com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    static DcMotor FL;
    static DcMotor BL;
    static DcMotor FR;
    static DcMotor BR;
    static DcMotor intake;
    static DcMotor spinner;
    static Servo bucketServo;
    static DcMotor trackMotor;
    static double speed = 1;

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.get(DcMotor.class, "FrontL");
        BL = hardwareMap.get(DcMotor.class, "BackL");
        FR = hardwareMap.get(DcMotor.class, "FrontR");
        BR = hardwareMap.get(DcMotor.class, "BackR");

        intake = hardwareMap.get(DcMotor.class, "IntakeMotor");
        spinner = hardwareMap.get(DcMotor.class, "SpinnerMotor");

        trackMotor = hardwareMap.get(DcMotor.class, "TrackMotor");
        bucketServo = hardwareMap.servo.get("BucketServo");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        trackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        trackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); // resets the encoder position to 0 so the position of the bucket thing should be at the designated start position or it can break
        while (opModeIsActive()){

            telemetry.addData("X", -gamepad1.left_stick_y);
            telemetry.addData("Y", gamepad1.left_stick_x);
            telemetry.update();

            move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);

            if(gamepad1.right_bumper){ // intake motor control
                intake.setPower(1);
            }else{
                intake.setPower(0);
            }

            if(gamepad1.left_bumper){ // spinner motor control
                spinner.setPower(.65);
            }else{
                spinner.setPower(0);
            }

            if(gamepad2.dpad_down){
                OuttakelowerPos(telemetry, gamepad1);
            }

            if(gamepad2.dpad_right){
                OuttakemiddlePos(telemetry, gamepad1);
            }

            if(gamepad2.dpad_up){
                OuttakeUpperPos(telemetry, gamepad1);
            }
        }
    }


    private static void move(double x, double y, double r, double speed){
        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(r), 1);
        double frontLeftPower = (y + x + r) * speed / denominator;
        double backLeftPower = (y - x + r) * speed / denominator;
        double frontRightPower = (y - x - r) * speed / denominator;
        double backRightPower = (y + x - r) * speed / denominator;

        FL.setPower(frontLeftPower);
        BL.setPower(backLeftPower);
        FR.setPower(frontRightPower);
        BR.setPower(backRightPower);
    }

    private static void OuttakelowerPos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            //move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed); this call might need to be here since nested for loops so the main while loop might stop meaning that there wont be any control of the robot
        }

        trackMotor.setPower(0);
    }

    private static void OuttakemiddlePos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            //move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);
        }

        trackMotor.setPower(0);
    }

    private static void OuttakeUpperPos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            //move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);\
        }

        trackMotor.setPower(0);
    }

    private static void OuttakeLoadingPos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //trackMotor.setTargetPosition(???); find the correct value

        trackMotor.setPower(.1);

        //bucketServo.setPosition(???); set to whatever position is

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            //move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);
        }

        trackMotor.setPower(0);
    }
}