package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.*;

@ com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")
public class TeleOp extends OpMode {
    static DcMotor FL;
    static DcMotor BL;
    static DcMotor FR;
    static DcMotor BR;
    double speed = .7;

    @Override
    public void init() {
        //initialize the motors
        FL = hardwareMap.get(DcMotor.class, "FrontL");
        BL = hardwareMap.get(DcMotor.class, "BackL");
        FR = hardwareMap.get(DcMotor.class, "FrontR");
        BR = hardwareMap.get(DcMotor.class, "BackR");
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        //left joystick does foward backwards straif left and right right joestick x axis should be rotating
    }

    @Override
    public void loop(){
        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x;
        double r = gamepad1.right_stick_x;

        if(Math.abs(x) < .2){x = 0;} // dead zone
        if(Math.abs(y) < .2){y = 0;}

        telemetry.addData("X", x);
        telemetry.addData("Y", y);
        telemetry.update();

        move(x, y, r, speed);
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