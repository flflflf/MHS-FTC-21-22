package com.example.meepmeeptrajectorytesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.ColorScheme;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                .setConstraints(52.48180821614297, 52.48180821614297, Math.toRadians(184.02607784577722), Math.toRadians(184.02607784577722), 16.34)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(-35, -60, Math.toRadians(90)))
                                .strafeLeft(10)
                                .waitSeconds(4)// temperary marker just to visualize the turning
                                .addDisplacementMarker(() -> {
                                    //spin the thing
                                })
                                .strafeTo(new Vector2d(-11,-42))
                                .addTemporalMarker(()->{
                                    //put the item to the right height
                                })
                                .waitSeconds(4)// also temp
                                .back(4)
                                .lineToLinearHeading(new Pose2d(-60,-35,Math.toRadians(0)))
                                .build()
                        );

        meepMeep.setBackground(MeepMeep.Background.FIELD_FREIGHTFRENZY_ADI_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}