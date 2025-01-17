package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Disabled
public class TeleOpBackup extends LinearOpMode {
    static DcMotor FL;
    static DcMotor BL;
    static DcMotor FR;
    static DcMotor BR;
    static DcMotor INTAKE;
    static DcMotor Spinner;
    double speed = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        FL = hardwareMap.get(DcMotor.class, "FrontL");
        BL = hardwareMap.get(DcMotor.class, "BackL");
        FR = hardwareMap.get(DcMotor.class, "FrontR");
        BR = hardwareMap.get(DcMotor.class, "BackR");

        INTAKE = hardwareMap.get(DcMotor.class, "IntakeMotor");
        Spinner = hardwareMap.get(DcMotor.class, "SpinnerMotor");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double r = gamepad1.right_stick_x;

            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.addData("A pressed", gamepad2.a);
            telemetry.update();

            move(x, y, r, speed);

            if(gamepad1.right_bumper){INTAKE.setPower(1);}else{INTAKE.setPower(0);}  //intake motor control
            if(gamepad1.left_bumper){
                Spinner.setPower(.65);
            }else{
                Spinner.setPower(0);
            } //spinner motor control
        }
    }


    static void move(double x, double y, double r, double speed){
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
}