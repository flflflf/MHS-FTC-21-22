package org.firstinspires.ftc.teamcode.Ours;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Ours.Vision.CupDetector;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "Blue_spin_far")
public class Blue_far_spin extends LinearOpMode {
    Pose2d startPos = new Pose2d(12, 62, Math.toRadians(270));
    static DcMotor spinner;
    static Servo bucketServo;
    static DcMotor trackMotor;
    OpenCvCamera webcam; // webcam object
    CupDetector detector = new CupDetector(telemetry);

    @Override
    public void runOpMode() throws InterruptedException {

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(detector);
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

                telemetry.addData("failed to open camera setting position to ",detector.getCupPosition());
            }

        });

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(startPos);
        spinner = hardwareMap.get(DcMotor.class, "SpinnerMotor");
        trackMotor = hardwareMap.get(DcMotor.class, "TrackMotor");
        bucketServo = hardwareMap.servo.get("BucketServo");

        while(!opModeIsActive() && !isStopRequested()){
            //bucketServo.setPosition(upright);
            int pos = detector.getCupPosition(); // gets the pos of the duck
            telemetry.addData("duck pos", pos);
            telemetry.update();
            sleep(100);
        }
        waitForStart();

        int snapshot = detector.getCupPosition();
        telemetry.addData("snapshot analysis", snapshot);

        TrajectorySequence trajSeq = drive.trajectorySequenceBuilder(startPos)
                .forward(3)
                .lineToSplineHeading(new Pose2d(2,37,Math.toRadians(225)))
                .addTemporalMarker(()->{
                    if(snapshot == 0){
                        OuttakelowerPos(telemetry);
                        //bucketServo.setPosition(dump);
                    } else if (snapshot == 1) {
                        OuttakemiddlePos(telemetry);
                        //bucketServo.setPosition(dump);
                    } else {
                        OuttakeUpperPos(telemetry);
                        //bucketServo.setPosition(dump);
                    }
                    transferPos(telemetry);
                })
                .back(10)
                .lineToLinearHeading(new Pose2d(-55,55,Math.toRadians(90)))
                .addDisplacementMarker(() -> {
                    spinner.setPower(.65);
                    sleep(5000);
                })
                .lineToLinearHeading(new Pose2d(-60,35,Math.toRadians(0)))
                .build();

        drive.followTrajectorySequence(trajSeq);
    }

    private static void OuttakelowerPos(Telemetry telemetry){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
        }

        trackMotor.setPower(0);
    }

    private static void OuttakemiddlePos(Telemetry telemetry){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
        }

        trackMotor.setPower(0);
    }

    private static void OuttakeUpperPos(Telemetry telemetry){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        // trackMotor.setTargetPosition(???); get this for the lower position tick amount

        trackMotor.setPower(.1); // adjust this low for testing

        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
        }

        trackMotor.setPower(0);
    }

    private static void transferPos(Telemetry telemetry){
        trackMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        trackMotor.setTargetPosition(0);

        trackMotor.setPower(.1);


        while (trackMotor.isBusy()){
            telemetry.addData("current pos", trackMotor.getCurrentPosition());
            // bucketServo.setPosition(upright);
        }
    }
}