package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/*
 * This is an example of a more complex path to really test the tuning.
 */
@Autonomous(name = "FFBlueWarehouse", group = "FF")
public class FFBlueWarehouse extends LinearOpMode {
    private double platformPower = 0.75;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        waitForStart();

        if (isStopRequested()) return;

        Trajectory toShippingHub = drive.trajectoryBuilder(new Pose2d(-12, 65, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 43.2, Math.toRadians(270)), Math.toRadians(270))
                .build();
        Trajectory toWarehouse = drive.trajectoryBuilder(new Pose2d(-12, 43.2, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 55, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 45.10, Math.toRadians(0)), Math.toRadians(0))
                .build();
        drive.followTrajectory(toShippingHub);
        while (drive.magnetSensor.getState()) {
            drive.platformMotor.setPower(platformPower);
        }
        drive.platformMotor.setPower(0.0);
        sleep(2000);
    }
}
