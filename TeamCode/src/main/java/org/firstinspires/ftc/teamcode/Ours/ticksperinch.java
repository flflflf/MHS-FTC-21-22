package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous
public class ticksperinch extends OpMode {
    int average = 0;
    DcMotor trackMotor;
    @Override
    public void init() {
         trackMotor = hardwareMap.get(DcMotor.class, "TrackMotor");
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    public void loop() {


        telemetry.addData("position",trackMotor.getCurrentPosition());


        if (gamepad1.a) {
            trackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else{}
    }
}
