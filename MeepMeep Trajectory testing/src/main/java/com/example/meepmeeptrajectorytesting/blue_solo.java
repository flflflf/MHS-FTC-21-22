package com.example.meepmeeptrajectorytesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class blue_solo {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.48180821614297, 52.48180821614297, Math.toRadians(184.02607784577722), Math.toRadians(184.02607784577722), 16.34)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, 60, Math.toRadians(270)))
                                .forward(3)
                                .lineToLinearHeading(new Pose2d(-50,60,Math.toRadians(180)))
                                .back(5)
                                .addDisplacementMarker(() -> {
                                    //spin the thing
                                })

                                .splineToLinearHeading(new Pose2d(-11,42,Math.toRadians(270)),Math.toRadians(0))
                                .addTemporalMarker(()->{
                                    //put the item to the right height
                                })
                                .back(5)
                                .lineToLinearHeading(new Pose2d(-60,35,Math.toRadians(0)))
                                .build()
                        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}