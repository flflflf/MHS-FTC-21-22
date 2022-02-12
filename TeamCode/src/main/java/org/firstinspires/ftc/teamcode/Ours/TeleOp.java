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
    static int load = 1;

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

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        while (opModeIsActive()){

            telemetry.addData("X", -gamepad1.left_stick_y);
            telemetry.addData("Y", gamepad1.left_stick_x);
            telemetry.update();

            move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);

            if(gamepad1.left_bumper){ // spinner motor control
                spinner.setPower(.65);
            }else{
                spinner.setPower(0);
            }

            if(gamepad2.dpad_down) {
                setOuttakePos(telemetry, gamepad1, -950);
            }

            if(gamepad2.dpad_right){
                setOuttakePos(telemetry, gamepad1, -1400);
            }

            if(gamepad2.dpad_up){
                setOuttakePos(telemetry, gamepad1, -2050);
            }

            if(gamepad2.b){
                load++;
                if(load % 2 == 0){
                    LoadingPos(telemetry,gamepad1);
                } else{
                    bucketServo.setPosition(.4);
                    transferPos(telemetry,gamepad1);
                }
            }
            if(trackMotor.getCurrentPosition() > -800) {
                bucketServo.setPosition(.12);
            }  else if(!gamepad2.a){bucketServo.setPosition(.5);}
            if(gamepad2.a){
                bucketServo.setPosition(1);
            }

            if(trackMotor.getCurrentPosition() < -2151) {
                trackMotor.setPower(.1 * gamepad2.left_stick_y);
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

    private static void setOuttakePos(Telemetry telemetry, Gamepad gamepad, int position){
        trackMotor.setTargetPosition(position); //get this for the lower position tick amount

        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        trackMotor.setPower(.65); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            move(gamepad.left_stick_x, -gamepad.left_stick_y, gamepad.right_stick_x, speed);
            telemetry.update();
            if(trackMotor.getCurrentPosition() > -800) {
                bucketServo.setPosition(.15);
            } else{
                bucketServo.setPosition(.5);
            }
        }
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        trackMotor.setPower(0);
    }

    private static void LoadingPos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setTargetPosition(-1);

        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        trackMotor.setPower(.3);


        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);
            //bucketServo.setPosition(horizontal);
            telemetry.update();
            if(trackMotor.getCurrentPosition() > -800) {
                bucketServo.setPosition(.15);
            } else{bucketServo.setPosition(.5);}
        }
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        trackMotor.setPower(0);
    }

    private static void transferPos(Telemetry telemetry, Gamepad gamepad1){
        trackMotor.setTargetPosition(-855);

        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        trackMotor.setPower(.65);


        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            //move(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x, speed);
            telemetry.update();
            if(trackMotor.getCurrentPosition() > -800) {
                bucketServo.setPosition(.3);
            }
        }
        trackMotor.setPower(0);
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}