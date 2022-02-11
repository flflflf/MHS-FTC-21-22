package org.firstinspires.ftc.teamcode.Ours;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class ticksperinch extends OpMode {
    int average = 0;
    DcMotor trackMotor;
    static Servo bucketServo;
    @Override
    public void init() {
        bucketServo = hardwareMap.servo.get("BucketServo");
        trackMotor = hardwareMap.get(DcMotor.class, "TrackMotor");
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        trackMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }


    @Override
    public void loop() {
        trackMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        trackMotor.setPower(gamepad1.left_stick_y);
        bucketServo.setDirection(Servo.Direction.REVERSE);
        bucketServo.setPosition(gamepad1.left_trigger);
        telemetry.addData("position",trackMotor.getCurrentPosition());
        telemetry.addData("servo position", gamepad1.left_trigger);
        if (gamepad1.a) {
            trackMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}
