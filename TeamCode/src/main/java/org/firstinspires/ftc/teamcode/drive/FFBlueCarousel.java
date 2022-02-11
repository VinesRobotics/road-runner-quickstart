package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "FFBlueCarousel", group = "FF")
public class FFBlueCarousel extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-28.4, 65.0, Math.toRadians(270.0)));

        waitForStart();

        if (isStopRequested()) return;

        Trajectory traj = drive.trajectoryBuilder(new Pose2d(-28.75, 62.4, Math.toRadians(270.0)))
//                .splineTo(new Vector2d(-36, 36), Math.toRadians(150))
//                .splineTo(new Vector2d(-60, 56), Math.toRadians(77))
                .splineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(175)), Math.toRadians(150))
                .splineToSplineHeading(new Pose2d(-60,56, Math.toRadians(77)),Math.toRadians(77))
                .build();

        drive.followTrajectory(traj);
    }
}
