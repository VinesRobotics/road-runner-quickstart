package org.firstinspires.ftc.teamcode.drive.activity;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * FINISHED AUTONOMOUS
 */
@Autonomous(name = "FFBlueCarousel", group = "FF")
public class FFBlueCarousel extends LinearOpMode {
    private double carouselPowerForward = 0.65;
    private double carouselPowerBackwards = -0.65;
    private double liftMotorPowerUp = -1;
    private double liftMotorPowerDown = 0.75;
    private static int UPPER_LEVEL = -724;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-28.4, 65.0, Math.toRadians(270.0)));

        waitForStart();

        if (isStopRequested()) return;

        Trajectory toCarousel = drive.trajectoryBuilder(new Pose2d(-28.75, 62.4, Math.toRadians(270.0)))
                .splineToSplineHeading(new Pose2d(-36, 36, Math.toRadians(175)), Math.toRadians(150))
                .splineToSplineHeading(new Pose2d(-60,58, Math.toRadians(74)),Math.toRadians(77))
                .build();
        Trajectory toStorage = drive.trajectoryBuilder(new Pose2d(-60,56, Math.toRadians(74)),Math.toRadians(77))
                .splineToSplineHeading(new Pose2d(-60, 38, Math.toRadians(100)), Math.toRadians(90))
                .build();
        drive.followTrajectory(toCarousel);
        sleep(250);
        drive.carouselMotor.setPower(carouselPowerBackwards);
        sleep(2000);
        drive.carouselMotor.setPower(0.0);
        drive.followTrajectory(toStorage);
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
        drive.cappingServo.setPosition(0.34);
    }
}
