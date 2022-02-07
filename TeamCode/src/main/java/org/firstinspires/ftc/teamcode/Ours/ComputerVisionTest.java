 package org.firstinspires.ftc.teamcode.Ours;

import android.annotation.SuppressLint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.Ours.Vision.DuckDetector;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;

@Autonomous(name = "ducktest2")
public class ComputerVisionTest extends LinearOpMode {
    OpenCvCamera webcam; // webcam object
    DuckDetector detector = new DuckDetector(telemetry);

    @SuppressLint("DefaultLocale")
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
                telemetry.addData("errprr!!!!1",1);
            }

        });

   /*while(!opModeIsActive()){
       telemetry.addData("in innit loop",0);
        }*/

        waitForStart();

        while (opModeIsActive()){
            int pos = detector.getDuckPosition(); // gets the pos of the duck

            if(pos == 0){
                telemetry.addData("Duck on the right", pos);
            } else if (pos == 1){
                telemetry.addData("duck is in the middle", pos);
            }else{
                telemetry.addData("duck is on the left", pos);
            }

            //adds a bunch of data to benchmark pipeline and controller hub
            telemetry.addData("Frame Count", webcam.getFrameCount());
            telemetry.addData("FPS", String.format("%.2f", webcam.getFps()));
            telemetry.addData("Total frame time ms", webcam.getTotalFrameTimeMs());
            telemetry.addData("Pipeline time ms", webcam.getPipelineTimeMs());
            telemetry.addData("Overhead time ms", webcam.getOverheadTimeMs());
            telemetry.addData("Theoretical max FPS", webcam.getCurrentPipelineMaxFps());
            telemetry.update();
            sleep(50);
        }
    }
}
