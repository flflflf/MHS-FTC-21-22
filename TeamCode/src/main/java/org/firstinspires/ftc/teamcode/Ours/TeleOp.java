package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

@ com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends LinearOpMode {
    static DcMotor FL;
    static DcMotor BL;
    static DcMotor FR;
    static DcMotor BR;
    static DcMotorSimple INTAKE;
    static DcMotorSimple Spinner;
    double speed = 1;

    @Override
    public void runOpMode() throws InterruptedException {
        FL = hardwareMap.get(DcMotor.class, "FrontL");
        BL = hardwareMap.get(DcMotor.class, "BackL");
        FR = hardwareMap.get(DcMotor.class, "FrontR");
        BR = hardwareMap.get(DcMotor.class, "BackR");

        INTAKE = hardwareMap.get(DcMotorSimple.class, "IntakeMotor");
        Spinner = hardwareMap.get(DcMotorSimple.class, "SpinnerMotor");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();

        while (opModeIsActive()){
            double y = -gamepad1.left_stick_y;
            double x = gamepad1.left_stick_x;
            double r = gamepad1.right_stick_x;

            telemetry.addData("X", x);
            telemetry.addData("Y", y);
            telemetry.update();

            move(x, y, r, speed);
            if(gamepad1.right_bumper){INTAKE.setPower(-1);}else{INTAKE.setPower(0);}  //intake motor control
            if(gamepad2.a){Spinner.setPower(0.5);}else{Spinner.setPower(0);} //spinner motor control
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