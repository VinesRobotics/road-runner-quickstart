package org.firstinspires.ftc.teamcode.drive.activity;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * FINISHED AUTONOMOUS
 */
@Disabled
@Autonomous(name = "FFBlueCarouselAndDeliver", group = "FF",preselectTeleOp = "FFTeleOp")
public class FFBlueCarouselAndDeliver extends LinearOpMode {
    private double carouselPowerForward = 0.60;
    private double carouselPowerBackwards = -0.60;
    private double liftMotorPowerUp = -1;
    private double liftMotorPowerDown = 0.75;
    private double platformPower = 0.75;
    private static int TOP = -1100, UPPER_LEVEL = -724, MIDDLE_LEVEL = -217, LOWER_LEVEL = 248, BOTTOM = 589, STARTING_POS = -60;
    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-28.4, 65.0, Math.toRadians(-45.0)));
        drive.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        waitForStart();

        if (isStopRequested()) return;

        Trajectory toShipping = drive.trajectoryBuilder(new Pose2d(-28.75, 62.4, Math.toRadians(-45.0)),Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 46, Math.toRadians(270)), Math.toRadians(270))
                .build();
        Trajectory toCarousel = drive.trajectoryBuilder(new Pose2d(-12, 46, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-12, 64, Math.toRadians(270)), Math.toRadians(270))
                .splineToSplineHeading(new Pose2d(-60, 58, Math.toRadians(-270)), Math.toRadians(74))
                .build();
        Trajectory toParking = drive.trajectoryBuilder(new Pose2d(-60,56, Math.toRadians(74)),Math.toRadians(77))
                .splineToSplineHeading(new Pose2d(-60,36, Math.toRadians(270)),Math.toRadians(-270))
                .build();
        drive.cappingServo.setPosition(0.34);
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
        drive.followTrajectory(toShipping);
        sleep(250);
        while (drive.magnetSensor.getState()) {
            drive.platformMotor.setPower(platformPower);
        }
        drive.platformMotor.setPower(0.0);
        drive.intake.setPower(1.0);
        sleep(3000);
        drive.platformMotor.setPower(-platformPower);
        sleep(2000);
        while(!(drive.liftMotor.getCurrentPosition() <= (MIDDLE_LEVEL+70))){
            drive.liftMotor.setPower(liftMotorPowerDown);
        }
        drive.liftMotor.setPower(0.0);
        drive.followTrajectory(toCarousel);
        drive.carouselMotor.setPower(carouselPowerBackwards);
        sleep(3000);
        drive.carouselMotor.setPower(0.0);
        drive.followTrajectory(toParking);
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
    }
}
