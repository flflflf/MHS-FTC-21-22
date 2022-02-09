package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class ticksperinch extends OpMode {
    int average = 0;

    @Override
    public void init() {

    }

    @Override
    public void loop() {
        DcMotor FL = hardwareMap.get(DcMotor.class, "FrontL");
        DcMotor FR = hardwareMap.get(DcMotor.class, "FrontR");
        DcMotor BL = hardwareMap.get(DcMotor.class, "BackL");
        DcMotor BR = hardwareMap.get(DcMotor.class, "BackR");

         FL.setDirection(DcMotorSimple.Direction.REVERSE);
         BL.setDirection(DcMotorSimple.Direction.REVERSE);

        int frontLeft = FL.getCurrentPosition();
        int frontRight = FR.getCurrentPosition();
        int leftBack = BL.getCurrentPosition();
        int rightBack = BR.getCurrentPosition();

        telemetry.addData("front left", frontLeft);
        telemetry.addData("front right", frontRight);
        telemetry.addData("back left", leftBack);
        telemetry.addData("back right", rightBack);
        average = (frontLeft + frontRight + leftBack + rightBack) / 4;
        telemetry.addData("average", average);
        if (gamepad1.a) {
            FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            average = 0;
        } else{}
    }
}
