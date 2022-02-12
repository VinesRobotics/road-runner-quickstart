package org.firstinspires.ftc.teamcode.drive.activity;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

/*
 * FINISHED AUTONOMOUS
 */
@Autonomous(name = "FFRedCarousel", group = "FF",preselectTeleOp = "FFTeleOp")
public class FFRedCarousel extends LinearOpMode {
    private double carouselPowerForward = 0.60;
    private double carouselPowerBackwards = -0.60;
    private double liftMotorPowerUp = -1;
    private double liftMotorPowerDown = 0.75;
    private static int UPPER_LEVEL = -724;

    @Override
    public void runOpMode() throws InterruptedException {
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        drive.setPoseEstimate(new Pose2d(-28.75, -62.4, Math.toRadians(-270.0)));
        drive.liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        drive.liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        if (isStopRequested()) return;

        Trajectory toCarousel = drive.trajectoryBuilder(new Pose2d(-28.75, -62.4, Math.toRadians(-230.0)),-270)
                .splineToSplineHeading(new Pose2d(-60, -58, Math.toRadians(170)), Math.toRadians(160.75))
                .build();
        Trajectory toStorage = drive.trajectoryBuilder(new Pose2d(-60,-58, Math.toRadians(170)),Math.toRadians(160.75))
                .splineToSplineHeading(new Pose2d(-60, -37.5, Math.toRadians(-270)),Math.toRadians(-270))
                .build();
        drive.followTrajectory(toCarousel);
        sleep(250);
        drive.carouselMotor.setPower(carouselPowerForward);
        sleep(3000);
        drive.carouselMotor.setPower(0.0);
        drive.followTrajectory(toStorage);
        while(!(drive.liftMotor.getCurrentPosition() <= (UPPER_LEVEL-70))){
            drive.liftMotor.setPower(liftMotorPowerUp);
        }
        drive.liftMotor.setPower(0.0);
        drive.cappingServo.setPosition(0.34);
    }
}
